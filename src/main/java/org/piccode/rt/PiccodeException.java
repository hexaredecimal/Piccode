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

	private List<PiccodeException> notes = new ArrayList<>();
	
	public PiccodeException(String file, int line, int col, String message) {
		super(message);
		this.file = file;
		this.line = line;
		this.col = col;
		this.message = message;
	}

	public void addNote(PiccodeException note) {
		this.notes.add(note);
	}
	
	public void reportError() {
		reportError(true, null);
	}	

	public void reportError(boolean die, String kind) {
		var fp = new File("");

		String[] lines = new String[]{};
		if (file != null) {
			fp = new File(file);
			lines = toLines(fp);
		}
		var line_fmt = String.format(" %d │", line);
		var gap = " ".repeat(line_fmt.length());
		var gap2 = " ".repeat(line_fmt.length() - 1);
		var fmt = String.format(
			"╭──[%s:%d:%d]: %s: %s", 
			file == null ? 
				"repl" 
				: fp.getName(), 
				line, col, 
			kind == null ? 
				"Error": kind, message
		);

		if (file == null) {
			System.out.println("" + fmt);
			return;
		}
		
		var index = line - 1 < lines.length ? line - 1 : lines.length -1;
		var code_line = lines[index].replaceAll("\t", " ".repeat(1));
		System.out.println(gap2 + fmt);
		System.out.println(line_fmt + " " + code_line);
		var tick = "─".repeat(col + 1) +  "╯";
		var tick2 = " ".repeat(col + 1) + "^"; 
		System.out.println(gap2 + "│" + tick2);
		System.out.println(gap2 + "╰" + tick);
		if (line + 1 < lines.length) {
			line_fmt = String.format(" %d │", line + 1);
			System.out.println(line_fmt);
		}


		if (!notes.isEmpty()) {
			System.out.println((".\n").repeat(2));
			System.out.println("[NOTE]");
			for (var note: notes) {
				note.reportError(false, "INFO");
			}
		}
		
		if (die) {
			System.exit(1);
		}
	}
	
	private static String[] toLines(File fp) {
		List<String> lines = new ArrayList<>();
		
		try (Scanner sc = new Scanner(fp)) {
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}
		} catch (FileNotFoundException ex) {
		}
		return lines.toArray(String[]::new);
	}
}
