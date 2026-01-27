keywords: tabs, accordion, carousel, collapse, popover, ui, bootstrap, components, angular
description: Interactive UI components including tabs, accordion, carousel, and popovers.

# UI Components (Tabs, Accordion, etc.)

The skin includes [Angular UI Bootstrap](https://angular-ui.github.io/bootstrap/) components for interactive documentation.

## Available Components

| Component               | Description                         |
| ----------------------- | ----------------------------------- |
| [Tabs](#tabs)           | Organize content in switchable tabs |
| [Accordion](#accordion) | Collapsible content panels          |
| [Collapse](#collapse)   | Toggle content visibility           |
| [Carousel](#carousel)   | Image or content slideshow          |
| [Popover](#popover)     | Contextual popup boxes              |

## Important: Syntax Rules

Since Markdown files are converted to HTML via Maven Doxia's Flexmark parser, there are specific rules for using UI components:

1. **Custom elements like `<uib-tabset>` are NOT supported** - they get stripped by the parser
2. **Use attribute-based syntax** with standard `<div>` elements
3. **All directive attributes must have a value** - use `=""`
4. **Only inline Markdown works inside components** - bold, italic, links, and inline code
5. **Block-level Markdown breaks out of components** - headings, lists, blockquotes, and code blocks will escape the `<div>` structure
6. **Use Bootstrap heading classes for titles** - use `<p class="h3">Title</p>` instead of `<h3>` tags
7. **The `sentry-uib` class is added automatically** - no need to specify it manually

```html
<!-- Correct: Use div elements with Bootstrap heading classes -->
<div uib-tabset="">
  <div uib-tab="" heading="Tab 1">
    <p class="h4">Section Title</p>
    <p>Use <strong>inline formatting</strong>, <em>emphasis</em>, and <a href="url">links</a>.</p>
  </div>
</div>

<!-- Wrong: Custom elements are stripped -->
<uib-tabset>
  <uib-tab heading="Tab 1">Content</uib-tab>
</uib-tabset>

<!-- Wrong: Heading tags create section wrappers that break out -->
<div uib-tab="" heading="Tab 1">
  <h4>This breaks out of the div!</h4>
</div>
```

## Tabs

Tabs organize content into multiple panels, with one panel visible at a time.

### Basic Tabs Example

```html
<div uib-tabset="">
  <div uib-tab="" heading="Overview">
    <p class="h4">Overview</p>
    <p>This is the overview content with <strong>inline</strong> formatting.</p>
  </div>
  <div uib-tab="" heading="Installation">
    <p class="h4">Installation</p>
    <p>Add the dependency, configure <code>site.xml</code>, then run <code>mvn site</code>.</p>
  </div>
  <div uib-tab="" heading="Configuration">
    <p class="h4">Configuration</p>
    <p>Configuration details with a <code>code snippet</code> example.</p>
  </div>
</div>
```

### Live Demo

<div uib-tabset="">
  <div uib-tab="" heading="Overview">
    <p class="h4">Overview</p>
    <p>This is the <strong>overview</strong> content. Tabs are great for organizing related information.</p>
  </div>
  <div uib-tab="" heading="Installation">
    <p class="h4">Installation</p>
    <p>To install the skin, add the <strong>skin dependency</strong> to your <code>site.xml</code>, add the <strong>maven-skin-tools</strong> dependency to your <code>pom.xml</code>, then run <code>mvn site</code> to generate your documentation.</p>
    <p>Check the <a href="start.html">Quick Start</a> guide for detailed instructions.</p>
  </div>
  <div uib-tab="" heading="Configuration">
    <p class="h4">Configuration</p>
    <p>Configure the skin in your <code>site.xml</code> file. See <a href="settings.html">Configuration Reference</a> for all options.</p>
  </div>
</div>

### Tabs with Icons

Include FontAwesome icons in tab headings:

```html
<div uib-tabset="">
  <div uib-tab="" heading="<i class='fas fa-home'></i> Home">
    <p>This is the <strong>home</strong> tab with an icon in the heading.</p>
  </div>
  <div uib-tab="" heading="<i class='fas fa-cog'></i> Settings">
    <p>Configure your preferences here.</p>
  </div>
</div>
```

### Justified Tabs

Make tabs fill the entire width:

```html
<div uib-tabset="" justified="true">
  <div uib-tab="" heading="Tab 1">
    <p>First tab content with <strong>bold</strong> text.</p>
  </div>
  <div uib-tab="" heading="Tab 2">
    <p>Second tab content with <em>emphasis</em>.</p>
  </div>
</div>
```

### Vertical Tabs

Display tabs vertically:

```html
<div uib-tabset="" vertical="true">
  <div uib-tab="" heading="Tab 1">
    <p>Vertical tabs are great for <strong>side navigation</strong>.</p>
  </div>
  <div uib-tab="" heading="Tab 2">
    <p>More content in the second tab.</p>
  </div>
</div>
```

## Accordion

Accordions display collapsible content panels for presenting information in limited space.

### Basic Accordion Example

```html
<div uib-accordion="">
  <div uib-accordion-group="" heading="Section 1" is-open="true">
    <p>This section is <strong>open by default</strong>. Use <em>inline formatting</em>, <code>code</code>, and <a href="url">links</a>.</p>
  </div>
  <div uib-accordion-group="" heading="Section 2">
    <p>More content with inline formatting.</p>
  </div>
  <div uib-accordion-group="" heading="Section 3">
    <p>Final section content.</p>
  </div>
</div>
```

### Live Demo

<div uib-accordion="">
  <div uib-accordion-group="" heading="What is Sentry Maven Skin?" is-open="true">
    <p>Sentry Maven Skin is a <strong>modern, responsive skin</strong> for Maven-generated documentation sites. It provides a professional look and feel, full-text search, dark and light themes, and mobile-friendly design.</p>
  </div>
  <div uib-accordion-group="" heading="How do I install it?">
    <p>Add the skin dependency to your <code>site.xml</code>, add <code>maven-skin-tools</code> to your <code>pom.xml</code>, then run <code>mvn site</code>. See the <a href="start.html">Quick Start</a> for complete instructions.</p>
  </div>
  <div uib-accordion-group="" heading="Is it free?">
    <p>Yes! Sentry Maven Skin is <strong>open source</strong> and licensed under the <a href="https://www.apache.org/licenses/LICENSE-2.0">Apache License 2.0</a>.</p>
  </div>
</div>

### Close Others

Allow multiple panels open simultaneously:

```html
<div uib-accordion="" close-others="false">
  <div uib-accordion-group="" heading="Section 1">
    <p>First section with <strong>bold</strong> content.</p>
  </div>
  <div uib-accordion-group="" heading="Section 2">
    <p>Second section with <em>more</em> content.</p>
  </div>
</div>
```

## Collapse

Hide and show content with smooth animation.

### Basic Collapse Example

```html
<p>
  <button type="button" class="btn btn-primary" ng-click="isCollapsed = !isCollapsed">Toggle Content</button>
</p>
<div uib-collapse="isCollapsed">
  <div class="well">
    <p><strong>Hidden Details</strong></p>
    <p>This content can be <strong>shown or hidden</strong> by clicking the button above.</p>
  </div>
</div>
```

### Live Demo

<p>
  <button type="button" class="btn btn-primary" ng-click="demoCollapsed = !demoCollapsed">
    <i class="fas fa-chevron-down" ng-hide="demoCollapsed"></i>
    <i class="fas fa-chevron-up" ng-show="demoCollapsed"></i>
    Toggle Details
  </button>
</p>
<div uib-collapse="!demoCollapsed">
  <div class="well">
    <p><strong>Additional Details</strong></p>
    <p>This content is <strong>hidden by default</strong> and can be revealed by clicking the button. This is useful for showing optional information, keeping the page clean, and progressive disclosure of details.</p>
  </div>
</div>

## Carousel

Cycle through elements like a slideshow.

### Basic Carousel Example

```html
<div uib-carousel="" interval="5000" no-wrap="false" active="activeSlide" ng-init="activeSlide = 0">
  <div uib-slide="" index="0">
    <img src="images/slide1.png" alt="Slide 1" />
    <div class="carousel-caption">
      <p class="h4">First Slide</p>
      <p>Description for the first slide.</p>
    </div>
  </div>
  <div uib-slide="" index="1">
    <img src="images/slide2.png" alt="Slide 2" />
    <div class="carousel-caption">
      <p class="h4">Second Slide</p>
      <p>Description for the second slide.</p>
    </div>
  </div>
</div>
```

### Carousel Options

| Attribute  | Description                                         | Default |
| ---------- | --------------------------------------------------- | ------- |
| `active`   | Variable to track active slide index (required)     | -       |
| `ng-init`  | Initialize the active variable (e.g., `active = 0`) | -       |
| `interval` | Time in ms between slides                           | `5000`  |
| `no-wrap`  | Disable continuous cycling                          | `false` |
| `no-pause` | Disable pausing on hover                            | `false` |

## Popover

Display additional content on hover or click.

### Basic Popover Example

```html
<button
  type="button"
  class="btn btn-default"
  uib-popover="This is the popover content!"
  popover-title="Popover Title"
  popover-trigger="'mouseenter'"
>
  Hover me
</button>
```

### Live Demo

<p>
  <button type="button" class="btn btn-info"
          uib-popover="This popover appears on hover. It's great for providing additional context without cluttering the page."
          popover-title="More Information"
          popover-trigger="'mouseenter'"
          popover-placement="right">
    <i class="fas fa-info-circle"></i> Hover for more info
  </button>
  <button type="button" class="btn btn-warning"
          uib-popover="Click anywhere outside to close this popover."
          popover-title="Click Popover"
          popover-trigger="'outsideClick'"
          popover-placement="top">
    <i class="fas fa-hand-pointer"></i> Click me
  </button>
</p>

### Popover Triggers

| Trigger          | Description                               |
| ---------------- | ----------------------------------------- |
| `'mouseenter'`   | Show on hover                             |
| `'click'`        | Toggle on click                           |
| `'outsideClick'` | Show on click, hide when clicking outside |
| `'focus'`        | Show on focus                             |

### Popover Placement

Use `popover-placement`: `top`, `bottom`, `left`, `right`, `auto`.

## Tooltips

For simple text hints, use tooltips:

```html
<button type="button" class="btn btn-default" uib-tooltip="This is a tooltip!" tooltip-placement="top">
  Hover for tooltip
</button>
```

### Live Demo

<p>
  <button type="button" class="btn btn-default" uib-tooltip="Quick hint text!" tooltip-placement="top">
    Top tooltip
  </button>
  <button type="button" class="btn btn-default" uib-tooltip="Another tooltip!" tooltip-placement="bottom">
    Bottom tooltip
  </button>
  <button type="button" class="btn btn-default" uib-tooltip="Left side!" tooltip-placement="left">
    Left tooltip
  </button>
  <button type="button" class="btn btn-default" uib-tooltip="Right side!" tooltip-placement="right">
    Right tooltip
  </button>
</p>

## Styling

The skin automatically adds the `sentry-uib` class to all UI Bootstrap components for consistent styling with the theme. You don't need to add it manually.

## Best Practices

1. **Keep it simple**: Use components to enhance readability, not add complexity
2. **Use HTML for all content**: Everything inside components must be HTML (no Markdown blocks)
3. **Use Bootstrap heading classes**: Use `<p class="h4">Title</p>` instead of `<h4>Title</h4>` for titles
4. **Inline Markdown works**: Bold (`**text**`), italic (`*text*`), links, and inline code are fine
5. **Accessibility**: Ensure interactive elements are keyboard-accessible
6. **Mobile-friendly**: Test components on smaller screens
7. **Fallback content**: Consider users with JavaScript disabled

## More Information

See the [Angular UI Bootstrap documentation](https://angular-ui.github.io/bootstrap/) for complete options.
