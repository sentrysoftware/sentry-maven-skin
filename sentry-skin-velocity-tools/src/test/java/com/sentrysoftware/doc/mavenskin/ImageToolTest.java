package com.sentrysoftware.doc.mavenskin;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageToolTest {

	private static final int MAX_SIZE = 100;

	@Test
	void testGetExtension() {
		assertEquals("jpg", ImageTool.getExtension(new File("/tmp/test.jpg")), "Extension of regular file must be retrieved");
		assertEquals("", ImageTool.getExtension(new File("/tmp/test")), "File with no extension must return empty string");
		assertEquals("", ImageTool.getExtension(new File("/tmp/test.")), "File with empty extension must return empty string");
	}

	@Test
	void testGetNameWithoutExtension() {
		assertEquals("test", ImageTool.getNameWithoutExtension(new File("/tmp/test.jpg")), "Extension of regular file must be retrieved");
		assertEquals("test", ImageTool.getNameWithoutExtension(new File("/tmp/test")), "File with no extension must return name");
		assertEquals("", ImageTool.getNameWithoutExtension(new File("/tmp/.test")), "File with no name (.ext) must return empty string");
	}

	@Test
	void testCreateThumbnailMaxHeight() throws Exception {

		List<String> imageTestList = Arrays.asList("eiffel.jpg", "api.gif", "homer.png", "beatles.webp");

		for (String imageTestName : imageTestList) {

			// Create a temporary file (on the filesystem)
			Path tempImagePath = Files.createTempFile("testImageThumbnail", imageTestName);
			Files.copy(ImageToolTest.class.getResourceAsStream("/" + imageTestName), tempImagePath, StandardCopyOption.REPLACE_EXISTING);
			tempImagePath.toFile().deleteOnExit();

			// Create the thumbnail
			File resultFile = ImageTool.createThumbnail(tempImagePath.toFile(), "-tn", 0, MAX_SIZE);
			resultFile.deleteOnExit();

			// Check the file name result
			String expectedName = ImageTool.getNameWithoutExtension(tempImagePath.toFile()) + "-tn.jpg";
			assertTrue(resultFile.getName().endsWith(expectedName), "Thumbnail name must contain the mark and finish with the same extension");

			assertTrue(resultFile.exists(), "The thumbnail file has been created");
			BufferedImage resultImage = ImageIO.read(resultFile);
			assertEquals(MAX_SIZE, resultImage.getHeight(), "Thumbnail must be max height of " + MAX_SIZE);

		}

	}

	@Test
	void testCreateThumbnailMaxWidth() throws Exception {

		List<String> imageTestList = Arrays.asList("eiffel.jpg", "api.gif", "homer.png", "beatles.webp");

		for (String imageTestName : imageTestList) {

			// Create a temporary file (on the filesystem)
			Path tempImagePath = Files.createTempFile("testImageThumbnail", imageTestName);
			Files.copy(ImageToolTest.class.getResourceAsStream("/" + imageTestName), tempImagePath, StandardCopyOption.REPLACE_EXISTING);
			tempImagePath.toFile().deleteOnExit();

			// Create the thumbnail
			File resultFile = ImageTool.createThumbnail(tempImagePath.toFile(), "-tn", MAX_SIZE, 0);
			resultFile.deleteOnExit();

			// Check the file name result
			String expectedName = ImageTool.getNameWithoutExtension(tempImagePath.toFile()) + "-tn.jpg";
			assertTrue(resultFile.getName().endsWith(expectedName), "Thumbnail name must contain the mark and finish with the same extension");

			assertTrue(resultFile.exists(), "The thumbnail file has been created");
			BufferedImage resultImage = ImageIO.read(resultFile);
			assertEquals(MAX_SIZE, resultImage.getWidth(), "Thumbnail must be max width of " + MAX_SIZE);

		}

	}

	@Test
	void testsaveImageFileAsWebp() throws Exception {

		List<String> imageTestList = Arrays.asList("eiffel.jpg", "api.gif", "homer.png", "beatles.webp");

		for (String imageTestName : imageTestList) {

			// Create a temporary file (on the filesystem)
			Path tempImagePath = Files.createTempFile("testImageWebp", imageTestName);
			Files.copy(ImageToolTest.class.getResourceAsStream("/" + imageTestName), tempImagePath, StandardCopyOption.REPLACE_EXISTING);

			// Save the file as webp
			File webpFile = ImageTool.saveImageFileAsWebp(tempImagePath.toFile());

			// If originally a webp, it's supposed to return null
			if (imageTestName.toLowerCase().endsWith(".webp")) {

				assertNull(webpFile, "Must return null if image is already WEBP");

			} else {

				assertTrue(webpFile.exists(), "The WEBP file must have been created");
				assertTrue(webpFile.getName().toLowerCase().endsWith(".webp"), "Resulting file extension must be .webp");

				webpFile.deleteOnExit();

			}

		}

	}

}
