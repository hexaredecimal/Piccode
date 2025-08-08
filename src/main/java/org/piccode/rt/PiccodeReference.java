
package org.piccode.rt;

import java.util.Objects;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeReference implements PiccodeValue {
	public static final Type TYPE = Type.STRING;
	private Object ref;

	public PiccodeReference(Object ref) {
		this.ref = ref;
	}
	
	@Override
	public Object raw() {
		return this;
	}

	public Object deref() {
		return ref;
	}

	public void setRef(Object value) {
		this.ref = value;
	}
	@Override
	public String toString() {
		return "&Ref<" + ref + ">";
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 47 * hash + Objects.hashCode(this.ref);
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
		final PiccodeReference other = (PiccodeReference) obj;
		return Objects.equals(this.ref, other.ref);
	}

	@Override
	public String type() {
		return "String";
	}
}
