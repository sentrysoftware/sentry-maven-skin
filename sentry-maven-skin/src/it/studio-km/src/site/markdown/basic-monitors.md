# Basic Monitors

> This section describes the basic monitors (File System, Process, SNMP Traps, Windows Event, Windows Performance Counter, Windows Service).

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## File System

The purpose of the **File System Monitor** is to monitor a file system that is critical to the monitored system or application. Typically, a full file system will prevent an application from working properly.

To monitor a file system, you must configure the properties below:

| Property         | Description                                                                                                                                                                                                             |
| ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| File System Path | Enter the path of the file system to be monitored (Example: ```C:\```). You can use the *Pick a File System* button to select the file system from a list of file systems on a selected host.                           |
| Display Name     | Name to identify the **File System** instance in TrueSight Operations Management.                                                                                                                                       |
| Internal ID      | ID to be used to store the **File System** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the file system path provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                         |

For more information about the parameters available and the thresholds set by default, refer to [Studio File System](./X_FILESYSTEM.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Process

The purpose of the **Process Monitor** is to monitor one or several processes. As there is no practical way to uniquely identify a process (its PID changes every time the process starts), the **Process Monitor** lets you specify several criteria to identify the process(es) you need to monitor:

* the process name
* its command line
* its user ID.

**${project.name}** will search for all processes that match the specified criteria and will report:

* the number of matching processes (if zero, it means the monitored process is not running)
* the total processor time utilization of the matching processes
* the total memory consumption of the matching processes
* ...and many more metrics.

Alternatively, you can specify the path to a *PID File*, which contains the PID of the process to monitor. **${project.name}** will read the file to retrieve the process PID.

### General Properties

| Property         | Description                                                                                                                                                                                                         |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name     | Name to identify the **Process** instance in TrueSight Operations Management.                                                                                                                                       |
| Internal ID      | ID to be used to store the **Process** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                     |

### Search Properties

At least one of the criteria below must be specified to allow **${project.name}** to find the Linux, UNIX or Windows process(es) to be monitored:

| Search Criterion        | Description                                                                                                                                                                                                                                 |
| ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Process Name            | Name of the process to be monitored (Example: java). Depending on the selected option, the process may be a partial match or may have to be exactly the entered process name.                                                               |
| Process Command Line    | Command line that launched the process. A partial match or a regular expression can be used.                                                                                                                                                |
| Process Runs as         | Username the monitored process is running as. To monitor all processes of a selected user, only set the **Process Runs as** field and leave the **Process Name** and **Process Command Line** fields empty.                                 |
| PID File Path           | Some technologies record their PID (process ID) in a pre-defined file. **${project.name}** can read the PID from this file and monitor the corresponding process. If you want to use this search criterion, enter the path of the PID file. |
| Include Child Processes | Enable this option to have **${project.name}** include the child processes of the processes matching the above criteria when calculating the value of the parameters.                                                                       |

For more information about the parameters available and the thresholds set by default, refer to [Studio Process](./X_PROCESS.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## SNMP Trap

The purpose of the **SNMP Trap Monitor** is to listen for SNMP traps and enable rapid recovery actions depending on the traps received, thereby ensuring optimal functioning of applications or devices that send SNMP traps.

The SNMP Agent emitting the traps should be configured to send them to the PATROL Agent where **${project.name}** is installed and running. Otherwise, the solution will not be able to receive the SNMP traps as it can only listen for the SNMP traps on the localhost. No other *Trap Listener* should be running at the same time. If you are unsure about the characteristics of the SNMP trap you want to detect, you may need to use a *SNMP MIB Browser* tool to understand the details of each trap.

If SNMP is deactivated on the PATROL Agent, **${project.name}** will trigger an alert on the *Collection Error Count* parameter if one or several SNMP Monitors have been created.

<div class="alert alert-danger"><i class="icon-remove-sign"></i><strong>Warning: </strong>Only SNMP version 1 is supported for SNMP trap listening. SNMP v2c and v3 traps are not supported.</div>

To listen for SNMP traps,  you must configure the properties below in order to define the SNMP Trap that is expected. **${project.name}** will react to the SNMP Trap received only if this information is found within the SNMP Trap. All other SNMP Traps will be ignored:

| Property                 | Description                                                                                                                                                                                                                                                                |
| ------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Enterprise OID           | Enterprise Object Identifier of the SNMP Trap. (Regular expressions are supported, for example: 1.3.6.1.4.44.141.32.*)                                                                                                                                                     |
| Trap Number              | *(Optional)* Number identifying the SNMP Trap.                                                                                                                                                                                                                             |
| Display Name             | Name to identify the **SNMP Trap** instance in TrueSight Operations Management.                                                                                                                                                                                            |
| Internal ID              | ID to be used to store the **SNMP Trap** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Template** and **Enterprise OID** names provided but can be edited if needed.                             |
| Collect Schedule         | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                            |
| Varbind1 OID             | First attached variable OID that should be contained within the SNMP trap.                                                                                                                                                                                                 |
| Varbind1 Value           | Value that should be found in the OID content.                                                                                                                                                                                                                             |
| Varbind2 OID             | Second attached variable OID that should be contained within the SNMP trap.                                                                                                                                                                                                |
| Varbind2 Value           | Value that should be found in the OID content.                                                                                                                                                                                                                             |
| Acknowledge on Timeout   | If enabled, acknowledges a received matching trap after the number of provided minutes                                                                                                                                                                                     |
| Acknowledge After        | Number of minutes after which a matching SNMP trap is automatically acknowledged (Default: 120 minutes).                                                                                                                                                                   |
| Acknowledge on SNMP Trap | If enabled, acknowledges a received matching trap when a trap matching the acknowledging information is received.                                                                                                                                                          |
| When Acknowledging       | *(Optional)* Specifies the action **${project.name}** will perform when acknowledging an alert: *Reset “Matching Trap Count”* to clear all previous alerts and reset the parameter value to zero or *Decrease “Matching Trap Count”* to decrease the parameter value by 1. |
| Execute Alert Actions    | Enable this option to trigger an Alert Action for every matching trap, even when the thresholds have already been breached.                                                                                                                                                |

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong> The SNMP trap listening port is actually a PATROL Agent configuration variable: <i>/snmp/trap_port=162</i>. You can set this variable from the <strong>Tools > Config Hacker</strong> page of <strong>${project.name}</strong> to whatever port you want the solution to listen on.</div>


<div class="alert alert-danger"><i class="icon-remove-sign"></i><strong>Warning: </strong>Make sure that only one device is listening for traps on the localhost or <strong>${project.name}</strong> will not be able to run the SNMP Trap listener.</div>


For more information about the parameters available and the thresholds set by default, refer to [Studio SNMP Trap](./X_SNMPTRAP.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Windows Events

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


For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Events](./X_WINEVENT.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Windows Performance Counter

The purpose of the **Windows Performance Counter Monitor** is to simply monitor Windows Performance Counters.

**${project.name}** uses counters to collect performance data. Each counter is uniquely identified through its **Performance Object** and **Instance**. Once collected, the performance data is available in TrueSight with the **Value** parameter.

To monitor Windows Performance Counters, you must configure the properties below:


| Property            | Description                                                                                                                                                                                                                                                                                                                                                     |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name        | Name to identify the **Windows Performance Counter** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                               |
| Internal ID         | ID to be used to store the **Windows Performance Counter** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                         |
| Collect Schedule    | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                                 |
| Performance Object  | Name of the Windows performance object that contains the counter to be monitored (Example: ``` Win32_PerfRawData_PerfOS_Processor```). You can use the *Pick a Performance Counter* button to pick a Performance Object, Instance and Counter from the list of existing Performance Counters on the selected host.                                              |
| Instance Name       | *(Optional)* Name of the instance to be monitored. This name is used to distinguish between multiple performance objects of the same type on a single device (Example: ```0```).                                                                                                                                                                                |
| Performance Counter | Name of the performance counter to be monitored (Example: ```PercentUserTime```).                                                                                                                                                                                                                                                                               |
| Rescaling           | *(Optional)*  How **${project.name}** will rescale the extracted value to have a more readable graph in TrueSight (Example: To display in megabytes values that are extracted in bytes, divide the values by 1048576 (1024*1024)). Leave this option set to **No Rescaling (default)** if you wish the solution to report on the performance counter raw value. |

For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Performance Counter](./X_WINPERF.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Windows Service

<div class="alert alert-info">This Monitor is only available to agents running on Windows systems, and on monitored Windows hosts (both the agent and the monitored host must be running on Windows).</div>

The purpose of the **Windows Services Monitor** is to:

* monitor the **State** of any Windows service
* interpret this **State**  to determine the value of its **Status** parameter (0 is for *OK*, 1 for *Suspicious* and 2 for *Failed*)
* and typically generate an alert if its **Status** is **Suspicious** or **Failed**.

By default, **${project.name}** interprets the **Service State** as follows:

| Service State    | Status Parameter |
| ---------------- | ---------------- |
| Running          | OK               |
| Paused           | Suspicious       |
| Stopped          | Failed           |
| Pending Start    | Suspicious       |
| Pending Continue | Suspicious       |
| Pending Pause    | Suspicious       |
| Pending Stop     | Suspicious       |
| Not Installed    | Failed           |
| Unknown          | Suspicious       |

And by default, **${project.name}** triggers a *WARNING* when **Status** is 1 (*Suspicious*) and an *ALARM* when **Status** is 2 (*Failed*).

To monitor a Windows service, you must configure the properties below:

| Property         | Description                                                                                                                                                                                                                                                      |
| ---------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Windows Service  | Shortname of the Windows service to be monitored as stored in the Windows registry (Example: *MpsSvc* for the Windows Firewall service). You can use the *Pick a Windows Service* button to pick a service from the list of Windows services on a selected host. |
| Display Name     | Name to identify the **Windows Service** instance in TrueSight Operations Management.                                                                                                                                                                            |
| Internal ID      | ID to be used to store the **Windows Service** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the service name provided but can be edited if needed.                                          |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                  |
| Service State    | *(Optional)* For each possible service state, indicate the value of the **Status** parameter. **${project.name}** will generate a **Warning** when the **Status** is **Suspicious**; an **Alarm** when the **Status** is **Failed**.                             |

For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Service](./X_WINSERVICE.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.
