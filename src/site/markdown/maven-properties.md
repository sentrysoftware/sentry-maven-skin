keywords: velocity, properties, variables, interpolation
description: Reference Maven properties from pom.xml and site.xml in your documentation.
interpolation: none

# Maven Properties

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Reference properties from `pom.xml` and `site.xml` directly in your Markdown files.

## Interpolation Modes

The skin supports 3 interpolation modes, configured in `site.xml` or via frontmatter:

> [!TABS]
>
> - site.xml (Global)
>
>   ```xml
>   <site xmlns="http://maven.apache.org/SITE/2.0.0">
>     <custom>
>       <interpolation>maven</interpolation>
>     </custom>
>   </site>
>   ```
>
> - Frontmatter (Per-Page)
>
>   ```markdown
>   interpolation: none
>
>   # Template Documentation
>
>   This page shows literal ${...} syntax.
>   ```

| Mode       | Description                                                                                              |
| ---------- | -------------------------------------------------------------------------------------------------------- |
| `none`     | No interpolation. All `${...}` placeholders remain as-is.                                                |
| `maven`    | **Default.** Replaces `${propertyName}` with values from `pom.xml`. Hash characters (`#`) work normally. |
| `velocity` | Full Velocity processing. **Use with caution** - has significant limitations (see below).                |

## Mode: `none`

With `none` interpolation, no property replacement is performed. All `${...}` placeholders remain as-is:

```xml
<!-- site.xml -->
<site xmlns="http://maven.apache.org/SITE/2.0.0">
  <custom>
    <interpolation>none</interpolation>
  </custom>
</site>
```

This is useful when you need to show literal `${...}` syntax in documentation or when using Velocity templates (`.md.vm` files) where you handle interpolation yourself.

## Mode: `maven` (Default)

With `maven` interpolation (the default), properties from `pom.xml` and custom properties from `site.xml` are replaced:

### pom.xml Properties

```xml
<!-- pom.xml -->
<project>
  <properties>
    <productShortname>MetricsHub</productShortname>
    <serviceUrl>https://metricshub.com/api</serviceUrl>
  </properties>
</project>
```

Then in Markdown:

```markdown
**${productShortname}** connects to the [API](${serviceUrl})...

Project version: ${project.version}
```

The following standard properties are also replaced:

| Property                       | Description       |
| ------------------------------ | ----------------- |
| `${project.version}`           | Project version   |
| `${project.name}`              | Project name      |
| `${project.groupId}`           | Group ID          |
| `${project.artifactId}`        | Artifact ID       |
| `${project.url}`               | Project URL       |
| `${project.description}`       | Description       |
| `${project.organization.name}` | Organization name |
| `${project.organization.url}`  | Organization URL  |

### site.xml Custom Properties

Custom properties defined in `<custom>` section of `site.xml` are also resolved:

```xml
<!-- site.xml -->
<site xmlns="http://maven.apache.org/SITE/2.0.0">
  <custom>
    <productShortname>MetricsHub</productShortname>
    <supportEmail>support@metricshub.com</supportEmail>
  </custom>
</site>
```

Then in Markdown:

```markdown
Contact **${productShortname}** support at ${supportEmail}.
```

### Preventing Interpolation

To display a property placeholder literally without replacement, escape the dollar sign with a **double dollar**:

```markdown
Use $${project.version} to reference the version.
```

This renders as: Use ${project.version} to reference the version.

For pages that extensively document `${...}` syntax, you can disable interpolation entirely using the `interpolation: none` front matter header:

```markdown
keywords: my, keywords
description: Page description
interpolation: none

# My Page Title

The ${project.version} placeholder will NOT be replaced.
```

### Mode: `velocity`

The `velocity` mode uses `$render.eval()` to process content as Velocity syntax.

The Markdown to HTML transformation follows these steps:

1. Doxia Markdown converts the Markdown source documents into raw XHTML content.
2. Velocity parses the produced XHTML and interprets all `$` references and `#` macros.
3. The Sentry Maven Skin wraps the processed XHTML content with HTML header and body (and the entire site decoration).

All properties from Maven's `pom.xml` can be referenced through the `$project` object.

Documents can also reference objects from the [Doxia Site Renderer](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-renderer/), including `$site` ([SiteModel](https://maven.apache.org/doxia/doxia-sitetools/doxia-site-model/apidocs/org/apache/maven/doxia/site/SiteModel.html)), various tools and project metadata.

> [!CAUTION]
> **Limitations in `velocity` interpolation mode:**
>
> 1. **Smart quotes break method calls**: Except in code blocks, Doxia's Markdown parser converts ASCII quotes (`"..."` and `'...'`) to Unicode smart quotes ("..." and '...'). Velocity doesn't recognize these as string delimiters, so method calls like `$site.getCustomValue("property")` will fail (outside of code blocks).
>
> 2. **Velocity directives are interpreted**: Any `#if`, `#foreach`, `#include`, etc. in your content will be processed as Velocity directives, which may not be desired.
>
> 3. **Basic variable references work**: Simple references like `${project.name}` work fine because they don't involve string quotes.

## Using Velocity Templates (`*.vm`)

For advanced use cases requiring full Velocity syntax (conditionals, loops, etc.), use **Velocity Markdown templates** with the `.md.vm` extension instead of setting an interpolation mode:

1. Velocity parses the `.md.vm` file and interprets all `$` references and `#` macros and outputs Markdown content.
2. Doxia Markdown converts the Markdown documents into raw XHTML content.
3. The Sentry Maven Skin wraps the processed XHTML content with HTML header and body (and the entire site decoration).

Velocity templates are processed **before** HTML conversion, so smart typography doesn't interfere with Velocity syntax.

Example: `advanced-config.md.vm`

```velocity
Configuration for $project.name (using underlined heading 1 style)
===============================

#if($project.description)
$project.description
#end

Properties (using underlined heading 2 style)
----------

${esc.h}${esc.h}${esc.h} This would be a heading 3

#foreach($prop in $project.properties.entrySet())
* **$prop.key**: $prop.value
#end
```

> [!NOTE]
> In `.md.vm` files, use `$esc.h` to output literal `#` characters in Markdown (e.g., `${esc.h}include` for C includes, `Fix ${esc.h}123` for issue references, or `${esc.h}${esc.h} My Title` for a heading 2 title).
>
> For headings, it is also recommended to use underline headings (as in the above example) to avoid hashes as in the example below:
>
> ```md
> My Main Title
> =============
>
> My First Entry
> --------------
>
> And now my text.
> ```
