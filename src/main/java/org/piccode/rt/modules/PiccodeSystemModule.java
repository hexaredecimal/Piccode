package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.PiccodeUnit;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeSystemModule {
	public static void addFunctions() {
		// TODO: Fix error messages and show line numbers
		// Print the stacktrace of the program when the program panics
		NativeFunctionFactory.create("panic", List.of("message"), (args, namedArgs) -> {
				var msg = namedArgs.get("message").toString();
				System.out.println("Panic: " + msg);
				System.exit(1);
				return new PiccodeUnit();
		});

		NativeFunctionFactory.create("todo", List.of("message"), (args, namedArgs) -> {
				var msg = namedArgs.get("message").toString();
				System.out.println("TODO: " + msg);
				System.exit(1);
				return new PiccodeUnit();
		});

		NativeFunctionFactory.create("unreachable", List.of("message"), (args, namedArgs) -> {
				var msg = namedArgs.get("message").toString();
				System.out.println("UNREACHABLE: " + msg);
				System.exit(1);
				return new PiccodeUnit();
		});
	}
}
