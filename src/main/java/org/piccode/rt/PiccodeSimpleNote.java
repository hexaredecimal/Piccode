package org.piccode.rt;

import com.github.tomaslanger.chalk.Chalk;

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
	public void reportError(boolean die, String kind) {
		System.out.println(Chalk.on("[NOTE]: ").bold().yellow() + message);
	}
}
