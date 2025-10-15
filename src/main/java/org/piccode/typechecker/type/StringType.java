package org.piccode.typechecker.type;

import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class StringType implements Type {
	public Ast node;

	public StringType(Ast node) {
		this.node = node;
	}

	@Override
	public String name() {
		return "String";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}
}
