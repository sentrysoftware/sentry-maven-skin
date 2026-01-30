# UI Components

This page tests Angular UI Bootstrap components.

## Tabs

<div uib-tabset="">
<div uib-tab="" heading="Tab 1">

**Tab 1 Content**

This is the content of the first tab.

</div>
<div uib-tab="" heading="Tab 2">

**Tab 2 Content**

This is the content of the second tab.

</div>
<div uib-tab="" heading="Tab 3">

**Tab 3 Content**

This is the content of the third tab.

</div>
</div>

## Accordion

<div uib-accordion="">
<div uib-accordion-group="" heading="Section 1">

**Section 1 Details**

Content for the first accordion section.

</div>
<div uib-accordion-group="" heading="Section 2">

**Section 2 Details**

Content for the second accordion section.

</div>
</div>

## Tooltips and Popovers

<button class="btn btn-primary" uib-tooltip="This is a tooltip!" tooltip-placement="top">Hover for Tooltip</button>

<button class="btn btn-info" uib-popover="Popover content here" popover-title="Popover Title" popover-trigger="'click'" popover-placement="right">Click for Popover</button>

## Collapse

<button class="btn btn-default" ng-click="showCollapse = !showCollapse">Toggle Content</button>

<div uib-collapse="!showCollapse">
<div class="well">
This content can be collapsed and expanded.
</div>
</div>

## Collapsible Sections (Markdown Syntax)

The following collapsible sections are created using the simplified Markdown blockquote syntax.

> [!COLLAPSIBLE]
> Click to expand this FAQ item
>
> This is the **collapsed content** that will appear when you click the title.
> It supports all Markdown formatting including *italics*, `code`, and [links](index.html).

> [!COLLAPSIBLE]
> Another collapsible section
>
> You can have multiple collapsible sections on the same page.
> Each one gets its own toggle button and animation.

## Tabs (Markdown Syntax)

The following tabs are created using the simplified Markdown blockquote syntax.

> [!TABS active=demoTabs]
> - <span class="fa fa-home"></span> <strong>First</strong> Tab
>
>   This is the content of the **first tab**.
>   It supports all Markdown formatting.
>
> - Second Tab
>
>   Content of the *second* tab with `code` examples.
>
> - Third Tab
>
>   The third tab has a [link](index.html) too.

> [!TABS justified=true]
> - Justified Tab 1
>
>   These tabs fill the entire width.
>
> - Justified Tab 2
>
>   Because `justified=true` is set.
