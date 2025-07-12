// Generated from PiccodeScript.g4 by ANTLR 4.9.3

	package org.piccode.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PiccodeScriptParser}.
 */
public interface PiccodeScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#stmts}.
	 * @param ctx the parse tree
	 */
	void enterStmts(PiccodeScriptParser.StmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#stmts}.
	 * @param ctx the parse tree
	 */
	void exitStmts(PiccodeScriptParser.StmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(PiccodeScriptParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(PiccodeScriptParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#import_module}.
	 * @param ctx the parse tree
	 */
	void enterImport_module(PiccodeScriptParser.Import_moduleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#import_module}.
	 * @param ctx the parse tree
	 */
	void exitImport_module(PiccodeScriptParser.Import_moduleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#module_path}.
	 * @param ctx the parse tree
	 */
	void enterModule_path(PiccodeScriptParser.Module_pathContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#module_path}.
	 * @param ctx the parse tree
	 */
	void exitModule_path(PiccodeScriptParser.Module_pathContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#symbol_lift}.
	 * @param ctx the parse tree
	 */
	void enterSymbol_lift(PiccodeScriptParser.Symbol_liftContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#symbol_lift}.
	 * @param ctx the parse tree
	 */
	void exitSymbol_lift(PiccodeScriptParser.Symbol_liftContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#symbol_entry}.
	 * @param ctx the parse tree
	 */
	void enterSymbol_entry(PiccodeScriptParser.Symbol_entryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#symbol_entry}.
	 * @param ctx the parse tree
	 */
	void exitSymbol_entry(PiccodeScriptParser.Symbol_entryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(PiccodeScriptParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(PiccodeScriptParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#module}.
	 * @param ctx the parse tree
	 */
	void enterModule(PiccodeScriptParser.ModuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#module}.
	 * @param ctx the parse tree
	 */
	void exitModule(PiccodeScriptParser.ModuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#module_stmts}.
	 * @param ctx the parse tree
	 */
	void enterModule_stmts(PiccodeScriptParser.Module_stmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#module_stmts}.
	 * @param ctx the parse tree
	 */
	void exitModule_stmts(PiccodeScriptParser.Module_stmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#module_stmt}.
	 * @param ctx the parse tree
	 */
	void enterModule_stmt(PiccodeScriptParser.Module_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#module_stmt}.
	 * @param ctx the parse tree
	 */
	void exitModule_stmt(PiccodeScriptParser.Module_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#func}.
	 * @param ctx the parse tree
	 */
	void enterFunc(PiccodeScriptParser.FuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#func}.
	 * @param ctx the parse tree
	 */
	void exitFunc(PiccodeScriptParser.FuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#func_args}.
	 * @param ctx the parse tree
	 */
	void enterFunc_args(PiccodeScriptParser.Func_argsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#func_args}.
	 * @param ctx the parse tree
	 */
	void exitFunc_args(PiccodeScriptParser.Func_argsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void enterArg_list(PiccodeScriptParser.Arg_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void exitArg_list(PiccodeScriptParser.Arg_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(PiccodeScriptParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(PiccodeScriptParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void enterExpr_stmt(PiccodeScriptParser.Expr_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#expr_stmt}.
	 * @param ctx the parse tree
	 */
	void exitExpr_stmt(PiccodeScriptParser.Expr_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PiccodeScriptParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PiccodeScriptParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#closure_decl}.
	 * @param ctx the parse tree
	 */
	void enterClosure_decl(PiccodeScriptParser.Closure_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#closure_decl}.
	 * @param ctx the parse tree
	 */
	void exitClosure_decl(PiccodeScriptParser.Closure_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(PiccodeScriptParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(PiccodeScriptParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#if_expr}.
	 * @param ctx the parse tree
	 */
	void enterIf_expr(PiccodeScriptParser.If_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#if_expr}.
	 * @param ctx the parse tree
	 */
	void exitIf_expr(PiccodeScriptParser.If_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#when_expr}.
	 * @param ctx the parse tree
	 */
	void enterWhen_expr(PiccodeScriptParser.When_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#when_expr}.
	 * @param ctx the parse tree
	 */
	void exitWhen_expr(PiccodeScriptParser.When_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#when_cases}.
	 * @param ctx the parse tree
	 */
	void enterWhen_cases(PiccodeScriptParser.When_casesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#when_cases}.
	 * @param ctx the parse tree
	 */
	void exitWhen_cases(PiccodeScriptParser.When_casesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#when_case}.
	 * @param ctx the parse tree
	 */
	void enterWhen_case(PiccodeScriptParser.When_caseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#when_case}.
	 * @param ctx the parse tree
	 */
	void exitWhen_case(PiccodeScriptParser.When_caseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#else_case}.
	 * @param ctx the parse tree
	 */
	void enterElse_case(PiccodeScriptParser.Else_caseContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#else_case}.
	 * @param ctx the parse tree
	 */
	void exitElse_case(PiccodeScriptParser.Else_caseContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void enterVar_decl(PiccodeScriptParser.Var_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#var_decl}.
	 * @param ctx the parse tree
	 */
	void exitVar_decl(PiccodeScriptParser.Var_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#let_decl}.
	 * @param ctx the parse tree
	 */
	void enterLet_decl(PiccodeScriptParser.Let_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#let_decl}.
	 * @param ctx the parse tree
	 */
	void exitLet_decl(PiccodeScriptParser.Let_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#tuple}.
	 * @param ctx the parse tree
	 */
	void enterTuple(PiccodeScriptParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#tuple}.
	 * @param ctx the parse tree
	 */
	void exitTuple(PiccodeScriptParser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(PiccodeScriptParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(PiccodeScriptParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(PiccodeScriptParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(PiccodeScriptParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void enterExpr_list(PiccodeScriptParser.Expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#expr_list}.
	 * @param ctx the parse tree
	 */
	void exitExpr_list(PiccodeScriptParser.Expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#call_expr_list}.
	 * @param ctx the parse tree
	 */
	void enterCall_expr_list(PiccodeScriptParser.Call_expr_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#call_expr_list}.
	 * @param ctx the parse tree
	 */
	void exitCall_expr_list(PiccodeScriptParser.Call_expr_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void enterCall_expr(PiccodeScriptParser.Call_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#call_expr}.
	 * @param ctx the parse tree
	 */
	void exitCall_expr(PiccodeScriptParser.Call_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#key_val_pair}.
	 * @param ctx the parse tree
	 */
	void enterKey_val_pair(PiccodeScriptParser.Key_val_pairContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#key_val_pair}.
	 * @param ctx the parse tree
	 */
	void exitKey_val_pair(PiccodeScriptParser.Key_val_pairContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#key_val_pairs}.
	 * @param ctx the parse tree
	 */
	void enterKey_val_pairs(PiccodeScriptParser.Key_val_pairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#key_val_pairs}.
	 * @param ctx the parse tree
	 */
	void exitKey_val_pairs(PiccodeScriptParser.Key_val_pairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PiccodeScriptParser#do_expr}.
	 * @param ctx the parse tree
	 */
	void enterDo_expr(PiccodeScriptParser.Do_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PiccodeScriptParser#do_expr}.
	 * @param ctx the parse tree
	 */
	void exitDo_expr(PiccodeScriptParser.Do_exprContext ctx);
}