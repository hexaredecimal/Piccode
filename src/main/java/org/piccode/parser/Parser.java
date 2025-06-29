package org.piccode.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.piccode.ast.*;
import org.piccode.rt.PiccodeException;
import org.piccode.tokens.Span;
import org.piccode.tokens.Token;
import org.piccode.tokens.TokenKind;

/**
 *
 * @author hexaredecimal
 */
public class Parser {

	private List<Token> tokens;
	public static List<Ast> typeAliases = new ArrayList<>();

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public List<Ast> parse(boolean report) {
		List<Ast> nodes = new ArrayList<>();
		var top = peek(0);
		var err_loops = 0;
		while (!tokens.isEmpty() && top.kind != TokenKind.EOF) {
			top = peek(0);
			if (report && err_loops > 0) {
				//Errors.reportSyntaxError(top, "Invalid token encountered: " + peek(0).text);
			} else if (err_loops > 0) { 
				break;
			}

			if (match(TokenKind.ID)) {
				var id = next();
				if (match(TokenKind.DBL_COLON)) {
					next();
					top = peek(0);
					if (match(TokenKind.KEYWORD) && top.text.equals("module")) {
						var mod = (ModuleAst) module();
						mod.name = id.text;
						nodes.add(finalizeNode(mod, id.span));
					}

					if (match(TokenKind.LPAREN)) {
						var node = (FunctionAst) function();
						node.name = id.text;
						nodes.add(finalizeNode(node, id.span));
					}
					
				}
			}

			if (match(TokenKind.EOF)) {
				break;
			}
			err_loops++;
		}

		// nodes.forEach(System.out::println);
		return nodes;
	}


	private Ast module() {
		next();
		expect(TokenKind.LBRACE, "Expected an `{` after the name of the module");
		var _nodes = parse(false);
		expect(TokenKind.RBRACE, "Expected an `}` at the end of the module");
		return new ModuleAst(null, _nodes);
	}


	private Ast annotatedNode() {
		next();
		var top = expect(TokenKind.LBRACKET, "Expected `[` after the `#`");
		List<String> annotations = new ArrayList<>();
		while (!match(TokenKind.RBRACKET)) {
			if (match(TokenKind.ID)) {
				var t = peek(0);
				annotations.add(t.text);
				next();

				if (match(TokenKind.COMMA)) {
					next();
				}
			}
		}
		expect(TokenKind.RBRACKET, "Expected `]` after the list of annotations");

		var t = peek(0);
		if (match(TokenKind.EOF)) {
			// Errors.reportSyntaxError(t, "Expected fn after the annotation but found EOF instead");
		}
		// TODO
		return null;
	}


	private Ast importModule() {
		next();
		List<String> path = new ArrayList<>();

		var id = expect(TokenKind.ID, "expected identifier for module name");
		path.add(id.text);

		while (match(TokenKind.DOT)) {
			next();
			id = expect(TokenKind.ID, "expected identifier for module name");
			path.add(id.text);
		}

		var import_path = "./" + String.join("/", path.toArray(String[]::new));

		var node = new ImportAst(import_path);
		var span = id.span;
		node.file = span.filepath;
		node.file = span.filepath;
		node.file = span.filepath;
		return finalizeNode(node, span);
	}


	private Ast finalizeNode(Ast node, Span span) {
		node.file = span.filepath;
		node.line = span.line;
		node.column= span.column;
		return node;
	}



	private Ast function() {
		var top = peek(0);
		var args = collectArgs();
		expect(TokenKind.ASSIGN, "Expected `=` after argument list");
		var expr = expression();
		Ast func = new FunctionAst(null, args, expr);
		return finalizeNode(func, top.span);
	}


