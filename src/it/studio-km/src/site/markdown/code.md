# Code Syntax Highlighting Test

This page tests PrismJS syntax highlighting with various languages using the autoloader plugin.

## Java

```java
public class HelloWorld {
    private final String message;

    public HelloWorld(String message) {
        this.message = message;
    }

    public void sayHello() {
        System.out.println("Hello, " + message + "!");
    }

    public static void main(String[] args) {
        HelloWorld hw = new HelloWorld("World");
        hw.sayHello();
    }
}
```

## JavaScript

```javascript
const fetchData = async (url) => {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Fetch failed:", error);
    }
};

// Arrow function with array methods
const numbers = [1, 2, 3, 4, 5];
const doubled = numbers.map(n => n * 2).filter(n => n > 4);
```

## Python

```python
from dataclasses import dataclass
from typing import List, Optional

@dataclass
class Person:
    name: str
    age: int
    email: Optional[str] = None

def greet_people(people: List[Person]) -> None:
    for person in people:
        print(f"Hello, {person.name}! You are {person.age} years old.")

if __name__ == "__main__":
    folks = [Person("Alice", 30), Person("Bob", 25, "bob@example.com")]
    greet_people(folks)
```

## YAML

```yaml
version: "3.8"
services:
  web:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./html:/usr/share/nginx/html
    environment:
      - NGINX_HOST=example.com
      - NGINX_PORT=80
    depends_on:
      - api
  api:
    build: ./api
    restart: always
```

## JSON

```json
{
  "name": "sentry-maven-skin",
  "version": "6.4.0",
  "dependencies": {
    "prismjs": "^1.30.0",
    "bootstrap": "^3.4.1"
  },
  "scripts": {
    "build": "gulp",
    "test": "jest"
  }
}
```

## Bash

```bash
#!/bin/bash

# Variables
PROJECT_DIR="/opt/project"
LOG_FILE="${PROJECT_DIR}/build.log"

# Function to build the project
build_project() {
    echo "Building project..."
    cd "$PROJECT_DIR" || exit 1
    mvn clean install -DskipTests 2>&1 | tee "$LOG_FILE"

    if [ ${PIPESTATUS[0]} -eq 0 ]; then
        echo "Build successful!"
    else
        echo "Build failed. Check $LOG_FILE for details."
        exit 1
    fi
}

build_project
```

## PowerShell

```powershell
# Get all running services
$services = Get-Service | Where-Object { $_.Status -eq 'Running' }

foreach ($service in $services) {
    Write-Host "Service: $($service.Name) - $($service.DisplayName)"
}

# Function with parameters
function Get-DiskSpace {
    param(
        [string]$ComputerName = $env:COMPUTERNAME,
        [int]$ThresholdGB = 10
    )

    Get-WmiObject Win32_LogicalDisk -ComputerName $ComputerName |
        Where-Object { $_.DriveType -eq 3 } |
        Select-Object DeviceID, @{N='FreeGB';E={[math]::Round($_.FreeSpace/1GB,2)}}
}
```

## SQL

```sql
SELECT
    c.customer_name,
    COUNT(o.order_id) AS total_orders,
    SUM(o.amount) AS total_spent
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 YEAR)
GROUP BY c.customer_id, c.customer_name
HAVING total_spent > 1000
ORDER BY total_spent DESC
LIMIT 10;
```

## XML / Markup

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-project</artifactId>
    <version>1.0.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.9.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## CSS

```css
.sentry-site {
    --primary-color: #007bff;
    --secondary-color: #6c757d;

    font-family: 'Segoe UI', Tahoma, sans-serif;
    line-height: 1.6;
}

.dark .sentry-site {
    background-color: #1e1e1e;
    color: #d4d4d4;
}

@media (max-width: 768px) {
    .container {
        padding: 0 15px;
    }
}
```

## Docker

```docker
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## TypeScript

```typescript
interface User {
    id: number;
    name: string;
    email: string;
    roles: string[];
}

class UserService {
    private users: Map<number, User> = new Map();

    async getUser(id: number): Promise<User | undefined> {
        return this.users.get(id);
    }

    async createUser(user: Omit<User, 'id'>): Promise<User> {
        const newUser: User = {
            id: Date.now(),
            ...user
        };
        this.users.set(newUser.id, newUser);
        return newUser;
    }
}
```

## Markdown

```markdown
# Heading 1

This is a paragraph with **bold** and *italic* text.

- List item 1
- List item 2
  - Nested item

[Link text](https://example.com)

![Alt text](image.png)

> Blockquote text
```

## Regex

```regex
^(?<protocol>https?):\/\/(?<domain>[\w.-]+)(?::(?<port>\d+))?(?<path>\/[\w./-]*)?(?:\?(?<query>[\w=&]+))?$
```

## INI / Properties

```ini
[database]
host = localhost
port = 5432
name = myapp
user = admin
password = secret123

[logging]
level = INFO
file = /var/log/app.log
max_size = 10MB
```

## Plain Text (no highlighting)

```text
This is plain text without any syntax highlighting.
It should render as-is without any color coding.
```
