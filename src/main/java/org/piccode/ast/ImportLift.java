package org.piccode.ast;

import java.util.List;

/**
 *
 * @author hexaredecimal
 */
public class ImportLift extends Ast{
	public String text;
	public List<Ast> nodes;

	public ImportLift(String text, List<Ast> nodes) {
		this.text = text;
		this.nodes = nodes;
	}
}
