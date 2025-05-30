package org.piccode.platf;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author hexaredecimal
 */
public class Platforms {
	
	public static boolean isWindows() {
		return getName().toLowerCase().contains("win");
	}

	public static boolean isMac() {
		return getName().toLowerCase().contains("mac");
	}

	public static boolean isLinux() {
		var os = getName().toLowerCase();
		return os.contains("nix") || os.contains("nux") || os.contains("aix");
	}

	public static String getName() {
		return System.getProperty("os.name");
	}
	
	public static String getArch() {
		return System.getProperty("os.arch");
	}
	
	public static void setWindowsCodePage() {
		try {
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
		} catch (UnsupportedEncodingException ex) {
			// TODO: Log the message
		}
	}
}
