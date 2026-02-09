# Maven Interpolation Mode Test

This page tests the default (maven) interpolation mode.

## Maven Properties (with braces)

Maven properties using curly braces are resolved:

* Project name: ${project.name}
* Project version: ${project.version}

## Custom Properties from site.xml

Custom properties defined in `<custom>` section of site.xml are also resolved:

* Project version (custom): ${projectVersion}

## Hash Characters Work Normally

In maven mode, hash characters are NOT interpreted as Velocity directives:

* Issue reference: Fix #123
* C include: #include <stdio.h>

The maven mode allows ${project.*} properties while preserving # characters.

## Escaped properties are rendered as is

Use $${project.name} in your Markdown source code to display the variable name referring to the Maven project name.
