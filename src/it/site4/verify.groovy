// Verify that the Maven Site Plugin 4.x integration test works correctly
// This script runs after the site is generated to verify all features work

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

// Helper to parse HTML file with JSoup
def parseHtml = { File file ->
    Jsoup.parse(file, "UTF-8")
}

// Verify that the files have been created properly
def indexFile = new File(basedir, "target/site/index.html")
assert indexFile.isFile() : "index.html must have been generated"

// Parse the index.html content
Document indexDoc = parseHtml(indexFile)
def indexHtml = indexFile.text // Keep raw text for some specific tests

// ============================================================================
// TEST: Site name and title from site.xml
// ============================================================================
assert indexDoc.text().contains("Site4 Test Project") : "Site name from site.xml must be present"
assert indexDoc.select("title").size() > 0 : "HTML title must be present"

// ============================================================================
// TEST: Custom properties
// ============================================================================
assert indexDoc.text().contains("1.0-SNAPSHOT-site4") : "Custom projectVersion from site.xml must be present"
assert indexDoc.select('body.sentry-site.sentry-green').size() > 0 : "Custom bodyClass must be applied"
assert indexDoc.select('.version-prefix:contains(Release)').size() > 0 : "projectVersionText from site.xml must be used in header"
assert indexDoc.select('.toc-heading:contains(On This Page (site.xml))').size() > 0 : "tocHeadingText from site.xml must be used"
assert indexDoc.select('input[placeholder="Search this documentation (site.xml)..."]').size() > 0 : "searchFieldText from site.xml must be used"
assert indexDoc.select('.search-results h2:contains(Search results in this documentation for)').size() > 0 : "searchResultsText from site.xml must be used"
assert indexDoc.html().contains("'1': 'result in this doc'") : "searchResultSingleText from site.xml must be used"
assert indexDoc.html().contains("'other': 'results in this doc'") : "searchResultCountText from site.xml must be used"
assert indexDoc.select('footer.footer :contains(Published on)').size() > 0 : "publishDateText from site.xml must be used"
assert indexDoc.select('footer.footer .copyright:contains(All rights reserved)').size() > 0 : "copyrightText from site.xml must be used"

// ============================================================================
// TEST: Google Analytics
// ============================================================================
assert indexHtml.contains("SITE4_GOOGLE_ID") : "Google Analytics ID must be inserted"

// ============================================================================
// TEST: Banner left and right (with new <image> sub-element syntax)
// ============================================================================
assert indexDoc.select('a[href=https://example.org]').size() > 0 : "bannerLeft.href must be included"
assert indexDoc.select(':contains(Banner Left)').size() > 0 : "bannerLeft.name must be included"
assert indexDoc.select('img[src=images/icon.png]').size() > 0 : "bannerLeft.image.src must be included (new 4.x syntax)"
assert indexDoc.select('img[alt=Banner Left Logo]').size() > 0 : "bannerLeft.image.alt must be included (new 4.x syntax)"
assert indexDoc.select('a[href=https://maven.apache.org]').size() > 0 : "bannerRight.href must be included"
assert indexDoc.select(':contains(Banner Right)').size() > 0 : "bannerRight.name must be included"
assert indexDoc.select('img[src=images/test-image.png]').size() > 0 : "bannerRight.image.src must be included (new 4.x syntax)"
assert indexDoc.select('img[alt=Banner Right Logo]').size() > 0 : "bannerRight.image.alt must be included (new 4.x syntax)"

// ============================================================================
// TEST: Navigation menus
// ============================================================================
assert indexDoc.select(':contains(Getting Started)').size() > 0 : "Menu 'Getting Started' must be present"
assert indexDoc.select(':contains(Content Examples)').size() > 0 : "Menu 'Content Examples' must be present"
assert indexDoc.select(':contains(Advanced)').size() > 0 : "Menu 'Advanced' must be present"
assert indexDoc.select('a[href=features.html]').size() > 0 : "Menu item link to features.html must be present"

// ============================================================================
// TEST: Breadcrumbs
// ============================================================================
assert indexDoc.select(':contains(Docs Home)').size() > 0 : "Breadcrumb 'Docs Home' must be present"

// ============================================================================
// TEST: Social links
// ============================================================================
assert indexDoc.select('a[href*=twitter.com/MavenProject]').size() > 0 : "Twitter link must be present"
assert indexDoc.select('a[href*=linkedin.com]').size() > 0 : "LinkedIn link must be present"

