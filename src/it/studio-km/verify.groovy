import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

// Helper to parse HTML file with JSoup
def parseHtml = { File file ->
    Jsoup.parse(file, "UTF-8")
}

// ============================================================================
// TEST: Index page exists and basic structure
// ============================================================================
def indexFile = new File(basedir, "target/site/index.html")
assert indexFile.isFile() : "Documents must have been rendered"
Document indexDoc = parseHtml(indexFile)
def indexHtml = indexFile.text // Keep raw text for some specific tests

// Verify that the left menu is correct using CSS selectors
def activeMenuItem = indexDoc.select('li.active > a[href=index.html]')
assert activeMenuItem.size() == 1 : "index.html must be listed exactly once as active entry in menu"
assert indexDoc.select('.version-prefix:contains(Release)').size() > 0 : "projectVersionText from site.xml must be used on pages without frontmatter override"
assert indexDoc.select('.toc-heading:contains(On This Page (site.xml))').size() > 0 : "tocHeadingText from site.xml must be used on pages without frontmatter override"
assert indexDoc.select('input[placeholder=\"Search this documentation (site.xml)...\"]').size() > 0 : "searchFieldText from site.xml must be used on pages without frontmatter override"
assert indexDoc.select('.search-results h2:contains(Search results in this documentation for)').size() > 0 : "searchResultsText from site.xml must be used on pages without frontmatter override"
assert indexDoc.html().contains("'1': 'result in this doc'") : "searchResultSingleText from site.xml must be used on pages without frontmatter override"
assert indexDoc.html().contains("'other': 'results in this doc'") : "searchResultCountText from site.xml must be used on pages without frontmatter override"
assert indexDoc.select('footer.footer :contains(Published on)').size() > 0 : "publishDateText from site.xml must be used on pages without frontmatter override"
assert indexDoc.select('footer.footer .copyright:contains(All rights reserved)').size() > 0 : "copyrightText from site.xml must be used on pages without frontmatter override"

// ============================================================================
// TEST: Events page - metadata and content
// ============================================================================
def eventsFile = new File(basedir, "target/site/events.html")
Document eventsDoc = parseHtml(eventsFile)

// Document metadata using CSS selectors
assert eventsDoc.title() == "Dealing with Events – skin-test Extended" : "Document title is set according to source's metadata"
assert eventsDoc.select('meta[name=description]').attr('content').startsWith('Monitoring Studio X allows the operators') : "Document's description is set"
assert eventsDoc.select('meta[name=keywords]').attr('content') == "event,testevent,blank space,studio,km,patrol,develop,web" : "Document's keywords is a merge"
assert eventsDoc.select('meta[name=generator]').attr('content') =~ /Maven Site Plugin, Doxia Site Renderer .*, Skin sentry-maven-skin .*, from markdown/ : "Document's generator is set"
assert eventsDoc.select('meta[name=author]').attr('content') == 'The "Proud" People' : "Document's author is set"
assert eventsDoc.select('meta[property=article:published_time]').attr('content').startsWith('1980-05-22') : "Document's published time is set"
assert eventsDoc.select('meta[property=article:modified_time]').attr('content').startsWith('1980-05-22') : "Document's modified time is set"
assert eventsDoc.select('link[rel=canonical]').attr('href') == "https://the.org/docs/events.html" : "Document's canonical link is set"
assert eventsDoc.select('link[rel=alternate][type=text/markdown]').attr('href') == "events.html.md" : "Document's alternate link uses .html.md extension"
assert eventsDoc.select('b:contains(skin-test)').size() > 0 : "pom.xml properties must be replaced with their values"

// Links, breadcrumbs, additionalLinks, and social networks
assert eventsDoc.select('a.externalLink[href=https://youtu.be/Th6NweyurWs]:contains(YouTube)').size() > 0 : "Links specified in site.xml are added"
assert eventsDoc.select('a.externalLink[href*=sentrysoftware.com/bmc/products]:contains(Great KMs)').size() > 0 : "Breadcrumbs specified in site.xml are added"
assert eventsDoc.select('a[href=https://the.org/cookies.html]:contains(Privacy)').size() > 0 : "Additional links are added"
assert eventsDoc.select('a[href*=twitter.com/TheASF]').size() > 0 : "Social networks must be added"
assert eventsDoc.select('i.fa-brands.fa-x-twitter').size() > 0 : "Twitter icon must be present"

