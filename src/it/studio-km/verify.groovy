// Verify that the files have been created properly
assert new File(basedir, "target/site/index.html").isFile()

// Verify that the created HTML files contain the proper information
String result = new File(basedir, "target/site/events.html").text

assert result.contains("<title>Dealing with Events &ndash; skin-test Extended</title>") : "Document title is set according to source's metadata"
assert result.contains('<meta name="description" content="Monitoring Studio X allows the operators') : "Document's description is set according to source's metadata"
assert result.contains('<meta name="keywords" content="event,testevent,blank space,studio,km,patrol,develop,web" />') : "Document's keywords is a merge of source's keywords and site.xml's keywords"
assert result =~ /<meta name="generator" content="Maven Site Plugin, Doxia Site Renderer .*, Skin sentry-maven-skin .*, from markdown"/ : "Document's generator is set correctly"
assert result.contains('<meta name="author" content="The &quot;Proud&quot; People" />') : "Document's author is set according to source's metadata"

assert result.contains("<b>skin-test</b> allows you") : "pom.xml properties must be replaced with their values"

assert result =~ '<li><a +href="https://youtu.be/Th6NweyurWs">YouTube</a></li>' : "Links specified in site.xml are added to the HTML"

assert new File(basedir, "target/site/images/Events-thumbnail.jpg").isFile() : "JPG thumbnails have been generated"
assert new File(basedir, "target/site/images/Events_Details-thumbnail.jpg").isFile() : "JPG thumbnails have been generated"

assert new File(basedir, "target/site/images/Events.webp").isFile() : "Images have been converted to WEBP"
assert new File(basedir, "target/site/images/Events_Details.webp").isFile() : "Images have been converted to WEBP"

assert result =~ '<zoomable.*images/Events-thumbnail\\.jpg' : "Zoomable picture refers to the thumbnail"
assert result =~ /<zoomable.*min-width: *200px;/ : "Zoomable thumbnail is max width 200px"
assert result =~ /<zoomable.*min-height: *101px;/ : "Zoomable thumbnail height is calculated as 101px"
assert result =~ /<zoomable.*max-width: *1895px;/ : "Zoomable max width is the picture width"
assert result =~ /<zoomable.*max-height: *963px;/ : "Zoomable max height is the picture height"

assert result =~ '<source srcset="(\\./)?images/Events\\.webp" type="image/webp">' : "Images refer to their WEBP version"

assert result =~ 'images/Events\\.png".*width="1895"' : "Images have their width set"
assert result =~ 'images/Events\\.png".*height="963"' : "Images have their height set"
assert result =~ 'images/Events\\.png".*width: *1895px;' : "Images have their style set with width"
assert result =~ 'images/Events\\.png".*height: *963px;' : "Images have their style set with height"

assert result.contains('<table border="0" class="bodyTable table table-striped table-hover">')
assert result.contains('<h2 id="filtering-events">Filtering Events</h2>')
assert result.contains('<h2 id="keyboard-shortcuts-28special-29">Keyboard Shortcuts (special)</h2>')
assert result.contains('<li><a href="#keyboard-shortcuts-28special-29" du-smooth-scroll="">Keyboard Shortcuts (special)</a></li>')

// TOC has been inserted
assert result =~ /(?s)toc-inline-container.*Table of Contents.*ul id="toc"/
assert result.indexOf('id="right-toc"') > -1 : "A copy of the TOC has been inserted"
assert result.indexOf('id="toc"') == result.lastIndexOf('id="toc"') : "The ID of the 2nd TOC has been changed (no duplicate with same ID)"

assert result.contains('<body class="sentry-site sentry-purple"')
assert result =~ /(?s)header-title.*skin-test Extended.*header-subtitle.*Version <strong>1.0-SNAPSHOT-test/
assert result =~ /<h5.*Getting Started/
assert result =~ /href="console.html".*Operating the Console/
assert result =~ /class="active".*href="events.html".*Managing Events/
assert result =~ /href="subdir\/agent.html".*Configuring the Agent/
assert result =~ /(?s)<div class="toc">.*<li><a href="#filtering-events" du-smooth-scroll="">Filtering Events/

// Document's footer
assert result =~ /Keywords:/ : "Keywords section is present"
assert result =~ '<span class="label label-default">event</span>' : "Document keywords are listed"
assert result =~ '<span class="label label-default">testevent</span>' : "Document keywords are listed (#2)"
assert result =~ '<span class="label label-default">patrol</span>' : "site.xml keywords are listed"
assert result =~ '<span class="label label-default">develop</span>' : "site.xml keywords are listed (#2)"
assert result =~ /Date:.*2021-01-01/ : "Document's date metadata is present in the body"
assert result =~ /Author:.*The &quot;Proud&quot; People/ : "Document's author metadata is present in the body"