	private List<Arg> collectArgs() {
		expect(TokenKind.LPAREN, "Expected `(`");
		List<Arg> nodes = new ArrayList<>();

		while (!match(TokenKind.RPAREN)) {
			var id = expect(TokenKind.ID, "Expected an identifier for the argument name");
			if (match(TokenKind.ASSIGN)) {
				next();
				var expr = expression();
				var arg = new Arg(id.text, expr);
				arg.file = id.span.filepath;
				arg.line = id.span.line;
				arg.column = id.span.column;
				nodes.add(arg);
			} else {
				var arg = new Arg(id.text);
				arg.file = id.span.filepath;
				arg.line = id.span.line;
				arg.column = id.span.column;
				nodes.add(arg);
			}

			if (!match(TokenKind.RPAREN)) {
				expect(TokenKind.COMMA, "Expected `,` to separate arguments");
			}
		}
		
		expect(TokenKind.RPAREN, "Expected `)`");
		return nodes;
	}


	private Ast expression() {
		var top = peek(0);
		var result = ternary();
		return result;
	}

	private Ast matchExpression() {
		next();
		var expr = expression();
		expect(TokenKind.LPAREN, "Expected `{` after match expression in when");

		var cases = new ArrayList<WhenCase>();
		while (!match(TokenKind.RPAREN)) {
			var top = peek(0);
			if (!match(TokenKind.KEYWORD) || (match(TokenKind.KEYWORD) && !top.text.equals("is"))) {
				if (match(TokenKind.KEYWORD) && top.text.equals("else")) {
					break;
				}
				expect(TokenKind.KEYWORD, "Expected the `is` keyword");
			}
			
			var exp = expression();
			expect(TokenKind.ARROW, "Expected `->` after a match case");
			var body = expression();
			var nodes = new ArrayList<Ast>();
			nodes.add(exp);
			var case_ = new WhenCase(nodes, body);
			cases.add(case_);
		}
		
		var when = new WhenAst(expr, cases, null);
		var top = peek(0);
		if (match(TokenKind.KEYWORD) && top.text.equals("else")) {
			expect(TokenKind.ARROW, "Expected `->` after a match case");
			var body = expression();
			when.else_case = body;
		}
		
		expect(TokenKind.RPAREN, "Expected `}` after when expression");
		return when;
	}



	private Ast blockOrObject() {
		if (match(TokenKind.LBRACE) && peek(1).kind == TokenKind.ID && peek(2).kind == TokenKind.COLON) {
			return object();
		}

		var top = peek(0);
		expect(TokenKind.LBRACE, "Expected `{` at start of the block");
		var nodes = new ArrayList<Ast>();
		while (!match(TokenKind.RBRACE)) {
			nodes.add(expression());
		}
		expect(TokenKind.RBRACE, "Expected `}` at start of the block");
		Ast node = new DoExprAst(nodes);
		return finalizeNode(node, top.span);
	}


	private Ast object() {
		var top = peek(0);
		expect(TokenKind.LBRACE, "Expected `{` at start of the block");
		var obj = new HashMap<String, Ast>();
		while (!match(TokenKind.RBRACE)) {
			var key = expect(TokenKind.ID, "Expected an identifier for the key of the object");
			expect(TokenKind.COLON, "Expected `:`");
			var expr = expression();
			obj.put(key.text, expr);
		}
		expect(TokenKind.RBRACE, "Expected `}` at start of the block");
		var object = new ObjectAst(obj);
		return finalizeNode(object, top.span);
	}



	private Ast groupOrTuple() {
		var t = next();

		if (match(TokenKind.RPAREN)) {
			next();
			return finalizeNode(new UnitAst(), t.span);
		}

		var node = expression();
		List<Ast> nodes = new ArrayList<>();
		t = peek(0);
		if (match(TokenKind.COMMA)) {
			nodes.add(node);
			while (match(TokenKind.COMMA)) {
				next();
				node = expression();
				nodes.add(node);
			}
			expect(TokenKind.RPAREN, "Expected `)` after expression in tuple expression");
			return finalizeNode(new TupleAst(nodes), t.span);
		}
		expect(TokenKind.RPAREN, "Expected `)` after expression in group expression, `" + peek(0).text + "` found instead");
		return node;
	}

