package org.piccode.typechecker.type;

import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class BooleanType implements Type {
	public Ast node;

	public BooleanType(Ast node) {
		this.node = node;
	}

	@Override
	public String name() {
		return "Boolean";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}

	@Override
	public String toString() {
		return "Boolean";
	}
}
