package org.piccode.ast;

import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class NamedCallArg implements Ast {
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

	@Override
	public PiccodeValue execute() {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
	
}
