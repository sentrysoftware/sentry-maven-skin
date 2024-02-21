keywords: banner, navigation
description: ${project.name} lets writers specify links in the top navigation bar.

# Top Navigation Links

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

At the very top of each page, the navigation bar allows you to include a logo and link to your organization, as well as links to other resources, typically outside of the generated documentation (links to pages inside the documentation are normally listed in the menu on the left), as in the example below:

![inline](images/nav-links.png)

In mobile view, the main link to your organization is displayed at the top of the page, and other links at the very bottom of the page, to make the page easier to read:

![inline](images/nav-links-mobile.png)

## `<bannerLeft>`

The first link and logo on the left in this navigation bar is specified with `<bannerLeft>` in `src/site/site.xml`:

```xml
<project name="My Documentation">

  <bannerLeft>
    <src>images/logo-short.png</src>
    <name>Banner Left</name>
    <href>https://banner.left</href>
  </bannerLeft>
```

The `<src>` property refers to an image storage in the `src/resources` directory. For better result, please ensure the image follows these rules:

* Height of 40px to 80px (the image will be resized to 40 pixels in height to fit the bar)
* Transparent background (PNG is strongly recommended)
* Light colors (to ensure contrast with the dark color of the bar)

If you don't have any logo to display, simply use the `<name>` property to specify the text to display for the main link in the navigation bar. Both `<src>` and `<name>` are optional, you only need to specify one of these properties.

## `<links>`

In `src/site/site.xml`, use the `<links>` tag under `<body>` to specify links in the top navigation bar. Specify each link as an [`<item>` element](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-model/site.html#item):

```xml
<project name="My Documentation">

  ...

  <body>
    ...
    <links>
      <item name="Fork on GitHub" href="\${project.scm.url}" />
      <item name="Maven Site Plugin" href="https://maven.apache.org/plugins/maven-site-plugin" />
      <item name="Maven Site Descriptor" href="https://maven.apache.org/plugins/maven-site-plugin/examples/sitedescriptor.html" />
      <item name="Other Maven Skins" href="https://maven.apache.org/skins" />
    </links>
```

## `<bannerRight>`

Very similar to `<bannerLeft>`, this element will be displayed on the right of the navigation bar. You can specify either `<src>`, `<name>`, or both.

## `<additionalLinks>`

In `src/site/site.xml`, use the `<additionalLinks>` tag under `<custom>` to specify additional links in the footer of the page. Such additional links are typically used for mandatory legal documents, like terms of use, disclaimer, privacy policy, etc.

Specify each link as a `<link>` element, as in the example below:

```xml
<project name="My Documentation">

  ...

  <custom>
    <additionalLinks>
      <link>
        <name>My Link Text</name>
        <href>https://w3c.org</href>
        <target>_blank</href>
      </link>
      <link>
        <name>Another Useful Link</name>
        <href>https://onehome.org</href>
      </link>
    </additionalLinks>
  </custom>

```