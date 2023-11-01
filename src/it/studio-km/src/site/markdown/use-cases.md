# Use Cases

>  This section provides configuration and action examples to illustrate monitoring scenarios that can be performed with **${project.name}**.

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Retrieving Messages or Events for the Current Date from a Linux Server

Create a **Command Line** Monitor from a **Template** and configure the following properties:

| Properties                                             | Settings                                                        |
| ------------------------------------------------------ | --------------------------------------------------------------- |
| Command Line                                           | Enter: **dmesg -T \| grep '%{TIME:%a} %{TIME:%b} %{TIME:%e}'**. |
| Execute As                                             | Select: **System credentials**.                                 |
| Run this Command on the Monitored Host or Agent system | Select: **On Host**.                                            |
| Display Name                                           | Enter: **Today's Messages/Events**.                             |
| Collect Schedule                                       | Select: **Run every 2 mins**.                                   |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

The list of current messages or events is displayed in **Console > (Hostname) > Result** or **Console > Monitoring Studio X > (Hostname) > Result**.


## Monitoring a Web Application with Cookie-based Authentication

Accessing a Web application that requires authentication is often performed through browser cookies. To access such an application and go through its authentication mechanism, **${project.name}** allows you to specify an HTTP Request Monitor to take care of the authentication that will be called first or when the HTTP server returns a _401 Unauthorized Error_.

### Create an HTTP Request Monitor to perform the authentication

From a **${project.name}** Template, create an **HTTP Request** Monitor that will perform the authentication to the monitored Web application and configure the following Properties:

#### HTTP Request Settings


| Properties                        | Settings                                                                                                                                                                                                                                              |
| --------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| URL                               | Select: **POST** and enter the URL of the Host on which you want to authenticate, following this format: **```https://<hostname>:<port number>/auth```**.                                                                                             |
| Authentication                    | Select: **Custom**.                                                                                                                                                                                                                                   |
| Display Name                      | Enter: **Authentication Request**.                                                                                                                                                                                                                    |
| HTTP Authentication Credentials   | Select the appropriate credentials that will be used to authenticate.                                                                                                                                                                                 |
| HTTP Request Body > Body Type     | Select: **Form**.                                                                                                                                                                                                                                     |
| HTTP Request Body > Content Type  | Select: **application/json**.                                                                                                                                                                                                                         |
| HTTP Request Body > Add Variables | Add the following variables: <ul> <li> **username** = **%{USERNAME}** (will be replaced at run-time with the specified credentials) </li> <li> **password** = **%{PASSWORD}** (will be replaced at run-time with the specified credentials)</li></ul> |
| Collect Schedule                  | Select: **Run manually only**.                                                                                                                                                                                                                        |


#### Analyze Settings

| Properties           | Settings                                          |
| -------------------- | ------------------------------------------------- |
| Content to Be Parsed | Select: **Entire HTTP Response - Header + Body**. |


### Create an HTTP Request Monitor to access the resources you actually need to monitor

#### HTTP Request Settings

| Properties                      | Settings                                                                                                                               |
| ------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------- |
| URL                             | Select: **GET** and enter the URL of the Host as specified in the previous Monitor (**```https://<hostname>:<port number>/auth```**) . |
| Authentication                  | Select: **Get Access Token**.                                                                                                          |
| **Get Access Token From**.      | Select the HTTP Request Monitor that have been previously created, i.e. **Authentication Request**.                                    |
| HTTP Authentication Credentials | Select the appropriate credentials that will be used to authenticate.                                                                  |
| Access Token Variable Name      | Leave **Empty**.                                                                                                                       |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

## Monitoring Large Temporary Log Files on a Linux System

Because many operations on Linux/UNIX systems create temporary files in the standard folder which are not always deleted, you may want to be notified when large files use storage space unnecessarily.

### Create a Folder Monitor