// Image thumbnails and WEBP conversion
assert new File(basedir, "target/site/images/Events-thumbnail.jpg").isFile() : "JPG thumbnails have been generated"
assert new File(basedir, "target/site/images/Events_Details-thumbnail.jpg").isFile() : "JPG thumbnails have been generated"
assert new File(basedir, "target/site/images/Events.webp").isFile() : "Images have been converted to WEBP"
assert new File(basedir, "target/site/images/Events_Details.webp").isFile() : "Images have been converted to WEBP"

// Zoomable images with proper styling
def zoomable = eventsDoc.select('zoomable').first()
assert zoomable != null : "Zoomable element must exist"
def zoomableStyle = zoomable.attr('style')
assert zoomableStyle.contains('Events-thumbnail.jpg') : "Zoomable picture refers to the thumbnail"
assert zoomableStyle.contains('min-width:') && zoomableStyle.contains('200px') : "Zoomable thumbnail has min-width"
assert zoomableStyle.contains('max-width:') && zoomableStyle.contains('1895px') : "Zoomable max width is the picture width"

// WEBP source elements
assert eventsDoc.select('source[type=image/webp][srcset*=Events.webp]').size() > 0 : "Images refer to their WEBP version"

// Image dimensions
def eventsImg = eventsDoc.select('img[src*=Events.png]').first()
assert eventsImg != null : "Events image must exist"
assert eventsImg.attr('width') == "1895" : "Images have their width attribute set"
assert eventsImg.attr('height') == "963" : "Images have their height attribute set"

// Tables with proper classes
assert eventsDoc.select('table.bodyTable.table.table-striped.table-hover').size() > 0 : "Tables have proper Bootstrap classes"

// Headings with IDs and anchor links
def filteringHeading = eventsDoc.select('h2#filtering-events').first()
assert filteringHeading != null : "Headings must have proper id attribute"
assert filteringHeading.select('a[href=#filtering-events]').size() > 0 : "Headings have anchor links"

def keyboardHeading = eventsDoc.select('h2#keyboard-shortcuts-28special-29').first()
assert keyboardHeading != null : "id attribute in headings must discard special chars"

// TOC verification
assert eventsDoc.select('.toc-inline-container').size() > 0 : "Inline TOC container exists"
assert eventsDoc.select('#toc').size() == 1 : "Only one element with id=toc (no duplicates)"
assert eventsDoc.select('#right-toc').size() > 0 : "Right TOC copy exists with different ID"
assert eventsDoc.select('a[href=#keyboard-shortcuts-28special-29][du-smooth-scroll]').size() > 0 : "TOC links have smooth scroll attribute"

// Body and header structure
assert eventsDoc.select('body.sentry-site.sentry-purple').size() > 0 : "Body has correct classes"
assert eventsDoc.select('.header-title:contains(skin-test Extended)').size() > 0 : "Header title is present"
assert eventsDoc.select('.header-subtitle:contains(1.0-SNAPSHOT-test)').size() > 0 : "Header subtitle with version is present"
assert eventsDoc.select('.version-prefix:contains(Build)').size() > 0 : "projectVersionText from frontmatter must override site.xml"
assert eventsDoc.select('.toc-heading:contains(In this Page)').size() > 0 : "tocHeadingText from frontmatter must override site.xml"
assert eventsDoc.select('input[placeholder=\"Search this documentation...\"]').size() > 0 : "searchFieldText from frontmatter must override site.xml"
assert eventsDoc.select('.search-results h2:contains(Results in this page for)').size() > 0 : "searchResultsText from frontmatter must override site.xml"
assert eventsDoc.html().contains("'1': 'match in this page'") : "searchResultSingleText from frontmatter must override site.xml"
assert eventsDoc.html().contains("'other': 'matches in this page'") : "searchResultCountText from frontmatter must override site.xml"

