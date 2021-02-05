package com.sentrysoftware.doc.mavenskin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Helper {

	/**
	 * Write a text file using System.lineSeparator()
	 * <p>
	 * @param file The file instance to write to
	 * @param text The text to write
	 * @param charset The charset to use when writing text
	 * @throws IOException when anything goes wrong
	 */
	public static void writeText(File file, String text, Charset charset) throws IOException {

		// Write the result using system line separator
		try (
				BufferedReader reader = new BufferedReader(new StringReader(text));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
		) {
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}
		}
	}

	/**
	 * Returns the time of last modification of specified Path in milliseconds since
	 * EPOCH.
	 *
	 * @param path
	 *            Path to the file
	 * @return Milliseconds since EPOCH, or 0 (zero) if file does not exist
	 */
	public static long getLastModifiedTime(Path path) {

		try {
			return Files.getLastModifiedTime(path, LinkOption.NOFOLLOW_LINKS).toMillis();
		} catch (IOException e) {
			return 0;
		}
	}

	/**
	 * Returns the time of last modification of specified File in milliseconds since
	 * EPOCH.
	 *
	 * @param file
	 *            File instance
	 * @return Milliseconds since EPOCH, or 0 (zero) if file does not exist
	 */
	public static long getLastModifiedTime(File file) {
		return getLastModifiedTime(Paths.get(file.toURI()));
	}

	/**
	 * Returns the time of last modification of specified file in milliseconds since
	 * EPOCH.
	 *
	 * @param filePathString
	 *            Path to the file (as a String)
	 * @return Milliseconds since EPOCH, or 0 (zero) if file does not exist
	 */
	public static long getLastModifiedTime(String filePathString) {
		return getLastModifiedTime(Paths.get(filePathString));
	}

}
