# General Concepts

>  This section provides general information about the important concepts of ${project.name}.

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Product Hierarchy

The **${project.name}** hierarchy can be represented as follows:

![**${project.name}** hierarchy](./images/product-hierarchy.png)

### Hosts

Hosts provide information about the target server where the technology to be monitored is running (hostname, system type, credentials, SNMP information, host availability check information, etc.). Hosts are at the highest level of the hierarchy and contain none, one or several Templates.

Refer to the section [Hosts and Templates](./hosts-templates.html) to know how to define hosts.

### Templates

**Templates** correspond to the technology you need to monitor (application, server, device, etc.). Arranged under the Host instance, they contain the list of Monitors and credentials required to assess the health and performance of the targeted technology or application. Templates can be attached to none, one or several hosts.

Refer to the section [Hosts and Templates](./hosts-templates.html) to know how to create templates and apply them to hosts.

### Monitors

Monitor instances are arranged under the Template instance and correspond to the monitoring action(s) to be performed by **${project.name}** such as:

* process monitoring
* file parsing
* HTTP request
* database query
* SNMP polling
* folder listing
* WBEM query
* WMI query
* Command line execution
* etc.

**Monitors** are configured when creating Templates. They can refer to credentials, and user-defined, host-level, or template-level macros. They can be referred to as "monitored objects".

Refer to [Basic Monitors](./basic-monitors.html), [Monitors with Content](./content-monitors.html), [Content Parsing Monitors](./content-parsing-monitors.html) and [Dynamic Instances](./dynamic-instances.html) to learn more about the Monitors available and how to configure them.

## Credentials Management

Credentials are **_declared_** at the **Template** level:

![Declaring credentials at the Template level](./images/required-credentials-template.png)

and **_set_** at the **Host** level.

![Setting credentials at the Host level](./images/required-credentials-host.png)


A default **Username** and **Password** can however be specified at the **Template** level. They will be used if no **Username** and **Password** are set at the **Host** level.

All credentials are stored in the PATROL configuration under the relevant **Template** and **Host** object in a comma-separated list.

* The **credentialsList** for **Templates** will contain comma-delimited list of credentials in the following format:<br>
```<credentials-label>;<default-username>;<default-password-or-passphrase>;<default-private-key-file>```</br>

* When set, the **credentialsList** for **Hosts** will be prefixed by ```<template-configuration-id>:``` as follows: ```<template-configuration-id>:<credentials-label>;<username>;<password-or-passphrase>;<private-key-file>```

Credentials can be removed only at the **Template** level.


## Alert Messages and Alert Actions

### Alert Messages

When a problem is detected by **${project.name}**, the following alert messaging options are available:

* Annotation
* PATROL Event
* Command Line
* PSL Script
* Email
* Write to a Log File
* Send an SNMP Trap

Enabled in the **Studio > Studio Settings** page, their content can be customized at the Monitor level by using Macros.

Default messages are also configured for Monitors and parameters that do not have specific Alert Messages. They can be configured in the **Default Message Content** section of the **Studio > Studio Settings** page.

The list of alert messages to send is stored under the main configuration path in ```/SENTRY/X/messageTypeList```

Refer to [Alert Messages and Actions](./alerts.html) to know how to enable alert messages and customize their content.

### Alert Actions

Alert Actions allow you to automatically refresh a Monitor of a same Template when a problem is detected. The Monitors to be refreshed are special diagnosis or troubleshooting Monitors configured to run manually (i.e. their polling interval is set to *manual*).  This feature allows you to decide what to do when something goes wrong and get feedback about the actions taken. You can for example decide that a *regular* Monitor triggers a *troubleshooting* Monitor which triggers a *repairing* Monitor and so on.

The Monitors to be refreshed are configured at the Monitor level, for each range of each parameter.

Refer to [Alert Messages and Actions](./alerts.html) to learn more about alert actions.