// Navigation structure
assert eventsDoc.select('h5:contains(Getting Started)').size() > 0 : "Getting Started section exists"
assert eventsDoc.select('a[href=console.html]:contains(Operating the Console)').size() > 0 : "Console link exists"
assert eventsDoc.select('li.active a[href=events.html]:contains(Managing Events)').size() > 0 : "Events page is marked active"
assert eventsDoc.select('a[href=subdir/agent.html]:contains(Configuring the Agent)').size() > 0 : "Agent link in menu exists"
assert eventsDoc.select('.toc li a[href=#filtering-events][du-smooth-scroll]').size() > 0 : "TOC contains filtering events link"

// Document's footer - keywords and metadata
assert eventsDoc.select('div.keywords').size() > 0 : "Keywords section is present"
assert eventsDoc.select('span.label.label-default:contains(event)').size() > 0 : "Document keywords are listed"
assert eventsDoc.select('span.label.label-default:contains(testevent)').size() > 0 : "Document keywords are listed (#2)"
assert eventsDoc.select('span.label.label-default:contains(patrol)').size() > 0 : "site.xml keywords are listed"
assert eventsDoc.select('span.label.label-default:contains(develop)').size() > 0 : "site.xml keywords are listed (#2)"
assert eventsDoc.select(':contains(Date)').text().contains('2021-01-01') : "Document's date metadata is present in the body"
assert eventsDoc.select(':contains(Author)').html().contains('Proud') : "Document's author metadata is present in the body"

// Page's footer
def footer = eventsDoc.select('footer.footer')
assert footer.size() > 0 : "Footer must exist"
assert footer.text().contains('skin-test Extended') : "Footer contains project name"
assert footer.text().contains('1.0-SNAPSHOT-test') : "Footer contains version"
assert footer.text().contains('1980-05-22') : "Publish date must be derived from pom.xml buildTimestamp property"
assert footer.text().contains('1975') && footer.text().contains('1980') : "inceptionYear must be displayed in the copyright"
assert footer.text().contains('Documentation as of (frontmatter)') : "publishDateText from frontmatter must override site.xml"
assert footer.select('.copyright:contains(Protected by copyright)').size() > 0 : "copyrightText from frontmatter must override site.xml"
assert footer.html().contains('The Organization') : '${project.organization.name} must be displayed in the footer'
assert footer.html().contains('https://the.org') : '${project.organization.url} must be displayed in the footer'

// schema.org JSON-LD metadata
def eventsHtml = eventsFile.text
assert eventsHtml =~ /"datePublished":\s*"1980-05-22T/ : "schema.org datePublished must be in ISO format with time"
assert eventsHtml =~ /"dateModified":\s*"1980-05-22T/ : "schema.org dateModified must be in ISO format with time"
assert eventsHtml =~ /"@type":\s*"Person"/ : "schema.org author must use Person type"
assert eventsHtml =~ /"@type":\s*"Person"[\s\S]*?"name":\s*"The / : "schema.org author must have a name field"

// Google Analytics
assert eventsHtml.contains("(window,document,'script','dataLayer','MY_GOOGLE_ID')") : "Specific googleAnalyticsAccountId must be inserted"

// bannerLeft and bannerRight are included
assert eventsDoc.select('a[href=https://banner.left]:contains(Banner Left)').size() > 0 : "bannerLeft.name must be included only in xs mode"
assert eventsDoc.select('img[src*=logo-short.png]').size() > 0 : "bannerLeft.src must be included"
assert eventsDoc.select('a[href=https://banner.left]').size() > 0 : "bannerLeft.href must be included"

assert eventsDoc.select('a[href=https://banner.right]:contains(Banner Right)').size() > 0 : "bannerRight.name must be included only in xs mode"
assert eventsDoc.select('img[src*=logo.png]').size() > 0 : "bannerRight.src must be included"
assert eventsDoc.select('a[href=https://banner.right]').size() > 0 : "bannerRight.href must be included"

// ============================================================================
// TEST: Interpolation (maven mode - default)
// ============================================================================
// studio-km uses the default interpolation mode "maven"
def interpolationTestFile = new File(basedir, "target/site/interpolation-test.html")
assert interpolationTestFile.isFile() : "interpolation-test.html must have been generated"
Document interpolationDoc = parseHtml(interpolationTestFile)

