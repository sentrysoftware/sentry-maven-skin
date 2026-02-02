keywords: velocity, properties, variables
description: Reference Maven properties from pom.xml and site.xml in your documentation.

# Maven Properties

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Reference properties from `pom.xml` and `site.xml` directly in your Markdown files.

> [!NOTE]
> This feature extends the default Maven Site behavior through special processing in the skin.

## Properties from pom.xml

Define properties in your `pom.xml`:

```xml
<project>
  <properties>
    <productShortname>MetricsHub</productShortname>
    <serviceUrl>https://metricshub.com/api</serviceUrl>
  </properties>
</project>
```

Reference them in Markdown using `${esc.d}propertyName`:

```markdown
**${esc.d}productShortname** connects to the [API](${esc.d}serviceUrl)...
```

**Result**:

> **MetricsHub** connects to the [API](https://metricshub.com/api)...

### Dotted Property Names

For properties with dots, use this syntax:

```markdown
Generated on ${esc.d}{context.get("project.build.outputTimestamp")}.
```

## Properties from site.xml

Define custom properties in `src/site/site.xml`:

```xml
<project name="My Documentation">
  <custom>
    <productShortname>MetricsHub</productShortname>
    <serviceUrl>https://metricshub.com/api</serviceUrl>
  </custom>
</project>
```

Reference them with:

```markdown
**${esc.d}decoration.getCustomValue("productShortname")** uses the [API](${esc.d}decoration.getCustomValue("serviceUrl"))...
```

> **Recommendation**: Prefer `site.xml` properties to keep documentation configuration separate from build configuration.

## Other Available Objects

Documents can also reference objects from the [Doxia Site Renderer](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-renderer/), including various tools and project metadata.
