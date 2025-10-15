package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.FunctionType;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class FunctionTypeDeclAst extends Ast implements TypeCheckable {

	public String name;
	public List<Ast> paramTypes;
	public Ast returnType;

	public FunctionTypeDeclAst(String name, List<Ast> paramTypes, Ast returnType) {
		this.name = name;
		this.paramTypes = paramTypes;
		this.returnType = returnType;
	}

	@Override
	public Type getType(Context ctx) {
		var typeTable = ctx.getTypeTable();
		var params = new ArrayList<Type>();

		paramTypes.forEach(paramType -> {
			if (paramType instanceof IdentifierAst id) {
				var saved = typeTable.getValueForSymbol(id.text);
				if (saved == null) {
					throw new PiccodeException(id.file, id.line, id.column, "Unknown type `" + id.text + "` used in function signature of `" + name + "`");
				}
				params.add(saved);
				return;
			}

			var typedNode = (TypeCheckable) paramType;
			var type = typedNode.getType(ctx);
			params.add(type);
		});

		

		Type retType = null;
		if (returnType instanceof IdentifierAst id) {
			var saved = typeTable.getValueForSymbol(id.text);
			if (saved == null) {
				throw new PiccodeException(id.file, id.line, id.column, "Unknown type `" + id.text + "` used as a return type for function `" + name + "`");
			}
			retType = saved;
		} else {
			var typedNode = (TypeCheckable) returnType;
			retType = typedNode.getType(ctx);
		}


		var functionType = new FunctionType(name, params, retType, this);
		var functionTable = ctx.getFunctionTable();
		
		var prevDeclaration = functionTable.getValueForSymbol(name);
		if (prevDeclaration != null) {
			var error = new PiccodeException(file, line, column, "Function `" + name + "` is already declared");
			var node = prevDeclaration.getDeclaringNode();
			error.addNote(new PiccodeException(node.file, node.line, node.column, "Function `" + name + "` was previously declared here"));
			throw error;
		}

		functionTable.putSymbol(name, functionType);
		return functionType;
	}
}
