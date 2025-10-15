package org.piccode.errors;

import org.piccode.errors.PiccodeInfo;
import com.github.tomaslanger.chalk.Chalk;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.piccode.backend.Compiler;
import org.piccode.piccodescript.ErrorAsciiKind;

/**
 *
 * @author hexaredecimal
 */
public class PiccodeWarning implements PiccodeInfo {

	public String file;
	public int line, col;
	public String message;

	private PrintStream out = Compiler.out;
	public Integer frame = null;

	private List<PiccodeInfo> notes = new ArrayList<>();

	public PiccodeWarning(String file, int line, int col, String message) {
		this.file = file;
		this.line = line;
		this.col = col;
		this.message = message;
	}

	public void addNote(PiccodeInfo note) {
		this.notes.add(note);
	}

	public void report() {
		report(false, "WARNING");
	}

	@Override
	public void report(boolean die, String kind) {
		var fp = new File("");

		String[] lines = new String[]{};
		if (file != null) {
			fp = new File(file);
			lines = toLines(fp);
		}

		var errorKind = Compiler.errorKind;
		if (errorKind == ErrorAsciiKind.GLEAM_STYLE) {
			printGleamStyleError(kind, lines, fp);
		} else {
			printEmacsCompStyleError(kind, lines, fp);
		}

		if (!notes.isEmpty()) {
			out.println((Chalk.on(".").yellow() + "\n").repeat(2));
			for (var note : notes) {
				note.report(false, "INFO");
			}
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

	private void printGleamStyleError(String kind, String[] lines, File fp) {
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
			out.println("" + fmt);
			return;
		}

		var index = line - 1 < lines.length ? line - 1 : lines.length - 1;
		if (index < 0) {
			index = 0;
		}
		var code_line = lines[index].replaceAll("\t", " ".repeat(1));
		out.println(gap2 + fmt);
		out.println(line_fmt + " " + code_line);
		var tick = "─".repeat(col + 1) + "╯";
		var tick2 = " ".repeat(col + 1) + "^";
		out.println(gap2 + "│" + tick2);
		out.println(gap2 + "╰" + tick);
		if (line + 1 < lines.length) {
			line_fmt = String.format(" %d │", line + 1);
			out.println(line_fmt);
		}
	}

	private void printEmacsCompStyleError(String kind, String[] lines, File fp) {
		var line_fmt = String.format(" %d │", line);
		var gap2 = " ".repeat(line_fmt.length() - 1);
		var fmt = String.format(
						"%s:%d:%d: %s: %s",
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
			out.println("" + fmt);
			return;
		}

		var index = line - 1 < lines.length ? line - 1 : lines.length - 1;
		if (index < 0) {
			index = 0;
		}
		var code_line = lines[index].replaceAll("\t", " ".repeat(1));
		out.println(fmt);
		out.println(line_fmt + " " + code_line);
		var tick2 = " ".repeat(col + 1) + "^";
		out.println(gap2 + " " + tick2);
		if (line + 1 < lines.length) {
			line_fmt = String.format(" %d │", line + 1);
			out.println(line_fmt);
		}
	}

}
