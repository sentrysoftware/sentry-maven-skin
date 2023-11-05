# Navigation Menu

The navigation menu on the left is specified with [the `<menu>` elements in `src/site/site.xml`](https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html#including-generated-content).

It is possible to specify a 2nd level of hierarchy in the pages of the documentation, as `<item>` elements can contain other `<item>` elements, as in the example below:

```xml
...
    <menu name="Using MetricsHub">
      <item name="General Concepts" href="general-concepts.html"/>
      <item name="Resources and Connectors" href="resource-connectors.html"/>
      <item name="Configuring MetricsHub" href="m8b-settings.html"/>
      <item name="Basic Monitors" href="basic-monitors/index.html">
        <item name="Filesystem" href="basic-monitors/filesystem.html" />
        <item name="Process" href="basic-monitors/process.html" />
        ...
      </item>
      ...
    </menu>
...
```

This will generate the below navigation menu:

![inline](images/menu-collapse.png)

The menu is expanded when the corresponding topic is opened:

![inline](images/menu-expand.png)

On mobile, links to all subtopics are automatically added to the parent page:

![inline](images/menu-expand-mobile.png)

> **Note**
>
> The Sentry Maven Skin does not support a 3rd level of hierarchy.

