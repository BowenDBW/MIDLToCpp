package org.dwb.antlr;// Generated from /home/bowen/Code/IDEA_PROJECT/MIDLToCpp/g4/MidlGrammar.g4 by ANTLR 4.12.0

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MidlGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface MidlGrammarVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#specification}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSpecification(MidlGrammarParser.SpecificationContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#definition}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDefinition(MidlGrammarParser.DefinitionContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#module}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitModule(MidlGrammarParser.ModuleContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#type_decl}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitType_decl(MidlGrammarParser.Type_declContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#struct_type}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitStruct_type(MidlGrammarParser.Struct_typeContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#member_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMember_list(MidlGrammarParser.Member_listContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#type_spec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitType_spec(MidlGrammarParser.Type_specContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#scoped_name}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitScoped_name(MidlGrammarParser.Scoped_nameContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#base_type_spec}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitBase_type_spec(MidlGrammarParser.Base_type_specContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#floating_pt_type}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitFloating_pt_type(MidlGrammarParser.Floating_pt_typeContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#integer_type}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInteger_type(MidlGrammarParser.Integer_typeContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#signed_int}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSigned_int(MidlGrammarParser.Signed_intContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#unsigned_int}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnsigned_int(MidlGrammarParser.Unsigned_intContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#declarators}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDeclarators(MidlGrammarParser.DeclaratorsContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#declarator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDeclarator(MidlGrammarParser.DeclaratorContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#simple_declarator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitSimple_declarator(MidlGrammarParser.Simple_declaratorContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#array_declarator}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitArray_declarator(MidlGrammarParser.Array_declaratorContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#exp_list}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExp_list(MidlGrammarParser.Exp_listContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#or_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOr_expr(MidlGrammarParser.Or_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#xor_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitXor_expr(MidlGrammarParser.Xor_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#and_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAnd_expr(MidlGrammarParser.And_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#shift_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitShift_expr(MidlGrammarParser.Shift_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#add_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAdd_expr(MidlGrammarParser.Add_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#mult_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitMult_expr(MidlGrammarParser.Mult_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#unary_expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitUnary_expr(MidlGrammarParser.Unary_exprContext ctx);

    /**
     * Visit a parse tree produced by {@link MidlGrammarParser#literal}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLiteral(MidlGrammarParser.LiteralContext ctx);
}