// In "maven" mode, ${project.name} should be resolved to actual value
// Find list items containing "Project name:" and verify resolution
def projectNameLi = interpolationDoc.select('li:contains(Project name:)').first()
assert projectNameLi != null && projectNameLi.text().contains('skin-test') :
    'In maven mode, ${project.name} should be resolved to "skin-test" (not remain as literal placeholder)'
// Verify ${project.name} does NOT appear as literal in the list item
assert !projectNameLi.text().contains('${project.name}') :
    'In maven mode, ${project.name} must NOT remain as literal placeholder in resolved section'

// In "maven" mode, ${project.version} should be resolved
def projectVersionLi = interpolationDoc.select('li:contains(Project version:)').first()
assert projectVersionLi != null && projectVersionLi.text().contains('1.0-SNAPSHOT') :
    'In maven mode, ${project.version} should be resolved to "1.0-SNAPSHOT"'

// In "maven" mode, custom properties from site.xml <custom> section should be resolved
assert interpolationDoc.text().contains('1.0-SNAPSHOT-test') :
    'In maven mode, ${projectVersion} from site.xml should be resolved to "1.0-SNAPSHOT-test"'

// In "maven" mode, hash characters should NOT be interpreted as Velocity directives
assert interpolationDoc.text().contains("Fix #123") :
    'In maven mode, hash characters must be preserved (Fix #123)'
assert interpolationDoc.html().contains("#include &lt;stdio.h&gt;") :
    'In maven mode, C-style includes must work (#include <stdio.h>)'

// In "maven" mode, escaped properties ($${...}) should render as literal ${...}
// The page should contain ${project.name} as literal text somewhere (in the explanation section)
def escapedSection = interpolationDoc.select('p:contains(Use ${project.name} in your Markdown)').first()
assert escapedSection != null :
    'In maven mode, $${project.name} should be unescaped and displayed as literal ${project.name}'

// Verify that the corresponding .html.md files are generated (using .html.md extension per llmstxt.org convention)
def mdFile = new File(basedir, "target/site/extend-summary.html.md")
assert mdFile.isFile() : "Markdown version of the HTML files must have been generated with .html.md extension"
def mdContent = mdFile.text
assert mdContent.contains('description: skin-test can be extended') : "In generated Markdown, Frontmatter header must be set according to source's metadata"
assert mdContent.contains('date_published: 1980-05-22') : "In generated Markdown, Frontmatter published date must be set correctly"
assert mdContent.contains('date_modified: 1980-05-22') : "In generated Markdown, Frontmatter modified date must be set correctly"
assert mdContent.contains('# Extending skin-test') : "In generated Markdown, content must be present"

// Verify that the llms.txt file is generated with absolute URLs and multi-line blockquote format
def llmsFile = new File(basedir, "target/site/llms.txt")
assert llmsFile.isFile() : "llms.txt file must have been generated"
def llmsContent = llmsFile.text
assert llmsContent.contains('# skin-test Extended') : "In llms.txt, title must be present"
assert llmsContent.contains('> A full documentation project (copied from Monitoring Studio)') : "In llms.txt, description must be present (multi-line blockquote format)"
assert llmsContent.contains('## Getting Started') : "In llms.txt, menu section must be present"
assert llmsContent.contains('- [Operating the Console](https://the.org/docs/console.html.md)') : "In llms.txt, menu items must use absolute URLs with .html.md extension"
assert llmsContent.contains('https://the.org/docs/') : "In llms.txt, URLs must be absolute when project URL is configured"

// Verify documents in a subdir
def agentFile = new File(basedir, "target/site/subdir/agent.html")
assert agentFile.isFile() : "Documents in subdir have been rendered"

Document agentDoc = parseHtml(agentFile)
def agentHtml = agentFile.text

// Image handling in subdir - thumbnail is in zoomable style attribute, actual image in img tag
def agentZoomable = agentDoc.select('zoomable').first()
assert agentZoomable != null && agentZoomable.attr('style').contains('MS_X_Architecture_Diagram-subdir-thumbnail.jpg') : "Thumbnail references work in subdir"
assert agentDoc.select('source[srcset*=MS_X_Architecture_Diagram-subdir.webp]').size() > 0 : "WEBP reference work in subdir"
def agentImg = agentDoc.select('img[src*=MS_X_Architecture_Diagram-subdir.png]').first()
assert agentImg != null && agentImg.attr('width') == "1723" : "Image size work in subdir"

