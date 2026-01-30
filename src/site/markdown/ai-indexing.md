keywords: llms.txt, ai, llm, chatgpt, copilot, claude, gemini
description: Automatic llms.txt and Markdown generation for AI platform indexing.

# AI Indexing

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

${project.name} automatically generates files to help AI platforms (ChatGPT, GitHub Copilot, Claude, Gemini) understand your documentation.

## Generated Files

During build, the skin creates:

1. **`llms.txt`** - Structured index following the [llmstxt.org](https://llmstxt.org/) proposal format
2. **`.html.md` files** - Markdown version of each HTML page (using the `.html.md` extension convention per llmstxt.org)
3. **`<link>` tags** - References to Markdown versions in HTML headers

### llms.txt Format

The `llms.txt` file follows the [llmstxt.org proposal](https://llmstxt.org/) and includes:

```markdown
# My Project

> A powerful library for doing amazing things
> that spans multiple lines when needed.

## Getting Started

- [Installation](https://example.com/docs/installation.html.md)
- [Quick Start](https://example.com/docs/quickstart.html.md)

## User Guide

- [Configuration](https://example.com/docs/configuration.html.md)
```

Key features:

- **Absolute URLs**: When `<url>` is defined in `pom.xml`, links are absolute (e.g., `https://example.com/docs/page.html.md`)
- **Multi-line blockquotes**: Project descriptions are properly formatted with `> ` prefix on each line
- **`.html.md` extension**: URLs use the `.html.md` convention (appending `.md` to the original HTML URL)
- **Menu sections**: Sections are generated from your `site.xml` menu structure

### Markdown Files

Each HTML page gets a corresponding `.html.md` file with YAML front matter:

```yaml
---
description: Learn how to install the library
date_published: 2024-01-15
date_modified: 2024-01-15
canonical_url: https://example.com/docs/install.html
---
```

The `.html.md` extension convention (appending `.md` to the original URL) allows AI platforms to easily determine the Markdown equivalent of any HTML page.

### Link Tags

HTML pages include a link to their Markdown version:

```html
<link rel="alternate" type="text/markdown" href="page.html.md"/>
```

This tells AI crawlers where to find the Markdown version of the current page.

## SEO Features

Also included automatically:

- Canonical URLs (`<link rel="canonical">`)
- Open Graph metadata
- Schema.org JSON-LD structured data

## Configuration

This feature is **enabled by default** with no configuration needed.

> [!TIP]
> For best results, define `<url>` in your `pom.xml` to generate absolute URLs in `llms.txt`:
>
> ```xml
> <project>
>   <url>https://example.com/docs</url>
> </project>
> ```

When `<url>` is defined, links in `llms.txt` become absolute URLs, making them easier for AI platforms to index regardless of context. If `<url>` is not defined, links remain relative.

Also define `<description>` in your `pom.xml`:

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
