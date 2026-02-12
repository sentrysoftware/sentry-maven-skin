---
buildIndex: false
buildAiIndex: false
---

# No Search Index Test

This page tests the `buildIndex: false` and `buildAiIndex: false` front matter settings.

## Expected Behavior

1. This page should NOT appear in the search index (index.json)
2. No `.html.md` file should be generated for this page
3. This page should NOT be listed in `llms.txt`

## Note

The `buildIndex` and `buildAiIndex` settings are typically site-wide settings
rather than per-page, but this demonstrates that they can be overridden.

## Search Test

The search functionality on the site should still work, but this specific
page's content should not be searchable.
