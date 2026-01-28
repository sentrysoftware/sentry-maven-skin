# Tables and Lists

This page tests table and list rendering.

## Basic Table

| Column A | Column B | Column C |
|----------|----------|----------|
| Value 1  | Value 2  | Value 3  |
| Value 4  | Value 5  | Value 6  |
| Value 7  | Value 8  | Value 9  |

## Complex Table

| Feature | 3.x Support | 4.x Support | Notes |
|---------|:-----------:|:-----------:|-------|
| Site name | ✓ | ✓ | Works in both versions |
| Custom properties | ✓ | ✓ | Via `$decoration.custom` / `$site.custom` |
| Menus | ✓ | ✓ | Same configuration |
| Breadcrumbs | ✓ | ✓ | Same configuration |
| Social links | ✓ | ✓ | Custom extension |

## Nested Lists

* Level 1 Item A
  * Level 2 Item A.1
  * Level 2 Item A.2
    * Level 3 Item A.2.1
    * Level 3 Item A.2.2
  * Level 2 Item A.3
* Level 1 Item B
  * Level 2 Item B.1

## Definition Lists

Maven Site Plugin
:   A Maven plugin for generating project documentation websites.

Sentry Maven Skin
:   A modern, responsive skin for Maven Site.

Doxia
:   The underlying documentation framework used by Maven.

## Mixed Content

1. First ordered item
   * Unordered sub-item
   * Another sub-item
2. Second ordered item
   1. Nested ordered item
   2. Another nested item
3. Third ordered item
