# Keywords

You can specify keywords associated to each document in 2 places:

* `./site/site.xml`, in the `<keywords>` property under `<custom>`, to add keywords that are
applicable to all pages in the documentation, as in the example below.

```xml
<project name="My Documentation">
  ...
  <custom>
    ...
    <keywords>generalkeyword1, generalkeyword2, ...</keywords>
    ...
  </custom>
  ...
</project>
```

* In the header of each document, to add keywords for a specific page.
In Markdown, this is done in the very first lines of the document, as in the example below.

```md
keywords: specifickeyword1, specifickeyword2, ...

# Document Title
...
```

The keywords specified in a specific page are merged with the keywords specified in `./site/site.xml`. They are listed in the `<meta name="keywords">` header value. They are also used for the indexing of the pages and the local *elasticlunr* engine. They may be useful to implement a "Related Topics" feature.
