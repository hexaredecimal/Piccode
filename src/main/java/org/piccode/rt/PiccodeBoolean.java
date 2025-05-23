
package org.piccode.rt;

import java.util.Objects;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeBoolean implements PiccodeValue {
	private String value;

	public PiccodeBoolean(String number) {
		this.value = number;
	}
	
	@Override
	public Object raw() {
		return (value.equals("true"));
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.value);
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
		final PiccodeBoolean other = (PiccodeBoolean) obj;
		return Objects.equals(this.value, other.value);
	}
	
	
}
