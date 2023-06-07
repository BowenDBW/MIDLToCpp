package org.dwb.semantic_check;

import org.dwb.antlr.MidlGrammarBaseVisitor;
import org.dwb.antlr.MidlGrammarParser;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 语义分析,计算收集信息插入符号表
 */
public class SemanticCheck extends MidlGrammarBaseVisitor<String> {

    //错误类型
    public static final int redefined = 0;
    public static final int undefined = 1;
    public static final int typeError = 2;
    public static final int overflow = 3;
    public static final int outIndex = 4;
    //符号表相关
    public ParamTable st = new ParamTable();
    public ErrorRecorder er = new ErrorRecorder();
    //当前的module和struct
    public ArrayList<String> tempModule = new ArrayList<String>();
    public ArrayList<String> tempStruct = new ArrayList<String>();
    //当前有属性等待赋值的节点
    public ParamNode tempNode = new ParamNode();
    public String lastStruct = "";
    public ArrayList<ParamNode> tempNodes = new ArrayList<ParamNode>();
    //当前正负号
    public boolean isPos = true;
    //是否舍弃当前节点
    public boolean abandoned = false;

    String typeLoc(@NotNull String type) {
        String loc;
        String[] types = {
                "float", "double", "long double",
                "unsigned short", "uint16"
                , "unsigned long", "uint32"
                , "unsigned long long", "uint64"
                , "uint8", "short", "int16"
                , "long", "int32"
                , "long long", "int64"
                , "int8"
                , "char",
                "string",
                "boolean"};
        int i = 0;
        if (type.startsWith("Array")) {
            type = type.substring(6, type.length() - 1);
        }
        for (; i < types.length; i++) {
            if (types[i].equals(type))
                break;
        }
        if (i <= 2) {
            loc = "FLOATING_PT";
        } else if (i <= 16) {
            loc = "INTEGER";
        } else if (i <= 17) {
            loc = "CHAR";
        } else if (i <= 18) {
            loc = "STRING";
        } else if (i <= 19) {
            loc = "BOOLEAN";
        } else loc = "SCOPED";
        return loc;
    }


