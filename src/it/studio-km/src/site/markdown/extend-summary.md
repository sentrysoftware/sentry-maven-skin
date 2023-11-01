keywords: api, develop, dev, psl, angularjs
description: ${project.name} can be extended so that KM developers take advantage of its UI features and allows the users to interact with the KM.

# Extending ${project.name}

<div class="alert alert-info">
This section is targeted at PATROL KM developers, with a good knowledge of PSL and how KM works in a PATROL Agent.
</div>

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

**${project.name}** offers a [Console](console.html) feature that displays the monitored instances and parameters by all KMs running on the PATROL Agent.

It allows some basic interaction with the different KMs, like *Refresh Parameters* and *Force Discovery*. However, none of the *KM Commands* defined in the KMs and exposed in the former **PATROL Classic Console** are available.

To expose specific actions or configuration options, a KM must be updated so that it declares extensions to the ${project.name} Web UI:

* Specific icons for its instances
* Documentation
* Specific action buttons
* Additional configuration and report pages

This section explains how to declare such extensions.

## Basic Extensions (PSL only)

This part of the document details how a PSL developer can customize the behavior and interface of ${project.name} for a specific KM: icons, online help and parameters refresh. These customizations are achieved simply by setting special namespace variables in the PATROL Agent, using PSL scripts.

### Customizing Icons

To get ${project.name}'s Console to display a specific icon for a class of your KM (instead of the default icon), you need to set the ```/<class>/X_icon``` namespace variable for each class with a CSS class, typically [FontAwesome](https://fontawesome.com/icons?d=gallery&m=free) or [Glyphicon](https://getbootstrap.com/docs/3.3/components/#glyphicons), which are both available in ${project.name}.

You will typically set the *X_icon* variable for all your classes in the *prediscovery* or the *discovery* PSL script of your KM.

Example of PSL script:

```psl
set("/SW_MAIN/X_icon", "fas fa-toolbox");
set("/SW_DBQUERIES/X_icon", "fas fa-database");
```

### Customizing Online Help Links

The ${project.name} Console will display links to online help pages for classes that set the ```/<class>/X_help``` namespace variable. The *X_help* variable can be set with either a link to a Web page served by the PATROL Agent, or an external link on the Web.

You will typically set the *X_help* variable for all your classes in the *prediscovery* or the *discovery* PSL script of your KM.

Example of PSL script:

```psl
# Specify the online help link for the SW_MAIN class (local link)
set("/SW_MAIN/X_help", "/swsy/help/");

# Specify the online help for the SW_DBQUERIES class (Internet link)
set("/SW_DBQUERIES/X_help", "https://www.sentrysoftware.com/library/swsy8700/sw_db_queries.htm");
```

### Modifying the Behavior of the [COLLECT NOW] Button

For each PATROL object instance, the ${project.name} Console provides a **[COLLECT NOW]** button. When clicked, this button triggers the execution of the *collector(s)* of the object instance. If the object instance does not have any *collector*, ${project.name} searches for *collectors* in the ancestors of the object instance and triggers the execution of the *collector(s)* of the first ancestor that has at least one *collector*.

KM developers can override this default behavior and specify what parameters or *collectors* needs to be executed when the user clicks on the **[COLLECT NOW]** button. This is achieved by setting the *X_refreshParameters* namespace variable, either on a class or on an instance of a class.

Possible values of *X_refreshParameters*:

