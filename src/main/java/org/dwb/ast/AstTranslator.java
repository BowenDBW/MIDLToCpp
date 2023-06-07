package org.dwb.ast;

import org.dwb.antlr.MidlGrammarBaseVisitor;
import org.dwb.antlr.MidlGrammarParser;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 *  AstTranslator 进行抽象语法树的格式化输出
 * 参考：AST的设计PPT
 */
public class AstTranslator extends MidlGrammarBaseVisitor<String> {

    //存储抽象语法树
    public String astParseTree = "";
    /**
     * specification -> definition { definition }
     */
    @Override
    public String visitSpecification(MidlGrammarParser.@NotNull SpecificationContext ctx) {
        astParseTree += "specification( ";
        for (int i = 0; i < ctx.getChildCount(); i++) {
            visit(ctx.getChild(i));
        }
        astParseTree += " ) ";
        return null;
    }

    /**
     * definiton -> type_decl“;”| module “;”
     */
    @Override
    public String visitDefinition(MidlGrammarParser.@NotNull DefinitionContext ctx) {

        visit(ctx.getChild(0));
        return null;
    }

    /**
     * module -> “module”ID “{” definition { definition } “}”
     */
    @Override
    public String visitModule(MidlGrammarParser.@NotNull ModuleContext ctx) {
        astParseTree += "module( ";
        astParseTree += "ID：" + ctx.getChild(1).getText()+" ";
        //不visit两边的大括号了
        for(int i = 3;i < ctx.getChildCount()-1;i++) {
            visit(ctx.getChild(i));
        }
        astParseTree += " ) ";
        return null;
    }

    /**
     * type_decl -> struct_type | “struct” ID
     */
    @Override
    public String visitType_decl(MidlGrammarParser.@NotNull Type_declContext ctx) {
        //struct_type
        if (ctx.getChildCount() == 1) {
            visit(ctx.getChild(0));
        }
        //"struct" ID
        else {
            astParseTree +="struct( ";
            astParseTree +="ID："+ctx.ID().getText()+" ";
            astParseTree +=" ) ";
        }
        return null;
    }

    /***
     * struct_type->“struct” ID “{”   member_list “}”
     */
    @Override
    public String visitStruct_type(MidlGrammarParser.@NotNull Struct_typeContext ctx) {
        astParseTree +="struct( ";
        astParseTree +="ID："+ctx.getChild(1).getText()+" ";
        visit(ctx.getChild(3));
        astParseTree +=" ) ";
        return null;
    }

    /**
     * member_list{ type_spec declarators “;” }
     */
    @Override
    public String visitMember_list(MidlGrammarParser.@NotNull Member_listContext ctx) {
        int n = ctx.getChildCount();
        if (n == 0) {
            return null;
        }
        else {
            for (int i = 0; i < n / 3; i++) {
                astParseTree += "member( ";
                visit(ctx.getChild(3 * i));
                visit(ctx.getChild(3 * i + 1));
                astParseTree +=" ) ";
            }
        }
        return null;
    }

    /**
     * type_spec -> scoped_name | base_type_spec | struct_type
     */
    @Override
    public String visitType_spec(MidlGrammarParser.@NotNull Type_specContext ctx) {
        visit(ctx.getChild(0));
        return null;
    }

    /**
     * scoped_name -> [“::”] ID {“::” ID }
     */
    @Override
    public String visitScoped_name(MidlGrammarParser.@NotNull Scoped_nameContext ctx) {
        astParseTree += "namespace( ";
        int i;
        if(Objects.equals(ctx.getChild(0).getText(), "::")){
            i = 1;
        }
        else{
            i = 0;
        }
        for (; i < ctx.getChildCount(); i += 2) {
            astParseTree += ctx.getChild(i).getText()+" ";
        }
        astParseTree += " ) ";
        return null;
    }

    /**
     * base_type_spec->floating_pt_type|integer_type|“char”|“string”|“boolean”
     */
    @Override
    public String visitBase_type_spec(MidlGrammarParser.@NotNull Base_type_specContext ctx) {
        //终结符
        if (ctx.getChild(0).getChildCount() == 0) {
            astParseTree += ctx.getChild(0).getText()+" ";
        }
        else {
            visit(ctx.getChild(0));
        }
        return null;
    }

    /**
     * floating_pt_type -> “float” | “double” | “long double”
     */
    @Override
    public String visitFloating_pt_type(MidlGrammarParser.@NotNull Floating_pt_typeContext ctx) {

        astParseTree += ctx.getChild(0).getText()+" ";
        return null;
    }

    /**
     * integer_type -> signed_int | unsigned_int
     */
    @Override
    public String visitInteger_type(MidlGrammarParser.@NotNull Integer_typeContext ctx) {

        visit(ctx.getChild(0));
        return null;
    }

    /**
     * signed_int->(“short”|“int16”)
     * |(“long”|“int32”)
     * |(“long long”|“int64”)
     * |“int8”
     */
    @Override
    public String visitSigned_int(MidlGrammarParser.@NotNull Signed_intContext ctx) {

        astParseTree += ctx.getChild(0).getText()+" ";

        return null;
    }

