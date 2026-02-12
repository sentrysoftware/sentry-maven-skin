---
copyToClipboard: false
---

# No Copy Button Test

This page tests the `copyToClipboard: false` front matter setting.

## Expected Behavior

Code blocks on this page should NOT have the copy-to-clipboard button.

## Test Code Block

```bash
echo "Hello, World!"
npm install
mvn clean install
```

```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>my-artifact</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Verification

1. Hover over the code blocks above
2. There should be NO copy button appearing
3. Check HTML source - no `copy-to-clipboard` attribute on `<pre>` elements
