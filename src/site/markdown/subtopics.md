# Sub-topics

It is possible to specify a 2nd level of hierarchy in the pages of the documentation:

```raw
+ Menu
|
+--- Item (topic)
|
+--+ Item (topic)
   |
   +--- Item (sub-topic)
   |
   +--- Item (sub-topic)
```

Above *menus* define sections of the documentation (Installation Guide, User Guide, Reference Guide, for example). *Items* are actual pages and can "contain" other items (pages), as in the **site.xml** example below:

```xml
...
    <menu name="Using MetricsHub">
      <item name="General Concepts" href="general-concepts.html"/>
      <item name="Resources and Connectors" href="resource-connectors.html"/>
      <item name="Configuring MetricsHub" href="m8b-settings.html"/>
      <item name="Basic Monitors" href="basic-monitors/index.html">
        <item name="Filesystem" href="basic-monitors/filesystem.html" />
        <item name="Process" href="basic-monitors/process.html" />
        <item name="SNMP Trap" href="basic-monitors/snmp-trap.html" />
        <item name="Windows Event" href="basic-monitors/windows-event.html" />
        <item name="Windows Performance Counter" href="basic-monitors/windows-perf.html" />
        <item name="Windows Service" href="basic-monitors/windows-service.html" />
      </item>
      ...
    </menu>
...
```

In the above example, the *Basic Monitors* page is a chapter of the documentation that groups several other pages. The Sentry Maven Skin will automatically add links to all subtopics in the parent page.

> **Note**
> The Sentry Maven Skin does not support a 3rd level of hierarchy.

