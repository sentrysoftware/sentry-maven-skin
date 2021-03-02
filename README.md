# Sentry Maven Skin

The *Sentry Maven Skin* is an Apache Maven site skin to be used to generate Sentry's technical user documentation, using modern-era development tools (git, maven, markdown), and producing modern-era Web-based documentation (HTML5, Bootstrap, etc.)

## Setup of your `site` project

First, the *Sentry Maven Skin* requires a specific Java library for its more advanced features (like the automatic generation of the search index). You therefore need to update your `./pom.xml` file as below:

```xml
<build>
  <plugins>
    ...
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-site-plugin</artifactId>
      <version>3.9.1</version>
      ...
      <dependencies>
        <dependency>
          <groupId>com.sentrysoftware.doc</groupId>
          <artifactId>sentry-skin-velocity-tools</artifactId>
          <version>4.0</version>
        </dependency>
      </dependencies>
    </plugin>
    ...
  </plugins>
</build>
```

Then, specify the Sentry Maven Skin artifact in your `./src/site/site.xml` file:

```xml
<project name="${project.name}">

  <skin>
    <groupId>com.sentrysoftware.doc</groupId>
    <artifactId>sentry-maven-skin</artifactId>
    <version>4.0</version>
  </skin>

  <custom>
    <bodyClass>sentry-studio</bodyClass>
    <keywords>keyword1,keyword2</keywords>
    <publishDate>$timestamp</publishDate>
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

> Note: Use the latest officially available version of the skin as published on Sentry's repository (it's **4.0** in the examples above).

## Features

### General Settings in **site.xml**

The general settings of the documentation are configured in `./site/site.xml`:

```xml
<project name="${project.name}">

...

  <custom>
    <publishDate><$timestamp></publishDate>
    <keywords>keyword1, keyword2, ...</keywords>
    <bodyClass>sentry-storage</bodyClass>
  </custom>
...
```

Configurable properties:

| Property | Description | Default |
|---|---|---|
| `<project name="...">` | Title of the documentation <br>Recommended value: `<project name="$project.name">` | |
| `<bodyClass>` | CSS class to be added to the `<body>` element of each page (which will control the color of the title).<br>Predefined values are: `sentry-studio` (purple), `sentry-hardware` (green) or `sentry-storage` (orange) | None (blue) |
| `<publishDate>` | Publish date of the documentation<br>Recommended value: `$timestamp` (i.e. build time)<br>Note: The date and time format is free | Current date |
| `<keywords>` | Comma-separated list of keywords that will be added to all pages in this documentation (and merged with the keywords set in each individual page) | |
| `<projectVersion>` | Version of the documentation (or of the product being documented). Useful to override the version defined in the Maven project. | `$project.version` |
| `<noDefaultLinks>` | When set to `true`, prevents the display of the default links to Sentry's Web site | *Not set* (false) |

Values in `./site/site.xml` can refer to properties defined in `./pom.xml`.

### Sub-topics

Starting with version 4.0 of the Sentry Maven Skin, it is possible to specify a 2nd level of hierarchy in the pages of the documentation:

```raw
+ Menu
|
+--- Item (topic)
|
+--+ Item (topic)
   |
   +--- Item (sub-topic)
   |
   +--- Item (sub-topic)
```

Above *menus* define sections of the documentation (Installation Guide, User Guide, Reference Guide, for example). *Items* are actual pages and can "contain" other items (pages), as in the **site.xml** example below:

```xml
...
    <menu name="Using Monitoring Studio">
      <item name="General Concepts" href="general-concepts.html"/>
      <item name="Hosts and Templates" href="hosts-templates.html"/>
      <item name="Configuring Monitoring Studio" href="studio-settings.html"/>
      <item name="Basic Monitors" href="basic-monitors/index.html">
        <item name="Filesystem" href="basic-monitors/filesystem.html" />
        <item name="Process" href="basic-monitors/process.html" />
        <item name="SNMP Trap" href="basic-monitors/snmp-trap.html" />
        <item name="Windows Event" href="basic-monitors/windows-event.html" />
        <item name="Windows Performance Counter" href="basic-monitors/windows-perf.html" />
        <item name="Windows Service" href="basic-monitors/windows-service.html" />
      </item>
      ...
    </menu>