	private Ast ternary() {
		Ast term = booleanOperation();
		return term;
	}

	private Ast booleanOperation() {
		Ast result = additive();

		while (true) {
			var op = peek(0);
			if (match(TokenKind.BOOLEAN_OPERATOR)) {
				next();
				result = new BinOpAst(result, op.text, expression());
				result = finalizeNode(result, op.span);
				continue;
			}
			break;
		}

		return result;
	}

	private Ast additive() {
		Ast result = multiplicative();

		while (true) {
			var op = peek(0);
			if (match(TokenKind.ADDITIVE_OPERATOR) || match(TokenKind.STR_CONCAT_OPERATOR)) {
				next();
				result = new BinOpAst(result, op.text, multiplicative());
				continue;
			} else if (match(TokenKind.PIPE)) {
				result = processPipe(result);
				continue;
			}
			break;
		}

		return result;
	}

	private Ast processPipe(Ast lhs) {
		next(); // Consume |>

		// For multi-line pipes, we need to handle newlines
		var rhs = callOrTerm();

		if (rhs instanceof IdentifierAst id) {
			// Case 1: RHS is an identifier like "sbString"
			List<Ast> args = new ArrayList<>();
			args.add(lhs);
			return new CallAst(id, args);
		} else if (rhs instanceof CCOperationAst ma) {
			// Case 2: RHS is a module access like "io::println"
			List<Ast> args = new ArrayList<>();
			args.add(lhs);
			return new CallAst(ma, args);
		} else if (rhs instanceof CallAst call) {
			// Case 3: RHS is a function call like "append "Hello""
			call.nodes.addFirst(lhs);
			return call;
		} else if (rhs instanceof ClosureAst closure) {
			// Case 4: RHS is a closure like {x -> ...}
			// Create a call with the closure as the function and lhs as the argument
			List<Ast> args = new ArrayList<>();
			args.add(lhs);
			return new CallAst(closure, args);
		} else {
			// Error case - report it
			var top = peek(0);
			var span = top.span;
			throw new PiccodeException(span.filepath, span.line, span.column, "Pipe operator requires identifier, function call, or closure on right side");
		}
	}

	private Ast callOrTerm() {
		// Parse a function call or a simple term
		// This is used by the pipe operator to get the right-hand side
		Token functionToken = peek(0);

		if (match(TokenKind.ID)) {
			next(); // Consume the identifier

			// Check for module access (::)
			if (match(TokenKind.DBL_COLON)) {
				next(); // Consume ::
				
				// Expect an identifier after ::
				var moduleTarget = expect(TokenKind.ID, "Expected identifier after '::'");

				Ast lhs = new IdentifierAst(functionToken.text);
				lhs = finalizeNode(lhs, functionToken.span);

				Ast rhs = new IdentifierAst(moduleTarget.text);
				rhs = finalizeNode(rhs, moduleTarget.span);
				
				var moduleExpr = new CCOperationAst(lhs, rhs);
				
				// Check if module access is followed by parentheses for a function call
				if (match(TokenKind.LPAREN)) {
					next(); // Consume the opening parenthesis
					List<Ast> args = new ArrayList<>();
					
					// Parse comma-separated arguments inside parentheses
					if (!match(TokenKind.RPAREN)) {
						args.add(expression());
						
						while (match(TokenKind.COMMA)) {
							next(); // Consume the comma
							args.add(expression());
						}
					}
					
					// Expect closing parenthesis
					expect(TokenKind.RPAREN, "Expected ')' after function arguments");
					
					// Create a function call with the parsed arguments
					return new CallAst(moduleExpr, args);
				}
				
				// Just a module access with no args
				return moduleExpr;
			}
			
			// Check if identifier is followed by parentheses for a function call
			if (match(TokenKind.LPAREN)) {
				next(); // Consume the opening parenthesis
				List<Ast> args = new ArrayList<>();
				
				// Parse comma-separated arguments inside parentheses
				if (!match(TokenKind.RPAREN)) {
					args.add(expression());
					
					while (match(TokenKind.COMMA)) {
						next(); // Consume the comma
						args.add(expression());
					}
				}
				
				// Expect closing parenthesis
				expect(TokenKind.RPAREN, "Expected ')' after function arguments");
				
				Ast node = new IdentifierAst(functionToken.text);
				node = finalizeNode(node, functionToken.span);
				// Create a function call with the parsed arguments
				return new CallAst(node, args);
			}
			
			// Just an identifier with no args
			Ast node = new IdentifierAst(functionToken.text);
			node = finalizeNode(node, functionToken.span);
			return node;
		}
		
		// Check for block/closure
		if (match(TokenKind.LBRACE)) {
			return blockOrObject();
		}
		
		// Not an identifier or closure, just return a term
		return parseTerm();
	}