    /**
     * unsigned_int -> (“unsigned short”| “uint16”)
     *    | (“unsigned long”| “uint32”)
     *    | (“unsigned long long” | “uint64”)
     *    | “uint8”
     */
    @Override
    public String visitUnsigned_int(MidlGrammarParser.@NotNull Unsigned_intContext ctx) {

        astParseTree += ctx.getChild(0).getText()+" ";
        return null;
    }

    /**
     * declarators -> declarator {“,” declarator }
     */
    @Override
    public String visitDeclarators(MidlGrammarParser.@NotNull DeclaratorsContext ctx) {
        int n = ctx.getChildCount();
        for(int i = 0;i < n;i++)
        {
            //除去逗号
            if(ctx.getChild(i).getChildCount()!=0)
                visit(ctx.getChild(i));
        }
        return null;
    }

    /**
     * declarator -> simple_declarator | array_declarator
     */
    @Override
    public String visitDeclarator(MidlGrammarParser.@NotNull DeclaratorContext ctx) {
        visit(ctx.getChild(0));
        return null;
    }

    /**
     * simple_declarator -> ID [“=” or_expr]
     */
    @Override
    public String visitSimple_declarator(MidlGrammarParser.@NotNull Simple_declaratorContext ctx) {

        if (ctx.getChildCount() != 1) {
            astParseTree += "=( ";
            astParseTree += "ID：" + ctx.getChild(0).getText()+" ";
            visit(ctx.or_expr());
            astParseTree += " ) ";
        }
        else astParseTree += "ID：" + ctx.getChild(0).getText()+" ";
        return null;
    }

    /**
     * array_declarator -> ID “[” or_expr “]” [“=” exp_list ]
     */
    @Override
    public String visitArray_declarator(MidlGrammarParser.@NotNull Array_declaratorContext ctx) {
        astParseTree += "array( ";
        astParseTree += "ID:"+ctx.getChild(0).getText() + " ";

        visit(ctx.getChild(2));

        if (ctx.getChildCount() != 4) {
            visit(ctx.exp_list());
        }
        astParseTree += " ) ";
        return null;
    }

    /**
     * exp_list -> “[” or_expr { “,”or_expr } “]”
     */
    @Override
    public String visitExp_list(MidlGrammarParser.@NotNull Exp_listContext ctx) {
        astParseTree += "arrayValues( ";
        int n = ctx.getChildCount();
        for (int i = 0; i < n; i ++) {
            if(ctx.getChild(i).getChildCount()>0)
                visit(ctx.getChild(i));
        }
        astParseTree += " ) ";
        return null;
    }

    /**
     * or_expr -> xor_expr {“|” xor_expr }
     */
    @Override
    public String visitOr_expr(MidlGrammarParser.@NotNull Or_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                astParseTree += "|( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * xor_expr -> and_expr {“^” and_expr }
     */
    @Override
    public String visitXor_expr(MidlGrammarParser.@NotNull Xor_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                astParseTree += "^( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * and_expr -> shift_expr {“&”shift_expr }
     */
    @Override
    public String visitAnd_expr(MidlGrammarParser.@NotNull And_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                astParseTree += "&( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * shift_expr -> add_expr { (“>>” | “<<”) add_expr }
     */
    @Override
    public String visitShift_expr(MidlGrammarParser.@NotNull Shift_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                //>>或<<
                astParseTree += ctx.getChild(i).getText()+"( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * add_expr -> mult_expr {(“+” | “-”)  mult_expr }
     */
    @Override
    public String visitAdd_expr(MidlGrammarParser.@NotNull Add_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                //>>或<<
                astParseTree += ctx.getChild(i).getText()+"( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * mult_expr -> unary_expr { (“*” |“/”|“%”) unary_expr }
     */
    @Override
    public String visitMult_expr(MidlGrammarParser.@NotNull Mult_exprContext ctx) {
        int n = ctx.getChildCount();
        if(n > 1) {
            for (int i = 1; i < n; i += 2) {
                //>>或<<
                astParseTree += ctx.getChild(i).getText()+"( ";
                visit(ctx.getChild(i - 1));
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
            for (int i = 1; i < n; i += 2) {
                astParseTree += " ) ";
            }
        }
        else visit(ctx.getChild(0));
        return null;
    }

    /**
     * unary_expr -> [“-”| “+” | “~”] literal
     */
    @Override
    public String visitUnary_expr(MidlGrammarParser.@NotNull Unary_exprContext ctx) {

        if (ctx.getChildCount() != 1) {
            astParseTree += ctx.getChild(0).getText()+"( ";
        }
        visit(ctx.literal());
        if (ctx.getChildCount() != 1) {
            astParseTree += " ) ";
        }
        return null;
    }

    /**
     * literal -> INTEGER | FLOATING_PT | CHAR | STRING | BOOLEAN
     */
    @Override
    public String visitLiteral(MidlGrammarParser.@NotNull LiteralContext ctx) {
        astParseTree += ctx.getChild(0)+" ";
        return null;
    }
}
