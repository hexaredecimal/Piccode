package org.piccode.rt.modules;

import java.util.List;
import org.piccode.rt.PiccodeNumber;


/**
 *
 * @author hexaredecimal
 */
public class PiccodeMathModule {
	public static void addFunctions() {
		NativeFunctionFactory.create("sin", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.sin(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("cos", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.cos(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("tan", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.tan(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);

		NativeFunctionFactory.create("asin", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.asin(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("acos", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.acos(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("atan", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.atan(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);


		NativeFunctionFactory.create("abs", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.abs(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);

		NativeFunctionFactory.create("floor", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.floor(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);

		NativeFunctionFactory.create("ceil", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.ceil(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);


		NativeFunctionFactory.create("sinh", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.sin(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("cosh", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.cos(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
		
		NativeFunctionFactory.create("tanh", List.of("a"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var result = Math.tan(value);
			return new PiccodeNumber(String.format("%s", result));
		}, null);

		NativeFunctionFactory.create("pow", List.of("a", "b"), (args, namedArgs, frame) -> {
			var value = (double) namedArgs.get("a").raw();
			var power = (double) namedArgs.get("b").raw();
			var result = Math.pow(value, power);
			return new PiccodeNumber(String.format("%s", result));
		}, null);
	}
}
