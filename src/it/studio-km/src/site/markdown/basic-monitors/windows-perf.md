# Windows Performance Counter

The purpose of the **Windows Performance Counter Monitor** is to simply monitor Windows Performance Counters.

**${project.name}** uses counters to collect performance data. Each counter is uniquely identified through its **Performance Object** and **Instance**. Once collected, the performance data is available in TrueSight with the **Value** parameter.

To monitor Windows Performance Counters, you must configure the properties below:

| Property            | Description                                                                                                                                                                                                                                                                                                                                                     |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Display Name        | Name to identify the **Windows Performance Counter** instance in TrueSight Operations Management.                                                                                                                                                                                                                                                               |
| Internal ID         | ID to be used to store the **Windows Performance Counter** Monitor configuration in the PATROL Agent configuration. This **Internal ID** is automatically generated based on the **Display Name** provided but can be edited if needed.                                                                                                                         |
| Collect Schedule    | How often new data is collected. A new collect can be performed from once every second, to once in a day. By default, the collect schedule is set to 2 minutes.                                                                                                                                                                                                 |
| Performance Object  | Name of the Windows performance object that contains the counter to be monitored (Example: ```Win32_PerfRawData_PerfOS_Processor```). You can use the *Pick a Performance Counter* button to pick a Performance Object, Instance and Counter from the list of existing Performance Counters on the selected host.                                              |
| Instance Name       | *(Optional)* Name of the instance to be monitored. This name is used to distinguish between multiple performance objects of the same type on a single device (Example: ```0```).                                                                                                                                                                                |
| Performance Counter | Name of the performance counter to be monitored (Example: ```PercentUserTime```).                                                                                                                                                                                                                                                                               |
| Rescaling           | *(Optional)*  How **${project.name}** will rescale the extracted value to have a more readable graph in TrueSight (Example: To display in megabytes values that are extracted in bytes, divide the values by 1048576 (1024*1024)). Leave this option set to **No Rescaling (default)** if you wish the solution to report on the performance counter raw value. |

For more information about the parameters available and the thresholds set by default, refer to [Studio Windows Performance Counter](../X_WINPERF.html).

Refer to [Alert Messages and Actions](../alerts.html) to know how to configure alerts on parameters.
