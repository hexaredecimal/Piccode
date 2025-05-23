package org.piccode.backend;

import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.ast.Ast;
import org.piccode.ast.PiccodeVisitor;
import org.piccode.ast.StatementList;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.modules.PiccodeArrayModule;
import org.piccode.rt.modules.PiccodeIOModule;
import org.piccode.rt.modules.PiccodeMathModule;
import org.piccode.rt.modules.PiccodeStringModule;
import org.piccode.rt.modules.PiccodeTupleModule;

/**
 *
 * @author hexaredecimal
 */
public class Compiler {


	public static PiccodeValue compile(String code) {
		var result = program(code);
		
		Context.top.pushStack();
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false"));
		addGlobalFunctions();
		
		PiccodeValue res = new PiccodeUnit();
		try {
			for (var stmt : result.nodes) {
				res = stmt.execute();
			}
			return res;
		} catch (Exception rte) {
			Context.top.dropStackFrame();
			System.err.println("ERROR: " + rte.getMessage());
		  return new PiccodeUnit();
		} 	
	}

	public static List<Ast> parse(String code) {
		return program(code).nodes;
	}

	public static StatementList program(String code) {
		var lexer = new PiccodeScriptLexer(CharStreams.fromString(code));
		var parser = new PiccodeScriptParser(new CommonTokenStream(lexer));
		lexer.removeErrorListeners();
		parser.removeErrorListeners();

		var visitor = new PiccodeVisitor();

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
	}

}
