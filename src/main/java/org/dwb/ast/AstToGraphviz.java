package org.dwb.ast;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * AstToGraphviz 将结构化的语法信息转为结点信息和边信息并交给 graphviz 输出 png 图像
 */
public class AstToGraphviz {
    /**
     * 去除 String[] 数组中的空字符串("")
     *
     * @param arr 源数组
     * @return 操作后的新数组 String[]
     */
    private String @NotNull [] removeArraysEmpty(String[] arr) {
        return Arrays.stream(arr).filter(s -> !"".equals(s)).toArray(String[]::new);
    }

    private @NotNull ArrayList<AstNode> formTree(@NotNull String line) {
        String[] blocks = line.split(" ");
        blocks = removeArraysEmpty(blocks);
        int layer = 1;
        int count = 0;
        ArrayList<AstNode> nodes = new ArrayList<>();
        Stack<AstNode> parents = new Stack<>();
        for (String word : blocks) {
            if (word.equals(")")) {
                layer--;
                parents.pop();
                continue;
            } else if (word.endsWith("(")) {
                if (layer == 1) {
                    var newNode = AstNode.builder()
                            .nodeId(count)
                            .nodeLayer(layer)
                            .nodeName(word.substring(0, word.length() - 1))
                            .build();
                    nodes.add(newNode);
                    parents.push(newNode);
                } else {
                    var newNode = AstNode.builder()
                            .nodeId(count)
                            .nodeLayer(layer)
                            .nodeName(word.substring(0, word.length() - 1))
                            .parentId(parents.peek().nodeId)
                            .parentName(parents.peek().nodeName)
                            .build();
                    nodes.add(newNode);
                    parents.push(newNode);
                }
                layer++;
            } else {
                var newNode = AstNode.builder()
                        .nodeId(count)
                        .nodeLayer(layer)
                        .nodeName(word)
                        .parentId(parents.peek().nodeId)
                        .parentName(parents.peek().nodeName)
                        .build();
                nodes.add(newNode);
            }
            count++;
        }
        return nodes;
    }

    private void genDotFiles(String outputPath, @NotNull ArrayList<String> srcTxt) throws IOException {
        int count = 1;
        for (String line : srcTxt) {
            int maxDepth = -1;
            ArrayList<AstNode> nodes = formTree(line);
            for (AstNode node : nodes) {
                if (node.nodeLayer > maxDepth) {
                    maxDepth = node.nodeLayer;
                }
            }
            String filePath = outputPath + "/SyntaxOut" + count + ".dot";
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(filePath));
            bufferedWriter.write("graph{");
            bufferedWriter.newLine();
            for (int i = 0; i < maxDepth; i++) {
                bufferedWriter.write("    { rank=same");
                for (AstNode node : nodes) {
                    if (node.nodeLayer - 1 == i) {
                        bufferedWriter.write("; \"" + node.nodeName + "@" + node.nodeId + "\"");
                    }
                }
                bufferedWriter.write("}");
                bufferedWriter.newLine();
            }

            bufferedWriter.newLine();

            for (AstNode node : nodes) {
                if (node.nodeLayer == 1) {
                    continue;
                }
                bufferedWriter.write("    \"" + node.nodeName + "@" + node.nodeId
                        + "\" -- \"" + node.parentName + "@" + node.parentId + "\"");
                bufferedWriter.newLine();
            }
            bufferedWriter.write("}");
            bufferedWriter.close();
            count++;
        }
    }

    private @NotNull ArrayList<String> getAllFiles(@NotNull String dir) {
        File f = new File(dir);
        ArrayList<String> names = new ArrayList<>();
        File[] files = f.listFiles();
        for (File file : files) {
            names.add(file.getAbsolutePath());
        }
        return names;
    }

    private void genGraphs(String outputPath, String dotDir) {
        ArrayList<String> files = getAllFiles(dotDir);
        for (String file : files) {
            System.out.println(file);
            try (InputStream dot = new FileInputStream(file)) {
                MutableGraph g = new Parser().read(dot);
                String serial = file.split("SyntaxOut")[1].split("\\.")[0];
                Graphviz.fromGraph(g).width(1200).render(Format.PNG)
                        .toFile(new File(outputPath + "/SyntaxOut"
                                + serial + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void visualize(String inputPath, String outputPath) throws IOException {

        ArrayList<String> txt = new ArrayList<>();
        BufferedReader bufferedReader =
                new BufferedReader(new FileReader(inputPath));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            txt.add(line);
        }
        bufferedReader.close();
        genDotFiles(outputPath + "/ast_dots", txt);
        try {
            File file = new File(inputPath);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        genGraphs(outputPath + "/ast_graphs", outputPath + "/ast_dots");
    }
}
