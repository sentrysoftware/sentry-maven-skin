keywords: search, index, elasticlunr
description: Built-in full-text search across your documentation without external services.

# Full-Text Search

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

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

> [!NOTE]
> The `index.json` file size grows with your documentation size.
