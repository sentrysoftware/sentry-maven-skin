keywords: doxia
description: ${project.name} is an Apache Maven site skin to generate technical user documentation on Java (Maven) projects.

# What is ${project.name}?

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Skin for static site generation with Maven

The **${project.name}** is an [Apache Maven site](https://maven.apache.org/plugins/maven-site-plugin) skin to generate technical user documentation on Java (Maven) projects.

It leverages a (reasonably) modern Web stack:

- HTML5
- Bootstrap 3 CSS
- Mobile-first
- Dark and light colors schemes
- Nice rendering for print
- Local search index with AngularJS (no Google!)
- Image optimization
- Excellent Lighthouse score for performance and accessibility
- and more!

[Get started with your documentation project](start.html).

## Screenshots

### Working on sentrysoftware.org

![Sentry Maven Skin used for sentrysoftware.org](images/general-screenshot.png)

### Dark colors

![Dark colors is supported out-of-the-box, automatic detection, manual toggle, and persistent user preference](images/dark.png)

### Mobile-friendly

![inline](images/mobile-screenshot.png)

### Local search

![An index is built automatically for your documentation and searchable, without Google or external services](images/search-2.png)

## Lighthouse

The below table summarizes the current score of ${project.name} with [Lighthouse](https://developer.chrome.com/docs/lighthouse/overview):

<table>
<tr><th>Device</th><th>Category</th><th>Score</th></tr>
<tr class="success"><td rowspan="4">Desktop</td><td>Performance</td><td>96%</td></tr>
<tr><td>Accessibility</td><td>91%</td></tr>
<tr class="success"><td>Best Practices</td><td>100%</td></tr>
<tr class="success"><td>SEO</td><td>100%</td></tr>
<tr class="warning"><td rowspan="4">Mobile</td><td>Performance</td><td>66%</td></tr>
<tr><td>Accessibility</td><td>88%</td></tr>
<tr class="success"><td>Best Practices</td><td>100%</td></tr>
<tr class="success"><td>SEO</td><td>92%</td></tr>
</table>
