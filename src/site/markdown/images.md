keywords: pictures, zoom, inline, thumbnail, lightbox, webp, compression, optimization
description: Add images with auto-zoom thumbnails and automatic WebP conversion.

# Images

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Images in your documentation are automatically enhanced with click-to-zoom and WebP conversion.

## Adding Images

Place images in `src/site/resources/images/` and reference them with standard Markdown syntax:

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

## Configuration

All image features can be configured globally in `site.xml` or per-page using frontmatter:

| Setting                     | Description                           | Default                                         |
| --------------------------- | ------------------------------------- | ----------------------------------------------- |
| `checkImageLinks`           | Verify image files exist during build | `true`                                          |
| `convertImagesToWebp`       | Convert images to WebP format         | `true`                                          |
| `setExplicitImageSize`      | Add width/height attributes to images | `true`                                          |
| `convertImagesToThumbnails` | CSS selector for zoomable images      | `img:not([alt=inline]):not([uib-carousel] img)` |

### Disable Image Validation

To allow missing images without build failures:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <checkImageLinks>false</checkImageLinks>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   checkImageLinks: false
>
>   # Draft Page
>
>   Missing images won't cause build failures.
>   ```

### Disable WebP Conversion

To keep images in their original format:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <convertImagesToWebp>false</convertImagesToWebp>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   convertImagesToWebp: false
>
>   # Legacy Screenshots
>
>   Images stay in original format.
>   ```

### Disable Explicit Image Size

To prevent automatic width/height attributes on images:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <setExplicitImageSize>false</setExplicitImageSize>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   setExplicitImageSize: false
>
>   # Page with Responsive Images
>
>   Images will not have explicit dimensions.
>   ```

### Disable Auto-Zoom

To disable the zoomable thumbnail feature completely:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <convertImagesToThumbnails>false</convertImagesToThumbnails>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   convertImagesToThumbnails: false
>
>   # Simple Page
>
>   All images display at full size without zoom.
>   ```

### Zoom Only Specific Images

Use a custom CSS selector to control which images become zoomable thumbnails.

**Zoom only images with "zoom" in the filename:**

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <convertImagesToThumbnails>img[src*=zoom]</convertImagesToThumbnails>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   convertImagesToThumbnails: img[src*=zoom]
>
>   # Selective Zoom
>
>   Only images with "zoom" in filename are zoomable.
>   ```

With this setting:
- `![Dashboard](images/dashboard-zoom.png)` → Zoomable thumbnail
- `![Icon](images/icon.png)` → Displays inline, no zoom

**Zoom only images in a specific folder:**

```xml
<custom>
  <convertImagesToThumbnails>img[src*=screenshots]</convertImagesToThumbnails>
</custom>
```

## Best Practices

- Use descriptive alt text for accessibility
- Place images in `src/site/resources/images/`
- Use `inline` for icons and badges (under 50px)
- Use zoom (default) for screenshots and diagrams
- Prefer PNG for diagrams, JPG for photos

## See Also

- [Configuration Reference](settings.html) - All configuration options
- [Code Highlighting](code.html) - Format code blocks
- [UI Components](ui-components.html) - Tabs and accordions

