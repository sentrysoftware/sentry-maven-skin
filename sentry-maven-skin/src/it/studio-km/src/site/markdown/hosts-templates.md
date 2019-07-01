# Hosts and Templates

>  This section provides general information about the configuration properties of Hosts and Templates.

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

To monitor technologies or applications with **${project.name}**, you need to:

  1. Define the host(s) on which the technology or application to be monitored is installed
  2. Create monitoring templates
  3. Apply the monitoring templates to the relevant host(s).

These operations are performed from the **Studio** menu of the **${project.name}** Web interface.

## Step 1: Defining Hosts

### Host Type

Hosts define the systems to be monitored with **${project.name}** and can be of three types:

* Linux/UNIX
* Windows
* Other

The **Templates** available for hosts depend on both the host type and the template type:

* Templates for **All Systems** apply to any host type
* Templates for **Windows Systems** apply to **Windows** hosts only
* Templates for **Linux/UNIX** apply to **Linux/UNIX** hosts only.

The list of available Monitors will depend on the host system type.

### Multiple Hosts

**${project.name}** allows you to monitor several hosts that share the same characteristics (system type, credentials, templates, etc.) with one single host *configuration object* by simply adding the hostnames to the host *configuration object*.

Hosts can either be added one at a time or in bulk. To add hosts in bulk, you will have to enter a list of hostnames (one entry per hostname) using one of the three formats supported:

    # Comments are ignored
    # Hostname TrueSight Device FQDN
    node01-clusterA clusterA.acme.com
    172.16.8.200 clusterA.acme.com

    # Hostnames Only
    node01-clusterA
    172.16.8.200

    # Hostname,TrueSight Device FQDN
    node01-clusterA,clusterA.acme.com

  _The same hostname/IP address may be used across multiple Hosts. FQDNs are used to create the devices in TrueSight. FQDNs are optional but, when provided, their number must match the number of hostnames._

Each specified hostname will be monitored independently in the Console and in TrueSight with a separate Host instance.

This feature is particularly useful when monitoring lots of similar hosts.

### Host Properties

| Field                     | Description                                                                                                                                                                                                                                                                                                     |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Display Name**          | Name to identify the host instance in TrueSight Operations Management.                                                                                                                                                                                                                                          |
| **Internal ID**           | ID to be used to store the Host configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                            |
| **System Type**           | Operating system running on the host to be monitored. The **System Type** is either **Windows**, **UNIX/Linux**, or **Other**.                                                                                                                                                                                  |
| **Hosts**                 | List of **Hostnames** or **IP addresses** of the hosts to be monitored and their associated **TrueSight Device FQDN**.                                                                                                                                                                                          |
| **Place Host Under a Host Group** | If checked, the host(s) will be created under a Host Group (X_HOSTGROUP class). This is usually used to group multiple hosts under a single instance. |
| **System Credentials**    | System Credentials are the default credentials used by the Monitors to perform operations on the monitored host (connect with SSH, or WMI on Windows, get the list of processes, etc.). While optional, it is recommended to provide valid system credentials.                                                  |
| **SNMP**                  | *(Optional)* Enable SNMP if this protocol will be used for monitoring or availability check. You will then have to specify the SNMP version (**SNMP v1**, **SNMP v2c**, **SNMP v3 - No Authentication**, **SNMP v3 - MD5 Authentication**, or **SNMP v3 - SHA Authentication**) and its corresponding settings. |
| **Availability Check**    | *(Optional)* Checks to be performed to assess the availability of the host.                                                                                                                                                                                                                                     |
| **Collect Schedule**      | *(Optional)* How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                    |
| **Parameters and Alerts** | *(Optional)* Alerts to be triggered when the Host availability status is **Unknown** or **Unreachable**.                                                                                                                                                                                                        |

Once saved, the corresponding **X_HOST** instance will appear in the Console. If the configured Host contains several hostnames, several instances will be created.

## Step 2: Defining Templates

### Creating a Template

