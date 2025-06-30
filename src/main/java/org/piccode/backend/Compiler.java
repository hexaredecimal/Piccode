package org.piccode.backend;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.ast.Ast;
import org.piccode.ast.CallAst;
import org.piccode.ast.FunctionAst;
import org.piccode.ast.IdentifierAst;
import org.piccode.ast.PiccodeVisitor;
import org.piccode.ast.ReturnAst;
import org.piccode.ast.StatementList;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeReturnException;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.modules.PiccodeArrayModule;
import org.piccode.rt.modules.PiccodeFileModule;
import org.piccode.rt.modules.PiccodeIOModule;
import org.piccode.rt.modules.PiccodeMathModule;
import org.piccode.rt.modules.PiccodeStringModule;
import org.piccode.rt.modules.PiccodeSystemModule;
import org.piccode.rt.modules.PiccodeTimeModule;
import org.piccode.rt.modules.PiccodeTupleModule;
import org.piccode.rt.modules.PiccodeTypesModule;
import org.piccode.rt.modules.PiccodeVirtualModule;

/**
 *
 * @author hexaredecimal
 */
public class Compiler {

	public static PiccodeValue compile(String file, String code) {
		return compile(file, code, List.of());
	}

	public static PiccodeValue compile(String file, String code, List<PiccodeValue> args) {
		try {
			var result = program(file, code);
			prepareGlobalScope(file);

			PiccodeValue res = new PiccodeUnit();
			var has_main = false;
			for (var stmt : result.nodes) {
				if (stmt instanceof FunctionAst func && func.name.equals("main") && (func.arg == null || func.arg.isEmpty())) {
					has_main = true;
				}

				if (stmt instanceof ReturnAst ret) {
					res = ret.expr.execute(null);
					break;
				}
				try {
					res = stmt.execute(null);
				} catch (PiccodeReturnException pre) {
					res = pre.value;
				} catch (Exception e) {
					throw e;
				}
			}

			if (has_main) {
				var _result = new CallAst(new IdentifierAst("main"), List.of()).execute(null);
				return _result;
			}
			
			Context.top.dropStackFrame();
			return res;
		} catch (PiccodeReturnException ret) {
			if (Context.top.getFramesCount() > 0) {
				Context.top.dropStackFrame();
			}
			return ret.value;
		} catch (PiccodeException e) {
			if (Context.top.getFramesCount() > 0) {
				Context.top.dropStackFrame();
			}
			//Context.top.dropStackFrame();
			e.reportError();
			//e.printStackTrace();
			return new PiccodeUnit();
		} catch (Exception rte) {
			if (Context.top.getFramesCount() > 0) {
				Context.top.dropStackFrame();
			}
			rte.printStackTrace();
			return new PiccodeUnit();
		}
	}

	public static List<Ast> parse(String file, String code) {
		return program(file, code).nodes;
	}

	public static StatementList program(String file, String code) {
		
		if (code.startsWith("#!/")) {
			code = sanitizeSourceFile(code);
		}
		
		var err = new SyntaxError(file);
		
		var lexer = new PiccodeScriptLexer(CharStreams.fromString(code));
		lexer.removeErrorListeners();
		lexer.addErrorListener(err);
		
		
		var parser = new PiccodeScriptParser(new CommonTokenStream(lexer));
		parser.removeErrorListeners();
		parser.addErrorListener(err);

		var visitor = new PiccodeVisitor(file);

		var result = (StatementList) visitor.visit(parser.stmts());
		return result;
	}

	private static String sanitizeSourceFile(String code) {
		var lines = new ArrayList<>(code.lines().toList());
		lines.removeFirst();
		return String.join("\n", lines);
	}

	public static void prepareGlobalScope(String file) {
		prepareGlobalScope(file, "globalScope");
	}

	public static void prepareGlobalScope(String file, String globalScopeName) {
		var scope_id = new IdentifierAst(globalScopeName);
		scope_id.column = 0;
		scope_id.line = 1;
		scope_id.file = file;
		Context.top.pushStackFrame(scope_id);
		Context.top.putLocal("true", new PiccodeBoolean("true"));
    Context.top.putLocal("false", new PiccodeBoolean("false"));
		addSystemFunctions();
	}


	private static void addSystemFunctions() {
		PiccodeIOModule.addFunctions();
		PiccodeArrayModule.addFunctions();
		PiccodeStringModule.addFunctions();
		PiccodeTupleModule.addFunctions();
		PiccodeMathModule.addFunctions();
		PiccodeSystemModule.addFunctions();
		PiccodeTimeModule.addFunctions();
		PiccodeTypesModule.addFunctions();
		PiccodeVirtualModule.addFunctions();
		PiccodeFileModule.addFunctions();
	}


	private static class SyntaxError extends BaseErrorListener {

		public String file;

		public SyntaxError(String file) {
			this.file = file;
		}
		
		
		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
			throw new PiccodeException(file, line, charPositionInLine, msg);
		}
	}
}
