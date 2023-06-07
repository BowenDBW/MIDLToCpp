package org.dwb.ast;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.dwb.antlr.MidlGrammarLexer;
import org.dwb.antlr.MidlGrammarParser;

import java.io.*;

/**
 * MIDL转换抽象语法树的启动类
 * Input:按行读取输入文件
 * Output:输出格式化抽象语法树到对应文件
 */
public class AstMain {
    private static final String inputFileName = "multi_midl.txt";
    private static final String outputFileName = "middle.txt";

    public static void main(String[] args) throws Exception {
        File inputFile = new File(inputFileName);
        if (inputFile.exists() && inputFile.isFile()) {
            System.out.println("Workplace: " + System.getProperties().getProperty("user.dir"));
            System.out.println("Successfully read " + inputFileName + "\n");
        } else {
            System.out.println("Invalid input file!");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        File outputFile = new File(outputFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        String line;
        while ((line = bufferedReader.readLine()) != null) {

            CharStream input = CharStreams.fromString(line);
            MidlGrammarLexer lexer = new MidlGrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MidlGrammarParser parser = new MidlGrammarParser(tokens);
            ParseTree tree = parser.specification();
            AstTranslator ag = new AstTranslator();
            ag.visit(tree);

            bufferedWriter.write(ag.astParseTree + "\n");
            bufferedWriter.flush();
        }
        bufferedReader.close();
        bufferedWriter.close();

        new AstToGraphviz().visualize(outputFileName, "ast_out");
    }
}
