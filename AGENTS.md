# AGENTS.md - AI Coding Agent Instructions

> This file provides instructions for AI coding agents (GitHub Copilot, Cursor, Cline, etc.) to effectively work with the **Sentry Maven Skin** project.

## Important Reminders

> [!CAUTION]
> **Keep documentation in sync with code changes!**
>
> - When behavior of the skin changes, **review and update the documentation site** (`src/site/markdown/*.md`)
> - When project structure or build process changes, **update `AGENTS.md` and `README.md`**
> - Always verify documentation renders correctly after skin changes: `mvn clean install site`

## Project Overview

The **Sentry Maven Skin** is an Apache Maven site skin that transforms Markdown files into a modern HTML5/Bootstrap documentation website with search, syntax highlighting, and responsive design.

### Key Technologies

| Technology     | Purpose                       | Location                    |
| -------------- | ----------------------------- | --------------------------- |
| **Velocity**   | HTML templating               | `src/main/webapp/*.vm`      |
| **JavaScript** | Frontend logic (AngularJS)    | `src/main/webapp/js/*.js`   |
| **CSS**        | Styling                       | `src/main/webapp/css/*.css` |
| **HTML**       | Angular templates             | `src/main/webapp/*.html`    |
| **Gulp.js**    | Build system for frontend     | `gulpfile.js`               |
| **Maven**      | Build orchestration           | `pom.xml`                   |
| **Groovy**     | Integration test verification | `src/it/*/verify.groovy`    |

### Required Dependency: Maven Skin Tools

The skin requires **[maven-skin-tools](https://github.com/sentrysoftware/maven-skin-tools)** (`org.sentrysoftware.maven:maven-skin-tools`) which provides custom Velocity tools for HTML processing, search indexing, image optimization, and more. Refer to the [maven-skin-tools documentation](https://sentrysoftware.github.io/maven-skin-tools) when working on Velocity templates.

## Project Structure

```
sentry-maven-skin/
├── src/
│   ├── main/webapp/          # Frontend source code
│   │   ├── site.vm           # Main Velocity template (generates each HTML page)
│   │   ├── tools.vm          # Velocity macros and utilities
│   │   ├── css/              # Stylesheets (sentry.css, print.css)
│   │   ├── js/               # JavaScript files (site.js, site-index.js)
│   │   └── fonts/            # Custom fonts
│   ├── it/                   # Integration tests
│   │   ├── settings.xml      # Maven settings for IT
│   │   ├── studio-km/        # Main test project (Maven Site Plugin 3.x)
│   │   └── site4/            # Test project for Maven Site Plugin 4.x
│   └── site/                 # Project's own documentation (dogfooding)
├── gulpfile.js               # Gulp build tasks for frontend
├── package.json              # Node.js dependencies
├── pom.xml                   # Maven build configuration
└── target/                   # Build output (gitignored)
```

## Building the Project

### Standard Build Command

```bash
mvn clean install site
```

This command:

1. Cleans previous build artifacts
2. Installs Node.js locally and runs `npm ci`
3. Runs Gulp to build, lint, and minify frontend assets
4. Packages the Maven skin JAR
5. Runs integration tests (`studio-km` and `site4`)
6. Generates this project's own documentation site

> [!IMPORTANT]
> **Always use `mvn clean install site`** for a full rebuild. The `clean` phase is needed because Maven's incremental build doesn't always detect changes in Velocity templates or frontend assets. The `install` phase must run before `site` because this project uses itself as its own skin (dogfooding).

### Build Output

| Output                             | Description                   |
| ---------------------------------- | ----------------------------- |
| `target/sentry-maven-skin-*.jar`   | The skin artifact             |
| `target/it/studio-km/target/site/` | Integration test output (3.x) |
| `target/it/site4/target/site/`     | Integration test output (4.x) |
| `target/site/`                     | This project's documentation  |
| `target/it/*/build.log`            | Build logs (check on failure) |

### Previewing Generated Sites

```bash
# Install http-server globally (once)
npm install --global http-server

# Serve any generated site
http-server target/it/studio-km/target/site   # Integration test (3.x)
http-server target/it/site4/target/site       # Integration test (4.x)
http-server target/site                        # Project documentation
```

## Making Changes

1. Edit files in `src/main/webapp/` (frontend) or `src/site/` (documentation)
2. Run `mvn clean install site`
3. Preview results in `target/it/studio-km/target/site/` or `target/site/`

> [!TIP]
> When changing skin behavior, always check both integration tests (`studio-km` for Maven Site Plugin 3.x, `site4` for 4.x) and the project's own documentation site.

### Quick Iteration for Integration Tests

When debugging integration tests, you can iterate faster:

1. Edit files in `src/it/studio-km/` or `src/it/site4/`
2. Run `mvn verify` (faster than full `install site`)
3. Check `target/it/*/build.log` for output

**Debugging Velocity templates:** Use `$log.debug(...)` in `.vm` files to output debug information to `build.log`:

```velocity
#call($log.debug("Variable value: $myVariable"))
#call($log.debug($bodyContent))
```

## Code Formatting

### JavaScript, CSS, HTML

```bash
npm run format        # Auto-fix formatting (Prettier)
npm run format:check  # Check only (CI mode)
npm run lint          # Check for linting issues (ESLint)
npm run lint:fix      # Auto-fix linting issues
```

### Velocity Templates

No automated formatting. Follow these conventions:

- **Indentation**: Use tabs
- **Comments**: `#* ... *#` for multi-line, `## ...` for single-line
- **Whitespace control**: Use `#*...*#` to suppress unwanted EOLs

```velocity
#**
 * Macro documentation
 **
*##macro(myMacro $param)#*
    *##if ($param)#*
        *#<div>$param</div>#*
    *##end#*
*##end
```

## Testing

Integration tests run automatically during `mvn install`. Each test:

1. Builds a documentation site using the skin
2. Verifies output with `verify.groovy` (checks HTML structure, assets, links)

| Test Project | Maven Site Plugin | Source Location     |
| ------------ | ----------------- | ------------------- |
| `studio-km`  | 3.x               | `src/it/studio-km/` |
| `site4`      | 4.x               | `src/it/site4/`     |

## Common Issues

| Problem                               | Solution                                                      |
| ------------------------------------- | ------------------------------------------------------------- |
| Changes not reflected                 | Run `mvn clean install site` (not just `mvn site`)            |
| `NoSuchFileException: target/classes` | Run `install` before `site` (dogfooding requirement)          |
| NPM errors                            | Delete `node/`, `node_modules/`, `package-lock.json`, rebuild |
| Velocity syntax errors                | Check `target/it/*/build.log` for details                     |

## Code Style Guidelines

- **JavaScript**: Double quotes, AngularJS 1.x patterns
- **CSS**: Bootstrap conventions, CSS variables for theming
- **Velocity**: Use `$!variable` for potentially null values, `#call()` to ignore return values

## Key Files

| File                             | Purpose                                   |
| -------------------------------- | ----------------------------------------- |
| `src/main/webapp/site.vm`        | Main HTML template - generates every page |
| `src/main/webapp/tools.vm`       | Velocity macros used by site.vm           |
| `src/main/webapp/js/site.js`     | Main AngularJS application                |
| `src/main/webapp/css/sentry.css` | Main stylesheet                           |
| `src/main/webapp/css/print.css`  | Print-specific styles                     |
| `src/site/markdown/*.md`         | Project documentation (dogfooding)        |

## Getting Help

- **Documentation**: https://sentrysoftware.github.io/sentry-maven-skin
- **Issues**: https://github.com/sentrysoftware/sentry-maven-skin/issues
