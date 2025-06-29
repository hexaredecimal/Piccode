package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.NativeFunction;
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
	public PiccodeValue execute(Integer frame) {
		var ctx = frame == null
			? Context.top
			: Context.getContextAt(frame);

		
		var value = ctx.getValue(text);
		
		if (value == null) {
			var err = new PiccodeException(file, line, column, "Unknown variable `" + Chalk.on(text).red() + "` ");
			err.frame = frame;
			var nm = Context.top.getSimilarName(text);
			if (nm != null && !nm.isEmpty()) {
				var note = new PiccodeSimpleNote("Did you mean `" + Chalk.on(nm).green() + "` instead of `" + Chalk.on(text).red() + "` ?");
				err.addNote(note);
			}
			
			var stack = ctx.getTopFrame().toMap();
			var sb = new StringBuilder();
			var entrySet = stack.entrySet();
			var size = entrySet.size();
			var index  = 0;
			for (var kv: entrySet) {
				var key = kv.getKey();
				sb.append(key);
				if (index < size - 1) {
					sb.append(" ,");
				}
				var str = sb.toString().length();
				if (index % 5 == 0) {
					sb.append("\n");
				}
				index++;
			}
			
			var note = new PiccodeSimpleNote("Track size: " + ctx.getFramesCount());
			err.addNote(note);
			
			note = new PiccodeSimpleNote("Symbol table dump: " + sb.toString());
			err.addNote(note);
			throw err;
		}

		if (value instanceof NativeFunction nat) {
			nat.frame = frame;
		} 
		return value;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return text;
	}

}
