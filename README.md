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
		<projectVersion>${project.version}</projectVersion>
		<bodyClass>sentry-studio</bodyClass>
		<keywords>keyword1,keyword2</keywords>
		<publishDate><!--Unspecified--></publishDate>
	</custom>

	<body>

		<menu name="First Menu Group">
			<item name="Overview" href="index.html"/>
			<item name="Other Page" href="other-page.html"/>
		</menu>

		<menu name="Second Menu Group">
			<item name="Write a Good Documentation" href="writing.html"/>
			<item name="Using Subdirectories" href="subdir/using.html"/>
		</menu>

	</body>

</project>
```

> Note: Use the latest officially available version of the skin as published on Sentry's repository (it's **4.0** in the examples above).


## Features

### Requirements in **site.xml**

For the skin to work properly, you will need to set a few values in `./site/site.xml`:

* `<project name="Documentation Title">`: The title of the documentation
* Properties under the `<custom>` tag:
  * `<projectVersion>1.2.34-SNAPSHOT</projectVersion>`: The version of the documentation (or of the product being documented)
  * `<publishDate>July 4th, 1776</publishDate>`: The publish date of the documentation (will use current date and time if not set)
  * `<keywords>some, keywords, describing, document</keywords>`: Common keywords that will be added to all pages in this documentation (and merged with the keywords set in each individual page)
  * `<bodyClass>sentry-studio</bodyClass>`: The CSS class to be added to the `<body>` element of each page (will control the color of the title). It can be `sentry-studio` (purple), `sentry-hardware` (green) or `sentry-storage` (orange), or no value (blue).

As values in `./site/site.xml` can refer to `./pom.xml`, your `./site/site.xml` will probably look like below:

```xml
<project name="${project.name}">

...

	<custom>
		<projectVersion>${project.version}</projectVersion>
		<publishDate><$timestamp></publishDate>
		<keywords><!-- keyword1, keyword2 --></keywords>
		<bodyClass><!-- sentry-studio, sentry-hardware or sentry-storage --></bodyClass>
	</custom>
...
```

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

In the source documents (Markdown or other), you can use `$productShortname` and `$model` that will be replaced with *Xtrem IO KM* and *EMC XtremIO* respectively.

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

In the source documents (Markdown or other), you can use `$decoration.getCustomValue("productShortName")` and `$decoration.getCustomValue("model")`, which will be replaced with with *Xtrem IO KM* and *EMC XtremIO* respectively.

The syntax to reference values in `./pom.xml` looks nicer and easier than the one to reference values in `./site/site.xml`. However, it's the latter method that is recommended so that documentation information remains in `./site/site.xml` rather than spread across several configuration files.

### Automatic Title

The page title is automatically built from the project name and the first heading found in the document.

### Links in the top navigation bar

At the very top of each page, above the title banner, several "general purpose" links are listed. You can specify additional links in **site.xml** as in the example below:

```xml
<project name="My Documentation">

	...

	<links>
		<item name="Product Page" href="https://www.sentrysoftware.com/products/km-monitoring-studio-x.html" />
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
to see in real size. The image is zoomed in place, there is no "lightbox", for a better experience on small
devices.

If you want an image to be displayed *as is* (like ones that you would use for a bullet list, etc.),
you will need to set its `alt` attribute to `inline`.

Examples:

```md
![My Description](./images/my-picture.png) This will show as a zoomable image

![inline](./images/icon.png) This will show "inline", with no zoom
```

### Automatic conversion of all images to WEBP

All images are automatically converted to the WEBP format. The original format is kept and the HTML page is updated
so that the browser falls back to the original format if WEBP is not supported.

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

They keywords specified in a specific page are merged with the keywords specified in `./site/site.xml`.
They are listed in the `<meta name="keywords">` header value. They are also used for the indexing of
the pages and the local *elasticlunr* engine. They may be useful to implement a "Related Topics" feature.

### Index and Search

The content of each page is automatically indexed with [elasticlunr.js](//elasticlunr.com/). *Elasticlunr.js*
is a Javascript front-end only indexing and searching solution.

The *Sentry Maven Skin* will build an *Elasticlunr.js* index and each page will be able to search the
entire project documentation that has been generated.

THe index file is `./target/site/index.json` and can be leveraged and merged with other documentation indexes,
but this will require extra work (in Javascript only).

When the user start typing in the *Search* box, the **index.josn** file will be loaded. Its size grows with
the size of the documentation.

### Miscellaneous

* Automatic creation of <a> anchor tags for all headings (HTML5-style, with `id` instead of `name`)
* Bootstrap-styled tables


## Bug tracker

Project in Jira: http://alpha.internal.sentrysoftware.net/tracker/projects/SMS/


## Contributing

### Source code

Fork the git repository from http://alpha/source, branch from **develop**, commit and push your changes, and submit pull requests.

### Structure and technologies

Beware that this project is a baroc mix of languages, frameworks and libraries:

* Java for some *backend* HTML processing
* Javascript-in-Java with [GraalVM](//www.graalvm.org/reference-manual/js/) for building the index
* [Velocity](//velocity.apache.org/engine/1.7/user-guide.html) for templating
* [AngularJS](//angularjs.org/) for front-end logic
* Various HTML, CSS and JS frameworks and libraries (Bootstrap, etc.)
* [npm](www.npmjs.com/) and [Gulp.js](//gulpjs.com/) to build the front-end
* [Groovy](//groovy-lang.org/) for validating the integration tests

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

```sh
$ mvn verify
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

```sh
$ mvn verify
```

This command builds the skin and run it against a documentation project. The result can be seen in
`./sentry-maven-skin/target/it/studio-km/site/*.html`. The output of the build (if it fails or if the integration tests
fail) is stored in `./sentry-maven-skin/target/it/studio-km/build.log`.

### Finalizing a version

Once the results are satisfactory, you will want to "officially" release the new version of the *Sentry Maven Skin*.
To do so, follow Gitflow procedure.

Note: To update the version number to a non-`SNAPSHOT` version, run the below command from the
root directory of the *Sentry Maven Skin* project:

```sh
$ mvn versions:set -DremoveSnapshot=true
```

Once validated by the QA team, deploy to the alpha Maven repository from the same root directory with the command below:

```sh
$ mvn clean deploy
```

This will deploy the official (non-`SNAPSHOT`) version of the *Sentry Maven Plugin* to the *Maven* repository
so that other projects (documentation projects) can consume it.

Then set the version to a new SNAPSHOT version:

```sh
$ mvn versions:set -DnewVersion=3.1-SNAPSHOT
```
