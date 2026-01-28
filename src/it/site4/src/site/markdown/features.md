title: Feature List
keywords: features, site4, maven, documentation
description: Complete list of features supported by Sentry Maven Skin with Maven Site Plugin 4.x
author: Test Author
date: 2024-06-15

# Features

This page documents all the features that work with Maven Site Plugin 4.x.

## Site Descriptor Features

### Site Name

The site name is configured in the `<site>` root element with the `name` attribute. This replaces the `<project name="...">` syntax from version 1.x.

### Custom Properties

Custom properties are defined under `<custom>` and accessible via `$site.custom`:

* **projectVersion**: Custom version display
* **bodyClass**: CSS class for styling
* **keywords**: Site-wide keywords

### Banners

Banner left and right are configured as before:

```xml
<bannerLeft>
  <name>Banner Left</name>
  <href>https://example.org</href>
</bannerLeft>
```

## Navigation Features

### Menu Structure

Menus are defined the same way as in 3.x:

```xml
<menu name="Getting Started">
  <item name="Overview" href="index.html" />
</menu>
```

### Breadcrumbs

Breadcrumbs help users navigate:

```xml
<breadcrumbs>
  <item name="Home" href="index.html" />
</breadcrumbs>
```

## Social Integration

Social links are configured under `<custom><social>`:

* Twitter
* LinkedIn
* Facebook
* Custom links

## Analytics

Google Analytics integration works the same way:

```xml
<googleAnalyticsAccountId>YOUR_ID</googleAnalyticsAccountId>
```
