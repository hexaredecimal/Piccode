package org.piccode.rt;

import java.util.HashMap;
import java.util.concurrent.Future;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeRtTask {
	private Future<PiccodeValue> future;
	private HashMap<String, PiccodeValue> scope;

	public PiccodeRtTask(Future<PiccodeValue> future, HashMap<String, PiccodeValue> scope) {
		this.future = future;
		this.scope = scope;
	}

	public Future<PiccodeValue> getFuture() {
		return future;
	}

	public HashMap<String, PiccodeValue> getScope() {
		return scope;
	}
	
}
