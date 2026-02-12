keywords: code, syntax, highlighting, prism, doxia, markdown, xdoc, apt
description: Syntax highlighting for 290+ programming languages with copy-to-clipboard.

# Code Syntax Highlighting

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

Code blocks are automatically syntax-highlighted using [PrismJS](https://prismjs.com/). All [290+ PrismJS languages](https://prismjs.com/#supported-languages) are supported — language components load automatically on demand.

Every code block includes a **Copy** button for easy clipboard copying.

## Syntax by Source Format

The Maven Site Plugin supports multiple source formats through Doxia. Each format has its own syntax for code blocks:

> [!TABS]
>
> - Markdown
>
>   Use fenced code blocks with the language identifier after the opening fence:
>
>   ````md
>   ```java
>   public class Hello {
>       public static void main(String[] args) {
>           System.out.println("Hello!");
>       }
>   }
>   ```
>   ````
>
>   Renders as:
>
>   ```java
>   public class Hello {
>       public static void main(String[] args) {
>           System.out.println("Hello!");
>       }
>   }
>   ```
>
> - XHTML
>
>   Use the `<pre><code class="language-xxx">` pattern:
>
>   ```html
>   <pre><code class="language-java">public class Hello {
>       public static void main(String[] args) {
>           System.out.println(&quot;Hello!&quot;);
>       }
>   }</code></pre>
>   ```
>
>   > [!TIP]
>   > Content inside `<code>` must be HTML-escaped (`<` → `&lt;`, `>` → `&gt;`, `&` → `&amp;`, `"` → `&quot;`).
>
> - Java (Doxia Sink)
>
>   When generating documentation or Maven reports programmatically with the [Doxia Sink API](https://maven.apache.org/doxia/developers/sink.html), use this pattern to produce syntax-highlighted code blocks:
>
>   ```java
>   SinkEventAttributes attrs = new SinkEventAttributes();
>   attrs.addAttribute(SinkEventAttributes.CLASS, "language-yaml");
>
>   sink.verbatim(attrs);
>   sink.rawText("<code class=\"language-yaml\">");
>   sink.text("object: # this is a YAML object\n");
>   sink.text("  pi: 3.14\n");
>   sink.rawText("</code>");
>   sink.verbatim_();
>   ```

## Examples

The below examples all use Markdown to demonstrate syntax highlighting of various languages.

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
mvn clean install site
echo $HOME > /tmp/my-home.txt
```
````

### Shell with Output

Use `shell-session` to show commands with their output:

````md
```shell-session
$ ls -l
total 0
-rw-r--r-- 1 user user 0 Jan 1 00:00 file.txt
```
````

### XML

````md
```xml
<dependency>
    <groupId>org.sentrysoftware.maven</groupId>
    <artifactId>sentry-maven-skin</artifactId>
    <version>7.0.00</version>
</dependency>
```
````

### YAML

````md
```yaml
server:
  port: 8080
  host: localhost
database:
  url: jdbc:postgresql://localhost/mydb
```
````

### JSON

````md
```json
{
  "name": "my-project",
  "version": "1.0.0",
  "dependencies": {
    "lodash": "^4.17.21"
  }
}
```
````

### SQL

````md
```sql
SELECT u.name, COUNT(o.id) AS order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE u.active = true
GROUP BY u.name;
```
````

### Python

````md
```python
def greet(name: str) -> str:
    """Return a greeting message."""
    return f"Hello, {name}!"

if __name__ == "__main__":
    print(greet("World"))
```
````

## Configuration

Code highlighting and copy features can be configured globally or per-page:

| Setting              | Description                                | Default                        |
| -------------------- | ------------------------------------------ | ------------------------------ |
| `syntaxHighlighting` | CSS selector for code blocks to highlight  | `pre > code[class*=language-]` |
| `copyToClipboard`    | CSS selector for elements with copy button | `pre`                          |

### Disable Syntax Highlighting

To disable PrismJS syntax highlighting:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <syntaxHighlighting>false</syntaxHighlighting>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   syntaxHighlighting: false
>
>   # Plain Text Page
>
>   Code blocks won't be syntax-highlighted.
>   ```

### Disable Copy Button

To remove the copy-to-clipboard button:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <copyToClipboard>false</copyToClipboard>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   copyToClipboard: false
>
>   # No Copy Buttons
>
>   Code blocks won't have copy buttons.
>   ```

### Custom Selectors

Use CSS selectors to control which elements get each feature:

> [!TABS]
>
> - Site-Wide (site.xml)
>
>   ```xml
>   <custom>
>     <!-- Only highlight Java and XML code -->
>     <syntaxHighlighting>pre > code[class*=language-java], pre > code[class*=language-xml]</syntaxHighlighting>
>
>     <!-- Add copy button to code and specific divs -->
>     <copyToClipboard>pre, .copyable</copyToClipboard>
>   </custom>
>   ```
>
> - Per-Page (Frontmatter)
>
>   ```markdown
>   syntaxHighlighting: pre > code[class*=language-java]
>   copyToClipboard: pre, .copyable
>
>   # Custom Code Settings
>
>   Only Java is highlighted, copy button on pre and .copyable.
>   ```

## Language Reference

Common languages and their fence identifiers:

| Category    | Language         | Markdown Fence Identifier     |
| ----------- | ---------------- | ----------------------------- |
| **JVM**     | Java             | <code>```java</code>          |
|             | Kotlin           | <code>```kotlin</code>        |
|             | Groovy           | <code>```groovy</code>        |
|             | Scala            | <code>```scala</code>         |
| **Web**     | HTML             | <code>```html</code>          |
|             | CSS              | <code>```css</code>           |
|             | JavaScript       | <code>```javascript</code>    |
|             |                  | <code>```js</code>            |
|             | TypeScript       | <code>```typescript</code>    |
|             |                  | <code>```ts</code>            |
|             | JSON             | <code>```json</code>          |
| **Config**  | YAML             | <code>```yaml</code>          |
|             | XML              | <code>```xml</code>           |
|             | TOML             | <code>```toml</code>          |
|             | INI / Properties | <code>```ini</code>           |
| **Shell**   | Bash             | <code>```bash</code>          |
|             | PowerShell       | <code>```powershell</code>    |
|             |                  | <code>```ps</code>            |
|             | Shell Session    | <code>```shell-session</code> |
|             | Batch            | <code>```batch</code>         |
| **Backend** | Python           | <code>```python</code>        |
|             | Go               | <code>```go</code>            |
|             | Rust             | <code>```rust</code>          |
|             | C#               | <code>```csharp</code>        |
|             | Ruby             | <code>```ruby</code>          |
|             | PHP              | <code>```php</code>           |
| **Data**    | SQL              | <code>```sql</code>           |
|             | GraphQL          | <code>```graphql</code>       |
|             | Regex            | <code>```regex</code>         |
| **Build**   | Dockerfile       | <code>```docker</code>        |
|             | Makefile         | <code>```makefile</code>      |
|             | Gradle (Groovy)  | <code>```groovy</code>        |
|             | Gradle (Kotlin)  | <code>```kotlin</code>        |
| **Docs**    | Markdown         | <code>```markdown</code>      |
|             |                  | <code>```md</code>            |
|             | LaTeX            | <code>```latex</code>         |

See the full list of [290+ supported languages](https://prismjs.com/#supported-languages) on the PrismJS website.

## See Also

- [Images](images.html) - Add screenshots and diagrams
- [UI Components](ui-components.html) - Tabs, accordions, and more
- [Configuration Reference](settings.html) - All configuration options
