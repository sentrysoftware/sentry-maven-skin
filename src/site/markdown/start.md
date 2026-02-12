keywords: setup, quickstart, installation, getting started
description: Get started with ${project.name} in 5 minutes. Step-by-step setup guide.

# Quick Start

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Get your documentation site up and running in 5 minutes.

## Prerequisites

- A [Maven project](https://maven.apache.org/plugins/maven-site-plugin/examples/creating-content.html) with `pom.xml`
- Maven 3.x installed
- Java 8 or later

## Step 1: Project Structure

Create this directory structure:

```
my-project/
├── pom.xml
└── src/
    └── site/
        ├── site.xml
        ├── markdown/
        │   └── index.md
        └── resources/
            ├── css/
            │   └── site.css (optional)
            └── images/
                └── logo.png (optional)
```

## Step 2: Configure pom.xml

Add the Maven Site Plugin with the required dependency:

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-site-plugin</artifactId>
      <version>4.0.0-M16</version>
      <dependencies>
        <dependency>
          <groupId>org.sentrysoftware.maven</groupId>
          <artifactId>maven-skin-tools</artifactId>
          <version>${skinToolsVersion}</version>
        </dependency>
      </dependencies>
    </plugin>
  </plugins>
</build>
```

> [!NOTE]
> This skin supports both `maven-site-plugin` **4.x** and **3.x**.

## Step 3: Configure site.xml

Create `src/site/site.xml`:

> [!TABS]
>
> - Maven Site Plugin 4.x
>
>   ```xml
>   <site xmlns="http://maven.apache.org/SITE/2.0.0"
>       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>       xsi:schemaLocation="http://maven.apache.org/SITE/2.0.0 https://maven.apache.org/xsd/site-2.0.0.xsd"
>       name="$${project.name}">
>
>     <skin>
>       <groupId>${project.groupId}</groupId>
>       <artifactId>${project.artifactId}</artifactId>
>       <version>${project.version}</version>
>     </skin>
>
>     <bannerLeft name="$${project.organization.name}" href="$${project.organization.url}">
>       <image src="images/logo.png" alt="$${project.organization.name}"/>
>     </bannerLeft>
>
>     <custom>
>       <!-- Skin settings (see https://sentrysoftware.org/sentry-maven-skin/settings.html) -->
>       <keywords>maven, documentation</keywords>
>     </custom>
>
>     <body>
>       <menu name="Documentation">
>         <item name="Overview" href="index.html"/>
>       </menu>
>     </body>
>
>   </site>
>   ```
>
> - Maven Site Plugin 3.x
>
>   ```xml
>   <project name="$${project.name}">
>
>     <skin>
>       <groupId>${project.groupId}</groupId>
>       <artifactId>${project.artifactId}</artifactId>
>       <version>${project.version}</version>
>     </skin>
>
>     <bannerLeft>
>       <name>$${project.organization.name}</name>
>       <href>$${project.organization.url}</href>
>       <src>images/logo.png</src>
>       <alt>$${project.organization.name}</alt>
>     </bannerLeft>
>
>     <custom>
>       <!-- Skin settings (see https://sentrysoftware.org/sentry-maven-skin/settings.html) -->
>       <keywords>maven, documentation</keywords>
>     </custom>
>
>     <body>
>       <menu name="Documentation">
>         <item name="Overview" href="index.html"/>
>       </menu>
>     </body>
>
>   </project>
>   ```

> [!TIP]
> See [Configuration Reference](settings.html) for all available `<custom>` settings.

## Step 4: Write Your First Page

Create `src/site/markdown/index.md`:

```markdown
# Welcome

This is my documentation built with **${project.name}**.

## Features

- Easy to write in Markdown
- Beautiful output
- Full-text search included
```

## Step 5: Build and Preview

Generate your site:

```bash
mvn clean site
```

The site is generated in `target/site/`. Open `target/site/index.html` in your browser.

### Live Preview

For real-time preview while editing:

```bash
mvn site:run
```

Then open [http://localhost:8080](http://localhost:8080). Changes to Markdown files are reflected immediately.

> [!NOTE]
> Changes to `site.xml` or `pom.xml` require restarting `mvn site:run`.

## Next Steps

Now that your site is running, continue with:

### Setup

- [Styling](styles.html) - Customize colors and fonts
- [Navigation Menu](nav-menu.html) - Add sidebar navigation
- [Navigation Links](nav-links.html) - Configure header and logo

### Writing

- [Writing a Page](page-structure.html) - Structure your documentation
- [Code Highlighting](code.html) - Format code examples
- [Images](images.html) - Add screenshots and diagrams

### Reference

- [Configuration Reference](settings.html) - All site.xml and front matter options
- [Features Overview](features.html) - Explore all features
