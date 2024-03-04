keywords: doxia
description: Quickstart guide to setup your documentation project with Apache Maven, Doxia, and ${project.name}.

# Getting Started

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Prerequisites

The following is absolutely required to use the **${project.name}**:

* A properly setup [Maven project](https://maven.apache.org/plugins/maven-site-plugin/examples/creating-content.html)
* Version 3.x of the [Maven Site plugin](https://maven.apache.org/plugins/maven-site-plugin)

## Setup of your `site` project

### Directory layout

Below is the typical directory layout of a documentation site project with Maven:

```
./  pom.xml
│
└───src/
    └───site/
        │   site.xml
        │
        ├─── markdown/
        │       index.md
        │
        └─── resources/
            ├─── css/
            │       site.css
            │
            └─── images/
```

### `pom.xml`

The **${project.name}** leverages Sentry's [Skin Tools Java library](https://sentrysoftware.github.io/maven-skin-tools/), that needs to be declared as a dependency when invoking the `maven-site-plugin`. Update you Maven `pom.xml` as below:

```xml
<build>
  <plugins>
    ...
    <plugin>
      <artifactId>maven-site-plugin</artifactId>
      <version>3.12.1</version>
      ...
      <dependencies>
        <dependency>
          <groupId>org.sentrysoftware.maven</groupId>
          <artifactId>maven-skin-tools</artifactId>
          <version>1.3.00</version> <!-- Check for the latest version -->
        </dependency>
      </dependencies>
    </plugin>
    ...
  </plugins>
</build>
```

### `site.xml`

Then, specify the **${project.name}** artifact in your `src/site/site.xml` file:

```xml
<project name="\${project.name}">

  <!-- Use ${project.name} -->
  <skin>
    <groupId>${project.groupId}</groupId>
    <artifactId>${project.artifactId}</artifactId>
    <version>${project.version}</version>
  </skin>

  <!-- ${project.name} specific settings -->
  <custom>
    <bodyClass>sentry-purple</bodyClass> <!-- banner color -->
    <keywords>keyword1,keyword2</keywords> <!-- keywords in all pages -->
      <additionalLinks> <!-- additional links in the footer -->
        <link>
          <name>Code of Ethics</name>
          <href>ethics.html</href>
        </link>
    </additionalLinks>
    <social>
      <linkedin>company/microsoft</linkedin>
    </social>
  </custom>

  <!-- Top link -->
  <bannerLeft>
    <name>\${project.organization.name}</name>
    <href>\${project.organization.url}</href>
    <!-- Use <src>images/logo-100x40.png</src> to display a logo, instead of <name> -->
  </bannerLeft>

  <body>

    <!-- Links in the top navigation bar -->
    <links>
      <item name="Link 1" href="https://some.url"/>
      <item name="Another Resource" href="https://other.site"/>
    </links>

    <!-- Documentation content on the left -->
    <menu name="First Menu Group">
      <item name="Overview" href="index.html"/>
      <item name="Other Page" href="other-page.html"/>
    </menu>

    <!-- 3 levels: menus (groups), items (pages), and sub-items (pages) -->
    <menu name="Second Menu Group">
      <item name="Write a Good Documentation" href="writing.html"/>
      <item name="Using Subdirectories" href="subdir/using.html"/>
      <item name="Big Chapter" href="big-chapter/index.html">
        <item name="Sub Topic 1" href="big-chapter/subtopic1.html" />
        <item name="Sub Topic 2" href="big-chapter/subtopic2.html" />
      </item>
    </menu>

  </body>

</project>
```

### Content

Write the content of your documentation in the `./src/site/markdown/` directory if you choose to use Markdown. Other markup languages are supported, like APT, Confluence or XHTML. The Maven documentation explains [how to create content for a documentation site](https://maven.apache.org/plugins/maven-site-plugin/examples/creating-content.html).

### Styling

You can [customize the look and feel](styles.md) of your documentation by writing a CSS stylesheet in `./src/site/resources/css/site.css` as in the example below:

```css
/* Colors for <bodyClass>my-theme</bodyClass> */
body.my-theme {
  --banner-bgcolor: #266fd0;
  --link-color: #d50c37;
}

/* Lighter red for dark mode (body.dark) */
body.my-theme.dark {
  --link-color: #ff6989;
}
```

Use the [`<bodyClass>` setting in `site.xml`](settings.html) to specify a class to the body element, so that you can reference it in `site.css`. In the above example, you would specify `<bodyClass>my-theme</bodyClass>`.

### Generating the documentation site

You're then good to go to build your documentation site:

```bash
mvn clean site
```

The site is built in `target/site/*`.

### Live rendering

Additionally, you can build the site *live* and make it available on a local Web server with the below command:

```bash
mvn site:run
```

This command spawns a Web server with the content of your documentation site. Each page is rendered *live* when you request it in your browser, so changes in your Markdown are taken into account immediately. This is particularly useful when you write your documentation and needs to see how it looks very quickly.

> **Note**
>
> Only the changes in your documentation sources (e.g. the Markdown `*.md` files) are taken into account. Changes in `site.xml` or `pom.xml` require to restart `mvn site:run`.
