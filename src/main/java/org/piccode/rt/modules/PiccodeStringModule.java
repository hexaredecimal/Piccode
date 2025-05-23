package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.List;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeStringModule {
	
	public static void addFunctions() {
		NativeFunctionFactory.create("stringformat", List.of("fmt", "args"), (args, namedArgs) -> {
			var fmt = namedArgs.get("fmt").raw().toString();
			var arr = ((PiccodeArray) namedArgs.get("args")).array();
			var result = String.format(fmt, (Object[]) arr);
			return new PiccodeString(result);
		});

		NativeFunctionFactory.create("stringtrim", List.of("value"), (args, namedArgs) -> {
			var value = namedArgs.get("value").raw().toString().trim();
			return new PiccodeString(value);
		});
		
		NativeFunctionFactory.create("stringuppercase", List.of("value"), (args, namedArgs) -> {
			var value = namedArgs.get("value").raw().toString().toUpperCase();
			return new PiccodeString(value);
		});
		
		NativeFunctionFactory.create("stringlowercase", List.of("value"), (args, namedArgs) -> {
			var value = namedArgs.get("value").raw().toString().toLowerCase();
			return new PiccodeString(value);
		});

		NativeFunctionFactory.create("stringlength", List.of("value"), (args, namedArgs) -> {
			var value = namedArgs.get("value").raw().toString().length();
			return new PiccodeNumber(""+value);
		});

		
		NativeFunctionFactory.create("stringsplit", List.of("value", "delim"), (args, namedArgs) -> {
			var value = namedArgs.get("value").raw().toString();
			var delim = namedArgs.get("delim").raw().toString();
			var splits = value.split(delim);
			var nodes = new ArrayList<PiccodeValue>();
			for (var split: splits) {
				nodes.add(new PiccodeString(split));
			}
			return new PiccodeArray(args);
		});
	}
}
