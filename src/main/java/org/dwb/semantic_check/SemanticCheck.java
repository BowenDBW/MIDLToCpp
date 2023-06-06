package org.dwb.semantic_check;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.dwb.antlr.MidlGrammarLexer;
import org.dwb.antlr.MidlGrammarParser;

import java.io.*;

/**
 * 整合前端
 * 词法-语法-语义
 */
public class SemanticCheck {
    private static final String inputFileName = "inputLines.txt";

    public static MeanCheck mc;
    public static void preCheck() throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new FileReader(inputFileName));
        StringBuilder codes= new StringBuilder();
        String line;
        int cnt = 1;
        while((line = bufferedReader.readLine()) != null) {
            System.out.printf("[%03d]: %s",cnt,line);
            System.out.println();
            codes.append(line).append("\n");
            cnt++;
        }

        //词法检查-语法检查
        CharStream input = CharStreams.fromString(codes.toString());
        MidlGrammarLexer lexer = new MidlGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MidlGrammarParser parser = new MidlGrammarParser(tokens);
        ParseTree tree = parser.specification();
        //遍历分析树-语义检查
        System.out.println("Semantic Check Processing...");
        mc = new MeanCheck();
        mc.visit(tree);
        System.out.println("Semantic Check Finished.");
        //打印错误信息
        System.err.println("Errors TraceBack: ");
        System.err.println(mc.er.getErrors());

        //打印符号表
        System.out.println("Found Errors: ");
        System.out.println(mc.st.toString());

        bufferedReader.close();
    }

    public static void main(String[] args) throws IOException {
        preCheck();
    }
}
