package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeTypesModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("isnumber", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value");
			return new PiccodeBoolean(String.valueOf(value instanceof PiccodeNumber));
		}, null);
		
		NativeFunctionFactory.create("isstring", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value");
			return new PiccodeBoolean(String.valueOf(value instanceof PiccodeString));
		}, null);

		NativeFunctionFactory.create("isobject", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value");
			return new PiccodeBoolean(String.valueOf(value instanceof PiccodeObject));
		}, null);

		NativeFunctionFactory.create("isarr", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value");
			return new PiccodeBoolean(String.valueOf(value instanceof PiccodeArray));
		}, null);

		NativeFunctionFactory.create("istuple", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value");
			return new PiccodeBoolean(String.valueOf(value instanceof PiccodeTuple));
		}, null);
		
	}
}
