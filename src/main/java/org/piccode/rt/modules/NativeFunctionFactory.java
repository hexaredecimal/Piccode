package org.piccode.rt.modules;

import java.util.List;
import java.util.Map;
import org.piccode.ast.FunctionAst;
import org.piccode.ast.IdentifierAst;
import org.piccode.rt.Context;
import org.piccode.rt.NativeFunction;
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
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				
				var parent = ctx.getTopFrame().caller;

				if (parent instanceof FunctionAst func) {
					parent = func.body;
				}

				var id = new IdentifierAst("<NativeFunc: " + name + ">");
				id.file = parent.file;
				id.line = parent.line;
				id.column = parent.column;
				ctx.pushStackFrame(id); 
				var result = fx.apply(args, namedArgs, frame);
				ctx.dropStackFrame();
				return result;
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
