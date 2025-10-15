package org.piccode.ast;

import java.util.List;
import org.piccode.errors.PiccodeException;
import org.piccode.errors.PiccodeInfo;

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

	private void executeLifted(List<Ast> moduleNodes, Ast liftedNode, Integer frame, List<Ast> final_nodes) {
		if (liftedNode instanceof ImportId id) {
			for (var node : moduleNodes) {
				if (node instanceof ImportAst imp) {
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

}
