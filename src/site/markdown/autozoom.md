keywords: pictures, zoom, inline, full size, thumbnail
description: ${project.name} automatically resizes images as thumbnails and allows the viewer to display the full-size image when needed.

# Auto-zoom images

By default, all images in the source documents are displayed as a thumbnail, which the reader needs to click on to see in real size.

![Nice moiré effect](images/zoomable-example.jpg)

If you want an image to be displayed inline _as is_, you will need to set its description to `inline`.

Examples:

```md
![My Description](./images/my-picture.png) This will show as a zoomable image

![inline](./images/icon.png) This will show "inline", with no zoom
```
