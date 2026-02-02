// Verify that the Maven Site Plugin 4.x integration test works correctly
// This script runs after the site is generated to verify all features work

// Verify that the files have been created properly
def indexFile = new File(basedir, "target/site/index.html")
assert indexFile.isFile() : "index.html must have been generated"

// Read the index.html content
def indexHtml = indexFile.text

// ============================================================================
// TEST: Site name and title from site.xml
// ============================================================================
assert indexHtml.contains("Site4 Test Project") : "Site name from site.xml must be present"
assert indexHtml.contains("<title>") : "HTML title must be present"

// ============================================================================
// TEST: Custom properties
// ============================================================================
assert indexHtml.contains("1.0-SNAPSHOT-site4") : "Custom projectVersion from site.xml must be present"
assert indexHtml.contains('class="sentry-site sentry-green"') : "Custom bodyClass must be applied"

// ============================================================================
// TEST: Google Analytics
// ============================================================================
assert indexHtml.contains("SITE4_GOOGLE_ID") : "Google Analytics ID must be inserted"

// ============================================================================
// TEST: Banner left and right
// ============================================================================
// TEST: Banner left and right (with new <image> sub-element syntax)
// ============================================================================
assert indexHtml.contains('href="https://example.org"') : "bannerLeft.href must be included"
assert indexHtml.contains("Banner Left") : "bannerLeft.name must be included"
assert indexHtml.contains('src="images/icon.png"') : "bannerLeft.image.src must be included (new 4.x syntax)"
assert indexHtml.contains('alt="Banner Left Logo"') : "bannerLeft.image.alt must be included (new 4.x syntax)"
assert indexHtml.contains('href="https://maven.apache.org"') : "bannerRight.href must be included"
assert indexHtml.contains("Banner Right") : "bannerRight.name must be included"
assert indexHtml.contains('src="images/test-image.png"') : "bannerRight.image.src must be included (new 4.x syntax)"
assert indexHtml.contains('alt="Banner Right Logo"') : "bannerRight.image.alt must be included (new 4.x syntax)"

// ============================================================================
// TEST: Navigation menus
// ============================================================================
assert indexHtml.contains("Getting Started") : "Menu 'Getting Started' must be present"
assert indexHtml.contains("Content Examples") : "Menu 'Content Examples' must be present"
assert indexHtml.contains("Advanced") : "Menu 'Advanced' must be present"
assert indexHtml.contains('href="features.html"') : "Menu item link to features.html must be present"

// ============================================================================
// TEST: Breadcrumbs
// ============================================================================
assert indexHtml.contains("Docs Home") : "Breadcrumb 'Docs Home' must be present"

// ============================================================================
// TEST: Social links
// ============================================================================
assert indexHtml.contains("twitter.com/MavenProject") : "Twitter link must be present"
assert indexHtml.contains("linkedin.com") : "LinkedIn link must be present"

// ============================================================================
// TEST: Additional links
// ============================================================================
assert indexHtml.contains("License") : "Additional link 'License' must be present"
assert indexHtml.contains("Contact") : "Additional link 'Contact' must be present"

// ============================================================================
// TEST: Footer
// ============================================================================
assert indexHtml.contains("Test Organization") : "Organization name must be in footer"
assert indexHtml.contains("2024") : "Inception year must be in footer"

// ============================================================================
// TEST: schema.org JSON-LD metadata
// ============================================================================
assert indexHtml.contains('"@context": "https://schema.org"') : "schema.org context must be present"
assert indexHtml.contains('"@type": "TechArticle"') : "schema.org type must be TechArticle"

// ============================================================================
// TEST: Generator meta tag shows skin info
// ============================================================================
assert indexHtml =~ /<meta name="generator" content="Maven Site Plugin, Doxia Site Renderer .*, Skin sentry-maven-skin/ : "Generator meta must mention the skin"

// ============================================================================
// TEST: Features page with metadata
// ============================================================================
def featuresFile = new File(basedir, "target/site/features.html")
assert featuresFile.isFile() : "features.html must have been generated"
def featuresHtml = featuresFile.text
assert featuresHtml.contains('<meta name="keywords" content="features,site4,maven,documentation') : "Keywords from frontmatter must be present"
assert featuresHtml.contains('<meta name="description" content="Complete list of features') : "Description from frontmatter must be present"
assert featuresHtml.contains('<meta name="author" content="Test Author"') : "Author from frontmatter must be present"

// ============================================================================
// TEST: Code highlighting
// ============================================================================
def codeSamplesFile = new File(basedir, "target/site/code-samples.html")
assert codeSamplesFile.isFile() : "code-samples.html must have been generated"
def codeSamplesHtml = codeSamplesFile.text
assert codeSamplesHtml.contains("prism.js") : "Page with code must load prism.js"
assert codeSamplesHtml.contains("language-java") : "Java code block must have language class"
assert codeSamplesHtml.contains("<pre copy-to-clipboard") : "<pre> blocks must have copy-to-clipboard attribute"

// ============================================================================
// TEST: TOC macro works with Maven Site Plugin 4.x / Doxia 2.x
// ============================================================================
assert indexHtml =~ /(?s)toc-inline-container.*Table of Contents.*ul id="toc"/ : "TOC must be inserted inline"
assert indexHtml.indexOf('id="right-toc"') > -1 : "A copy of the TOC has been inserted in the sidebar"
assert indexHtml.indexOf('id="toc"') == indexHtml.lastIndexOf('id="toc"') : "The ID of the 2nd TOC must be changed (no duplicate)"

