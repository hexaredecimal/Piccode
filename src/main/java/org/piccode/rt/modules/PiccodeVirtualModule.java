package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeVirtualModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("task", List.of("fx"), (args, namedArgs) -> {
				var scope = Context.top.getTopFrame();
				var fx = namedArgs.get("fx");
			
				if (!(fx instanceof PiccodeClosure)) {
					var node = scope.caller;
					throw new PiccodeException(node.file, node.line, node.column, "Invalid value passed. Expected a closure but found " + fx);
				}
				var id = Context.top.makeThread((PiccodeClosure) fx);
				var future = Context.top.getFuture(id);
				var obj = new HashMap<String, PiccodeValue>();
				obj.put("uuid", new PiccodeString(id));
				obj.put("future", new PiccodeNumber(future.hashCode()));
				return new PiccodeObject(obj);
		});
		
		NativeFunctionFactory.create("sleep", List.of("ms"), (args, namedArgs) -> {
				var scope = Context.top.getTopFrame();
				var ms = namedArgs.get("ms");
			
				if (!(ms instanceof PiccodeNumber)) {
					var node = scope.caller;
					throw new PiccodeException(node.file, node.line, node.column, "Invalid value passed. Expected a number but found " + ms.type());
				}

			try {
				var millisec = (long) (double) ms.raw();
				Thread.sleep(millisec);
			} catch (InterruptedException ex) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Internal Error: " + ex.getMessage());
			}
				
				return new PiccodeUnit();
		});
		
	}
}