**${project.name}** allows you to design templates to monitor devices, systems or applications on several hosts. Templates are attached to one or several hosts. They contain the list of monitors to assess the health and performance of the targeted technology or application (CLIs, REST API, SNMP, WBEM, JDBC, etc.) and additional credentials.

To create templates, select the **Studio** menu, click **New Template...** and provide the following information:

| Field                           | Description                                                                                                                                                                                                                                                                                                                                                                                                                       |
| ------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Template Name**               | Name of the technology you wish to monitor (for example: MySQL Server, Huawei OceanStor 9000, Skype for Business, Veeam Backup, etc.)                                                                                                                                                                                                                                                                                             |
| **Internal ID**                 | ID to be used to store the Template configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Template Name** provided but can be edited if needed.                                                                                                                                                                                                                         |
| **Applies to Host System Type** | Host system type to which the template will apply (**Linux/UNIX only**, **Windows only**, or **All systems**). This setting can not be modified later if the configured Monitors are compatible with this specific host system type.                                                                                                                                                                                           |
| **Required Credentials**        | Declare the credentials to be used by the Monitors to assess the health and performance of the targeted technology or application. If, for example, you are creating a mySQL monitoring template, you can declare the **db_admin** credentials that will be used by each monitor to run queries. Although the **Required Credentials** will be set at the Host level, you can specify a default username and password to be used. |
| **Macros**                      | Macros to be used by the Monitors.                                                                                                                                                                                                                                                                                                                                                                                                |
| **Database**                    | *(Optional - Database Monitoring Only)* Type of database used by the **Database Query** Monitor. Possible values:  **No Database**, **Microsoft SQL Server**, **MySQL Server**, **Oracle Database Server**, **PostgreSQL**, **Other Database**.                                                                                                                                                                                   |
| **Parameters and Alerts**       | *(Optional)* Alerts to be triggered when errors are reported on the **Collection Error Count** parameter. This parameter reports all data collection-related errors that occurred during the collection process of the Template and its Monitors and therefore prevent **${project.name}** from monitoring properly. By default, an alarm is triggered when an error occurs.                                                                                        |
| **Monitors**                    | Monitoring actions to be performed in the Template. Refer to the sections [Basic Monitors](./basic-monitors.html), [Monitors with Content](./content-monitors.html), [Content Parsing Monitors](./content-parsing-monitors.html), and [Dynamic Instances](./dynamic-instances.html) to learn more about the available Monitors and how to configure them.                                                                         |

Once the above information is provided, click **Save**. You can then apply your Template to your hosts. An instance of the **X_TEMPLATE** class is created each time a host is set to use the Template.

### Importing a Template

**${project.name}** allows you to an import an existing template that can be deployed on a different Agent.

To import a template, select the **Studio** menu, click **Import Template**, then locate and select the template you wish to import. The template, including all its properties, will automatically listed in the **Studio > Template** page and ready to use.

**${project.name}** verifies that the Internal ID of the imported template is unique. If a conflict occurs with an existing Internal ID, you will be prompted to change the template's name before being allowed to import it.


## Step 3: Applying Templates to Hosts

To apply templates to hosts:

1. In the **${project.name}** Web interface, select the **Studio** menu.
2. In the **Monitored Hosts** section, click the **Host** to which you wish to apply templates.
3. In the **Templates** section, enable the Template(s) to apply to the host. If a Template does not appear, verify that the value set for the **System Type** of the host is the same as the one set in the **Applies to Host System Type** field while configuring the Template.
4. If **Required Credentials** have been declared while configuring the **Template**, set the **Username** and **Password** to be used by the Monitors to assess the health and performance of the targeted technology or application.
5. If **Macros** are declared in the **Template**, enter their value.
5. Click **Save**.

## Additional Information

### Availability Checks

Availability checks are verifications to be performed to assess the availability of the host. You can combine any of the following options.

