package org.piccode.rt;

import java.util.List;
import java.util.Objects;
import org.piccode.ast.Ast;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeModule implements PiccodeValue{
	
	public String name; 
	public List<Ast> nodes; 

	public PiccodeModule(String name, List<Ast> nodes) {
		this.name = name;
		this.nodes = nodes;
	}
	
	@Override
	public Object raw() {
		return name;
	}

	@Override
	public String toString() {
		return "module " + name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + Objects.hashCode(this.name);
		hash = 37 * hash + Objects.hashCode(this.nodes);
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
		final PiccodeModule other = (PiccodeModule) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		return Objects.equals(this.nodes, other.nodes);
	}

	@Override
	public String type() {
		return "Module";
	}
	
}
