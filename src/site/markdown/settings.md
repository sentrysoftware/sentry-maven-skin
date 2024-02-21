keywords: option, configuration, config
description: ${project.name} includes a few specific settings to modify its behavior.

# General Settings in `site.xml`

Like any documentation using Maven Site, the general settings are stored in the [*site descriptor*](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html) (See also [reference for `src/site/site.xml`](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-model/site.html)).

The **${project.name}** specific configuration parameters are specified under the `<custom>` tag:

```xml
<project name="\${project.name}">

...

  <custom>
    <keywords>keyword1, keyword2, ...</keywords>
    <bodyClass>sentry-orange</bodyClass>
    <additionalLinks>
      <link>
        <name>My Link Text</name>
        <href>https://w3c.org</href>
        <target>_blank</href>
      </link>
    </additionalLinks>
  </custom>
...
```

## Sentry Maven Skin specific properties

| Property | Description | Default |
|---|---|---|
| `<project name="...">` | Title of the documentation <br/>Recommended value: `<project name="\$project.name">` | |
| `<bodyClass>` | CSS class to be added to the `<body>` element of each page (which will control the color of the title).<br/>Predefined values are: `sentry-purple` (purple), `sentry-green` (green) or `sentry-orange` (orange) | *None* (blue) |
| `<publishDate>` | Publish date of the documentation<br/>It is recommended to use a static value, or leave empty so the skin will use the build date.<br/>Note: Format should be ISO (e.g. `2024-02-18T19:30:00Z`), so the value can be interpreted properly. | `${esc.d}project.build.outputTimestamp` or current date |
| `<keywords>` | Comma-separated list of keywords that will be added to all pages in this documentation (and merged with the keywords set in each individual page) | |
| `<projectVersion>` | Version of the documentation (or of the product being documented). Useful to override the version defined in the Maven project. | `\$project.version` |
| `<additionalLinks>` | Specify one or multiple `<link>` items that will be displayed in the footer of the generated pages. Useful for legal references, like terms of use, disclaimer, privacy policy, etc.<br/>Specify `<name>` for the text to display, `<href>` for the link, and `<target>` to open in a new window or tab. | *None* |

Values in `src/site/site.xml` can refer to properties defined in `pom.xml`.