Create a **Folder** Monitor from a **Template** and configure the following properties to identify the monitored folder location and the type of files to take into account (log files in our example):

| Folder Monitor Properties | Settings             |
| ------------------------- | -------------------- |
| Folder Path               | Enter: **/var/tmp**. |
| Include Subfolders        | Select: **YES**.     |
| Filter Files              | Enter: ***.log**.    |

| Monitor Properties | Settings                   |
| ------------------ | -------------------------- |
| Display Name       | Enter: **/var/tmp>100MB**. |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

### Create a Dynamic Object Monitor

From the **Folder** Monitor page, create a **Dynamic Object** Monitor (```/var/tmp>100MB > Analyze > Dynamic Object```, in our example) to have **${project.name}** analyze log files path whose first column is ```/var/tmp```, (```%{1}``` in our example).

| Dynamic Object Properties     | Settings                                                            |
| ----------------------------- | ------------------------------------------------------------------- |
| Dynamic Instance Internal ID  | Enter: **%{1}**.                                                    |
| Dynamic Instance Display Name | Enter: **%{1}**.                                                    |
| Column Separator(s)           | Select: **; (semicolon)** Adjacent Separators: Keep (empty column). |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

### Create a Dynamic Numeric Value Extraction Monitor

From the **Dynamic Object** Monitor page, create a **Numeric Value Extraction** Monitor (```Dynamic: %{1} > Analyze > Dynamic Object```, in our example) for extracting the file size value from a specific column in the Monitor output (column 3 in our example).

| Numeric Value Extraction Properties | Settings                                                              |
| ----------------------------------- | --------------------------------------------------------------------- |
| Extracting Numeric Values From      | Select: **All Lines**. Select: **In Column Number** and enter: **3**. |
| Column Separator(s)                 | Select: ***: (semicolon)** Adjacent Separators: Keep (empty column).  |
| Support Negative Values             | Select: **No**.                                                       |
| Rescale                             | Select: **Divide by: 1048576**.                                       |

| Parameters and Alerts Properties | Settings                                                            |
| -------------------------------- | ------------------------------------------------------------------- |
| Alarm 1                          | Select: **ALARM** when **Value >= 100** and select: **immediately** |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.


**${project.name}** returns a list of all the discovered temporary files in this format: ```<file-path>;<modification-time-epoch>;<size-bytes>``` in the **Results** section of the **/var/tmp>100MB** Monitor in the **Console** menu.

Example:
```/var/tmp/sysidconfig.log;1324058185;630
/var/tmp/swupas/swupas.error.log;1308687869;0
/var/tmp/swupas/swupas.log;1308687869;0
/var/tmp/puredisk/2012-01-16_02:59-pdde-install.log;1326679198;121047
/var/tmp/puredisk/2012-04-26_07:01-pdde-install.log;1335416476;47
/var/tmp/puredisk/2012-04-26_07:10-pdde-install.log;1335417041;2052
/var/tmp/puredisk/2013-06-21_09:12-pdde-install.log;1371798814;2526
/var/tmp/puredisk/2014-03-06_06:11-pdde-install.log;1394082692;226
/var/tmp/puredisk/2014-03-06_18:59-pdde-install.log;1394128786;226
```


## Verifying the Deployment Status of an Application

This example shows how to verify that TrueSight Capacity Optimization is successfully deployed on a Linux system.

This 4-step procedure consists in:

1. Defining a Host
2. Creating a Template
3. Enabling the Template
4. Configuring a Database Query Monitor and performing a String Search

### 1. Define the Host on which TrueSight Capacity Optimization is Deployed

From the **Studio > Monitoring Hosts > New Host...** page, configure the following properties:

| Properties             | Settings                                                                                |
| ---------------------- | --------------------------------------------------------------------------------------- |
| **Display Name**       | Enter the name that will identify the host instance in TrueSight Operations Management. |
| **System Type**        | Select: **UNIX/Linux**.                                                                 |
| **System Credentials** | Provide valid credentials for the Host.                                                 |

