# General Settings in `site.xml`

The general settings of the documentation are configured in `./src/site/site.xml`, under the `<custom>` tag:

```xml
<project name="${project.name}">

...

  <custom>
    <publishDate>$timestamp</publishDate>
    <keywords>keyword1, keyword2, ...</keywords>
    <bodyClass>sentry-orange</bodyClass>
  </custom>
...
```

Configurable properties:

| Property | Description | Default |
|---|---|---|
| `<project name="...">` | Title of the documentation <br/>Recommended value: `<project name="$project.name">` | |
| `<bodyClass>` | CSS class to be added to the `<body>` element of each page (which will control the color of the title).<br/>Predefined values are: `sentry-purple` (purple), `sentry-green` (green) or `sentry-orange` (orange) | None (blue) |
| `<publishDate>` | Publish date of the documentation<br/>Recommended value: `$timestamp` (i.e. build time)<br/>Note: The date and time format is free | Current date |
| `<keywords>` | Comma-separated list of keywords that will be added to all pages in this documentation (and merged with the keywords set in each individual page) | |
| `<projectVersion>` | Version of the documentation (or of the product being documented). Useful to override the version defined in the Maven project. | `$project.version` |

Values in `./site/site.xml` can refer to properties defined in `./pom.xml`.
