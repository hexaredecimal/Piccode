package org.piccode.ast;

import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.StringType;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class StringAst extends Ast implements TypeCheckable {
	public String text; 

	public StringAst(String text) {
		this.text = text.substring(1, text.length() - 1);
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public Type getType(Context ctx) {
		return new StringType(this);
	}
}
