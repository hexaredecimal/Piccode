package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeSimpleNote;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class IdentifierAst extends Ast {

	public String text;

	public IdentifierAst(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public PiccodeValue execute() {
		var value = Context.top.getValue(text);
		if (value == null) {
			var err = new PiccodeException(file, line, column, "Unknown variable `" + Chalk.on(text).red() + "` ");
			var nm = Context.top.getSimilarName(text);
			if (nm != null && !nm.isEmpty()) {
				var note = new PiccodeSimpleNote("Did you mean `" + Chalk.on(nm).green() + "` instead of `" + Chalk.on(text).red() + "` ?");
				err.addNote(note);
			}
			throw err;
		}

		return value;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return text;
	}

}
