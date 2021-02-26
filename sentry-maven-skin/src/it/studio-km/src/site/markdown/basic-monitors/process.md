# Process

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

## General Properties

| Property         | Description                                                                                                                                                                                                         |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name     | Name to identify the **Process** instance in TrueSight Operations Management.                                                                                                                                       |
| Internal ID      | ID to be used to store the **Process** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                     |

## Search Properties

At least one of the criteria below must be specified to allow **${project.name}** to find the Linux, UNIX or Windows process(es) to be monitored:

| Search Criterion        | Description                                                                                                                                                                                                                                 |
| ----------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Process Name            | Name of the process to be monitored (Example: java). Depending on the selected option, the process may be a partial match or may have to be exactly the entered process name.                                                               |
| Process Command Line    | Command line that launched the process. A partial match or a regular expression can be used.                                                                                                                                                |
| Process Runs as         | Username the monitored process is running as. To monitor all processes of a selected user, only set the **Process Runs as** field and leave the **Process Name** and **Process Command Line** fields empty.                                 |
| PID File Path           | Some technologies record their PID (process ID) in a pre-defined file. **${project.name}** can read the PID from this file and monitor the corresponding process. If you want to use this search criterion, enter the path of the PID file. |
| Include Child Processes | Enable this option to have **${project.name}** include the child processes of the processes matching the above criteria when calculating the value of the parameters.                                                                       |

For more information about the parameters available and the thresholds set by default, refer to [Studio Process](../X_PROCESS.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
