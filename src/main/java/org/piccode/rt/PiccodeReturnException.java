
package org.piccode.rt;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeReturnException extends RuntimeException {
	public PiccodeValue value;

	public PiccodeReturnException(PiccodeValue value) {
		this.value = value;
	}
}
