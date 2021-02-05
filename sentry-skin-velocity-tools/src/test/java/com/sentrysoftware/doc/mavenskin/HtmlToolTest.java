package com.sentrysoftware.doc.mavenskin;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlToolTest {

	private static Pattern WHITES_REGEX = Pattern.compile("\\s+");

	private static String HTML_SOURCE;
	private static Element HTML_ELEMENT;
	private static String AGENT_HTML_SOURCE;
	private static Element AGENT_HTML_ELEMENT;
	private static String GENERAL_HTML_SOURCE;
	private static Element GENERAL_HTML_ELEMENT;

	private static HtmlTool HTML_TOOL;

	@BeforeAll
	static void setUp() throws Exception {

		// What we're testing
		HTML_TOOL = new HtmlTool();

		// Some interesting HTML sources
		HTML_SOURCE = getResourceAsString("/test.html");
		HTML_ELEMENT = HTML_TOOL.parseContent(HTML_SOURCE);
		AGENT_HTML_SOURCE = getResourceAsString("/studio-agent.html");
		AGENT_HTML_ELEMENT = HTML_TOOL.parseContent(AGENT_HTML_SOURCE);
		GENERAL_HTML_SOURCE = getResourceAsString("/studio-general-concepts.html");
		GENERAL_HTML_ELEMENT = HTML_TOOL.parseContent(GENERAL_HTML_SOURCE);
	}

	@Test
	void testAppend() {
		String result = trimWhites(HTML_TOOL.append(HTML_ELEMENT.clone(), "div.my-class", "<ul>Test</ul>", 1));
		assertTrue(result.contains("<ul>Test</ul></div>"));
	}

	@Test
	void testPrepend() {
		String result = trimWhites(HTML_TOOL.prepend(HTML_ELEMENT.clone(), "div.my-class", "<ol>Test</ol>", 1));
		assertTrue(result.contains("<divclass=\"my-class\"><ol>Test</ol>"));
	}

	@Test
	void testWrap() {
		String result = trimWhites(HTML_TOOL.wrap(GENERAL_HTML_ELEMENT.clone(), "ul#toc", "<div class='toc-inline-container hidden-lg'>", 1));
		assertTrue(result.contains("<divclass=\"toc-inline-containerhidden-lg\"><ulid=\"toc\">"));
	}

	@Test
	void testEnsureHeadingIds() {
		String result = trimWhites(HTML_TOOL.ensureHeadingIds(AGENT_HTML_ELEMENT.clone()));
		assertTrue(result.contains("<h1id=\"configuring-the-agent_0\">"));
		assertTrue(result.contains("<h2id=\"managing-user-access-rights-access-control-list_0\">"));

		result = trimWhites(HTML_TOOL.ensureHeadingIds(HTML_TOOL.parseContent("<h3>test</h3><h4>Test</h4>")));
		assertEquals("<h3id=\"test_0\">test</h3><h4id=\"test_1\">Test</h4>", result);

		result = trimWhites(HTML_TOOL.ensureHeadingIds(HTML_TOOL.parseContent("<h3>abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz</h3>")));
		assertEquals("<h3id=\"abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwx_0\">abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz</h3>", result);

	}

	@Test
	void testFixIds() {
		String result = trimWhites(HTML_TOOL.fixIds(AGENT_HTML_ELEMENT.clone()));
		assertTrue(result.contains("<ahref=\"#Managing_User_Access_Rights_28Access_Control_List29\">"));

		result = trimWhites(HTML_TOOL.fixIds(HTML_TOOL.parseContent("<div id=\"bad.id\"></div>")));
		assertEquals("<divid=\"badid\"></div>", result);
	}

	@Test
	void testHeadingAnchorToId() {
		String result = trimWhites(HTML_TOOL.headingAnchorToId(AGENT_HTML_ELEMENT.clone()));
		assertTrue(result.contains("<h2id=\"Defining_a_Default_User_Account\">"));

		result = trimWhites(HTML_TOOL.headingAnchorToId(HTML_TOOL.parseContent("<h6>test</h6><a name='myTest'>")));
		assertTrue(result.contains("<h6id=\"myTest\">"));

		result = trimWhites(HTML_TOOL.headingAnchorToId(HTML_TOOL.parseContent("<a name='myTest' /><h6>test</h6>")));
		assertTrue(result.contains("<h6id=\"myTest\">"));
	}

	@Test
	void testText() {
		List<String> result = HTML_TOOL.text(AGENT_HTML_ELEMENT.clone(), "h1");
		assertEquals(1, result.size());
		assertEquals("Configuring the Agent", result.get(0));
	}

	@Test
	void testTitleText() {
		List<String> result = HTML_TOOL.text(HTML_TOOL.parseContent("<title>Title</title>"), "title");
		assertEquals(1, result.size());
		assertEquals("Title", result.get(0));
	}

	@Test
	void testFixTableHeads() {
		String result = trimWhites(HTML_TOOL.fixTableHeads(HTML_TOOL.parseContent("<table><tbody><tr><th>head</th></tr><tr><td>body</td></tr></tbody></table>")));
		assertEquals("<table><thead><tr><th>head</th></tr></thead><tbody><tr><td>body</td></tr></tbody></table>", result);
	}

	@Test
	void testRemove() {
		String result = trimWhites(HTML_TOOL.remove(AGENT_HTML_ELEMENT.clone(), "ul#toc"));
		assertTrue(result.contains("</blockquote><p><b>skin-test</b>"));
	}

	@Test
	void testAddClass() {
		String result = trimWhites(HTML_TOOL.addClass(AGENT_HTML_ELEMENT.clone(), "ul#toc", "my-class"));
		assertTrue(result.contains("<ulid=\"toc\"class=\"my-class\""));
	}

	@Test
	void testGetAttr() {
		List<String> result = HTML_TOOL.getAttr(AGENT_HTML_ELEMENT.clone(), "a", "href");
		assertNotEquals(0, result.size());
		assertEquals("#Related_Topics", result.get(0));
		assertEquals("//docs.bmc.com/docs/PATROLAgent/113/integration-variables-766670137.html", result.get(result.size() - 1));
	}

	@Test
	void testSetAttr() {
		String result = trimWhites(HTML_TOOL.setAttr(AGENT_HTML_ELEMENT.clone(), "h1", "id", "my-id"));
		assertTrue(result.startsWith("<h1id=\"my-id\">ConfiguringtheAgent</h1>"));
	}

	@Test
	void testfixProtocolRelativeUrls() {
		String result = trimWhites(HTML_TOOL.fixProtocolRelativeUrls(AGENT_HTML_ELEMENT.clone()));
		assertFalse(result.contains("\"//"), "Protocol-relative URLs must have been fixed");
	}

	/**
	 * Reads the specified resource file and returns its content as a String
	 *
	 * @param path Path to the resource file
	 * @return The content of the resource file as a String
	 */
	protected static String getResourceAsString(String path) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(HtmlToolTest.class.getResourceAsStream(path)));
		StringBuilder builder = new StringBuilder();
		String l;
		try {
			while ((l = reader.readLine()) != null) {
				builder.append(l).append('\n');
			}
		} catch (IOException e) {
			return null;
		}

		return builder.toString();
	}

	/**
	 * Remove all white characters (\\s regex, i.e. spaces, tabs and new lines)
	 * @param source The text from which we remove white spaces
	 * @return the same text without any white spaces
	 */
	private static String trimWhites(String source) {
		return WHITES_REGEX.matcher(source).replaceAll("");
	}

	/**
	 * Remove all white characters (\\s regex, i.e. spaces, tabs and new lines)
	 * @param element The HTML Element to parse
	 * @return the HTML source without any white spaces
	 */
	private static String trimWhites(Element element) {
		return WHITES_REGEX.matcher(element.html()).replaceAll("");
	}

}
