# Generate a ToC automatically

The *Sentry Maven Skin* now leverages Maven Doxia standard **TOC** macro, which needs to be inserted in Markdown documents as below:

```html
<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->
```

More information about [Maven Doxia macros](https://maven.apache.org/doxia/macros/index.html).

**IMPORTANT**: For the TOC macro to work in the skin, it must specify `id=toc` as in the example above.

