# Getting Started

The *Sentry Maven Skin* is an Apache Maven site skin to generate technical user documentation on Java (Maven) projects, using modern-era development tools (Maven, Markdown), and producing modern-era Web-based documentation:

* HTML5
* Bootstrap CSS
* Search index with AngularJS
* Image optimization
* Excellent Lighthouse score
* and more!

## Setup of your `site` project

The *Sentry Maven Skin* leverages a Java library that needs to be declared as a dependency when invoking the `maven-site-plugin`. Update you Maven `pom.xml` as below:

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

Then, specify the *Sentry Maven Skin* artifact in your `./src/site/site.xml` file:

```xml
<project name="${project.name}">

  <skin>
    <groupId>org.sentrysoftware.maven</groupId>
    <artifactId>sentry-maven-skin</artifactId>
    <version>6.0.00</version>
  </skin>

  <custom>
    <bodyClass>sentry-studio</bodyClass>
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
mvn site
```

The site is built in `./target/site/*`.
