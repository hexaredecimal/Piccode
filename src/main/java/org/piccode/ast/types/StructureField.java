package org.piccode.ast.types;

import org.piccode.ast.Ast;
import org.piccode.ast.IdentifierAst;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class StructureField extends Ast implements TypeCheckable{

	public String name; 
	public Ast type;

	public StructureField(String name, Ast type) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Type getType(Context ctx) {
		var typeTable = ctx.getTypeTable();
		if (type instanceof IdentifierAst id) {
			var declaredType = typeTable.getValueForSymbol(id.text);
			if (declaredType == null) {
				throw new PiccodeException(id.file, id.line, id.column, "Undeclared type `" + id.text + "` in the declaration for field `" + name + "`");
			}

			return declaredType;
		}

		var typedNode = (TypeCheckable) type;
		return typedNode.getType(ctx);
	}

}
