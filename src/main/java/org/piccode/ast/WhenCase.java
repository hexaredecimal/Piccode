package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class WhenCase extends Ast{
	public List<Ast> match; 
	public Ast value; 

	public WhenCase(List<Ast> match, Ast value) {
		this.match = match;
		this.value = value;
	}

	@Override
	public String toString() {
		var m = match.toString();
		m = m.substring(1, m.length() - 1);
		return "is " + m + " -> " + value;
	}
}
