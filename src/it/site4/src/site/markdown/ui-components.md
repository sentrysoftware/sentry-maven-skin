# UI Components

This page tests Angular UI Bootstrap components using the simplified Markdown blockquote syntax.

## Tabs

> [!TABS]
> - Tab 1
>
>   **Tab 1 Content**
>
>   This is the content of the first tab.
>
> - Tab 2
>
>   **Tab 2 Content**
>
>   This is the content of the second tab.
>
> - Tab 3
>
>   **Tab 3 Content**
>
>   This is the content of the third tab.

## Accordion

> [!ACCORDION]
> - Section 1
>
>   **Section 1 Details**
>
>   Content for the first accordion section.
>
> - Section 2
>
>   **Section 2 Details**
>
>   Content for the second accordion section.

## Tooltips and Popovers

<button class="btn btn-primary" uib-tooltip="This is a tooltip!" tooltip-placement="top">Hover for Tooltip</button>

<button class="btn btn-info" uib-popover="Popover content here" popover-title="Popover Title" popover-trigger="'click'" popover-placement="right">Click for Popover</button>

## Collapse

> [!COLLAPSIBLE]
> Toggle Content
>
> This content can be collapsed and expanded.

## More Collapsible Sections

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

## More Tabs

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