// ============================================================================
// TEST: Additional links
// ============================================================================
assert indexDoc.select(':contains(License)').size() > 0 : "Additional link 'License' must be present"
assert indexDoc.select(':contains(Contact)').size() > 0 : "Additional link 'Contact' must be present"

// ============================================================================
// TEST: Footer
// ============================================================================
def footer = indexDoc.select('footer.footer')
assert footer.text().contains("Test Organization") : "Organization name must be in footer"
assert footer.text().contains("2024") : "Inception year must be in footer"

// ============================================================================
// TEST: schema.org JSON-LD metadata
// ============================================================================
assert indexHtml.contains('"@context": "https://schema.org"') : "schema.org context must be present"
assert indexHtml.contains('"@type": "TechArticle"') : "schema.org type must be TechArticle"

// ============================================================================
// TEST: Generator meta tag shows skin info
// ============================================================================
assert indexDoc.select('meta[name=generator]').attr('content') =~ /Maven Site Plugin, Doxia Site Renderer .*, Skin sentry-maven-skin/ : "Generator meta must mention the skin"

// ============================================================================
// TEST: Features page with metadata
// ============================================================================
def featuresFile = new File(basedir, "target/site/features.html")
assert featuresFile.isFile() : "features.html must have been generated"
Document featuresDoc = parseHtml(featuresFile)
assert featuresDoc.select('meta[name=keywords]').attr('content').startsWith('features,site4,maven,documentation') : "Keywords from frontmatter must be present"
assert featuresDoc.select('meta[name=description]').attr('content').startsWith('Complete list of features') : "Description from frontmatter must be present"
assert featuresDoc.select('meta[name=author]').attr('content') == 'Test Author' : "Author from frontmatter must be present"
assert featuresDoc.select('.version-prefix:contains(Build)').size() > 0 : "projectVersionText from frontmatter must override site.xml"
assert featuresDoc.select('.toc-heading:contains(In this Page)').size() > 0 : "tocHeadingText from frontmatter must override site.xml"
assert featuresDoc.select('input[placeholder="Search this documentation..."]').size() > 0 : "searchFieldText from frontmatter must override site.xml"
assert featuresDoc.select('.search-results h2:contains(Results in this page for)').size() > 0 : "searchResultsText from frontmatter must override site.xml"
assert featuresDoc.html().contains("'1': 'match in this page'") : "searchResultSingleText from frontmatter must override site.xml"
assert featuresDoc.html().contains("'other': 'matches in this page'") : "searchResultCountText from frontmatter must override site.xml"
assert featuresDoc.select('footer.footer :contains(Documentation as of (frontmatter))').size() > 0 : "publishDateText from frontmatter must override site.xml"
assert featuresDoc.select('footer.footer .copyright:contains(Protected by copyright)').size() > 0 : "copyrightText from frontmatter must override site.xml"

// ============================================================================
// TEST: Interpolation (maven mode)
// ============================================================================
// site4 uses interpolation mode "maven" (configured in site.xml)
def interpolationTestFile = new File(basedir, "target/site/interpolation-test.html")
assert interpolationTestFile.isFile() : "interpolation-test.html must have been generated"
def interpolationTestHtml = interpolationTestFile.text

// Helper to check table rows: Expected and Actual columns should match
// Pattern: <td>Expected</td>\s*<td>Actual</td> at end of row
def checkTableRow = { html, expected, actual, description ->
    // Look for consecutive <td> cells with expected and actual values
    def pattern = ~/<td>\s*\Q${expected}\E\s*<\/td>\s*<td>\s*\Q${actual}\E\s*<\/td>/
    assert pattern.matcher(html).find() : description
}

// In "maven" mode, ${project.name} should be resolved (Expected=Actual=resolved value)
checkTableRow(interpolationTestHtml, "Site4 Test Project", "Site4 Test Project",
    'In maven mode, ${project.name} should resolve: Expected and Actual must both be "Site4 Test Project"')
checkTableRow(interpolationTestHtml, "1.0-SNAPSHOT", "1.0-SNAPSHOT",
    'In maven mode, ${project.version} should resolve: Expected and Actual must both be "1.0-SNAPSHOT"')
