package org.piccode.typechecker.type;

import java.util.List;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class TupleType implements Type {
	public Ast node;
	public List<Type> nodes;

	public TupleType(Ast node, List<Type> nodes) {
		this.node = node;
		this.nodes = nodes;
	}

	@Override
	public String name() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (var i = 0; i < nodes.size(); ++i) {
			var top = nodes.get(i);
			sb.append(top.name());
			if (i < nodes.size() - 1) {
				sb.append(",");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}

}
