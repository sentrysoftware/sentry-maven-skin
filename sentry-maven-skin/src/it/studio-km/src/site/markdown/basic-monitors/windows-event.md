# Windows Events

The purpose of the **Windows Event Monitor** is to track events posted to the Windows Event Log and generate an alert when a specific event is found.

When the **Windows Event Monitor** is used, **${project.name}** continuously parses the Windows Event Log. As soon as **${project.name}** finds a Windows Event matching the specified criteria (source, type, content, etc.), the **Matching Event Count** parameter is increased by **1** and an alarm is triggered (if configured so).

You have several options for configuring alerts on Windows Events:

* You can trigger an alert for every single event that matches your criteria
* You can trigger an alert only for the first event matching the criteria
* You can specify a timeout after which alerts will be cleared automatically if no other similar event is found
* You can specify another type of events that will automatically *acknowledge* the previous alerts.

To monitor Windows Events, you must configure the properties below:

| Property                                   | Description                                                                                                                                                                                                                                                     |
| ------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name                               | Name to identify the **Windows Event** instance in TrueSight Operations Management.                                                                                                                                                                             |
| Internal ID                                | ID to be used to store the **Windows Event** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                       |
| Collect Schedule                           | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                 |
| Event Log to Parse                         | Name of the Windows event log to be monitored (Example: ```System```). You can use the *Pick an Event Log* button to pick a an Event Log and a Provider from the Event Log list on the selected host.                                                           |
| Event Source (Provider Name)               | Name of the software that logs the event (Example: ```Service Control Manager```).                                                                                                                                                                              |
| Count Events Only with These Event IDs     | *(Optional)* ID(s) of the event(s) for which **${project.name}** will trigger an alert. Use a comma (,) to separate several IDs or a hyphen (-) between the first and the last ID to indicate a range (Example: ```4372,4375,4380-4385```).                     |
| But Exclude Events with These Event IDs    | *(Optional)* ID(s) of the event(s) for which **${project.name}** will **NOT** trigger an alert. Use a comma (,) to separate several IDs or a hyphen (-) between the first and the last ID to indicate a range (Example: ```4372,4375,4380-4385```).             |
| Count Events with Specific Content         | *(Optional)* Content of the event(s) for which **${project.name}** will trigger an alert. Use a string or regular expression to specify the content to look for in the events message (Example:  ```stopped interacting with Windows and was closed```).        |
| Count Events Only with the Selected Level  | *(Optional)* Level of the event(s) for which **${project.name}** will trigger an alert. By default, **Critical**, **Error**, and **Warning** events will trigger an alert.                                                                                      |
| Acknowledge Event After a Timeout          | *(Optional)* Enable this option if you want the alerts to be acknowledged after a certain time and specify the time in minutes (Default: 120 minutes).                                                                                                          |
| Acknowledge on These Event IDs             | *(Optional)* ID(s) of the event(s) for which **${project.name}** will automatically acknowledge the alerts. Use a comma (,) to separate several IDs or a hyphen (-) between the first and the last ID to indicate a range (Example: ```4372,4375,4380-4385```). |
| Acknowledge on Event with Specific Content | *(Optional)* Content of the event(s) for which **${project.name}** will automatically acknowledge the alerts. Use a string or regular expression to specify the content to look for in the events message.                                                      |
| When Acknowledging                         | *(Optional)* Specify the action **${project.name}** will perform when acknowledging an alert. Select **Decrease "Matching Event Count by One"** if you need **${project.name}** to acknowledge each event count to get a close follow-up on the log activity.   |
| Trigger Alert Message for Every Match      | Enable this option to trigger an Alert Message for every matching event, even when the thresholds have already been breached.                                                                                                                                   |

For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Events](../X_WINEVENT.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
