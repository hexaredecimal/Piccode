package org.piccode.rt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.piccode.ast.Ast;
import org.piccode.backend.Compiler;

/**
 *
 * @author hexaredecimal
 */
public class Context {

	private static HashMap<String, PiccodeValue> global_scope = new HashMap<>();
	private static MessageDigest hash = null;

	private Stack<StackFrame> call_frames;
	public StackFrame bottom = null;

	public static Context top = new Context();
	public static ConcurrentHashMap<String, PiccodeModule> modules = new ConcurrentHashMap<>();
	private static List<Context> threadContexts = new ArrayList<>();
	private ConcurrentHashMap<String, List<Ast>> import_cache = new ConcurrentHashMap<>();
	private static ExecutorService threadPool = Executors.newVirtualThreadPerTaskExecutor();
	private static ConcurrentMap<String, Future<PiccodeValue>> futureMap = new ConcurrentHashMap<>();

	public Context() {
		call_frames = new Stack<>();
		import_cache = new ConcurrentHashMap<>();
	}

	public static int makeThreadContext(Context base) {
		int index = threadContexts.size();
		var context = new Context();
		context.call_frames.addAll(base.call_frames);
		context.futureMap.putAll(base.futureMap);
		threadContexts.add(context);
		return index;
	}

	public static void dropThreadContext(int index) {
		threadContexts.remove(index);
	}

	public static Context getContextAt(int index) {
		return threadContexts.get(index);
	}

	public Stack<StackFrame> getCallStack() {
		return call_frames;
	}

	public int getFramesCount() {
		return call_frames.size();
	}

	public static String makeThread(PiccodeClosure node) {
		synchronized (Context.class) {
			var future = threadPool.submit(() -> {
				synchronized (node) {
					var ctx = node.frame == null ? top : threadContexts.get(node.frame);
					var frame = makeThreadContext(ctx);
					var call = (PiccodeClosure) node.call(new PiccodeUnit());
					call.frame = frame;
					return call.evaluateIfReady();
				}
			});
			var size = futureMap.size();
			var name = String.format("Thread@%d", size);
			var id = name + UUID.randomUUID().toString();
			futureMap.put(id, future);
			return id;
		}
	}

	public static Future<PiccodeValue> getFuture(String id) {
		synchronized (Context.class) {
			return futureMap.get(id);
		}
	}

	public static void removeFuture(String uuid) {
		futureMap.remove(uuid);
	}

	public StackFrame getTopFrame() {
		return call_frames.peek();
	}

	public static void addGlobal(String name, PiccodeValue value) {
		global_scope.put(name, value);
	}

	public boolean isImportCached(String path) {
		return import_cache.containsKey(path);
	}

	public void cacheImport(String path, List<Ast> nodes) {
		import_cache.put(path, nodes);
	}

	public List<Ast> getCached(String path) {
		return import_cache.get(path);
	}

	public void pushScope() {
		call_frames.peek().addScope();
	}

	public void dropScope() {
		call_frames.peek().dropScope();
	}

	public void pushStackFrame(Ast top) {
		if (call_frames.isEmpty()) {
			var sp = new StackFrame(top);
			bottom = sp;
			call_frames.push(sp);
			return;
		}

		var prev = call_frames.peek();
		call_frames.push(new StackFrame(top, prev));
	}

	public void dropStackFrame() {
		call_frames.pop();
	}

	public void putLocal(String name, PiccodeValue value) {
		var frame = call_frames.peek();
		frame.putLocal(name, value);
	}

	public PiccodeValue getValue(String name) {
		if (call_frames.isEmpty()) {
			return global_scope.get(name);
		}
		var frame = call_frames.peek();
		var value = frame.getValue(name);
		if (value == null) {
			return global_scope.get(name);
		}
		return value;
	}

	public String getSimilarName(String id) {
		HashMap<String, Integer> calculations = new HashMap<>();
		var top = call_frames.peek().toMap();
		top.putAll(global_scope);
		for (var entry : top.entrySet()) {
			int count = 0;
			String base = entry.getKey();
			try {
				for (int i = 0; i < id.length(); i++) {
					if (id.charAt(i) == base.charAt(i)) {
						count++;
					} else {
						break;
					}
				}
			} catch (Exception ex) {
				// skip
			}
			calculations.put(base, count);
		}

		String result = "";
		int oldCount = 0;
		for (var entry : calculations.entrySet()) {
			String fword = entry.getKey();
			int count = entry.getValue();
			if (count > oldCount) {
				result = fword;
			}
		}
		return result;
	}

}
