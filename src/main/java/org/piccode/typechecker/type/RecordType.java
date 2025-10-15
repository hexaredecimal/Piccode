package org.piccode.typechecker.type;

import java.util.HashMap;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public class RecordType implements Type {
	public Ast node;
	public HashMap<String, Type> fields;

	public RecordType(Ast node, HashMap<String, Type> fields) {
		this.node = node;
		this.fields = fields;
	}

	@Override
	public String name() {
		return "Record";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}
}
