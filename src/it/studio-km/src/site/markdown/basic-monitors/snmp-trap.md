# SNMP Trap

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

For more information about the parameters available and the thresholds set by default, refer to [Studio SNMP Trap](../X_SNMPTRAP.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
