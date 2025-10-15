package org.piccode.ast;

/**
 *
 * @author hexaredecimal
 */
public class VarDecl extends Ast {
	public String name; 
	public Ast value;

	public VarDecl(String name, Ast value) {
		this.name = name;
		this.value = value;
	}


	@Override
	public String toString() {
		return name + " := " + value;
	}

}
