package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.text.AbstractDocument;
import org.piccode.ast.IdentifierAst;
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
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeVirtualModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("task", List.of("fx"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				
				var scope = ctx.getTopFrame();
				
				var fx = namedArgs.get("fx");
				PiccodeValue.verifyType(scope.caller, fx, Type.CLOSURE);

				var closure = (PiccodeClosure) fx;
				scope = closure.frame == null ? 
						scope
						: Context.getContextAt(closure.frame).getTopFrame();
				
				var id = Context.makeThread(closure);
				var obj = new HashMap<String, PiccodeValue>();
				obj.put("uuid", new PiccodeString(id));
				return new PiccodeObject(obj);
		}, null);
		
		NativeFunctionFactory.create("sleep", List.of("ms"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);

				var scope = ctx.getTopFrame();
				var ms = namedArgs.get("ms");
				PiccodeValue.verifyType(scope.caller, ms, Type.NUMBER);

			try {
				var millisec = (long) (double) ms.raw();
				Thread.sleep(millisec);
			} catch (InterruptedException ex) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Internal Error: " + ex.getMessage());
			}
				
				return new PiccodeUnit();
		}, null);
		
	}
}
