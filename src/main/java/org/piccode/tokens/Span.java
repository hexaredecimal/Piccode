package org.piccode.tokens;

/**
 *
 * @author hexaredecimal
 */
public class Span {
	public int line, column;
	public String filepath;

	public Span(int line, int column, String filepath) {
		this.line = line;
		this.column = column;
		this.filepath = filepath;
	}

	public Span(int line, int column) {
		this.line = line;
		this.column = column;
		this.filepath = null;
	}

	@Override
	public String toString() {
		return "Span{" + "line=" + line + ", column=" + column + ", filepath=" + filepath + '}';
	}
	
}