Refer to [Hosts and Templates](./hosts-templates.html) for additional information.

### 2. Create a Template to Apply to the Host

From the **Studio > Monitoring Templates > New Template...** page, configure the following properties:

| Template Properties         | Settings                                |
| --------------------------- | --------------------------------------- |
| Template Name               | Enter: **Template MS Protocols Linux**. |
| Applies to Host System Type | Select: **Linux/UNIX only**.            |

| Required Credentials Properties | Settings                                                                                        |
| ------------------------------- | ----------------------------------------------------------------------------------------------- |
| Credentials Name                | Enter valid credentials that the Template will use to access the Host (_db_admin_ for example). |
| Default Username                | Optional. This default username will be used if no specific username is for the monitored Host. |
| Default Password                | Optional. This default password will be used if no specific password is for the monitored Host. |

| Database Properties                                      | Settings                            |
| -------------------------------------------------------- | ----------------------------------- |
| Database > Database Type Used by Database Query Monitors | Select: **Oracle Database Server**. |

Use the **Create** button to initiate the Template and save its properties.

Refer to [Hosts and Templates](./hosts-templates.html) for additional information.

### 3. Enable the Template on the Monitored Host

The **Template MS Protocols Linux** Template needs to be enabled on the targeted Host and the database connection settings must be configured.

1. In the treeview of the **Studio** page, locate the **Hosts** node and select the Host to which you wish to apply the Template.
2. In the **Templates** section, set the **Template MS Protocols Linux** to **ON** and provide the following information:

| Required Credentials: db_admin | Settings                                                              |
| ------------------------------ | --------------------------------------------------------------------- |
| Usernane                       | Provide valid username for the _db_admin_ account to access the Host. |
| Password                       | Provide valid password for the _db_admin_ account to access the Host. |


| Oracle Database Connection Settings | Settings                                           |
| ----------------------------------- | -------------------------------------------------- |
| Oracle Database Name                | Enter the database name.                           |
| Oracle Port Number                  | Enter the port number used to access the database. |

**Save** your settings.

### 4. Configure a Database Query Monitor on TSCO Deployment Logs

From the Template (_TSCO Deployment Status_ in our example) page, create a **Database Query** Monitor and provide the following information:

| Properties | Settings                                                                     |
| ---------- | ---------------------------------------------------------------------------- |
| SQL Query  | Enter: **select * from deployment_log** and select: **Anywhere In the Line** |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

#### Create a Search String Monitor to Retrieve the Deployment Status

| Properties                                  | Settings                                                                          |
| ------------------------------------------- | --------------------------------------------------------------------------------- |
| Search for Lines That                       | Select: **Contain** and enter: **Deployment process failed**.                     |
| Parameters and Alerts > Matching Line Count | Select: **Alert When Matching Line Count ≥ 1 lines** and select: **immediately**. |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

An alarm will be triggered if at least one line corresponding to the search criterion, i.e. _Deployment process failed_, is found in the monitored deployment log outputs.

## Querying a Database of Type 'Other'

This use case explains how to query a database other than the commonly used Microsoft SQL Server, MySQL Server, PostgreSQL or Oracle Server databases.
In this example, we are querying an **H2** database to retrieve the list of protocols supported by **${project.name}**.

### Prerequisites

1. Obtain the driver related to the database you are querying (_h2-1.3.166.jar_ in this example)
2. Install the driver in: ```/bmc/Patrol3/bin/```
3. Add the path to the driver in the **JVM Arguments** field of the **Agent > Java Settings** page of the **${project.name}** Web interface (_-Xbootclasspath/p:/opt/bmc/Patrol3/bin/h2-1.3.166.jar_ in our example). **Save** your changes and **Restart** the Agent.

