package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeSystemModule {
	public static void addFunctions() {
		// TODO: Fix error messages and show line numbers
		// Print the stacktrace of the program when the program panics
		NativeFunctionFactory.create("panic", List.of("message"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var msg = namedArgs.get("message");
				PiccodeValue.verifyType(caller, msg, Type.STRING);
				var message = msg.raw().toString();
				System.out.println("Panic: " + message);
				System.exit(1);
				return new PiccodeUnit();
		}, null);

		NativeFunctionFactory.create("todo", List.of("message"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var msg = namedArgs.get("message");
				PiccodeValue.verifyType(caller, msg, Type.STRING);
				var message = msg.raw().toString();
				System.out.println("TODO: " + message);
				System.exit(1);
				return new PiccodeUnit();
		}, null);

		NativeFunctionFactory.create("unreachable", List.of("message"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var msg = namedArgs.get("message");
				PiccodeValue.verifyType(caller, msg, Type.STRING);
				var message = msg.raw().toString();
				System.out.println("UNREACHABLE: " + message);
				System.exit(1);
				return new PiccodeUnit();
		}, null);
	}
}