    /**
     * specification -> definition { definition }
     */
    @Override
    public String visitSpecification(MidlGrammarParser.@NotNull SpecificationContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            tempModule.clear();
            visit(ctx.getChild(i));
        }
        return null;
    }

    /**
     * definiton -> type_decl“;”| module “;”
     */
    @Override
    public String visitDefinition(MidlGrammarParser.@NotNull DefinitionContext ctx) {
        //进入struct或者module的申明

        visit(ctx.getChild(0));

        //module或struct域结束
        if (ctx.getChild(0).getText().startsWith("struct")) {
            if (tempStruct.size() > 1)
                tempStruct.remove(tempStruct.size() - 1);
        } else {
            if (tempModule.size() > 1)
                tempModule.remove(tempModule.size() - 1);
        }
        return null;
    }

    /***
     * module -> “module”ID “{” definition { definition } “}”
     */
    @Override
    public String visitModule(MidlGrammarParser.@NotNull ModuleContext ctx) {

        //新建符号表元素节点
        ParamNode sn = new ParamNode();
        sn.setName(ctx.getChild(1).getText());
        sn.setType("module");
        if (tempModule.size() > 0)
            sn.setModuleName(tempModule.toString());

        //检查是否存在相同的节点申明
        ParamNode result = st.lookupSt(sn);
        //若不存在则插入
        if (result == null) {
            st.insertST(sn);
        }
        //否则报错，不插入
        else {
            er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "module", ctx.getChild(1).getText(), redefined, null);
        }
        //更新当前module域
        tempModule.add(ctx.getChild(1).getText());

        //访问子节点
        for (int i = 3; i < ctx.getChildCount() - 1; i++) {
            visit(ctx.getChild(i));
        }

        return null;
    }

    /**
     * type_decl -> struct_type | “struct” ID
     */
    @Override
    public String visitType_decl(MidlGrammarParser.@NotNull Type_declContext ctx) {
        //struct_type
        if (ctx.getChildCount() == 1) {
            lastStruct = "";
            tempStruct.clear();
            visit(ctx.getChild(0));
        }
        //"struct" ID
        else {
            //新建符号表元素节点
            ParamNode sn = new ParamNode();
            sn.setName(ctx.getChild(1).getText());
            sn.setType("struct");

            if (tempModule.size() > 0)
                sn.setModuleName(tempModule.toString());

            //检查是否存在相同的节点申明
            ParamNode result = st.lookupSt(sn);
            //若不存在则插入
            if (result == null) {
                st.insertST(sn);
            }
            //否则报错，不插入
            else {
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "struct", ctx.getChild(1).getText(), redefined, null);
            }
        }
        return null;
    }

    /***
     * struct_type->“struct” ID “{”   member_list “}”
     */
    @Override
    public String visitStruct_type(MidlGrammarParser.@NotNull Struct_typeContext ctx) {

        ParamNode sn = new ParamNode();
        sn.setName(ctx.getChild(1).getText());
        sn.setType("struct");

        if (tempStruct.size() > 0)
            sn.setStructName(tempStruct.toString());
        if (tempModule.size() > 0)
            sn.setModuleName(tempModule.toString());

        //检查是否存在相同的节点申明
        ParamNode result = st.lookupSt(sn);
        //若不存在则插入
        if (result == null) {
            st.insertST(sn);
        }
        //否则报错，不插入
        else {
            er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "struct", ctx.getChild(1).getText(), redefined, null);
        }
        //更新当前struct域
        tempStruct.add(ctx.getChild(1).getText());
        lastStruct = tempStruct.get(tempStruct.size() - 1);

        //访问子节点
        visit(ctx.getChild(3));

        //保留,以备
        lastStruct = tempStruct.get(tempStruct.size() - 1);
        //退出
        tempStruct.remove(tempStruct.size() - 1);

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
        } else {
            for (int i = 0; i < n / 3; i++) {
                //typeSpec 确定了类型
                visit(ctx.getChild(3 * i));
                //declarators 接收类型
                visit(ctx.getChild(3 * i + 1));
                //删除最后一个
                lastStruct = "";
                //接收完一种类型,更新
                tempNode = new ParamNode();
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
     * 最后一个是struct
     */
    @Override
    public String visitScoped_name(MidlGrammarParser.@NotNull Scoped_nameContext ctx) {
        ArrayList<String> namespaces = new ArrayList<String>();
        int i;
        if (Objects.equals(ctx.getChild(0).getText(), "::")) {
            i = 1;
        } else {
            i = 0;
        }
        //最后一个ID是struct
        for (; i < ctx.getChildCount() - 2; i += 2) {
            namespaces.add(ctx.getChild(i).getText());
        }
        //最后一个ID
        String structName = ctx.getChild(i).getText();
        //module字符串
        String moduleName = null;
        //带有前缀namespace信息则在前面找
        if (namespaces.size() > 0)
            moduleName = namespaces.toString();
        //不带namespace在本地找
        if (namespaces.size() == 0)
            moduleName = tempModule.toString();

        //查询symnode
        ParamNode sn = new ParamNode();
        sn.setName(structName);
        sn.setType("struct");
        if (moduleName != null)
            sn.setModuleName(moduleName);
        ParamNode res = st.lookupSt(sn);
        if (res == null) {

            er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "struct", structName, undefined, null);
        }
        //保留这个node直到申明完成所有declarator,赋值
        else {
            tempNode.setType(res.getModuleName() + "::" + res.getStructName() + "::" + structName);
            tempNode.setStructName(res.getStructName());
            if (moduleName != null)
                tempNode.setModuleName(moduleName);
        }

        return null;
    }

    /**
     * base_type_spec->floating_pt_type|integer_type|“char”|“string”|“boolean”
     */
    @Override
    public String visitBase_type_spec(MidlGrammarParser.@NotNull Base_type_specContext ctx) {

        //若为终结符
        if (ctx.getChild(0).getChildCount() == 0) {
            tempNode.setType(ctx.getChild(0).getText());
        } else {
            visit(ctx.getChild(0));
        }
        return null;
    }

    /**
     * floating_pt_type -> “float” | “double” | “long double”
     */
    @Override
    public String visitFloating_pt_type(MidlGrammarParser.@NotNull Floating_pt_typeContext ctx) {
        //if(abandoned)return null;
        tempNode.setType(ctx.getChild(0).getText());
        return null;
    }

    /**
     * integer_type -> signed_int | unsigned_int
     */
    @Override
    public String visitInteger_type(MidlGrammarParser.@NotNull Integer_typeContext ctx) {
        //if(abandoned)return null;
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
        //if(abandoned)return null;
        tempNode.setType(ctx.getChild(0).getText());

        return null;
    }

    /**
     * unsigned_int -> (“unsigned short”| “uint16”)
     * | (“unsigned long”| “uint32”)
     * | (“unsigned long long” | “uint64”)
     * | “uint8”
     */
    @Override
    public String visitUnsigned_int(MidlGrammarParser.@NotNull Unsigned_intContext ctx) {
        //if(abandoned)return null;
        tempNode.setType(ctx.getChild(0).getText());
        return null;
    }

    /**
     * declarators -> declarator {“,” declarator }
     */
    @Override
    public String visitDeclarators(MidlGrammarParser.@NotNull DeclaratorsContext ctx) {
        //if(abandoned)return null;

        int n = ctx.getChildCount();
        for (int i = 0; i < n; i++) {
            //同类型的多个节点
            if (ctx.getChild(i).getChildCount() != 0) {
                abandoned = false;
                visit(ctx.getChild(i));
                //遍历完一个declarator及时插入符号表
                if (tempNodes.size() > 0 && !abandoned) {
                    st.insertST(tempNodes.get(tempNodes.size() - 1));
                }

            }
        }
        //declarators所有节点赋值完成
        tempNodes.clear();
        return null;
    }

    /**
     * declarator -> simple_declarator | array_declarator
     */
    @Override
    public String visitDeclarator(MidlGrammarParser.@NotNull DeclaratorContext ctx) {
        //if(abandoned)return null;
        visit(ctx.getChild(0));

        return null;
    }

    /**
     * simple_declarator -> ID [“=” or_expr]
     */
    @Override
    public String visitSimple_declarator(MidlGrammarParser.@NotNull Simple_declaratorContext ctx) {
        //if(abandoned)return null;

        ParamNode sn = new ParamNode();
        sn.setName(ctx.getChild(0).getText());
        if (tempModule.size() > 0)
            sn.setModuleName(tempModule.toString());

        //出现struct A{}a; 或者 scoped失败的情况
        if (tempNode.getType() == null) {
            tempNode.setType(lastStruct);
        }
        if (tempStruct.size() > 0)
            sn.setStructName(tempStruct.toString());

        //检查冲突
        ParamNode res = st.lookupSt(sn);

        //同struct下不能同名变量
        if (res != null)
            er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), tempNode.getType(), ctx.getChild(0).getText(), redefined, null);
        else {
            sn.setType(tempNode.getType());
            if (ctx.getChildCount() == 1 && sn.getType().length() > 0)
                st.insertST(sn);
                //可能需要赋值
            else if (ctx.getChildCount() > 1)
                tempNodes.add(sn);
        }
        //如果申明没出错才进行赋值了
        if (ctx.getChildCount() != 1 && res == null) {
            visit(ctx.or_expr());
        }

        return null;
    }

    /**
     * array_declarator -> ID “[” or_expr “]” [“=” exp_list ]
     */
    @Override
    public String visitArray_declarator(MidlGrammarParser.@NotNull Array_declaratorContext ctx) {
        //if(abandoned)return null;
        ParamNode sn = new ParamNode();
        sn.setName(ctx.getChild(0).getText());
        sn.setStructName(tempStruct.toString());
        sn.setModuleName(tempModule.toString());
        ParamNode res = st.lookupSt(sn);

        //同struct下不能同名变量
        if (res != null)
            er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), tempNode.getType(), ctx.getChild(0).getText(), redefined, null);
        else {
            sn.setType("Array<" + tempNode.getType() + ">");
            tempNodes.add(sn);
        }
        if (res == null) {

            visit(ctx.getChild(2));
            // 说明出现异常，提前终止
            if (tempNodes.get(tempNodes.size() - 1).getVal() == null)
                return null;
            String arrayLength = tempNodes.get(tempNodes.size() - 1).getVal();
            String calLengths = Calculator.calAns(arrayLength, "INTEGER", true);
            //检查一下类型
            int calLength = -1;
            if (calLengths.equals("overflow")) {
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "INTEGER", arrayLength, overflow, null);
                abandoned = true;
            } else if (!calLengths.equals("TypeError")) {
                calLength = Integer.parseInt(calLengths);
                tempNodes.get(tempNodes.size() - 1).setVal(calLength + "#");
            } else
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "Array", arrayLength, typeError, null);

            //统计赋值语句中数组的元素个数
            int eles = 0;
            if (ctx.getChildCount() != 4) {
                for (int i = 0; i < ctx.exp_list().getChildCount(); i++) {
                    if (ctx.exp_list().getChild(i).getChildCount() > 0)
                        eles++;
                }
                //报错且舍弃
                if (eles > calLength && calLength != -1) {
                    er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "Array", tempNodes.get(tempNodes.size() - 1).getName(), outIndex, null);
                    abandoned = true;
                }
                visit(ctx.exp_list());
            }
        }
        return null;
    }

    /**
     * exp_list -> “[” or_expr { “,”or_expr } “]”
     */
    @Override
    public String visitExp_list(MidlGrammarParser.@NotNull Exp_listContext ctx) {

        int n = ctx.getChildCount();
        for (int i = 0; i < n; i++) {
            if (ctx.getChild(i).getChildCount() > 0) {
                visit(ctx.getChild(i));
                String preValues = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preValues + "#");
            }
        }

        return null;
    }

    /**
     * or_expr -> xor_expr {“|” xor_expr }
     */
    @Override
    public String visitOr_expr(MidlGrammarParser.@NotNull Or_exprContext ctx) {

        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {
                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + "|");
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }

        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * xor_expr -> and_expr {“^” and_expr }
     */
    @Override
    public String visitXor_expr(MidlGrammarParser.@NotNull Xor_exprContext ctx) {
        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {
                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + "^");
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * and_expr -> shift_expr {“&”shift_expr }
     */
    @Override
    public String visitAnd_expr(MidlGrammarParser.@NotNull And_exprContext ctx) {
        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {

                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + "&");
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * shift_expr -> add_expr { (“>>” | “<<”) add_expr }
     */
    @Override
    public String visitShift_expr(MidlGrammarParser.@NotNull Shift_exprContext ctx) {
        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {
                //>>或<<
                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + ctx.getChild(i).getText());
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * add_expr -> mult_expr {(“+” | “-”)  mult_expr }
     */
    @Override
    public String visitAdd_expr(MidlGrammarParser.@NotNull Add_exprContext ctx) {
        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {
                //>>或<<
                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + ctx.getChild(i).getText());
                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * mult_expr -> unary_expr { (“*” |“/”|“%”) unary_expr }
     */
    @Override
    public String visitMult_expr(MidlGrammarParser.@NotNull Mult_exprContext ctx) {
        int n = ctx.getChildCount();
        if (n > 1) {
            for (int i = 1; i < n; i += 2) {
                //*” |“/”|“%”

                visit(ctx.getChild(i - 1));
                String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + ctx.getChild(i).getText());

                if (i == n - 2)
                    visit(ctx.getChild(i + 1));
            }
        } else visit(ctx.getChild(0));
        return null;
    }

    /**
     * unary_expr -> [“-”| “+” | “~”] literal
     */
    @Override
    public String visitUnary_expr(MidlGrammarParser.@NotNull Unary_exprContext ctx) {

        if (ctx.getChild(0).getText().equals("-"))
            isPos = false;
        else if (ctx.getChild(0).getText().equals("+"))
            isPos = true;
        if (ctx.getChildCount() != 1) {
            String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
            if (preVal == null)
                tempNodes.get(tempNodes.size() - 1).setVal(ctx.getChild(0).getText());
            else {
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + ctx.getChild(0).getText());
            }
        }

        visit(ctx.literal());
        return null;
    }

    /**
     * literal -> INTEGER | FLOATING_PT | CHAR | STRING | BOOLEAN
     */
    @Override
    public String visitLiteral(MidlGrammarParser.@NotNull LiteralContext ctx) {

        String checkType = tempNodes.get(tempNodes.size() - 1).getType();
        boolean typeTrue = false;
        if (ctx.INTEGER() != null) {
            //看看是不是在赋值数组下标
            if (tempNodes.get(tempNodes.size() - 1).getType().startsWith("Array")) {
                if (!isPos) {
                    er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "INTEGER", "-" + ctx.getChild(0).getText(), overflow, null);
                } else if (Calculator.checkOverFlow(ctx.getChild(0).getText(), isPos))
                    er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "INTEGER", ctx.getChild(0).getText(), overflow, null);
                else typeTrue = true;
            } else if (!typeLoc(checkType).equals("INTEGER"))
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "INTEGER", ctx.getChild(0).getText(), typeError, typeLoc(checkType));
            else if (Calculator.checkOverFlow(ctx.getChild(0).getText(), isPos)) {
                String prefix = "";
                if (!isPos) prefix += "-";
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "INTEGER", prefix + ctx.getChild(0).getText(), overflow, null);
            } else typeTrue = true;
        } else if (ctx.FLOATING_PT() != null) {
            if (!typeLoc(checkType).equals("FLOATING_PT"))
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "FLOATING_PT", ctx.getChild(0).getText(), typeError, typeLoc(checkType));
            else typeTrue = true;
        } else if (ctx.CHAR() != null) {
            if (!typeLoc(checkType).equals("CHAR"))
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "CHAR", ctx.getChild(0).getText(), typeError, typeLoc(checkType));
            else typeTrue = true;
        } else if (ctx.STRING() != null) {
            if (!typeLoc(checkType).equals("STRING"))
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "STRING", ctx.getChild(0).getText(), typeError, typeLoc(checkType));
            else typeTrue = true;
        } else if (ctx.BOOLEAN() != null) {
            if (!typeLoc(checkType).equals("BOOLEAN"))
                er.addError(ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine(), "BOOLEAN", ctx.getChild(0).getText(), typeError, typeLoc(checkType));
            else typeTrue = true;
        }

        //标记这个declaration不要插入符号表
        if (!typeTrue)
            abandoned = true;
        if (typeTrue) {
            //存在~1
            String preVal = tempNodes.get(tempNodes.size() - 1).getVal();
            if (preVal == null)
                tempNodes.get(tempNodes.size() - 1).setVal(ctx.getChild(0).getText());
            else {
                tempNodes.get(tempNodes.size() - 1).setVal(preVal + ctx.getChild(0).getText());
            }
        }
        return null;
    }
}
