package org.piccode.rt.modules;

import java.util.List;
import javax.swing.JOptionPane;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeUnit;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeIOModule {
	
	public static void addFunctions() {
		NativeFunctionFactory.create("print", List.of("value"), (args, namedArgs) -> {
			var value = namedArgs.get("value").toString();
			System.out.print(value);
			return new PiccodeUnit();
		});
		
		NativeFunctionFactory.create("read", List.of("msg"), (args, namedArgs) -> {
			var value = namedArgs.get("msg");
			var result = JOptionPane.showInputDialog(value);
			if (result == null) {
				return new PiccodeString("No input provided");
			}
			return new PiccodeString(result);
		});
		
	}
	
}
