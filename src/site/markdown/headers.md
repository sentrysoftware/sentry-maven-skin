keywords: front matter, metadata, title, description, author
description: Add metadata to your pages using front matter headers.

# Front Matter Headers

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Add metadata to your Markdown pages using front matter headers at the top of the file.

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

## Supported Headers

| Header          | Description                                                 |
| --------------- | ----------------------------------------------------------- |
| `title`         | Page title (overrides first heading)                        |
| `author`        | Document author (multiple allowed)                          |
| `description`   | SEO description for search engines                          |
| `keywords`      | Page-specific keywords (comma-separated)                    |
| `date`          | Document date (creation or update)                          |
| `interpolation` | Override interpolation mode: `velocity`, `maven`, or `none` |

## How It Works

Headers are added to the HTML `<head>` section:

```html
<meta name="author" content="John Doe">
<meta name="description" content="Step-by-step installation...">
<meta name="keywords" content="install, setup, configuration">
```

## Tips

> [!TIP]
> - **Description** is critical for SEO - keep it under 160 characters
> - **Keywords** merge with global keywords from `site.xml`
> - **Multiple authors** can be specified with separate `author` lines
