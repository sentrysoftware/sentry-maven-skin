keywords: css, color, font, theme, customization
description: Customize colors and fonts with simple CSS variables or use built-in color themes.

# Styling (Colors and Fonts)

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Customize the look and feel of your documentation with built-in themes or custom CSS variables.

## Built-in Themes

The easiest way to change colors is to use a built-in theme. In `src/site/site.xml`:

```xml
<custom>
  <bodyClass>sentry-purple</bodyClass>
</custom>
```

| Theme           | Preview                                   |
| --------------- | ----------------------------------------- |
| Default (blue)  | ![Blue theme](images/sentry-blue.png)     |
| `sentry-green`  | ![Green theme](images/sentry-green.png)   |
| `sentry-orange` | ![Orange theme](images/sentry-orange.png) |
| `sentry-purple` | ![Purple theme](images/sentry-purple.png) |

## Custom Colors

Create `src/site/resources/css/site.css` to override CSS variables:

```css
body {
  --banner-bgcolor: #266fd0;
  --banner-fgcolor: white;
  --link-color: #d50c37;
}
```

### Color Variables

| Variable              | Description                     |
| --------------------- | ------------------------------- |
| `--banner-bgcolor`    | Header background color         |
| `--banner-fgcolor`    | Header text color               |
| `--main-bgcolor`      | Page background (usually white) |
| `--main-fgcolor`      | Page text color (usually black) |
| `--alternate-bgcolor` | Accent background color         |
| `--alternate-fgcolor` | Accent text color               |
| `--link-color`        | Link color                      |

### Default Values

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=colors} -->

### Dark Mode Colors

Customize colors for dark mode with the `body.dark` selector:

```css
body {
  --link-color: #d50c37;
}

body.dark {
  --link-color: #ff6989;
}
```

Default dark mode values:

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=dark-colors} -->

## Custom Fonts

Override font families on the `:root` element:

```css
:root {
  --title-font: "Gill Sans", sans-serif;
  --heading-font: system-ui;
  --content-font: Georgia, serif;
  --content-font-size: 16px;
}
```

### Font Variables

| Variable              | Description             | Default    |
| --------------------- | ----------------------- | ---------- |
| `--title-font`        | Project title in banner | RobotoSlab |
| `--heading-font`      | Section headings        | RobotoSlab |
| `--content-font`      | Body text               | Roboto     |
| `--content-font-size` | Base font size          | 15px       |

### All Font Properties

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=fonts} -->

## Next Steps

- [Navigation Links](nav-links.html) - Configure header and logo
- [Navigation Menu](nav-menu.html) - Set up sidebar navigation
- [Writing a Page](page-structure.html) - Start writing documentation
