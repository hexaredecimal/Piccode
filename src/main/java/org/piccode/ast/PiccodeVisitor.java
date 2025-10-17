package org.piccode.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.tree.ExpandVetoException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.piccode.antlr4.PiccodeScriptBaseVisitor;
import org.piccode.antlr4.PiccodeScriptParser.*;
import org.piccode.errors.PiccodeException;
import org.piccode.ast.types.*;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeVisitor extends PiccodeScriptBaseVisitor<Ast> {

	public String fileName;

	public PiccodeVisitor(String file) {
		fileName = file;
	}


	@Override
	public Ast visitTypeDecl(TypeDeclContext ctx) {
		var token = ctx.getStart();
		var typeLValue = visitTypeLVal(ctx.typeLVal());
		var typeRValue = visitTypeRVal(ctx.typeRVal());

		if (typeRValue instanceof RecordTypeDeclaration record) {
			record.typeLValue = typeLValue;
			return record;
		}
		
		return finalizeAstNode(new TypeDeclaration(typeLValue, typeRValue), token);
	}

	@Override
	public Ast visitTypeLVal(TypeLValContext ctx) {
		var idToken = ctx.ID().getSymbol();
		var typeName = ctx.ID().getText();

		if (ctx.genericBody() != null) {
			var genericTypes = new ArrayList<Ast>();
			for (var param : ctx.genericBody().typeLVal()) {
				genericTypes.add(visitTypeLVal(param));
			}
			return finalizeAstNode(new TypeLValue(typeName, genericTypes), idToken);
		}
		return finalizeAstNode(new IdentifierAst(typeName), idToken);
	}

	@Override
	public Ast visitTypeRVal(TypeRValContext ctx) {
		if (ctx.usableType() != null) {
			return visitUsableType(ctx.usableType());
		}

		if (ctx.record() != null) {
			return visitRecord(ctx.record());
		}

		var tok = ctx.getStart();
		return finalizeAstNode(new UnitTypeDeclaration(), tok);
	}
	

	@Override
	public Ast visitRecord(RecordContext ctx) {
		var recordFields = new ArrayList<StructureField>();
		for (var field : ctx.recordField()) {
			var recordField = (StructureField) visitRecordField(field);
			recordFields.add(recordField);
		}

		var start = ctx.getStart();
		return finalizeAstNode(new RecordTypeDeclaration(recordFields), start);
	}

	@Override
	public Ast visitRecordField(RecordFieldContext ctx) {
		var idToken = ctx.ID().getSymbol();
		var idText = ctx.ID().getText();

		var fieldType = visitUsableType(ctx.usableType());
		return finalizeAstNode(new StructureField(idText, fieldType), idToken);
	}




	@Override
	public Ast visitUsableType(UsableTypeContext ctx) {
		if (ctx.typeLVal() != null) {
			return visitTypeLVal(ctx.typeLVal());
		} 

		var tok = ctx.getStart();
		throw new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "TODO: Finish");
	}



	@Override
	public Ast visitLet_decl(Let_declContext ctx) {
		var tok = ctx.getStart();
		List<Ast> vars = new ArrayList<>();
		for (var decl : ctx.var_decl()) {
			vars.add(visitVar_decl(decl));
		}
		var expr = visitExpr(ctx.expr());
		var result = new LetInExprAst(vars, expr);
		result.file = fileName;
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitVar_decl(Var_declContext var_decl) {
		var tok = var_decl.ID().getSymbol();
		var name = tok.getText();

		if (var_decl.DASSIGN() != null) {
			var expr = visitExpr(var_decl.expr());
			var result = new VarDecl(name, expr);
			result.file = fileName;
			return finalizeAstNode(result, tok);
		}

		var result = new IdentifierAst(name);
		result.file = fileName;
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitStmts(StmtsContext ctx) {
		var tok = ctx.getStart();
		var stmts = new ArrayList<Ast>();
		for (var stmt : ctx.stmt()) {
			stmts.add(visitStmt(stmt));
		}
		var result = new StatementList(stmts);
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitClosure_decl(Closure_declContext ctx) {
		var tok = ctx.getStart();
		var args = visitArgs(ctx.arg_list());
		var body = visitExpr(ctx.expr());

		if (args.isEmpty()) {
			var result = new ClosureAst(null, body);
			return finalizeAstNode(result, tok);
		}

		var result = new ClosureAst(args, body);
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitFunc(FuncContext ctx) {
		var tok = ctx.getStart();
		var args = visitFuncArgs(ctx.func_args());
		var body = visitExpr(ctx.expr());

		if (args.isEmpty()) {
			var result = new FunctionAst(null, List.of(), body);
			return finalizeAstNode(result, tok);
		}

		var result = new FunctionAst(null, args, body);
		return finalizeAstNode(result, tok);
	}

	public List<Ast> visitFuncArgs(Func_argsContext ctx) {
		var args = new ArrayList<Ast>();
		if (ctx.arg_list() == null) {
			return args;
		}

		return visitArgs(ctx.arg_list());
	}

	public List<Ast> visitArgs(Arg_listContext ctx) {
		var args = new ArrayList<Ast>();
		for (var arg : ctx.arg()) {
			var _arg = visitArg(arg);
			args.add(_arg);
		}
		return args;
	}

	@Override
	public Ast visitArg(ArgContext ctx) {
		var tok = ctx.getStart();
		if (ctx.ID() == null && ctx.expr() != null) {
			return finalizeAstNode(visitExpr(ctx.expr()), tok);
		}

		var name = ctx.ID().getText();
		if (ctx.ASSIGN() != null) {
			var value = visitExpr(ctx.expr());
			var result = new Arg(name, value);
			result.export = ctx.USE() != null;
			return finalizeAstNode(result, tok);
		}

		var result = new Arg(name);
		result.export = ctx.USE() != null;
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitStmt(StmtContext ctx) {
		if (ctx.expr_stmt() != null) {
			return visitExpr(ctx.expr_stmt().expr());
		}

		if (ctx.typedFunctionDecl() != null) {
			return visitTypedFunctionDecl(ctx.typedFunctionDecl());
		}

		/*
		if (ctx.declaration() != null) {
			return visitDeclaration(ctx.declaration());
		}

		if (ctx.import_module() != null) {
			return visitImport_module(ctx.import_module());
		}

*/

		if (ctx.typeDecl() != null) {
			return visitTypeDecl(ctx.typeDecl());
		}

		var tok = ctx.getStart();
		var err = new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Invalid declaration");
		err.frame = null;
		throw err;
	}




	@Override
	public Ast visitTypedFunctionDecl(TypedFunctionDeclContext ctx) {
		var tok = ctx.getStart();
		var id = ctx.ID().getText();
		var result = (FunctionTypeDeclAst) visitFunctionTypeDecl(ctx.functionTypeDecl());
		result.name = id;

		var funcs = new ArrayList<FunctionAst>();
		for (var func: ctx.funcDef()) {
			var funcTok = func.getStart();
			var funcName = func.ID().getText();
			var function = (FunctionAst) visitFunc(func.func());
			function.name = funcName;
			funcs.add((FunctionAst) finalizeAstNode(function, funcTok));
		}

		var typedFunc = new TypedFunction(result, funcs);
		return finalizeAstNode(typedFunc, tok);
	}
	
	@Override
	public Ast visitDeclaration(DeclarationContext ctx) {
		var annotations = new ArrayList<String>();
		if (ctx.annotations() != null) {
			for (var id : ctx.annotations().ID()) {
				annotations.add(id.getText());
			}
		}

		var id = ctx.ID().getText();
		if (ctx.func() != null) {
			var func = (FunctionAst) visitFunc(ctx.func());
			func.name = id;
			func.annotations = annotations;
			return func;
		}

		if (ctx.module() != null) {
			var mod = (ModuleAst) visitModule(ctx.module());
			mod.name = id;
			return mod;
		}

		if (ctx.import_module() != null) {
			var tok = ctx.import_module().getStart();
			var mod = (ImportAst) visitImport_module(ctx.import_module());
			var result = new ImportModuleCreateAst(id, mod);
			return finalizeAstNode(result, tok);
		}

		if (ctx.functionTypeDecl() != null) {
			var result = (FunctionTypeDeclAst) visitFunctionTypeDecl(ctx.functionTypeDecl());
			result.name = id;
			return result;
		}

		var tok = ctx.getStart();
		throw new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Invalid declaration node");
	}

	@Override
	public Ast visitFunctionTypeDecl(FunctionTypeDeclContext ctx) {
		var types = new ArrayList<Ast>();
		for (var type: ctx.usableType()) {
			types.add(visitUsableType(type));
		}

		var tok = ctx.getStart();
		var retType = types.removeLast();
		var result = new FunctionTypeDeclAst(null, types, retType);
		return finalizeAstNode(result, tok);
	}
	
	@Override
	public Ast visitImport_module(Import_moduleContext ctx) {
		var module_path = ctx.module_path();
		var tok = ctx.getStart();
		if (module_path != null) {
			var arr = new ArrayList<String>();
			for (var path : module_path.ID()) {
				tok = path.getSymbol();
				arr.add(path.getText());
			}

			var path = String.join("/", arr);

			if (module_path.symbol_lift() != null) {
				tok = module_path.symbol_lift().start;
				var nodes = visitSymbollift(module_path.symbol_lift());
				return finalizeAstNode(new ImportAst(path, nodes), tok);
			}

			return finalizeAstNode(new ImportAst(path), tok);
		}

		var err = new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Invalid token in import");
		err.frame = null;
		throw err;
	}

	public List<Ast> visitSymbollift(Symbol_liftContext ctx) {
		var nodes = new ArrayList<Ast>();
		for (var entry : ctx.symbol_entry()) {
			if (entry.symbol_lift() != null) {
				var _nodes = visitSymbollift(entry.symbol_lift());
				var symbol = entry.ID().getSymbol();
				var result = new ImportLift(symbol.getText(), _nodes);
				nodes.add(finalizeAstNode(result, symbol));
			} else {
				var symbol = entry.ID().getSymbol();
				var result = new ImportId(symbol.getText());
				nodes.add(finalizeAstNode(result, symbol));
			}
		}
		return nodes;
	}

	@Override
	public Ast visitModule(ModuleContext ctx) {
		if (ctx.module_stmts() != null) {
			var nodes = visitModuleStmts(ctx.module_stmts());
			return new ModuleAst(null, nodes);
		}
		return new ModuleAst(null, List.of());
	}

	public List<Ast> visitModuleStmts(Module_stmtsContext ctx) {
		var list = new ArrayList<Ast>();
		for (var stmt : ctx.module_stmt()) {
			list.add(visitModuleStmt(stmt));
		}
		return list;
	}

	private Ast visitModuleStmt(Module_stmtContext stmt) {
		if (stmt.var_decl() != null) {
			return visitVar_decl(stmt.var_decl());
		}

		if (stmt.declaration() != null) {
			return visitDeclaration(stmt.declaration());
		}

		var tok = stmt.getStart();

		var err = new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Invalid statement");
		err.frame = null;
		throw err;
	}

	@Override
	public Ast visitWhen_expr(When_exprContext ctx) {
		var expr = visitExpr(ctx.expr());
		var cases = visitWhenCases(ctx.when_cases());
		if (ctx.else_case() != null) {
			var elze = visitExpr(ctx.else_case().expr());
			return new WhenAst(expr, cases, elze);
		}
		return new WhenAst(expr, cases, null);
	}

	private List<WhenCase> visitWhenCases(When_casesContext whens) {
		var cases = new ArrayList<WhenCase>();
		for (var _case : whens.when_case()) {
			var match = visitExprlist(_case.expr_list());
			var value = visitExpr(_case.expr());
			cases.add(new WhenCase(match, value));
		}
		return cases;
	}

	@Override
	public Ast visitExpr(ExprContext expr) {
		if (expr.var_decl() != null) {
			return visitVar_decl(expr.var_decl());
		}

		if (expr.let_decl() != null) {
			return visitLet_decl(expr.let_decl());
		}

		if (expr.closure_decl() != null) {
			return visitClosure_decl(expr.closure_decl());
		}

		if (expr.when_expr() != null) {
			return visitWhen_expr(expr.when_expr());
		}

		if (expr.NUMBER() != null) {
			return visitNumber(expr.NUMBER());
		}

		if (expr.STRING() != null) {
			return visitString(expr.STRING());
		}

		if (expr.CATCH_TOK() != null) {
			var tok = expr.CATCH_TOK().getSymbol();
			var result = finalizeAstNode(visitCatch(expr), tok);
			return result;
		}

		if (expr.ADD() != null) {
			var tok = expr.ADD().getSymbol();
			var result = finalizeAstNode(visitBinOp("+", expr), tok);

			return result;
		}

		if (expr.SUB() != null) {
			var tok = expr.SUB().getSymbol();
			var result = finalizeAstNode(visitBinOp("-", expr), tok);
			return result;
		}

		if (expr.MUL() != null) {
			var tok = expr.MUL().getSymbol();
			var result = finalizeAstNode(visitBinOp("*", expr), tok);
			return result;
		}

		if (expr.DIV() != null) {
			var tok = expr.DIV().getSymbol();
			var result = finalizeAstNode(visitBinOp("/", expr), tok);
			return result;
		}

		if (expr.MOD() != null) {
			var tok = expr.MOD().getSymbol();
			var result = finalizeAstNode(visitBinOp("%", expr), tok);
			return result;
		}

		if (expr.GT() != null) {
			var tok = expr.GT().getSymbol();
			var result = finalizeAstNode(visitBinOp(">", expr), tok);
			return result;
		}

		if (expr.GE() != null) {
			var tok = expr.GE().getSymbol();
			var result = finalizeAstNode(visitBinOp(">=", expr), tok);
			return result;
		}

		if (expr.LT() != null) {
			var tok = expr.LT().getSymbol();
			var result = finalizeAstNode(visitBinOp("<", expr), tok);
			return result;
		}

		if (expr.LE() != null) {
			var tok = expr.LE().getSymbol();
			var result = finalizeAstNode(visitBinOp("<=", expr), tok);
			return result;
		}

		if (expr.EQ() != null) {
			var tok = expr.EQ().getSymbol();
			var result = finalizeAstNode(visitBinOp("==", expr), tok);
			return result;
		}

		if (expr.NE() != null) {
			var tok = expr.NE().getSymbol();
			var result = finalizeAstNode(visitBinOp("!=", expr), tok);
			return result;
		}

		if (expr.AND() != null) {
			var tok = expr.AND().getSymbol();
			var result = finalizeAstNode(visitBinOp("&&", expr), tok);
			return result;
		}

		if (expr.OR() != null) {
			var tok = expr.OR().getSymbol();
			var result = finalizeAstNode(visitBinOp("||", expr), tok);
			return result;
		}

		if (expr.BAND() != null) {
			var tok = expr.BAND().getSymbol();
			var result = finalizeAstNode(visitBinOp("&", expr), tok);
			return result;
		}

		if (expr.BOR() != null) {
			var tok = expr.BOR().getSymbol();
			var result = finalizeAstNode(visitBinOp("|", expr), tok);
			return result;
		}

		if (expr.unary() != null) {
			return visitUnaryExpr(expr.unary());
		}

		if (expr.DOT() != null) {
			var tok = expr.DOT().getSymbol();
			var result = finalizeAstNode(visitDotOperation(expr), tok);
			return result;
		}

		if (expr.CC() != null) {
			var tok = expr.CC().getSymbol();
			var result = finalizeAstNode(visitCCOperation(expr), tok);
			return result;
		}

		if (expr.COLON() != null) {
			var tok = expr.COLON().getSymbol();
			var result = finalizeAstNode(visitConsOperation(expr), tok);
			return result;
		}

		if (expr.if_expr() != null) {
			return visitIf_expr(expr.if_expr());
		}

		if (expr.PIPE() != null) {
			var tok = expr.PIPE().getSymbol();
			var result = finalizeAstNode(visitPipeOp(expr), tok);
			return result;
		}

		if (expr.array() != null) {
			return visitArray(expr.array());
		}

		if (expr.tuple() != null) {
			return visitTuple(expr.tuple());
		}

		if (expr.object() != null) {
			return visitObject(expr.object());
		}

		if (expr.do_expr() != null) {
			return visitDo_expr(expr.do_expr());
		}

		if (expr.LPAREN() != null && expr.RPAREN() != null && expr.expr() == null) {
			return new UnitAst();
		}

		if (expr.LBRACKET() != null && expr.RBRACKET() != null && expr.expr() != null && !expr.expr().isEmpty() && expr.expr().size() == 2 && expr.call_expr_list() == null) {
			var tok = expr.getStart();
			var lhs = visitExpr(expr.expr().getFirst());
			var rhs = visitExpr(expr.expr().getLast());
			var dot = new DotOperationAst(lhs, rhs);
			return finalizeAstNode(dot, tok);
		}

		if (expr.LPAREN() != null && expr.RPAREN() != null && expr.expr() != null && !expr.expr().isEmpty() && expr.expr().size() == 1 && expr.call_expr_list() == null) {
			var lp = expr.getChild(0).getText();
			var rp = expr.getChild(expr.getChildCount() - 1).getText();

			if (lp.equals("(") && rp.equals(")")) {
				return visitExpr(expr.expr().getFirst());
			}
			return visitCall(expr.expr().getFirst(), null);
		}

		if (!expr.expr().isEmpty() && expr.call_expr_list() == null) {
			return visitCall(expr, expr.call_expr_list());
		}

		if (!expr.expr().isEmpty() && expr.call_expr_list() != null) {
			return visitCall(expr.expr().getFirst(), expr.call_expr_list());
		}

		
		//TODO: Remove this dirty hack and actually figure out the bug
		if (expr.getText().equals("()")) {
			return new UnitAst();
		}

		var start = expr.getStart();
		var err = new PiccodeException(fileName, start.getLine(), start.getCharPositionInLine(), "Invalid token encountered");
		err.frame = null;
		throw err;
	}

	@Override
	public Ast visitDo_expr(Do_exprContext ctx) {
		var nodes = new ArrayList<Ast>();
		var exprs = ctx.expr();

		if (exprs.isEmpty()) {
			return new UnitAst();
		}

		for (var e : exprs) {
			var expr = visitExpr(e);
			nodes.add(expr);
		}

		return new DoExprAst(nodes);
	}

	@Override
	public Ast visitIf_expr(If_exprContext ctx) {
		var cond = visitExpr(ctx.expr(0));
		var t = visitExpr(ctx.expr(1));

		if (ctx.ELSE() != null) {
			var f = visitExpr(ctx.expr(2));
			return new IfExpression(cond, t, f);
		}
		return new IfExpression(cond, t, new UnitAst());
	}

	private Ast visitUnaryExpr(UnaryContext ctx) {
		if (ctx.EXCLAIM() != null) {
			var tok = ctx.EXCLAIM().getSymbol();
			var result = finalizeAstNode(
							new UnaryAst("!", visitExpr(ctx.expr())),
							tok);
			return result;
		}

		if (ctx.BAND() != null) {
			var tok = ctx.BAND().getSymbol();
			var result = finalizeAstNode(
							new UnaryAst("&", visitExpr(ctx.expr())),
							tok);
			return result;
		}

		if (ctx.MUL() != null) {
			var tok = ctx.MUL().getSymbol();
			var result = finalizeAstNode(
							new UnaryAst("*", visitExpr(ctx.expr())),
							tok);
			return result;
		}
		if (ctx.SUB() != null) {
			var tok = ctx.SUB().getSymbol();
			var result = finalizeAstNode(
							new UnaryAst("-", visitExpr(ctx.expr())),
							tok);
			return result;
		}

		if (ctx.TILDE() != null) {
			var tok = ctx.TILDE().getSymbol();
			var result = finalizeAstNode(
							new UnaryAst("~", visitExpr(ctx.expr())),
							tok);
			return result;
		}

		if (ctx.RETURN_TOK() != null) {
			var tok = ctx.RETURN_TOK().getSymbol();
			var result = finalizeAstNode(
							new ReturnAst(visitExpr(ctx.expr())),
							tok);
			return result;
		}

		var start = ctx.getStart();
		var err = new PiccodeException(fileName, start.getLine(), start.getCharPositionInLine(), "Invalid unary expression");
		err.frame = null;
		throw err;
	}

	public Ast visitCall(ExprContext expr, Call_expr_listContext exprList) {
		var value = visitExpr(expr);
		if (exprList == null) {
			var tok = expr.getStart();
			var result = new CallAst(value, new ArrayList<Ast>());
			return finalizeAstNode(result, tok);
		}

		var tok = exprList.getStart();
		var args = visitCallExprList(exprList);
		var result = new CallAst(value, args);
		return finalizeAstNode(result, tok);
	}

	private List<Ast> visitCallExprList(Call_expr_listContext ctx) {
		var nodes = new ArrayList<Ast>();
		for (var call_expr : ctx.call_expr()) {
			nodes.add(visitCallExpr(call_expr));
		}
		return nodes;
	}

	public Ast visitCallExpr(Call_exprContext ctx) {
		if (ctx.ASSIGN() != null) {
			var tok = ctx.ID().getSymbol();
			var id = ctx.ID().getText();
			var value = visitExpr(ctx.expr());
			var result = finalizeAstNode(
							new NamedCallArg(id, value),
							tok);
			return result;
		}

		if (ctx.ASSIGN() == null && ctx.ID() != null) {
			var tok = ctx.ID().getSymbol();
			var result = finalizeAstNode(
							new IdentifierAst(ctx.ID().getText()),
							tok);
			return result;
		}

		return visitExpr(ctx.expr());
	}

	@Override
	public Ast visitObject(ObjectContext ctx) {
		var tok = ctx.LBRACE().getSymbol();
		var kvs = visitKeyValuePairs(ctx.key_val_pairs());
		var result = new ObjectAst(kvs);
		return finalizeAstNode(result, tok);
	}

	private HashMap<String, Ast> visitKeyValuePairs(Key_val_pairsContext key_val_pairs) {
		var obj = new HashMap<String, Ast>();
		for (var kv : key_val_pairs.key_val_pair()) {
			var id = kv.ID();
			if (id == null) {
				var tok = key_val_pairs.getStart();
				throw new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Missing a key in object literal");
			}

			var expr = kv.expr();

			if (expr == null) {
				var tok = key_val_pairs.getStart();
				throw new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Missing expression in object literal");
			}

			obj.put(id.getText(), visitExpr(expr));
		}
		return obj;
	}

	@Override
	public Ast visitTuple(TupleContext ctx) {
		var tok = ctx.LPAREN().getSymbol();
		var exprs = visitExprlist(ctx.expr_list());
		if (exprs.size() == 1) {
			var result = exprs.getFirst();
			return finalizeAstNode(result, tok);
		}

		var result = new TupleAst(exprs);
		return finalizeAstNode(result, tok);
	}

	@Override
	public Ast visitArray(ArrayContext ctx) {
		var tok = ctx.LBRACKET().getSymbol();
		if (ctx.expr_list() != null) {
			var result = new ArrayAst(visitExprlist(ctx.expr_list()));
			return finalizeAstNode(result, tok);
		}
		var result = new ArrayAst(List.of());
		return finalizeAstNode(result, tok);
	}

	private List<Ast> visitExprlist(Expr_listContext ctx) {
		return ctx
						.expr()
						.stream()
						.map(this::visitExpr)
						.toList();
	}

	private Ast visitNumber(TerminalNode NUMBER) {
		var number = new NumberAst(NUMBER.getText());
		var tok = NUMBER.getSymbol();
		return finalizeAstNode(number, tok);
	}

	private Ast visitString(TerminalNode STRING) {
		var string = new StringAst(STRING.getText());
		var tok = STRING.getSymbol();
		return finalizeAstNode(string, tok);
	}

	private Ast visitBinOp(String op, ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new BinOpAst(lhs, op, rhs);
	}

	private Ast visitDotOperation(ExprContext expr) {
		var tok = expr.getStart();
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new DotOperationAst(lhs, rhs);
	}

	private Ast visitCCOperation(ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new CCOperationAst(lhs, rhs);
	}

	private Ast visitId(TerminalNode ID) {
		var tok = ID.getSymbol();
		String _id = ID.getText();
		var id = new IdentifierAst(_id);
		return finalizeAstNode(id, tok);
	}

	private Ast visitPipeOp(ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new PipeAst(lhs, rhs);
	}

	private Ast visitConsOperation(ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new ListConstAst(lhs, rhs);
	}

	private Ast visitCatch(ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new CatchAst(lhs, rhs);
	}

	private Ast finalizeAstNode(Ast result, Token tok) {
		result.file = fileName;
		return Ast.finalizeNode(result, tok);
	}

}
