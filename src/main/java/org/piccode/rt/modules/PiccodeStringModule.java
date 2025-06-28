package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeStringModule {
	
	public static void addFunctions() {
		NativeFunctionFactory.create("stringformat", List.of("fmt", "args"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var fmt = namedArgs.get("fmt");
			var arr = namedArgs.get("args");
			
			PiccodeValue.verifyType(caller, fmt, Type.STRING);
			PiccodeValue.verifyType(caller, arr, Type.ARRAY);

			var format = fmt.raw().toString();
			var array = ((PiccodeArray) arr).array();
			var result = String.format(format, (Object[]) array);
			return new PiccodeString(result);
		}, null);

		NativeFunctionFactory.create("stringtrim", List.of("value"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var val = namedArgs.get("value");
			PiccodeValue.verifyType(caller, val, Type.STRING);
			var value = val.raw().toString().trim();
			return new PiccodeString(value);
		}, null);
		
		NativeFunctionFactory.create("stringuppercase", List.of("value"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var val = namedArgs.get("value");
			PiccodeValue.verifyType(caller, val, Type.STRING);
			var value = val.raw().toString().toUpperCase();
			return new PiccodeString(value);
		}, null);
		
		NativeFunctionFactory.create("stringlowercase", List.of("value"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var val = namedArgs.get("value");
			PiccodeValue.verifyType(caller, val, Type.STRING);
			var value = val.raw().toString().toLowerCase();
			return new PiccodeString(value);
		}, null);

		NativeFunctionFactory.create("stringlength", List.of("value"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var val = namedArgs.get("value");
			PiccodeValue.verifyType(caller, val, Type.STRING);
			var value = val.raw().toString().length();
			return new PiccodeNumber(value);
		}, null);

		
		NativeFunctionFactory.create("stringsplit", List.of("value", "delim"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var val = namedArgs.get("value");
			var del = namedArgs.get("delim");
			PiccodeValue.verifyType(caller, val, Type.STRING);
			PiccodeValue.verifyType(caller, del, Type.STRING);

			var value = val.raw().toString();
			var delim = del.raw().toString();
			var splits = value.split(delim);
			var nodes = new ArrayList<PiccodeValue>();
			for (var split: splits) {
				nodes.add(new PiccodeString(split));
			}
			return new PiccodeArray(args);
		}, null);
	}
}
