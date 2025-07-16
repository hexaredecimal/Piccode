package org.piccode.rt.modules;

import com.github.tomaslanger.chalk.Chalk;
import java.util.List;
import java.util.Map;
import org.piccode.ast.Ast;
import org.piccode.ast.FunctionAst;
import org.piccode.ast.IdentifierAst;
import org.piccode.rt.Context;
import org.piccode.rt.NativeFunction;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class NativeFunctionFactory {

	public static NativeFunction create(String name, List<String> args, TriFunction<List<PiccodeValue>, Map<String, PiccodeValue>, Integer, PiccodeValue> fx, Integer frame) {
		NativeFunction func = new NativeFunction(name, args, null, frame) {
			@Override
			public PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs, Integer frame) {
				var ctx = frame == null
								? Context.top
								: Context.getContextAt(frame);

				var parent = ctx.getTopFrame().caller;

				if (parent instanceof FunctionAst func) {
					parent = func.body;
				}

				var id = new IdentifierAst("<NativeFunc: " + name + ">");
				id.file = parent.file;
				id.line = parent.line;
				id.column = parent.column;

				var self = (NativeFunction) this;
				if (self.params.size() != args.size()) {
					var expected = Chalk.on(self.params.size() + "").green();
					var received = Chalk.on(args.size() + "").red();
					throw new PiccodeException(id.file, id.line, id.column,
									"Invalid number of parameters passed to native function `" + Chalk.on(id.text).green() + "` , expcted " + expected + " but got " + received);
				}

				return Ast.safeExecute(frame, id, (node) -> {
					return fx.apply(args, namedArgs, frame);
				});
			}

			@Override
			public Object raw() {
				return this;
			}
		};

		Context.addGlobal("pic_nat_" + name, func);
		return func;
	}

	@FunctionalInterface
	public interface TriFunction<A, B, C, R> {

		R apply(A a, B b, C c);
	}
}
