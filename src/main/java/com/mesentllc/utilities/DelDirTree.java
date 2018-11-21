package com.mesentllc.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DelDirTree {
	private static final Log logger = LogFactory.getLog(DelDirTree.class);

	public static void main(String[] args) throws IOException {
		long timer = System.currentTimeMillis();
		if (args.length != 1) {
			System.out.println("Specify a file path to delete.");
			logger.info("Recursive Directory Delete called without starting path - exiting.");
			System.exit(1);
		}
		logger.info("Recursive Directory Delete Started - Deleting path: " + args[0]);
		DelDirTree delDirTree = new DelDirTree();
		delDirTree.process(args[0]);
		logger.info("Recursive Directory Delete Completed in " + Utilities.explode(System.currentTimeMillis() - timer));
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
