# Other Tools

> This section describes the tools you can use to quickly manage a PATROL Agent configuration or troubleshoot your monitored environment.

![This is a nice test image from the Web which must not trigger any error](https://cds.cern.ch/images/CERN-HOMEWEB-PHO-2019-004-1/file?size=large)

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Configuration Tools

### Config Hacker

The **Config Hacker** allows experienced users to bypass the standard configuration options to download, modify, add, export, import or delete PATROL Agent configuration variables. Modifications are automatically applied to the PATROL Agent and immediately impact your monitoring environment.

Note that some variables cannot be deleted as they are defined in the *config.default* file. In this case, you can only reset their value to their definition in *config.default*.

The **DOWNLOAD .CFG** buttons allows you to download the currently selected configuration "folder" as a .CFG file, that can be re-used with ```pconfig```, imported into a CMA policy or imported via the **IMPORT .CFG** button. Select the root of the configuration and click the **DOWNLOAD .CFG** button to download the entire agent's configuration.

If you wish to export the configuration of **${project.name}** only, you need to use the download button from the Studio Settings page. To download a single _Template_ configuration, you need to use the **DOWNLOAD** button in the Template's interface.

The **PURGE** button deletes all customized variables and restores the configuration of the PATROL Agent with the variables defined in the *config.default* file. The configuration is automatically backed up before being purged.

<div class="alert alert-danger">Once its configuration is purged, an agent is likely to be unusable and will not start correctly if you try a restart. It is strongly advised to purge the configuration, and then restore or re-import a proper configuration immediately.</div>

### Encryption Tool

Some Agent configuration variables need to be set with encrypted passwords (such as the ```defaultAccount``` variable, for example). The encryption tool lets you encrypt a password using different algorithms. You will typically use Agent's default encryption for PATROL Agent's and BMC KMs password variables, and Sentry's encryption for Sentry KMs password variables.


## Troubleshooting Tools

### Debug

When you encounter an issue and wish to report it to Sentry Software, you will be asked to enable the **Debug** mode and provide the debug log to the Sentry Software support team.

When the debug mode is set to **ON**, **${project.name}** creates 3 log files that record all the KM actions until the date and time you have set:

* **X_debug_km_{port-and-timestamp}.log**: log file of the discovery and collect operations of the KM
* **X_CollectionHub_debug_psl_{port-and-timestamp}.log**: log file of the *Collection Hub*, the interface between PSL and the Java engine
* **X_CollectionHub_debug_java_{port-and-timestamp}.log**: log file of the Java engine of the _Collection Hub_

These files are saved to the ```$PATROL_HOME/log``` directory.

### PSL Process List

The **PSL Process List** provides an exhaustive list of currently scheduled or active PSL processes on the monitored PATROL Agent. The information displayed includes the unique numerical process id (PID), the PSL process name and type, and its status.

### KM list

The **PATROL KM List** provides an exhaustive list of all the KMs currently loaded on the monitored PATROL Agent, along with their version, static ("yes" if the KM always remains loaded by the Agent, "no" if the Knowledge Module is only loaded on request), and the number of consoles currently connected and using the KM.
