package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class TupleAst extends Ast {
	public List<Ast> nodes; 

	public TupleAst(List<Ast> nodes) {
		this.nodes = nodes;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		for (int i = 0; i < nodes.size(); i++) {
			sb.append(nodes.get(i));
			if (i < nodes.size() - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var list = new ArrayList<PiccodeValue>();
		for (var node: nodes) {
			list.add(node.execute(frame));
		}
		return new PiccodeTuple(list);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

}
