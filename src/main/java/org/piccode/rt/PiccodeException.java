package org.piccode.rt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeException extends RuntimeException{
	public String file;
	public int line, col;
	public String message;
	
	public PiccodeException(String file, int line, int col, String message) {
		super(message);
		this.file = file;
		this.line = line;
		this.col = col;
		this.message = message;
	}


	public void reportError() {
		
		String[] lines = new String[]{};
		if (file != null) lines = toLines(file);
		var line_fmt = String.format(" %d │", line);
		var gap = " ".repeat(line_fmt.length());
		var gap2 = " ".repeat(line_fmt.length() - 1);
		var fmt = String.format("┌─%s:%d:%d: %s: %s", file == null ? "repl" : file, line, col, "Error", message);
		
		var index = line - 1 < lines.length ? line - 1 : lines.length -1;
		var code_line = lines[index].replaceAll("\t", " ".repeat(1));
		System.out.println(gap2 + fmt);
		System.out.println(line_fmt + " " + code_line);
		var tick = "─".repeat(col + 1) +  "┘";
		var tick2 = " ".repeat(col + 1) + "^"; 
		System.out.println(gap2 + "│" + tick2);
		System.out.println(gap2 + "└" + tick);
		if (line + 1 < lines.length) {
			line_fmt = String.format(" %d │", line + 1);
			System.out.println(line_fmt);
		}
		System.exit(1);
	}
	
	private static String[] toLines(String path) {
		List<String> lines = new ArrayList<>();
		
		try (Scanner sc = new Scanner(new File(path))) {
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
		} catch (FileNotFoundException ex) {
		}
		return lines.toArray(String[]::new);
	}
}
