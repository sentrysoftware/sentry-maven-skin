title: Page Titles
keywords: title, heading, page title, browser title
description: How the skin generates page titles from headings and the title header.

# Page Title Generation

By default, the page title is built from:
1. The **project name** (from pom.xml)
2. The **first heading** in the document

![Document and browser title generated automatically](images/title.png)

## Custom Title

Override the automatic title using the `title` front matter header:

```markdown
title: My Custom Title

# First Heading

This page will use "My Custom Title" instead of "First Heading".
```

## Title Display

| Location    | Content                   |
| ----------- | ------------------------- |
| Browser tab | Project Name - Page Title |
| Page header | Page Title only           |

See [Front Matter Headers](headers.html) for more header options.
