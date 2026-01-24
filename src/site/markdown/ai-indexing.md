keywords: llms.txt, ai, llm, chatgpt, copilot, claude, gemini, markdown
description: ${project.name} automatically generates llms.txt and Markdown files to optimize your documentation for AI platforms indexing.

# AI Indexing

**${project.name}** automatically generates files to help AI platforms (like ChatGPT, GitHub Copilot, Claude, Gemini, etc.) better understand and index your documentation. This improves the quality of AI-assisted answers about your project.

## How does it work?

When your documentation site is built, the skin automatically:

1. **Creates an `llms.txt` file** at the root of your site, following the [llms.txt convention](https://llmstxt.org/)
2. **Generates a Markdown (`.md`) version** of each HTML page
3. **Adds `<link rel="alternate">` tags** in each HTML page pointing to the Markdown version

### The llms.txt File

The `llms.txt` file provides a structured index of your documentation that AI crawlers can easily parse. It follows the [llms.txt specification](https://llmstxt.org/) with:

- **H1 header** with your project name
- **Blockquote** with your project description
- **Sections** organized by your navigation menu structure
- **Links** to all documentation pages

Example:

```markdown
# My Project

> A powerful library for doing amazing things

## Getting Started

- [Installation](installation.html)
- [Quick Start Guide](quickstart.html)

## User Guide

- [Configuration](configuration.html)
- [Advanced Usage](advanced.html)

## Other

- [Project Summary](summary.html)
```

The sections in `llms.txt` are automatically determined from your `site.xml` menu structure. Pages that don't belong to any menu are placed in the "Other" section.

### Markdown Files

For each HTML page, a corresponding `.md` file is generated with:

- **YAML front matter** containing metadata (description, dates, canonical URL)
- **Clean Markdown content** converted from the HTML body

Example front matter:

```yaml
---
description: Learn how to install and configure the library
date_published: 2024-01-15
date_modified: 2024-01-15
canonical_url: https://example.com/docs/installation.html
---
```

### Link Tags

Each HTML page includes a `<link rel="alternate">` tag pointing to its Markdown version:

```html
<link rel="alternate" type="text/markdown" href="installation.md" />
```

This helps AI platforms discover the Markdown versions of your pages.

## SEO and Structured Data

In addition to the AI-specific files, **${project.name}** also includes:

- **Canonical URLs** (`<link rel="canonical">`) when `<url>` is defined in your `pom.xml`
- **Open Graph metadata** (`article:published_time`, `article:modified_time`)
- **Schema.org JSON-LD** structured data for each page

## Configuration

This feature is enabled by default and requires no configuration. The files are generated automatically during the site build.

To benefit from the canonical URL and complete metadata:

1. Define `<url>` in your `pom.xml`:

```xml
<project>
    ...
    <url>https://example.com/docs</url>
    ...
</project>
```

2. Define `<description>` in your `pom.xml`:

```xml
<project>
    ...
    <description>A powerful library for doing amazing things</description>
    ...
</project>
```

## Why AI Indexing Matters

As AI assistants become more prevalent in developer workflows, ensuring your documentation is AI-friendly provides several benefits:

- **Better AI-generated answers** about your project
- **Improved discoverability** by AI-powered search engines
- **Structured content** that AI can easily parse and understand
- **Future-proofing** your documentation for emerging AI platforms

The combination of `llms.txt`, Markdown files, and structured metadata makes your documentation a first-class citizen in the AI ecosystem.
