package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeProcModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("forEach", List.of("array", "func"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var array = namedArgs.get("array");
				var func = namedArgs.get("func");
				PiccodeValue.verifyType(caller, array, Type.ARRAY);
				PiccodeValue.verifyType(caller, func, Type.CLOSURE);
				var closure = (PiccodeClosure) func;
				((PiccodeArray)array).nodes.forEach(value -> {
					closure.call(value);
					closure.evaluateIfReady();
				});
				return new PiccodeUnit();
		}, null);
		
		NativeFunctionFactory.create("forever", List.of("func"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var func = namedArgs.get("func");
				PiccodeValue.verifyType(caller, func, Type.CLOSURE);
				var closure = (PiccodeClosure) func;
				while (true) {
					closure.call(new PiccodeUnit());
					closure.evaluateIfReady();
				}
		}, null);
	}
}
