package org.piccode.rt.modules;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.piccode.ast.CallAst;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;
import org.piccode.rt.PiccodeValue.Type;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeTimeModule {

	private static PiccodeValue makeTime(LocalDateTime time) {
		var obj = new HashMap<String, PiccodeValue>();
		obj.put("year", new PiccodeNumber(time.getYear()));
		obj.put("month", new PiccodeNumber(time.getMonthValue()));
		obj.put("day", new PiccodeNumber(time.getDayOfMonth()));
		obj.put("hour", new PiccodeNumber(time.getHour()));
		obj.put("minute", new PiccodeNumber(time.getMinute()));
		obj.put("second", new PiccodeNumber(time.getSecond()));
		obj.put("hour", new PiccodeNumber(time.getNano()));

		return new PiccodeObject(obj);
	}

	public static void addFunctions() {

		NativeFunctionFactory.create("now", List.of(), (args, namedArgs, frame) -> {
			var time = LocalDateTime.now();
			return makeTime(time);
		}, null);

		NativeFunctionFactory.create("min", List.of(), (args, namedArgs, frame) -> {
			var time = LocalDateTime.MIN;
			return makeTime(time);
		}, null);

		NativeFunctionFactory.create("max", List.of(), (args, namedArgs, frame) -> {
			var time = LocalDateTime.MAX;
			return makeTime(time);
		}, null);

		NativeFunctionFactory.create("from", List.of("year", "month", "day", "hour", "minute"), (args, namedArgs, frame) -> {
				var ctx = frame == null ? 
						Context.top
						: Context.getContextAt(frame);
				var caller = ctx.getTopFrame().caller;
				
				var yr  = namedArgs.get("year");
				var mn  = namedArgs.get("month");
				var dy  = namedArgs.get("day");
				var hr  = namedArgs.get("hour");
				var min = namedArgs.get("minute");

				PiccodeValue.verifyType(caller, yr, Type.NUMBER);
				PiccodeValue.verifyType(caller, mn, Type.NUMBER);
				PiccodeValue.verifyType(caller, dy, Type.NUMBER);
				PiccodeValue.verifyType(caller, hr, Type.NUMBER);
				PiccodeValue.verifyType(caller, min, Type.NUMBER);
			try {
				var year = (int) (double) yr.raw();
				var month = (int) (double) mn.raw();
				var day = (int) (double) dy.raw();
				var hour = (int) (double) hr.raw();
				var minute = (int) (double) min.raw();

				var time = LocalDateTime.of(year, month, day, hour, minute);
				return makeTime(time);
			} catch (RuntimeException e) {
				var last = ctx.getTopFrame().caller;
				if (last == null) {
					var err = new PiccodeException("repl", 0, 0, e.getMessage());
					throw err;
				}

				throw new PiccodeException(last.file, last.line, last.column, e.getMessage());
			}
		}, null);
	}
}
