package org.piccode.rt.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.piccode.ast.Ast;
import org.piccode.ast.CallAst;
import org.piccode.ast.ClosureAst;
import org.piccode.ast.IdentifierAst;
import org.piccode.ast.UnitAst;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeClosure;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeUnit;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeVirtualModule {

	public static void addFunctions() {

		Context.addAnnotation("Virtual", (frame, node) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var top = ctx.getTopFrame().caller;
			var ignored = Ast.finalizeNode(new UnitAst(), top);

			Map<String, PiccodeValue> newArgs = new HashMap<>();
			var args = new ArrayList<Ast>();
			args.add(ignored);

			var result = new PiccodeClosure(args, newArgs, 0, node);
			var id = Context.makeThread(result);

			var obj = new HashMap<String, PiccodeValue>();
			obj.put("uuid", new PiccodeString(id));
			return new PiccodeObject(obj);
		});

		NativeFunctionFactory.create("task", List.of("fx"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();

			var fx = namedArgs.get("fx");
			PiccodeValue.verifyType(scope.caller, fx, Type.CLOSURE);

			var closure = (PiccodeClosure) fx;
			scope = closure.frame == null
							? scope
							: Context.getContextAt(closure.frame).getTopFrame();

			var id = Context.makeThread(closure);
			var obj = new HashMap<String, PiccodeValue>();
			obj.put("uuid", new PiccodeString(id));
			return new PiccodeObject(obj);
		}, null);

		NativeFunctionFactory.create("sleep", List.of("ms"), (args, namedArgs, frame) -> {
			var ctx = frame == null
							? Context.top
							: Context.getContextAt(frame);

			var scope = ctx.getTopFrame();
			var ms = namedArgs.get("ms");
			PiccodeValue.verifyType(scope.caller, ms, Type.NUMBER);

			try {
				var millisec = (long) (double) ms.raw();
				Thread.sleep(millisec);
			} catch (InterruptedException ex) {
				var node = scope.caller;
				throw new PiccodeException(node.file, node.line, node.column, "Internal Error: " + ex.getMessage());
			}

			return new PiccodeUnit();
		}, null);

	}
}
