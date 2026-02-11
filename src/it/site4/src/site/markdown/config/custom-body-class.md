---
bodyClass: custom-page-class my-special-theme
---

# Custom Body Class Test

This page tests the `bodyClass` front matter setting.

## Expected Behavior

The `<body>` element should have the additional CSS classes:
`custom-page-class my-special-theme`

## Verification

Open browser developer tools and inspect the `<body>` element.
It should have classes like:
```
<body class="sentry-site custom-page-class my-special-theme ...">
```

## Use Case

This feature allows:
- Per-page theming
- Special styling for certain pages
- A/B testing different styles
- Landing page customization