...
```

In the above example, the *Basic Monitors* page is a chapter of the documentation that groups several other pages. The Sentry Maven Skin will automatically add links to all subtopics in the parent page.

Note: The Sentry Maven Skin does not support a 3rd level of hierarchy.

### Generate a ToC automatically

The *Sentry Maven Skin* now leverages Maven Doxia standard **TOC** macro, which needs to be inserted in Markdown documents as below:

```html
<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->
```

More information about [Maven Doxia macros](https://maven.apache.org/doxia/macros/index.html).

**IMPORTANT**: For the TOC macro to work in the skin, it must specify `id=toc` as in the example above.

### Parsing of the `${property}` properties

All properties defined in `./pom.xml` can be referenced in the Markdown (and other) source documents and will be replaced with their corresponding values in the resulting HTML, using the `${propertyName}` syntax.

Similarly, all properties defined in `./site/site.xml` under the `<custom>` tag can also be referenced with the `$decoration.getCustomValue("propertyName")` syntax.

Example of a `pom.xml`:

```xml
<project>
  ...
  <properties>
    <productShortname>Xtrem IO KM</productShortname>
    <model>EMC XtremIO</model>
    ...
  </properties>
```

In the source documents (Markdown or other), you can use `$productShortname` and `$model` that will be replaced with *Xtrem IO KM* and *EMC XtremIO* respectively:

```md
**$productShortname** allows administrators to setup the monitoring of $model storage systems...
```

Same example with `./site/site.xml`:

```xml
<project name="My Documentation">

  ...

  <custom>
    <productShortname>Xtrem IO KM</productShortname>
    <model>EMC XtremIO</model>
    ...
  </custom>

```

In the source documents (Markdown or other), you can use `$decoration.getCustomValue("productShortName")` and `$decoration.getCustomValue("model")`, which will be replaced with with *Xtrem IO KM* and *EMC XtremIO* respectively:

```md
**$decoration.getCustomValue("productShortName")** allows administrators to setup the monitoring of $decoration.getCustomValue("model") storage systems...
```

Note: The syntax to reference values in `./pom.xml` looks nicer and easier than the one to reference values in `./site/site.xml`. However, it's the latter method that is recommended so that documentation information remains in `./site/site.xml` rather than spread across several configuration files.

### Automatic Title

The page title is automatically built from the project name and the first heading found in the document. However, you can also specify the page title in the Markdown source with the `title` header:

```md
title: The Real Title

# First Heading

Lorem ipsum...
```

### Links in the top navigation bar

At the very top of each page, above the title banner, several "general purpose" links are listed. You can specify additional links in **site.xml** as in the example below:

```xml
<project name="My Documentation">

  ...

  <links>
    <item name="Product Page" href="//www.sentrysoftware.com/products/km-monitoring-studio-x.html" />
    <item name="YouTube" href="https://youtu.be/Th6NweyurWs" />
  </links>
```

You can also prevent the skin from adding the usual default links to Sentry's Web site with the `noDefaultLinks` option in the `<custom>` section as in the example below:

```xml
  ...

  <links>
    ...
  </links>

  <custom>
    ...
    <noDefaultLinks>true</noDefaultLinks>
  </custom>

```

### Auto-zoom images

By default, all images in the source documents are displayed as a thumbnail, which the reader needs to click on
to see in real size.

If you want an image to be displayed *as is* (like ones that you would use for a bullet list, etc.),
you will need to set its `alt` attribute to `inline`.

Examples:

```md
![My Description](./images/my-picture.png) This will show as a zoomable image

![inline](./images/icon.png) This will show "inline", with no zoom
```

### Automatic conversion of all images to WEBP

All images are automatically converted to the WEBP format. The original format is kept and the HTML page is updated so that the browser falls back to the original format if WEBP is not supported.

* PNG and GIF are converted to *lossless* WEBP
* other formats are converted to *lossy* WEBP

### Keywords

You can specify keywords associated to each document in 2 places:

* `./site/site.xml`, in the `<keywords>` property under `<custom>`, to add keywords that are
applicable to all pages in the documentation, as in the example below.

```xml
<project name="My Documentation">
  ...
  <custom>
    ...
    <keywords>generalkeyword1, generalkeyword2, ...</keywords>
    ...
  </custom>
  ...
