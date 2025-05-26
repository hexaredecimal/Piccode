package org.piccode.backend;

import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.ast.Ast;
import org.piccode.ast.CallAst;
import org.piccode.ast.FunctionAst;
import org.piccode.ast.IdentifierAst;
import org.piccode.ast.PiccodeVisitor;
import org.piccode.ast.StatementList;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.modules.PiccodeArrayModule;
import org.piccode.rt.modules.PiccodeIOModule;
import org.piccode.rt.modules.PiccodeMathModule;
import org.piccode.rt.modules.PiccodeStringModule;
import org.piccode.rt.modules.PiccodeSystemModule;
import org.piccode.rt.modules.PiccodeTupleModule;

/**
 *
 * @author hexaredecimal
 */
public class Compiler {

	public static PiccodeValue compile(String file, String code) {
		return compile(file, code, List.of());
	}

	public static PiccodeValue compile(String file, String code, List<PiccodeValue> args) {
		var result = program(file, code);

		Context.top.pushStack();
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false"));
		Context.top.putLocal("_pic_nat_user_args", new PiccodeArray(args));
		addGlobalFunctions();

		PiccodeValue res = new PiccodeUnit();
		var has_main = false;
		try {
			for (var stmt : result.nodes) {
				if (stmt instanceof FunctionAst func && func.name.equals("main") && (func.arg == null || func.arg.isEmpty())) {
					has_main = true;
				}
				res = stmt.execute();
			}

			if (has_main) {
				return new CallAst(new IdentifierAst("main"), List.of()).execute();
			}
			return res;
		} catch (PiccodeException e) {
			e.reportError();
			//e.printStackTrace();
			return new PiccodeUnit();
		} catch (Exception rte) {
			Context.top.dropStackFrame();
			rte.printStackTrace();
			return new PiccodeUnit();
		}
	}

	public static List<Ast> parse(String file, String code) {
		return program(file, code).nodes;
	}

	public static StatementList program(String file, String code) {
		var lexer = new PiccodeScriptLexer(CharStreams.fromString(code));
		var parser = new PiccodeScriptParser(new CommonTokenStream(lexer));
		lexer.removeErrorListeners();
		parser.removeErrorListeners();

		var visitor = new PiccodeVisitor(file);

		var result = (StatementList) visitor.visit(parser.stmts());
		return result;
	}

	public static void prepareGlobalScope() {
		Context.top.pushStack();
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false"));
		addGlobalFunctions();
	}

	private static void addGlobalFunctions() {
		PiccodeIOModule.addFunctions();
		PiccodeArrayModule.addFunctions();
		PiccodeStringModule.addFunctions();
		PiccodeTupleModule.addFunctions();
		PiccodeMathModule.addFunctions();
		PiccodeSystemModule.addFunctions();
	}

}
