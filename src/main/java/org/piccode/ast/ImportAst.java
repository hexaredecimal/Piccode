package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.errors.PiccodeException;
import org.piccode.errors.PiccodeInfo;

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


	private void executeLifted(List<Ast> moduleNodes, Ast liftedNode, Integer frame) {
		if (liftedNode instanceof ImportId id) {
			for (var node : moduleNodes) {
				if (node instanceof ImportAst imp) {
				}
				if (node instanceof StatementList sts) {
					executeLifted(sts.nodes, id, frame);
					return;
				}
				if (node instanceof FunctionAst func && func.name.equals(id.text)) {
					return;
				}
				if (node instanceof ModuleAst mod && mod.name.equals(id.text)) {
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
}
