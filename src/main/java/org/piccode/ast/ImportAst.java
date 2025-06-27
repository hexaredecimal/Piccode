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
	public PiccodeValue execute(Integer frame) {
		var nodes = loadModuleFromStdLib(path, frame);

		if (lifted.isEmpty()) {
			nodes.forEach(node -> node.execute(frame));
			return new PiccodeUnit();
		}

		for (var liftedNode : lifted) {
			executeLifted(nodes, liftedNode, frame);
		}

		return new PiccodeUnit();
	}

	private void executeLifted(List<Ast> moduleNodes, Ast liftedNode, Integer frame) {
		if (liftedNode instanceof ImportId id) {
			for (var node : moduleNodes) {
				if (node instanceof ImportAst imp) {
					imp.execute(frame);
				}
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, id, frame);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(id.text)) {
					func.execute(frame);
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(id.text)) {
					mod.execute(frame);
					return;
				}
			}
			var err = new PiccodeException(file, line, column, "Symbol '" + id.text + "' not found in module: " + path);
			err.frame = frame;
			throw err;
		}

		if (liftedNode instanceof ImportLift lift) {
			for (var node : moduleNodes) {
				if (node instanceof ImportAst imp) {
					imp.execute(frame);
				}
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, lift, frame);
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(lift.text)) {
					// Recursively execute lifted symbols from inside the module
					executeLifted(mod.nodes, lift.nodes, frame);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(lift.text)) {
					var err = new PiccodeException(lift.file, lift.line, lift.column,
									"Invalid import lift. Attempt to lift function as module");
					err.frame = frame;
					PiccodeInfo note = new PiccodeException(func.file, func.line, func.column,
									"Function is declared here");
					err.addNote(note);
					throw err;
				}
			}
			var err = new PiccodeException(file, line, column,
							"Module '" + lift.text + "' not found in module: " + path.replaceAll("/", "."));
			err.frame = frame;
			throw err;
		}
	}

	private void executeLifted(List<Ast> moduleNodes, List<Ast> nestedLifted, Integer frame) {
		for (var lifted : nestedLifted) {
			executeLifted(moduleNodes, lifted, frame);
		}
	}

	private List<Ast> loadModuleFromStdLib(String module, Integer frame) {
		var storage = getAppStorage();
		var paths = List.of(storage, "./");
		var nodes = new ArrayList<Ast>();
		var _file = findModule(module, paths);
		if (_file == null) {
			var err = new PiccodeException(file, line, column, "Invalid module " + module.replaceAll("/", "."));
			err.frame = frame;
			throw err;
		}

		var path = _file.getPath();
		if (Context.top.isImportCached(path)) {
			return Context.top.getCached(path);
		}

		var files = _file.listFiles();

		for (var fp : files) {
			if (fp.getName().endsWith(".pics")) {
				var code = readFile(fp);
				if (code == null) {
					var err =	new PiccodeException(file, line, column, "Invalid module " + module.replaceAll("/", "."));
					err.frame = frame;
					throw err;
				}
				var program = (StatementList) _import(fp.getAbsolutePath(), code);
				var noImports = program.nodes
								.stream()
								.filter((value) -> {
									if (value instanceof ImportAst) {
										value.execute(frame);
									}
									return !(value instanceof ImportAst);
								})
								.toList();
				nodes.addAll(noImports);
			}
		}
		if (!Context.top.isImportCached(path)) {
			Context.top.cacheImport(path, lifted);
		}
		return nodes;
	}

	private Ast _import(String file, String code) {
		Context.top.putLocal("true", new PiccodeBoolean("true"));
		Context.top.putLocal("false", new PiccodeBoolean("false"));

		try {
			return Compiler.program(file, code);
		} catch (PiccodeException e) {
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

	private String getAppStorage() {
		try {
			String path = ImportAst.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			return new File(path).getParentFile().getPath();
		} catch (URISyntaxException ex) {
			return "./";
		}
	}

	private File findModule(String module, List<String> of) {
		for (var dir : of) {
			var fp = new File(dir + "/" + module);
			if (fp.isDirectory()) {
				return fp;
			}
		}
		return null;
	}
}
