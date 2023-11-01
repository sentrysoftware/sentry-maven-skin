# Auto-zoom images

By default, all images in the source documents are displayed as a thumbnail, which the reader needs to click on
to see in real size.

If you want an image to be displayed *as is* (like ones that you would use for a bullet list, etc.),
you will need to set its `alt` attribute to `inline`.

Examples:

```md
![My Description](./images/my-picture.png) This will show as a zoomable image

![inline](./images/icon.png) This will show "inline", with no zoom
```
