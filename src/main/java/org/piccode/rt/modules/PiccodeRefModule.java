package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeTupleModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("tuplesize", List.of("tuple"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var tup = namedArgs.get("tuple");
				PiccodeValue.verifyType(caller, tup, Type.TYPLE);
				var arr = ((PiccodeTuple) tup).array().length;
				return new PiccodeNumber("" + arr);
		}, null);
		
		NativeFunctionFactory.create("tupletoarray", List.of("tuple"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var tup = namedArgs.get("tuple");
				PiccodeValue.verifyType(caller, tup, Type.TYPLE);
				var arr = ((PiccodeTuple) tup).array();
				var list = new ArrayList<PiccodeValue>();
				list.addAll(Arrays.asList(arr));
				return new PiccodeArray(list);
		}, null);
		
	}
}
