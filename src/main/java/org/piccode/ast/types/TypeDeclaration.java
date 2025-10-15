package org.piccode.ast.types;

import org.piccode.ast.Ast;
import org.piccode.ast.IdentifierAst;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.TypeAlias;
/**
 *
 * @author hexaredecimal
 */
public class TypeDeclaration extends Ast {
	public Ast typeLValue;
	public Ast typeRValue;

	public TypeDeclaration(Ast typeLValue, Ast typeRValue) {
		this.typeLValue = typeLValue;
		this.typeRValue = typeRValue;
	}

	public void typeCheck(Context ctx) {
		switch (typeLValue) {
			case IdentifierAst id -> typeCheckNonGenericType(id, ctx);
			case TypeLValue lv -> typeCheckGenericType(lv, ctx);
			default -> {
			}
		}
	}

	private void typeCheckNonGenericType(IdentifierAst id, Context ctx) {
		var typeTable = ctx.getTypeTable();
		var saved = typeTable.getValueForSymbol(id.text);
		if (saved != null) {
			var error = new PiccodeException(id.file, id.line, id.column, "Type `" + id.text + "` is already declared. ");
			
			var declaringNode = saved.getDeclaringNode();
			var note = new PiccodeException(declaringNode.file, declaringNode.line, declaringNode.column, "The type was previosly declared here");
			error.addNote(note);
			throw error;
		}

		if (typeRValue instanceof IdentifierAst rhsId) {
			var declaredType = typeTable.getValueForSymbol(rhsId.text);
			if (declaredType == null) {
				throw new PiccodeException(rhsId.file, rhsId.line, rhsId.column, "Type `" + rhsId.text + "` is not defined`");
			}
			typeTable.putSymbol(id.text, new TypeAlias(id.text, declaredType, id));
			return;
		}
		
		var typedNode = (TypeCheckable) typeRValue;
		typeTable.putSymbol(id.text, typedNode.getType(ctx));
	}

	private void typeCheckGenericType(TypeLValue lv, Context ctx) {
		throw new PiccodeException(file, line, column, "TODO: implement generic types :)");
			
	}

	
}
