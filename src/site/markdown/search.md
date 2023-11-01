# Index and Search

The content of each page is automatically indexed with [elasticlunr.js](https://elasticlunr.com/). *Elasticlunr.js* is a Javascript front-end only indexing and searching solution.

The *Sentry Maven Skin* will build an *Elasticlunr.js* index and each page will be able to search the entire project documentation that has been generated.

The index file is `./target/site/index.json` and can be leveraged and merged with other documentation indexes, but this will require extra work (in Javascript only).

The **index.json** file is lazily loaded when the user starts typing in the *Search* box. Its size grows with the size of the documentation.