<table>
  <tr>
    <th>Check</th>
    <th>Description</th>
  </tr>

  <tr>
    <td><b>Hostname Resolution</b></td>
    <td>
      <p>Resolves the hostname of the monitored host to an IP address, or perform a reverse lookup on the specified IP address.</p>
      <p>If an IP address is specified, this check reports the Host as unavailable when the <em>reverse lookup</em> fails. This is to ensure that the corresponding FQDN can be retrieved and exposed to BMC TrueSight.</p>
    </td>
  </tr>

  <tr>
    <td><b>Ping</b></td>
    <td>
      <p>Pings the monitored host.</p>
      <p>
        For the <em>Ping</em> check to be successful, the targeted host must respond to at least one ping command during each collection cycle.
      </p>
    </td>
  </tr>

  <tr>
    <td><b>SNMP</b></td>
    <td>
      <p>Performs an SNMP query.</p>
      <p>
        For the <em>SNMP Availability Check</em> to be successful:
        <ul>
          <li>the SNMP credentials must be provided</li>
          <li>if using SNMP v1, the PATROL Agent configuration variable</li> <code>/snmp/support</code> value must be set to <code>yes</code>
          <li>the targeted host must respond to a <code>GETNEXT</code> request on either the OID <code>1.3.6.1</code> or the OID <code>1.3.6.1.4.1</code></li>
        </ul>
      </p>
    </td>
  </tr>

  <tr>
    <td><b>WMI</b></td>
    <td>
      Performs a simple WMI query on the monitored host.
    </td>
  </tr>

  <tr>
    <td><b>TCP</b></td>
    <td>
      <p>Connects to the specified TCP port.</p>
      <p>The check will fail in nothing is listening on the remote port. No data is actually sent and the connection is closed immediately.</p>
    </td>
  </tr>
</table>

### Credentials

Credentials required to perform certain monitoring operations, like a database connection, or a REST authentication, are *declared* at the **Template** level for all its related **Monitors**.

Unless a default username and password have been set in the **Template**, you will be asked to provide a valid username and password when applying the **Template** to the **Host**. Credentials are therefore *declared* at the **Template** level and *configured* at the **Host** level.

<div class="alert alert-danger">It is not recommended to set default username and password in the Template in production environments where credentials are tied to hosts and not to applications.</div>

**Monitors** may not require template-specific credentials and can always use *System Credentials*, which are available for all monitored hosts.

### Databases

To monitor an application with a database, a **Template** might need to perform SQL queries. In that case, the required credentials would have to be declared and the database type specified at the **Template** level. You will only be able to create **Database Query** Monitors when the database type is specified.

You will be asked to provide the database connection settings when applying the **Template** to the **Host**.

### Embedded Files

Embedded Files are text files that are saved in the **Template** configuration itself (i.e. as a PATROL configuration variable). If **Monitors** in the **Template** require a file (like a script file to be executed, for example), using Embedded Files makes sure the file is distributed along with the **Template** configuration (through TrueSight CMA, or just with a classic configuration export).

Embedded Files are uploaded at the **Template** level:

![Adding an Embedded File in a Template](./images/EmbeddedFiles-Template.png)

and referred to from a **Monitor** with the `%{FILE:<filename>}` macro:

![Referring to an Embedded File](./images/EmbeddedFiles-Nagios.png)

When the **Template** is used to monitor a **Host**, its Embedded Files are stored in a temporary directory. When the **Monitor** is run, the `%{FILE:<filename>}` macro is replaced with the path to the corresponding temporary file.

The below **Monitors** support the `%{FILE:<filename>}` macro:

* Command Line
* Nagios Plugin

**Example:**

To execute the `check_cpu_stats.sh` script, you first add it as an Embedded File in the Template. Then, you create a **Nagios Plugin Monitor** with the  command line: `%{FILE:check_cpu_stats.sh}`

### Macros

Macros are special variables that are declared in the **Template** and that can be customized for each **Host**. Macros are particularly useful when designing templates that will apply to slightly different environments.

You can for example declare a **PATH** macro in the **Template**. All the **Monitors** (typically **File**, **Folder** or **Command Lines**) in this **Template** can use this `%{PATH}` macro.

Unless a default value was provided in the **Template**, you will have to specify the value of the macros when applying the **Template** to a **Host**.
