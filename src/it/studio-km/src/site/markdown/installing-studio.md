# Installing ${project.name}

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Installing **${project.name}** in your BMC environment can be performed by using the BMC Utility Package or by importing the monitoring solution into TrueSight. In both cases, you need to:

-	make sure that Java 1.8.00 or higher and a Java Runtime Environment (JRE) are installed on the same system that runs the PATROL Agent. You can download the Java Runtime Environment along with the KM on the [Sentry Software Website](https://www.sentrysoftware.com/products/km-monitoring-studio.asp#section-latest)

-	have installed and properly configured a PATROL Agent version 9.5 or higher

## Installing the KM

###  Using the BMC Utility Package

1. Download and extract the [BMC Utility Package](https://www.sentrysoftware.com/products/km-monitoring-studio.asp#section-latest) according to the platform you plan to install the solution on:

	- For UNIX/Linux systems: ```ins_ALL_<version>.tar```
	- For Windows systems: ```ins_WINDOWS_<version>.zip```

2. Download and extract the appropriate **${project.name}** [package](https://www.sentrysoftware.com/products/km-monitoring-studio.asp#section-latest) according to the platform you are using:

	- For UNIX/Linux systems: ```monitoring-studio-<version>-thorium.tar```
	- For Windows systems: ```monitoring-studio-<version>-thorium.zip```

	 	 <div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong>The packages are valid for all the PATROL components: Agent, Console, Console Server, etc.</div>

3. Download and extract the Java Runtime Environment [package](https://www.sentrysoftware.com/products/km-monitoring-studio.asp#section-latest) according to the platform you are using, if you want to install the JRE along with **${project.name}**:

	- ```jre_LINUX-SOLARIS_<version>.tar - JRE <version> for Linux Solaris```
	- ```jre_WINDOWS_<version>.zip - JRE <version> for Windows```


4. Browse to the **bmc_products** folder where the files have been extracted and launch the **setup.exe** program (Windows) or **setup.sh** script (UNIX)
5. Follow the standard step-by-step installation procedure (see BMC Documentation for details). At the **Products and Components to Install** step:
 	- select the monitoring solution: **```${project.name} <version>```**
	- select the JRE: **```Oracle Java <version> for <OS>```** program if you want to install it along with **${project.name}** (this option is only provided when you have previously downloaded the package)
6. Continue the installation process until it is complete.

###  Using TrueSight

Installing **${project.name}** through TrueSight is a 3-step process that consists in:

1. importing the monitoring solution
2. creating the installation package
3. installing the package

<div class="alert alert-info"><i class="icon-hand-up"></i><strong>Note: </strong>Make sure to have the appropriate access rights to the TrueSight console.</div>


#### Step 1 - Importing the Monitoring Solution

The TrueSight Central Monitoring Repository may include the current versions of **${project.name}** that you can use with BMC TrueSight. If the version available in the Repository does not correspond to the latest one, you will have to manually import it:

1. Log on to the TrueSight Central Monitoring Console
2. In the navigation pane, select **Administration > Repository**
3. Click the **Installation Components** tab

	![Installation Components](./images/TS_Install_Install_Components.png)
4. Check that the version of **${project.name}** is actually the latest one. If it is is, you may skip this step. Otherwise, download the latest version corresponding to your operating system (Windows or UNIX/Linux) available on the [Sentry Software Website](https://www.sentrysoftware.com/products/km-monitoring-studio.asp#section-latest)
5. Click **Import**
6. Select **Single solution**
7. Browse to the appropriate file depending on your operating system. The monitoring solution must be stored on the local computer from which you are running Central Monitoring Administration:
	- For UNIX/Linux systems: ```monitoring-studio-<version>-thorium.tar```
	- For Windows systems: ```monitoring-studio-<version>-thorium.zip```
8. Click **Import**. The selected archive file is imported to the repository and extracted
9. When the selected archive file is imported to the repository, click **Close**.


#### Step 2 - Creating the Installation Package

You can create the installation package that will be deployed to the managed systems directly from TrueSight Presentation Server:

1. Log on to the TrueSight Console
2. From the navigation pane, select **Administration > Repository**
3. Click the **Deployable Packages** tab
4. Click **Create a Deployable Package**
5. Select the operating system and platform for which you want to create a package. The components available in the repository for the selected operating system and platform are displayed

	![Selecting the Operating System and Platform](./images/TS_Package_OS_and_Platform.png)
6. Select the **${project.name}** solution to be included in the package, specify its version, and click **Next**

	![Selecting the Monitoring Studio Solution](./images/TS_Install_Package.png)
7. Specify the **Installation Directory** and click **Next**

	![Selecting the Installation Directory](./images/TS_Package_Install_Directory.png)
8. Provide the **PATROL 3.x Product Directory** and click **Next**

	![Specifying the PATROL 3.x Product Directory](./images/TS_Package_PATROL3_Directory.png)
9. Specify the BMC Product Startup Information and click **Next**

	![Specifying the BMC Product Startup Information](./images/TS_Package_StartupInfo.png)
10. Provide the following information:
 	- **Name**: Enter a unique name for the package
	- (Optional) **Description**: Enter a description of the package
	-	**Format**: Select a file compression format for the package

	![Providing the Installation Package Details](./images/TS_Package_Details.png)
11. Click **Save**
12. Click **Close**. The package is now available in the **Deployable Packages** list.


#### Step 3 - Installing the Package

1. Log on to the TrueSight Console
2. From the navigation pane, select **Configuration > Managed Devices**

	![Installing the Package - Managed Devices](./images/TS_Managed_Devices.png)
3. Locate the managed device on which you wish to install **${project.name}**, click its action button ![inline](./images/TS_Action_Menu.png) and select **Deploy and Install Packages**

	![Installing the Package - Locating the Managed Device](./images/TS_Managed_Devices_Menu.png)
4. Select the **${project.name}** package and click **Deploy and Install**

	![Installing the Package - Selecting the Monitoring Studio Package](./images/TS_Select_Package.png)
5. Wait for the installation to complete. You can click the ![inline](./images/TS_Refresh_btn.png) button to refresh the page and check the installation progress in the **Deploy Status** column.


## Accessing ${project.name} Web Interface

Once the KM is properly installed, you can access the Web interface to configure your monitoring environment, configure hosts to monitor, design templates and define monitors.

Enter the following URL in your Web browser: ```https://<patrol-agent>:<patrol-agent-port+262>/```

Where:

	- ```<patrol-agent>``` is the name of the host on which the PATROL Agent runs

and

	- ```<patrol-agent-port+262>``` is the number you obtain when you add 262 to the PATROL Agent port number. This is the port number used by default by **${project.name}**.

For example, if your PATROL Agent is named ```<MyPatrolAgent>``` and the PATROL Agent Port is ```3181```, the URL to access the **${project.name}** Web interface is: ```https://MyPatrolAgent:3443/```.
