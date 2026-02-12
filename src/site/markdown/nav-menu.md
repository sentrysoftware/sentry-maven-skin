keywords: menu, sidebar, navigation, hierarchy
description: Configure the sidebar navigation menu with up to 2 levels of hierarchy.

# Navigation Menu

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

The sidebar menu organizes your documentation into sections and pages.

## Basic Structure

Define menus in `src/site/site.xml` under `<body>`:

```xml
<body>
  <menu name="Getting Started">
    <item name="Overview" href="index.html"/>
    <item name="Installation" href="install.html"/>
  </menu>

  <menu name="User Guide">
    <item name="Configuration" href="config.html"/>
    <item name="Usage" href="usage.html"/>
  </menu>
</body>
```

## Two-Level Hierarchy

Nest `<item>` elements for sub-pages:

```xml
<menu name="Documentation">
  <item name="Basic Monitors" href="monitors/index.html">
    <item name="Filesystem" href="monitors/filesystem.html"/>
    <item name="Process" href="monitors/process.html"/>
    <item name="Network" href="monitors/network.html"/>
  </item>
  <item name="Advanced Topics" href="advanced/index.html">
    <item name="Custom Scripts" href="advanced/scripts.html"/>
    <item name="API Reference" href="advanced/api.html"/>
  </item>
</menu>
```

### Collapsed View

![Collapsed menu showing folder icons](images/menu-collapse.png)

### Expanded View

![Expanded menu showing sub-items](images/menu-expand.png)

### Mobile View

On mobile, sub-items are shown as links within the parent page:

![Mobile menu with inline sub-items](images/menu-expand-mobile.png)

## Limitations

> [!WARNING]
> Maximum **2 levels** of hierarchy (menu > item > item). Third-level nesting is not supported.

## Next Steps

Your site is now set up! Start writing documentation:

- [Writing a Page](page-structure.html) - Structure your content
- [Code Highlighting](code.html) - Format code examples
- [Images](images.html) - Add screenshots
- [Configuration Reference](settings.html) - Customize skin features
