title: Maven Interpolation Mode Test
keywords: interpolation, maven, properties
description: Tests the maven interpolation mode (default)

# Maven Interpolation Mode Test

This page tests the `maven` interpolation mode (the default).

## Property Interpolation

Properties are resolved from pom.xml and site.xml:

| Expression | Expected | Actual |
|------------|----------|--------|
| `$${project.name}` | Site4 Test Project | ${project.name} |
| `$${project.version}` | 1.0-SNAPSHOT | ${project.version} |
| `$${skinVersion}` | (pom property) | ${skinVersion} |
| `$${testProperty}` | Custom Value from site.xml | ${testProperty} |

## Escaping Dollar Expressions

Use `$${property}` in Markdown to output literal `${property}` in your page:

| Expression | Expected | Actual |
|------------|----------|--------|
| `$${project.name}` | $${project.name} | $${project.name} |
| `$${project.version}` | $${project.version} | $${project.version} |
| `$${unknownProperty}` | $${unknownProperty} | $${unknownProperty} |

## Hash Characters

Hash characters work normally (NOT interpreted as Velocity):

| Expression | Expected | Actual |
|------------|----------|--------|
| `#include` | #include | #include |
| `Fix #123` | Fix #123 | Fix #123 |

## Velocity Escape Sequences (NOT processed in maven mode)

| Expression | Expected | Actual |
|------------|----------|--------|
| `${esc.h}` | ${esc.h} (literal) | ${esc.h} |
| `${esc.d}` | ${esc.d} (literal) | ${esc.d} |
| `${esc.h}include` | ${esc.h}include (literal) | ${esc.h}include |
| `${esc.d}{project.name}` | ${esc.d}{project.name} (literal) | ${esc.d}{project.name} |

## Unknown Properties

Properties that don't exist remain as-is:

| Expression | Expected | Actual |
|------------|----------|--------|
| `${unknown.property}` | ${unknown.property} | ${unknown.property} |

