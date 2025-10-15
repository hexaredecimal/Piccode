package org.piccode.ast;

import java.util.HashMap;

/**
 *
 * @author hexaredecimal
 */
public class ObjectAst extends Ast {
	public HashMap<String, Ast> objs;

	public ObjectAst(HashMap<String, Ast> objs) {
		this.objs = objs;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		var nodes = objs.entrySet();
		var size = nodes.size(); 
		int i = 0;
		for (var kv: nodes) {
			var key = kv.getKey();
			var val = kv.getValue();
			sb.append(String.format("%s:%s", key, val));
			if (i < size - 1) {
				sb.append(", ");
			}
			i++;
		}
		sb.append("}");
		return sb.toString();
	}

}
