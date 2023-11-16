keywords: elastic, lunr
description: ${project.name} includes complete search capabilities within the documentation.

# Index and Search

The *Sentry Maven Skin* includes complete search capabilities within the documentation and doesn't rely on external search engines like Google. Readers can search the documentation very easily.

![User documentation is easily searchable with clear result listing, including category, matching score and highlighted matching excerpts](images/search.png)

Search also works on mobile:

![inline](images/search-mobile.png)

## How does it work?

The content of each page is automatically indexed with [elasticlunr.js](https://elasticlunr.com/). *Elasticlunr.js* is a Javascript front-end only indexing and searching solution.

The *Sentry Maven Skin* will build an *Elasticlunr.js* index and each page will be able to search the entire project documentation. The *Elasticlunr.js* indexer is executed by the *Sentry Maven Skin* using [GraalVM's JavaScript engine](https://www.graalvm.org/latest/reference-manual/js/). Then the *Elasticlunr.js* client is loaded as part of the AngularJS app running on each page.

The index file is `target/site/index.json` and can be leveraged and merged with other documentation indexes, but this will require extra work (in Javascript only).

The **index.json** file is lazily loaded when the user starts typing in the *Search* box. Its size grows with the size of the documentation.
