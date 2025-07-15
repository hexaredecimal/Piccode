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
			var nms = new StringBuilder();
			var entrySet = stack.entrySet();
			var size = entrySet.size();
			var index = 0;
			var count = 1;

			for (var kv : entrySet) {
				var key = kv.getKey();
				// Skip built-in functions in the report
				if(key.startsWith("pic_nat_")) {
				  continue;
				}
				nms.append(key);
				var val = kv.getValue().hashCode();
				var fmt = String.format("0x%08X", val);
				sb.append(fmt);

				if (index < size - 1) {
					sb.append(" ");
					nms.append(" ");
				}
				count++;

				if (index + 1 == size) {
					var split = sb.toString().split("\n");
					if (split.length > 0 && count < 3) {
						var part = split[0].split("|")[1];
						var lastSplt = split[split.length - 1].split("|");
						var last = lastSplt.length == 1 ? lastSplt[0] : lastSplt[lastSplt.length - 1];
						var padd = " ".repeat(part.length() - last.length());
						sb.append(padd)
							.append(" | ")
							.append(nms.toString().trim());
						count=1;
					}
				} else if ((index + 1) % 3 == 0) {
					count =1;
					sb
									.append(" | ")
									.append(nms.toString().trim());
					sb.append("\n");
					nms = new StringBuilder();
				}
				index++;
			}

			var sz = ctx.getFramesCount();
			var note = new PiccodeSimpleNote("Stack size: " + sz);
			err.addNote(note);

			if (sz > 0) {
				var _sb = new StringBuilder();
				for (var _frame: ctx.getCallStack()) {
					_sb.append(_frame.caller).append("\n");
				}
				note = new PiccodeSimpleNote("Stack dump: \n" + _sb.toString());
				err.addNote(note);
			}

			note = new PiccodeSimpleNote("Symbol table dump: \n" + sb.toString());
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
