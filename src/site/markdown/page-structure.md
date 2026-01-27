keywords: page, structure, layout, markdown, writing
description: How to structure a documentation page for clarity and consistency.

# Writing a Documentation Page

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

This guide shows how to structure a typical documentation page.

## Page Template

A well-structured page follows this pattern:

```markdown
keywords: relevant, search, terms
description: A one-sentence summary for search engines.

# Page Title

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Brief introduction explaining what this page covers.

## First Section

Content organized logically...

## Second Section

More content...

## See Also

- [Related Page](related.html)
```

## Front Matter

Start every page with metadata headers:

```markdown
keywords: api, configuration, setup
description: How to configure the API client.
```

| Header | Purpose |
|--------|---------|
| `keywords` | Search terms (comma-separated) |
| `description` | SEO meta description |
| `title` | Override automatic title |

See [Front Matter Headers](headers.html) for all options.

## Page Title

Use a single `#` heading as your page title:

```markdown
# Configuring the API Client
```

This becomes:
- The page header
- The browser tab title (with project name)
- The search index entry

## Table of Contents

For longer pages, add the ToC macro right after the title:

```markdown
# Page Title

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->
```

The ToC floats on the right (desktop) or displays inline (mobile). See [Table of Contents](toc.html).

## Introduction

Write 1-3 sentences explaining what the reader will learn. This helps readers confirm they're on the right page.

## Sections

Organize content with `${esc.h}${esc.h}` headings:

```markdown
${esc.h}${esc.h} Installation

Steps to install...

${esc.h}${esc.h} Configuration

How to configure...

${esc.h}${esc.h} Usage

How to use...
```

**Tips:**
- Keep sections focused on one topic
- Use consistent heading levels (`${esc.h}${esc.h}` for main sections, `${esc.h}${esc.h}${esc.h}` for subsections)
- Each heading becomes a linkable anchor

## Code Examples

Include practical code examples:

~~~markdown
```xml
<dependency>
  <groupId>com.example</groupId>
  <artifactId>my-library</artifactId>
  <version>1.0.0</version>
</dependency>
```
~~~

See [Code Highlighting](code.html) for supported languages.

## Images

Add screenshots and diagrams:

```markdown
![Configuration dialog](images/config-dialog.png)
```

Images are automatically:
- Displayed as clickable thumbnails
- Converted to WebP for performance

Use `![inline](image.png)` for small icons. See [Images](images.html).

## Tables

Use Markdown tables for structured data:

```markdown
| Parameter | Type | Description |
|-----------|------|-------------|
| `timeout` | int | Request timeout in ms |
| `retries` | int | Number of retry attempts |
```

## Notes and Warnings

Use blockquotes for callouts:

```markdown
> **Note**: This requires version 2.0 or later.

> **Warning**: This action cannot be undone.
```

## Cross-References

Link to related pages:

```markdown
See [Configuration Reference](settings.html) for details.
```

For external links:

```markdown
See the [Maven documentation](https://maven.apache.org).
```

## Interactive Elements

For complex content, consider:

- **[Tabs](ui-components.html#tabs)**: Show alternatives (e.g., Maven/Gradle)
- **[Accordion](ui-components.html#accordion)**: Collapse optional details
- **[Collapse](ui-components.html#collapse)**: Hide advanced content

## Checklist

Before publishing, verify:

- [ ] Front matter (`keywords`, `description`) is set
- [ ] Title is clear and descriptive
- [ ] Introduction explains the page purpose
- [ ] Code examples are tested and correct
- [ ] Images have alt text
- [ ] Links work correctly
- [ ] ToC is included for long pages

## Next Steps

- [Code Highlighting](code.html) - Format code blocks
- [Images](images.html) - Add screenshots
- [UI Components](ui-components.html) - Add tabs and accordions
- [Doxia Features](doxia.html) - Advanced macros
