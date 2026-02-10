# Welcome to Site4 Test

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## About This Project

This is an integration test project for **Sentry Maven Skin** with **Maven Site Plugin 4.x**.

The main purpose of this test is to verify that the skin works correctly with:

* The new site descriptor namespace (`http://maven.apache.org/SITE/2.0.0`)
* The new `$site` Velocity variable (replacing deprecated `$decoration`)
* All existing skin features

## Key Features Tested

| Feature | Status |
|---------|--------|
| Site name from site.xml | ✓ |
| Custom properties | ✓ |
| Banner left/right | ✓ |
| Navigation menus | ✓ |
| Breadcrumbs | ✓ |
| Social links | ✓ |
| Google Analytics | ✓ |
| Additional links | ✓ |

## Project Information

* **Artifact**: `${project.artifactId}`
* **Version**: `${project.version}`
* **Organization**: ${project.organization.name}

## Interpolation Test (Maven Mode)

This project uses `<interpolation>maven</interpolation>` mode.

### Hash Characters Work Directly

With maven mode, # characters are NOT interpreted as Velocity directives:

* Heading with # is fine: # not a directive
* Code: `#include <stdio.h>` should work
* Issue reference: Fix #123

### Maven Properties

* Custom Maven property: ${customMavenProperty}
* Project version: ${project.version}

## Next Steps

Explore the documentation:

* [Features](features.html) - Detailed feature list
* [Code Samples](code-samples.html) - Code highlighting examples
* [Images](images.html) - Image handling tests
