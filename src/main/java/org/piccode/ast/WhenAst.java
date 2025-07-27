package org.piccode.ast;

import java.util.List;

import org.piccode.rt.*;
import java.util.*;
import org.piccode.piccodescript.TargetEnvironment;

/**
 *
 * @author hexaredecimal
 */
public class WhenAst extends Ast {

	public Ast cond;
	public List<WhenCase> cases;
	public Ast else_case;

	public WhenAst(Ast cond, List<WhenCase> cases, Ast else_case) {
		this.cond = cond;
		this.cases = cases;
		this.else_case = else_case;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("when ").append(cond).append(" {\n");
		for (var when_c : cases) {
			sb.append(when_c.toString().indent(4));
		}
		if (else_case != null) {
			sb.append(String.format("else %s", else_case).indent(4));
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public PiccodeValue execute(Integer frame) {
		var cond_value = Ast.safeExecute(frame, () -> {
			return cond.execute(frame);
		});

		for (var match_case : cases) {
			var tempSymtable = new HashMap<String, PiccodeValue>();
			if (isMatching(match_case.match, cond_value, tempSymtable, frame)) {
				return Ast.safeExecute(frame, () -> {

					var ctx = frame == null
									? Context.top
									: Context.getContextAt(frame);

					if (!tempSymtable.isEmpty()) {
						for (var entry : tempSymtable.entrySet()) {
							ctx.putLocal(entry.getKey(), entry.getValue());
						}
					}
					var result = match_case.value.execute(frame);
					return result;
				});
			}
		}

		if (else_case == null) {
			var err = new PiccodeException(file, line, column, "Inexhaustive when expression: no pattern matched: when " + cond + " { ... }");
			err.frame = frame;
			throw err;
		}

		return else_case.execute(frame);

	}

	private boolean isMatching(List<Ast> patterns, PiccodeValue cond_value, Map<String, PiccodeValue> temp, Integer frame) {
		for (Ast pattern : patterns) {
			if (matchPattern(pattern, cond_value, temp, frame)) {
				return true;
			}
		}
		return false;
	}

	private boolean matchPattern(Ast pattern, PiccodeValue value, Map<String, PiccodeValue> temp, Integer frame) {
		if (pattern instanceof IdentifierAst id) {
			if (id.text.equals("true")) {
				var result = value instanceof PiccodeBoolean bool && ((boolean) bool.raw()) == true;
				return result;
			}
			if (id.text.equals("false")) {
				var result = value instanceof PiccodeBoolean bool && ((boolean) bool.raw()) == false;
				return result;
			}
			if (!id.text.equals("_")) {
				temp.put(id.text, value);
			}
			return true;
		}

		if (pattern instanceof NumberAst lit) {
			var litVal = lit.execute(frame);
			return litVal.equals(value);
		}

		if (pattern instanceof StringAst lit) {
			var litVal = lit.execute(frame);
			return Objects.equals(litVal, value);
		}

		if (pattern instanceof TupleAst tup && value instanceof PiccodeTuple vTup) {
			var items = tup.nodes;
			var vItems = vTup.nodes;
			if (items.size() != vItems.size()) {
				return false;
			}

			for (int i = 0; i < items.size(); i++) {
				if (!matchPattern(items.get(i), vItems.get(i), temp, frame)) {
					return false;
				}
			}
			return true;
		}
		if (pattern instanceof ArrayAst listPat && value instanceof PiccodeArray listVal) {
			var patItems = listPat.nodes;
			var valItems = listVal.nodes;
			if (patItems.size() != valItems.size()) {
				return false;
			}

			for (int i = 0; i < patItems.size(); i++) {
				if (!matchPattern(patItems.get(i), valItems.get(i), temp, frame)) {
					return false;
				}
			}
			return true;
		}
		if (pattern instanceof ListConstAst cons && value instanceof PiccodeArray listVal2) {
			if (listVal2.nodes.isEmpty()) {
				return false;
			}
			var head = listVal2.nodes.getFirst();
			var tail = new PiccodeArray(listVal2.nodes.subList(1, listVal2.nodes.size()));

			return matchPattern(cons.lhs, head, temp, frame) && matchPattern(cons.rhs, tail, temp, frame);
		}
		if (pattern instanceof ObjectAst objPat && value instanceof PiccodeObject objVal) {
			for (var entry : objPat.objs.entrySet()) {
				if (!objVal.obj.containsKey(entry.getKey())) {
					return false;
				}
				if (!matchPattern(entry.getValue(), objVal.obj.get(entry.getKey()), temp, frame)) {
					return false;
				}
			}
			return true;
		}

		if (pattern instanceof ArrayAst && value instanceof PiccodeArray vList && vList.nodes.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
}
