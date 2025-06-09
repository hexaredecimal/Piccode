package org.piccode.ast;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.piccode.antlr4.PiccodeScriptLexer;
import org.piccode.antlr4.PiccodeScriptParser;
import org.piccode.backend.Compiler;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeInfo;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ImportAst extends Ast {

	public String path;
	public List<Ast> lifted;

	public ImportAst(String path) {
		this.path = path;
		this.lifted = new ArrayList<>();
	}

	public ImportAst(String path, List<Ast> lifted) {
		this.path = path;
		this.lifted = lifted;
	}
	

	@Override
	public String toString() {
		return "import " + path;
	}

	@Override
	public PiccodeValue execute() {
		var nodes = loadModuleFromStdLib(path);
		if (lifted.isEmpty()){
			return new PiccodeUnit();
		}

		for (var liftedNode : lifted) {
			executeLifted(nodes, liftedNode);
		}

		
		return new PiccodeUnit();
	}



	private void executeLifted(List<Ast> moduleNodes, Ast liftedNode) {
		if (liftedNode instanceof ImportId id) {
			for (var node : moduleNodes) {
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, id);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(id.text)) {
					func.execute();
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(id.text)) {
					mod.execute();
					return;
				}
			}
			throw new PiccodeException(file, line, column, "Symbol '" + id.text + "' not found in module: " + path);
		}

		if (liftedNode instanceof ImportLift lift) {
			for (var node : moduleNodes) {
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, lift);
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(lift.text)) {
					// Recursively execute lifted symbols from inside the module
					executeLifted(mod.nodes, lift.nodes);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(lift.text)) {
					var err = new PiccodeException(lift.file, lift.line, lift.column,
									"Invalid import lift. Attempt to lift function as module");
					PiccodeInfo note = new PiccodeException(func.file, func.line, func.column,
									"Function is declared here");
					err.addNote(note);
					throw err;
				}
			}
			throw new PiccodeException(file, line, column,
							"Module '" + lift.text + "' not found in module: " + path.replaceAll("/", "."));
		}
	}

	private void executeLifted(List<Ast> moduleNodes, List<Ast> nestedLifted) {
		for (var lifted : nestedLifted) {
			executeLifted(moduleNodes, lifted);
		}
	}


	

	private List<Ast> loadModuleFromStdLib(String module) {
		var nodes = new ArrayList<Ast>();
		var _file = new File(module);
		if (_file.isFile()) {
			throw new PiccodeException(file, line, column,"Invalid module " + module + " in pkg");
		}
		
		for (var fp : _file.listFiles()) {
			if (fp.getName().endsWith(".pics")) {
				var code = readFile(fp);
				if (code == null) {
					throw new PiccodeException(file, line, column,"Invalid module " + module + " in pkg");
				}
				nodes.add(_import(fp.getAbsolutePath(), code));
			}
		}
		return nodes;
	}

	private Ast _import(String file, String code) {
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false")); 

		// In case of an error we leak the current scope on sym table. 
		try {
			return Compiler.program(file, code);
		} catch (PiccodeException e) {
			Context.top.dropStackFrame();
			throw e;
		}
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

	@Override
	public String codeGen(TargetEnvironment target) {
		return "";
	}
}
