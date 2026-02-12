keywords: dark mode, anchors, tables, copyright, copy code, external links
description: Additional features including dark mode, anchor links, styled tables, external links, and code copying.

# Additional Features

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

${project.name} includes many automatic enhancements that improve your documentation.

## Dark Mode

The skin supports automatic dark/light mode based on system preferences. Users can also toggle manually, and the preference is saved.

## Heading Anchors

All headings automatically get anchor IDs for direct linking:

```html
<h2 id="my-heading">My Heading</h2>
```

Link to sections using `#my-heading` in URLs.

### Configuration

| Setting       | Description                                | Default |
| ------------- | ------------------------------------------ | ------- |
| `fixHeadings` | Enable heading anchor fixes and ID cleanup | `true`  |

To disable heading processing:

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
>   No automatic anchor links.
>   ```

## Bootstrap Tables

All tables are automatically styled with Bootstrap classes for consistent appearance.

## External Links

External links (URLs starting with `http://` or `https://`) are automatically styled with a CSS class to distinguish them from internal links.

### Configuration

| Setting             | Description                  | Default        |
| ------------------- | ---------------------------- | -------------- |
| `externalLinkClass` | CSS class for external links | `externalLink` |

To use a custom class or disable external link styling:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <externalLinkClass>external</externalLinkClass>
>   </custom>
>   ```
>
>   Set to `false` to disable external link styling.
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   externalLinkClass: false
>
>   # No External Link Styling
>
>   External links look like internal links.
>   ```

> [!NOTE]
> Links to your project's URL (from `pom.xml`) and organization URL are not marked as external.

## Protocol-Relative URLs

Protocol-relative URLs (starting with `//`) are automatically converted to `https://` for security.

### Configuration

| Setting                   | Description                     | Default |
| ------------------------- | ------------------------------- | ------- |
| `fixProtocolRelativeUrls` | Convert `//` URLs to `https://` | `true`  |

To disable:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <fixProtocolRelativeUrls>false</fixProtocolRelativeUrls>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   fixProtocolRelativeUrls: false
>
>   # Keep Protocol-Relative URLs
>
>   URLs starting with // stay unchanged.
>   ```

## Footer Copyright

The footer displays copyright using:
- `<inceptionYear>` from pom.xml
- `<organization><name>` from pom.xml

## Code Block Features

- **Copy button**: One-click copying of code blocks
- **Syntax highlighting**: Automatic via PrismJS

See [Code Highlighting](code.html) for configuration options.

## See Also

- [Configuration Reference](settings.html) - All configuration options
- [Styling](styles.html) - Color themes and CSS customization
