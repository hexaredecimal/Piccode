package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeObjectModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("haskey", List.of("obj", "key"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			
			var caller = ctx.getTopFrame().caller;
			var object = namedArgs.get("obj");
			var key = namedArgs.get("key");
			PiccodeValue.verifyType(caller, object, Type.OBJECT);
			PiccodeValue.verifyType(caller, key, Type.STRING);
			var obj = (PiccodeObject) object;
			var ky = (PiccodeString) key;
			return new PiccodeBoolean(obj.obj.containsKey(ky.toString()));
		}, null);
	
		NativeFunctionFactory.create("hasvalue", List.of("obj", "value"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			
			var caller = ctx.getTopFrame().caller;
			var object = namedArgs.get("obj");
			var key = namedArgs.get("value");
			PiccodeValue.verifyType(caller, object, Type.OBJECT);
			var obj = (PiccodeObject) object;
			return new PiccodeBoolean(obj.obj.containsValue(key));
		}, null);

		NativeFunctionFactory.create("getvalues", List.of("obj"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			
			var caller = ctx.getTopFrame().caller;
			var object = namedArgs.get("obj");
			PiccodeValue.verifyType(caller, object, Type.OBJECT);
			var obj = (PiccodeObject) object;
			var nodes = new ArrayList<PiccodeValue>();
			for (var value: obj.obj.values()) {
				nodes.add(value);
			}
			return new PiccodeArray(args);
		}, null);

		NativeFunctionFactory.create("getkeys", List.of("obj"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			
			var caller = ctx.getTopFrame().caller;
			var object = namedArgs.get("obj");
			PiccodeValue.verifyType(caller, object, Type.OBJECT);
			var obj = (PiccodeObject) object;
			var nodes = new ArrayList<PiccodeValue>();
			for (var key: obj.obj.keySet()) {
				nodes.add(new PiccodeString(key));
			}
			return new PiccodeArray(args);
		}, null);

	}
}
