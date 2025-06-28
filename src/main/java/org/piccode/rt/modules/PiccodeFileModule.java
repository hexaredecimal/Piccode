package org.piccode.rt.modules;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeBoolean;
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
		obj.put("file", new PiccodeBoolean(fp.isFile()));
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
			var id = Context.allocate(fp);

			return makeFile(fp);
		}, null);

	}
}
