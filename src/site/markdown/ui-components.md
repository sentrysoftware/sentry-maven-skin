keywords: tabs, accordion, carousel, collapse, popover, ui, bootstrap, components, angular
description: ${project.name} includes Angular UI Bootstrap components like tabs, accordion, carousel, collapse, and popover to make your documentation more interactive.

# UI Components (Tabs, Accordion, ...)

**${project.name}** includes [Angular UI Bootstrap](https://angular-ui.github.io/bootstrap/) components to enhance your documentation with interactive UI elements. These components work seamlessly with Bootstrap 3 and AngularJS.

## Available Components

| Component | Description |
|-----------|-------------|
| [Tabs](#tabs) | Organize content in switchable tabs |
| [Accordion](#accordion) | Collapsible content panels |
| [Collapse](#collapse) | Toggle content visibility |
| [Carousel](#carousel) | Image or content slideshow |
| [Popover](#popover) | Contextual popup boxes |

## Important: Markdown Compatibility

Since Markdown files are converted to HTML via Maven Doxia's XHTML parser, there are specific syntax requirements:

1. **Custom elements like `<uib-tabset>` are NOT supported** - they get stripped by the parser
2. **Use attribute-based syntax** with standard `<div>` elements
3. **All directive attributes must have a value** - use `=""` for boolean directives

```html
<!-- ✓ CORRECT: Use attribute syntax with empty values -->
<div uib-tabset="">
  <div uib-tab="" heading="Tab 1">Content of Tab 1</div>
  <div uib-tab="" heading="Tab 2">Content of Tab 2</div>
</div>

<!-- ✗ WRONG: Custom elements are stripped -->
<uib-tabset>
  <uib-tab heading="Tab 1">Content</uib-tab>
</uib-tabset>

<!-- ✗ WRONG: Bare attributes cause parse errors -->
<div uib-tabset>...</div>
```

## Tabs

Tabs allow you to organize content into multiple panels, with only one panel visible at a time.

### Basic Tabs Example

```html
<div uib-tabset="">
  <div uib-tab="" heading="Overview">
    <p>This is the overview content.</p>
  </div>
  <div uib-tab="" heading="Installation">
    <p>Installation instructions go here.</p>
  </div>
  <div uib-tab="" heading="Configuration">
    <p>Configuration details go here.</p>
  </div>
</div>
```

### Live Demo

<div uib-tabset="" class="sentry-uib">
  <div uib-tab="" heading="Overview">
    <p>This is the <strong>overview</strong> content. Tabs are great for organizing related information.</p>
  </div>
  <div uib-tab="" heading="Installation">
    <p>Installation instructions would go here. You can include code blocks, lists, and any other HTML content.</p>
  </div>
  <div uib-tab="" heading="Configuration">
    <p>Configuration details and examples would be shown in this tab.</p>
  </div>
</div>

### Tabs with Icons

You can include FontAwesome icons in tab headings:

```html
<div uib-tabset="" class="sentry-uib">
  <div uib-tab="" heading="<i class='fas fa-home'></i> Home">Home content</div>
  <div uib-tab="" heading="<i class='fas fa-cog'></i> Settings">Settings content</div>
</div>
```

### Justified Tabs

Make tabs fill the entire width:

```html
<div uib-tabset="" justified="true">
  <div uib-tab="" heading="Tab 1">Content 1</div>
  <div uib-tab="" heading="Tab 2">Content 2</div>
</div>
```

### Vertical Tabs

Display tabs vertically:

```html
<div uib-tabset="" vertical="true">
  <div uib-tab="" heading="Tab 1">Content 1</div>
  <div uib-tab="" heading="Tab 2">Content 2</div>
</div>
```

## Accordion

Accordions display collapsible content panels for presenting information in a limited space.

### Basic Accordion Example

```html
<div uib-accordion="">
  <div uib-accordion-group="" heading="Section 1" is-open="true">
    Content of section 1. This section is open by default.
  </div>
  <div uib-accordion-group="" heading="Section 2">
    Content of section 2.
  </div>
  <div uib-accordion-group="" heading="Section 3">
    Content of section 3.
  </div>
</div>
```

### Live Demo

<div uib-accordion="" class="sentry-uib">
  <div uib-accordion-group="" heading="What is Sentry Maven Skin?" is-open="true">
    <p>Sentry Maven Skin is a modern, responsive skin for Maven-generated documentation sites. It provides a professional look and feel with many useful features.</p>
  </div>
  <div uib-accordion-group="" heading="How do I install it?">
    <p>Simply add the skin dependency to your <code>site.xml</code> file and configure it according to the documentation.</p>
  </div>
  <div uib-accordion-group="" heading="Is it free?">
    <p>Yes! Sentry Maven Skin is open source and licensed under the Apache License 2.0.</p>
  </div>
</div>

### Close Others

By default, opening one panel closes others. Set `close-others="false"` to allow multiple panels open:

```html
<div uib-accordion="" close-others="false">
  <div uib-accordion-group="" heading="Section 1">Content 1</div>
  <div uib-accordion-group="" heading="Section 2">Content 2</div>
</div>
```

## Collapse

Collapse provides a simple way to hide and show content with a smooth animation.

### Basic Collapse Example

```html
<p>
  <button type="button" class="btn btn-primary" ng-click="isCollapsed = !isCollapsed">
    Toggle Content
  </button>
</p>
<div uib-collapse="isCollapsed">
  <div class="well">
    This content can be shown or hidden by clicking the button above.
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
    <p>This content is hidden by default and can be revealed by clicking the button. This is useful for showing optional information without cluttering the page.</p>
  </div>
</div>

## Carousel

Carousels cycle through elements like a slideshow.

### Basic Carousel Example

```html
<div uib-carousel="" interval="5000" no-wrap="false">
  <div uib-slide="" index="0">
    <img src="images/slide1.png" alt="Slide 1">
    <div class="carousel-caption">
      <h4>First Slide</h4>
      <p>Description for the first slide.</p>
    </div>
  </div>
  <div uib-slide="" index="1">
    <img src="images/slide2.png" alt="Slide 2">
    <div class="carousel-caption">
      <h4>Second Slide</h4>
      <p>Description for the second slide.</p>
    </div>
  </div>
</div>
```

### Carousel Options

| Attribute | Description | Default |
|-----------|-------------|---------|
| `interval` | Time in milliseconds between slides | `5000` |
| `no-wrap` | Disable continuous cycling | `false` |
| `no-pause` | Disable pausing on hover | `false` |

## Popover

Popovers display additional content on hover or click.

### Basic Popover Example

```html
<button type="button" class="btn btn-default"
        uib-popover="This is the popover content!"
        popover-title="Popover Title"
        popover-trigger="'mouseenter'">
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

| Trigger | Description |
|---------|-------------|
| `'mouseenter'` | Show on hover |
| `'click'` | Toggle on click |
| `'outsideClick'` | Show on click, hide when clicking outside |
| `'focus'` | Show on focus |

### Popover Placement

Use `popover-placement` to control where the popover appears: `top`, `bottom`, `left`, `right`, `auto`.

## Tooltips

For simple text hints, use tooltips instead of popovers:

```html
<button type="button" class="btn btn-default"
        uib-tooltip="This is a tooltip!"
        tooltip-placement="top">
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

## Styling Tips

Add the `sentry-uib` class to your UI Bootstrap components for consistent styling with the Sentry Maven Skin theme:

```html
<div uib-tabset="" class="sentry-uib">
  <!-- tabs content -->
</div>
```

## Best Practices

1. **Keep it simple**: Use UI components to enhance readability, not to add unnecessary complexity.
2. **Accessibility**: Ensure that all interactive elements are keyboard-accessible.
3. **Mobile-friendly**: Test your documentation on mobile devices to ensure components work well on smaller screens.
4. **Fallback content**: Consider users who may have JavaScript disabled.

## More Information

For complete documentation of all available options and components, visit the [Angular UI Bootstrap documentation](https://angular-ui.github.io/bootstrap/).
