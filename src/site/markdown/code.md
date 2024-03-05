keywords: code, syntax, highlighting, coloring
description: ${project.name} includes syntax highlighting for any code block that specifies the language

# Code Syntax Highlighting

Fenced code blocks will be syntax-highlighted using [PrismJS](https://prismjs.com/). The language of the code block must be specified as in the below example:

````md
```java
System.out.println("Hello, World!");
```
````

Each block of code can be easily copied to the system's clipboard with a dedicated `[Copy]` button.

## Supported languages

### Command lines (Linux)

````md
```bash
echo $HOME > /tmp/my-home.txt
```
````

### Command lines (Windows)

````md
```batch
echo %USERNAME% > username.txt
```
````

### Command lines (with output)

````md
```shell-session
$ ls -l
test: Not found
```
````

### CSS

````md
```css
body.my-theme > a {
  color: #012345;
}
```
````

### Docker file

````md
```docker
FROM ubuntu:22.04
COPY . /app
RUN make /app
CMD python /app/app.py
```
````

### HTML

````md
```html
<a href="https://onehome.org" title="Moving">OneHome</a>
```
````

### JavaScript

````md
```js
function printPage() {
  window.print();
}
```
````

### JSON

````md
```json
{
	"myArray": [3, 1, 4, 1, 5, 2, 9],
	"other": null
}
```
````

### Markdown

````md
```md
This is a **bold** move, [do it!](https://sentrysoftware.org).
```
````

### PowerShell

````md
```ps
Get-CimInstance -ClassName Win32_OperatingSystem | Invoke-CimMethod -MethodName Shutdown
```
````

### PSL

````md
```psl
print("home: ".get("/patrolHome")."\n");
```
````

### Regular Expressions

````md
```regex
[Bb]rainf\*\*k
```
````

### SQL

````md
```sql
SELECT value FROM table WHERE name="test" -- Testing
```
````

### xml

````md
```xml
<item name="test">42</item> <!-- Test -->
```
````

### YAML

````md
```yaml
myArray: [3, 1, 4, 1, 5, 9, 2]
other:
  main: null # This is null
  rest: "nothing"
```
````

> **Note:**
>
> Syntax highlighting for fenced code blocks is available only when using version 3.10 (and later) of *maven-site-plugin*.
