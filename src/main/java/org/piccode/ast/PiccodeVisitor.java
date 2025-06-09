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
import org.piccode.rt.PiccodeException;

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
	public Ast visitVar_decl(Var_declContext var_decl) {
		var tok = var_decl.ID().getSymbol();
		var name = tok.getText();
		var expr = visitExpr(var_decl.expr());
		var result = new VarDecl(name, expr);
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}

	@Override
	public Ast visitStmts(StmtsContext ctx) {
		var stmts = new ArrayList<Ast>();
		for (var stmt : ctx.stmt()) {
			stmts.add(visitStmt(stmt));
		}
		var result = new StatementList(stmts);
		result.file = fileName;
		return result;
	}

	@Override
	public Ast visitClosure_decl(Closure_declContext ctx) {
		var args = visitArgs(ctx.arg_list());
		var body = visitExpr(ctx.expr());

		if (args.isEmpty()) {
			var result = new ClosureAst(null, body);
			result.file = fileName;
			return result;
		}

		var result = new ClosureAst(args, body);
		result.file = fileName;
		return result;
	}

	@Override
	public Ast visitFunc(FuncContext ctx) {
		var tok = ctx.ID().getSymbol();
		var name = tok.getText();
		var args = visitFuncArgs(ctx.func_args());
		var body = visitExpr(ctx.expr());

		if (args.isEmpty()) {
			var result = new FunctionAst(name, null, body);
			result.line = tok.getLine();
			result.column = tok.getCharPositionInLine();
			result.file = fileName;
			return result;
		}

		var result = new FunctionAst(name, args, body);
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}

	public List<Arg> visitFuncArgs(Func_argsContext ctx) {
		var args = new ArrayList<Arg>();
		if (ctx.arg_list() == null) {
			return args;
		}

		return visitArgs(ctx.arg_list());
	}

	public List<Arg> visitArgs(Arg_listContext ctx) {
		var args = new ArrayList<Arg>();
		for (var arg : ctx.arg()) {
			var _arg = (Arg) visitArg(arg);
			args.add(_arg);
		}
		return args;
	}

	@Override
	public Ast visitArg(ArgContext ctx) {
		var tok = ctx.ID().getSymbol();
		var name = ctx.ID().getText();
		if (ctx.ASSIGN() != null) {
			var value = visitLiteral_expr(ctx.literal_expr());
			var result = new Arg(name, value);
			result.line = tok.getLine();
			result.column = tok.getCharPositionInLine();
			result.file = fileName;
			return result;
		}

		var result = new Arg(name);
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}

	@Override
	public Ast visitLiteral_expr(Literal_exprContext ctx) {
		if (ctx.NUMBER() != null) {
			return visitNumber(ctx.NUMBER());
		}

		if (ctx.STRING() != null) {
			return visitString(ctx.STRING());
		}

		if (ctx.array() != null) {
			return visitArray(ctx.array());
		}
		if (ctx.tuple() != null) {
			return visitTuple(ctx.tuple());
		}
		if (ctx.object() != null) {
			return visitObject(ctx.object());
		}

		return null;
	}

	@Override
	public Ast visitStmt(StmtContext ctx) {
		if (ctx.expr_stmt() != null) {
			return visitExpr(ctx.expr_stmt().expr());
		}

		if (ctx.func() != null) {
			return visitFunc(ctx.func());
		}

		if (ctx.module() != null) {
			return visitModule(ctx.module());
		}

		if (ctx.import_module() != null) {
			return visitImport_module(ctx.import_module());
		}

		return null;
	}

	@Override
	public Ast visitImport_module(Import_moduleContext ctx) {
		var module_path = ctx.module_path();
		var tok = ctx.getStart();
		if (module_path != null) {
			var arr = new ArrayList<String>();
			for (var path: module_path.ID()) {
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

		throw new PiccodeException(fileName, tok.getLine(), tok.getCharPositionInLine(), "Invalid token in import");
	}

	public List<Ast> visitSymbollift(Symbol_liftContext ctx) {
		var nodes = new ArrayList<Ast>();
		for (var entry: ctx.symbol_entry()) {
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
		var name = ctx.ID().getText();
		if (ctx.module_stmts() != null) {
			var nodes = visitModuleStmts(ctx.module_stmts());
			return new ModuleAst(name, nodes);
		}
		return new ModuleAst(name, List.of());
	}

	public List<Ast> visitModuleStmts(Module_stmtsContext ctx) {
		var list = new ArrayList<Ast>();
		for (var stmt : ctx.module_stmt()) {
			list.add(visitModuleStmt(stmt));
		}
		return list;
	}

	private Ast visitModuleStmt(Module_stmtContext stmt) {
		if (stmt.func() != null) {
			return visitFunc(stmt.func());
		}

		if (stmt.var_decl() != null) {
			return visitVar_decl(stmt.var_decl());
		}

		if (stmt.module() != null) {
			return visitModule(stmt.module());
		}
		return null;
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

		if (expr.SHL() != null) {
			var tok = expr.SHL().getSymbol();
			var result = finalizeAstNode(visitBinOp("<<", expr), tok);
			return result;
		}

		if (expr.SHR() != null) {
			var tok = expr.SHR().getSymbol();
			var result = finalizeAstNode(visitBinOp(">>", expr), tok);
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

		if (expr.ID() != null) {
			return visitId(expr.ID());
		}

		if (expr.do_expr() != null) {
			return visitDo_expr(expr.do_expr());
		}

		if (expr.LPAREN() != null && expr.RPAREN() != null && expr.expr() == null) {
			return new UnitAst();
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
		throw new PiccodeException(fileName, start.getLine(), start.getCharPositionInLine(), "Invalid token encountered");
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
		var f = visitExpr(ctx.expr(2));

		return new IfExpression(cond, t, f);
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

		var start = ctx.getStart();
		throw new PiccodeException(fileName, start.getLine(), start.getCharPositionInLine(), "Invalid unary expression");
	}

	public Ast visitCall(ExprContext expr, Call_expr_listContext exprList) {
		var value = visitExpr(expr);
		if (exprList == null) {
			var result = new CallAst(value, List.of());
			result.file = fileName;
			return result;
		}

		var args = visitCallExprList(exprList);
		var result = new CallAst(value, args);
		result.file = fileName;
		return result;
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
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}

	private HashMap<String, Ast> visitKeyValuePairs(Key_val_pairsContext key_val_pairs) {
		var obj = new HashMap<String, Ast>();
		for (var kv : key_val_pairs.key_val_pair()) {
			obj.put(kv.ID().getText(), visitExpr(kv.expr()));
		}
		return obj;
	}

	@Override
	public Ast visitTuple(TupleContext ctx) {
		var tok = ctx.LPAREN().getSymbol();
		var exprs = visitExprlist(ctx.expr_list());
		if (exprs.size() == 1) {
			var result = exprs.getFirst();
			result.line = tok.getLine();
			result.column = tok.getCharPositionInLine();
			result.file = fileName;
			return result;
		}

		var result = new TupleAst(exprs);
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}

	@Override
	public Ast visitArray(ArrayContext ctx) {
		var tok = ctx.LBRACKET().getSymbol();
		if (ctx.expr_list() != null) {
			var result = new ArrayAst(visitExprlist(ctx.expr_list()));
			result.line = tok.getLine();
			result.column = tok.getCharPositionInLine();
			result.file = fileName;
			return result;
		}
		var result = new ArrayAst(List.of());
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
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
		var line = NUMBER.getSymbol().getLine();
		var col = NUMBER.getSymbol().getStartIndex();
		number.line = line;
		number.column = col;
		number.file = fileName;
		return number;
	}

	private Ast visitString(TerminalNode STRING) {
		var string = new StringAst(STRING.getText());
		var line = STRING.getSymbol().getLine();
		var col = STRING.getSymbol().getStartIndex();
		string.line = line;
		string.column = col;
		string.file = fileName;
		return string;
	}

	private Ast visitBinOp(String op, ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new BinOpAst(lhs, op, rhs);
	}

	private Ast visitDotOperation(ExprContext expr) {
		var lhs = visitExpr(expr.expr().getFirst());
		var rhs = visitExpr(expr.expr().getLast());
		return new DotOperationAst(lhs, rhs);
	}

	private Ast visitId(TerminalNode ID) {
		var tok = ID.getSymbol();
		String _id = ID.getText();
		var id = new IdentifierAst(_id);
		id.line = tok.getLine();
		id.column = tok.getCharPositionInLine();
		id.file = fileName;
		return id;
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

	private Ast finalizeAstNode(Ast result, Token tok) {
		result.line = tok.getLine();
		result.column = tok.getCharPositionInLine();
		result.file = fileName;
		return result;
	}
}
