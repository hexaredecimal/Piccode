package org.piccode.typechecker.type;

import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class UserType implements Type {
	public Ast node;
	public String name;

	public UserType(Ast node, String name) {
		this.node = node;
		this.name = name;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}

}
