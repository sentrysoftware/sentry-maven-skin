keywords: ai, prompt, writing, documentation, doxia, maven site
description: Reusable prompt focused on Maven Site Plugin and Sentry Maven Skin specifics.

# Vibe Writing (AI Prompt)

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Use this prompt when asking an AI assistant to create or update documentation in a project that uses **Maven Site Plugin (Doxia)** with the **Sentry Maven Skin**.

> [!IMPORTANT]
> For any new documentation page, add its corresponding navigation entry in `src/site/site.xml` (using the generated `.html` target).

## Copy/Paste Prompt

```text
You are contributing documentation to a Maven Site project using Doxia Markdown and the Sentry Maven Skin.

Project rules and structure:
- Documentation pages are in src/site/markdown/*.md
- Navigation is declared in src/site/site.xml
- New page workflow:
  1) create/update the Markdown page in src/site/markdown/
  2) add/update the corresponding <item> in src/site/site.xml so the page appears in the menu
  3) use the .html target in site.xml links (example: my-page.html)

Maven Site / Doxia specifics to respect:
- Start pages with supported metadata headers when relevant:
  - keywords: comma-separated values
  - description: meta description
  - title: optional explicit page title override
- Use one H1 (#) for the page title
- Use relative internal links to generated pages (example: settings.html)
- Keep content compatible with Doxia Markdown and documented macros/features
- Maven properties may be referenced where needed (example: ${project.version})

Sentry Maven Skin specifics to use when relevant:
- Table of contents macro near the top of long pages:
  <!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->
- Callouts supported by the skin:
  > [!NOTE]
  > [!TIP]
  > [!IMPORTANT]
  > [!WARNING]
  > [!CAUTION]
- UI components syntax from the skin docs (tabs, accordion, collapse)
- Code fences with explicit language for Prism highlighting
- Images with project-relative paths; standard Markdown image syntax is supported
- Multilingual sites must keep equivalent page structure across locales when pages are translated

Expected output:
- Provide updated Markdown page content
- If navigation changes are required, provide the corresponding site.xml snippet
```

## Full Reference

For complete details, see:

- [Sentry Maven Skin Documentation](https://sentrysoftware.github.io/sentry-maven-skin)
- [Writing a Documentation Page](page-structure.html)
- [Navigation Menu](nav-menu.html)
- [Front Matter Headers](headers.html)
- [UI Components](ui-components.html)
- [Doxia Features](doxia.html)
- [Table of Contents](toc.html)
- [Maven Properties](maven-properties.html)
- [Multilingual Documentation](multilingual.html)
