package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
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
	public PiccodeValue execute() {
		var list = new ArrayList<PiccodeValue>();
		for (var node: nodes) {
			list.add(node.execute());
		}
		return new PiccodeArray(list);
	}

}
