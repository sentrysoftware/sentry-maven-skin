# UI Components Demo

This page demonstrates Angular UI Bootstrap components integrated with the Sentry Maven Skin.

**Important:**
- Use UIB elements like `<uib-tabset>` and `<uib-accordion>` for Angular UI Bootstrap.
- Use `<uib-tab-heading>` and `<uib-accordion-heading>` to allow rich HTML in headings.
- All Angular directive attributes must have a value (use `=""` for boolean directives).
- **Headings** inside components should use `<p class="h4">Title</p>` (Bootstrap heading classes) instead of Markdown `${esc.h}${esc.h}${esc.h}${esc.h}` syntax.

## Callouts

GitHub-style callouts for notes, tips, warnings, etc.

> [!NOTE]
> This is a note callout with useful information.

> [!TIP]
> This is a helpful tip for users.

> [!IMPORTANT]
> Key information users need to know.

> [!WARNING]
> Urgent info that needs immediate attention.

> [!CAUTION]
> Advises about risks or negative outcomes.

## Tabs

Use `<uib-tabset>` with nested `<uib-tab>` elements:

> [!TABS active=demoTabs]
> - <span class="fa fa-home"></span> Tab A
>
>   <p class="h4">Tab A Title</p>
>
>   Content of **Tab A**. This uses Bootstrap heading classes inside the tab.
>
> - Tab B
>
>   <p class="h4">Tab B Title</p>
>
>   Content of **Tab B** with a [link](index.html).
>
> - Tab C
>
>   <p class="h4">Tab C Title</p>
>
>   Content of **Tab C** with `inline code`.

## Accordion

### Markdown Syntax

> [!ACCORDION]
> - Panel A
>
>   Content of **Panel A** using the simple Markdown syntax.
>
> - Panel B
>
>   Content of **Panel B** with *emphasis*.

### HTML Syntax

Use `<uib-accordion>` with nested `<uib-accordion-group>` elements:

<uib-accordion>
<uib-accordion-group is-open="true">

<uib-accordion-heading>
Panel A
</uib-accordion-heading>

<p class="h4">Panel A Details</p>

Content of **Panel A** using Bootstrap heading classes inside accordion.

</uib-accordion-group>
<uib-accordion-group>

<uib-accordion-heading>
Panel B
</uib-accordion-heading>

<p class="h4">Panel B Details</p>

Content of **Panel B** with *emphasis*.

</uib-accordion-group>
</uib-accordion>

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

## Carousel

Use a blockquote with the `[!CAROUSEL]` marker followed by a list of images:

> [!CAROUSEL]
> * ![Studio Events Screenshot](./images/Events.png)
> * ![Console Overview Screenshot](./images/Console_Overview.png)
> * ![Internal Architecture Diagram](./images/Internal_Architecture.png)

## Tooltips and Popovers

<p>
<button type="button" class="btn btn-default" uib-tooltip="This is a tooltip!" tooltip-placement="top">
Hover for Tooltip
</button>
<button type="button" class="btn btn-info" uib-popover="Popover content here" popover-title="Popover Title" popover-trigger="'mouseenter'" popover-placement="right">
Hover for Popover
</button>
</p>
