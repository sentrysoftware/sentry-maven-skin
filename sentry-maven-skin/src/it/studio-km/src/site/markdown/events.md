title: Dealing with Events
keywords: event, testevent, blank space
description: Monitoring Studio X allows the operators and administrators to manage the events in the PATROL Agent.
author: The "Proud" People
date: 2021-01-01

# Managing Events

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

PATROL Events are an essential part of the monitoring in BMC TrueSight. Everything that happens on the PATROL Agent is recorded as an _event_:

* Loading of a KM
* Connection of a console
* Authentication of a user
* Start and stop of the PATROL Agent
* Breach of an alert threshold
* Parameter, instance or class that goes into ALARM or WARN state
* Parameter, instance or class that goes back to normal state
* Various errors and problems

Most of these events are forwarded to BMC TrueSight Operations Management, which consolidates and correlates events from multiple sources.

In the **Events** page, **${project.name}** allows you to visualize all events that are triggered by the PATROL Agent. This can be particularly useful when you are unsure of why certain events were not reported in BMC TrueSight.

![Events Page](./images/Events.png)

Events are listed in a table, always from the most recent to the oldest. You can scroll down to load additional older events. Only the events matching the criteria specified in the *Filter* pane are displayed.

The time displayed in the list uses time relative to your browser, taking into account the time zone difference between the PATROL Agent and your browser, and also the clock difference.

The **REFRESH** button checks whether newer events have been triggered on the Agent. By default, the list of events is updated every 5 seconds. Use the **AUTO/OFF** toggle to disable the automatic refresh of the page.

With the **DOWNLOAD CSV** button, you can download a CSV file with the content of the list of events. Note that the time displayed in the CSV will be the time as reported by the PATROL Agent, with no time zone and clock adjustment.


## Filtering Events

By default, all events are displayed except for *"State Change"* and *"Response"* events.

With the **FILTER** button, you can modify the criteria of the filter:

| Filter Criteria | Description   |
| ---------------------- | ------ |
| Event Description| The actual content of the event |
| Event Origin| A specific string representing the object that triggered the event, in general in the form of ```CLASS.instance.parameter```|
| Event Class  | Named *PATROL Class* in BMC TrueSight, it is typically ```11``` for a threshold breach, ```Studio``` for a **${project.name}** event, etc.  |
| Type  | State Change, Response, Information, Warning, Alarm or Error  |
| Status  | Open, Acknowledged, Closed, Escalated, or Deleted  |

The **Event Description**, **Origin** and **Class** criteria allow you to specify a regular expression and to search for events that do not match the criteria. Use the corresponding buttons. Note that technical constraints  prevent filtering events by the hostname they are related to.

The list of events is updated immediately when you change the filter criteria. Use the **RESET** button to clear the criteria.

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong>The specified filter is stored in your browser and will be loaded by default the next time to visit this page.</div>

## Interacting with the Events

From the list of events, you can directly update the status of each event:

* Acknowledge
* Close
* Delete

<div class="alert alert-danger"><i class="icon-remove-sign"></i><strong>Warning: </strong>You cannot <i>un-delete</i>, <i>un-close</i> or <i>un-acknowledge</i> an event.</div>

On larger screens, all details about the selected event are displayed on the right. On smaller screens, you can click on the description of an event to display its details.

![Event Details](./images/Events_Details.png)

From the detailed view, you can perform the following operations:

* change the status of the event
* view the entire content of the event
* copy the event content to the clipboard
* add a comment to the *Diary* of the event


## Keyboard Shortcuts (special)

You can navigate the list of events with the below keyboard shortcuts:

* <kbd>Up</kbd> and <kbd>Down</kbd>
* <kbd>Enter</kbd> to display the details of an event
* <kbd>A</kbd> to acknowledge the selected event
* <kbd>C</kbd> to close the selected event
* <kbd>D</kbd> or <kbd>Del</kbd> to delete the selected event
* <kbd>F3</kbd> to change the filter criteria
* <kbd>Ctrl</kbd>-<kbd>Enter</kbd> to submit the comment
