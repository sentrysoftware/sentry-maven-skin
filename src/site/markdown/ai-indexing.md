keywords: llms.txt, ai, llm, chatgpt, copilot, claude, gemini
description: Automatic llms.txt and Markdown generation for AI platform indexing.

# AI Indexing

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

${project.name} automatically generates files to help AI platforms (ChatGPT, GitHub Copilot, Claude, Gemini) understand your documentation.

## Generated Files

During build, the skin creates:

1. **`llms.txt`** - Structured index following the [llms.txt convention](https://llmstxt.org/)
2. **`.md` files** - Markdown version of each HTML page
3. **`<link>` tags** - References to Markdown versions in HTML

### llms.txt Format

```markdown
# My Project

> A powerful library for doing amazing things

## Getting Started

- [Installation](installation.html)
- [Quick Start](quickstart.html)

## User Guide

- [Configuration](configuration.html)
```

Sections are generated from your `site.xml` menu structure.

### Markdown Files

Each HTML page gets a corresponding `.md` file with YAML front matter:

```yaml
---
description: Learn how to install the library
date_published: 2024-01-15
date_modified: 2024-01-15
canonical_url: https://example.com/docs/install.html
---
```

### Link Tags

HTML pages include a link to their Markdown version:

```html
<link rel="alternate" type="text/markdown" href="page.md"/>
```

## SEO Features

Also included automatically:

- Canonical URLs (`<link rel="canonical">`)
- Open Graph metadata
- Schema.org JSON-LD structured data

## Configuration

This feature is **enabled by default** with no configuration needed.

For best results, define `<url>` in your `pom.xml`:

```xml
<project>
  <url>https://example.com/docs</url>
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
