keywords: configuration, settings, options, site.xml
description: Complete configuration reference for ${project.name} in site.xml.

# Configuration Reference

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

All settings are configured in `src/site/site.xml`, the [Maven site descriptor](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html).

## Quick Reference

```xml
<project name="\${project.name}">

  <skin>
    <groupId>org.sentrysoftware.maven</groupId>
    <artifactId>sentry-maven-skin</artifactId>
    <version>${project.version}</version>
  </skin>

  <custom>
    <bodyClass>sentry-purple</bodyClass>
    <keywords>keyword1, keyword2</keywords>
    <projectVersion>2.0.0</projectVersion>
    <additionalLinks>
      <link>
        <name>Terms of Service</name>
        <href>https://example.com/terms</href>
      </link>
    </additionalLinks>
    <social>
      <twitter>YourHandle</twitter>
      <linkedin>company/YourCompany</linkedin>
    </social>
  </custom>

  <bannerLeft>
    <src>images/logo.png</src>
    <href>https://example.com</href>
  </bannerLeft>

  <body>
    <links>
      <item name="GitHub" href="https://github.com/you/project"/>
    </links>
    <menu name="Documentation">
      <item name="Overview" href="index.html"/>
    </menu>
  </body>

</project>
```

## Configuration Options

### `<custom>` Settings

| Setting             | Description                                                                   | Default                   |
| ------------------- | ----------------------------------------------------------------------------- | ------------------------- |
| `<bodyClass>`       | Color theme: `sentry-purple`, `sentry-green`, `sentry-orange`, or none (blue) | Blue                      |
| `<keywords>`        | Global keywords for all pages (comma-separated)                               | None                      |
| `<projectVersion>`  | Override the displayed version                                                | `$${project.version}`     |
| `<publishDate>`     | Documentation publish date (ISO format)                                       | Build timestamp           |
| `<additionalLinks>` | Footer links (legal, privacy, etc.)                                           | None                      |
| `<social>`          | Social media links in header                                                  | None                      |

### `<bannerLeft>` and `<bannerRight>`

| Setting  | Description                            |
| -------- | -------------------------------------- |
| `<src>`  | Path to logo image                     |
| `<href>` | Link URL when clicked                  |
| `<name>` | Text to display (alternative to image) |
| `<alt>`  | Alt text for image                     |

### `<body>` Settings

| Setting         | Description              |
| --------------- | ------------------------ |
| `<links>`       | Top navigation bar links |
| `<breadcrumbs>` | Breadcrumb navigation    |
| `<menu>`        | Sidebar navigation menu  |

## See Also

- [Navigation Links](nav-links.html) - Header and footer links
- [Navigation Menu](nav-menu.html) - Sidebar menu configuration
- [Styling](styles.html) - Color themes and CSS customization
- [Keywords](keywords.html) - SEO keywords configuration