</project>
```

* Each the header of each document, to add keywords for a specific page.
In Markdown, this is done in the very first lines of the document, as in the example below.

```md
keywords: specifickeyword1, specifickeyword2, ...

# Document Title
...
```

The keywords specified in a specific page are merged with the keywords specified in `./site/site.xml`. They are listed in the `<meta name="keywords">` header value. They are also used for the indexing of
the pages and the local *elasticlunr* engine. They may be useful to implement a "Related Topics" feature.

### Index and Search

The content of each page is automatically indexed with [elasticlunr.js](https://elasticlunr.com/). *Elasticlunr.js* is a Javascript front-end only indexing and searching solution.

The *Sentry Maven Skin* will build an *Elasticlunr.js* index and each page will be able to search the entire project documentation that has been generated.

The index file is `./target/site/index.json` and can be leveraged and merged with other documentation indexes, but this will require extra work (in Javascript only).

The **index.json** file is lazily loaded when the user starts typing in the *Search* box. Its size grows with the size of the documentation.

### Additional Document Headers

Headers can be added to each Markdown document, notably to specify the document's title, author and description. Actually, any custom header can be added using the below syntax in the Markdown source:

```md
title: Casino Royale
author: Ian Fleming
description: The story concerns the British secret agent James Bond, gambling at the casino in Royale-les-Eaux to bankrupt Le Chiffre, the treasurer of a French communist union and a secret member of Soviet state intelligence. Bond is supported in his endeavours by Vesper Lynd, a member of his own service, as well as Felix Leiter of the CIA and René Mathis of the French Deuxième Bureau.
date: 13 April 1953

# Casino Royale (James Bond)

'A dry martini,' Bond said 'in a deep Champagne goblet. Three measures of Gordon's, one of Vodka, half a measure of Kina Lillet. Shake it very well until it's ice cold, then add a thin slice of lemon peel. Got it?'

