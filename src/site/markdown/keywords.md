keywords: seo, search, meta
description: Configure keywords globally or per-page for SEO and internal search.

# Keywords

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Keywords appear in the HTML `<meta name="keywords">` tag and are indexed by the built-in search engine.

## Global Keywords

Set keywords for all pages in `src/site/site.xml`:

```xml
<custom>
  <keywords>java, maven, documentation</keywords>
</custom>
```

## Page-Specific Keywords

Add keywords to individual pages using front matter:

```markdown
keywords: installation, setup, quickstart

# Getting Started

Your content...
```

## How Keywords Merge

Page keywords are combined with global keywords. If you have:

- Global: `java, maven`
- Page: `installation, setup`

The resulting HTML will be:

```html
<meta name="keywords" content="java, maven, installation, setup"/>
```

## Search Optimization

Keywords are indexed by the [built-in search](search.html). Use them to:

- Add synonyms (e.g., `keywords: jpg` for a page about JPEG)
- Include common misspellings
- Add related terms users might search for
