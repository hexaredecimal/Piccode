package org.piccode.typechecker.type;

import org.piccode.ast.Ast;
import org.piccode.ast.types.TypeLValue;

/**
 *
 * @author hexaredecimal
 */
public interface Type {

	public abstract String name();
	public abstract Ast getDeclaringNode();

	public static Type of(String name, Ast node) {
		return switch (name) {
			case "Number" ->
				new NumberType(node);
			case "String" ->
				new StringType(node);
			case "Boolean" ->
				new BooleanType(node);
			default ->
				new UserType(node, name);
		};
	}

	public static boolean compare(Type left, Type right) {
		if (left instanceof NumberType && right instanceof NumberType) {
			return true;
		}

		if (left instanceof StringType && right instanceof StringType) {
			return true;
		}

		if (left instanceof BooleanType && right instanceof BooleanType) {
			return true;
		}

		if (left instanceof UserType lhs && right instanceof UserType rhs) {
			if (!lhs.name.equals(rhs.name)) {
				return false;
			}

			var expected = lhs.genericParams.size();
			var found = rhs.genericParams.size();
			if (expected != found) {
				return false;
			}

			for (int i = 0; i < expected; ++i) {
				if (!Type.compare(lhs.genericParams.get(i), rhs.genericParams.get(i))) return false;
			}

			return true;
		}

		if (left instanceof TupleType lhs && right instanceof TupleType rhs) {
			var leftCount = lhs.nodes.size();
			var rightCount = rhs.nodes.size();

			if (leftCount != rightCount) {
				return false;
			}
			for (int i = 0; i < leftCount; ++i) {
				var leftTop = lhs.nodes.get(i);
				var rightTop = rhs.nodes.get(i);

				if (!Type.compare(leftTop, rightTop)) {
					return false;
				}
			}

			return true;
		}

		if (left instanceof TypeAlias lhs && right instanceof TypeAlias rhs) {
			var leftCount = lhs.generics.size();
			var rightCount = rhs.generics.size();
			return lhs.name.equals(rhs.name)
							&& leftCount == rightCount
							&& Type.compare(lhs.other, rhs.other);
		}

		return false;
	}


}
