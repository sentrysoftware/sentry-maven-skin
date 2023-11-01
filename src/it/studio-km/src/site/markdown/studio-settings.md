# Configuring Monitoring Studio

>  This section explains how to configure ${project.name} global settings.

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Alert Messages

The settings of alert notification delivery options and default messages apply to all the Monitors running on the Agent. Refer to the [Alert Messages and Actions](./alerts.html#Alert_Messages) section for detailed information.

## KM Engine

You can configure and fine tune some global settings that will determine **${project.name}** default behavior. These properties will apply to any component monitored by the Agent when relevant.

### General Settings

| Property                                                        | Description                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| --------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Current Configuration Mode                                      | Indicates the mode in which **${project.name}** currently operates.<br></br> **${project.name}** can either be managed from the **${project.name}** Web interface (Classic) or BMC TrueSight Operations Management (TrueSight CMA).                                                                                                                                                                                                             |
| Force Classic Configuration Mode                                | Forces the configuration mode to the **${project.name}** Web Interface mode (Classic) and prevents the solution from applying any **${project.name}** configuration received from BMC TrueSight Central Monitoring Administration (CMA).                                                                                                                                                                                                        |
| Automatically Clear Alerts on _Collection Error Count After_... | The _Collection Error Count_ parameter reports runtime problems encountered by the solution. Such problems, should they occur, are expected to happen repeatedly. For example: the solution is unable to find a suitable Java JRE, or SNMP is disabled on the local Agent and a Template is trying to perform SNMP v1 queries, etc. By clearing alerts after a certain time without errors, you only keep alerts related to permanent problems. |
| Place Hosts Under the **${project.name}** Main Icon             | Organizes the list of hosts in the treeview of the **${project.name}** Web interface **Console** menu:<br></br> <li>Turn **ON** to list the hosts ```(<display Name> or <Host Name>)``` under the **${project.name}** icon.</li> <li>Turn **OFF** to list the hosts ```(<display Name> or <Host Name>)``` under the PATROL Agent icon.</li>                                                                                                     |

### Tuning Settings

| Property                                           | Description                                                                                                                                                                                                  |
| -------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| "Discovery" Interval                               | The frequency at which configuration variables are read and applied. Default is 60 minutes. <br></br>_Note: Updating the configuration through CMA or the Web Interface automatically executes a discovery_. |
| Maximum Number of "Discovery" Threads              | The maximum number of discovery threads allowed per Host. Default is 50 threads.                                                                                                                             |
| Maximum Number of Concurrent WMI Queries           | The maximum number of concurrent WMI queries allowed. Default is 25 processes.                                                                                                                               |
| Maximum Number of Concurrent SSH Sessions per Host | The maximum number of concurrent SSH connections allowed per Host. Default is 10 connections.                                                                                                                |
| Maximum Size of Text Parameters                    | The maximum size (in bytes) of text parameters. Default is 1048576 bytes (1 MB).                                                                                                                             |
| Maximum In-memory Buffer Size                      | The maximum size (in bytes) of data stored in the PATROL namespace (memory) for one Monitor collection result. Default is 10000000 bytes. (Oversize output will be stored in a temporary file).              |

### HTTP Proxy Settings

You need to enable the **HTTP PROXY FOR HTTP REQUEST MONITORS** property to access the HTTP Proxy settings. Once configured click on **TEST** to verify the settings.

| Property               | Description                                                                                              |
| ---------------------- | -------------------------------------------------------------------------------------------------------- |
| Hostname or IP Address | The hostname or IP address of the proxy server used to connect to the **${project.name}** Web Interface. |
| Port                   | The port number used to access the proxy server. Default is 3128.                                        |
| Username               | The username used for proxy authentication.                                                              |
| Password               | The password used for proxy authentication.                                                              |


## Main Parameters and Alerts

You can enable or disable the parameters attached to the main **${project.name}** instance to report the status and activity of the KM. Simply turn **ON** any parameter and set its level of alert and associated default message (refer to [Alert Messages and Actions](./alerts.html) for additional information). Values for turned **OFF** parameters are not collected.

| Parameter              | Description                                                                                                                                              |
|------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Collection Error Count | Number of errors that prevent **${project.name}** from operating properly. This parameter is cumulative, new errors increase the value of the parameter. |
| Debug Status           | Indicates whether the debug mode is enabled or not. Refer to [Enabling the Debug Mode](./tools.html#Debug) for additional information.                   |
| Discovery Status       | Indicates if the global discovery is currently running.                                                                                                  |
| Discovery Time         | Time taken to execute the global discovery.                                                                                                              |
| Host Count             | Total number of Hosts managed by **${project.name}**.                                                                                                    |
| Monitor Count          | Total number of Monitors managed by **${project.name}**.                                                                                                 |

## Download .CFG

You can export **${project.name}** global settings as a pconfig file (.CFG) that can be imported into a TrueSight CMA policy. All the Agents using the policy will therefore share the same **Monitoring Studio Global Settings**. Refer to [Config Hacker](./tools.html#Config_Hacker) for additional information.


## Reset to Factory Settings

You can reset **${project.name}** global settings back to the default settings by clicking on **Reset to Factory Settings**. This will reset all **${project.name}** global settings, including **${project.name}** Java and HTTP settings. *Templates*, *Monitors* and *Hosts* will however not be deleted.
