keywords: front matter, metadata, title, description, author, configuration
description: Add metadata and configure features per-page using front matter headers.

# Front Matter Headers

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Add metadata and configure features for individual pages using front matter headers at the top of Markdown files.

## Basic Usage

```markdown
title: Installation Guide
author: John Doe
description: Step-by-step installation instructions
keywords: install, setup, configuration
date: 2024-01-15

# Installation Guide

Your content here...
```

## Metadata Headers

| Header        | Description                              |
| ------------- | ---------------------------------------- |
| `title`       | Page title (overrides first heading)     |
| `author`      | Document author (multiple allowed)       |
| `description` | SEO description for search engines       |
| `keywords`    | Page-specific keywords (comma-separated) |
| `date`        | Document date (creation or update)       |

## Configuration Headers

All [configuration options](settings.html) can be set per-page via frontmatter. **Frontmatter takes precedence** over site.xml settings.

```markdown
buildIndex: false
convertImagesToWebp: false
syntaxHighlighting: false

# My Page

Content here...
```

See the [Configuration Reference](settings.html) for the complete list of available settings.

## Examples

### Exclude from Search and AI

```markdown
buildIndex: false
buildAiIndex: false

# Internal Notes

This page won't appear in search or AI indexes.
```

### Disable Image Processing

```markdown
convertImagesToWebp: false
convertImagesToThumbnails: false

# Raw Images

Images stay in original format without zoom.
```

### Use Velocity Interpolation

```markdown
interpolation: velocity

# Dynamic Page

This page uses Velocity: $project.version
```

## AngularJS Content

By default, `protectAngular: true` keeps `{{...}}` expressions literal in document text,
including code examples, headings, and the table of contents. This allows Go, Helm, and other template examples
to display and copy unchanged while the skin's search, tabs, image zoom, and other interactive features still work.

Pages that intentionally use AngularJS text bindings must opt out:

> [!TABS]
>
> - Markdown
>
>   Add the option to the front matter. Undelimited headers must begin with a standard metadata key such as `title`:
>
>   ```markdown
>   title: Interactive Page
>   protectAngular: false
>
>   # Interactive Page
>
>   <p>{{1 + 2}}</p>
>   ```
>
> - XHTML
>
>   Set the equivalent metadata in the page's `<head>`:
>
>   ```xml
>   <meta name="protectAngular" content="false" />
>   ```

You can also set a site-wide default with `<protectAngular>false</protectAngular>` inside `site.xml`'s `<custom>`
section. Page metadata takes precedence, so `protectAngular: true` can protect individual pages on that site.
Existing pages that use AngularJS bindings need this opt-out when upgrading.

Protection is applied when the site is built and targets text and code, rather than arbitrary HTML attributes or
author-supplied AngularJS directives. It does not sanitize scripts. The separate `interpolation` setting controls Maven/Velocity `${...}` expressions.

## How It Works

Headers are processed in two ways:

1. **Metadata headers** (title, author, description, keywords) are added to the HTML `<head>`:

   ```html
   <meta name="author" content="John Doe">
   <meta name="description" content="Step-by-step installation...">
   <meta name="keywords" content="install, setup, configuration">
   ```

2. **Configuration headers** control how the page is processed by the skin.

## Tips

> [!TIP]
> - **Description** is critical for SEO - keep it under 160 characters
> - **Keywords** merge with global keywords from `site.xml`
> - **Multiple authors** can be specified with separate `author` lines
> - **Boolean values** accept: `true`/`false`, `yes`/`no`, `on`/`off`, `1`/`0`

## See Also

- [Configuration Reference](settings.html) - All configuration options
- [Maven Properties](maven-properties.html) - Property interpolation modes
