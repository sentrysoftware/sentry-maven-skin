# Operating the Console

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

The PATROL Agent runs Knowledge Modules (KMs). KMs create instances (*Monitors*) to represent the systems, devices and objects they monitor. For each monitored instance, parameters are collected at a regular interval to assess its health and performance. The *Console* in the Web-based interface of **${project.name}** shows all monitored instances and collected parameters, and their current status.

![Console Home Page](./images/Console_Overview.png)

The main view will adapt its content to the selected object.

## KMs

The default view (when you select the top-level *KMs* object) lists all of the KMs installed and running on the PATROL Agent and the main instances they have created (root instances).

The **TRIGGER DISCOVERY** button allows you to trigger a *Discovery* for all KMs. This is the equivalent of the *Force discovery* KM Command in the PATROL Classic Console.

The **REFRESH** button triggers a refresh of the data displayed in the page. It does not trigger a collect on the agent.

## Instances *(Monitors)*

When you select a monitored instance, the *Console* displays its collected parameters and its *Attached Monitors*, i.e. other monitored instances that are attached to it, if any.

![View of a Monitor in the Console](./images/Console_Instance.png)

*Attached Monitors* can be displayed in two ways:

* **Matrix View**: All instances of the same class are represented in a table, with the values of the collected parameters in different columns. This view allows you to have a quick overview of an entire class of *Monitors*.
* **Standard View**: All instances and their collected parameters are listed at once, with a bar representing their own *Sub-Monitors*. This view allows you to easily navigate the tree of all monitored instances.

The **DETAILS** button displays additional information about the selected instance (*Monitor*). The list of available properties depends on the KM the instance belongs to. If available, the list of properties includes a link to the online help of the corresponding class.

The **REFRESH** button triggers a refresh of the data displayed in the page. It does not trigger a collect on the agent.

The **COLLECT NOW** button triggers an actual collect on the agent for the selected instance. If no collector is available for the selected instance, the action takes place on its parent, and so on. The display is automatically refreshed after 5 seconds. If the collect takes more than 5 seconds to complete, the view will appear unchanged. Make sure to check the *Last Update* field in the parameters table to see whether they have been updated already.

Depending on the class and the KM the selected instance belongs to, other action buttons will be available.

* **PAUSE** and **RESUME**, on all **${project.name}** objects: to pause and resume the collect
* **EDIT IN STUDIO**, on all **${project.name}** objects: to display and edit the corresponding *Studio* object (*Host*, *Template* or *Monitor*)
* **RESET ERROR COUNT** (or **RESET LINE COUNT**, **RESET EVENT COUNT**, **RESET TRAP COUNT**), on applicable **${project.name}** objects: reset the corresponding parameter to zero. This is particularly useful on *Count* parameters that are not reset automatically and may stay in alarm forever.

Other actions may be available as KM developers expose them in **${project.name}**.

## Parameters

This view shows the values of the selected parameter, as stored over time in the history of the PATROL Agent, as a graph and as a table.

By default, the 500 last points are displayed. Each time you click on the **GET MORE DATA** button, you double the number of points, until you reach the limit of the PATROL Agent history (1 day by default, customizable in the [Agent Settings](subdir/agent.html)). Be careful: your browser may struggle to render thousands of points at once.

Note that best effort is made by **${project.name}** to interpret the units of the parameter and render *enumerations* values as such, rather than as simple graphs.

![View of the History of a Parameter in the Console](./images/Console_Parameter_Graph.png)

You can scroll and zoom the graph horizontally (on the time axis) using the mouse wheel or touch gestures.

Annotation points are rendered as a red asterisk on the graph itself. You need to click on the point to get the content of the annotation point. Annotation points are also displayed in the table.

The **Details** tab gives you additional information about the parameter, including its alert thresholds.

The **DOWNLOAD CSV** button lets you download the CSV corresponding to the data being displayed. This can be useful if you want to import such data in a spreadsheet.

In the same way as the instance (*Monitor*) view, the **REFRESH** button refreshes the data on the page to reflect the latest values and status collected by the PATROL Agent. The **COLLECT NOW** button triggers an execution of the collector(s) of the instance, or its parent if applicable, followed by a refresh of the page 5 seconds later.
