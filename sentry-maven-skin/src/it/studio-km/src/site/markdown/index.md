# Overview

<!-- MACRO{toc|fromDepth=1|toDepth=1|id=toc} -->

[This is a nice test image from the Web which must not trigger any error](https://cds.cern.ch/images/CERN-HOMEWEB-PHO-2019-004-1/file?size=large)

## What is ${project.name}?

**${project.name}** is a monitoring solution that enables you to monitor almost any technology (application, server, device, etc.) for which there is no out-of-the-box monitoring solution. **${project.name}** is designed as a regular Knowledge Module (KM) with a Web interface that brings the benefits of a standard solution: maintenance, updates, patches, etc. to further respond to growing technological needs for specific business-critical technologies.

Compatible with Linux/UNIX and Windows, **${project.name}** can rapidly be deployed to monitor anything, anywhere **locally** and **remotely** without coding.


## What's New with ${project.name}?

While **${project.name}** remains a regular KM that can be installed through BMC Thorium or BMC TrueSight CMA, it provides a major new feature: its own innovative Web interface to:

  - configure **${project.name}** to monitor any technology, application, system or device, with easy-to-use Monitors for CLI, HTTP, SNMP, WBEM, WMI, database, etc
  - try out your monitoring configuration live with the **Dry Run** tool and verify beforehand that it meets your needs and expectations
  - visualize monitored objects, instances, parameters and events directly on the PATROL Agent
  - administer, query and configure the PATROL Agent in real-time

  ![Monitoring Studio X - Architecture Diagram](./images/MS_X_Architecture_Diagram.png)


## What to Monitor with ${project.name}?

**${project.name}** offers a large choice of [Monitors](./basic-monitors.html) easily configurable to discover, monitor and collect information about the targeted technology.

**${project.name}** allows you to perform:

  * Command line executions
  * HTTP and REST API requests
  * Files parsing
  * File systems monitoring
  * Folders listing
  * Processes monitoring
  * PSL command execution
  * SNMP queries
  * SNMP traps listening
  * SQL queries
  * WBEM queries
  * WMI queries
  * Windows events monitoring
  * Windows performance counters reading
  * Windows services monitoring

The collected metrics of some of the above [Monitors](./content-monitors.html) can be analyzed by searching for strings and regular expressions, extracting numeric values, mapping values, and creating instances dynamically to easily visualize the components of the monitored technologies.

The solution makes it easy to define [alert thresholds and alert actions](./alerts.html) to detect and react to critical conditions with corrective solutions.

The configuration of the above Monitors is grouped inside what is called a **Template**, typically targeted at the monitoring of a given technology, or application. Then, you can apply a Template to one or several Hosts. The monitoring as configured in a template will be performed on each of the configured Hosts.

**${project.name}** comes with a large offering of templates to configure the monitoring of uncovered technologies and offers integration capabilities with Nagios plugins.


## What to Do with the Web Interface?
The **${project.name}** Web Interface provides a modern, interactive and powerful way to visualize, configure and manage your monitored environment from an easy-to-use Web-based console. Refer to the [Operating the Console](./console.html) section for details.


## What to Do with the REST API?
The REST API provided with **${project.name}** allows you to interact with the PATROL Agent and its main components. You gain access to:

- Agent (platform information, logs)
- Agent Configuration (pconfig)
- Events
- Console (monitored instances and parameters)
- Various tools

The **${project.name}** REST API leverages OAuth 2.0 for the authentication.

To access the [REST API documentation](/swagger-ui/url=../openapi.yaml), enter the following URL in your Web browser: ```https://<patrol-agent>:<ui-port>/swagger-ui/url=../openapi.yaml```

Where:

	- ```<patrol-agent>``` is the name of the PATROL Agent you have configured

and

	- ```<ui-port>``` is the port number used by the Web UI of **${project.name}**

For example (https://MyPatrolAgent:3443/swagger-ui/url=/openapi.yaml).
