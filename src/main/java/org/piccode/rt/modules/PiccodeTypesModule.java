package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeTypesModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("tuplesize", List.of("tuple"), (args, namedArgs) -> {
				var arr = ((PiccodeTuple) namedArgs.get("tuple")).array().length;
				return new PiccodeNumber("" + arr);
		});
		
		NativeFunctionFactory.create("tupletoarray", List.of("tuple"), (args, namedArgs) -> {
				var arr = ((PiccodeTuple) namedArgs.get("tuple")).array();
				var list = new ArrayList<PiccodeValue>();
				list.addAll(Arrays.asList(arr));
				return new PiccodeArray(list);
		});
		
	}
}
