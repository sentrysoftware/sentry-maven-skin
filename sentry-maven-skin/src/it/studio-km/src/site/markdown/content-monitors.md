# Monitors with Content

> This section describes all Monitors that generate content that can later be analyzed through the wide range of tools available in **${project.name}** (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Command Line

The purpose of the **Command Line Monitor** is to run commands or in-house scripts and analyze their result on a regular basic.

To monitor commands or in-house scripts, **${project.name}** needs:

* the command line to be executed. It can either be a shell command, a shell script, or an executable file with arguments. A file containing the command to execute can also be referred to with the `%{FILE:<filename>}` macro. Refer to [Embedded Files](./hosts-templates.html#Embedded_Files) for more details.
* the credentials required to run the command.

By default, the specified command line is executed on the monitored *Host*. The command line can contain the macros below:

* ```%{TIME:<date-time-format>}```, which will be replaced at run-time with the current date and time. Refer to [Time/Date Formats for Macros](./macros.html) for more details.
* ```%{LASTTIME:<date-time-format>}```, which will be replaced at run-time with the date and time at which the command was last executed. This macro is useful to specify a time range for the command, like listing events since the last time you checked. When using this macro, the execution is skipped entirely the first time the Monitor runs (after the PATROL Agent starts). This is to ensure that an actual date and time is inserted with an actual value for this macro.
* ```%{FILE:<filename>}```, which will be replaced by the path to the corresponding *Embedded File*, stored in a temporary location at run time. Refer to [Embedded Files](./hosts-templates.html#Embedded_Files) for more details.

When the command is executed on the agent system, you can use the following macros:

* `%{HOSTNAME}`, which will be replaced at run-time with the hostname of the monitored host
* `%{USERNAME}`, which will be replaced at run-time with the username of the selected credentials
* `%{PASSWORD}`, which will be replaced at run-time with the password of the selected credentials.

Example: `WMIC /NAMESPACE:\\%{HOSTNAME}\root\cimv2 /USERNAME:%{USERNAME} /PASSWORD:%{PASSWORD} CPU`

<div class="alert alert-danger">The password being inserted in clear text, it may be visible in command-lines to non-root users. Use at your own risk. </div>


You can specify how **${project.name}** will determine whether the command executed successfully with:

* a regular expression that must be found in the output of the command (e.g. the `ping` command must contain `Pinging`)
* exit codes that mean the command succeeded (typically with exit code 0)

When the command is successfully executed, **${project.name}** provides the execution time, the exit code returned, the exit status, and the result of the command line.

If the command is not successful, **${project.name}** will either  trigger an alert on the **Status** parameter of the **Monitor** or report execution errors in the **Collection Error Count** parameter of the **Template**. Other parameters will not be collected and the output of the command will not be parsed with the **String Searches**, **Numeric Value Extractions**, etc.

The result of the command line can also be analyzed through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

Refer to the table below to know how to configure the **Command Line Monitor**:

| Property                                                     | Description                                                                                                                                                                                                                                                                                                                                               |
| ------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Command Line                                                 | Command line to be executed.                                                                                                                                                                                                                                                                                                                              |
| Execute As                                                   | *(Only when command is run on the host)* Credentials required to execute the command line on the host. By default,  **System Credentials** are used.                                                                                                                                                                                                      |
| Set %{USERNAME} And %{PASSWORD} With                         | *(Only when command is run on the Agent)* Credentials available through the ```%{USERNAME}``` and ```%{PASSWORD}``` macros in the command line executed on the Agent (Example: ```isql -U %{USERNAME} -P %{PASSWORD} "SELECT * FROM sysprocesses"```). The command will be executed as the default PATROL Agent user.                                     |
| Run this Command on the Monitored Host or Agent system?      | Host on which the command will be run (**ON HOST** or **ON AGENT**). When the command is run on the Agent, you will have to set the credentials to be used through the  ```%{USERNAME}``` and ```%{PASSWORD}``` macros.                                                                                                                                   |
| Display Name                                                 | Name to identify the **Command Line Monitor** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                                |
| Internal ID                                                  | ID to be used to store the **Command Line** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                                          |
| Collect Schedule                                             | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                           |
| Command Output Should Contain                                | Regular expression to be found in the command output for the command to be considered successful. If not found, the **Status** attribute of the **Command Line Monitor** is automatically set to 2 (Failed). This option helps ensure command has been properly executed and avoid false alerts on the associated String Search/Numeric Value Extraction. |
| Timeout After                                                | Time in seconds after which the command line will be stopped (Default: 30 seconds). If the command times out, the **Status** parameter of the *Monitor* will be set to 2 (Failed) and an alarm will be triggered, or the Template's Collection Error Count will be increased by '1'.                                                                      |
| Command Exit Code                                            | Exit codes that will indicate whether the command was successfully executed or failed. Select **No Validation** if you do not wish to validate the command execution.                                                                                                                                                                                     |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the Monitor's **Status** parameter. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled.                     |

At this time, you can run the **Dry Run** feature to trigger the execution of this command line on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a command line.
* [Studio Command Line](./X_COMMANDLINE.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Database Query (SQL)

The **Database Query Monitor** lets you execute an SQL statement against a database server to:

* check the availability of the database
* check the result of a specific query
* extract data of interest for monitoring.

The most popular database servers are supported out-of-the-box (Microsoft SQL Server, MySQL, Oracle and PostgreSQL), but any JDBC-enabled database can be supported as well.

To be able to execute SQL queries with **${project.name}**, you have to:

* indicate at the **Template** level the type of database used:

    ![Selecting the Database Type](./images/database-type.png)

* click the **Database Query** button to specify the SQL query to be run.

  <div class="alert alert-info">A Template can only connect to and monitor <strong>one single database</strong>.The database-specific information, like the port, will have to be specified in the Host page when applying the Template to the Host.</div>

When the query is successfully executed, **${project.name}** can provide its execution time, result, and status.

The result, which is stored by **${project.name}** in a pipe-separated table format,  can be analyzed through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

Refer to the table below to know how to configure the **Database Query Monitor**:



| Property                                                     | Description                                                                                                                                                                                                                                                                                                                          |     |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | --- |
| SQL Query                                                    | SQL query to be executed.                                                                                                                                                                                                                                                                                                            |     |
| Execute As                                                   | Credentials required to execute the SQL query. By default,  **System Credentials** are used.                                                                                                                                                                                                                                         |     |
| Timeout After                                                | Time in seconds after which the request will be stopped (Default: 30 seconds). If the SQL query  times out, the **Status** parameter of the *Monitor* will be set to 2 (Failed) and an alarm will be triggered, or the Template's Collection Error Count will be increased by '1'.                                                   |     |
| Display Name                                                 | Name to identify the **SQL Monitor** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                    |     |
| Internal ID                                                  | ID to be used to store the **SQL Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                      |     |
| Collect Schedule                                             | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                      |     |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the Monitor's **Status** paramete. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled. |     |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a database query.
* [Studio Database Query](./X_DBQUERY.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## File

The purpose of the **File Monitor** is to:

* check the presence of a log or flat file
* monitor the file size, its growth percentage and growth speed
* indicate when the file was last modified
* parse the content of the file.

**${project.name}** only needs the path to the file to be monitored to start collecting information. The file path can contain the wildcards and macros below:

* `?` wildcard to match with exactly one character (e.g.: `errorLog.???` will match with **errorLog.001**, **errorLog.020**, etc.)
* `*` wildcard to match with any sequence of characters (e.g.: `system-*.log` will match with **system-MYHOSTNAME.log**)
* `%{TIME:...}` followed by the expected date and time format, that will be replaced at run-time with the current date and time (e.g.: `%{TIME:%Y-%m-%D}`). Refer to [Time/Date Formats for Macros](./macros.html) for more details.
* Environment variables like `%SystemRoot%` on Windows or `$HOME` on Linux and UNIX

Depending on your environment, you may also have to provide **${project.name}** with the credentials required to access the file.

You can parse and analyze the content of the file through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**). The most typical usage of file monitoring is parsing a log file and be informed when a specific string or numeric value is found.

Refer to the table below to know how to configure the **File Monitor**:


| Property                          | Description                                                                                                                                                                                                      |
| --------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| File Path                         | Path and name of the file to be monitored.                                                                                                                                                                       |
| Credentials Used to Read the File | Credentials required to access and read the file. By default, **System Credentials** are used.                                                                                                                   |
| File Read Mode                    | Select **Log** if only new content should be read; **Flat** if the whole file should be read at every collect.                                                                                                   |
| Display Name                      | Name to identify the **File Monitor** instance in TrueSight Operations Management.                                                                                                                               |
| Internal ID                       | ID to be used to store the **File Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed. |
| Collect Schedule                  | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                  |
| Timeout After                     | Time in seconds after which the File collector will be stopped (Default: 30 seconds). When a timeout is reached, an error is reported in the Template's Collection Error Count parameter.                        |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

<div class="alert alert-info">For log files, the <strong>Result</strong> parameter will remain empty until new content is added to the file.</div>



Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the content of a monitored file.
* [Studio File](./X_FILE.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Folder

The **Folder Monitor** monitors a directory. This *Monitor* comes handy when an application actively uses directories for its operations. The **Folder Monitor**  measures the directory size, growth and flow to:

* ensure that the application is not overloaded (e.g.: number of files to be processed)
* measure the application activity (how many files have been removed, that is, how many have been processed)
* check the age of the newest file to know whether the data is coming in properly
* check the age of the oldest file to know whether the application is running late in processing queued files.

You can use the ```%{TIME:<date-time-format>}``` macro in the **Folder Path** to dynamically assign the current date or time in the folder name. Refer to [Time/Date Formats for Macros](./macros.html) for more details.

Advanced file monitoring is possible with the **Folder Monitor** through filters and a wide range of analysis tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

<div class="alert alert-info">By default, the Folder Monitor automatically times out after 30 seconds.</div>

To monitor a folder, you must configure the properties below:

| Property                       | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Folder Path                    | Path to the folder (directory) to be monitored (Example: `C:\Users\Default\Downloads`).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Include Subfolders             | Select this option to include all sub-folders of the above-specified folder when collecting data.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| Filter Files                   | Enter a mask to have **${project.name}** monitor only the corresponding files. You can use the `?` and `*` wildcards and several masks can be specified, separated by ";" (Example: ```.txt;myFiles?.log;file.*```).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| Credentials to Read the Folder | Credentials required to read the folder. By default,  **System Credentials** are used.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| Display Name                   | Name to identify the **Folder** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| Internal ID                    | ID to be used to store the **Folder** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the folder path provided but can be edited if needed.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| Collect Schedule               | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| When Folder Is Empty           | Action to be performed by **${project.name}** on the time-based attributes (**Last Modified File Elapsed Time**, **Longest Time File Remains In** and **Oldest Modified File Elapsed Time**) when the monitored folder is empty. <br /> Possible values are:<ul> <li> **Do not update the time-based attributes**: The attributes values are not updated, and the alerts are not cleared (Default). In this case, the attributes keep the same value as previously set upon the last collect. If the last value set was within an alarm range, the alert remains active until the value is set again, that is when the folder will no longer be empty.</li> <li> **Suspend the time-based attributes**: The attributes values are not set, but any alert is cleared when the folder becomes empty. In this case, the attributes are suspended (i.e. deactivated) and immediately enabled again. The value of these attributes are not updated and the attributes remain offline in TrueSight OM until a new value is set, that is when the folder will no longer be empty. If the attributes were previously in alarm, the alert is not cleared (No PATROL event (STD/9) is triggered).</li> <li> **Set the time-based attributes to zero**: The attributes values are reset to zero and all alerts are automatically acknowledged. In this case, the attributes are set to zero as long as the folder remains empty. While the value could be considered improper, it ensures that previous alerts are cleared and that the corresponding PATROL event (STD/9) is triggered (assuming that zero is out of the alarm range).</li></ul> |
| Timeout After                  | Time in seconds after which the Folder collector will be stopped (Default: 30 seconds). When a timeout is reached, an error is reported in the Template's Collection Error Count parameter.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of the Monitor execution.
* [Studio Folder](./X_FOLDER.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.


## HTTP Request

The purpose of the **HTTP Request Monitor** is to perform an HTTP request and analyze the response of the HTTP server to:

* monitor the availability of a Web page,
* extract useful information from a Web-based administration user interface,
* extract data from a Web service,
* extract data from a REST API.

### HTTP Request

An **HTTP Request** must specify a URL, which must contain the hostname of the HTTP server. To query:

* **the HTTP server running on the *Host* to which the *Template* is applied**, use the `%{HOSTNAME}` macro in the URL (e.g.: `https://%{HOSTNAME}/api/v2/directory`). The HTTP request is then performed from the PATROL Agent to the monitored *Host*.
* **an external Web server**, specify the hostname in the URL (e.g.: `http://www.didthanoskill.me/`). The HTTP request is then performed from the PATROL Agent to the specified host. This host may not be related to the *Host* to which the *Template* is applied.

If the HTTP request requires authentication, you will have to specify the method to be used:

* **HTTP Basic**. The specified credentials will be sent directly to the HTTP server. You should use this method over HTTPS only.
* **Challenge (Basic, Digest, or NTLM)**. A first request is made without any credentials, to which the server responds a 401 code with the type of supported authentications. Then a second request is made with the required information, including the username and password of the specified credentials.
* **Custom**. This method allows using the ```%{USERNAME}```, ```%{PASSWORD}```, ```%{PASSWORD_BASE64}```, ```%{PASSWORD_URL}``` and ```%{PASSWORD_JSON}``` macros anywhere in the URL, header or body of the HTTP request.
* **Access Token**. You will then have to specify:

    * the HTTP Request Monitor of the *Template* from which the access token will be extracted.
    * the name of the variable that holds the access token in the response of the HTTP request performing the authentication (e.g.: ```access_token``` for OAuth 2.0).

Refer to the table below to know how to configure the HTTP request:

| Property       | Description                                                                                                                                                                                                                                                                                          |
| -------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| URL            | HTTP method (GET, POST, PUT, DELETE, etc.) to be performed and URL (http or https) of the resource to be polled and monitored.                                                                                                                                                                       |
| Authentication | Authentication method to be used for the HTTP request. Depending on the method, you may have to provide additional information (credentials, HTTP Request Monitor, token variable name, etc.)                                                                                                        |
| Timeout After  | Time in seconds after which the request will be stopped (Default: 30 seconds). If the request times out, the **Status** parameter of the **Studio Web Request Monitor** will be set to 2 (Failed) and an alarm will be triggered, or the Template's Collection Error Count will be increased by '1'. |

### HTTP Request Headers and Body

You can customize the HTTP request that will be sent with specific headers and a custom body. The Web interface will help you send variables as a form or a JSON document, but you are free to specify any type of header and body format.

If you chose a custom authentication with credentials, you can use the following macros:

* `%{USERNAME}`: The username of the specified credentials
* `%{PASSWORD}`: The password of the specified credentials, in clear.
* `%{PASSWORD_BASE64}`: The password encoded in Base64 (typical for Authorization: Basic headers)
* `%{PASSWORD_URL}`: The password URL-encoded (typical for an *application/x-www-form-urlencoded* encoded body)
* `%{PASSWORD_JSON}`: The password as a JSON string (typical for an *application/json* body)

### HTTP Status Codes

You can specify which HTTP status codes indicate that the request has been successfully completed. Type the codes separated by commas. Use a dash to denote a range of codes. By default, **${project.name}** considers all 2xx  codes **(200-208,226)** as success codes.

### Monitor Properties

| Property         | Description                                                                                                                                                                                                              |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Display Name     | Name to identify the **HTTP Request Monitor** instance in TrueSight Operations Management.                                                                                                                               |
| Internal ID      | ID to be used to store the **HTTP Request Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                          |

### Parameters and Alerts

| Property                                                     | Description                                                                                                                                                                                                                                                                                                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the Monitor's **Status** parameter. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled. |

Refer to:

* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters
* [Studio Web Request](./X_HTTP.html) for more information about the parameters available and the thresholds set by default.

You can also specify what information will appear in the **Result** parameter:

* The entire HTTP response (header and body)
* The HTTP response body
* The text content extracted from the body of the Web page (all tags are removed)
* A conversion of the JSON document to a flat map.

If successful, the response to the HTTP request is provided in the **Result** parameter and can be parsed with **[String Searches, Numeric Value Extractions, etc.](./content-parsing-monitors.html)**

Once the **HTTP Request Monitor** is configured, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

## Nagios Plugin

The purpose of the **Nagios Plugin Monitor** is to execute any Nagios Plugin and monitor the results directly from TrueSight without requiring a native Nagios environment.

To be able to execute a specific Nagios Plugin, **${project.name}** simply needs the command to run the Nagios plugin. You can either enter manually the command or refer to an embedded file using the `%{FILE:<filename>}` macro. Refer to [Embedded Files](./hosts-templates.html#Embedded_Files) for more details.

You can choose to run the Nagios plugin on the agent system, or on the monitored system (remotely).

Nagios plugins that provide performance information are fully supported. Each output line of the Nagios plugin will be represented with a separate instance in TrueSight, with its own metrics.

Refer to the table below to know how to configure the properties available:

| Property                                                | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| ------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Nagios Plugin Command                                   | Full path to the Nagios Plugin Command with options to be run (Example: ```/usr/local/nagios/libexec/check_disk -w 30% -c 50%```). You can use the ```%{HOSTNAME}```,``` %{USERNAME}``` and ```%{PASSWORD}``` macros if the command is run on the Agent system. When monitoring remote Windows system from a PATROL Agent running on a UNIX/Linux system, the command will be supported only on the Agent system. Example: ```/usr/local/nagios/libexec/check_nt -H $HOSTADDRESS -v CPULOAD -w 80% -c 90%```. Here the macro ```$HOSTADDRESS``` will be resolved to the monitored host at the time of execution. |
| Execute As                                              | *(Only when command is run on the host)* Credentials required to execute the command. By default, **System Credentials** are used.                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Set %{USERNAME} And %{PASSWORD} With                    | *(Only when command is run on the Agent)* Credentials available through the ```%{USERNAME}``` and ```%{PASSWORD}``` macros in the command executed on the Agent. The command will be executed as the default PATROL Agent user.                                                                                                                                                                                                                                                                                                                                                                                  |
| Run this Command on the Monitored Host or Agent system? | *(Not available when command is run from UNIX/Linux Agent to Windows system)* Host on which the command will be run (**ON HOST** or **ON AGENT**). When the command is run on the Agent, you will have to set the credentials to be used through the  ```%{USERNAME}``` and ```%{PASSWORD}``` macros.                                                                                                                                                                                                                                                                                                            |
| Timeout After                                           | Time in seconds after which the command will be stopped (Default: 30 seconds). If the timeout is reached, a new error is logged in the **Collection Error Count** parameter of the template, indicating that the command failed to execute properly.                                                                                                                                                                                                                                                                                                                                                             |
| Display Name                                            | Name to identify the **Nagios Plugin** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| Internal ID                                             | ID to be used to store the **Nagios Plugin** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                                                                                                                                                                                                                                                                                        |
| Collect Schedule                                        | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Report Unknown (3) Service Status                       | How an unknown error will be reported in TrueSight.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| Extract Nagios Performance Data                         | When enabled, performance data provided by the Nagios plugin will be extracted and displayed in separate instances.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| Use Plugin's Default Alert Thresholds on Value          | When enabled, the default alerts for the **Value** attribute based on the returned performance data will be used.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Rescaling                                               | How **${project.name}** will rescale the extracted value.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| What to Do with Transient Instances                     | How **${project.name}** will handle transient instances.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |


For more information about the parameters available and the thresholds set by default, refer to [Studio Nagios Plugin](./X_NAGIOSPLUGIN.html) and [Studio Nagios Performance Data](./X_NAGIOSPERF.html).

Refer to [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## Parameter Combination

The **Parameter Combination** Monitor can combine any Monitor parameter of the Template with a specified mathematical formula or a PSL statement, like a function call. You can for example add, subtract, multiply, concatenate, or divide two values, calculate an average, perform table joins, etc. The returned value can be used to perform additional operations such as converting units, performing correlation, etc.

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong> It is not possible to refer to Monitors from other Templates.</div>

Refer to the tables below to know how to configure the **Parameter Combination** Monitor:

### Variables Properties

| Property                   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| -------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Variable Name              | Name of the variable that will be processed in the formula. Add as many variables as needed.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| Variable Path              | Set to **PICK** to select the Parameter of a Monitor, or **CUSTOM** to specify an internal namespace variable (advanced).                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Monitor                    | _(Only available when PICK is selected)_ Select a **Monitor** from the list of Monitors available for the given Template.                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| Parameter                  | _(Only available when PICK is selected)_ Select a **Parameter** among the ones available for the Monitor previously selected.                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| Formula                    | The formula to apply to the provided. The parameters are identified by a letter listed in the dialog box. Build the required formula using:<br></br><li>The variable name (A, B, etc.).</li><li>PSL operators: addition (+), subtraction (-), multiplication (*), division (/) concatenation (.), bitwise (&), etc.</li><li>Predefined PSL functions: [tableJoin()](./content-monitors.html#tableJoin()), [min()](./content-monitors.html#min()), [max()](./content-monitors.html#max()), [average()](./content-monitors.html#average()), and [sum()](./content-monitors.html#sum()) parameter(s).</li> |
| When a Variable is Not Set | Indicates how **${project.name}** handles undefined variables:<br></br> <li>**Interpret as zero**: Parameters without values are considered as parameters with a value of 0 </li><li>**Skip Collection**: The collect of parameters without value is skipped.</li>                                                                                                                                                                                                                                                                                                                                      |

### Monitor Properties

| Property         | Description                                                                                                                                                                                                               |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name     | Name to identify the **Parameter Combination** instance in TrueSight Operations Management.                                                                                                                               |
| Internal ID      | ID to be used to store the **Parameter Combination** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                           |


### Predefined PSL Functions

#### tableJoin

The ```tableJoin()``` PSL function is used to join two different tables from text parameters:

```tableJoin(tableA, separatorsA, keyColumnA, tableB, separatorsB, keyColumnB, defaultRightLine, keyType, timeout)```


where:

- ```tableA``` is the left table derived from text parameter A
- ```separatorsA``` are the separators that separate the columns in tableA
- ```keyColumnA``` is the key column number in tableA used for matching the key columns in tableB
- ```tableB``` is the right table derived from text parameter B
- ```separatorsB``` are the separators that separate the columns in tableB
- ```keyColumnB``` is the key column number in tableB used for matching the key columns in tableA
- ```defaultRightLine``` (Optional) default rightTable line, when a match is not found
- ```keyType``` (Optional) key type such as wbem used by Matsya TableJointClient used by the Java client.
- ```timeout``` (Optional) timeout for the table joint query.

Example:

```tableJoin(A, "|", 1, B, "|", 1)```

where:

**Table 1 (in parameter A: path1/Result):**
key ```1|A|B|C|```
key ```2|aa|bb|cc|```
key ```3|1|2|3|```

**Table 2 (in parameter B: path2/Result):**
key ```1|X|Y|Z|```
key ```2|xx|yy|zz|```
key ```3|4|5|6|```

The returned output is a table that is set to the **Result** text parameter:
key ```1;A;B;C;key 1;X;Y;Z;```
key ```2;aa;bb;cc;key 2;xx;yy;zz;```
key ```3;1;2;3;key 3;4;5;6;```

#### min

The ```min()``` PSL function is used to find the minimum value among several parameters:

where:

- **A** and **B** are number parameters: ```min([A, B, etc.])```

#### max

The ```max()``` PSL function is used to find the maximum value among several parameters:

where:

- **A** and **B** are number parameters: ```max([A, B, etc.])```


#### average

The ```average()``` PSL function is used to find the average value for the chosen list of parameters:

where:

- **A** and **B** are number parameters): ```average([A, B, etc.])```


#### sum

The ```sum()``` PSL function is used to find the sum of the chosen list of parameters:

where:

- **A** and **B** are number parameters): ```sum([A, B, etc.])```

The returned output of the ```tableJoin```, ```min```, ```max```, ```average```, and ```sum``` PSL functions will be displayed by the **Value** parameter.

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong> If the provided formula or parameters are not PSL-compatible, an error will be reported by the <strong>Collection Error Count</strong> parameter.</div>

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a PSL Script.
* [Studio Multi-parameter Formula](./X_FORMULA.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## PSL Script

<div class="alert alert-info">The PSL Script Monitor is intended for advanced users with solid experience in PSL (PATROL Script Language).</div>

The purpose of the **PSL Script Monitor** is to run PSL commands on the local PATROL Agent system to check the health of the PATROL Agent. You can for example run the following PSL scripts:

* ```system("%DUMP KM_LIST")``` to get the list of all loaded KMs
* ```system("%PSLPS")``` to get the PSL processes and their status
* ```system("%DUMP RUNQ")``` to get the list of items scheduled in the run queue
* ```system("%STAT ALL")``` to get all Agent memory usage statistics.

Once the **PSL Script Monitor** is properly configured, **${project.name}** provides the execution time, the result, and the status of the script.

The result of the PSL script can also be analyzed through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

Refer to the table below to know how to configure the **PSL Script Monitor**:

| Property              | Description                                                                                                                                                                                                                 |
| --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| PSL Command or Script | PSL command or script to be executed. PSL commands are executed on the local host, where the PATROL Agent is running.                                                                                                       |
| Timeout After         | Time in seconds after which the PSL command will be stopped (Default: 30 seconds). If the query times out, the **Status** parameter of the **Studio PSL Command** will be set to 2 (Failed) and an alarm will be triggered. |
| Display Name          | Name to identify the **PSL Monitor** instance in TrueSight Operations Management.                                                                                                                                           |
| Internal ID           | ID to be used to store the **PSL Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.             |
| Collect Schedule      | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                             |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a PSL Script.
* [Studio PSL Command](./X_PSLCOMMAND.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## SNMP Polling

The **SNMP Polling Monitor** polls the SNMP agent and retrieves the values of a given OID (object identifier), or the values of an SNMP table to know the status of the monitored device and be informed when a problem occurs.

Once the **SNMP Polling Monitor** is properly configured, **${project.name}** provides the execution time, the result, the status, and the value of the SNMP polling.

The result can also be analyzed through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

<div class="alert alert-info">SNMP polling supports SNMP v1, v2c, and v3.</div>

Refer to the table below to know how to configure the **SNMP Polling Monitor**:

| Property                                                     | Description                                                                                                                                                                                                                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| OID                                                          | OID (object identifier) to poll, as provided by the Management Information Base (MIB).                                                                                                                                                                              |
| Display Name                                                 | Name to identify the **SNMP Monitor** instance in TrueSight Operations Management.                                                                                                                                                                                  |
| Internal ID                                                  | ID to be used to store the **SNMP Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                    |
| Collect Schedule                                             | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                     |
| Poll a Single OID or an SNMP Table?                          | Indicate whether **${project.name}** will poll a single OID or an SNMP Table.                                                                                                                                                                                       |
| Column Numbers                                               | *(Only when polling an SNMP Table)*  Column numbers in the SNMP table to be polled. Column numbers must be delimited by commas (Example: ```4,8,9```). Leave the field blank to retrieve values from the entire row. Enter ```ID``` to retrieve the row identifier. |
| OID Content Type                                             | *(Only when polling a single OID)* Type of the OID Content (**String** or **Number**).                                                                                                                                                                              |
| Numeric Content Options             | *(Only when polling a single OID of type Number)* Indicate whether the **Monitored Value** is a raw value or a delta. If you select one of the **Delta** options, you will have to indicate whether negative values should be discarded. You may also select the type of a rescaling to perform.                        |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the **Status** parameter of the *Monitor*. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled. |                                                                                                                                        |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of an SNMP Polling
* [Studio SNMP Polling](./X_SNMPPOLLING.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## WBEM Query

The **WBEM Query Monitor** allows you to query a WBEM Provider. Depending on the implementation, the WBEM Provider may also be called CIM Provider, CIM Agent, WBEM server, etc.

To configure the **WBEM Query Monitor**, you need to:

* specify a WBEM namespace (or CIM namespace)
* specify a WQL query such as *SELECT DeviceID, Name, OperationalStatus FROM CIM_LogicalDevice*).

    <div class="alert alert-info">WQL usually requires the <i>WBEM Provider</i> (or <i>CIM Provider</i>) to implement the <i>executeQuery()</i> method. Because this is rarely the case, <strong>${project.name}</strong> converts the WQL query into a regular <i>enumerateInstances()</i>query, which is supported on all platforms.</div>

You will then set the **WBEM Connection Settings** (port and encryption information) at the **Host** level, when applying the **Template(s)** containing the **WBEM Query Monitor(s)**.

Once the **WBEM Query Monitor** is properly configured, the result of the query is displayed in the **Result** parameter. Each instance is represented as a separate line and properties are separated with semicolons. Properties of type *Array* are represented as a pipe-separated list of values. The *SELECT DeviceID, Name, OperationalStatus FROM CIM_DiskDrive* query will for example return this type of result:

    DISK0;Physical Disk Drive 0;2|;
    DISK1;Physical Disk Drive 1;2|;
    DISK2;RAID A;6|32769|;

The result of the WBEM query can then be analyzed through a wide range of tools (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

Refer to the table below to know how to configure the **WBEM Query Monitor**:


| Property                                                     | Description                                                                                                                                                                                                               |
| ------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| WBEM Query                                                   | WBEM query to be run (Example: ```SELECT DeviceID,OperationalStatus FROM CIM_DiskDrive```).                                                                                                                               |
| Credentials Used to Execute the WBEM Query                   | Credentials required to execute the WBEM query. By default,  **System Credentials** are used.                                                                                                                             |
| Display Name                                                 | Name to identify the **WBEM Monitor** instance in TrueSight Operations Management.                                                                                                                                        |
| Internal ID                                                  | ID to be used to store the **WBEM Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.          |
| Collect Schedule                                             | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                           |
| Namespace                                                    | Logical group of related monitor types representing a specific technology or area of management (Example: ```root/cimv2```).                                                                                              |
| Timeout After                                                | Time in seconds after which the query will be stopped (Default: 30 seconds). If the query times out, a collection error will be triggered either on the **Status** parameter or the Template's Collection Error Count parameter.                                                                                                                     |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the **Status** parameter of the *Monitor*. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled. |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a WBEM query.
* [Studio WBEM Query](./X_WBEMQUERY.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.

## WMI Query

<div class="alert alert-danger">This feature is only available for Windows hosts and can be used on agents running on Windows. Both the agent and the monitored system must be running on Windows. WMI queries cannot be performed from a Linux agent to a Windows host.</div>

The **WMI Query Monitor** allows you to execute a WQL query against a Windows host and analyze its result. A typical [WQL query](https://docs.microsoft.com/en-us/windows/desktop/wmisdk/wql-sql-for-wmi) has the following syntax:

    SELECT DeviceID,Size FROM Win32_LogicalDisk

The result of the WQL query is a semicolon-separated list where each row represents an instance:

    C:;238846734336;
    D:;479967834112;
    E:;366997504;

The result can be analyzed through a wide range of tools  (**Dynamic Object**, **Numeric Value Extraction**, **Text Pre-Processing**, **String Search**, and **Value Map**).

Refer to the table below to know how to configure the **WMI Query Monitor**:


| Property                                                     | Description                                                                                                                                                                                                                                                                                                                                              |
| ------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| WMI Query                                                    | WMI query to be run. You can use [WMI CIM Studio](https://technet.microsoft.com/en-us/library/cc181062.aspx) to build your query.                                                                                                                                                                                                                        |
| Namespace                                                    | WMI namespace the query must be executed in (example: ```root\cimv2```).                                                                                                                                                                                                                                                                                 |
| Display Name                                                 | Name to identify the **WMI Query Monitor** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                                  |
| Internal ID                                                  | ID to be used to store the **WMI Query Monitor** configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                                    |
| Collect Schedule                                             | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                          |
| Timeout After                                                | Time in seconds after which the WMI query will be stopped (Default: 30 seconds). If the query times out, a collection error will be triggered either on the **Status** parameter or the Template's Collection Error Count parameter.                                                                                                                     |
| Report Execution Errors in Template's Collection Error Count | Enable this option if you want execution errors to be reported in the **Collection Error Count** parameter of the template. When disabled, execution errors are reported in the **Status** parameter of the **WMI Query Monitor**. Note that collection errors will not be reported in any way if this option and the **Status** parameter are disabled. |

At this time, you can run the **Dry Run** feature to simulate the execution of this Monitor on a specific host and therefore verify that the  output is conform to the expected result.

Refer to:

* [Content Parsing Monitors](./content-parsing-monitors.html) to know how to analyze the result of a WMI query.
* [Studio WMI Query](./X_WMIQUERY.html) for more information about the parameters available and the thresholds set by default.
* [Alert Messages and Actions](./alerts.html) to know how to configure alerts on parameters.
