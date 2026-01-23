# UI Components Demo

This page demonstrates Angular UI Bootstrap components integrated with the Sentry Maven Skin.

**Important:** Due to Maven Doxia's XHTML parser limitations:
- Custom elements like `<uib-tabset>` are **not supported** (they get stripped)
- Only the attribute syntax with `<div>` elements works: `<div uib-tabset="">`
- All Angular directive attributes must have a value (use `=""` for boolean directives)

## Tabs

Use `<div uib-tabset="">` with nested `<div uib-tab="">` elements:

<div uib-tabset="" class="demo-tabset">
<div uib-tab="" heading="Tab A">
Content of **Tab A**. This uses the attribute syntax with div elements.
</div>
<div uib-tab="" heading="Tab B">
<p>Content of <strong>Tab B</strong>.</p>
</div>
<div uib-tab="" heading="Tab C">
<p>Content of <strong>Tab C</strong>.</p>
</div>
</div>

## Accordion

Use `<div uib-accordion="">` with nested `<div uib-accordion-group="">` elements:

<div uib-accordion="" class="demo-accordion">
<div uib-accordion-group="" heading="Panel A" is-open="true">
<p>Content of Panel A using attribute syntax.</p>
</div>
<div uib-accordion-group="" heading="Panel B">
<p>Content of Panel B.</p>
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
<p>This content can be toggled with the button above.</p>
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
