package org.piccode.typechecker.type;

import org.piccode.ast.Ast;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;

/**
 *
 * @author hexaredecimal
 */
public class GenericType implements Type {
	public Ast node;
	public String name;

	public GenericType(Ast node, String name) {
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

	@Override
	public String toString() {
		return "^" + name;
	}

	public Type specialize(Context ctx) {
		var typeTable = ctx.getTypeTable();

		var saved = typeTable.getValueForSymbol(name);
		if (saved == null) {
			throw new PiccodeException(node.file, node.line, node.column, "Cannot generalize type `" + name + "`. Cannot find approprate type");
		}

		return saved;
	}
	
}
