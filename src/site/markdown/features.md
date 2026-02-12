keywords: features, capabilities, overview
description: Complete overview of all features in ${project.name} for building professional documentation sites.

# Features Overview

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

A quick reference to all ${project.name} features. For a guided tour, see the [Documentation Guide](index.html#documentation-guide).

> [!TIP]
> All features are **configurable** - enable, disable, or customize them globally in `site.xml` or per-page using frontmatter. See the [Configuration Reference](settings.html).

## Setup Features

| Feature                            | Description                                             | Configurable |
| ---------------------------------- | ------------------------------------------------------- | ------------ |
| [Styling](styles.html)             | Customize colors and fonts with CSS variables           | `bodyClass`  |
| [Navigation Links](nav-links.html) | Configure header bar with logo, links, and social media | site.xml     |
| [Navigation Menu](nav-menu.html)   | Collapsible sidebar menu (supports 2 levels)            | site.xml     |

## Writing Features

| Feature                               | Description                              | Configurable                                                          |
| ------------------------------------- | ---------------------------------------- | --------------------------------------------------------------------- |
| [Writing a Page](page-structure.html) | Structure and template for documentation | —                                                                     |
| [Automatic Title](title.html)         | Page titles generated from first heading | —                                                                     |
| [Automatic ToC](toc.html)             | Table of contents from headings          | `tocSelector`, `fixHeadings`                                          |
| [Images](images.html)                 | Auto-zoom and WebP conversion            | `convertImagesToWebp`, `convertImagesToThumbnails`, `checkImageLinks` |
| [Code Highlighting](code.html)        | 290+ languages with copy-to-clipboard    | `syntaxHighlighting`, `copyToClipboard`                               |
| [UI Components](ui-components.html)   | Tabs, accordions, carousels, popovers    | —                                                                     |
| [Doxia Features](doxia.html)          | Macros, snippets, and Velocity variables | —                                                                     |

## Search and Discovery

| Feature                         | Description                                      | Configurable   |
| ------------------------------- | ------------------------------------------------ | -------------- |
| [Full-Text Search](search.html) | Built-in search index (no external services)     | `buildIndex`   |
| [AI Indexing](ai-indexing.html) | Automatic `llms.txt` generation for AI platforms | `buildAiIndex` |
| [Keywords](keywords.html)       | SEO keywords (global and per-page)               | `keywords`     |

## Advanced Features

| Feature                                   | Description                                 | Configurable                       |
| ----------------------------------------- | ------------------------------------------- | ---------------------------------- |
| [Configuration Reference](settings.html)  | All site.xml and frontmatter options        | —                                  |
| [Front Matter Headers](headers.html)      | Add metadata and configure pages            | All settings                       |
| [Print Support](print.html)               | Optimized CSS for printing                  | —                                  |
| [Maven Properties](maven-properties.html) | Reference pom.xml values in pages           | `interpolation`                    |
| [Additional Features](misc.html)          | Dark mode, accessibility, responsive design | `fixHeadings`, `externalLinkClass` |
