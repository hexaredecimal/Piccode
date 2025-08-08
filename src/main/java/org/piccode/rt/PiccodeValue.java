package org.piccode.rt;

import com.github.tomaslanger.chalk.Chalk;
import java.util.ArrayList;
import java.util.List;
import org.piccode.ast.Ast;

/**
 *
 * @author hexaredecimal
 */
public interface PiccodeValue {
	public Object raw();
	public String type();

	public static void verifyType(Ast caller, PiccodeValue value, Type type) {
		var typeOfValue = Type.of(value);
		if (typeOfValue != type) {
			throw new PiccodeException(
				caller.file, 
				caller.line, 
				caller.column, 
				"Invalid type. Expected type " + Chalk.on(type.toString()).green() 
					+ " but got " + Chalk.on(typeOfValue.toString()).red()
			);
		}
	}

	public static PiccodeValue error(PiccodeValue value) {
		var nodes = new ArrayList<PiccodeValue>();
		nodes.addFirst(new PiccodeUnit());
		nodes.addLast(value);
		return new PiccodeTuple(nodes);
	}
	
	public static PiccodeValue success(PiccodeValue value) {
		var nodes = new ArrayList<PiccodeValue>();
		nodes.addFirst(value);
		nodes.addLast(new PiccodeUnit());
		return new PiccodeTuple(nodes);
	}
	
	public static PiccodeValue error(String message) {
		var nodes = new ArrayList<PiccodeValue>();
		nodes.addFirst(new PiccodeUnit());
		nodes.addLast(new PiccodeString(message));
		return new PiccodeTuple(nodes);
	}

	public static enum Type {
		NUMBER, 
		STRING,
		BOOLEAN,
		ARRAY,
		TYPLE,
		OBJECT,
		REFERENCE,
		CLOSURE,
		MODULE,
		UNIT,
		VALUE,
		NATIVEFUNCTION;

		public static Type of(PiccodeValue value) {
			if (value instanceof PiccodeNumber ) return NUMBER;
			if (value instanceof PiccodeString ) return STRING;
			if (value instanceof PiccodeBoolean) return BOOLEAN;
			if (value instanceof PiccodeObject ) return OBJECT;
			if (value instanceof PiccodeTuple  ) return TYPLE;
			if (value instanceof PiccodeArray  ) return ARRAY;
			if (value instanceof PiccodeClosure) return CLOSURE;
			if (value instanceof PiccodeUnit   ) return UNIT;
			if (value instanceof PiccodeReference) return REFERENCE;
			if (value instanceof NativeFunction) return NATIVEFUNCTION;

			System.out.println(value + " : " + value.getClass());
			return VALUE;
		}
	}
}
