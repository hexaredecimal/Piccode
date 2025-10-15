package org.piccode.ast.types;

import java.util.HashMap;
import java.util.List;
import org.piccode.ast.Ast;
import org.piccode.typechecker.Context;
import org.piccode.typechecker.TypeCheckable;
import org.piccode.typechecker.type.RecordType;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class RecordTypeDeclaration extends Ast implements TypeCheckable {
	public List<StructureField> recordFields; 

	public RecordTypeDeclaration(List<StructureField> reocrdFields) {
		this.recordFields = reocrdFields;
	}

	@Override
	public Type getType(Context ctx) {
		var fields = new HashMap<String, Type>();
		recordFields.forEach(field -> {
			var name = field.name;
			var type = field.getType(ctx);
			fields.put(name, type);
		});

		return new RecordType(this, fields);
	}
}
