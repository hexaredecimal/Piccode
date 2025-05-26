package org.piccode.ast;

import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class IfExpression extends Ast{
	public Ast cond, then, elze;

	public IfExpression(Ast cond, Ast then, Ast elze) {
		this.cond = cond;
		this.then = then;
		this.elze = elze;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("if ").append(cond).append(" {\n");
		sb.append(then.toString().indent(4));
		sb.append(" else {\n");
		sb.append(elze.toString().indent(4));
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PiccodeValue execute() {
		var _cond = cond.execute().raw().toString();
		if (_cond.equals("true")) {
			return then.execute();
		} 

		return elze.execute();
	}
	
	/*
import pkg:io

function fact(x=1) = 
  if x <= 1 { 1 }
  else x * fact(x - 1)

IO.println(1 + 2 * 2)
	*/
}
