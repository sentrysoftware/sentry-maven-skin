keywords: compression, lighthouse, jpg, jpeg, apng, agif
description: ${project.name} automatically converts all images to WEBP to optimize the page and improve its Lighthouse score.

# Automatic conversion of all images to WEBP

All images are automatically converted to the WEBP format. The original format is kept and the HTML page is updated so that the browser falls back to the original format if WEBP is not supported.

* PNG and GIF are converted to *lossless* WEBP
* other formats are converted to *lossy* WEBP

Also, all links to images are checked: the build will fail if an image referenced in a Markdown document doesn't exist.