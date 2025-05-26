package org.piccode.piccodescript;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.SysexMessage;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import static net.sourceforge.argparse4j.impl.Arguments.storeTrue;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import org.piccode.backend.Compiler;
import org.piccode.rt.PiccodeString;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class Piccode {

  private static double VERSION = 0.1;

	public static void main(String[] args) {
		run(args);
	}

	private static void run(String[] args) {
		var parser = ArgumentParsers.newFor("piccode").build()
						.defaultHelp(true)
						.version(String.format("PicodeScript %s", VERSION))
						.epilog(about())
						.version("${prog} 0.1")
						.description("The compiler for the PiccodeScript programming language");

		var subparsers = parser.addSubparsers()
						.title("subcommands")
						.metavar("COMMAND")
						.dest("command");

		// glmr build
		var build = subparsers.addParser("build")
						.defaultHelp(true)
						.help("Compile the project");

		build.addArgument("--emit")
						.action(storeTrue())
						.help("Print the generated JavaScript to stdout");

		build.addArgument("--target")
						.setDefault(TargetEnvironment.JS)
						.type(TargetEnvironment.class)
						.choices(TargetEnvironment.JS)
						.help("Specify the target environment to build for");

		build.addArgument("--outfile")
						.setDefault("./target/build.js")
						.help("Set the output file path");

		build.addArgument("file")
						.nargs("?") // Optional
						.help("Path to a specific source file to build");

		// glmr dump
		var dump = subparsers.addParser("dump")
						.defaultHelp(true)
						.help("Prints internal IR and other structures");

		dump.addArgument("tc")
						.nargs("?")
						.help("Print functions after typecheck");

		// glmr run
		var run = subparsers.addParser("run")
						.defaultHelp(true)
						.help("Execute after builing");

		run.addArgument("--repl")
						.dest("repl")
						.setDefault(false)
						.action(Arguments.storeTrue())
						.help("Launch the REPL instead of executing a file");

		run.addArgument("file")
						.nargs("?") // Optional
						.help("Path to a specific source file to build");

		run.addArgument("--")
						.nargs("*")
						.dest("extra_args")
						.help("Pass-through args");

		parser.addArgument("--version").action(Arguments.version());

		// optional: glmr help
		var help = subparsers.addParser("help")
						.help("Show help for a subcommand")
						.defaultHelp(false);

		help.addArgument("topic")
						.help("The topic or subcommand to show help for");

		try {
			Namespace res = parser.parseArgs(args);
			String command = res.getString("command");

			if ("build".equals(command)) {
				/*System.out.println("==> build:");
				System.out.println("Emit: " + res.getBoolean("emit"));
				System.out.println("Target: " + res.getString("target"));
				System.out.println("Outfile: " + res.getString("outfile"));
				 */
				System.out.println("TODO: Implement build command");
				return;
			} else if ("dump".equals(command)) {
				System.out.println("==> dump:");
				System.out.println("TC arg: " + res.getString("tc"));
			} else if ("run".equals(command)) {
				List<PiccodeValue> user_args = makeUserArgs(res.getList("extra_args"));
				String input = res.getString("file");
				boolean repl = res.getBoolean("repl");
				if (repl) {
					repl(user_args);
				} else {
					if (input == null && isProject()) {
						System.out.println("Project run");
						return;
					} else if (input == null) {
						System.out.println("No input file to run");
						return;
					}
					var code = readFile(input);
					Compiler.compile(input, code, user_args);
				}
			} else if ("help".equals(command)) {
				String topic = res.getString("topic");
				parser.parseArgs(new String[]{topic, "--help"});
			}
		} catch (ArgumentParserException e) {
			parser.handleError(e);
		}
	}

	public static String readFile(String file) {
		try {
			return Files.readString(new File(file).toPath());
		} catch (IOException ex) {
			System.out.println("" + ex.getMessage());
			System.exit(1);
		}
		return null;
	}

	private static String about() {
		return String.format("""
▄▖▘       ▌  ▄▖    ▘  ▗ 
▙▌▌▛▘▛▘▛▌▛▌█▌▚ ▛▘▛▘▌▛▌▜▘
▌ ▌▙▖▙▖▙▌▙▌▙▖▄▌▙▖▌ ▌▙▌▐▖
                    ▌   
       [v%s]
""", VERSION).indent(8);
	}

	private static boolean isProject() {
		var fp = new File("./project.toml");
		return fp.exists();
	}

	private static void repl(List<PiccodeValue> user_args) {
		Compiler.prepareGlobalScope();
		try (Scanner scanner = new Scanner(System.in);) {
			System.out.println(String.format("Welcome to the REPL for PiccodeScript v%s", VERSION));

			while (true) {
				StringBuilder inputBlock = new StringBuilder();
				while (true) {
					System.out.print("λ ");
					String line = scanner.nextLine();

					if (line.trim().isEmpty()) {
						break; // empty or just whitespace — end of block
					}

					inputBlock.append(line).append("\n");
				}

				String code = inputBlock.toString();
				if (code.trim().isEmpty()) {
					continue;
				}

				var result = Compiler.compile("repl", code, user_args);
				System.out.println(result);
				// Optionally continue loop for next input block
			}
		}
	}

	private static List<PiccodeValue> makeUserArgs(List<Object> list) {
		var args = new ArrayList<PiccodeValue>();
		list.forEach(item -> args.add(new PiccodeString(item.toString())));
		return args;
	}
}
