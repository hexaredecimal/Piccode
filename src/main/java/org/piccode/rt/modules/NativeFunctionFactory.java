package org.piccode.rt.modules;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.piccode.rt.Context;
import org.piccode.rt.NativeFunction;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class NativeFunctionFactory {
	public static NativeFunction create(String name, BiFunction<List<PiccodeValue>, Map<String, PiccodeValue>, PiccodeValue> fx) {
		return create(name, List.of(), fx);
	}
	
	public static NativeFunction create(String name, List<String> args,  BiFunction<List<PiccodeValue>, Map<String, PiccodeValue>, PiccodeValue> fx) {
		NativeFunction func= new NativeFunction(name, args, null) {
			@Override
			public PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs) {
				return fx.apply(args, namedArgs);
			}

			@Override
			public Object raw() {
				return this;
			}
		};

		Context.addGlobal("pic_nat_" + name, func);
		return func;
	}
}
