package org.dwb.ast;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import org.dwb.antlr.MidlGrammarLexer;
import org.dwb.antlr.MidlGrammarParser;

import java.io.*;

/**
 * MIDL转换抽象语法树的启动类
 * Input:按行读取输入文件
 * Output:输出格式化抽象语法树到对应文件
 */
public class MidlGrammarToAst{
    private static final String inputFileName = "input.txt";
    private static final String outputFileName = "output.txt";
    public static void main(String[] args) throws Exception {
        File inputFile = new File(inputFileName);
        if(inputFile.exists() && inputFile.isFile()) {
            System.out.println("Workplace: " + System.getProperties().getProperty("user.dir"));
            System.out.println("Successfully read " + inputFileName + "\n");
        }
        else {
            System.out.println("Invalid input file!");
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        File outputFile = new File(outputFileName);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

        String line;
        int cnt = 1;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println("Case " + cnt + ": " + line);

            CharStream input = CharStreams.fromString(line);
            MidlGrammarLexer lexer = new MidlGrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MidlGrammarParser parser = new MidlGrammarParser(tokens);
            ParseTree tree = parser.specification();
            ASTFormat ag = new ASTFormat();
            ag.visit(tree);

            System.out.println("Case " + cnt++ + "\'s ASTParseTree: " + ag.astParseTree);
            System.out.println("----------------------------------------");

            bufferedWriter.write(ag.astParseTree + "\n");
            bufferedWriter.flush();
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
}
