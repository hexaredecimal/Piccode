package org.editor.fs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hexaredecimal
 */
public class FilePersistance {
	private static final List<String> EMPTY = new ArrayList<>();
	private static final String RECENT_FILES = "./etc/rf/xxx.yy";
	private static final String RECENT_PROJECTS = "./etc/rf/xxx.yy";

	/**
	 * Retrieves a list of recently accessed files.
	 *
	 * @return a list of up to 15 recent file paths as strings; returns an empty list if none are found or on error
	 */
	public static List<String> getRecentFiles() {
		return getFiles(RECENT_FILES);
	}

	/**
	 * Retrieves a list of recently accessed projects from persistent storage.
	 *
	 * @return a list of recent project paths as strings, or an empty list if none are found
	 */
	public static List<String> getRecentProjects() {
		return getFiles(RECENT_PROJECTS);
	}

	/**
	 * Checks if the specified file path is present in the list of recently persisted files.
	 *
	 * @param path the file path to check
	 * @return true if the path exists in the recent files list; false otherwise
	 */
	public static boolean isFilePersisted(Path path) {
		var name = path.toString();
		return getRecentFiles().contains(name);
	}

	/**
	 * Checks if the given project path is present in the list of recent files.
	 *
	 * @param path the project path to check
	 * @return true if the path exists in the recent files list; false otherwise
	 */
	public static boolean isProjectPersisted(Path path) {
		var name = path.toString();
		return getRecentFiles().contains(name);
	}

	/**
	 * Adds the specified project path to the list of recent projects if not already present.
	 *
	 * @param path the project path to persist
	 */
	public static void persistProject(Path path) {
		persistTo(path, RECENT_PROJECTS);
	}
	
	/**
	 * Adds the specified file path to the list of recent projects if not already present.
	 *
	 * @param path the file path to persist as a recent project
	 */
	public static void persistFile(Path path) {
		persistTo(path, RECENT_PROJECTS);
	}

	
	/**
	 * Adds the specified path to the list of recent files and writes the updated list to the given directory path, unless the path is already present.
	 *
	 * If the path already exists in the recent files list, no action is taken. Any I/O errors during writing are logged.
	 *
	 * @param path the file or project path to persist
	 * @param dir the file system path where the updated list should be written
	 */
	private static void persistTo(Path path, String dir) {
		if (isFilePersisted(path)) {
			return;
		}
		var files = getRecentFiles();
		files.addFirst(path.toString());
		var dest = new File(dir).toPath();
		try {
			Files.write(dest, files);
		} catch (IOException ex) {
			Logger.getLogger(FilePersistance.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	/**
	 * Reads up to 15 lines from the specified file into a mutable list of strings.
	 *
	 * If the file does not exist, it is created and an empty list is returned. If an I/O error occurs during reading or creation, an empty list is returned.
	 *
	 * @param file the path to the file to read
	 * @return a mutable list containing up to 15 lines from the file, or an empty list if the file does not exist or cannot be read
	 */
	private static List<String> getFiles(String file) {
		var fp = new File(file);

		
		if (!fp.exists()) {
			try {
				fp.createNewFile();
				return EMPTY;
			} catch (IOException ex) {
				return EMPTY;
			}
		}

		try {
			var contents = Files.readString(fp.toPath());
			var items = contents
				.lines()
				.limit(15) // TODO: Allow this to be customizable throw the settings
				.toList();
			var mutableList = new ArrayList<String>();
			items.forEach(item -> mutableList.add(item));
			return mutableList;
		} catch (IOException ex) {
			return EMPTY;
		}
	}
}
