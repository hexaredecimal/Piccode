package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class Arg extends Ast {
	public String name;
	public Ast def_val;
	public boolean export;

	public Arg(String name, Ast def_val) {
		this.name = name;
		this.def_val = def_val;
		this.export = false;
	}

	public Arg(String name) {
		this.name = name;
		this.def_val = null;
		this.export = false;
	}

	@Override
	public String toString() {
		if (def_val == null) {
			return name;
		}
		return name  + "=" + def_val;
	}

}
