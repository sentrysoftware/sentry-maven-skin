keywords: css, color, font
description: ${project.name}'s look and feel can be easily customized for your documentation with a few CSS properties.

# Styling

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

${project.name}'s look and feel can be easily customized for your documentation with a few CSS properties.

## How CSS customization works

Create a `src/site/resources/css/site.css` file in your project. This CSS will be loaded with your documentation and that will override the CSS that ships by default with the skin.

In this `site.css`, you will redefine the [value of a few CSS variables](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties) on the `<body>` element for colors, and on the `:root` element for the fonts.

## Colors

You will mainly set 5 CSS variables to modify the colors of the skin for your documentation:

| CSS Variable | Description |
|---|---|
| `--banner-bgcolor` | Main theme color, used in the title banner background. |
| `--banner-fgcolor` | Text color in the title banner. Must provide enough contrast with `banner-bgcolor` for accessibility. |
| `--main-bgcolor` | Main content background color (usually `white`). |
| `--main-fgcolor` | Main content text color (usually `black`). |
|  `--link-color` | Color for the links (usually something blue). |

```css
/* We're only customizing the title banner background color and the links */
body {
  --banner-bgcolor: #266fd0;
  --link-color: #d50c37;
}
```

### Colors default values

Default values for all customizable colors are (variable names are self-explanatory):

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=colors} -->

### Dark colors

When the user is displaying your documentation using a dark colors scheme, the `dark` class is added to the `<body>` element, so you can customize the colors to use when in dark mode with the `body.dark` CSS selector:

```css
/* Default colors in light mode */
body {
  --banner-bgcolor: #266fd0;
  --link-color: #d50c37;
}
/* Use a lighter red when in dark mode */
body.dark {
  --link-color: #ff6989;
}
```

Default values for colors in dark are:

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=dark-colors} -->

## Fonts

3 main font families are used by the ${project.name}, which can be customized with the below CSS variables defined on the `:root` pseudo-element:

| CSS Variable | Description | Default |
| ------------ | ----------- | ------- |
| `--title-font` | Mainly used for the project title in the banner and the document title. | `Raleway` |
| `--heading-font` | Used for headings in the document, and header and footer. | `Raleway` |
| `--content-font` | Used in the main content of your documentation, everywhere else. | `Lato` |
| `--content-font-size` | Associated default font size for the main content. | `15px` |

Each of these variables can specify [several alternate fonts](https://developer.mozilla.org/en-US/docs/Web/CSS/font-family) if the first one is not available on the reader's system.

Example of `site.css`:

```css
:root {
  --title-font: "Gill Sans", sans-serif;
  --heading-font: system-ui;
  --content-font: Georgia, serif;
  --content-font-size: medium;
}
```

### All fonts customizable properties

Actually, the font family, size, weight and style can all be customized with CSS variables defined on the `:root` element. The HTML page is split in 6 main sections with separate settings:

* `top-...` for the very top header
* `banner-...` for the colorful banner with the project title
* `left-...` for the navigation menu on the left
* `main-title-...` for the document title
* `content-...` for the content default properties
* `bottom-...` for the page footer

The default values for all fonts properties are:

<!-- MACRO{snippet|file=src/main/webapp/css/sentry.css|id=fonts} -->
