package org.piccode.typechecker.type;

import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class UnitType implements Type {
	public Ast node;

	public UnitType(Ast node) {
		this.node = node;
	}

	@Override
	public String name() {
		return "Unit";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}

	@Override
	public String toString() {
		return "Unit";
	}
	
}
