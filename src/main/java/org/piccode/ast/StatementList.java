package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class StatementList extends Ast {
	public List<Ast> nodes; 

	public StatementList(List<Ast> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		PiccodeValue result = null;
		for (var stmt: nodes) {
			result = stmt.execute(frame);
		}
		return result;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		var sb = new StringBuilder();
		for (var node: nodes) {
			sb.append(node.codeGen(target)).append("\n");
		}

		return sb.toString();
	}

}
