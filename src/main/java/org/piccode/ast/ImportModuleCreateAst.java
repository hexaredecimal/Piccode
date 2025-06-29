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
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ImportModuleCreateAst extends Ast {

	public String name;
	public ImportAst import_;
	private String path;

	public ImportModuleCreateAst(String name, ImportAst import_) {
		this.name = name;
		this.import_ = import_;
		this.path = import_.path;
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var nodes = import_.loadModuleFromStdLib(path, frame);

		var final_nodes = new ArrayList<Ast>();
		var lifted = import_.lifted;

		if (lifted.isEmpty()) {
			nodes.forEach(node -> {
				if (node instanceof ModuleAst mod && mod.name.equals(name)) {
					final_nodes.addAll(mod.nodes);
				} else {
					final_nodes.add(node);
				}
			});

			var top = Context.top.getValue(name);
			if (top != null && top instanceof PiccodeModule module) {
				final_nodes.addAll(module.nodes);
				var mod = new ModuleAst(name, final_nodes);
				var result = mod.execute(frame);
				Context.top.putLocal(name, result);
				return result;
			} else {
				var mod = new ModuleAst(name, final_nodes);
				var result = mod.execute(frame);
				return result;
			}
		}

		for (var liftedNode : lifted) {
			executeLifted(nodes, liftedNode, frame, final_nodes);
		}

		var top = Context.top.getValue(name);
		if (top != null && top instanceof PiccodeModule module) {
			final_nodes.addAll(module.nodes);
			var mod = new ModuleAst(name, final_nodes);
			var result = mod.execute(frame);
			Context.top.putLocal(name, result);
			return result;
		} else {
			var mod = new ModuleAst(name, final_nodes);
			var result = mod.execute(frame);
			return result;
		}

	}

	private void executeLifted(List<Ast> moduleNodes, Ast liftedNode, Integer frame, List<Ast> final_nodes) {
		if (liftedNode instanceof ImportId id) {
			for (var node : moduleNodes) {
				if (node instanceof ImportAst imp) {
					imp.execute(frame);
				}
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, id, frame, final_nodes);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(id.text)) {
					final_nodes.add(node);
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(id.text)) {
					if (id.text.equals(name)) {
						final_nodes.addAll(mod.nodes);
						return;
					}

					mod.createSymbol = false;
					final_nodes.add(node);
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
					executeLifted(sts.nodes, lift, frame, final_nodes);
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(lift.text)) {
					// Recursively execute lifted symbols from inside the module
					executeLifted(mod.nodes, lift.nodes, frame, final_nodes);
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

	private void executeLifted(List<Ast> moduleNodes, List<Ast> nestedLifted, Integer frame, List<Ast> final_nodes) {
		for (var lifted : nestedLifted) {
			executeLifted(moduleNodes, lifted, frame, final_nodes);
		}
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
}
