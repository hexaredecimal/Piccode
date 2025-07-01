package org.piccode.rt.modules;

import com.github.tomaslanger.chalk.Chalk;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeFileModule {

	private static PiccodeValue makeFile(File fp) {
		var obj = new HashMap<String, PiccodeValue>();
		obj.put("hash", new PiccodeNumber(fp.hashCode()));
		obj.put("path", new PiccodeString(fp.getPath()));
		obj.put("parent", new PiccodeString(fp.getParent()));
		obj.put("exists", new PiccodeBoolean(fp.exists()));
		obj.put("isFile", new PiccodeBoolean(fp.isFile()));
		obj.put("hidden", new PiccodeBoolean(fp.isHidden()));
		return new PiccodeObject(obj);
	}

	public static void addFunctions() {

		NativeFunctionFactory.create("filenew", List.of("path"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var _path = namedArgs.get("path");
			PiccodeValue.verifyType(scope.caller, _path, Type.STRING);

			var path = _path.raw().toString();
			var fp = new File(path);
			Context.allocate(fp);

			return makeFile(fp);
		}, null);

	
		NativeFunctionFactory.create("create", List.of("object"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var obj = namedArgs.get("object");
			PiccodeValue.verifyType(scope.caller, obj , Type.OBJECT);

			var object = (PiccodeObject) obj;
			var hash = object.obj.get("hash");
			if (hash == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Value is not a native object");
			}

			if (!(hash instanceof PiccodeNumber)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Invalid native object. Hash code is not a number, found " + Chalk.on(hash.type()).red());
			}
			
			var allocatedObject = Context.getObject((int) (double) hash.raw());
			if (allocatedObject == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("No object is allocated at 0x%04d", hash.raw()));
			}

			if (!(allocatedObject instanceof File)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a File", hash.raw()));
			}

			var file = (File) allocatedObject;
			try {
				file.createNewFile();
				return makeFile(file);
			} catch (IOException ex) {
				return obj;
			}
		}, null);
	
		NativeFunctionFactory.create("delete", List.of("object"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var obj = namedArgs.get("object");
			PiccodeValue.verifyType(scope.caller, obj , Type.OBJECT);

			var object = (PiccodeObject) obj;
			var hash = object.obj.get("hash");
			if (hash == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Value is not a native object");
			}

			if (!(hash instanceof PiccodeNumber)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Invalid native object. Hash code is not a number, found " + Chalk.on(hash.type()).red());
			}
			
			var allocatedObject = Context.getObject((int) (double) hash.raw());
			if (allocatedObject == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("No object is allocated at 0x%04d", hash.raw()));
			}

			if (!(allocatedObject instanceof File)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a File", hash.raw()));
			}

			var file = (File) allocatedObject;
			file.delete();
			return makeFile(file);
		}, null);
	
		NativeFunctionFactory.create("listfiles", List.of("object"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var obj = namedArgs.get("object");
			PiccodeValue.verifyType(scope.caller, obj , Type.OBJECT);

			var object = (PiccodeObject) obj;
			var hash = object.obj.get("hash");
			if (hash == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Value is not a native object");
			}

			if (!(hash instanceof PiccodeNumber)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Invalid native object. Hash code is not a number, found " + Chalk.on(hash.type()).red());
			}
			
			var allocatedObject = Context.getObject((int) (double) hash.raw());
			if (allocatedObject == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("No object is allocated at 0x%04d", hash.raw()));
			}

			if (!(allocatedObject instanceof File)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a File", hash.raw()));
			}

			var elements = new ArrayList<PiccodeValue>();
			var file = (File) allocatedObject;
			for (var fp: file.listFiles()) {
				var _file = makeFile(fp);
				elements.add(_file);
			}
			return new PiccodeArray(elements);
		}, null);
	
		NativeFunctionFactory.create("listpaths", List.of("object"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var obj = namedArgs.get("object");
			PiccodeValue.verifyType(scope.caller, obj , Type.OBJECT);

			var object = (PiccodeObject) obj;
			var hash = object.obj.get("hash");
			if (hash == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Value is not a native object");
			}

			if (!(hash instanceof PiccodeNumber)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Invalid native object. Hash code is not a number, found " + Chalk.on(hash.type()).red());
			}
			
			var allocatedObject = Context.getObject((int) (double) hash.raw());
			if (allocatedObject == null) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("No object is allocated at 0x%04d", hash.raw()));
			}

			if (!(allocatedObject instanceof File)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a File", hash.raw()));
			}

			var elements = new ArrayList<PiccodeValue>();
			var file = (File) allocatedObject;
			for (var st: file.list()) {
				elements.add(new PiccodeString(st));
			}
			return new PiccodeArray(elements);
		}, null);
	
	}
}
