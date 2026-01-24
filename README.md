# Sentry Maven Skin

![GitHub release (with filter)](https://img.shields.io/github/v/release/sentrysoftware/sentry-maven-skin)
![Build](https://img.shields.io/github/actions/workflow/status/sentrysoftware/sentry-maven-skin/deploy.yml)
![GitHub top language](https://img.shields.io/github/languages/top/sentrysoftware/sentry-maven-skin)
![License](https://img.shields.io/github/license/sentrysoftware/sentry-maven-skin)

The [_Sentry Maven Skin_](https://sentrysoftware.github.io/sentry-maven-skin) is an Apache Maven site skin to be used to generate Sentry's technical user documentation, using modern-era development tools (git, maven, markdown), and producing modern-era Web-based documentation (HTML5, Bootstrap, etc.)

To use the _Sentry Maven Skin_, read the [full documentation](https://sentrysoftware.github.io/sentry-maven-skin).

## Structure and technologies

Beware that this project is a baroc mix of languages, frameworks and libraries:

- Java for some _backend_ HTML processing
- Javascript-in-Java with [GraalVM](https://www.graalvm.org/reference-manual/js/) for building the index
- [Velocity](https://velocity.apache.org/engine/1.7/user-guide.html) for templating
- [AngularJS](https://angularjs.org/) for front-end logic
- Various HTML, CSS and JS frameworks and libraries (Bootstrap, etc.)
- [npm](https:www.npmjs.com/) and [Gulp.js](https://gulpjs.com/) to build the front-end
- [Groovy](https://groovy-lang.org/) for validating the integration tests

The _Sentry Maven Skin_ project is made of several main components:

- `./src/webapp/**`: the front-end web app, including CSS, JS, HTML templates, etc.
- `./src/webapp/site.vm`: the _Velocity_ template that will generate each HTML page, this is the one referencing the JS and CSS
- `./src/package.json`: for NPM
- `./src/gulpfile.js`: to build and minify the web app
- `./src/it/studio-km`: an integration test using a slightly customized version of Monitoring Studio's documentation

## Build

The build is done with Maven with the below command:

```bash
mvn verify
```

Build steps:

- NodeJS is installed in `./node` (and is ignored by Git)
- `npm install` is run to get all dependencies listed in `package.json`, which are installed in the `./node_modules` (also ignored by Git)
- `gulp` is run with `./gulpfile.js` to build the front-end web app (lint, minification, template embedding, etc.), and the result is stored in `./target/dist`
- The Maven skin JAR is assembled in `./target`
- A temporary local Maven repository is set up with both JAR artifacts (the skin and the Velocity tools)
- A test project is set up in `./target/it` and `mvn site` is run on this test project
- The result validated with a Groovy script

## Test

While modifying the _Sentry Maven Skin_, you will want to see how your changes are reflected in a _test_ documentation project.

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