checkTableRow(interpolationTestHtml, "Custom Value from site.xml", "Custom Value from site.xml",
    'In maven mode, ${testProperty} should resolve: Expected and Actual must both be "Custom Value from site.xml"')

// In "maven" mode, escaping should work: $${property} -> ${property} (literal)
checkTableRow(interpolationTestHtml, '${project.name}', '${project.name}',
    'In maven mode, $${project.name} should escape: Expected and Actual must both be literal "${project.name}"')
checkTableRow(interpolationTestHtml, '${project.version}', '${project.version}',
    'In maven mode, $${project.version} should escape: Expected and Actual must both be literal "${project.version}"')

// In "maven" mode, hash characters should NOT be interpreted as Velocity directives
checkTableRow(interpolationTestHtml, "#include", "#include",
    'In maven mode, hash characters must be preserved: Expected and Actual must both be "#include"')
checkTableRow(interpolationTestHtml, "Fix #123", "Fix #123",
    'In maven mode, issue references must work: Expected and Actual must both be "Fix #123"')

// In "maven" mode, Velocity escape sequences are NOT processed (remain literal)
// Check that ${esc.h} appears in both Expected and Actual (with possible suffix like "(literal)")
assert interpolationTestHtml =~ /<td>\s*\$\{esc\.h\}[^<]*<\/td>\s*<td>\s*\$\{esc\.h\}\s*<\/td>/ :
    'In maven mode, ${esc.h} must remain literal in both Expected and Actual columns'
assert interpolationTestHtml =~ /<td>\s*\$\{esc\.d\}[^<]*<\/td>\s*<td>\s*\$\{esc\.d\}\s*<\/td>/ :
    'In maven mode, ${esc.d} must remain literal in both Expected and Actual columns'

// Unknown properties should remain as-is
checkTableRow(interpolationTestHtml, '${unknown.property}', '${unknown.property}',
    'In maven mode, unknown properties must remain literal: Expected and Actual must both be "${unknown.property}"')

// ============================================================================
// TEST: Interpolation (velocity mode)
// ============================================================================
def interpolationVelocityFile = new File(basedir, "target/site/interpolation-velocity.html")
assert interpolationVelocityFile.isFile() : "interpolation-velocity.html must have been generated"
def interpolationVelocityHtml = interpolationVelocityFile.text

// In "velocity" mode, ${project.name} should be resolved
checkTableRow(interpolationVelocityHtml, "Site4 Test Project", "Site4 Test Project",
    'In velocity mode, ${project.name} should resolve: Expected and Actual must both be "Site4 Test Project"')
checkTableRow(interpolationVelocityHtml, "1.0-SNAPSHOT", "1.0-SNAPSHOT",
    'In velocity mode, ${project.version} should resolve: Expected and Actual must both be "1.0-SNAPSHOT"')

// In "velocity" mode, $esc.h and $esc.d are processed
checkTableRow(interpolationVelocityHtml, "#", "#",
    'In velocity mode, $esc.h should resolve to "#": Expected and Actual must both be "#"')
checkTableRow(interpolationVelocityHtml, '$', '$',
    'In velocity mode, $esc.d should resolve to "$": Expected and Actual must both be "$"')

// NOTE: The following tests verify velocity mode LIMITATIONS
// ${esc.h}include: Expected column has \#include in markdown, but after markdown processing
//                  it becomes #include which Velocity interprets as directive (now empty)
//                  Actual column has ${esc.h}include which correctly resolves to "#include"
assert interpolationVelocityHtml =~ /<td><code>\$\{esc\.h\}include<\/code><\/td>\s*<td>\s*<\/td>\s*<td>#include<\/td>/ :
    'In velocity mode, ${esc.h}include row: Expected is empty (Velocity ate #include), Actual is "#include"'

// NOTE: ${esc.d}{project.name} demonstrates a velocity mode limitation
// Expected column had "${project.name}" which got interpolated to "Site4 Test Project"
// Actual column has ${esc.d}{project.name} which resolves to literal "${project.name}"
assert interpolationVelocityHtml =~ /<td><code>\$\{esc\.d\}\{project\.name\}<\/code><\/td>\s*<td>Site4 Test Project<\/td>\s*<td>\$\{project\.name\}<\/td>/ :
    'In velocity mode, ${esc.d}{project.name} row: Expected is interpolated, Actual is literal'

