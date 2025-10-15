package org.piccode.errors;

import org.piccode.errors.PiccodeInfo;
import com.github.tomaslanger.chalk.Chalk;
import org.piccode.backend.Compiler;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeSimpleNote implements PiccodeInfo {
	private String message;

	public PiccodeSimpleNote(String message) {
		this.message = message;
	}
	
	@Override
	public void report(boolean die, String kind) {
		Compiler.out.println(Chalk.on("[NOTE]: ").bold().yellow() + message);
	}
}
