
package org.piccode.rt;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeUnit implements PiccodeValue {
	@Override
	public Object raw() {
		return Void.TYPE;
	}

	@Override
	public String toString() {
		return "()";
	}
	
	
}
