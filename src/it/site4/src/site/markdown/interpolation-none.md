title: No Interpolation Mode Test
keywords: interpolation, none, properties
description: Tests the none interpolation mode (no replacement)
interpolation: none

# No Interpolation Mode Test

This page tests the `none` interpolation mode (no property replacement).

## Property Placeholders (NOT interpolated)

All property placeholders remain as-is:

| Expression | Expected | Actual |
|------------|----------|--------|
| `${project.name}` | ${project.name} | ${project.name} |
| `${project.version}` | ${project.version} | ${project.version} |
| `${skinVersion}` | ${skinVersion} | ${skinVersion} |
| `${testProperty}` | ${testProperty} | ${testProperty} |

## Dollar Escaping (NOT needed)

Dollar escaping is not needed.

| Expression | Expected | Actual |
|------------|----------|--------|
| `$${project.name}` | $${project.name} | $${project.name} |

## Hash Characters (NOT interpreted)

| Expression | Expected | Actual |
|------------|----------|--------|
| `#include` | #include | #include |
| `Fix #123` | Fix #123 | Fix #123 |

## Velocity Escape Sequences (NOT processed)

| Expression | Expected | Actual |
|------------|----------|--------|
| `${esc.h}` | ${esc.h} | ${esc.h} |
| `${esc.d}` | ${esc.d} | ${esc.d} |
| `${esc.h}include` | ${esc.h}include | ${esc.h}include |
| `${esc.d}{project.name}` | ${esc.d}{project.name} | ${esc.d}{project.name} |

## Use Case

This mode is useful for:

- Documentation that shows `${...}` syntax examples
- Pages that need complete control over content
- Avoiding any automatic replacement
