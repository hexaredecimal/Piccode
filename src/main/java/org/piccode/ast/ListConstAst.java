package org.piccode.ast;

import java.util.ArrayList;
import org.piccode.rt.Context;
import org.piccode.rt.PiccodeArray;
import org.piccode.rt.PiccodeException;
import org.piccode.rt.PiccodeModule;
import org.piccode.rt.PiccodeNumber;
import org.piccode.rt.PiccodeObject;
import org.piccode.rt.PiccodeTuple;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ListConstAst extends Ast {

	public Ast lhs;
	public Ast rhs;

	public ListConstAst(Ast lhs, Ast rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return lhs + ":" + rhs;
	}

	@Override
	public PiccodeValue execute() {
		var left = lhs.execute();
		var right = rhs.execute();

		if (left instanceof PiccodeArray larr && right instanceof PiccodeArray rarr) {
			var nodes = new ArrayList<PiccodeValue>();
			nodes.addAll(larr.getList());
			nodes.addAll(rarr.getList());
			return new PiccodeArray(nodes);
		}

		if (left instanceof PiccodeArray larr) {
			var nodes = new ArrayList<PiccodeValue>();
			nodes.addAll(larr.getList());
			nodes.addFirst(right);
			return new PiccodeArray(nodes);
		}
		if (right instanceof PiccodeArray rarr) {
			var nodes = new ArrayList<PiccodeValue>();
			nodes.addAll(rarr.getList());
			nodes.addFirst(left);
			return new PiccodeArray(nodes);
		}
		var nodes = new ArrayList<PiccodeValue>();
		nodes.addFirst(left);
		nodes.addFirst(right);
		return new PiccodeArray(nodes);
	}

}
