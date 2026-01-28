# Code Samples

This page tests code syntax highlighting with PrismJS.

## Java

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Maven Site Plugin 4.x!");
    }
}
```

## XML

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-app</artifactId>
    <version>1.0-SNAPSHOT</version>
</project>
```

## JavaScript

```javascript
const greeting = (name) => {
    return `Hello, ${name}!`;
};

console.log(greeting('Site4'));
```

## Python

```python
def fibonacci(n):
    if n <= 1:
        return n
    return fibonacci(n - 1) + fibonacci(n - 2)

print([fibonacci(i) for i in range(10)])
```

## Shell

```bash
#!/bin/bash
mvn clean site
echo "Site generated successfully!"
```

## YAML

```yaml
name: Build Site
on: [push]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Build site
        run: mvn site
```

## JSON

```json
{
  "name": "site4-test",
  "version": "1.0.0",
  "description": "Maven Site Plugin 4.x test"
}
```
