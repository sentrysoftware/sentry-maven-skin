keywords: agent,java,http,preload

# Configuring the Agent

> This section explains how to configure the main properties of the PATROL Agent from the **Agent** menu of the **${project.name}** user interface. Detailed information about the PATROL Agent properties itself is available from the BMC Documentation, see the [Related Topic](#Related_Topics) section.

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

![Monitoring Studio X - Architecture Diagram](../images/MS_X_Architecture_Diagram-subdir.png)

**${project.name}** allows you to manage the main configuration properties of the PATROL Agent directly from its Web interface, that is:

- Defining a default user account
- Managing user access rights
- Preloading KMs
- Configuring data retention periods
- Managing TrueSight Integration properties
- Configuring advanced settings

Once the PATROL Agent is properly configured, you need to review and customize the settings for:

- Java
- HTTP server

## Defining a Default User Account

You can configure the **Default Account** that the agent uses for parameter collects, recovery actions, and discovery procedures. By default, this account is the one used to install the PATROL Agent.

On Windows systems, if the **Run As LOCALSYSTEM** option is activated, the PATROL Agent uses the _LocalSystem_ account, that is the predefined account on the server running the PATROL Agent which grants extensive privileges. This account does not have a password.

## Managing User Access Rights (Access Control List)

You can restrict user access to a PATROL Agent by creating an **Access Control List** (ACL) in which you can specify the allowed or denied users, hosts that are authorized or denied access to the agent and the granted access modes.

An Access Control List consists in a comma-separated list of entries of the following format: ```<username>/<hostname>/mode```.

## Preloading KMs

The **Preloaded KMs** section lists all the KMs that are automatically loaded at startup by the PATROL Agent. Preloaded KMs run as long as the PATROL Agent runs.

<div class="alert alert-info"><i class="icon-info-sign"></i><strong>Tip: </strong> Preload the KMs that you regularly use and for which you want to maintain a continuous data collection history.</div>

If you want the PATROL Agent to only execute the KMs configured to be preloaded, set the **Prevent Dynamic Loading of Other KMs** to **PREVENT** to keep a BMC PATROL Classic Console from loading unlisted KMs.

## Configuring Data Retention Periods

The **Parameters Retention** option sets the number of days that collected parameter values are retained in the history database. After this retention period has expired, the retained information is deleted.

The **Events Retention** option controls the maximum size in bytes of the event log. The PATROL Agent's local event log is a circular file, therefore, when the file fills up, the agent deletes older events to accommodate new ones.

## Managing TrueSight Integration Properties

This section describes how you can add Integration Services to Infrastructure Management Servers and manage their properties.

### Specifying the Integration Service Path

An Integration Service string needs to be provided to initiate the PATROL Agent connection to the **Integration Service**. You can specify multiple Integration Service by using a comma as a separator between the Integration Service strings. If the PATROL Agent fails to connect to the first Integration Service, then it uses the next one.

### Defining Tags

You can assign a **Tag** to PATROL Agents to easily apply a configuration to all the PATROL Agents that share the same tag. The format of each tag is ```tagName:tagDescription```. If the tag description contains spaces, enclose the description within double quotes. You can provide multiple tags separated by commas.

### Managing Events Routing

Several options are available to define how PATROL events are routed to TrueSight Event Management:

| Options                                          |Description  |
| ------------------------------------------------ | ------------------ |
| **TrueSight Event Management Cells**             | Specifies where the events generated by the BMC PATROL Agents must be routed. The primary host is used by default. A secondary host (comma-separated), can be added to create a cluster configuration.     |
| **Encryption Key**                               | Defines the encryption key used to send events to TrueSight Event Management.                                                                                                                              |
| **Event Format Container**                       | String that is added to the name of the PATROL generated events to identify the format of the event that is sent to a TrueSight Event Management Cell. By default, the string is ```BiiP3```.              |
| **Route Events Through the Integration Service** | Prevents events to be sent directly to the TrueSight Event Management cell.                                                                                                                                |
| **Send the STD/41 Event to TrueSight**           | By default, STD/41 events are not sent to TrueSight. However, some KMs, including **${project.name}**, leverage this class of event to provide useful information, such as threshold breaches, for example.|

### Configuring Advanced Settings

**${project.name}** enables you to easily configure PATROL Agent key integration variables.

When you configure **${project.name}** from the Web interface, you may want to block any updates from CMA policies that could interfere or supersede your settings. To prevent changes from CMA policies on the PATROL Agent, simply set the **Disable Configuration Updates from TrueSight CMA Policies** to **YES**.

Additionally, you can fine-tune the integration with BMC TrueSight with the following configuration variables:

- Publish Hostname
- Integration Service Connection Timeout
- Retries
- Retries per Integration Services
- Wait Before Retries
- Text Parameter Maximum Length

<div class="alert alert-danger"><i class="icon-remove-sign"></i><strong>Warning: </strong>Most of the Agent settings require a restart of the PATROL Agent to take effect.</div>

## Configuring JAVA

To operate properly, **${project.name}** requires that Java 1.8.00 or higher and a Java Runtime Environment (JRE) are installed on the same system that runs the PATROL Agent.

The **Java Settings** page displays information about the JRE currently in use and a list of all the JREs installed on the monitored system. You can also configure the following options to fine-tune the Java properties:

| Options                                  |Description                                                                                                                                                                                                                                                                                                                                              |
| ---------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Automatically Select Appropriate JRE** | To let **${project.name}** automatically find and use the most compatible JRE at the initial discovery that occurs when the PATROL Agent and **${project.name}** start.                                                                                                                                                                                 |
| **Execute the JVM as an Alternate User** | By default, the Java process is launched with the same credentials as the PATROL Agent. If the PATROL Agent's default account does not have sufficient privileges to perform the operations required by the Java Collection Hub, another user account can be provided.                                                                                  |
| **Java Minimum Heap Size**               | To customize the minimum size (in MB) of the **Java Hub Heap Memory** according to your environment requirements. By default, the minimum size is set to 128 MB.                                                                                                                                                                                        |
| **Java Maximum Heap Size**               | To customize the maximum size (in MB) of the **Java Hub Heap Memory** according to your environment requirements. By default, the maximum size is set to 512 MB.                                                                                                                                                                                        |
| **Disable JRE Pre-Validation**           | By default, **${project.name}** checks that the selected JRE meets the requirements. However, you can disable this option to bypass the compatibility check. Use this option only if you are certain that the provided JRE is compatible even if the compatibility check fails. An unsuitable JRE will prevent **${project.name}** to operate properly. |
| **JVM Arguments**                        | To provide arbitrary arguments to the java -jar ... command line that **${project.name}** uses to launch the Java Collection Hub in the Command line options field.                                                                                                                                                                                     |

The JVM needs to be restarted for your new settings to be taken in account.

## Configuring the HTTP Server

The **HTTP Server** page enables you to configure the connection properties for your HTTP Server. The HTTP Server configuration applies to the **${project.name}** Web Interface as well as the REST API.

If you need to prevent access to the Web UI and REST API provided by **${project.name}**, you can disable the HTTP server entirely.

Define the HTTP Server authentication properties to control the access to the PATROL Agent, the REST API and the **${project.name}** Web interface:

| Properties                                                                           | Description                                                                                                                                                                                |
| ------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Must Be Listed in the PATROL Agent's ACL**                                         | Restricts access to users allowed by the PATROL Agent's ACL.                                                                                                                               |
| **Only These Users Can Sign In** <br /> **Only Members of These Groups Can Sign In** | Restricts access to specific users or user groups. User names and groups must be comma-separated. Leave both lists empty if you do not want to restrict access to specific users or groups.|
| **Always Allow the PATROL Agent's Default Account to Sign In**                       | Grants access to the PATROL Agent's default account regardless of any other user access restrictions. _This option supersedes other access restrictions settings._                         |
| **Allow Anonymous Access (Read-Only)**                                               | Grants read-only access to user without prompting them for a user name or password. Use this option in trusted environments only.                                                          |

Finally, configure the HTTP Server connection properties:

| Properties                        | Description                                                                                                                                                                                                                                                                                                                                     |
| --------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **HTTPS Port**                    | Defines the port number of the HTTP server. Using "+" or "-", you can specify a relative port number based on the PATROL Agent port number to avoid having to change the HTTPS port number if the PATROL Agent port changes. By default, this value is set to +262 and sets the HTTPS port to ```PATROL Agent Port: 3181 + 262```, that is 3443.|
| **Bind to IP Address**            | Assigns a static IP address to the HTTP server.                                                                                                                                                                                                                                                                                                 |
| **Number of HTTP Server Threads** | Defines the maximum number of simultaneous transactions that the HTTP Server can handle. By default, this option is set to 20 threads.                                                                                                                                                                                                          |
| **Session Timeout**               | Specifies the duration that a session can remain idle before the server terminates it automatically and the user is required to authenticate again.                                                                                                                                                                                             |

The JVM needs to be restarted for your new settings to be taken in account.

## Viewing the PATROL Agent Configuration Information

The **Information** page, as its name suggests, provides any relevant information about the PATROL Agent such as its version, the port it is running on, the Operating System, etc. From this page, you can also preview and download the PATROL Agent log file. Messages sent to the error log include both error messages that result from a failed action, and informational messages that result from successful action.

## Related Topics

- [Defining Access Control Lists](//docs.bmc.com/docs/PATROLAgent/113/defining-access-control-lists-766670424.html)
- [Preloading KMs on the PATROL Agent](//docs.bmc.com/docs/PATROLAgent/113/preloading-kms-on-the-patrol-agent-766670451.html)
- [Setting the number of days that history is retained in the history database](//docs.bmc.com/docs/PATROLAgent/113/setting-the-number-of-days-that-history-is-retained-in-the-history-database-766670474.html)
- [Setting up the event log file and size](//docs.bmc.com/docs/PATROLAgent/113/setting-up-the-event-log-file-and-size-766670479.html)
- [Specifying the Integration Service path](//docs.bmc.com/docs/PATROLAgent/113/specifying-the-integration-service-path-766670245.html)
- [Configuring TrueSight Infrastructure Management Event Manager Integration](//docs.bmc.com/docs/PATROLAgent/113/configuring-truesight-event-manager-integration-766670201.html)
- [Integration Variables](//docs.bmc.com/docs/PATROLAgent/113/integration-variables-766670137.html)
