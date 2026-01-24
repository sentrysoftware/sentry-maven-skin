keywords: elastic, lunr
description: ${project.name} includes a dedicated mechanism for keywords, specified either globally or on each page.

# Keywords

Keywords are listed in the `<head>` section of the HTML pages with `<meta name="keywords">`. While they are no longer used by online search engines like Google or Bing, they are used in the [internal index and search engine](search.html).

```html
<meta name="keywords" content="HTML, CSS, JavaScript" />
```

You can specify keywords in 2 places:

1. In `src/site/site.xml`, with the `<keywords>` property under `<custom>`, for keywords that will be listed **in every pages** of the documentation, as in the example below.

    ```xml
    <project name="My Documentation">
      ...
      <custom>
        ...
        <keywords>general,topic</keywords>
      ...
      </custom>
      ...
    </project>
    ```

2. In the [header of a document](headers.html), to add keywords for a specific page. In Markdown, this is done in the very first lines of the document, as in the example below.

    ```md
    keywords: specific,special

    # Document Title

    ...
    ```

The keywords specified in a specific page are merged with the keywords specified in `src/site/site.xml`. In the above example, the HTML page will include this header:

```html
<meta name="keywords" content="general,topic,specific,special" />
```

As the keywords are indexed in the documentation's search index, you can specify keywords that are likely to be searched in your documentation, that should link to a page which doesn't actually contain that exact word.

> **Example**
>
> If users are likely to search for the word `JPG` while your page only mentions `JPEG` (so the page doesn't show up in the results when searching for `JPG`), you can specify `keywords: jpg` in the corresponding Markdown document to fix that.