	private Ast multiplicative() {
		Ast result = unary();

		while (true) {
			var op = peek(0);
			if (match(TokenKind.MULTPLICATIVE_OPERATOR)) {
				next();
				var rhs = unary();
				result = new BinOpAst(result, op.text, rhs);
				continue;
			}
			break;
		}

		return result;
	}

	private Ast unary() {
		var op = peek(0);
		if (match(TokenKind.ADDITIVE_OPERATOR) && op.text.equals("-")) {
			next();
			var lhs = parseTerm();
			return new UnaryAst("-", lhs);
		}

		return fieldAccess();
	}


	private Ast fieldAccess() {
		Ast result = call();
		while (true) {
			if (match(TokenKind.DOT)) {
				next();
				var t = peek(0);
				if (match(TokenKind.ID)) {
					next();
					Ast id = new IdentifierAst(t.text);
					id = finalizeNode(id, t.span);
					result = new DotOperationAst(result, id);
					continue;
				}
			}
			break;
		}

		return result;
	}

	private Ast call() {
		Token functionToken = peek(0);
		Ast result = moduleAccess();

		// Handle function calls with parentheses
		if (match(TokenKind.LPAREN)) {
			next(); // Consume the opening parenthesis
			List<Ast> args = new ArrayList<>();
			
			// Parse comma-separated arguments inside parentheses
			if (!match(TokenKind.RPAREN)) {
				args.add(expression());
				
				while (match(TokenKind.COMMA)) {
					next(); // Consume the comma
					args.add(expression());
				}
			}
			
			// Expect closing parenthesis
			expect(TokenKind.RPAREN, "Expected ')' after function arguments");
			
			result = new CallAst(result, args);
		}

		return result;
	}

	private Ast parseTerm() {
		if (match(TokenKind.NUMBER) || match(TokenKind.STRING) || match(TokenKind.ID)) {
			return parsePrimary();
		}

		if (match(TokenKind.LPAREN)) {
			return groupOrTuple();
		}

		if (match(TokenKind.LBRACE)) {
			return blockOrObject();
		}

		var top = peek(0);
		if (match(TokenKind.KEYWORD) && top.text.equals("if")) {
			return ifStatement();
		}

		var span = peek(0).span;
		
		throw new PiccodeException(span.filepath, span.line, span.column, "Expected an expression");
	}

	private Ast moduleAccess() {
		var t0 = peek(0);
		Ast result = conditional();
		while (true) {
			if (match(TokenKind.DBL_COLON)) {
				next();
				var t = peek(0);
				if (match(TokenKind.ID)) {
					next();

					Ast rhs = new IdentifierAst(t.text);
					rhs = finalizeNode(rhs, t.span);
				
					result = new CCOperationAst(result, rhs);
					
					// After creating a ModuleAccess, check if it's followed by function call parentheses
					if (match(TokenKind.LPAREN)) {
						next(); // Consume the opening parenthesis
						List<Ast> args = new ArrayList<>();
						
						// Parse comma-separated arguments inside parentheses
						if (!match(TokenKind.RPAREN)) {
							args.add(expression());
							
							while (match(TokenKind.COMMA)) {
								next(); // Consume the comma
								args.add(expression());
							}
						}
						
						// Expect closing parenthesis
						expect(TokenKind.RPAREN, "Expected ')' after function arguments");
						
						// Create a function call with the parsed arguments
						result = new CallAst(result, args);
					}
					
					continue;
				}
			}
			break;
		}
		return result;
	}

