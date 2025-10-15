package org.piccode.ast.types;

import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class TypeLValue extends Ast implements TypeCheckable {

	public String name;
	public List<Ast> genericTypes;

	public TypeLValue(String name, List<Ast> genericTypes) {
		this.name = name;
		this.genericTypes = genericTypes;
	}

	@Override
	public Type getType(Context ctx) {
		var typeTable = ctx.getTypeTable();
		
		if (genericTypes.isEmpty()) {
			var savedType = typeTable.getValueForSymbol(name);
			if (savedType == null) {
				throw new PiccodeException(file, line, column, "Usage of undeclared type `" + name + "`");
			}
			return savedType;
		}

		throw new PiccodeException(file, line, column, "TODO: Implement generic types");
	}
}
