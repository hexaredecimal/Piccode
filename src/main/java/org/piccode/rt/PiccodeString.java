
package org.piccode.rt;

import java.util.Objects;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeString implements PiccodeValue {
	private String string;

	public PiccodeString(String str) {
		this.string = str;
	}
	
	@Override
	public Object raw() {
		return string;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 47 * hash + Objects.hashCode(this.string);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PiccodeString other = (PiccodeString) obj;
		return Objects.equals(this.string, other.string);
	}

	@Override
	public String type() {
		return "String";
	}
}
