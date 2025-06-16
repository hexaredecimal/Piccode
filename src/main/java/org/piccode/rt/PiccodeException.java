package org.piccode.rt;

import com.github.tomaslanger.chalk.Chalk;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeException extends RuntimeException implements PiccodeInfo {

	public String file;
	public int line, col;
	public String message;

	private List<PiccodeInfo> notes = new ArrayList<>();

	public PiccodeException(String file, int line, int col, String message) {
		super(message);
		this.file = file;
		this.line = line;
		this.col = col;
		this.message = message;
	}

	public void addNote(PiccodeInfo note) {
		this.notes.add(note);
	}

	public void reportError() {
		reportError(!ReplState.ACTIVE, "ERROR");
	}

	@Override
	public void reportError(boolean die, String kind) {
		var fp = new File("");

		String[] lines = new String[]{};
		if (file != null) {
			if (file.equals("repl")) {
				lines = ReplState.CODE.lines().toList().toArray(String[]::new);
			} else {
				fp = new File(file);
				lines = toLines(fp);
			}
		}

		var line_fmt = String.format(" %d │", line);
		var gap = " ".repeat(line_fmt.length());
		var gap2 = " ".repeat(line_fmt.length() - 1);
		var fmt = String.format(
						"╭──[%s:%d:%d]: %s: %s",
						file == null || file.equals("repl")
						? "repl"
						: fp.getName(),
						line, col + 1,
						kind == null || kind.equals("ERROR")
						? Chalk.on("ERROR").red() // I hard code ERROR just in case it is null;
						: Chalk.on(kind.toUpperCase()).yellow(),
						message
		);

		if (file == null) {
			System.out.println("" + fmt);
			return;
		}

		var index = line - 1 < lines.length ? line - 1 : lines.length - 1;
		if (index < 0) {
			index = 0;
		}
		var code_line = lines[index].replaceAll("\t", " ".repeat(1));
		System.out.println(gap2 + fmt);
		System.out.println(line_fmt + " " + code_line);
		var tick = "─".repeat(col + 1) + "╯";
		var tick2 = " ".repeat(col + 1) + "^";
		System.out.println(gap2 + "│" + tick2);
		System.out.println(gap2 + "╰" + tick);
		if (line + 1 < lines.length) {
			line_fmt = String.format(" %d │", line + 1);
			System.out.println(line_fmt);
		}

		if (!notes.isEmpty()) {
			System.out.println((Chalk.on(".").yellow() + "\n").repeat(2));
			for (var note : notes) {
				note.reportError(false, "INFO");
			}
		}

		var stack = Context.top.getCallStack();

		var list = List.of(stack.toArray(StackFrame[]::new)).reversed();

		if (!list.isEmpty()) {
			System.out.println("\n[STACK TRACE]");
			for (int i = 0; i < list.size(); i++) {
				var frame = list.get(i);
				var callSite = frame.caller;
				var _str = String.format(
								"[%s:%d:%d]: %s", callSite.file,
								callSite.line, callSite.column + 1,
								i == 0 
									? Chalk.on(callSite.toString()).red()
									: callSite.toString());

				System.out.println(_str);
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
