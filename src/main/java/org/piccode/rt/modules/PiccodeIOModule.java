package org.piccode.rt.modules;

import java.util.List;
import javax.swing.JOptionPane;
import org.piccode.backend.Compiler;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeIOModule {
	
	public static void addFunctions() {
		NativeFunctionFactory.create("print", List.of("value"), (args, namedArgs, frame) -> {
			var value = namedArgs.get("value").toString();
			Compiler.out.print(value);
			return new PiccodeUnit();
		}, null);
		
		NativeFunctionFactory.create("read", List.of("msg"), (args, namedArgs, frame) -> {
			var ctx = frame == null ? 
					Context.top
					: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			
			var value = namedArgs.get("msg");
			PiccodeValue.verifyType(caller, value, Type.STRING);

			var result = JOptionPane.showInputDialog(value);
			if (result == null) {
				return new PiccodeString("No input provided");
			}
			return new PiccodeString(result);
		}, null);
		
	}
	
}
