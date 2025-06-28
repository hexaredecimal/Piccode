package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeArrayModule {
	public static void addFunctions() {
		NativeFunctionFactory.create("array_tostring", List.of("arr"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				var value = namedArgs.get("arr");
				
				PiccodeValue.verifyType(caller, value, Type.ARRAY);

				var arr = ((PiccodeArray) value).getList();
				return new PiccodeString(arr.toString());
		}, null);
	}
}
