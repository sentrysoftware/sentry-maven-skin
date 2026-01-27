keywords: toc, table of contents, navigation
description: Automatically generate a table of contents from your page headings.

# Automatic Table of Contents

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Generate a table of contents automatically from your page headings.

## How to Add a ToC

Insert this macro in your Markdown file:

```html
<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->
```

**Important**: The `id=toc` parameter is required for proper styling.

## Macro Parameters

| Parameter   | Description                     | Default  |
| ----------- | ------------------------------- | -------- |
| `fromDepth` | Starting heading level (1 = h1) | 1        |
| `toDepth`   | Ending heading level (2 = h2)   | 2        |
| `id`        | Must be `toc` for the skin      | Required |

## Example

A document with these headings:

```markdown
${esc.h} Page Title
${esc.h}${esc.h} Section One
${esc.h}${esc.h}${esc.h} Subsection
${esc.h}${esc.h} Section Two
```

With `fromDepth=1` and `toDepth=2`, the ToC includes:
- Page Title
- Section One
- Section Two

(Subsection is excluded because it's level 3)

## Display Behavior

- **Desktop**: ToC floats on the right side of the page
- **Mobile**: ToC displays inline at the top

## Reference

See [Maven Doxia macros](https://maven.apache.org/doxia/macros/index.html) for more details.

## Advanced ToC Techniques

For more advanced usage, you can consider the following:

- **Custom Styling**: Customize the style of your Table of Contents using CSS or Markdown extensions to match your document's design.

- **Depth Control**: Choose how many heading levels to include in your Table of Contents based on the complexity of your content.

- **Ordered vs. Unordered Lists**: Decide whether you want an ordered (numbered) or unordered (bulleted) list format for your ToC.

## Conclusion

A well-crafted Table of Contents is a valuable tool for presenting your content in a clear and user-friendly manner. Whether you're creating a document, a book, a website, or any content with multiple sections, a ToC simplifies navigation and improves the overall reading experience. So, the next time you embark on a content creation journey, remember the power of a well-structured Table of Contents to guide your readers through your material effortlessly.
