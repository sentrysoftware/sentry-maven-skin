keywords: configuration, settings, options, site.xml, frontmatter
description: Complete configuration reference for ${project.name} - all settings for site.xml and frontmatter.

# Configuration Reference

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

All ${project.name} features can be configured globally in `site.xml` or per-page using frontmatter headers.

## Configuration Methods

> [!TABS]
>
> - site.xml (Global)
>
>   Configure settings in `src/site/site.xml` under the `<custom>` section:
>
>   ```xml
>   <site xmlns="http://maven.apache.org/SITE/2.0.0"
>       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>       xsi:schemaLocation="http://maven.apache.org/SITE/2.0.0 https://maven.apache.org/xsd/site-2.0.0.xsd"
>       name="$${project.name}">
>
>     <skin>
>       <groupId>org.sentrysoftware.maven</groupId>
>       <artifactId>sentry-maven-skin</artifactId>
>       <version>${project.version}</version>
>     </skin>
>
>     <custom>
>       <bodyClass>sentry-purple</bodyClass>
>       <buildIndex>true</buildIndex>
>       <convertImagesToWebp>true</convertImagesToWebp>
>     </custom>
>
>   </site>
>   ```
>
>   These settings apply to **all pages** in your documentation.
>
> - Frontmatter (Per-Page)
>
>   Override settings for individual pages using frontmatter at the top of Markdown files:
>
>   ```markdown
>   title: API Reference
>   buildIndex: false
>   buildAiIndex: false
>   copyToClipboard: false
>
>   # API Reference
>
>   Your content here...
>   ```
>
>   **Frontmatter takes precedence** over site.xml settings.

## Configuration Precedence

When a setting is defined in multiple places, the precedence order is:

1. **Frontmatter** (per-page) - highest priority
2. **site.xml `<custom>`** (global)
3. **Default value** - lowest priority

> [!TIP]
> Use site.xml for global defaults, then override specific pages with frontmatter when needed.

## Complete Settings Reference

### Content Processing

| Setting          | Type   | Description                                                                            | Default               |
| ---------------- | ------ | -------------------------------------------------------------------------------------- | --------------------- |
| `interpolation`  | String | How `${...}` expressions are processed. See [Maven Properties](maven-properties.html). | `maven`               |
| `publishDate`    | Date   | Publication date for metadata                                                          | Build timestamp       |
| `projectVersion` | String | Version displayed in the header                                                        | `\${project.version}` |

### UI Text Labels

| Setting                 | Type   | Description                                         | Default               |
| ----------------------- | ------ | --------------------------------------------------- | --------------------- |
| `tocHeadingText`        | String | Heading displayed above generated table of contents | `Table of Contents`   |
| `publishDateText`       | String | Label displayed before publish date in footer       | `Documentation as of` |
| `copyrightText`         | String | Prefix displayed in footer copyright line           | `Copyright`           |
| `searchFieldText`       | String | Search input placeholder and aria-label text        | `Search...`           |
| `searchResultsText`     | String | Heading displayed above search results              | `Search Results for`  |
| `searchResultSingleText`| String | Text displayed for a single search result           | `result`              |
| `searchResultCountText` | String | Text displayed after the number of search results   | `results`             |
| `projectVersionText`    | String | Label displayed before version in header            | `Version`             |

### Images

| Setting                     | Type     | Description                                                  | Default                                         |
| --------------------------- | -------- | ------------------------------------------------------------ | ----------------------------------------------- |
| `checkImageLinks`           | Boolean  | Verify image files exist during build                        | `true`                                          |
| `convertImagesToWebp`       | Boolean  | Convert images to WebP format                                | `true`                                          |
| `setExplicitImageSize`      | Boolean  | Add width/height attributes to images                        | `true`                                          |
| `convertImagesToThumbnails` | Selector | CSS selector for zoomable images. Set to `false` to disable. | `img:not([alt=inline]):not([uib-carousel] img)` |

### Code

| Setting              | Type     | Description                                                      | Default                        |
| -------------------- | -------- | ---------------------------------------------------------------- | ------------------------------ |
| `syntaxHighlighting` | Selector | CSS selector for code blocks. Set to `false` to disable PrismJS. | `pre > code[class*=language-]` |
| `copyToClipboard`    | Selector | CSS selector for copy button. Set to `false` to disable.         | `pre`                          |

### Navigation

| Setting                   | Type     | Description                                              | Default        |
| ------------------------- | -------- | -------------------------------------------------------- | -------------- |
| `tocSelector`             | Selector | CSS selector for the table of contents                   | `ul#toc`       |
| `fixProtocolRelativeUrls` | Boolean  | Convert `//` URLs to `https://`                          | `true`         |
| `fixHeadings`             | Boolean  | Fix heading anchors and IDs                              | `true`         |
| `externalLinkClass`       | String   | CSS class for external links. Set to `false` to disable. | `externalLink` |

