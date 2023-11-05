# Getting Started

The *Sentry Maven Skin* is an [Apache Maven site](https://maven.apache.org/plugins/maven-site-plugin) skin to generate technical user documentation on Java (Maven) projects.

![Sentry Maven Skin used for its own documentation](images/general-screenshot.png)

It leverages a (reasonably) modern Web stack:

* HTML5
* Bootstrap CSS
* Mobile-first
* Search index with AngularJS
* Image optimization
* Excellent Lighthouse score
* and more!

![inline](images/mobile-screenshot.png)

## Prerequisites

The following is absolutely required to use the *Sentry Maven Skin*:

* A properly setup [Maven project](https://maven.apache.org/plugins/maven-site-plugin/examples/creating-content.html)
* Version 3.x of the [Maven Site plugin](https://maven.apache.org/plugins/maven-site-plugin)

## Setup of your `site` project

The *Sentry Maven Skin* leverages Sentry's [Skin Tools Java library](https://sentrysoftware.github.io/maven-skin-tools/), that needs to be declared as a dependency when invoking the `maven-site-plugin`. Update you Maven `pom.xml` as below:

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
          <version>1.1.00</version> <!-- Check for the latest version -->
        </dependency>
      </dependencies>
    </plugin>
    ...
  </plugins>
</build>
```

Then, specify the *Sentry Maven Skin* artifact in your `src/site/site.xml` file:

```xml
<project name="${project.name}">

  <skin>
    <groupId>org.sentrysoftware.maven</groupId>
    <artifactId>sentry-maven-skin</artifactId>
    <version>6.0.00</version>
  </skin>

  <custom>
    <bodyClass>sentry-purple</bodyClass>
    <keywords>keyword1,keyword2</keywords>
    <publishDate>$project.build.outputTimestamp</publishDate>
  </custom>

  <body>

    <menu name="First Menu Group">
      <item name="Overview" href="index.html"/>
      <item name="Other Page" href="other-page.html"/>
    </menu>

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

You're then good to go to build your documentation site:

```sh
mvn clean site
```

The site is built in `target/site/*`.
