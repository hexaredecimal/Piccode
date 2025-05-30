
package org.piccode.rt;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeObject implements PiccodeValue {
	public HashMap<String, PiccodeValue> obj;

	public PiccodeObject(HashMap<String, PiccodeValue> obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");

		var nodes = obj.entrySet();
		var size = nodes.size();
		
		int i = 0;
		for (var node: nodes) {
			sb.append(node.getKey()).append(": ").append(node.getValue());
			if (i < size - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append("}");
		return sb.toString();
	}

	public PiccodeValue getValue(String key) {
		return obj.get(key);
	}

	@Override
	public Object raw() {
		return obj;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 17 * hash + Objects.hashCode(this.obj);
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
		final PiccodeObject other = (PiccodeObject) obj;
		return Objects.equals(this.obj, other.obj);
	}

	@Override
	public String type() {
		return "Object";
	}
}
