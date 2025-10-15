package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class NamedCallArg extends Ast {
	public String name;
	public Ast value;

	public NamedCallArg(String name, Ast def_val) {
		this.name = name;
		this.value = def_val;
	}

	@Override
	public String toString() {
		if (value == null) {
			return name;
		}
		return name  + "=" + value;
	}
	
}
