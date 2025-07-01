
package org.piccode.rt;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeArray implements PiccodeValue {
	public List<PiccodeValue> nodes;

	public PiccodeArray(List<PiccodeValue> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");

		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public List<PiccodeValue> getList() {
		return nodes;
	}

	public PiccodeValue[] array() {
		return nodes.toArray(PiccodeValue[]::new);
	}
	
	@Override
	public Object raw() {
		return array();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + Objects.hashCode(this.nodes);
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
		final PiccodeArray other = (PiccodeArray) obj;
		return Objects.equals(this.nodes, other.nodes);
	}

	@Override
	public String type() {
		return "Array.[" + this.nodes.size() + "]";
	}
}
