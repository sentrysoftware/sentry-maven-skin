package com.sentrysoftware.doc.mavenskin;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndexToolTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testBuildElasticLunrIndex() throws Exception {

		// The index will be stored in a temporary file
		Path indexPath = Files.createTempFile("test-indexing", ".json");

		// Get a page content
		String studioAgentBody = HtmlToolTest.getResourceAsString("/studio-agent.html");
		HtmlTool htmlTool = new HtmlTool();
		String studioAgentText = htmlTool.text(studioAgentBody, "body").get(0);

		// Add it to the index, twice! (it's supposed to be updated properly and the page present only once)
		IndexTool.buildElasticLunrIndex(indexPath.toString(), "agent.html", "Agent", "testagent,dragon", studioAgentText);
		IndexTool.buildElasticLunrIndex(indexPath.toString(), "agent.html", "Agent", "testagent", studioAgentText);

		// And now add another fake entry
		IndexTool.buildElasticLunrIndex(indexPath.toString(), "fake.html", "Fake", "fake", "This is a fake test.");

		String indexContent = new String(Files.readAllBytes(indexPath), "UTF-8");

		assertFalse(indexContent.contains("{\"id\":\"agent.html\",\"title\":\"Agent\",\"keywords\":\"testagent,dragon\""));
		assertTrue(indexContent.contains("{\"id\":\"agent.html\",\"title\":\"Agent\",\"keywords\":\"testagent\""));
		assertTrue(indexContent.contains("{\"id\":\"fake.html\",\"title\":\"Fake\",\"keywords\":\"fake\""));

		// Delete the temporary file
		Files.delete(indexPath);
	}


}
