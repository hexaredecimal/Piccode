package org.piccode.rt;

import com.github.tomaslanger.chalk.Chalk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.piccode.ast.Arg;
import org.piccode.ast.Ast;
import org.piccode.ast.ClauseAst;
import org.piccode.ast.ErrorNodeExpr;
import org.piccode.ast.FunctionAst;
import org.piccode.ast.TupleAst;
import org.piccode.ast.WhenAst;
import org.piccode.ast.WhenCase;
import org.piccode.ast.IdentifierAst;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeClosure implements PiccodeValue {

	public List<Ast> params; // Full list of args (with name and default val)
	Map<String, PiccodeValue> appliedArgs; // Applied named args
	int positionalIndex; // How many positional args have been applied so far
	Ast body;
	public Ast creator;

	public Ast.Location callSite = null;
	public String callSiteFile = null;

	public List<ClauseAst> clauses = new ArrayList<>();
	public String file;
	public int line, column;
	public Integer frame = null;

	public List<String> annotations = new ArrayList<>();

	public PiccodeClosure(List<Ast> params, Map<String, PiccodeValue> appliedArgs, int positionalIndex, Ast body) {
		this.params = params == null ? new ArrayList<>() : params;
		this.appliedArgs = appliedArgs;
		this.positionalIndex = positionalIndex;
		this.body = body;
	}

	public PiccodeValue call(PiccodeValue arg) {
		if (positionalIndex >= params.size()) {
			var err = new PiccodeException(callSiteFile, callSite.line, callSite.col, "Too many arguments. Expected " + params.size() + " but got " + (positionalIndex + 1));
			err.frame = frame;
			var note = new PiccodeException(file, line, column, "The function you are trying to call is declared below");
			err.addNote(note);
			throw err;
		}

		computeStableClause();
		if (!clauses.isEmpty()) {
			createWhenExpression(frame);
		}
		Map<String, PiccodeValue> newArgs = new HashMap<>(appliedArgs);
		if (params.get(positionalIndex) instanceof Arg _arg) {
			newArgs.put(_arg.name, arg);
		}

		var result = new PiccodeClosure(params, newArgs, positionalIndex + 1, body);
		result.annotations = annotations;
		result.creator = creator;
		result.frame = frame;
		result.callSite = callSite;
		result.callSiteFile = callSiteFile;
		result.file = file;
		result.line = line;
		result.column = column;
		return result;
	}

	public PiccodeValue callNamed(String name, PiccodeValue arg) {
		boolean paramExists = params.stream().anyMatch(p -> p instanceof Arg _arg && _arg.name.equals(name));
		if (!paramExists) {
			var err = new PiccodeException(callSiteFile, callSite.line, callSite.col, "Function does not have a parameter named '" + name + "'");
			err.frame = frame;
			var note = new PiccodeException(file, line, column, "The function you are trying to call is declared below");
			err.addNote(note);
			throw err;
		}

		if (positionalIndex >= params.size()) {
			var err = new PiccodeException(callSiteFile, callSite.line, callSite.col, "Too many arguments. Expected " + params.size() + " but got " + (positionalIndex + 1));
			err.frame = frame;
			var note = new PiccodeException(file, line, column, "The function you are trying to call is declared below");
			err.addNote(note);
			throw err;
		}

		computeStableClause();
		if (!clauses.isEmpty()) {
			createWhenExpression(frame);
		}
		if (appliedArgs.containsKey(name)) {
			var err = new PiccodeException(callSiteFile, callSite.line, callSite.col, "Duplicate argument: " + name);
			err.frame = frame;
			var note = new PiccodeException(file, line, column, "The function you are trying to call is declared below");
			err.addNote(note);
			throw err;
		}

		Map<String, PiccodeValue> newArgs = new HashMap<>(appliedArgs);
		newArgs.put(name, arg);

		var result = new PiccodeClosure(params, newArgs, positionalIndex + 1, body);
		result.annotations = annotations;
		result.creator = creator;
		result.frame = frame;
		result.callSite = callSite;
		result.callSiteFile = callSiteFile;
		result.file = file;
		result.line = line;
		result.column = column;
		return result;
	}

	public PiccodeValue evaluateIfReady() {
		for (Ast param : params) {
			if (param instanceof Arg arg) {
				boolean isSet = appliedArgs.containsKey(arg.name);
				boolean hasDefault = arg.def_val != null;
				if (!isSet && !hasDefault) {
					return this; // Not ready yet — required arg still missing
				}
			}
		}
		return Ast.safeExecute(frame, creator, ($_unused_$) -> {
			for (Ast param : params) {
				var ctx = frame == null
								? Context.top
								: Context.getContextAt(frame);

				
				PiccodeValue val = param instanceof Arg arg
								? appliedArgs.getOrDefault(
												arg.name,
												arg.def_val != null ? arg.def_val.execute(frame) : null
								) : param.execute(frame);

				if (param instanceof Arg arg) {
					if (arg.export && !(val instanceof PiccodeObject)) {
						throw new PiccodeException(param.file, param.line, param.column, "Cannot export fields of a value that is not an object. Found type " + Chalk.on(val.type()).red());
					} else if (arg.export && val instanceof PiccodeObject obj) {
						for (var kv : obj.obj.entrySet()) {
							var name = kv.getKey();
							var value = kv.getValue();
							ctx.putLocal(name, value);
						}
					} else {
						ctx.putLocal(arg.name, val);
					}
				}
			}

			
			try {
				if (!annotations.isEmpty()) {
					for (var annotation: annotations) {
						if (!Context.hasAnnotation(annotation)) {
							throw new PiccodeException(file, line, column, "Annotation `" + Chalk.on(annotation).red() + "` does not exist.");
						}
						// TODO: Allow mutiple nested annotations
						var fx = Context.getAnnotation(annotation);
						return fx.apply(frame, body);
					}
				}
				var result = body.execute(frame);
				return result;
			} catch (StackOverflowError se) {
				var err = new PiccodeException(callSiteFile, callSite.line, callSite.col, "Stack overflow");
				err.frame = frame;
				var note = new PiccodeException(file, line, column, "Stack overflow error most likely occured when you called the function below. ");
				err.addNote(note);
				throw err;
			}
		});
	}

	@Override
	public Object raw() {
		return toString();
	}

	@Override
	public String toString() {
		return "Rt.Fn/" + (params.size() - positionalIndex);
	}

	@Override
	public String type() {
		return "Function";
	}

	private void createWhenExpression(Integer frame) {
		var ctx = frame == null
						? Context.top
						: Context.getContextAt(frame);

		clauses.add(new ClauseAst(params, body));

		var index = 0;
		for (int i = 0; i < clauses.size(); i++) {
			var clause = clauses.get(i);
			if (clause.isStable()) {
				index = i;
				break;
			}
		}
		var clause = clauses.remove(index);
		var cases = new ArrayList<WhenCase>();

		for (var _case : clauses) {
			if (_case.args == params) {
				continue;
			}
			var args = _case.getArgs();
			var val = args.size() == 1 ? args.getFirst() : new TupleAst(args);
			var when = new WhenCase(List.of(val), _case.body);
			cases.add(when);
		}

		var args = clause.getArgs();
		var cond = args.size() == 1 ? args.getFirst() : new TupleAst(args);

		var errNode = new ErrorNodeExpr("Inexhaustive when expression: no pattern matched: when " + cond + " { ... }");
		errNode.file = file;
		errNode.line = line;
		errNode.column = column;

		var generalCaseBody = isMatched(clause.body, cases)
						? errNode
						: clause.body;

		cases.add(new WhenCase(List.of(cond), generalCaseBody));
		var match = new WhenAst(cond, cases, null);
		// System.out.println("Expr: " + match + " " + clauses);
		match.file = file;
		match.line = line;
		match.column = column;
		body = match;
		if (creator instanceof FunctionAst func) {
			ctx.putLocal(func.name, this);
		}
		clauses.clear();
	}

	private void computeStableClause() {
		if (clauses.isEmpty()) {
			return;
		}

		for (int i = 0; i < clauses.size(); i++) {
			var clause = clauses.get(i);
			if (clause.isStable()) {
				if (params != clause.args) {
					clauses.add(new ClauseAst(params, body));
					params = clause.args;
					return;
				}
			}
		}

		int index = 0;
		int size = params.size();
		List<Ast> newParams = new ArrayList<>();
		for (int i = 0; i < clauses.size() && index < size; i++) {
			var args = clauses.get(i).args;
			for (int j = 0; j < size; j++) {
				var top = args.get(index);
				var param = params.get(index);
				if (param instanceof Arg arg) {
					newParams.add(index, arg);
					index++;
				} else if (top instanceof Arg arg) {
					newParams.add(index, arg);
					index++;
				} else {
					var arg = new Arg("$_PLACEHOLDER_ID_" + index + "__$");
					arg.file = file;
					arg.line = line;
					arg.column = column;
					newParams.add(index, arg);
					index++;
				}
			}
		}

		if (!clauses.isEmpty()) {
			size = newParams.size();
			if (newParams.size() < size) {
				for (int i = 0; i < size; i++) {
					var arg = new Arg("$_PLACEHOLDER_ID_" + i + "__$");
					arg.file = file;
					arg.line = line;
					arg.column = column;
					newParams.addLast(arg);
				}
			}
		}

		clauses.add(new ClauseAst(params, body));
		params = newParams;
	}

	private boolean isMatched(Ast body, ArrayList<WhenCase> cases) {
		for (var case_ : cases) {
			if (case_.value == body || case_.value.equals(body)) {
				return true;
			}
		}

		return false;
	}

}
