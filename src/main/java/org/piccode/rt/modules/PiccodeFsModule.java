package org.piccode.rt.modules;

import com.github.tomaslanger.chalk.Chalk;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeBoolean;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeFsModule {

	public static void addFunctions() {
		NativeFunctionFactory.create("getcontext", List.of("path"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			var context = Path.of(_path.toString()).toAbsolutePath().normalize();
			var id = Context.allocate(context);
			var obj = new HashMap<String, PiccodeValue>();
			obj.put("hash", new PiccodeNumber(id));
			obj.put("path", new PiccodeString(context.getFileName().toString()));
			obj.put("fileSystem", new PiccodeString(context.getFileSystem().toString()));
			return new PiccodeObject(obj);
		}, null);

		NativeFunctionFactory.create("getfilecontext", List.of("file"), (args, namedArgs, frame) -> {

			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _file = namedArgs.get("file");
			PiccodeValue.verifyType(caller, _file, Type.STRING);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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
			var context = file.toPath().toAbsolutePath().normalize();
			var id = Context.allocate(context);
			var obj = new HashMap<String, PiccodeValue>();
			obj.put("hash", new PiccodeNumber(id));
			obj.put("path", new PiccodeString(context.getFileName().toString()));
			obj.put("fileSystem", new PiccodeString(context.getFileSystem().toString()));
			return new PiccodeObject(obj);
		}, null);

		NativeFunctionFactory.create("cd", List.of("ctx", "path"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			var _context = namedArgs.get("ctx");
			var _path = namedArgs.get("path");

			PiccodeValue.verifyType(caller, _context, Type.OBJECT);
			PiccodeValue.verifyType(caller, _path, Type.STRING);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _context;
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

			if (!(allocatedObject instanceof Path)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a Path Context", hash.raw()));
			}

			var _ctx = (Path) allocatedObject;
			Path context = _ctx.resolve(_path.toString()).normalize();
			var obj = new HashMap<String, PiccodeValue>();
			obj.put("hash", new PiccodeNumber(context.hashCode()));
			obj.put("path", new PiccodeString(context.getFileName().toString()));
			obj.put("fileSystem", new PiccodeString(context.getFileSystem().toString()));
			return new PiccodeObject(obj);
		}, null);

		NativeFunctionFactory.create("ls", List.of("ctx", "path"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			var _context = namedArgs.get("ctx");
			var _path = namedArgs.get("path");

			PiccodeValue.verifyType(caller, _context, Type.OBJECT);
			PiccodeValue.verifyType(caller, _path, Type.STRING);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _context;
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

			if (!(allocatedObject instanceof Path)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a Path Context", hash.raw()));
			}

			var _ctx = (Path) allocatedObject;

			try (Stream<Path> stream = Files.list(_ctx)) {
				var nodes = new ArrayList<PiccodeValue>();
				stream.forEach(p -> nodes.add(new PiccodeString(p.getFileName().toString())));
				return new PiccodeArray(nodes);
			} catch (IOException ex) {
				return PiccodeValue.error(ex.getMessage());
			}

		}, null);

		NativeFunctionFactory.create("pwd", List.of("ctx"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			var _context = namedArgs.get("ctx");

			PiccodeValue.verifyType(caller, _context, Type.OBJECT);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _context;
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

			if (!(allocatedObject instanceof Path)) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, String.format("Object allocated at 0x%04d is not a Path Context", hash.raw()));
			}

			var _ctx = (Path) allocatedObject;
			return new PiccodeString(_ctx.toString());
		}, null);

		NativeFunctionFactory.create("readtostring", List.of("path"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			try {
				var str = Files.readString(new File(_path.toString()).toPath());
				return PiccodeValue.success(new PiccodeString(str));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("readfiletostring", List.of("file"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;
			var _file = namedArgs.get("file");

			PiccodeValue.verifyType(caller, _file, Type.OBJECT);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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
				var str = Files.readString(file.toPath());
				return PiccodeValue.success(new PiccodeString(str));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("writeline", List.of("path", "line"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			var _line = namedArgs.get("line");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			PiccodeValue.verifyType(caller, _line, Type.STRING);
			try {
				var path = new File(_path.toString()).toPath();
				Files.writeString(
								path,
								_line.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.TRUNCATE_EXISTING, // wipe if exists
								StandardOpenOption.WRITE
				);
				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("writelines", List.of("path", "lines"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			var _lines = namedArgs.get("lines");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			PiccodeValue.verifyType(caller, _lines, Type.ARRAY);
			try {
				var lines = (PiccodeArray) _lines;
				var sb = new StringBuilder();
				for (var line : lines.nodes) {
					sb.append(line).append("\n");
				}
				var path = new File(_path.toString()).toPath();
				Files.writeString(
								path,
								sb.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.TRUNCATE_EXISTING, // wipe if exists
								StandardOpenOption.WRITE
				);

				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("appendline", List.of("path", "line"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			var _line = namedArgs.get("line");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			PiccodeValue.verifyType(caller, _line, Type.STRING);
			try {
				var path = new File(_path.toString()).toPath();
				Files.writeString(
								path,
								_line.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.WRITE,
								StandardOpenOption.APPEND
				);
				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("appendlines", List.of("path", "lines"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _path = namedArgs.get("path");
			var _lines = namedArgs.get("lines");
			PiccodeValue.verifyType(caller, _path, Type.STRING);
			PiccodeValue.verifyType(caller, _lines, Type.ARRAY);
			try {
				var lines = (PiccodeArray) _lines;
				var sb = new StringBuilder();
				for (var line : lines.nodes) {
					sb.append(line).append("\n");
				}
				var path = new File(_path.toString()).toPath();
				Files.writeString(
								path,
								sb.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.WRITE,
								StandardOpenOption.APPEND
				);

				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("writelinetofile", List.of("file", "line"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _file = namedArgs.get("file");
			var _line = namedArgs.get("line");
			PiccodeValue.verifyType(caller, _file, Type.STRING);
			PiccodeValue.verifyType(caller, _line, Type.STRING);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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
				var path = file.toPath();
				Files.writeString(
								path,
								_line.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.TRUNCATE_EXISTING, // wipe if exists
								StandardOpenOption.WRITE
				);
				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("writelinestofile", List.of("file", "lines"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _file = namedArgs.get("file");
			var _lines = namedArgs.get("lines");
			PiccodeValue.verifyType(caller, _file, Type.STRING);
			PiccodeValue.verifyType(caller, _lines, Type.ARRAY);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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
				var lines = (PiccodeArray) _lines;
				var sb = new StringBuilder();
				for (var line : lines.nodes) {
					sb.append(line).append("\n");
				}

				Files.writeString(
								file.toPath(),
								sb.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.TRUNCATE_EXISTING, // wipe if exists
								StandardOpenOption.WRITE
				);

				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("appendlinetofile", List.of("file", "line"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _file = namedArgs.get("file");
			var _line = namedArgs.get("line");
			PiccodeValue.verifyType(caller, _file, Type.STRING);
			PiccodeValue.verifyType(caller, _line, Type.STRING);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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
				var path = file.toPath();
				Files.writeString(
								path,
								_line.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.WRITE,
								StandardOpenOption.APPEND
				);
				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);

		NativeFunctionFactory.create("appendlinestofile", List.of("file", "lines"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);
			var caller = ctx.getTopFrame().caller;

			var _file = namedArgs.get("file");
			var _lines = namedArgs.get("lines");
			PiccodeValue.verifyType(caller, _file, Type.STRING);
			PiccodeValue.verifyType(caller, _lines, Type.ARRAY);

			var scope = ctx.getTopFrame();
			var object = (PiccodeObject) _file;
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

			var lines = (PiccodeArray) _lines;
			var sb = new StringBuilder();
			for (var line : lines.nodes) {
				sb.append(line).append("\n");
			}

			try {
				var path = file.toPath();
				Files.writeString(
								path,
								sb.toString(),
								StandardOpenOption.CREATE, // create if not exists
								StandardOpenOption.WRITE,
								StandardOpenOption.APPEND
				);
				return PiccodeValue.success(new PiccodeBoolean(true));
			} catch (Exception e) {
				return PiccodeValue.error(e.getMessage());
			}
		}, null);
	}

}
