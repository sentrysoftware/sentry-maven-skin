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
// TEST: Configuration - No Thumbnails (convertImagesToThumbnails: false)
// ============================================================================
def noThumbnailsFile = new File(basedir, "target/site/config/no-thumbnails.html")
assert noThumbnailsFile.isFile() : "no-thumbnails.html must have been generated"
def noThumbnailsHtml = noThumbnailsFile.text
assert !noThumbnailsHtml.contains("<zoomable") : "Page with convertImagesToThumbnails:false must not have zoomable elements"
assert noThumbnailsHtml.contains("<img") : "Page must still have img elements"

// ============================================================================
// TEST: Configuration - No Syntax Highlighting (syntaxHighlighting: false)
// ============================================================================
def noSyntaxFile = new File(basedir, "target/site/config/no-syntax-highlighting.html")
assert noSyntaxFile.isFile() : "no-syntax-highlighting.html must have been generated"
def noSyntaxHtml = noSyntaxFile.text
assert !noSyntaxHtml.contains('src="../js/prism.js"') : "Page with syntaxHighlighting:false must not load prism.js script"
assert !noSyntaxHtml.contains('src="js/prism.js"') : "Page with syntaxHighlighting:false must not load prism.js script"
assert noSyntaxHtml.contains("<code class=\"language-") : "Page must still have code blocks"

// ============================================================================
// TEST: Configuration - No Copy Button (copyToClipboard: false)
// ============================================================================
def noCopyFile = new File(basedir, "target/site/config/no-copy-button.html")
assert noCopyFile.isFile() : "no-copy-button.html must have been generated"
def noCopyHtml = noCopyFile.text
assert !noCopyHtml.contains('copy-to-clipboard=""') : "Page with copyToClipboard:false must not have copy-to-clipboard attribute"
assert noCopyHtml.contains("<pre>") : "Page must still have pre elements"

// ============================================================================
// TEST: Configuration - Custom Body Class
// ============================================================================
def customBodyFile = new File(basedir, "target/site/config/custom-body-class.html")
assert customBodyFile.isFile() : "custom-body-class.html must have been generated"
def customBodyHtml = customBodyFile.text
assert customBodyHtml.contains('class="sentry-site custom-page-class my-special-theme"') : "Body must have custom classes from frontmatter"

// ============================================================================
// TEST: Configuration - All Disabled
// ============================================================================
def allDisabledFile = new File(basedir, "target/site/config/all-disabled.html")
assert allDisabledFile.isFile() : "all-disabled.html must have been generated"
def allDisabledHtml = allDisabledFile.text
assert allDisabledHtml.contains('class="sentry-site minimal-page"') : "Body must have minimal-page class"
assert !allDisabledHtml.contains("<zoomable") : "Must not have zoomable elements"
assert !allDisabledHtml.contains("prism.js") : "Must not load prism.js"
assert !allDisabledHtml.contains('copy-to-clipboard=""') : "Must not have copy-to-clipboard"
// Check that ${project.version} is NOT interpolated (should appear literally)
assert allDisabledHtml.contains('${project.version}') : "With interpolation:none, ${project.version} should appear literally"

// ============================================================================
// TEST: Configuration - No Search Index
// ============================================================================
def noSearchFile = new File(basedir, "target/site/config/no-search-index.html")
assert noSearchFile.isFile() : "no-search-index.html must have been generated"
// This page should NOT be in the search index (but we can't easily verify partial index content)
// The page should not have search UI elements if buildIndex is disabled per-page
// Note: buildIndex typically affects index building, not UI. Per-page it affects this page's inclusion.

// ============================================================================
// TEST: Configuration - No Image Processing
// ============================================================================
def noImageProcFile = new File(basedir, "target/site/config/no-image-processing.html")
assert noImageProcFile.isFile() : "no-image-processing.html must have been generated"
def noImageProcHtml = noImageProcFile.text
assert !noImageProcHtml.contains("<zoomable") : "Must not have zoomable elements when thumbnails disabled"
// Note: WebP conversion check would require checking actual image files

// ============================================================================
// All tests passed!
// ============================================================================
println "SUCCESS: All Maven Site Plugin 4.x integration tests passed!"
