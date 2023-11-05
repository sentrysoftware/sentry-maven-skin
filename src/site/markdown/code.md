keywords: code, syntax, highlighting, coloring

# Code Syntax Highlighting

Fenced code blocks will be syntax-highlighted using [PrismJS](https://prismjs.com/). The language of the code block must be specified as in the below example:

````md
```java
System.out.println("Hello, World!");
```
````

Supported languages (or syntax highlighting types) are:

| Language | Markdown markup |
|---|---|
| Command line | <pre><code class="language-md">```batch</code></pre> |
| Commands with output | <pre><code class="language-md">```shell-session<br/>$ ls -l test<br/>test: Not found<br/></code></pre> |
| CSS | <pre><code class="language-md">```css</code></pre> |
| Dockerfile | <pre><code class="language-md">```docker</code></pre> |
| HTML | <pre><code class="language-md">```html</code></pre> |
| JavaScript | <pre><code class="language-md">```js</code></pre> |
| JSON | <pre><code class="language-md">```json</code></pre> |
| Markdown | <pre><code class="language-md">```md</code></pre> |
| PowerShell | <pre><code class="language-md">```ps</code></pre> |
| PSL | <pre><code class="language-md">```psl</code></pre> |
| Regular expressions | <pre><code class="language-md">```regex</code></pre> |
| Shell script (Linux) | <pre><code class="language-md">```bash</code></pre> |
| Shell script (Windows) | <pre><code class="language-md">```batch</code></pre> |
| SQL | <pre><code class="language-md">```sql</code></pre> |
| XML | <pre><code class="language-md">```xml</code></pre> |
| YAML | <pre><code class="language-md">```yaml</code></pre> |

> **Note:**
> Syntax highlighting for fenced code blocks is available only when using version 3.10 (and later) of *maven-site-plugin*.