// Page's footer
assert result =~ /(?s)<footer class="footer">.*skin-test Extended 1.0-SNAPSHOT-test/
assert result =~ /Documentation as of.*1975-03-24 19:30:00/ : "Publish date is derived from site.xml, which uses a pom.xml property"
assert result =~ /Copyright.*1975.*20[1-9][0-9]/ : "inceptionYear must be displayed in the copyright"
assert result =~ /The Organization/ : '${project.organization.name} must be displayed in the footer'
assert result =~ /https:\/\/the\.org/ : '${project.organization.url} must be displayed in the footer'

// Rendering time
assert result =~ /<!-- Rendering time: [0-9.]+ ms -->/

// Google Analytics
assert result.contains("https://www.googletagmanager.com/gtag/js?id=MY_GOOGLE_ID") : "Specific googleAnalyticsAccountId must be inserted"

// bannerLeft and bannerRight are included
assert result.contains("Banner Left") : "bannerLeft.name must be included"
assert result.contains("images/logo-short.png") : "bannerLeft.src must be included"
assert result.contains('href="https://banner.left"') : "bannerLeft.src must be included"

assert result.contains("Banner Right") : "bannerRight.name must be included"
assert result.contains("images/logo.png") : "bannerRight.src must be included"
assert result.contains('href="https://banner.right"') : "bannerRight.src must be included"

// Verify documents in a subdir
def agentFile = new File(basedir, "target/site/subdir/agent.html")
assert agentFile.isFile() : "Documents in subdir have been rendered"

def agentHtml = agentFile.text
assert agentHtml =~ '\\.\\./images/MS_X_Architecture_Diagram-subdir-thumbnail\\.jpg' : "Thumbnail references work in subdir"
assert agentHtml =~ '\\.\\./images/MS_X_Architecture_Diagram-subdir\\.webp' : "WEBP reference work in subdir"
assert agentHtml =~ '\\.\\./images/MS_X_Architecture_Diagram-subdir\\.png.*width="1723"' : "Image size work in subdir"

assert agentHtml.contains("\"../js/") : "Page in subdir must refer to ../js/"
assert !agentHtml.contains("\"js/") && !agentHtml.contains("\"./js/") : "All references to JS must refer to parent dir"

assert agentHtml.contains("\"../css/") : "Page in subdir must refer to ../css/"
assert !agentHtml.contains("\"css/") && !agentHtml.contains("\"./css/") : "All references to CSS must refer to parent dir"

assert agentHtml.contains("<title>Configuring the Agent &ndash; skin-test Extended</title>") : "Document title is set according to source's first heading"

// Verify that there is no protocol-relative links left
assert !(agentHtml =~ '"//') : "URLs must not be protocol-relative"

// Verify that index.json contains the proper information
indexJsonFile = new File(basedir, "target/site/index.json")
assert indexJsonFile.isFile()
String indexJson = indexJsonFile.text
def eventsGood = indexJson.contains('"events.html":{"id":"events.html","title":"Dealing with Events","keywords":"event,testevent,blank space,studio,km,patrol,develop,web"')
assert eventsGood : "The index must contain title and keywords from the source metadata"

// Basic Monitors topic contains 6 subtopics
def basicHtml = new File(basedir, "target/site/basic-monitors/index.html").text
assert basicHtml =~ /Basic Monitors.*6/ : "Basic Monitors entry must say it has 6 subitems in the left menu"
assert basicHtml.contains("../basic-monitors/filesystem.html") : "Link to filesystem.html must be present"
assert basicHtml.contains("../basic-monitors/process.html") : "Link to process.html must be present"

// Process topic lists its parents (as defined in site.xml)
def processHtml = new File(basedir, "target/site/basic-monitors/process.html").text
assert processHtml =~ /\bHome\b/ : "Home must be in the breadcrumb"
assert processHtml =~ /Using Monitoring Studio/ : "'Using Monitoring Studio' must be in the breadcrumb"
assert processHtml =~ /Basic Monitors/ : "'Basic Monitors' must be in the breadcrumb"

// Code highlighting
// Not enabled for basic-monitors/index.html (because there is no code)
assert !basicHtml.contains("prism.js") : "Page without code must not load prism.js"
// Enabled for extend-summary.html (because it has code)
def extendHtml = new File(basedir, "target/site/extend-summary.html").text
assert extendHtml.contains("prism.js") : "Page with code must load prism.js"

// Icons
def iconsHtml = new File(basedir, "target/site/icons.html").text
assert !iconsHtml.contains("close.gif") : "In icons.html, image close.gif must have been removed"
assert iconsHtml.contains('<i class="fa-regular fa-rectangle-xmark"></i>') : "In icons.html, the fa-circle-xmark icon must have been inserted"
assert !iconsHtml.contains("icon_error_sml.gif") : "In icons.html, all instances of images that represent icons must have been removed"

// Also check that the page doesn't mention Sentry
assert !iconsHtml.contains("Sentry") : "Sentry must not be mentioned anywhere by the skin itself"