package org.piccode.piccodescript;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author hexaredecimal
 */

public class GitInfo {

	private static final Properties props = new Properties();

	static {
		try (InputStream in = GitInfo.class.getClassLoader().getResourceAsStream("git.properties")) {
			if (in != null) {
				props.load(in);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load git.properties", e);
		}
	}

	public static String getCommitId() {
		return props.getProperty("git.commit.id.abbrev", "UNKNOWN");
	}

	public static String getBranch() {
		return props.getProperty("git.branch", "UNKNOWN");
	}

	public static String getCommitTime() {
		return props.getProperty("git.commit.time", "UNKNOWN");
	}

	// Add more as needed, there's a ton of fields
}
