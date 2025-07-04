package org.piccode.ast;

import com.github.tomaslanger.chalk.Chalk;
import java.util.ArrayList;
import java.util.function.Function;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeReturnException;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class CatchAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public CatchAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + " catch " + rhs;
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var ctx = frame == null
						? Context.top
						: Context.getContextAt(frame);

		return executeOnElse(frame, lhs, (node) -> {
			try {
				var result = node.execute(frame);
				ctx.dropScope();
				return result;
			} catch (PiccodeReturnException ret) {
				ctx.dropScope();
				return ret.value;
			} catch (Exception e1) {
				ctx.dropScope();
				throw e1;
			}

		});
	}

	private PiccodeValue executeOnElse(Integer frame, Ast node, Function<Ast, PiccodeValue> other) {
		try {
			var result = node.execute(frame);
			if (result instanceof PiccodeTuple tupl) {
				var last = tupl.nodes.getLast();
				if (!(last instanceof PiccodeUnit)) {
					var ctx = frame == null
									? Context.top
									: Context.getContextAt(frame);
					ctx.pushScope();
					ctx.putLocal("err", last);
					result = other.apply(rhs);
					return result;
				} else {
					var nodes = new ArrayList<PiccodeValue>();
					var size = tupl.nodes.size();
					for (var index = 0; index < size - 1; index++) {
						var _node = tupl.nodes.get(index);
						nodes.add(_node);
					}
					
					return nodes.size() > 1 ?
								new PiccodeTuple(nodes)
								: nodes.getLast();
				}
			}

			return result;
		} catch (PiccodeReturnException ret) {
			return ret.value;
		} catch (PiccodeException e) {
			throw e;
		} catch (Exception e) {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			ctx.pushScope();
			ctx.putLocal("err", new PiccodeString(e.toString()));
			var result = other.apply(rhs);
			return result;
		}
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("%s %s", lhs, rhs);
	}
}
