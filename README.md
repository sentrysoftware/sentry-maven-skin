# Sentry Maven Skin

The *Sentry Maven Skin* is an Apache Maven site skin to be used to generate Sentry's technical
user documentation, using modern-era development tools (git, maven, markdown), and producing
modern-era Web-based documentation (HTML5, Bootstrap, etc.)

## Setup of your `site` project

First, the *Sentry Maven Skin* requires a specific Java library for its more advanced features (like the automatic
generation of the ToC of each page). You therefore need to update your `./pom.xml` file as below:

```xml
<build>
	<plugins>
		...
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-site-plugin</artifactId>
			<version>3.6</version>
			...
			<dependencies>
				<dependency>
					<groupId>com.sentrysoftware.doc</groupId>
					<artifactId>sentry-skin-velocity-tools</artifactId>
					<version>1.0</version>
				</dependency>
			</dependencies>
		</plugin>
		...
	</plugins>
</build>
```

Then, specify the Sentry Maven Skin artifact in your `./src/site/site.xml` file:

```xml
<project name="My Project">
	...
	<skin>
		<groupId>com.sentrysoftware.doc</groupId>
		<artifactId>sentry-maven-skin</artifactId>
		<version>1.0</version>
	</skin>
	...
</project>
```

> Note: Use the latest officially available version of the skin as published on Sentry's repository
(it's *1.0* in the examples above).

## Features

### Change the class of the body

In the generated HTML pages, you can change the CSS class applied to the `<body>` element and make the page
look like the solution it belongs to (*Hardware* in gree, *Storage* in orange and *Studio* in purple).

To do so, simply a `solutionClass` property to your `./pom.xml`:

```xml
<project>
	...
	<properties>
		<solutionClass>sentry-storage</solutionClass>
		...
	</properties>
	...
</project>
```

The class that you specify must match with one specified in the CSS that ship with the skin, i.e.:
* sentry-hardware
* sentry-storage
* sentry-studio

### Generate a ToC automatically

The *Sentry Maven Skin* can generate automatically the table of content (ToC) of a page, based on the headings defined in the document.

If you add the below to your input page (your Markdown source document), the *Sentry Maven Skin* will create a
ToC based on the `<h2>` and `<h3>` headings:

```xml
<div id="autoToc" data-headings="h2,h3" />
```

In the *data-headings* field, you can specify the CSS selector that will pick the proper headings to build the ToC.
By default, it will use the `<h2>` heading only.

### Parsing of the `${property}` properties

All properties defined in `pom.xml` can be referenced in the Markdown (and other) source documents and will be
replaced with their corresponding values in the resulting HTML.

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

In the source documents (Markdown or other), you can use `$productShortname` and `$model` that will be replaced
with *Xtrem IO KM* and *EMC XtremIO* respectively.

### Automatic Title

The page title is automatically built from the project name and the first `<h1>` heading found in the document.

### Lightbox for images

By default, all images in the source documents are displayed using *lightbox* (http://lokeshdhakar.com/projects/lightbox2/).
A thumbnail is displayed in the document, using the same image with forced height of 150 pixels.

If you don't want to use *lightbox* for a specific image (like ones that you would use for a bullet list, etc.),
you will need to set its `alt` attribute to `inline`.

Examples:

```
![My Description](./images/my-picture.png) This will show in *lightbox*

![inline](./images/icon.png) This will show "inline", with no *lightbox*
```

### Miscellaneous

* Proper parsing of the links and URLs (relative, subfolders and external)
* Automatic creation of <a> anchor tags for all headings (HTML5-style, with `id` instead of `name`)
* Bootstrap-styled tables


## Bug tracker

Project in Jira: http://alpha.internal.sentrysoftware.net/tracker/projects/SMS/


## Contributing

### Source code

Fork the git repository from http://alpha/source, branch from `master`, commit and push your changes, and submit pull requests.

> Note: This is a purely internal project with no need for patches. As such, its workflows in *Git* are simplified, with only
a `master` branch (no `develop` branch).

### Testing

While modifying the *Sentry Maven Skin*, you will want to see how your changes are reflected in a *test* documentation project.

#### Setting up the Sentry Maven Skin project

The *Sentry Maven Skin* project is made of 2 modules:
* *sentry-maven-skin*: the actual Maven skin
* *sentry-skin-velocity-tools*: a Java module required by the *Sentry Maven Skin* at runtime

The version number is referenced in several files. To change it, it is recommended to use the command below:
```sh
$ mvn versions:set -DnewVersion=1.1-SNAPSHOT
```

Make sure that you set the version number to a proper `SNAPSHOT` version number, higher than the current *production* version.

Ideally, commit and push, create a pull request and merge to `master` in git at this stage.

#### Setting up the *test* documentation project

To see how your changes in the *Sentry Maven Skin* would affect a documentation project, you will have to get a copy of a
documentation project on your computer (it doesn't need to be an official branch in *Git*, as the changes you will do in this
copy are just for testing purpose).

Normally, a documentation project will refer to a *production* level of the *Sentry Maven Skin* (like official version 1.0).
Change the references to the *Sentry Maven Skin* in `pom.xml` and `site.xml` of the *test* project to refer to the `SNAPSHOT`
version of the *Sentry Maven Skin* you're working on.

Example:

In `./pom.xml` of the *test* documentation project:

```xml
<build>
	<plugins>
		...
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-site-plugin</artifactId>
			<version>3.6</version>
			...
			<dependencies>
				<dependency>
					<groupId>com.sentrysoftware.doc</groupId>
					<artifactId>sentry-skin-velocity-tools</artifactId>
					<version>1.1-SNAPSHOT</version>
				</dependency>
			</dependencies>
		</plugin>
		...
	</plugins>
</build>
```

In your `./src/site/site.xml` of the *test* documentation project:

```xml
<project name="My Project">
	...
	<skin>
		<groupId>com.sentrysoftware.doc</groupId>
		<artifactId>sentry-maven-skin</artifactId>
		<version>1.1-SNAPSHOT</version>
	</skin>
	...
</project>
```

#### Changing the Sentry Maven Skin

The skin itself is in the `sentry-maven-skin` module subfolder of the project.

All files are then in the `./src/main/resources` subfolder. There, you can add *CSS* files in the `./src/main/resources/css`
folder. Or *Javascript* files in the `./src/main/resources/js` folder, etc.

The HTML pages are rendered by the `site.vm` file in the `./src/main/resources/META-INF/maven` directory.

`site.vm` contains the HTML page structure and references to the CSS and JS. It refers to `tools.vm` which does the
initialization job (Velocity).

#### Testing changes

Once you've done some changes to the *Sentry Maven Skin*, you will need to *install* it in your local repository
so that your *test* documentation project can "consume" it.

From the root directory of the *Sentry Maven Skin* project, run the following command:

```sh
$ mvn clean install
```

> Note: In case of errors, your changes won't have been installed to your local repository and it is therefore useless to
check things in your *test* documentation project before fixing these errors. There may be *WARNING* messages, but they may
be normal.

Then, from the root directory of your *test* documentation project, run the following command:

```sh
$ mvn clean site -o -U
```

This will force the Maven site plugin to use the latest resources in your local repository (where you just installed your
updated version of the *Sentry Maven Skin*).

> Note: Syntax errors in your `site.vm` will only show at this time.

#### Finalizing a version

Once the results are satisfactory, you will want to "officially" release the new version of the *Sentry Maven Skin*.

To do so, you first need to update the version number to a non-`SNAPSHOT` version. Run the below command from the
root directory of the *Sentry Maven Skin* project:

```sh
$ mvn versions:set -DremoveSnapshot=true
```

Commit and push your changes to the *Git* repository. Submit a pull request to `master` and merge to `master`.

Then, from the same root directory, run the command below:

```sh
$ mvn clean deploy
```

This will deploy the official (non-`SNAPSHOT`) version of the *Sentry Maven Plugin* to the *Maven* repository
so that other projects (documentation projects) can consume it.

Then, immediately, create a new branch from `master` to change the version to the next `SNAPSHOT` version
(as explained above). Commit, push, pull request and merge to `master`.

You're done! Congratulations! :-)


## Copyright and license

This project is based on the work of Andrius Velykis who originally created
the Reflow Maven Skin (http://andriusvelykis.github.io/reflow-maven-skin/),
which is now end-of-lifed (since... 2013?).

As it was originally licensed under the Apache License 2.0, this one therefore remains:

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this work except in compliance with the License.
You may obtain a copy of the License in the LICENSE file, or at:

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
