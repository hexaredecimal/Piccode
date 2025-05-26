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

	public NativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs) {
		this.name = name;
		this.params = params;
		this.defaultArgs = defaultArgs != null ? defaultArgs : new HashMap<>();
		this.boundArgs = new HashMap<>();
	}

	public NativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs, Map<String, PiccodeValue> boundArgs) {
		this.name = name;
		this.params = params;
		this.defaultArgs = defaultArgs != null ? defaultArgs : new HashMap<>();
		this.boundArgs = boundArgs != null ? new HashMap<>(boundArgs) : new HashMap<>();
	}

	// Curry-style apply
	public PiccodeValue call(PiccodeValue value) {
		if (boundArgs.size() >= params.size()) {
			throw new PiccodeException("native", 0, 0,"Too many arguments applied");
		}

		String nextParam = params.get(boundArgs.size());
		Map<String, PiccodeValue> newBound = new HashMap<>(boundArgs);
		newBound.put(nextParam, value);
		return new CurriedNativeFunction(name, params, defaultArgs, newBound, this);
	}

	public PiccodeValue callNamed(String name, PiccodeValue value) {
		if (!params.contains(name)) {
			throw new PiccodeException("native", 0, 0,"Unknown argument: " + name);
		}

		Map<String, PiccodeValue> newBound = new HashMap<>(boundArgs);
		newBound.put(name, value);
		return new CurriedNativeFunction(name, params, defaultArgs, newBound, this);
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
				throw new PiccodeException("native", 0, 0,"Missing argument: " + param);
			}
		}

		return invoke(orderedArgs, boundArgs);
	}

	public class CurriedNativeFunction extends NativeFunction {

		private final NativeFunction target;
		public CurriedNativeFunction(String name, List<String> params, Map<String, PiccodeValue> defaultArgs,
						Map<String, PiccodeValue> boundArgs, NativeFunction target) {
			super(name, params, defaultArgs, boundArgs);
			this.target = target;
		}

		@Override
		public PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs) {
			return target.invoke(args, namedArgs);
		}

		@Override
		public Object raw() {
			return target;
		}
	}

	
	@Override
	public String toString() {
		return "<native function " + name + ">";
	}

	public abstract PiccodeValue invoke(List<PiccodeValue> args, Map<String, PiccodeValue> namedArgs);
}
