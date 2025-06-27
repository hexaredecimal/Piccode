package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeString;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeArrayModule {
	public static void addFunctions() {
		NativeFunctionFactory.create("array_tostring", List.of("arr"), (args, namedArgs, frame) -> {
				var arr = ((PiccodeArray) namedArgs.get("arr")).getList();
				return new PiccodeString(arr.toString());
		}, null);
	}
}