### Search

| Setting        | Type    | Description                                     | Default |
| -------------- | ------- | ----------------------------------------------- | ------- |
| `buildIndex`   | Boolean | Include page in search index                    | `true`  |
| `buildAiIndex` | Boolean | Generate `.html.md` files and update `llms.txt` | `true`  |
| `keywords`     | String  | Comma-separated keywords for SEO and search     | None    |

### Appearance

| Setting     | Type   | Description                            | Default |
| ----------- | ------ | -------------------------------------- | ------- |
| `bodyClass` | String | CSS class(es) for the `<body>` element | None    |

## Examples

### Disable Features for a Page

Exclude a page from search and AI indexing:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <buildIndex>false</buildIndex>
>     <buildAiIndex>false</buildAiIndex>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   buildIndex: false
>   buildAiIndex: false
>
>   # Internal Notes
>
>   This page won't appear in search results or AI indexes.
>   ```

### Disable Image Processing

Keep original images without WebP conversion:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <convertImagesToWebp>false</convertImagesToWebp>
>     <setExplicitImageSize>false</setExplicitImageSize>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   convertImagesToWebp: false
>   setExplicitImageSize: false
>
>   # Legacy Images
>
>   Images on this page stay in their original format.
>   ```

### Disable All Automatic Processing

For pages where you want complete control:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <fixHeadings>false</fixHeadings>
>     <fixProtocolRelativeUrls>false</fixProtocolRelativeUrls>
>     <copyToClipboard>false</copyToClipboard>
>     <convertImagesToThumbnails>false</convertImagesToThumbnails>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   fixHeadings: false
>   fixProtocolRelativeUrls: false
>   copyToClipboard: false
>   convertImagesToThumbnails: false
>
>   # Raw Content
>
>   No automatic enhancements on this page.
>   ```

## Boolean Values

Boolean settings accept these values:

| True values | False values |
| ----------- | ------------ |
| `true`      | `false`      |
| `yes`       | `no`         |
| `on`        | `off`        |
| `1`         | `0`          |

## CSS Selector Settings

Several settings use [CSS selectors](https://www.w3schools.com/cssref/css_selectors.php) to target HTML elements:

- `convertImagesToThumbnails` - Which images get zoom functionality
- `syntaxHighlighting` - Which code blocks get PrismJS
- `copyToClipboard` - Which elements get copy buttons
- `tocSelector` - Which element is the table of contents

Set any selector setting to `false` to completely disable that feature.

### Selector Examples

```xml
<!-- Only highlight code blocks with specific languages -->
<syntaxHighlighting>pre > code[class*=language-java], pre > code[class*=language-xml]</syntaxHighlighting>

<!-- Copy button for code and YAML blocks -->
<copyToClipboard>pre, .yaml-example</copyToClipboard>

<!-- Disable zoom for all images -->
<convertImagesToThumbnails>false</convertImagesToThumbnails>
```

## Additional site.xml Settings

These settings are only available in `site.xml` (not frontmatter):

### Banners

```xml
<bannerLeft>
  <src>images/logo.png</src>
  <href>https://example.com</href>
  <name>My Project</name>
  <alt>Project Logo</alt>
</bannerLeft>

<bannerRight>
  <!-- Same structure as bannerLeft -->
</bannerRight>
```

### Navigation

```xml
<body>
  <links>
    <item name="GitHub" href="https://github.com/you/project"/>
  </links>
  <breadcrumbs>
    <item name="Home" href="https://example.com"/>
  </breadcrumbs>
  <menu name="Documentation">
    <item name="Overview" href="index.html"/>
  </menu>
</body>
```

### Social Media

```xml
<custom>
  <social>
    <twitter>YourHandle</twitter>
    <linkedin>company/YourCompany</linkedin>
    <facebook>YourPage</facebook>
    <custom>
      <title>Join us on Slack!</title>
      <href>https://slack.example.com</href>
      <icon>fa-brands fa-slack</icon>
    </custom>
  </social>
</custom>
```

### Footer Links

```xml
<custom>
  <additionalLinks>
    <link>
      <name>Terms of Service</name>
      <href>https://example.com/terms</href>
    </link>
    <link>
      <name>Privacy Policy</name>
      <href>https://example.com/privacy</href>
    </link>
  </additionalLinks>
</custom>
```

## See Also

- [Front Matter Headers](headers.html) - Per-page metadata
- [Maven Properties](maven-properties.html) - Property interpolation
- [Styling](styles.html) - Color themes and CSS
- [Navigation Links](nav-links.html) - Header and footer links
- [Navigation Menu](nav-menu.html) - Sidebar menu configuration

