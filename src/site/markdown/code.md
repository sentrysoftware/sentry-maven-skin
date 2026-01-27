keywords: code, syntax, highlighting, prism
description: Syntax highlighting for 290+ programming languages with copy-to-clipboard.

# Code Syntax Highlighting

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

Code blocks are automatically syntax-highlighted using [PrismJS](https://prismjs.com/).

## Basic Usage

Specify the language after the opening fence:

````md
```java
System.out.println("Hello, World!");
```
````

Every code block includes a **Copy** button for easy clipboard copying.

## Supported Languages

All [290+ PrismJS languages](https://prismjs.com/#supported-languages) are supported, including:

- **Web**: HTML, CSS, JavaScript, TypeScript, JSON
- **Backend**: Java, Python, Go, Rust, C#, Ruby, PHP
- **Config**: YAML, XML, TOML, INI, Properties
- **Shell**: Bash, PowerShell, Batch
- **Database**: SQL, GraphQL
- **And many more...**

Language components load automatically on demand.

## Examples

### Java

````md
```java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
```
````

### Shell Commands

````md
```bash
echo $HOME > /tmp/my-home.txt
```
````

### Shell with Output

````md
```shell-session
$ ls -l
total 0
-rw-r--r-- 1 user user 0 Jan 1 00:00 file.txt
```
````

### YAML

````md
```yaml
server:
  port: 8080
  host: localhost
```
````

### XML

````md
```xml
<dependency>
  <groupId>org.example</groupId>
  <artifactId>my-lib</artifactId>
</dependency>
```
````

### SQL

````md
```sql
SELECT * FROM users WHERE active = true;
```
````

### Docker

````md
```docker
FROM ubuntu:22.04
COPY . /app
CMD ["python", "/app/app.py"]
```
````
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

### Java

````md
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```
````

### Python

````md
```python
def greet(name: str) -> str:
    return f"Hello, {name}!"

print(greet("World"))
```
````

### TypeScript

````md
```typescript
interface User {
	name: string;
	age: number;
}

const greet = (user: User): string => `Hello, ${user.name}!`;
```
````

> **Note:**
>
> Syntax highlighting for fenced code blocks is available only when using version 3.10 (and later) of _maven-site-plugin_.

## Next Steps

- [Images](images.html) - Add screenshots and diagrams
- [UI Components](ui-components.html) - Tabs, accordions, and more
- [Doxia Features](doxia.html) - Include code from external files
