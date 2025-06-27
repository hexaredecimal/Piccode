package org.piccode.rt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hexaredecimal
 */
public abstract class NativeFunction implements PiccodeValue {

	private final String name;
	
	private final List<String> params;
	private final Map<String, PiccodeValue> defaultArgs;
	private final Map<String, PiccodeValue> boundArgs;
	public Integer frame;
	public int line = 0;
	public int column = 0;
	public String file = null;

	public NativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs, Integer frame) {
		this.name = name;
		this.params = params;
		this.defaultArgs = defaultArgs != null ? defaultArgs : new HashMap<>();
		this.boundArgs = new HashMap<>();
		this.frame = frame;
	}

	public NativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs, Map<String, PiccodeValue> boundArgs, Integer frame) {
		this.name = name;
		this.params = params;
		this.defaultArgs = defaultArgs != null ? defaultArgs : new HashMap<>();
		this.boundArgs = boundArgs != null ? new HashMap<>(boundArgs) : new HashMap<>();
		this.frame = frame;
	}

	public PiccodeValue call(PiccodeValue value) {
		if (boundArgs.size() >= params.size()) {
			throw new PiccodeException(file, line, column, "Too many arguments applied");
		}

		String nextParam = params.get(boundArgs.size());
		Map<String, PiccodeValue> newBound = new HashMap<>(boundArgs);
		newBound.put(nextParam, value);
		var cn = new CurriedNativeFunction(name, params, defaultArgs, newBound, this, frame);
		cn.file = file;
		cn.line = line;
		cn.column = column;
		return cn;
	}

	public PiccodeValue callNamed(String name, PiccodeValue value) {
		if (!params.contains(name)) {
			throw new PiccodeException(file, line, column, "Unknown argument: " + name);
		}

		Map<String, PiccodeValue> newBound = new HashMap<>(boundArgs);
		newBound.put(name, value);
		var cn = new CurriedNativeFunction(name, params, defaultArgs, newBound, this, frame);
		cn.file = file;
		cn.line = line;
		cn.column = column;
		return cn;
	}

	public PiccodeValue evaluateIfReady() {
		if (boundArgs.size() < params.size()) {
			return this; // Not enough args yet
		}
		List<PiccodeValue> orderedArgs = new ArrayList<>();
		for (String param : params) {
			if (boundArgs.containsKey(param)) {
				orderedArgs.add(boundArgs.get(param));
			} else if (defaultArgs.containsKey(param)) {
				orderedArgs.add(defaultArgs.get(param));
			} else {
				throw new PiccodeException(file, line, column, "Missing argument: " + param);
			}
		}

		return invoke(orderedArgs, boundArgs, frame);
	}

	public class CurriedNativeFunction extends NativeFunction {

		private final NativeFunction target;
		public int line = 0;
		public int column = 0;
		public String file = null;

		public CurriedNativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs,
						Map<String, PiccodeValue> boundArgs, NativeFunction target, Integer frame) {
			super(name, params, defaultArgs, boundArgs, frame);
			this.target = target;
		}

		@Override
		public PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs, Integer frame) {
			return target.invoke(args, namedArgs, frame);
		}

		@Override
		public Object raw() {
			return target;
		}

		@Override
		public String type() {
			return "NativeFunction";
		}
	}

	@Override
	public String type() {
		return "NativeFunction";
	}

	@Override
	public String toString() {
		return "<native function " + name + ">";
	}

	public abstract PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs, Integer frame);
}
