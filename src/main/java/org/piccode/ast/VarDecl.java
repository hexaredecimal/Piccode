package org.piccode.ast;

import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeValue;

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

	@Override
	public PiccodeValue execute(Integer frame) {
		
		var ctx = frame == null
			? Context.top
			: Context.getContextAt(frame);

		
		ctx.pushStackFrame(this);
		var _value = value.execute(frame);
		ctx.dropStackFrame();
		ctx.putLocal(name, _value);
		return _value;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return String.format("let %s = %s;", name, value.codeGen(target));
	}
}
