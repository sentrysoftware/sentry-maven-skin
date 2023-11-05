# Navigation Links

At the very top of each page, above the title banner, you can specify general links in `src/site/site.xml` as in the example below:

![inline](images/nav-links.png)

In mobile view, these links are displayed at the very bottom of the page, to make the page easier to read:

![inline](images/nav-links-mobile.png)

In `src/site/site.xml`, use the `<links>` tag under `<body>` to specify such links. Specify each link as an [`<item>` element](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-model/site.html#item):

```xml
<project name="My Documentation">

  ...

  <body>
    ...
    <links>
      <item name="Fork on GitHub" href="${project.scm.url}" />
      <item name="Maven Site Plugin" href="https://maven.apache.org/plugins/maven-site-plugin" />
      <item name="Maven Site Descriptor" href="https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html" />
      <item name="Other Maven Skins" href="https://maven.apache.org/skins" />
    </links>
```
