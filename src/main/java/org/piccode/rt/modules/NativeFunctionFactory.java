package org.piccode.rt.modules;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
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
				
				ctx.pushStackFrame(new IdentifierAst("<NativeFunc: " + name + ">")); 
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
