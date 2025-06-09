package org.piccode.ast;

import java.util.HashMap;
import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import static org.piccode.piccodescript.TargetEnvironment.JS;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeValue;

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

	@Override
	public PiccodeValue execute() {
		HashMap<String, PiccodeValue> obj = new HashMap<>();
		
		for (var kv: objs.entrySet()) {
			obj.put(kv.getKey(), kv.getValue().execute());
		}

		return new PiccodeObject(obj);
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		return switch (target) {
			case JS -> codeGenJsObject(target);
			default ->
				"todo";
		};
	}

	private String codeGenJsObject(TargetEnvironment env) {
		var sb = new StringBuilder()
			.append("{");

		var kvs = objs.entrySet();
		var size = kvs.size();
		var index  = 0;
		for (var kv: kvs) {
			sb
				.append(kv.getKey())
				.append(":")
				.append(sb);

			if (index < size - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
