package com.sentrysoftware.doc.mavenskin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class HelperTest {

	@Test
	void testTestLastModifiedTime() throws IOException {

		File testFile = File.createTempFile("temp", ".test");
		testFile.deleteOnExit();
		long timestamp = System.currentTimeMillis();
		Helper.writeText(testFile, "test", Charset.defaultCharset());

		assertEquals(
				Helper.getLastModifiedTime(testFile),
				Helper.getLastModifiedTime(testFile.getAbsolutePath()),
				"Value obtained from String or File must be identical"
		);

		assertEquals(
				Helper.getLastModifiedTime(testFile),
				Helper.getLastModifiedTime(Paths.get(testFile.toURI())),
				"Value obtained from Path or File must be identical"
		);

		long timeDiff = Helper.getLastModifiedTime(testFile) - timestamp;
		assertTrue(timeDiff >= -1000, "File should be marked as modified after timestamp (timeDiff " + timeDiff + " should be > 0 ms)");

	}


}
