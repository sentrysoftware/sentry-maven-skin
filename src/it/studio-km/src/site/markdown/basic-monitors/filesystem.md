# File System

The purpose of the **File System Monitor** is to monitor a file system that is critical to the monitored system or application. Typically, a full file system will prevent an application from working properly.

To monitor a file system, you must configure the properties below:

| Property         | Description                                                                                                                                                                                                             |
| ---------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| File System Path | Enter the path of the file system to be monitored (Example: ```C:\```). You can use the *Pick a File System* button to select the file system from a list of file systems on a selected host.                           |
| Display Name     | Name to identify the **File System** instance in TrueSight Operations Management.                                                                                                                                       |
| Internal ID      | ID to be used to store the **File System** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the file system path provided but can be edited if needed. |
| Collect Schedule | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                         |

For more information about the parameters available and the thresholds set by default, refer to [Studio File System](../X_FILESYSTEM.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
