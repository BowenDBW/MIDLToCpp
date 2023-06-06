package org.dwb.symtab;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import org.dwb.antlr.MidlGrammarLexer;
import org.dwb.antlr.MidlGrammarParser;
import org.dwb.precheck.MeanCheck;

import java.io.*;

/**
 * MIDL转换抽象语法树的启动类
 * Input:按行读取输入文件
 * Output:输出格式化抽象语法树到对应文件
 */
public class TestSymtab{

    private static final String inputFileName = "inputLines.txt";

    public static void test() throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new FileReader(inputFileName));
        StringBuilder codes= new StringBuilder();
        String line;
        int cnt = 1;
        while((line = bufferedReader.readLine()) != null) {
            System.out.println("["+cnt + "]: " + line);
            codes.append(line);
            cnt++;
        }

        //词法检查-语法检查
        CharStream input = CharStreams.fromString(codes.toString());
        MidlGrammarLexer lexer = new MidlGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MidlGrammarParser parser = new MidlGrammarParser(tokens);
        ParseTree tree = parser.specification();
        //遍历分析树-语义检查
        MeanCheck mc = new MeanCheck();
        mc.visit(tree);

        bufferedReader.close();

    }
    public static void main(String[] args) throws Exception {
        test();
    }
}