'Certainly, monsieur.'
```

Typical headers:

| Header | Description |
|---|---|
| `title` | To specify a document title different than its first heading. |
| `author` | Specifies the author of the document. Multiple `author` entries are possible in the same document. |
| `date` | Date of writing of the document (creation or update).<br>No date format is enforced; it is recommended to be consistent across the documentation. |
| `description` | Typically used by Search Engines as short description of the page. This is critical to SEO. |
| `keywords` | List of keywords applicable to the page. Not used by public Search Engines, but will be used for internal *Related Topics* listing. |

### Code Syntax Highlighting

Fenced code blocks will be syntax-highlighted using [PrismJS](https://prismjs.com/). The language of the code block must be specified as in the below example:

````md
```java
System.out.println("Hello, World!");
```
````

Supported languages (or syntax highlighting types) are:

| Language | Markdown markup |
|---|---|
| Command line | <pre><code class="language-md">```batch</code></pre> |
| Commands with output | <pre><code class="language-md">```shell-session<br>$ ls -l test<br>test: Not found<br></code></pre> |
| CSS | <pre><code class="language-md">```css</code></pre> |
| Dockerfile | <pre><code class="language-md">```docker</code></pre> |
| HTML | <pre><code class="language-md">```html</code></pre> |
| JavaScript | <pre><code class="language-md">```js</code></pre> |
| JSON | <pre><code class="language-md">```json</code></pre> |
| Markdown | <pre><code class="language-md">```md</code></pre> |
| PowerShell | <pre><code class="language-md">```ps</code></pre> |
| PSL | <pre><code class="language-md">```psl</code></pre> |
| Regular expressions | <pre><code class="language-md">```regex</code></pre> |
| Shell script (Linux) | <pre><code class="language-md">```bash</code></pre> |
| Shell script (Windows) | <pre><code class="language-md">```batch</code></pre> |
| SQL | <pre><code class="language-md">```sql</code></pre> |
| XML | <pre><code class="language-md">```xml</code></pre> |
| YAML | <pre><code class="language-md">```yaml</code></pre> |

> Note: Syntax highlighting for fenced code blocks is available only when using version 3.10 (and later) of *maven-site-plugin*. At the time of writing, the latest version is **3.9.1**.

### Miscellaneous

* Automatic creation of &lt;a&gt; anchor tags for all headings (HTML5-style, with `id` instead of `name`)
* Bootstrap-styled tables

## Bug tracker

[Project in JIRA](http://alpha.internal.sentrysoftware.net/tracker/issues/?jql=project%20%3D%20ALPHA%20AND%20component%20%3D%20%22Maven%20Skin%20Plugin%22)

## Contributing

### Source code

Fork the [git repository](http://alpha.internal.sentrysoftware.net/source/projects/DU/repos/sentry-maven-skin/browse), branch from **develop**, commit and push your changes, and submit pull requests.

Repository policy: **DO NOT SQUASH OR REBASE COMMITS THAT HAVE BEEN PUSHED**

### Structure and technologies

Beware that this project is a baroc mix of languages, frameworks and libraries:

* Java for some *backend* HTML processing
* Javascript-in-Java with [GraalVM](https://www.graalvm.org/reference-manual/js/) for building the index
* [Velocity](https://velocity.apache.org/engine/1.7/user-guide.html) for templating
* [AngularJS](https://angularjs.org/) for front-end logic
* Various HTML, CSS and JS frameworks and libraries (Bootstrap, etc.)
* [npm](https:www.npmjs.com/) and [Gulp.js](https://gulpjs.com/) to build the front-end
* [Groovy](https://groovy-lang.org/) for validating the integration tests

The *Sentry Maven Skin* project is made of 2 modules:

* *sentry-skin-velocity-tools*: a Java module required by the *Sentry Maven Skin* at runtime
* *sentry-maven-skin*: the actual Maven skin, which itself is comprised of:
  * `./src/webapp/**`: the front-end web app, including CSS, JS, HTML templates, etc.
  * `./src/webapp/site.vm`: the *Velocity* template that will generate each HTML page, this is the one referencing the JS and CSS
  * `./src/package.json`: for NPM
  * `./src/gulpfile.js`: to build and minify the web app
  * `./src/it/studio-km`: an integration test using a slightly customized version of Monitoring Studio's documentation

### Building

The build is done with Maven with the below command:

```bash
mvn verify
```

Build steps:

* First, the *sentry-skin-velocity-tools* module is built as a regular Java project
  * JUnit 5 (Jupiter) is used for unit tests
* Then, the *sentry-maven-skin* module is built
  * NodeJS is installed in `./node` (and is ignored by Git)
  * `npm install` is run to get all dependencies listed in `package.json`, which are installed in the `./node_modules` (also ignored by Git)
  * `gulp` is run with `./gulpfile.js` to build the front-end web app (lint, minification, template embedding, etc.), and the result is stored in `./target/dist`
  * The Maven skin JAR is assembled in `./target`
  * A temporary local Maven repository is set up with both JAR artifacts (the skin and the Velocity tools)
  * A test project is set up in `./target/it` and `mvn site` is run on this test project
  * The result validated with a Groovy script

### Testing

While modifying the *Sentry Maven Skin*, you will want to see how your changes are reflected in a *test* documentation project.

Conveniently, the project comes with integration tests, i.e. a documentation project that is automatically
built with the skin as it is in the workspace. The integration test is run with the below command:

```bash
mvn verify
```

This command builds the skin and run it against a documentation project. The result can be seen in `./sentry-maven-skin/target/it/studio-km/site/*.html`.

We recommend running [http-server](https://github.com/http-party/http-server#readme) to browse the result. Install with:

```bash
npm install --global http-server
```

Launch a Web server with the generated test documentation with:

```bash
http-server sentry-maven-skin/target/it/studio-km/target/site
```

In case of a build failure, the output of the build is stored in `./sentry-maven-skin/target/it/studio-km/build.log`.

### Finalizing a version

Once the results are satisfactory, you will want to "officially" release the new version of the *Sentry Maven Skin*.
To do so, follow Gitflow procedure.

Note: To update the version number to a non-`SNAPSHOT` version, run the below command from the
root directory of the *Sentry Maven Skin* project:

```bash
mvn versions:set -DremoveSnapshot=true
```

Once validated by the QA team, deploy to the alpha Maven repository from the same root directory with the command below:

```bash
mvn clean deploy
```

This will deploy the official (non-`SNAPSHOT`) version of the *Sentry Maven Plugin* to the *Maven* repository
so that other projects (documentation projects) can consume it.

Then set the version to a new SNAPSHOT version:

```bash
mvn versions:set -DnewVersion=3.1-SNAPSHOT
```
