# Installation Guide

## Prerequisites

Before installing, ensure you have:

* Maven 3.9.0 or later
* Java 11 or later
* Maven Site Plugin 4.x

## Quick Start

Add the skin to your `site.xml`:

```xml
<skin>
  <groupId>org.sentrysoftware.maven</groupId>
  <artifactId>sentry-maven-skin</artifactId>
  <version>LATEST</version>
</skin>
```

## Maven Configuration

Configure the Maven Site Plugin in your `pom.xml`:

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-site-plugin</artifactId>
  <version>4.0.0-M16</version>
  <dependencies>
    <dependency>
      <groupId>org.sentrysoftware.maven</groupId>
      <artifactId>maven-skin-tools</artifactId>
      <version>LATEST</version>
    </dependency>
  </dependencies>
</plugin>
```

## Site Descriptor

For Maven Site Plugin 4.x, use the new namespace:

```xml
<site name="My Project"
      xmlns="http://maven.apache.org/SITE/2.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SITE/2.0.0
        https://maven.apache.org/xsd/site-2.0.0.xsd">
  <!-- ... -->
</site>
```

<div class="alert alert-info">
<strong>Note:</strong> The root element changed from <code>&lt;project&gt;</code> to <code>&lt;site&gt;</code> in version 2.0.0 of the site descriptor.
</div>

## Building the Site

Run the following command:

```bash
mvn site
```

The generated site will be in `target/site/`.
