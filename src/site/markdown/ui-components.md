keywords: tabs, accordion, carousel, collapse, callouts, notes, admonitions, ui, bootstrap, components
description: Interactive UI components including tabs, accordion, carousel, collapsible sections, and callouts.

# UI Components (Tabs, Accordion, etc.)

The skin includes [Angular UI Bootstrap](https://angular-ui.github.io/bootstrap/) components for interactive documentation, with a simple Markdown syntax.

## Available Components

| Component               | Description                              |
| ----------------------- | ---------------------------------------- |
| [Callouts](#callouts)   | Notes, tips, warnings, and cautions      |
| [Tabs](#tabs)           | Organize content in switchable tabs      |
| [Code Tabs](#code-tabs) | Show code examples in multiple languages |
| [Accordion](#accordion) | Collapsible content panels               |
| [Collapse](#collapse)   | Toggle content visibility                |
| [Carousel](#carousel)   | Image slideshow                          |

## Callouts

Callouts highlight important information using GitHub-style admonition syntax. They help draw attention to notes, tips, warnings, and other key content.

### Syntax

Use a blockquote starting with `[!TYPE]` where TYPE is one of: NOTE, TIP, IMPORTANT, WARNING, or CAUTION.

```markdown
> [!NOTE]
> Useful information that users should know, even when skimming content.

> [!TIP]
> Helpful advice for doing things better or more easily.

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

> [!WARNING]
> Urgent info that needs immediate user attention to avoid problems.

> [!CAUTION]
> Advises about risks or negative outcomes of certain actions.
```

### Live Demo

> [!NOTE]
> Useful information that users should know, even when skimming content.

> [!TIP]
> Helpful advice for doing things better or more easily.

> [!IMPORTANT]
> Key information users need to know to achieve their goal.

> [!WARNING]
> Urgent info that needs immediate user attention to avoid problems.

> [!CAUTION]
> Advises about risks or negative outcomes of certain actions.

### With Rich Content

Callouts support full Markdown formatting inside:

```markdown
> [!TIP]
> You can include **bold**, *italic*, and `code` in callouts.
>
> - Lists work too
> - Multiple items
>
> And even [links](https://example.com) and code blocks:
>
> ```java
> System.out.println("Hello!");
> ```
```

> [!TIP]
> You can include **bold**, *italic*, and `code` in callouts.
>
> - Lists work too
> - Multiple items
>
> And even [links](https://example.com) and code blocks:
>
> ```java
> System.out.println("Hello!");
> ```

## Tabs

Tabs organize content into multiple panels, with one panel visible at a time.

### Syntax

Use a blockquote with the `[!TABS]` marker and a bullet list:

```markdown
> [!TABS]
> - First Tab
>
>   Content of the **first tab** with Markdown formatting.
>
> - Second Tab
>
>   Content of the *second tab*.
>
> - Third Tab
>
>   Content with `code` and [links](url).
```

**How it works:**

- Start a blockquote with `> [!TABS]` followed by optional parameters
- Use a bullet list (`-` or `*`) for each tab
- The first line of each list item becomes the tab heading (plain text)
- Add a blank line, then the tab content with full Markdown support

**Available options:**

| Option           | Description                            | Default                            |
| ---------------- | -------------------------------------- | ---------------------------------- |
| `active=varName` | AngularJS variable to track active tab | `tabsActive1`, `tabsActive2`, etc. |
| `justified=true` | Make tabs fill the entire width        | `false`                            |
| `vertical=true`  | Display tabs vertically                | `false`                            |

### Live Demo

> [!TABS active=demoMarkdownTabs]
> - <span class="fa fa-home"></span> _Overview_
>
>   This is the **overview** content created with the simple Markdown syntax.
>   Tabs are great for organizing related information.
>
> - Installation
>
>   To install the skin:
>   1. Add the skin dependency to your `site.xml`
>   2. Add `maven-skin-tools` to your `pom.xml`
>   3. Run `mvn site`
>
>   Check the [Quick Start](start.html) guide for details.
>
> - Configuration
>
>   Configure the skin in your `site.xml` file.
>   See [Configuration Reference](settings.html) for all options.

### Justified Tabs

> [!TABS justified=true]
> - Tab One
>
>   These tabs fill the entire width because `justified=true` is set.
>
> - Tab Two
>
>   The second tab content.

### Vertical Tabs

> [!TABS vertical=true]
> - Vertical Tab 1
>
>   Vertical tabs are great for side navigation.
>
> - Vertical Tab 2
>
>   More content in the second tab.

### Code Tabs

A common use case for tabs in technical documentation is showing the same code example in multiple programming languages. Combine tabs with fenced code blocks:

````markdown
> [!TABS]
> - Java
>
>   ```java
>   public class HelloWorld {
>       public static void main(String[] args) {
>           System.out.println("Hello, World!");
>       }
>   }
>   ```
>
> - Python
>
>   ```python
>   def main():
>       print("Hello, World!")
>
>   if __name__ == "__main__":
>       main()
>   ```
>
> - JavaScript
>
>   ```javascript
>   function main() {
>     console.log("Hello, World!");
>   }
>
>   main();
>   ```
````

**Live Demo:**

> [!TABS]
> - Java
>
>   ```java
>   public class HelloWorld {
>       public static void main(String[] args) {
>           System.out.println("Hello, World!");
>       }
>   }
>   ```
>
> - Python
>
>   ```python
>   def main():
>       print("Hello, World!")
>
>   if __name__ == "__main__":
>       main()
>   ```
>
> - JavaScript
>
>   ```javascript
>   function main() {
>     console.log("Hello, World!");
>   }
>
>   main();
>   ```

> [!TIP]
> Code tabs are perfect for API documentation, SDK examples, and tutorials where users may work with different programming languages.

## Accordion

Accordions display collapsible content panels for presenting information in limited space.

### Syntax

Use a blockquote with the `[!ACCORDION]` marker and a bullet list:

```markdown
> [!ACCORDION]
> - First Panel Title
>
>   Content of the **first panel** with Markdown formatting.
>
> - Second Panel Title
>
>   Content of the *second panel*.
>
> - Third Panel Title
>
>   Content with `code` and [links](url).
```

**How it works:**

- Start a blockquote with `> [!ACCORDION]` followed by optional parameters
- Use a bullet list (`-` or `*`) for each panel
- The first line of each list item becomes the panel heading (plain text)
- Add a blank line, then the panel content with full Markdown support

**Available options:**

| Option               | Description                                     | Default           |
| -------------------- | ----------------------------------------------- | ----------------- |
| `is-open=N`          | Index of the panel to open by default (0-based) | `0` (first panel) |
| `close-others=false` | Allow multiple panels open simultaneously       | `true`            |

### Live Demo

> [!ACCORDION]
> - What is Sentry Maven Skin?
>
>   Sentry Maven Skin is a **modern, responsive skin** for Maven-generated
>   documentation sites. It provides:
>
>   - Professional look and feel
>   - Full-text search
>   - Dark and light themes
>   - Mobile-friendly design
>
> - How do I install it?
>
>   Add the skin dependency to your `site.xml`, add `maven-skin-tools` to your
>   `pom.xml`, then run `mvn site`. See the [Quick Start](start.html) for details.
>
> - Is it free?
>
>   Yes! Sentry Maven Skin is **open source** and licensed under the
>   [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).

### With Options

```markdown
> [!ACCORDION close-others=false, is-open=1]
> - Panel One
>
>   This accordion allows multiple panels open at once.
>
> - Panel Two (Open by Default)
>
>   This panel is open by default because `is-open=1`.
```

## Collapse (Collapsible Sections)

Hide and show content with smooth animation. This is perfect for optional details, FAQ sections, or reducing page clutter.

### Syntax

Use a blockquote with the `[!COLLAPSIBLE]` marker, followed by the title on the next line:

```markdown
> [!COLLAPSIBLE]
> Click to expand this section
>
> This content will be **hidden by default** and can be revealed
> by clicking the title. You can use all Markdown formatting:
>
> - Lists work perfectly
> - Including **bold** and *italic* text
> - And [links](https://example.com) too
>
> Even code blocks are supported.
```

**How it works:**

- Start a blockquote with `> [!COLLAPSIBLE]` followed by optional parameters
- Put the title on the next line of the same blockquote
- Add a blank line (`>` alone) to separate the title from the content
- All following paragraphs in the blockquote become the collapsible content
- Standard Markdown formatting is fully supported inside

### Live Demo

> [!COLLAPSIBLE]
> What is Sentry Maven Skin?
>
> Sentry Maven Skin is a **modern, responsive skin** for Maven-generated
> documentation sites. It provides:
>
> - Professional look and feel
> - Full-text search
> - Dark and light themes
> - Mobile-friendly design
>
> See the [Quick Start](start.html) guide to get started!

> [!COLLAPSIBLE]
> How do I install it?
>
> Add the skin dependency to your `site.xml`, add `maven-skin-tools` to your
> `pom.xml`, then run `mvn site`. It's that simple!

## Carousel

Cycle through images like a slideshow. Perfect for screenshots, galleries, or step-by-step visual guides.

### Syntax

Use a blockquote with the `[!CAROUSEL]` marker and a list of images:

```markdown
> [!CAROUSEL]
> * ![First image description](images/slide1.png)
> * ![Second image description](images/slide2.png)
> * ![Third image description](images/slide3.png)
```

**How it works:**

- Start a blockquote with `> [!CAROUSEL]` followed by optional parameters
- Use a bullet list (`-` or `*`) for each slide
- Each list item contains an image using standard Markdown syntax: `![alt text](path)`
- The alt text becomes both the image description and the carousel caption

**Available options:**

| Option           | Description                              | Default                                    |
| ---------------- | ---------------------------------------- | ------------------------------------------ |
| `active=varName` | AngularJS variable to track active slide | `carouselActive1`, `carouselActive2`, etc. |
| `interval=5000`  | Time in milliseconds between slides      | `5000` (Angular UI Bootstrap default)      |
| `no-wrap=true`   | Disable continuous cycling               | `false`                                    |
| `no-pause=true`  | Disable pausing on hover                 | `false`                                    |

### Live Demo

> [!CAROUSEL]
> * ![Desktop View: Clean, professional layout](images/general-screenshot.png)
> * ![Dark Mode: Automatic theme detection](images/dark.png)
> * ![Built-in Search: No external services needed](images/search-2.png)

### With Options

```markdown
> [!CAROUSEL interval=3000, no-wrap=true]
> * ![First slide](images/slide1.png)
> * ![Second slide](images/slide2.png)
```

## Best Practices

1. **Keep it simple**: Use components to enhance readability, not add complexity
2. **Use meaningful headings**: Panel and tab titles should clearly describe content
3. **Accessibility**: Ensure interactive elements are keyboard-accessible
4. **Mobile-friendly**: Test components on smaller screens

## More Information

See the [Angular UI Bootstrap documentation](https://angular-ui.github.io/bootstrap/) for advanced HTML syntax and additional options.
