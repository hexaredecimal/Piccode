package org.piccode.rt.modules;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.piccode.ast.CallAst;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;

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
			try {
				var year = (int) (double) namedArgs.get("year").raw();
				var month = (int) (double) namedArgs.get("month").raw();
				var day = (int) (double) namedArgs.get("day").raw();
				var hour = (int) (double) namedArgs.get("hour").raw();
				var minute = (int) (double) namedArgs.get("minute").raw();

				var time = LocalDateTime.of(year, month, day, hour, minute);
				return makeTime(time);
			} catch (RuntimeException e) {
				var last = CallAst.lastCall;
				if (last == null) {
					var err = new PiccodeException("repl", 0, 0, e.getMessage());
					throw err;
				}

				throw new PiccodeException(last.file, last.line, last.column, e.getMessage());
			}
		}, null);
	}
}
