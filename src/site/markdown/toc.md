keywords: toc, table of contents, navigation
description: Automatically generate a table of contents from your page headings.

# Automatic Table of Contents

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Generate a table of contents automatically from your page headings.

## How to Add a ToC

Insert this macro in your Markdown file:

```html
<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->
```

> [!IMPORTANT]
> The `id=toc` parameter is required for proper styling.

## Macro Parameters

| Parameter   | Description                             | Default  |
| ----------- | --------------------------------------- | -------- |
| `fromDepth` | Starting heading level (1 = h1)         | 1        |
| `toDepth`   | Ending heading level (2 = h2)           | 2        |
| `id`        | Must be `toc` to be handled by the skin | Required |

## Example

A document with these headings:

```markdown
# Page Title
## Section One
### Subsection
## Section Two
```

With `fromDepth=1` and `toDepth=2`, the ToC includes:
- Page Title
- Section One
- Section Two

(Subsection is excluded because it's level 3)

## Display Behavior

- **Desktop**: ToC floats on the right side of the page
- **Mobile**: ToC displays inline at the top

## Configuration

| Setting       | Description                                        | Default  |
| ------------- | -------------------------------------------------- | -------- |
| `tocSelector` | CSS selector for the ToC element                   | `ul#toc` |
| `fixHeadings` | Enhance headings with proper IDs, and anchor links | `true`   |

### Custom ToC Selector

If you use a different ID or structure for your ToC:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <tocSelector>ul#my-custom-toc</tocSelector>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   tocSelector: ul#my-custom-toc
>
>   # My Page
>
>   <!-- MACRO{toc|fromDepth=2|toDepth=3|id=my-custom-toc} -->
>   ```

### Disable Enhanced Headings

To disable the enhanced headings and automatic anchor links:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <fixHeadings>false</fixHeadings>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   fixHeadings: false
>
>   # Plain Headings
>
>   Headings won't have clickable anchor links and their internal ID will use pre-HTML5 syntax.
>   ```

## Reference

See [Maven Doxia macros](https://maven.apache.org/doxia/macros/index.html) for more details.

## See Also

- [Configuration Reference](settings.html) - All configuration options
- [Page Structure](page-structure.html) - How to structure your pages
