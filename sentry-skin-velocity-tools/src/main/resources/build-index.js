/**
 * addDocumentToElasticLunr
 **/
var addDocumentToElasticLunr = function(indexJson, id, title, keywords, body) {
	
	var idx;
	
	// New or existing index?
	if (indexJson == null || indexJson == "") {

		// Create a new index
		idx = elasticlunr(function () {
		    this.addField("title");
		    this.addField("body");
		    this.addField("keywords");
		    this.setRef("id");
		    this.saveDocument(true);
		});
		
	} else {
		
		// Load the specified index
		indexJsonObj = JSON.parse(indexJson);
		idx = elasticlunr.Index.load(indexJsonObj);
	}
	
	// Add the specified document
	idx.addDoc({
		id: id,
		title: title,
		keywords: keywords,
		body: body
	});
	
	// Return the JSON-serialized index
	return JSON.stringify(idx);

}