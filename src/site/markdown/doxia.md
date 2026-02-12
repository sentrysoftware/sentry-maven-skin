keywords: doxia, macro, snippet, toc, include, velocity
description: Maven Doxia macros and features you can use in your documentation.
interpolation: none

# Doxia Features

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Maven Doxia provides powerful macros for dynamic content. This page covers the most useful ones.

## Table of Contents Macro

Generate a ToC from page headings:

```html
<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->
```

| Parameter | Description | Default |
|-----------|-------------|---------|
| `fromDepth` | Starting heading level | 1 |
| `toDepth` | Ending heading level | 2 |
| `id` | Must be `toc` for styling | Required |

See [Table of Contents](toc.html) for details.

## Snippet Macro

Include code from external files:

```html
<!-- MACRO{snippet|id=example|file=src/main/java/Example.java} -->
```

### Marking Snippets in Source

In your source file, mark the snippet boundaries:

```java
public class Example {
    // START SNIPPET: example
    public void doSomething() {
        System.out.println("Hello");
    }
    // END SNIPPET: example
}
```

### Snippet Parameters

| Parameter | Description |
|-----------|-------------|
| `id` | Snippet identifier (matches START/END markers) |
| `file` | Path to source file (relative to project root) |
| `url` | URL to fetch snippet from |
| `verbatim` | If `false`, content is processed as markup |

### Full File Include

Omit `id` to include the entire file:

```html
<!-- MACRO{snippet|file=src/config/example.xml} -->
```

## Echo Macro

Display the current date/time:

```html
<!-- MACRO{echo|value=Today is ${currentDate}} -->
```

Useful for showing when documentation was generated.

## Comments

Add comments that won't appear in output:

```html
<!-- This is a comment and won't be rendered -->
```

## Reference

- [Maven Doxia Macros](https://maven.apache.org/doxia/macros/index.html) - Official documentation
- [Doxia Markdown](https://maven.apache.org/doxia/doxia/doxia-modules/doxia-module-markdown/) - Markdown module
- [Maven Site Plugin](https://maven.apache.org/plugins/maven-site-plugin/) - Site generation

## Next Steps

- [Page Structure](page-structure.html) - How to structure pages
- [Maven Properties](maven-properties.html) - Use pom.xml values
- [Code Highlighting](code.html) - Format code blocks
- [Configuration Reference](settings.html) - All site.xml and front matter options
