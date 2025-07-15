// Generated from PiccodeScript.g4 by ANTLR 4.9.3

	package org.piccode.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PiccodeScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PiccodeScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#stmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmts(PiccodeScriptParser.StmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(PiccodeScriptParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#import_module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_module(PiccodeScriptParser.Import_moduleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#module_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule_path(PiccodeScriptParser.Module_pathContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#symbol_lift}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol_lift(PiccodeScriptParser.Symbol_liftContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#symbol_entry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSymbol_entry(PiccodeScriptParser.Symbol_entryContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(PiccodeScriptParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(PiccodeScriptParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#module_stmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule_stmts(PiccodeScriptParser.Module_stmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#module_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule_stmt(PiccodeScriptParser.Module_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#annotations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotations(PiccodeScriptParser.AnnotationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#func}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc(PiccodeScriptParser.FuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#func_args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunc_args(PiccodeScriptParser.Func_argsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#arg_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_list(PiccodeScriptParser.Arg_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(PiccodeScriptParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#expr_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_stmt(PiccodeScriptParser.Expr_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PiccodeScriptParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#closure_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosure_decl(PiccodeScriptParser.Closure_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(PiccodeScriptParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#if_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_expr(PiccodeScriptParser.If_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#when_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen_expr(PiccodeScriptParser.When_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#when_cases}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen_cases(PiccodeScriptParser.When_casesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#when_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhen_case(PiccodeScriptParser.When_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#else_case}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_case(PiccodeScriptParser.Else_caseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#var_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar_decl(PiccodeScriptParser.Var_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#let_decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLet_decl(PiccodeScriptParser.Let_declContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#tuple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTuple(PiccodeScriptParser.TupleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(PiccodeScriptParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(PiccodeScriptParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr_list(PiccodeScriptParser.Expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#call_expr_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_expr_list(PiccodeScriptParser.Call_expr_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#call_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall_expr(PiccodeScriptParser.Call_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#key_val_pair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey_val_pair(PiccodeScriptParser.Key_val_pairContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#key_val_pairs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey_val_pairs(PiccodeScriptParser.Key_val_pairsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PiccodeScriptParser#do_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_expr(PiccodeScriptParser.Do_exprContext ctx);
}