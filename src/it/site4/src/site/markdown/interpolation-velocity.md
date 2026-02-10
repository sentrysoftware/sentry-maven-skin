title: Velocity Interpolation Mode Test
keywords: interpolation, velocity, properties
description: Tests the velocity interpolation mode
interpolation: velocity

# Velocity Interpolation Mode Test

This page tests the `velocity` interpolation mode.

> [!WARNING]
> Velocity mode has significant limitations due to Doxia converting ASCII quotes to Unicode smart quotes.

## Property Interpolation

Properties are resolved via Velocity:

| Expression            | Expected           | Actual             |
| --------------------- | ------------------ | ------------------ |
| `\${project.name}`    | Site4 Test Project | ${project.name}    |
| `\${project.version}` | 1.0-SNAPSHOT       | ${project.version} |

## Velocity Escape Sequences

In velocity mode, `$esc.h` and `$esc.d` are processed:

| Expression                | Expected        | Actual                 |
| ------------------------- | --------------- | ---------------------- |
| `\$esc.h`                 | #               | $esc.h                 |
| `\$esc.d`                 | $               | $esc.d                 |
| `\${esc.h}include`        | #include        | ${esc.h}include        |
| `\${esc.d}{project.name}` | ${project.name} | ${esc.d}{project.name} |

## Hash Characters (Velocity Directives)

Hash characters are interpreted as Velocity directives:

| Expression        | Expected  | Actual         |
| ----------------- | --------- | -------------- |
| `\$esc.h include` | # include | $esc.h include |
| `Fix \$esc.h 123` | Fix # 123 | Fix $esc.h 123 |

> [!NOTE]
> We cannot test `\#include` directly as it would be interpreted as a Velocity directive and cause errors.

## Known Limitations

Method calls with string arguments don't work because Doxia converts quotes to smart quotes:
- `$` `site.getCustomValue("testProperty")` would fail because `"` becomes `"`
