package org.piccode.typechecker.type;

import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.ast.FunctionAst;

/**
 *
 * @author hexaredecimal
 */
public class FunctionType implements Type {
	public String name;
	public List<Type> args;
	public Type returnType;
	public Ast node;
	public FunctionAst function = null;

	public FunctionType(String name, List<Type> args, Type returnType, Ast node) {
		this.name = name;
		this.args = args;
		this.returnType = returnType;
		this.node = node;
	}

	@Override
	public String name() {
		return name + " :: -> " + returnType.name();
	}

	public String magle() {
		var sb = new StringBuilder();
		sb.append(name);
		for (var arg: args) {
			sb.append(arg.name()).append("_");
		}
		sb.append("_x_");
		sb.append(returnType);
		return sb.toString();
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}
}
