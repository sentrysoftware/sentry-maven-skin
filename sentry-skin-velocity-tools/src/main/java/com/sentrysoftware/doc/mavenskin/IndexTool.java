package com.sentrysoftware.doc.mavenskin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.velocity.tools.config.DefaultKey;

@DefaultKey("indexTool")
public class IndexTool {

	/**
	 * UTF-8 Charset
	 */
	final static Charset UTF8_CHARSET = Charset.forName("UTF-8");

	/**
	 * Nashorn engine to execute Javascript
	 */
	final static ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

	static {

		try {
			// Load elasticlunr (http://elasticlunr.com/)
			engine.eval(new InputStreamReader(IndexTool.class.getResourceAsStream("/elasticlunr.min.js")));

			// Load our own JS script
			engine.eval(new InputStreamReader(IndexTool.class.getResourceAsStream("/build-index.js")));

		} catch (ScriptException e) {
			// What can we do in a static statement to handle exceptions? Not much...
		}


	}

	/**
	 * Builds and update the specified elasticlunr.js index.
	 * <p>
	 * If the specified elasticlunr.js index file doesn't exist, it will be created.
	 * Otherwise, it will be updated with the specified document.
	 * <p>
	 * The elasticlunr.js index file is the JSON-serialized index that needs to be loaded
	 * into elasticlunr.js with:
	 * <p>
	 * {@code elasticlunr.Index.load(indexJson);}
	 * <p>
	 * This uses http://elasticlunrjs.com version 0.9.5.
	 * <p>
	 * @param indexPathString Path to the JSON-serialized elasticlunr.js index
	 * @param id ID of the document to add/update (typically it's URL)
	 * @param title Title of the document
	 * @param keywords Keywords of the document (separated with any non alphabetical characters)
	 * @param body Content of the document to be added to the index
	 * @throws IOException when cannot read or write the index file
	 * @throws ScriptException when anything bad happens with the Javascript (should never happen except when developing)
	 * @throws NoSuchMethodException when developer broke the Javascript code
	 */
	public static synchronized void buildElasticLunrIndex(String indexPathString, String id, String title, String keywords, String body) throws IOException, ScriptException, NoSuchMethodException {

		// Read the index file, if any
		String indexJson;
		Path indexPath = Paths.get(indexPathString);
		if (Files.exists(indexPath)) {
			indexJson = new String(Files.readAllBytes(indexPath), UTF8_CHARSET);
		} else {
			indexJson = "";
		}

		// Call our Javascript function
		Invocable invocable = (Invocable)engine;
		String result = (String)invocable.invokeFunction("addDocumentToElasticLunr", indexJson, id, title, keywords, body);

		// Write the result
		Files.write(indexPath, result.getBytes(UTF8_CHARSET));

	}

}
