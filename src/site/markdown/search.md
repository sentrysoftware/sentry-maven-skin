keywords: search, index, elasticlunr
description: Built-in full-text search across your documentation without external services.

# Full-Text Search

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

${project.name} includes built-in full-text search - no Google or external services required.

![Search results with highlighted matches](images/search.png)

Works great on mobile too:

![inline](images/search-mobile.png)

## How It Works

1. During build, all pages are indexed using [elasticlunr.js](https://elasticlunr.com/)
2. The index is saved to `target/site/index.json`
3. When users search, the index loads and results appear instantly

The indexer runs on [GraalVM's JavaScript engine](https://www.graalvm.org/latest/reference-manual/js/) during the Maven build.

## Features

- **Instant results** as you type
- **Highlighted excerpts** showing matching text
- **Relevance scoring** for result ranking
- **No external dependencies** - works offline
- **Lazy loading** - index loads only when needed

## Configuration

Search works automatically with no configuration needed.

| Setting      | Description                  | Default |
| ------------ | ---------------------------- | ------- |
| `buildIndex` | Include page in search index | `true`  |

### Exclude a Page from Search

To exclude specific pages from the search index:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <buildIndex>false</buildIndex>
>   </custom>
>   ```
>
>   This removes both the search index generation and the search UI from all pages.
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   buildIndex: false
>
>   # Internal Notes
>
>   This page won't appear in search results.
>   ```
>
>   The search UI remains visible on that page.

## Index Size

> [!TIP]
> The `index.json` file size grows with your documentation size. For very large sites, consider excluding auto-generated pages or verbose API documentation from the index.

## See Also

- [AI Indexing](ai-indexing.html) - Markdown files for AI platforms
- [Configuration Reference](settings.html) - All configuration options
