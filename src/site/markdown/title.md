title: The Real Title
description: ${project.name} uses the first heading as the document title by default. This can be customized with the title header.

# Automatic Title

The page title is automatically built from the project name and the first heading found in the document.

![Document and window title is automatically generated](images/title.png)

However, you can also specify the page title in the Markdown source file with [the `title` header](headers.html):

```md
title: The Real Title

# First Heading (not used as title)

Lorem ipsum...
```
