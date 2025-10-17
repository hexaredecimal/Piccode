package org.piccode.ast.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.ast.IdentifierAst;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.GenericType;
import org.piccode.typechecker.type.RecordType;
import org.piccode.typechecker.type.Type;
import org.piccode.typechecker.type.UserType;

/**
 *
 * @author hexaredecimal
 */
public class RecordTypeDeclaration extends Ast implements TypeCheckable {

	public Ast typeLValue;
	public List<StructureField> recordFields;

	public RecordTypeDeclaration(List<StructureField> reocrdFields) {
		this.recordFields = reocrdFields;
		this.typeLValue = null;
	}

	public RecordTypeDeclaration(Ast typeLVAst, List<StructureField> recordFields) {
		this.typeLValue = typeLVAst;
		this.recordFields = recordFields;
	}

	@Override
	public Type getType(Context ctx) {
		var fields = new HashMap<String, Type>();
		recordFields.forEach(field -> {
			var name = field.name;
			var type = field.getType(ctx);
			fields.put(name, type);
		});

		return new RecordType(typeLValue, fields, this);
	}

	public void typeCheck(Context ctx) {

		switch (typeLValue) {
			case IdentifierAst id ->
				typeCheckNonGenericRecord(id, ctx);
			case TypeLValue genericType ->
				typeCheckGenericRecord(genericType, ctx);
			default -> {
			}
		}
	}

	private void typeCheckNonGenericRecord(IdentifierAst id, Context ctx) {
		var typeTable = ctx.getTypeTable();
		var saved = typeTable.getValueForSymbol(id.text);

		if (saved != null) {
			var error = new PiccodeException(id.file, id.line, id.column, "Type with `" + id.text + "` already exists");
			var declNode = saved.getDeclaringNode();
			error.addNote(new PiccodeException(declNode.file, declNode.line, declNode.column, "The previous declarations is here"));
			throw error;
		}

		typeTable.pushScope();
		typeTable.putSymbol(id.text, new UserType(id, id.text));
		var bodyType = getType(ctx);
		typeTable.dropScope();

		typeTable.putSymbol(id.text, bodyType);
	}

	private void typeCheckGenericRecord(TypeLValue lvalue, Context ctx) {
		var typeTable = ctx.getTypeTable();
		var saved = typeTable.getValueForSymbol(lvalue.name);

		if (saved != null) {
			var error = new PiccodeException(lvalue.file, lvalue.line, lvalue.column, "Type with `" + lvalue.name + "` already exists");
			var declNode = saved.getDeclaringNode();
			error.addNote(new PiccodeException(declNode.file, declNode.line, declNode.column, "The previous declarations is here"));
			throw error;
		}

		typeTable.pushScope();
		var generics = new ArrayList<Type>();
		lvalue.genericTypes.forEach(generic -> generics.add(verifyAndAdd(generic, ctx)));
		typeTable.putSymbol(lvalue.name, new UserType(lvalue, lvalue.name, generics));
		
		var bodyType = getType(ctx);
		typeTable.dropScope();
		typeTable.putSymbol(lvalue.name, bodyType);
	}

	private Type verifyAndAdd(Ast generic, Context ctx) {
		var typeTable = ctx.getTypeTable();
		if (generic instanceof IdentifierAst id) {
			var saved = typeTable.getValueForSymbol(id.text);
			if (saved != null) {
				var error = new PiccodeException(id.file, id.line, id.column, "Type `" + id.text + "` already exists");
				var declNode = saved.getDeclaringNode();
				error.addNote(new PiccodeException(declNode.file, declNode.line, declNode.column, "The previous declarations is here"));
				throw error;
			}
			var result = new GenericType(generic, id.text);
			typeTable.putSymbol(id.text, result);
			return result;
		}

		throw new PiccodeException(generic.file, generic.line, generic.column, "Nested generic parameters are not allowed");
	}

}