<div class="alert alert-info"><i class="icon-info-sign"></i><strong>Tip: </strong>It is recommended to verify that the configuration is properly taken into account:<br></br>
From the treeview of the <strong>Tools > Configuration Hacker</strong> menu, locate <strong>Sentry > X</strong> and search for the .jar file by using key words (ex: <i>h2-1</i>). The full path to the database driver should be returned in the <i>collectionHubAdditionalArgs</i> field (<i>/SENTRY/X/collectionHubAdditionalArgs = -Xbootclasspath/p:/opt/bmc/Patrol3/bin/h2-1.3.166.jar</i> in our example).</div>

This 4-step procedure consists in:

1. Defining a Host
2. Creating a Template
3. Enabling the Template
4. Configuring a Database Query Monitor

### 1. Define the Host on which the Database is Installed

From the **Studio > Monitoring Hosts > New Host...** page, configure the following properties:

| Properties             | Settings                                                                                                |
| ---------------------- | ------------------------------------------------------------------------------------------------------- |
| **Display Name**       | Enter the name that will identify the host instance in TrueSight Operations Management (ex: qa_docker). |
| **System Type**        | Select: **UNIX/Linux**.                                                                                 |
| **System Credentials** | Provide valid credentials for the Host.                                                                 |

Refer to [Hosts and Templates](./hosts-templates.html) for additional information.

### 2. Create a Template to Apply to the Host

From the **Studio > Monitoring Templates > New Template...** page, configure the following properties:

| Template Properties         | Settings                                |
| --------------------------- | --------------------------------------- |
| Template Name               | Enter: **Template MS Protocols Linux**. |
| Applies to Host System Type | Select: **Linux/UNIX only**.            |

| Required Credentials Properties | Settings                                                                                        |
| ------------------------------- | ----------------------------------------------------------------------------------------------- |
| Credentials Name                | Enter valid credentials that the Template will use to access the Host (_db_admin_ for example). |
| Default Username                | Optional. This default username will be used if no specific username is for the monitored Host. |
| Default Password                | Optional. This default password will be used if no specific password is for the monitored Host. |

| Database Properties                                      | Settings                    |
| -------------------------------------------------------- | --------------------------- |
| Database > Database Type Used by Database Query Monitors | Select: **Other Database**. |

Use the **Create** button to initiate the Template and save its properties.

Refer to [Hosts and Templates](./hosts-templates.html) for additional information.

### 3. Enable the Template on the Monitored Host

The **Template MS_Protocols Linux** Template needs to be enabled on the targeted Host and the database connection settings must be configured.

1. In the treeview of the **Studio** page, locate the **Hosts** node and select the Host to which you wish to apply the Template.
2. In the **Templates** section, set the **Template MS Protocols Linux** to **ON** and provide the following information:

| Other Database Connection Settings | Settings                                                                                                                                                                                                                                                                      |
| ---------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| URL                                | Provide the URL of the database (```jdbc:h2:tcp://qa-docker:1521/test``` in our example) where _h2_ is the database type, _qa/docker_ is the hostname, _1521_ is the host port and _test_ is database name.                                                                   |
| Driver Class                       | Provide the name of the driver that **${project.name}** will leverage to execute the query (_h2-1.3.166.jar_ in our example). Make sure that the driver is properly installed. Refer to the [Prerequisites](./use-cases.html#Prerequisites) section for detailed information. |

**Save** your settings.

### 4. Configure a Database Query Monitor

From the **Template** page (_Template_MS_Protocols_Linux_ in our example), create a **Database Query** Monitor and provide the following information:

| Properties | Settings                                                                  |
| ---------- | ------------------------------------------------------------------------- |
| SQL Query  | Enter: **SELECT * FROM Protocols_SupportedByStudio ORDER BY Protocol_ID** |

Use the **Dry Run** button to verify that the response is successful and click **Create** to save your Monitor.

Once the query is executed, you can visualize the result from the **Monitor** page of the **Console** menu:

![Other Database Query Result](./images/Other_DB_Result.png)