// Verify relative paths to resources use parent dir
assert agentDoc.select('script[src^=../js/]').size() > 0 : "Page in subdir must refer to ../js/"
assert agentDoc.select('script[src^=js/], script[src^=./js/]').size() == 0 : "All references to JS must refer to parent dir"
assert agentDoc.select('link[href^=../css/]').size() > 0 : "Page in subdir must refer to ../css/"
assert agentDoc.select('link[href^=css/], link[href^=./css/]').size() == 0 : "All references to CSS must refer to parent dir"

assert agentDoc.title() == "Configuring the Agent – skin-test Extended" : "Document title is set according to source's first heading"

// Verify that there is no protocol-relative links left
assert !(agentHtml =~ '"//') : "URLs must not be protocol-relative"

// Verify that index.json contains the proper information
indexJsonFile = new File(basedir, "target/site/index.json")
assert indexJsonFile.isFile()
String indexJson = indexJsonFile.text
def eventsGood = indexJson.contains('"events.html":{"id":"events.html","title":"Dealing with Events","keywords":"event,testevent,blank space,studio,km,patrol,develop,web"')
assert eventsGood : "The index must contain title and keywords from the source metadata"

// Basic Monitors topic contains 6 subtopics
def basicFile = new File(basedir, "target/site/basic-monitors/index.html")
Document basicDoc = parseHtml(basicFile)
assert basicDoc.select(':contains(Basic Monitors)').text().contains('6') : "Basic Monitors entry must say it has 6 subitems in the left menu"
assert basicDoc.select('a[href*=basic-monitors/filesystem.html]').size() > 0 : "Link to filesystem.html must be present"
assert basicDoc.select('a[href*=basic-monitors/process.html]').size() > 0 : "Link to process.html must be present"

// Process topic lists its parents (as defined in site.xml)
def processFile = new File(basedir, "target/site/basic-monitors/process.html")
Document processDoc = parseHtml(processFile)
assert processDoc.select(':contains(Home)').size() > 0 : "Home must be in the breadcrumb"
assert processDoc.select(':contains(Using Monitoring Studio)').size() > 0 : "'Using Monitoring Studio' must be in the breadcrumb"
assert processDoc.select(':contains(Basic Monitors)').size() > 0 : "'Basic Monitors' must be in the breadcrumb"

// Code highlighting
// Not enabled for basic-monitors/index.html (because there is no code)
def basicHtml = basicFile.text
assert !basicHtml.contains("prism.js") : "Page without code must not load prism.js"
// Enabled for extend-summary.html (because it has code)
def extendFile = new File(basedir, "target/site/extend-summary.html")
Document extendDoc = parseHtml(extendFile)
def extendHtml = extendFile.text
assert extendHtml.contains("prism.js") : "Page with code must load prism.js"
// Code must be copy-pastable
assert extendDoc.select('pre[copy-to-clipboard]').size() > 0 : "<pre> blocks must have the copy-to-clipboard attribute"

// Icons
def iconsFile = new File(basedir, "target/site/icons.html")
Document iconsDoc = parseHtml(iconsFile)
def iconsHtml = iconsFile.text
assert !iconsHtml.contains("close.gif") : "In icons.html, image close.gif must have been removed"
assert iconsDoc.select('i.fa-regular.fa-rectangle-xmark').size() > 0 : "In icons.html, the fa-circle-xmark icon must have been inserted"
assert !iconsHtml.contains("icon_error_sml.gif") : "In icons.html, all instances of images that represent icons must have been removed"

// Also check that the page doesn't mention Sentry
assert !iconsHtml.contains("Sentry") : "Sentry must not be mentioned anywhere by the skin itself"

