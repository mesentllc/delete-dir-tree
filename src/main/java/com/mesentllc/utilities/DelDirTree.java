package com.mesentllc.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DelDirTree {
	private static final Log logger = LogFactory.getLog(DelDirTree.class);

	public static void main(String[] args) throws IOException {
		long timer = System.currentTimeMillis();
		String path;

		if (args.length != 1) {
			path = getBackupDrive();
		}
		else {
			path = args[0];
		}
		if (path == null) {
			System.out.println("Specify a file path to delete.");
			logger.info("Recursive Directory Delete called without starting path - exiting.");
			System.exit(1);
		}
		logger.info("Recursive Directory Delete Started - Deleting path: " + path);
		DelDirTree delDirTree = new DelDirTree();
		try {
			delDirTree.process(path);
		}
		catch (NoSuchFileException nsfe) {
			logger.info(path + " doesn't exist -- exiting.");
		}
		logger.info("Recursive Directory Delete Completed in " + Utilities.explode(System.currentTimeMillis() - timer));
	}

	private static String getBackupDrive() {
		Calendar now = Calendar.getInstance();
		List<File> roots = Arrays.asList(File.listRoots());
		for (File root : roots) {
			String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(root);
			if (s1.startsWith("Secured BU")) {
				return String.format("%sBackup\\Day_%d", root.getAbsolutePath(), now.get(Calendar.DATE));
			}
		}
		return null;
	}

	private void process(String dirName) throws IOException {
		Path directory = Paths.get(dirName);
		Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.setAttribute(file, "dos:readonly", false);
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.setAttribute(dir, "dos:readonly", false);
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
