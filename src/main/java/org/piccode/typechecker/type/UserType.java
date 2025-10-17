package org.piccode.typechecker.type;

import java.util.ArrayList;
import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;

/**
 *
 * @author hexaredecimal
 */
public class UserType implements Type {

	public Ast node;
	public String name;
	public List<Type> genericParams;

	public UserType(Ast node, String name) {
		this.node = node;
		this.name = name;
	}

	public UserType(Ast node, String name, List<Type> genericParams) {
		this.node = node;
		this.name = name;
		this.genericParams = genericParams;
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
		int size = genericParams.size();

		sb.append(name);

		if (!genericParams.isEmpty()) {
			sb.append("<");
			for (int i = 0; i < size; ++i) {
				var top = genericParams.get(i);
				sb.append(top);
				if (i < size - 1) {
					sb.append(", ");
				}
			}
			sb.append(">");
		}

		return sb.toString();
	}

	public Type specialize(Context ctx) {
		if (genericParams.isEmpty()) 
			throw new PiccodeException(node.file, node.line, node.column, "Cannot specialize on a type that does not take generic parameters");

		var typeTable = ctx.getTypeTable();
		var generics = new ArrayList<Type>();
		for (var type: genericParams) {
			switch (type) {
				case UserType uType -> generics.add(uType.specialize(ctx));
				case GenericType genType -> {
					var saved = typeTable.getValueForSymbol(genType.name);
					if (saved == null) {
						throw new PiccodeException(genType.node.file, genType.node.line, genType.node.column, "Cannot specialize generic type `" + genType.name + "`.");
					}
					generics.add(saved);
				}
				default -> generics.add(type);
			}
		}

		return new UserType(node, name, generics);
	}
}