// Hash character tests: $esc.h produces "#" which works correctly
checkTableRow(interpolationVelocityHtml, "# include", "# include",
    'In velocity mode, $esc.h include should resolve to "# include"')
checkTableRow(interpolationVelocityHtml, "Fix # 123", "Fix # 123",
    'In velocity mode, Fix $esc.h 123 should resolve to "Fix # 123"')

// ============================================================================
// TEST: Interpolation (none mode)
// ============================================================================
def interpolationNoneFile = new File(basedir, "target/site/interpolation-none.html")
assert interpolationNoneFile.isFile() : "interpolation-none.html must have been generated"
def interpolationNoneHtml = interpolationNoneFile.text

// In "none" mode, nothing should be interpolated - all placeholders remain literal
checkTableRow(interpolationNoneHtml, '${project.name}', '${project.name}',
    'In none mode, ${project.name} must remain literal: Expected and Actual must both be "${project.name}"')
checkTableRow(interpolationNoneHtml, '${project.version}', '${project.version}',
    'In none mode, ${project.version} must remain literal: Expected and Actual must both be "${project.version}"')
checkTableRow(interpolationNoneHtml, '${testProperty}', '${testProperty}',
    'In none mode, ${testProperty} must remain literal: Expected and Actual must both be "${testProperty}"')

// In "none" mode, even $${...} is not processed (remains as-is)
checkTableRow(interpolationNoneHtml, '$${project.name}', '$${project.name}',
    'In none mode, $${project.name} must remain literal: Expected and Actual must both be "$${project.name}"')

// In "none" mode, Velocity escape sequences remain literal
checkTableRow(interpolationNoneHtml, '${esc.h}', '${esc.h}',
    'In none mode, ${esc.h} must remain literal: Expected and Actual must both be "${esc.h}"')
checkTableRow(interpolationNoneHtml, '${esc.d}', '${esc.d}',
    'In none mode, ${esc.d} must remain literal: Expected and Actual must both be "${esc.d}"')
assert interpolationNoneHtml.contains('>${esc.d}<') : 'In none mode, ${esc.d} remains literal'

// Also test that the index page has maven interpolation working
// Hash characters should NOT be interpreted as Velocity directives in maven mode
assert indexHtml.contains("#include &lt;stdio.h&gt;") : "Hash characters must be preserved in maven mode (not interpreted as Velocity)"
assert indexHtml.contains("Fix #123") : "Hash in issue reference must be preserved"
// Maven properties should be replaced
assert indexHtml.contains("Maven Property Value") : "Custom Maven properties from pom.xml <properties> must be replaced"
assert indexHtml.contains("1.0-SNAPSHOT") : "Project version must be replaced via maven interpolation"

// ============================================================================
// TEST: Code highlighting
// ============================================================================
def codeSamplesFile = new File(basedir, "target/site/code-samples.html")
assert codeSamplesFile.isFile() : "code-samples.html must have been generated"
Document codeSamplesDoc = parseHtml(codeSamplesFile)
def codeSamplesHtml = codeSamplesFile.text
assert codeSamplesHtml.contains("prism.js") : "Page with code must load prism.js"
assert codeSamplesDoc.select('code.language-java').size() > 0 : "Java code block must have language class"
assert codeSamplesDoc.select('pre[copy-to-clipboard]').size() > 0 : "<pre> blocks must have copy-to-clipboard attribute"

// ============================================================================
// TEST: TOC macro works with Maven Site Plugin 4.x / Doxia 2.x
// ============================================================================
assert indexDoc.select('.toc-inline-container').size() > 0 : "TOC inline container must exist"
assert indexDoc.select('#toc').size() == 1 : "Only one element with id=toc (no duplicates)"
assert indexDoc.select('#right-toc').size() > 0 : "A copy of the TOC has been inserted in the sidebar"

// ============================================================================
// TEST: Tables
// ============================================================================
def tablesFile = new File(basedir, "target/site/tables.html")
assert tablesFile.isFile() : "tables.html must have been generated"
Document tablesDoc = parseHtml(tablesFile)
assert tablesDoc.select('table.bodyTable.table.table-striped.table-hover').size() > 0 : "Tables must have Bootstrap classes"

