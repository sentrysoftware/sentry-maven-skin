keywords: option, configuration, config
description: ${project.name} includes a few specific settings to modify its behavior.

# General Settings in `site.xml`

Like any documentation using Maven Site, the general settings are stored in the [*site descriptor*](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html) (See also [reference for `src/site/site.xml`](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-model/site.html)).

However, the *Sentry Maven Skin* **does not honor** some of the [documented properties](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html) of `src/site/site.xml`:

* `<publishDate>`
* `<poweredBy>`
* `<breadcrumbs>`

The *Sentry Maven Skin* specific configuration parameters are specified under the `<custom>` tag:

```xml
<project name="\${project.name}">

...

  <custom>
    <publishDate>\$timestamp</publishDate>
    <keywords>keyword1, keyword2, ...</keywords>
    <bodyClass>sentry-orange</bodyClass>
  </custom>
...
```

## Sentry Maven Skin specific properties

| Property | Description | Default |
|---|---|---|
| `<project name="...">` | Title of the documentation <br/>Recommended value: `<project name="\$project.name">` | |
| `<bodyClass>` | CSS class to be added to the `<body>` element of each page (which will control the color of the title).<br/>Predefined values are: `sentry-purple` (purple), `sentry-green` (green) or `sentry-orange` (orange) | None (blue) |
| `<publishDate>` | Publish date of the documentation<br/>Recommended value: `\$project.build.outputTimestamp` (i.e. build time)<br/>Note: The date and time format is free | Current date |
| `<keywords>` | Comma-separated list of keywords that will be added to all pages in this documentation (and merged with the keywords set in each individual page) | |
| `<projectVersion>` | Version of the documentation (or of the product being documented). Useful to override the version defined in the Maven project. | `\$project.version` |
| `<showRenderingTime>` | Whether to insert in each generated page the time it took to render the page with the skin. For a reproducible build, leave this to `false`. | `false` |

Values in `src/site/site.xml` can refer to properties defined in `pom.xml`.
