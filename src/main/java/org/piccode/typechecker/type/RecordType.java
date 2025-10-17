package org.piccode.typechecker.type;

import java.util.HashMap;
import org.piccode.ast.Ast;
import org.piccode.ast.IdentifierAst;
import org.piccode.ast.types.TypeLValue;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;

/**
 *
 * @author hexaredecimal
 */
public class RecordType implements Type {

	public Ast typeLvalue;
	public HashMap<String, Type> fields;
	public Ast node;

	public RecordType(Ast typeLvalue, HashMap<String, Type> fields, Ast node) {
		this.typeLvalue = typeLvalue;
		this.fields = fields;
		this.node = node;
	}

	@Override
	public String name() {
		return "Record";
	}

	@Override
	public Ast getDeclaringNode() {
		return node;
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();

		switch (typeLvalue) {
			case IdentifierAst id ->
				sb.append(id.text);
			case TypeLValue lvalue -> {
				var name = lvalue.name;
				sb.append(name);

				var generics = lvalue.genericTypes;

				if (generics.isEmpty()) {
					sb.append("<");
					int size = generics.size();
					for (int i = 0; i < size; ++i) {
						sb.append(generics.get(i));
						if (i < size - 1) {
							sb.append(" -> ");
						}
					}
					sb.append(">");
				}
			}
			default -> {}
		}

		sb.append(" ").append("{");

		var entries = fields.entrySet();
		var size = entries.size();
		int count = 0;
		for (var kv: entries) {
			sb.append(kv.getKey()).append(": ").append(kv.getValue());
			if (count < size - 1) 
				sb.append(", ");

			count++;
		}

		sb.append("}");
		return sb.toString();
	}

	public Type specialize(Context ctx, TypeLValue lvalue) {
		if (lvalue.genericTypes.isEmpty()) {
		}

		var expected = switch (typeLvalue) {
			case TypeLValue lv -> lv.genericTypes.size();
			default -> 0;
		};

		var found = lvalue.genericTypes.size();

		if (expected == 0) {
			throw new PiccodeException(lvalue.file, lvalue.line, lvalue.column, "Cannot specialize generics for a non generic type");
		}
		

		if (expected != found) {
			var error = new PiccodeException(lvalue.file, lvalue.line, lvalue.column, "Invalid number of generic types. Expected " + expected + " but found " + found);
			error.addNote(new PiccodeException(node.file, node.line, node.column, "The generic type was previously declared here"));
			throw error;
		}
		
		TypeLValue declNode = (TypeLValue) typeLvalue;
		
		//System.out.println(declNode.name + ": " + declNode.genericTypes);

		var typeTable = ctx.getTypeTable();
		typeTable.pushScope();
		for (int i = 0; i < expected; ++i){
			var typeId = (IdentifierAst) declNode.genericTypes.get(i);
			var applyType = lvalue.genericTypes.get(i);
			
			if (applyType instanceof IdentifierAst id) {
				var saved = typeTable.getValueForSymbol(id.text);
				if (saved == null) {
					throw new PiccodeException(id.file, id.line, id.column, "Type `" + id.text + " is not defined");
				}

				typeTable.putSymbol(typeId.text, saved);
			} 
		}

		var newFields = new HashMap<String, Type>();
		
		for (var kv: fields.entrySet()) {
			var fieldName = kv.getKey();
			var oldType = kv.getValue();

			var type = switch (oldType) {
				case GenericType genericType -> genericType.specialize(ctx);
				case UserType userType -> userType.specialize(ctx);
				default -> oldType;
			};

			newFields.put(fieldName, type);
		}
		
		typeTable.dropScope();
		
		return new RecordType(typeLvalue, newFields, node);
	}

}