// ============================================================================
// TEST: Nested navigation (subdirectory)
// ============================================================================
def advancedIndexFile = new File(basedir, "target/site/advanced/index.html")
assert advancedIndexFile.isFile() : "advanced/index.html must have been generated"
Document advancedIndexDoc = parseHtml(advancedIndexFile)
assert advancedIndexDoc.select('link[href^=../css/]').size() > 0 : "Page in subdir must refer to ../css/"
assert advancedIndexDoc.select('script[src^=../js/]').size() > 0 : "Page in subdir must refer to ../js/"

def topicAFile = new File(basedir, "target/site/advanced/topic-a.html")
assert topicAFile.isFile() : "advanced/topic-a.html must have been generated"
Document topicADoc = parseHtml(topicAFile)
// Verify breadcrumb parents are shown
assert topicADoc.select(':contains(Advanced)').size() > 0 : "Parent menu 'Advanced' should be in breadcrumb"

// ============================================================================
// TEST: UI Components
// Now using the simplified Markdown blockquote syntax exclusively.
// ============================================================================
def uiComponentsFile = new File(basedir, "target/site/ui-components.html")
assert uiComponentsFile.isFile() : "ui-components.html must have been generated"
Document uiComponentsDoc = parseHtml(uiComponentsFile)
def uiComponentsHtml = uiComponentsFile.text
// Basic structure check - div elements should be present
assert uiComponentsDoc.select('div').size() > 0 : "Div elements must be present"
assert uiComponentsDoc.text().contains('UI Components') : "Page title must be present"

// ============================================================================
// TEST: Tabs ([!TABS] blockquote syntax)
// ============================================================================
assert uiComponentsDoc.select('uib-tabset').size() > 0 : "UIB tabset element must be present"
assert uiComponentsDoc.select('uib-tab').size() > 0 : "UIB tab element must be present"
assert uiComponentsDoc.select('uib-tabset[active=demoTabs]').size() > 0 : "Custom active variable must be set"
assert uiComponentsDoc.select('uib-tabset[justified=true]').size() > 0 : "Justified attribute must be parsed"
assert uiComponentsDoc.select('uib-tab-heading').text().contains('First') : "Tab heading must be extracted"
assert uiComponentsDoc.select('uib-tab-heading').size() > 0 : "Tab headings must use uib-tab-heading"
assert uiComponentsDoc.select('i.fa-home').size() > 0 : "Tab heading HTML must be preserved"
assert uiComponentsDoc.text().contains('first tab') : "Tab content must be preserved"
assert uiComponentsDoc.text().contains('Tab 1') : "Basic tabs must be generated"

// ============================================================================
// TEST: Accordion ([!ACCORDION] blockquote syntax)
// ============================================================================
assert uiComponentsDoc.select('uib-accordion').size() > 0 : "UIB accordion element must be present"
assert uiComponentsDoc.text().contains('Section 1') : "Accordion panel title must be extracted"
assert uiComponentsDoc.text().contains('Section 2') : "Accordion panel title must be extracted"

// ============================================================================
// TEST: Collapsible sections ([!COLLAPSIBLE] blockquote syntax)
// ============================================================================
assert uiComponentsDoc.select('[uib-collapse]').size() > 0 : "UIB collapse directive must be present for collapsible sections"
assert uiComponentsDoc.text().contains('Click to expand this FAQ item') : "Collapsible title must be extracted correctly"
assert uiComponentsDoc.text().contains('collapsed content') : "Collapsible content must be preserved"
assert uiComponentsDoc.select('i[class*=fa-chevron]').size() > 0 : "Chevron icons must be present in toggle button"
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
// TEST: Configuration - No Thumbnails (convertImagesToThumbnails: false)
// ============================================================================
def noThumbnailsFile = new File(basedir, "target/site/config/no-thumbnails.html")
assert noThumbnailsFile.isFile() : "no-thumbnails.html must have been generated"
Document noThumbnailsDoc = parseHtml(noThumbnailsFile)
assert noThumbnailsDoc.select('zoomable').size() == 0 : "Page with convertImagesToThumbnails:false must not have zoomable elements"
assert noThumbnailsDoc.select('img').size() > 0 : "Page must still have img elements"

// ============================================================================
// TEST: Configuration - No Syntax Highlighting (syntaxHighlighting: false)
// ============================================================================
def noSyntaxFile = new File(basedir, "target/site/config/no-syntax-highlighting.html")
assert noSyntaxFile.isFile() : "no-syntax-highlighting.html must have been generated"
Document noSyntaxDoc = parseHtml(noSyntaxFile)
def noSyntaxHtml = noSyntaxFile.text
assert noSyntaxDoc.select('script[src*=prism.js]').size() == 0 : "Page with syntaxHighlighting:false must not load prism.js script"
assert noSyntaxDoc.select('code[class^=language-]').size() > 0 : "Page must still have code blocks"

