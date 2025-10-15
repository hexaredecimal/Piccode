package org.piccode.typechecker.type;

import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class NumberType implements Type {
	public Ast node;

	public NumberType(Ast node) {
		this.node = node;
	}

	@Override
	public String name() {
		return "Number";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}
}
