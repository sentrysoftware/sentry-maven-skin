# TrueSight CMA Policies

> This section explains how to download a **${project.name}** Template and all its Monitors as a configuration file and import it into a TrueSight CMA policy.

1. Create a Template from **Studio > Template** and provide the required information as detailed in
[Creating a Template](./hosts-templates.html#Step_2_Creating_Templates).

2. Scroll down to the bottom of the page and click the **Download** button (```proxmoxperf.cfg``` in this example), to export the Template, its Monitors and properties as a configuration file.

    ![Download a Template](./images/Export_Config_Button.png)

3. Import the Template into a CMA Policy:

    - From the CMA console, create a policy that will be deployed on the PATROL Agents that share the same criteria (tag, hostname, IP address, etc.).
    - In the **Configuration Variables** tab, click the action button ![inline](./images/TS_Action_Menu.png) and select **Import**
    - Locate and select the **${project.name}** Template (```proxmoxperf.cfg``` in this example) you wish to import into the policy
    - Verify the configuration values and change them if required.
    - In the **Monitoring** tab, click **Add Monitoring Configuration** and select **${project.name}**. The _Version_, _Monitor Profile_ and _Monitor Type_ are provided by default and can be modified if required
    - Configure the **Host** (_Display Name_, _Internal ID_, _System Type_, _System Credentials_, _SNMP Properties_, _Availability Check Settings_, etc.)
    - Scroll down to the **Templates** section to add an associated template. Provide the **Internal ID** of the Template (_proxmoxperf_ in this example). Provide the specific credentials and macros for the Template, if any, and set the Database properties that will be used by all the monitors of the Template. Finally, if **${project.name}** is configured to go through a proxy for processing Web Requests, you can choose to bypass the proxy. This can be useful when the resource is located on the internal network and the proxy refuses to serve it
    - Save the policy. The Template and all its properties are imported to the policy and ready to use.
