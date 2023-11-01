# Windows Service

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

For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Service](../X_WINSERVICE.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
