# Maven properties (`${property}`)

All properties defined in `./pom.xml` can be referenced in the Markdown (and other) source documents and will be replaced with their corresponding values in the resulting HTML, using the `${propertyName}` syntax.

Similarly, all properties defined in `./site/site.xml` under the `<custom>` tag can also be referenced with the `$decoration.getCustomValue("propertyName")` syntax.

Example of a `pom.xml`:

```xml
<project>
  ...
  <properties>
    <productShortname>MetricsHub</productShortname>
    <serviceUrl>https://metricshub.com/api</serviceUrl>
    ...
  </properties>
```

In the source documents (`./src/site/markdown/*.md`, or others), you can use `$productShortname` and `$serviceUrl`, which will be replaced with their corresponding value in the produced HTML:

```md
**$productShortname** allows administrators to setup the monitoring of any application through an [API]($serviceUrl)...
```

Same principle with `./site/site.xml`:

```xml
<project name="My Documentation">

  ...

  <custom>
    <productShortname>Xtrem IO KM</productShortname>
    <serviceUrl>https://metricshub.com/api</serviceUrl>
    ...
  </custom>

```

In the source documents (`./src/site/markdown/*.md`, or others), you can use `$decoration.getCustomValue("productShortName")` and `$decoration.getCustomValue("serviceUrl")`, which will be replaced with with *Xtrem IO KM* and *EMC XtremIO* respectively:

```md
**$decoration.getCustomValue("productShortName")** allows administrators to setup the monitoring of any application through an [API]($decoration.getCustomValue("serviceUrl"))...
```

> **Note**
> The syntax to reference values in `./pom.xml` looks nicer and easier than the syntax for `./site/site.xml`. However, it's the latter method that is recommended so that documentation information remains in `./site/site.xml` rather than spread across several configuration files.