// Links (for printing)
def linksFile = new File(basedir, "target/site/links.html")
Document linksDoc = parseHtml(linksFile)
def linksHtml = linksFile.text
assert linksHtml.contains("[1] index.html") : "Internal links must be listed as footnote"
assert linksHtml.contains("[2] https://onehome.org") : "External links must be listed as footnote"
assert linksHtml.contains("[3] https://the.org") : "Organization's URL must be listed as footnote"
assert !linksHtml.contains("[4]") : "No anchors must be listed as footnote and identical URLs must be listed once"
assert linksDoc.select('a[href=index.html]:contains(This)').size() > 0 : "Internal links don't have the class externalLink"
// Check external links have externalLink class
assert linksDoc.select('a.externalLink[href=https://onehome.org]').size() > 0 : "External links must have the externalLink class"

// Rendering time
def buildLog = new File(basedir, "build.log").text
assert buildLog =~ /Rendering time: [0-9.]+ ms/

// UI Components (Angular UI Bootstrap)
def uiComponentsFile = new File(basedir, "target/site/ui-components.html")
assert uiComponentsFile.isFile() : "UI Components demo page must have been generated"
Document uiComponentsDoc = parseHtml(uiComponentsFile)
def uiComponentsHtml = uiComponentsFile.text

// Test Markdown syntax generates correct UIB elements: <uib-tabset> and <uib-tab>
assert uiComponentsDoc.select('uib-tabset').size() > 0 : "Element uib-tabset must be present in the HTML output"
assert uiComponentsDoc.select('uib-tab').size() > 0 : "Element uib-tab must be present in the HTML output"
assert uiComponentsDoc.select('uib-tab-heading').size() > 0 : "Tab heading elements must be generated"

// Test Markdown syntax generates correct UIB elements: <uib-accordion> and <div uib-accordion-group>
assert uiComponentsDoc.select('uib-accordion').size() > 0 : "Element uib-accordion must be present in the HTML output"
assert uiComponentsDoc.select('div[uib-accordion-group]').size() > 0 : "Attribute uib-accordion-group on div must be present in the HTML output"
assert uiComponentsDoc.select('uib-accordion-heading').size() > 0 : "Accordion heading elements must be generated"

// Test that content stays INSIDE the UI components

// Helper function to extract content between markers using JSoup
def tabset = uiComponentsDoc.select('uib-tabset').first()
assert tabset != null : "Tabset element must exist"
assert tabset.text().contains('Tab A') : "Tab A content must be inside the tabset"
assert tabset.text().contains('Tab B') : "Tab B content must be inside the tabset"
assert tabset.text().contains('Tab C') : "Tab C content must be inside the tabset"

def accordion = uiComponentsDoc.select('uib-accordion').first()
assert accordion != null : "Accordion element must exist"
assert accordion.text().contains('Panel A') : "Panel A content must be inside the accordion"
assert accordion.text().contains('Panel B') : "Panel B content must be inside the accordion"

// Verify sentry-uib class is present on UIB components
assert uiComponentsDoc.select('uib-tabset.sentry-uib').size() > 0 : "sentry-uib class must be present on tabset"
assert uiComponentsDoc.select('uib-accordion.sentry-uib').size() > 0 : "sentry-uib class must be present on accordion"

// Test collapse (generated from [!COLLAPSIBLE] syntax)
assert uiComponentsDoc.select('[uib-collapse]').size() > 0 : "Attribute uib-collapse must be present in the HTML output"

// Test tooltip and popover
assert uiComponentsDoc.select('[uib-tooltip="This is a tooltip!"]').size() > 0 : "Attribute uib-tooltip must be preserved in the HTML output"
assert uiComponentsDoc.select('[uib-popover="Popover content here"]').size() > 0 : "Attribute uib-popover must be preserved in the HTML output"
assert uiComponentsDoc.select('[popover-title="Popover Title"]').size() > 0 : "Attribute popover-title must be preserved in the HTML output"
assert uiComponentsDoc.select('[popover-trigger]').size() > 0 : "Attribute popover-trigger must be preserved in the HTML output"
assert uiComponentsDoc.select('[popover-placement=right]').size() > 0 : "Attribute popover-placement must be preserved in the HTML output"
assert uiComponentsDoc.select('[tooltip-placement=top]').size() > 0 : "Attribute tooltip-placement must be preserved in the HTML output"

// Verify ui-bootstrap-tpls.js is included (either directly or via combined JS)
assert uiComponentsHtml.contains("ui-bootstrap-tpls") || uiComponentsHtml.contains("main-combined.js") : "UI Bootstrap library must be included"

