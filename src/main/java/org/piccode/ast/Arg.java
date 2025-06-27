package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class Arg extends Ast {
	public String name;
	public Ast def_val;

	public Arg(String name, Ast def_val) {
		this.name = name;
		this.def_val = def_val;
	}

	public Arg(String name) {
		this.name = name;
		this.def_val = null;
	}

	@Override
	public String toString() {
		if (def_val == null) {
			return name;
		}
		return name  + "=" + def_val;
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
	
}
