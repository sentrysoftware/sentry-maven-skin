keywords: multilingual, locales, i18n, language selector, translation
description: Build multilingual documentation sites with locale-specific pages, labels, and navigation.

# Multilingual Documentation

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

${project.name} supports multilingual documentation out of the box:

- Locale-specific content (`src/site/<locale>/...`)
- Locale-specific site descriptors (`site_<locale>.xml`)
- Localized skin UI labels (search, ToC heading, copyright, version label, etc.)
- Automatic locale selector in header/footer when multiple locales are configured

## Out-of-the-Box UI Locales

The skin ships built-in UI label bundles for:

| Locale | Java Locale ID | Example |
| ------ | -------------- | ------- |
| English (default) | `en` | `On this Page`, `Search...` |
| French | `fr` | `Sur cette page`, `Rechercher...` |
| Spanish | `es` | `En esta pagina`, `Buscar...` |
| Simplified Chinese | `zh_CN` | Chinese UI labels |

> [!NOTE]
> Source bundles are maintained in UTF-8 and converted at build time into Java 8-safe escaped properties.

## Configure Locales in Maven

Enable locales in `maven-site-plugin`:

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-site-plugin</artifactId>
      <configuration>
        <locales>default,fr,es,zh_CN</locales>
      </configuration>
    </plugin>
  </plugins>
</build>
```

Use `default` for the root locale (`target/site/...`) and explicit locale IDs for locale subdirectories (`target/site/fr/...`, etc.).

## Project Layout for Multiple Languages

Typical structure:

```text
src/site/
|-- site.xml                 # default locale descriptor
|-- site_fr.xml              # French descriptor
|-- site_es.xml              # Spanish descriptor
|-- markdown/
|   `-- index.md             # default locale pages
|-- fr/
|   `-- markdown/
|       `-- index.md         # French pages
`-- es/
    `-- markdown/
        `-- index.md         # Spanish pages
```

## Add a New Locale (Not Built In)

For a locale not shipped by the skin (for example `de`), create:

1. Locale content under `src/site/de/markdown/`
2. A locale descriptor `src/site/site_de.xml`
3. UI label overrides in `site_de.xml` `<custom>` using label settings

Example `site_de.xml` excerpt:

```xml
<site xmlns="http://maven.apache.org/SITE/2.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/SITE/2.0.0 https://maven.apache.org/xsd/site-2.0.0.xsd"
      name="${project.name}">
  <custom>
    <tocHeadingText>Auf dieser Seite</tocHeadingText>
    <searchFieldText>Suchen...</searchFieldText>
    <searchResultsText>Suchergebnisse fur</searchResultsText>
    <searchResultSingleText>Ergebnis</searchResultSingleText>
    <searchResultCountText>Ergebnisse</searchResultCountText>
    <publishDateText>Dokumentation vom</publishDateText>
    <copyrightText>Urheberrecht</copyrightText>
    <projectVersionText>Version</projectVersionText>
  </custom>
</site>
```

Those values can still be overridden per page via frontmatter (same precedence rules as other settings).

## Locale Selector

When multiple locales are configured, the selector appears automatically:

- Desktop: header (next to social links/theme toggle)
- Mobile: footer

![Locale selector in header](images/nav-links.png)

![Locale selector in mobile view](images/nav-links-mobile.png)

## See Also

- [Navigation Links](nav-links.html) - Header/footer links and locale selector placement
- [Configuration Reference](settings.html) - All label keys and precedence
- [Front Matter Headers](headers.html) - Per-page overrides