* ```[<list of paths to collectors>, ...]```, to specify a list of *collectors* to be executed when the user clicks **[COLLECT NOW]**
* ```""```, empty string to use the default behavior
* ```0```, to disable the **[COLLECT NOW]** button (because it's not applicable to this instance or class)

The *X_refreshParameters* namespace variable can be set at any moment in the KM lifecycle: in the *prediscovery*, in the *discovery*, in a *collector* or a *recovery action* script. The Web UI will adapt the **[COLLECT NOW]** button based on the value of *X_refreshParameters* at the moment the page is displayed to the user. This allows the developer to change the behavior of the **[COLLECT NOW]** button dynamically, depending on the current state of the monitored instance. For example, you may want to disable the **[COLLECT NOW]** button when the object instance is OFFLINE.

Example of PSL script:

```psl
# Triggers the execution of the proColl collector of the /SW_SENTRY/SENTRY8 instance
# when the user clicks on [COLLECT NOW] for any instance of the SW_PROCESSES class
set("/SW_PROCESSES/X_refreshParameters", ["/SW_SENTRY/SENTRY8/proColl"]);

# Disables the [COLLECT NOW] button on the main class
set("/SW_SENTRY/X_refreshParameters", 0);

# Specify the collector for one specific instance
set("/SW_STRINGS/error/X_refreshParameters", ["/SW_FILES/myLog/fileColl"]);
```

## Advanced Extensions

This part of the document details how a developer can add pages to the user interface of ${project.name} with advanced user interactions. This part involves PSL scripting and Web development with JavaScript and AngularJS.

### Internal Architecture

In order to extend ${project.name}, you need to understand its internal architecture and how the Web UI works.

![${project.name} Web Server Internal Architecture](images/Internal_Architecture.png)

${project.name} starts a HTTP server through its *Java Collection Hub*. This HTTP server is configured to serve 2 types of resources:

* Static files (typically HTML, CSS and JS, but also debug files for example)
* REST API endpoints, in the form of PSL functions executed on the PATROL Agent to respond to HTTP requests

To summarize, the ${project.name} Web application is made of static HTML, CSS and JS files where the JS code makes HTTP requests to the REST API, which is made of PSL functions executed on the PATROL Agent.

### Interactive UI with AngularJS

The front-end of ${project.name} is developed with [AngularJS](//angularjs.org/) 1.x (not to be confused with the most recent *Angular*). To extend the Web UI of ${project.name}, a developer must first understand how *AngularJS* works. It is strongly recommended to have a good expertise with *AngularJS* before working on ${project.name} extension.

<div class="alert alert-warning">Any error in your Javascript code, even a simple syntax error, can break the UI of ${project.name} entirely. If the interface no longer loads in your browser, use the debugging features of your browser to troubleshoot your code.</div>

The main steps when extending ${project.name} are:

* Declare and implement additional REST API endpoints in PSL that will be invoked by the Web UI to interact with your KM
* Declare an [AngularJS module](//docs.angularjs.org/guide/module)
* Create an [AngularJS service](//docs.angularjs.org/guide/services) that implements methods that query your REST API endpoints
* Create [components](//docs.angularjs.org/guide/component) that implement parts of your UI
* Declare routes in [\$routeProvider](//docs.angularjs.org/api/ngRoute/provider/$routeProvider) that map URLs to your main components

### Declaring REST API Endpoints in PSL

In ${project.name}, the REST API backend is implemented in PSL. For example, a specific PSL function is invoked when the user browser requests the ```/rest/agent``` URL. The functions called on specific URL requests are called *endpoints*.

In the PSL scripts of your KM, you can declare *endpoints*, PSL functions that will be called when specific HTTP requests are made to the HTTP server of ${project.name}.

To declare your own *endpoints*, you need to set the ```/<class>/X_httpMapPsl``` namespace variable. The *X_httpMapPsl* variable must be set to a PSL list formatted as below:

```psl
set("/<class>/X_httpMapPsl", [
    "<url>;<PSL library>;<PSL function>",
    ...
]);
```

where:

* ```<url>``` is the URL for which the specified PSL function will be invoked. All requests on this URL and its sub-resources ("subdirectories") will trigger the execution of the specified PSL function.
* ```<PSL library>``` is the name of the compiled PSL library which contains the code of the specified PSL function.
* ```<PSL function>``` is the name of the PSL function to execute when the specified URL is requested. This function must be *exported* in the specified compiled PSL library.

Notes:

* The *X_httpMapPsl* namespace variable can be set with a list of multiple REST API endpoints.
* You need **not** to set this namespace variable for every class of your KM. You must set ```/<class>/X_httpMapPsl``` on only one class that will be loaded and activated.
* **The PSL script that sets this namespace variable must be the pre-discovery script of a class of your KM, to ensure it is executed before the discovery of ${project.name}.**

The specified PSL endpoint functions are called with the below arguments:

* ```method```: GET, PUT, POST or DELETE
* ```path```: the requested URL path
* ```query```: the part behind the '?' in the URL
* ```headerList```: the list of headers in the HTTP request
* ```requestBody```: the body of the HTTP request
* ```username```: the username if the user is authenticated
* ```remoteAddress```: the address of the browser or client performing the HTTP request
* ```readOnly```: whether the request is made by a user that has read-only access (and therefore no full access)

The specified PSL function must return a PSL list that contain the elements of the HTTP response:

* First line: the HTTP response code (200, 400, 500, etc.)
* HTTP response headers: one header per line (e.g.: ```["Content-type: application/json"]```)
* Empty line as a separator between the headers and the response body
* HTTP response body

Example of a REST API endpoint PSL function:

```psl
export function restEndpointExample;
function restEndpointExample(method, path, query, headerList, requestBody, username, remoteAddress, readOnly) {

  # Only supported method is GET
  if (method == "GET") {
    return [
      200,
      "Content-type: application/json; charset=utf-8",
      "\n",
      "{ \"result\": \"An example\", \"success\": true, \"time\": ".time()." }"
    ];
  }

  # For the rest, return 400 Bad Request
  return [
    400,
    "Content-type: application/json; charset=utf-8",
    "\n",
    "{ \"result\": \"Bad request\", \"success\": false, \"time\": ".time()." }"
  ];

}
```

The above PSL code must be compiled into a PSL library. If the PSL source file is **restLibraryExample.psl**, it will be compiled into a library with the below command:

```batch
psl -l restLibraryExample.psl
```

Then, this REST API endpoint PSL function must be declared with the *X_httpMapPsl* namespace variable in the *prediscovery* PSL script of your KM, as below:

```psl
# Map /rest/example to restEndpointExample
set("/EXAMPLE_CLASS/X_httpMapPsl", [
  "/rest/example;restLibraryExample;restEndpointExample"
]);
```

After a restart of the PATROL Agent, the *prediscovery* of your KM sets *X_httpMapPsl* and the *discovery* of ${project.name} takes this into account when starting its HTTP server. Sending an HTTP request to get ```/rest/example``` will then trigger the execution of our *restEndpointExample()* PSL function. Such request would typically be made from the user browser, in the frontend Web application.

Example with *curl*:

```shell-session
$ curl -k https://localhost:3443/rest/example
{ "result": "An example", "success": true, "time": 1583351013 }

$ curl -k https://localhost:3443/rest/example/sub-example/1
{ "result": "An example", "success": true, "time": 1583351045 }

$ curl -k -X POST https://localhost:3443/rest/example
{ "result": "Bad request", "success": false, "time": 1583351070 }
```

When writing your REST API endpoint functions, you may leverage utility functions declared in the **X_restapi_utils** PSL library, which ships with ${project.name}. Simply adds the below line at the very beginning of your PSL source files:

```psl
requires X_restapi_utils;
```

Here are some useful functions in **X_restapi_utils**:

| Function | Description |
|---|---|
| ```list2JsonArray(list)```  | Converts the specified PSL *list* into a JSON array. <br/> Example: <br/> ```list2JsonArray(["first", "second"])``` will return ```["first", "second"]``` |
| ```string2Json(string)```  | Returns a JSON-encoded string representation of the specified string, including the surrounding quotes. Example: ```"my \"quoted string\" with\nmultiple lines"```  |
| ```string2FullJson(string)```  | Full (but slower) version of ```string2Json()```, properly encoding all non-printable characters. Example: ```"Special \u000d \u001f"``` <br/>Use this function only when there is a risk that the string to encode contains non-printable chars other than newline (```\n```) and tab (```\t```)  |
| ```uriDecode(uri)``` | Decodes the specified URI. <br/>Example: ```uriDecode("Studio+rulez%21")``` will return ```Studio rulez!```  |
| ```returnSimpleJsonResponse(httpResponseCode, json)``` | Returns a properly formatted HTTP response (as described above) with the specified JSON document.<br/>Example:<br/>```return returnSimpleJsonResponse(200, "{\"result\": \"success\"}");```  |
| ```returnSimpleInformation(message)``` | Returns a HTTP/200 response with the specified message. <br/>Example:<br/>```return returnSimpleInformation("It worked!");```  |
| ```returnSimpleError(message)``` | Returns a HTTP/500 response ("Internal Error") with the specified message. <br/> Example: <br/> ```return returnSimpleError("It failed miserably...");```  |
| ```returnBadRequest(message)``` | Returns a HTTP/400 response ("Bad request") with the specified (optional) message. <br/>Example: <br/> ```return returnBadRequest();```  |

<div class="alert alert-warning">You may need to recompile your PSL library with your REST API PSL endpoint functions when you install a new version of ${product.name}, which may ship with a newer version of <b>X_restapi_utils</b>.</div>

### Exposing Additional Web Resources (HTML, CSS, JS)

When extending the Web UI for your KM, you will need to expose your front-end code (JS, HTML, CSS and images) to the user browser.

From the PSL code of a KM, you can configure the HTTP server of ${project.name} to serve the content of a directory, a ZIP or a JAR file, under a specified "virtual" folder.

This is achieved by setting the ```/<class>/X_httpMapResources``` namespace variable during the *pre-discovery*. The value of ```X_httpMapResources``` is a PSL list with each line containing an entry to map:

```csv
<virtual folder>;<path to resource>
```

Example of PSL script:

```psl
set("/SW_MAIN/X_httpMapResources", [
  "/swsy;./lib/SW_web.jar"
  "/swsy/help;./lib/SW_help"
]);
```

Notes:

* Path to the resource can be a directory, a ZIP or a JAR file
* Path to the resource is relative to **$PATROL_HOME** (except if specified path is absolute)
* In the above example, an HTTP request to ```https://localhost:3443/swsy/index.html``` will return the content of **index.html** in **$PATROL_HOME/lib/SW_web.jar**.
* In the above example, an HTTP request to ```https://localhost:3443/swsy/help/css/bootstrap4.min.css``` will return the content of **$PATROL_HOME/lib/swsy/help/css/bootstrap4.min.css**
* You need **not** to set this namespace variable for every class of your KM. You must set ```/<class>/X_httpMapResources``` on only one class that will be loaded and activated.
* **The PSL script that sets this namespace variable must be the pre-discovery script of a class of the KM, to ensure it is executed before the discovery of ${project.name}.**

#### Authentication

By default, resources that are exposed through ```X_httpMapResources``` do not require the user to be authenticated to retrieve them. Since the authentication is handled by the Web UI, it is necessary for its HTML, CSS, JS and other resources to be accessible before being authenticated.

If you need to expose other file resources, non-static Web resources like subdirectories of the PATROL Agent, or other parts of the local filesystem, it is strongly recommended to enforce authentication before letting anyone read these files.

Instead of declaring resources to be mapped with ```X_httpMapResources```, you will use ```/<class>/X_httpMapResourcesSecure```. This namespace variables behaves the very same way as ```X_httpMapResources```, but accessing these resources will require the proper authentication token (which can be obtained through the Web UI or OAuth 2.0).

Notes:

* All resources (with or without authentication) are read-only.
* Authorization (which user can authenticate) is managed in the [${project.name} settings](agent.html).

### Loading JS and CSS

For the Web UI to leverage additional JS and CSS code of your own, it is necessary to ensure the JS and CSS files are loaded in the browser.

To do so, you will declare such resources in the ```/<class>/X_browserLoad``` namespace variable. The ```X_browserLoad``` variable contains a PSL list of resource paths that will be loaded dynamically by the browser when the user connects to the Web UI, as in the example below:

```psl
set("/SW_MAIN/X_browserLoad", [
    "/swsy/js/swsy-service.js",
    "/swsy/components/swsy-config/swsy-config.js",
    "/swsy/components/swsy-config/swsy-config.css"
]);
```

Notes:

* Paths are *virtual paths*, i.e. accessible by the browser through ${project.name}'s Web server.
* While not recommended, absolute URLs are supported and can reference CDNs for common resources (like Bootstrap, jQuery, Angular, etc.)
