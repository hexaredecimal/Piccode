package org.piccode.ast.types;

import java.util.ArrayList;
import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.ast.IdentifierAst;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.Type;
import org.piccode.typechecker.type.UserType;

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
		var savedType = typeTable.getValueForSymbol(name);

		if (savedType == null) {
			throw new PiccodeException(file, line, column, "Usage of undeclared type `" + name + "`");
		}

		if (genericTypes.isEmpty()) {
			return savedType;
		}

		if (savedType instanceof UserType userType) {
			var expected = userType.genericParams.size();
			var found = genericTypes.size();
			if (found != expected) {
				var error = new PiccodeException(file, line, column, "Invalid number of generic parameters passed to type `" + name + "`. Expected " + expected + " but found " + found + " ");
				var declNode = userType.getDeclaringNode();
				error.addNote(new PiccodeException(declNode.file, declNode.line, declNode.column, "The generic type is declared here"));
				throw error;
			}

			genericTypes.forEach(genericType -> verify(genericType, ctx));
		}

		return savedType;
	}


	private void verify(Ast generic, Context ctx) {
		var typeTable = ctx.getTypeTable();
		if (generic instanceof IdentifierAst id) {
			var saved = typeTable.getValueForSymbol(id.text);
			if (saved == null) {
				throw new PiccodeException(id.file, id.line, id.column, "Type `" + id.text + "` does not exists");
			}
			return;
		}

		var typedNode = (TypeCheckable) generic;
		typedNode.getType(ctx);
	}

}
