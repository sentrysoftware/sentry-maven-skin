# UI Components Demo

This page demonstrates Angular UI Bootstrap components integrated with the Sentry Maven Skin.

**Important:** Due to Maven Doxia's XHTML parser limitations:
- Custom elements like `<uib-tabset>` are **not supported** (they get stripped)
- Only the attribute syntax with `<div>` elements works: `<div uib-tabset="">`
- All Angular directive attributes must have a value (use `=""` for boolean directives)
- **Headings** inside components should use `<p class="h4">Title</p>` (Bootstrap heading classes) instead of Markdown `${esc.h}${esc.h}${esc.h}${esc.h}` syntax

## Tabs

Use `<div uib-tabset="">` with nested `<div uib-tab="">` elements:

<div uib-tabset="">
<div uib-tab="" heading="Tab A">

<p class="h4">Tab A Title</p>

Content of **Tab A**. This uses Bootstrap heading classes inside the tab.

</div>
<div uib-tab="" heading="Tab B">

<p class="h4">Tab B Title</p>

Content of **Tab B** with a [link](index.html).

</div>
<div uib-tab="" heading="Tab C">

<p class="h4">Tab C Title</p>

Content of **Tab C** with `inline code`.

</div>
</div>

## Accordion

Use `<div uib-accordion="">` with nested `<div uib-accordion-group="">` elements:

<div uib-accordion="">
<div uib-accordion-group="" heading="Panel A" is-open="true">

<p class="h4">Panel A Details</p>

Content of **Panel A** using Bootstrap heading classes inside accordion.

</div>
<div uib-accordion-group="" heading="Panel B">

<p class="h4">Panel B Details</p>

Content of **Panel B** with *emphasis*.

</div>
</div>

## Collapse

Use the `uib-collapse` attribute to toggle content visibility:

<p>
<button type="button" class="btn btn-primary" ng-click="demoCollapse = !demoCollapse">
Toggle Content
</button>
</p>
<div uib-collapse="demoCollapse">
<div class="well">

<p class="h4">Collapsible Content</p>

This content can be **toggled** with the button above.

</div>
</div>

## Tooltips and Popovers

<p>
<button type="button" class="btn btn-default" uib-tooltip="This is a tooltip!" tooltip-placement="top">
Hover for Tooltip
</button>
<button type="button" class="btn btn-info" uib-popover="Popover content here" popover-title="Popover Title" popover-trigger="'mouseenter'" popover-placement="right">
Hover for Popover
</button>
</p>
