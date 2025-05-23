package org.piccode.ast;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.backend.Compiler;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ImportAst implements Ast {

	public String pkg;
	public String module;

	public ImportAst(String pkg, String module) {
		this.pkg = pkg;
		this.module = module;
	}

	@Override
	public String toString() {
		return "import " + pkg + ":" + module;
	}

	@Override
	public PiccodeValue execute() {
		if (pkg.equals("pkg")) {
			loadModuleFromStdLib(module);
		}

		return new PiccodeBoolean("true");
	}

	private void loadModuleFromStdLib(String module) {
		var file = new File("pkg/" + module);
		if (file.isFile()) {
			throw new PiccodeException("Invalid module " + module + " in pkg");
		}
		for (var fp : file.listFiles()) {
			if (fp.getName().endsWith(".pics")) {
				var code = readFile(fp);
				if (code == null) {
					throw new PiccodeException("Invalid module " + module + " in pkg");
				}
				_import(code);
			}
		}
	}

	private void _import(String code) {
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false")); 

		// In case of an error we leak the current scope on sym table. 
		Compiler.program(code).execute();
	}

	private String readFile(File fp) {
		StringBuilder sb = new StringBuilder();
		Scanner sc;
		try {
			sc = new Scanner(fp);
			while (sc.hasNext()) {
				sb.append(sc.nextLine()).append("\n");
			}
			return sb.toString();
		} catch (FileNotFoundException ex) {
			return null;
		}
	}
}
