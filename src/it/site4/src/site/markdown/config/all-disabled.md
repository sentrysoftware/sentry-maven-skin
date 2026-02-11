---
interpolation: none
fixProtocolRelativeUrls: false
checkImageLinks: false
convertImagesToWebp: false
setExplicitImageSize: false
convertImagesToThumbnails: false
externalLinkClass: false
fixHeadings: false
copyToClipboard: false
buildIndex: false
buildAiIndex: false
syntaxHighlighting: false
bodyClass: minimal-page
---

# All Features Disabled Test

This page tests disabling ALL configurable features via front matter.

## Configuration Applied

| Setting | Value |
|---------|-------|
| interpolation | none |
| fixProtocolRelativeUrls | false |
| checkImageLinks | false |
| convertImagesToWebp | false |
| setExplicitImageSize | false |
| convertImagesToThumbnails | false |
| externalLinkClass | false |
| fixHeadings | false |
| copyToClipboard | false |
| buildIndex | false |
| buildAiIndex | false |
| syntaxHighlighting | false |
| bodyClass | minimal-page |

## Test Content

### Image (No Processing)

![Test Image](../images/test-image.png)

### Code Block (No Syntax Highlighting, No Copy Button)

```java
public class Test {
    public static void main(String[] args) {
        System.out.println("No highlighting!");
    }
}
```

### External Link (No externalLink Class)

Check out [Apache Maven](https://maven.apache.org) - should NOT have `externalLink` class.

### Property Placeholder (No Interpolation)

This should show literally: ${project.version}

## Verification Checklist

- [ ] Body has `minimal-page` class
- [ ] Image is plain PNG (not WebP)
- [ ] No zoomable wrapper on image
- [ ] No copy button on code blocks
- [ ] No syntax highlighting colors
- [ ] External links have no special class
- [ ] `${project.version}` shown literally
- [ ] Page not in search index
- [ ] No .html.md file generated
