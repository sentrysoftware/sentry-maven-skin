keywords: pictures, zoom, inline, thumbnail, lightbox, webp, compression, optimization
description: Add images with auto-zoom thumbnails and automatic WebP conversion.

# Images

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Images in your documentation are automatically enhanced with click-to-zoom and WebP conversion.

## Adding Images

Use standard Markdown syntax:

```markdown
![Screenshot of the dashboard](images/dashboard.png)
```

## Auto-Zoom Thumbnails

By default, images are displayed as clickable thumbnails:

![Example zoomable image](images/zoomable-example.jpg)

**Default behavior:**
- Thumbnail display at reduced size
- Click-to-zoom lightbox overlay
- Smooth zoom animation

### Inline Images (No Zoom)

For icons or small images that should display at actual size, use `inline` as the alt text:

```markdown
![inline](./images/icon.png)
```

### Comparison

| Alt Text | Behavior |
|----------|----------|
| `![Description](image.png)` | Zoomable thumbnail |
| `![inline](image.png)` | Inline, no zoom |

## Automatic WebP Conversion

All images are automatically converted to WebP format during the build for better performance.

| Source Format | WebP Type | Quality |
|---------------|-----------|---------|
| PNG, GIF | Lossless | Perfect quality |
| JPG, JPEG | Lossy | Optimized size |

**Benefits:**
- **Smaller files**: WebP is typically 25-35% smaller than JPEG/PNG
- **Better Lighthouse scores**: Faster page loads improve performance metrics
- **Automatic fallback**: Browsers without WebP support use the original

No configuration required - conversion is automatic.

> [!TIP]
> WebP conversion significantly improves Lighthouse performance scores by reducing image file sizes by 25-35%.

## Image Validation

The build fails if a referenced image does not exist, catching broken image links early.

## Best Practices

- Use descriptive alt text for accessibility
- Place images in `src/site/resources/images/`
- Use `inline` for icons and badges (under 50px)
- Use zoom (default) for screenshots and diagrams
- Prefer PNG for diagrams, JPG for photos

## Next Steps

- [Code Highlighting](code.html) - Format code blocks
- [UI Components](ui-components.html) - Tabs and accordions
- [Doxia Features](doxia.html) - Advanced macros