	private Ast conditional() {
		var top = peek(0);
		if (match(TokenKind.KEYWORD) && top.text.equals("if")) {
			return ifStatement();
		}
		Ast result = whenExpression();
		return result;
	}

	private Ast whenExpression() {
		var top = peek(0);
		if (match(TokenKind.KEYWORD) && top.text.equals("when")) {
			return matchExpression();
		}
		Ast result = blockOrClosureOrObject();
		return result;
	}

	private Ast blockOrClosureOrObject() {
		if (match(TokenKind.LBRACE)) {
			return blockOrObject();
		}
		Ast result = groupExpression();
		return result;
	}

	private Ast groupExpression() {
		if (match(TokenKind.LPAREN)) {
			return groupOrTuple();
		}
		return arrayExpression();
	}

	private Ast arrayExpression() {
		if (match(TokenKind.LBRACKET)) {
			return arrayExpr();
		}
		return parsePrimary();
	}
	
	private Ast arrayExpr() {
		var top = peek(0);
		next();
		List<Ast> elements = new ArrayList<>();
		while (!match(TokenKind.RBRACKET)) {
			elements.add(expression());
			if (match(TokenKind.RBRACKET)) {
				break;
			} else {
				expect(TokenKind.COMMA, "Expected a comma after the expression in array");
			}
		}
		next();
		return new ArrayAst(elements);
	}
	
	private Ast ifStatement() {
		next();
		var cond = expression();
		var t = peek(0);
		if (!match(TokenKind.LBRACE)) { 
			expect(TokenKind.ERR, "Expected `{` after condition expresssion in if expression");
		}
		var then = expression();
		t = peek(0);
		if (!(match(TokenKind.KEYWORD) && t.text.equals("else"))) {
			expect(TokenKind.ERR, "If expression without else is not allowed");
		}
		next();

		if (!match(TokenKind.LBRACE)) { 
			expect(TokenKind.ERR, "Expected `{` after else word expresssion in if expression");
		}
		var elze = expression();

		return new IfExpression(cond, then, elze);
	}

	private Ast parsePrimary() {
		var t = peek(0);
		if (match(TokenKind.NUMBER)) {
			next();
			Ast node = new NumberAst(t.text);
			return finalizeNode(node, t.span);
		}

		if (match(TokenKind.STRING)) {
			next();
			Ast node = new StringAst(t.text);
			return finalizeNode(node, t.span);
		}

		if (match(TokenKind.ID)) {
			next();
			Ast node = new IdentifierAst(t.text);
			return finalizeNode(node, t.span);
		}

		var span = t.span;
		throw new PiccodeException(span.filepath, span.line, span.column, "Invalid primary expression");
	}

	private Token peek(int offset) {
		if (offset >= this.tokens.size()) {
			return new Token(TokenKind.EOF, null);
		}
		return tokens.get(offset);
	}

	private Token next() {
		assert !tokens.isEmpty();
		return tokens.removeFirst();
	}

	private boolean match(TokenKind type) {
		final Token current = peek(0);
		if (type != current.kind) {
			return false;
		}
		return true;
	}

	private Token expect(TokenKind type, String text) {
		var top = peek(0);
		if (match(type)) {
			next();
			return top;
		}

		var span = top.span;
		throw new PiccodeException(span.filepath, span.line, span.column, "Expeceted token " + type.name());
	}

}