// ============================================================================
// TEST: Configuration - No Copy Button (copyToClipboard: false)
// ============================================================================
def noCopyFile = new File(basedir, "target/site/config/no-copy-button.html")
assert noCopyFile.isFile() : "no-copy-button.html must have been generated"
Document noCopyDoc = parseHtml(noCopyFile)
assert noCopyDoc.select('pre[copy-to-clipboard]').size() == 0 : "Page with copyToClipboard:false must not have copy-to-clipboard attribute"
assert noCopyDoc.select('pre').size() > 0 : "Page must still have pre elements"

// ============================================================================
// TEST: Configuration - Custom Body Class
// ============================================================================
def customBodyFile = new File(basedir, "target/site/config/custom-body-class.html")
assert customBodyFile.isFile() : "custom-body-class.html must have been generated"
Document customBodyDoc = parseHtml(customBodyFile)
assert customBodyDoc.select('body.sentry-site.custom-page-class.my-special-theme').size() > 0 : "Body must have custom classes from frontmatter"

// ============================================================================
// TEST: Configuration - All Disabled
// ============================================================================
def allDisabledFile = new File(basedir, "target/site/config/all-disabled.html")
assert allDisabledFile.isFile() : "all-disabled.html must have been generated"
Document allDisabledDoc = parseHtml(allDisabledFile)
def allDisabledHtml = allDisabledFile.text
assert allDisabledDoc.select('body.sentry-site.minimal-page').size() > 0 : "Body must have minimal-page class"
assert allDisabledDoc.select('zoomable').size() == 0 : "Must not have zoomable elements"
assert !allDisabledHtml.contains("prism.js") : "Must not load prism.js"
assert allDisabledDoc.select('pre[copy-to-clipboard]').size() == 0 : "Must not have copy-to-clipboard"
// Check that ${project.version} is NOT interpolated (should appear literally)
assert allDisabledHtml.contains('${project.version}') : "With interpolation:none, ${project.version} should appear literally"
// Check that externalLinkClass:false removes the externalLink class from external links
assert allDisabledDoc.select('a.externalLink').size() == 0 : "With externalLinkClass:false, external links must not have externalLink class"

// ============================================================================
// TEST: Configuration - No Search Index
// ============================================================================
def noSearchFile = new File(basedir, "target/site/config/no-search-index.html")
assert noSearchFile.isFile() : "no-search-index.html must have been generated"
Document noSearchDoc = parseHtml(noSearchFile)

// Verify this page is NOT in the search index (reuse indexJson from earlier)
assert !indexJson.contains('"config/no-search-index.html"') : "Page with buildIndex:false must not be in index.json"

// Verify AI indexing artifacts are NOT generated when buildAiIndex:false
def noSearchMdFile = new File(basedir, "target/site/config/no-search-index.html.md")
assert !noSearchMdFile.exists() : "Page with buildAiIndex:false must not have .html.md file generated"

// Verify llms.txt does not list this page
def llmsTxtContent = new File(basedir, "target/site/llms.txt").text
assert !llmsTxtContent.contains("no-search-index.html") : "Page with buildAiIndex:false must not be listed in llms.txt"

// Verify search UI is still present (buildIndex:false per-page should NOT hide search UI)
assert noSearchDoc.select('[site-search]').size() > 0 || noSearchDoc.html().contains('site-search') : "Search UI must still be present even when buildIndex:false per-page"

// ============================================================================
// TEST: Configuration - No Image Processing
// ============================================================================
def noImageProcFile = new File(basedir, "target/site/config/no-image-processing.html")
assert noImageProcFile.isFile() : "no-image-processing.html must have been generated"
Document noImageProcDoc = parseHtml(noImageProcFile)
assert noImageProcDoc.select('zoomable').size() == 0 : "Must not have zoomable elements when thumbnails disabled"
// Note: WebP conversion check would require checking actual image files

// ============================================================================
// All tests passed!
// ============================================================================
println "SUCCESS: All Maven Site Plugin 4.x integration tests passed!"