// ============================================================================
// TEST: HTML Structure Validity
// ============================================================================
// These tests were added after issues found during Velocity template restructuring

// Test 1: Verify main element structure is correct
// (Issue: Extra </div> tags caused browser to auto-close <main> early, placing search results outside)
def mainElement = indexDoc.select('main.main-content').first()
assert mainElement != null : "main element must exist"

// Test 2: Verify search results are inside main element using CSS structure
// (Issue: Search results appeared below left menu due to malformed HTML structure)
def searchResultsInMain = indexDoc.select('main.main-content .search-results')
assert searchResultsInMain.size() > 0 : "Search results must be inside main element, not outside"

// Test 3: Verify Angular dependencies are loaded in correct order in combined JS
// (Issue: gulpfile.js concatenation order broke Angular module dependencies)
def mainCombinedJs = new File(basedir, "target/site/js/main-combined.js")
assert mainCombinedJs.isFile() : "main-combined.js must exist"
def jsContent = mainCombinedJs.text

// Verify Angular modules are loaded in correct dependency order
// Note: In minified Angular, core "ng" module isn't explicitly defined - angular object is initialized directly
// We verify that modules depending on others come AFTER their dependencies
def matchMediaLightPos = jsContent.indexOf('angular.module("matchMediaLight"')
def uiBootstrapPos = jsContent.indexOf('angular.module("ui.bootstrap')
def sentrySitePos = jsContent.indexOf('angular.module("sentry.site"')

// Verify all required modules exist
assert jsContent.contains('angular.module(') : "Angular modules must be in main-combined.js"
assert matchMediaLightPos >= 0 : "matchMediaLight module must be in main-combined.js"
assert uiBootstrapPos >= 0 : "ui.bootstrap module must be in main-combined.js"
assert sentrySitePos >= 0 : "sentry.site module must be in main-combined.js"

// sentry.site depends on matchMediaLight and ui.bootstrap, so it must come after them
assert sentrySitePos > matchMediaLightPos : "sentry.site must come after matchMediaLight (dependency)"
assert sentrySitePos > uiBootstrapPos : "sentry.site must come after ui.bootstrap (dependency)"

// Test 4: Verify CSS dependencies are loaded in correct order
// (Issue: CSS concatenation order could break styling dependencies)
def mainCombinedCss = new File(basedir, "target/site/css/main-combined.css")
assert mainCombinedCss.isFile() : "main-combined.css must exist"
def cssContent = mainCombinedCss.text

// Bootstrap must be loaded before custom styles that override it
def bootstrapCssPos = cssContent.indexOf('.container-fluid')
def sentryCssPos = cssContent.indexOf('.sentry-site')
assert bootstrapCssPos >= 0 : "Bootstrap styles must be in main-combined.css"
assert sentryCssPos > bootstrapCssPos : "Sentry styles must come after Bootstrap (to allow overrides)"

// Test 5: Verify ng-show/ng-hide directives are present for search functionality
// (Issue: Removed ng-if conditions broke search visibility toggle)
assert indexDoc.select('[ng-hide=siteSearch]').size() > 0 : "Content must have ng-hide directive for search toggle"
assert indexDoc.select('[ng-show=siteSearch]').size() > 0 : "Search results must have ng-show directive"

// Test 6: Verify matchMediaLight module is included and functional
// (Issue: Missing matchMediaLight broke responsive features and dark mode)
assert jsContent.contains('matchMediaLight') : "matchMediaLight module must be included in combined JS"
assert jsContent.contains('$matchMedia') : "$matchMedia service must be available"
assert indexDoc.select('[ng-class*=$matchMedia.dark]').size() > 0 : "Dark mode toggle must reference $matchMedia.dark"

// Test 7: Verify elasticlunr search library is included
// (Issue: Search functionality depends on elasticlunr)
assert jsContent.contains('elasticlunr') : "elasticlunr search library must be included in combined JS"

// Test 8: Verify mark.js highlighting library is included
// (Issue: Search result highlighting depends on mark.js)
assert jsContent.contains('Mark(') || jsContent.contains('mark.js') : "mark.js library must be included for search highlighting"