// ============================================================================
// TEST: Tables
// ============================================================================
def tablesFile = new File(basedir, "target/site/tables.html")
assert tablesFile.isFile() : "tables.html must have been generated"
def tablesHtml = tablesFile.text
assert tablesHtml.contains('class="bodyTable table table-striped table-hover"') : "Tables must have Bootstrap classes"

// ============================================================================
// TEST: Nested navigation (subdirectory)
// ============================================================================
def advancedIndexFile = new File(basedir, "target/site/advanced/index.html")
assert advancedIndexFile.isFile() : "advanced/index.html must have been generated"
def advancedIndexHtml = advancedIndexFile.text
assert advancedIndexHtml.contains("../css/") : "Page in subdir must refer to ../css/"
assert advancedIndexHtml.contains("../js/") : "Page in subdir must refer to ../js/"

def topicAFile = new File(basedir, "target/site/advanced/topic-a.html")
assert topicAFile.isFile() : "advanced/topic-a.html must have been generated"
def topicAHtml = topicAFile.text
// Verify breadcrumb parents are shown
assert topicAHtml.contains("Advanced") : "Parent menu 'Advanced' should be in breadcrumb"

// ============================================================================
// TEST: UI Components
// Now using the simplified Markdown blockquote syntax exclusively.
// ============================================================================
def uiComponentsFile = new File(basedir, "target/site/ui-components.html")
assert uiComponentsFile.isFile() : "ui-components.html must have been generated"
def uiComponentsHtml = uiComponentsFile.text
// Basic structure check - div elements should be present
assert uiComponentsHtml.contains('<div') : "Div elements must be present"
assert uiComponentsHtml.contains('UI Components') : "Page title must be present"

// ============================================================================
// TEST: Tabs ([!TABS] blockquote syntax)
// ============================================================================
assert uiComponentsHtml.contains('<uib-tabset') : "UIB tabset element must be present"
assert uiComponentsHtml.contains('<uib-tab') : "UIB tab element must be present"
assert uiComponentsHtml.contains('active="demoTabs"') : "Custom active variable must be set"
assert uiComponentsHtml.contains('justified="true"') : "Justified attribute must be parsed"
assert uiComponentsHtml.contains('First') && uiComponentsHtml.contains('Tab') : "Tab heading must be extracted"
assert uiComponentsHtml.contains('<uib-tab-heading>') : "Tab headings must use uib-tab-heading"
assert uiComponentsHtml.contains('fa-home') : "Tab heading HTML must be preserved"
assert uiComponentsHtml.contains('first tab') : "Tab content must be preserved"
assert uiComponentsHtml.contains('Tab 1') : "Basic tabs must be generated"

// ============================================================================
// TEST: Accordion ([!ACCORDION] blockquote syntax)
// ============================================================================
assert uiComponentsHtml.contains('<uib-accordion') : "UIB accordion element must be present"
assert uiComponentsHtml.contains('Section 1') : "Accordion panel title must be extracted"
assert uiComponentsHtml.contains('Section 2') : "Accordion panel title must be extracted"

// ============================================================================
// TEST: Collapsible sections ([!COLLAPSIBLE] blockquote syntax)
// ============================================================================
assert uiComponentsHtml.contains('uib-collapse=') : "UIB collapse directive must be present for collapsible sections"
assert uiComponentsHtml.contains('Click to expand this FAQ item') : "Collapsible title must be extracted correctly"
assert uiComponentsHtml.contains('collapsed content') : "Collapsible content must be preserved"
assert uiComponentsHtml.contains('fa-chevron') : "Chevron icons must be present in toggle button"
assert !uiComponentsHtml.contains('[!COLLAPSIBLE]') : "Collapsible marker must be removed from output"

// ============================================================================
// TEST: Images
// ============================================================================
def imagesFile = new File(basedir, "target/site/images.html")
assert imagesFile.isFile() : "images.html must have been generated"
// Test image should have been processed
def testImageFile = new File(basedir, "target/site/images/test-image.png")
assert testImageFile.isFile() : "test-image.png must exist"

// ============================================================================
// TEST: llms.txt file generation
// ============================================================================
def llmsFile = new File(basedir, "target/site/llms.txt")
assert llmsFile.isFile() : "llms.txt file must have been generated"
def llmsContent = llmsFile.text
assert llmsContent.contains("Site4 Test Project") : "llms.txt must contain site name"
assert llmsContent.contains("## Getting Started") : "llms.txt must contain menu sections"

// ============================================================================
// TEST: Search index
// ============================================================================
def indexJsonFile = new File(basedir, "target/site/index.json")
assert indexJsonFile.isFile() : "index.json search index must have been generated"
def indexJson = indexJsonFile.text
assert indexJson.contains('"index.html"') : "Search index must contain index.html"
assert indexJson.contains('"features.html"') : "Search index must contain features.html"

// ============================================================================
// TEST: Markdown AI files (.html.md)
// ============================================================================
def mdFile = new File(basedir, "target/site/features.html.md")
assert mdFile.isFile() : "features.html.md must have been generated for AI indexing"
def mdContent = mdFile.text
assert mdContent.contains("# Features") || mdContent.contains("# Feature List") : "Markdown AI file must contain content"

// ============================================================================
// TEST: Build log output
// ============================================================================
def buildLog = new File(basedir, "build.log").text
assert buildLog =~ /Rendering time: [0-9.]+ ms/ : "Rendering time must be logged"

// ============================================================================
// TEST: No protocol-relative links
// ============================================================================
assert !(indexHtml =~ '"//') : "URLs must not be protocol-relative"

// ============================================================================
// All tests passed!
// ============================================================================
println "SUCCESS: All Maven Site Plugin 4.x integration tests passed!"
