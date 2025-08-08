package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeReference;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeRefModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("set_ref", List.of("ref", "value"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var ref = namedArgs.get("ref");
				var value = namedArgs.get("value");
				PiccodeValue.verifyType(caller, ref, Type.REFERENCE);
				
				var obj = (PiccodeReference) ref;
				obj.setRef(value);
					
				return obj;
		}, null);
		
		
	}
}
