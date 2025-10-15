package org.piccode.ast.types;

import org.piccode.ast.Ast;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;
import org.piccode.typechecker.type.UnitType;

/**
 *
 * @author hexaredecimal
 */
public class UnitTypeDeclaration extends Ast implements TypeCheckable {

	@Override
	public Type getType(Context ctx) {
		return new UnitType(this);
	}
}
