package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeFileModule {
	public static void addFunctions() {
		
		NativeFunctionFactory.create("new", List.of("path"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);

				var scope = ctx.getTopFrame();
				var ms = namedArgs.get("ms");
				PiccodeValue.verifyType(scope.caller, ms, Type.NUMBER);
				var path = namedArgs.get("path");
				return new PiccodeNumber("" + path);
		}, null);
		
		
	}
}
