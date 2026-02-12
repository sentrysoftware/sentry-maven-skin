---
checkImageLinks: false
convertImagesToWebp: false
setExplicitImageSize: false
convertImagesToThumbnails: false
---

# No Image Processing Test

This page tests disabling all image processing via front matter:

- `checkImageLinks: false` - Don't verify image links exist
- `convertImagesToWebp: false` - Don't convert images to WebP
- `setExplicitImageSize: false` - Don't add width/height attributes
- `convertImagesToThumbnails: false` - Don't wrap in zoomable thumbnails

## Expected Behavior

Images should remain exactly as specified in Markdown, with no transformations.

## Test Image

![Test Image](../images/test-image.png)

## Verification

Check the HTML source:
1. Image `src` should still be `.png` (not `.webp`)
2. No `width` or `height` attributes on the `<img>` tag
3. No `<zoomable>` wrapper element
