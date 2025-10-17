package org.piccode.typechecker.type;

import java.util.List;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class TypeAlias implements Type {

	public String name;
	public Type other;
	public Ast node;
	public List<Type> generics;

	// type Name = Other
	public TypeAlias(String name, Type other, Ast node) {
		this.name = name;
		this.other = other;
		this.node = node;
	}

	// type Name<A, ..> = Other (<A, ..>)?
	public TypeAlias(String name, Type other, Ast node, List<Type> generics) {
		this.name = name;
		this.other = other;
		this.node = node;
		this.generics = generics;
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
		var sb = new StringBuilder();
		sb.append(name);

		if (generics.isEmpty()) {
			sb.append("<");
			int size = generics.size();
			for (int i = 0; i < size; ++i) {
				sb.append(generics.get(i));
				if (i < size - 1) {
					sb.append(" -> ");
				}
			}
			sb.append(">");
		}

		sb.append(":: ").append(other);
		return sb.toString();
	}

}
