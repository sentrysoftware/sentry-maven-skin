---
syntaxHighlighting: false
---

# No Syntax Highlighting Test

This page tests the `syntaxHighlighting: false` front matter setting.

## Expected Behavior

Code blocks should NOT have PrismJS syntax highlighting applied.
The PrismJS JavaScript should NOT be loaded on this page.

## Test Code Block

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

```python
def hello():
    print("Hello, World!")

if __name__ == "__main__":
    hello()
```

## Verification

1. Check the HTML source - there should be no `<script>` tags for prism.js
2. Code blocks should have plain text without syntax coloring
