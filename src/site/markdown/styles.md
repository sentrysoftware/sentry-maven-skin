keywords: css, color, font
description: ${project.name}'s look and feel can be easily customized for your documentation with a few CSS properties.

# Styling

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

${project.name}'s look and feel can be easily customized for your documentation with a few CSS properties.

## How CSS customization works

Create a `src/site/resources/css/site.css` file that will be loaded with your documentation and that will override the CSS that ships by default with the skin.

In `site.css`, you will redefine the [value of a few CSS variables](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties) on the `<body>` element.

To make sure your CSS rules apply in priority, you instruct ${project.name} to add a specific CSS class to the `<body>` element, by setting the `<bodyClass>` class in `src/site/site.xml` (see [Settings](settings.xml)).

If you specify in `site.xml`:

```xml
<project>
  ...
  <custom>
    <bodyClass>my-theme</bodyClass>
    ...
  </custom>
```

The `<body>` element in the generated HTML pages is created as:

```html
<html>
  ...
  <body class="my-theme">
```

Which can then be *selected* in CSS with:

```css
body.my-theme {
  /* Override the skin's CSS variables */
}
```

## Colors

You will mainly set 5 CSS variables to modify the colors of the skin for your documentation:

| CSS Variable | Description |
|---|---|
| `--banner-bgcolor` | Main theme color, used in the title banner background. |
| `--banner-fgcolor` | Text color in the title banner. Must provide enough contrast with `banner-bgcolor` for accessibility. |
| `--main-bgcolor` | Main content background color (usually `white`). |
| `--main-fgcolor` | Main content text color (usually `black`). |
|  `--link-color` | Color for the links (usually something blue). |

If `<bodyClass>` is set to `my-theme`, the CSS in `site.css` may simply look like:

```css
/* We're only customizing the title banner background color and the links */
body.my-theme {
  --banner-bgcolor: #266fd0;
  --link-color: #d50c37;
}
```

Default values for all customizable colors are (variable names are self-explanatory):

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=colors} -->

### Dark colors

When the user is displaying your documentation using a dark colors scheme, the `dark` class is added to the `<body>` element, so you can customize the colors to use when in dark mode with the `body.my-theme.dark` CSS selector:

```css
/* Default colors in light mode */
body.open-sentry {
  --banner-bgcolor: #266fd0;
  --link-color: #d50c37;
}
/* Use a lighter red when in dark mode */
body.open-sentry.dark {
  --link-color: #ff6989;
}
```

Default values for colors in dark are:

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=dark-colors} -->

## Fonts

3 fonts are used by the ${project.name}, which can be customized with the below CSS variables:

| CSS Variable | Description |
| ------------ | ----------- |
| `--title-heavy-font` | Used for the main title in the banner and the document headings 1 and 2. |
| `--title-font` | Used in the navigation bars. |
| `--content-font` | Used in the main content of your documentation, everywhere else. |

Each of these variables can specify [several alternate fonts](https://developer.mozilla.org/en-US/docs/Web/CSS/font-family) if the first one is not available on the reader's system.

Example in `site.css`:

```css
body.my-theme {
  --title-heavy-font: "Gill Sans", sans-serif;
  --title-font: system-ui;
  --content-font: Georgia, serif;
}
```
