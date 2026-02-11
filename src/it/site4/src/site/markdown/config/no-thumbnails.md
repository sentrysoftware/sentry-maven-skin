---
convertImagesToThumbnails: false
---

# No Thumbnails Test

This page tests the `convertImagesToThumbnails: false` front matter setting.

## Expected Behavior

Images on this page should NOT be wrapped in `<zoomable>` elements.
They should appear as regular `<img>` tags.

## Test Image

![Test Image](../images/test-image.png)

The image above should be a plain `<img>` tag, not wrapped in a zoomable thumbnail.

## Verification

Check the HTML source - images should not have `<zoomable>` wrapper elements.
