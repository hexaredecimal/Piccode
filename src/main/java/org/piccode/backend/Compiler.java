package org.piccode.backend;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.ast.Ast;
import org.piccode.ast.PiccodeVisitor;
import org.piccode.ast.StatementList;
import org.piccode.ast.TypedFunction;
import org.piccode.ast.types.RecordTypeDeclaration;
import org.piccode.ast.types.TypeDeclaration;
import org.piccode.piccodescript.ErrorAsciiKind;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.type.BooleanType;
import org.piccode.typechecker.type.NumberType;
import org.piccode.typechecker.type.StringType;
import org.piccode.typechecker.type.UnitType;

/**
 *
 * @author hexaredecimal
 */
public class Compiler {

	public static PrintStream out = System.out;
	public static ErrorAsciiKind errorKind = ErrorAsciiKind.GLEAM_STYLE;
	public static boolean exitOnError = true;


	public static void compile(String file, String code) {
		try {
			var result = program(file, code);
			var ctx = new Context();
			var typeTable = ctx.getTypeTable();
			typeTable.putSymbol("Number", new NumberType(result));
			typeTable.putSymbol("String", new StringType(result));
			typeTable.putSymbol("Boolean", new BooleanType(result));
			typeTable.putSymbol("Unit", new UnitType(result));
			
			List<TypedFunction> typedFunctions = new ArrayList<>();
			for (var stmt : result.nodes) {
				switch (stmt) {
					case TypeDeclaration td -> td.typeCheck(ctx);
					case TypedFunction tf -> typedFunctions.add(tf);
					case RecordTypeDeclaration rc -> rc.typeCheck(ctx);
					default -> {}
				}
			}

			

			typedFunctions.forEach(func -> func.typeCheckHeader(ctx));
			typedFunctions.forEach(func -> func.typeCheck(ctx));

			var functonTable = ctx.getFunctionTable();
			System.out.println(typeTable);
			System.out.println(functonTable);
			
		} catch (PiccodeException e) {
			e.reportError(exitOnError);
		} catch (Exception rte) {
			rte.printStackTrace();
		}
	}



	public static List<Ast> parse(String file, String code) {
		return program(file, code).nodes;
	}

	public static StatementList program(String file, String code) {
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
