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
