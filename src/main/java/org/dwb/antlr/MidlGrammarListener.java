package org.dwb.antlr;// Generated from /home/bowen/Code/IDEA_PROJECT/MIDLToCpp/g4/MidlGrammar.g4 by ANTLR 4.12.0

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MidlGrammarParser}.
 */
public interface MidlGrammarListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#specification}.
     *
     * @param ctx the parse tree
     */
    void enterSpecification(MidlGrammarParser.SpecificationContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#specification}.
     *
     * @param ctx the parse tree
     */
    void exitSpecification(MidlGrammarParser.SpecificationContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#definition}.
     *
     * @param ctx the parse tree
     */
    void enterDefinition(MidlGrammarParser.DefinitionContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#definition}.
     *
     * @param ctx the parse tree
     */
    void exitDefinition(MidlGrammarParser.DefinitionContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#module}.
     *
     * @param ctx the parse tree
     */
    void enterModule(MidlGrammarParser.ModuleContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#module}.
     *
     * @param ctx the parse tree
     */
    void exitModule(MidlGrammarParser.ModuleContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#type_decl}.
     *
     * @param ctx the parse tree
     */
    void enterType_decl(MidlGrammarParser.Type_declContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#type_decl}.
     *
     * @param ctx the parse tree
     */
    void exitType_decl(MidlGrammarParser.Type_declContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#struct_type}.
     *
     * @param ctx the parse tree
     */
    void enterStruct_type(MidlGrammarParser.Struct_typeContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#struct_type}.
     *
     * @param ctx the parse tree
     */
    void exitStruct_type(MidlGrammarParser.Struct_typeContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#member_list}.
     *
     * @param ctx the parse tree
     */
    void enterMember_list(MidlGrammarParser.Member_listContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#member_list}.
     *
     * @param ctx the parse tree
     */
    void exitMember_list(MidlGrammarParser.Member_listContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#type_spec}.
     *
     * @param ctx the parse tree
     */
    void enterType_spec(MidlGrammarParser.Type_specContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#type_spec}.
     *
     * @param ctx the parse tree
     */
    void exitType_spec(MidlGrammarParser.Type_specContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#scoped_name}.
     *
     * @param ctx the parse tree
     */
    void enterScoped_name(MidlGrammarParser.Scoped_nameContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#scoped_name}.
     *
     * @param ctx the parse tree
     */
    void exitScoped_name(MidlGrammarParser.Scoped_nameContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#base_type_spec}.
     *
     * @param ctx the parse tree
     */
    void enterBase_type_spec(MidlGrammarParser.Base_type_specContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#base_type_spec}.
     *
     * @param ctx the parse tree
     */
    void exitBase_type_spec(MidlGrammarParser.Base_type_specContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#floating_pt_type}.
     *
     * @param ctx the parse tree
     */
    void enterFloating_pt_type(MidlGrammarParser.Floating_pt_typeContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#floating_pt_type}.
     *
     * @param ctx the parse tree
     */
    void exitFloating_pt_type(MidlGrammarParser.Floating_pt_typeContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#integer_type}.
     *
     * @param ctx the parse tree
     */
    void enterInteger_type(MidlGrammarParser.Integer_typeContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#integer_type}.
     *
     * @param ctx the parse tree
     */
    void exitInteger_type(MidlGrammarParser.Integer_typeContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#signed_int}.
     *
     * @param ctx the parse tree
     */
    void enterSigned_int(MidlGrammarParser.Signed_intContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#signed_int}.
     *
     * @param ctx the parse tree
     */
    void exitSigned_int(MidlGrammarParser.Signed_intContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#unsigned_int}.
     *
     * @param ctx the parse tree
     */
    void enterUnsigned_int(MidlGrammarParser.Unsigned_intContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#unsigned_int}.
     *
     * @param ctx the parse tree
     */
    void exitUnsigned_int(MidlGrammarParser.Unsigned_intContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#declarators}.
     *
     * @param ctx the parse tree
     */
    void enterDeclarators(MidlGrammarParser.DeclaratorsContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#declarators}.
     *
     * @param ctx the parse tree
     */
    void exitDeclarators(MidlGrammarParser.DeclaratorsContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#declarator}.
     *
     * @param ctx the parse tree
     */
    void enterDeclarator(MidlGrammarParser.DeclaratorContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#declarator}.
     *
     * @param ctx the parse tree
     */
    void exitDeclarator(MidlGrammarParser.DeclaratorContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#simple_declarator}.
     *
     * @param ctx the parse tree
     */
    void enterSimple_declarator(MidlGrammarParser.Simple_declaratorContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#simple_declarator}.
     *
     * @param ctx the parse tree
     */
    void exitSimple_declarator(MidlGrammarParser.Simple_declaratorContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#array_declarator}.
     *
     * @param ctx the parse tree
     */
    void enterArray_declarator(MidlGrammarParser.Array_declaratorContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#array_declarator}.
     *
     * @param ctx the parse tree
     */
    void exitArray_declarator(MidlGrammarParser.Array_declaratorContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#exp_list}.
     *
     * @param ctx the parse tree
     */
    void enterExp_list(MidlGrammarParser.Exp_listContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#exp_list}.
     *
     * @param ctx the parse tree
     */
    void exitExp_list(MidlGrammarParser.Exp_listContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#or_expr}.
     *
     * @param ctx the parse tree
     */
    void enterOr_expr(MidlGrammarParser.Or_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#or_expr}.
     *
     * @param ctx the parse tree
     */
    void exitOr_expr(MidlGrammarParser.Or_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#xor_expr}.
     *
     * @param ctx the parse tree
     */
    void enterXor_expr(MidlGrammarParser.Xor_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#xor_expr}.
     *
     * @param ctx the parse tree
     */
    void exitXor_expr(MidlGrammarParser.Xor_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#and_expr}.
     *
     * @param ctx the parse tree
     */
    void enterAnd_expr(MidlGrammarParser.And_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#and_expr}.
     *
     * @param ctx the parse tree
     */
    void exitAnd_expr(MidlGrammarParser.And_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#shift_expr}.
     *
     * @param ctx the parse tree
     */
    void enterShift_expr(MidlGrammarParser.Shift_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#shift_expr}.
     *
     * @param ctx the parse tree
     */
    void exitShift_expr(MidlGrammarParser.Shift_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#add_expr}.
     *
     * @param ctx the parse tree
     */
    void enterAdd_expr(MidlGrammarParser.Add_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#add_expr}.
     *
     * @param ctx the parse tree
     */
    void exitAdd_expr(MidlGrammarParser.Add_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#mult_expr}.
     *
     * @param ctx the parse tree
     */
    void enterMult_expr(MidlGrammarParser.Mult_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#mult_expr}.
     *
     * @param ctx the parse tree
     */
    void exitMult_expr(MidlGrammarParser.Mult_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#unary_expr}.
     *
     * @param ctx the parse tree
     */
    void enterUnary_expr(MidlGrammarParser.Unary_exprContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#unary_expr}.
     *
     * @param ctx the parse tree
     */
    void exitUnary_expr(MidlGrammarParser.Unary_exprContext ctx);

    /**
     * Enter a parse tree produced by {@link MidlGrammarParser#literal}.
     *
     * @param ctx the parse tree
     */
    void enterLiteral(MidlGrammarParser.LiteralContext ctx);

    /**
     * Exit a parse tree produced by {@link MidlGrammarParser#literal}.
     *
     * @param ctx the parse tree
     */
    void exitLiteral(MidlGrammarParser.LiteralContext ctx);
}