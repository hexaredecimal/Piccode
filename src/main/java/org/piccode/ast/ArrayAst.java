package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ArrayAst extends Ast {
	public List<Ast> nodes; 

	public ArrayAst(List<Ast> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var list = new ArrayList<PiccodeValue>();
		for (var node: nodes) {
			list.add(node.execute(frame));
		}
		return new PiccodeArray(list);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS -> codeGenJsArray(target);
			default -> "todo";
		};
	}

	private String codeGenJsArray(TargetEnvironment env) {
		var sb = new StringBuilder()
			.append("[");

		nodes.forEach((node) -> {
			sb.append(node.codeGen(env));
		});

		sb.append(']');
		return sb.toString();
	}

}
