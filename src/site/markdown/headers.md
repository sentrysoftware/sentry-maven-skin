# Additional Document Headers

Headers can be added to each Markdown document as "front matter" headers, notably to specify the document's title, author and description. Actually, any custom header can be added using the below syntax in the Markdown source:

```md
title: Casino Royale
author: Ian Fleming
description: The story concerns the British secret agent James Bond, gambling at the casino in Royale-les-Eaux to bankrupt Le Chiffre, the treasurer of a French communist union and a secret member of Soviet state intelligence. Bond is supported in his endeavours by Vesper Lynd, a member of his own service, as well as Felix Leiter of the CIA and René Mathis of the French Deuxième Bureau.
date: 13 April 1953

# Casino Royale (James Bond)

'A dry martini,' Bond said 'in a deep Champagne goblet. Three measures of Gordon's, one of Vodka, half a measure of Kina Lillet. Shake it very well until it's ice cold, then add a thin slice of lemon peel. Got it?'

'Certainly, monsieur.'
```

Typical headers:

| Header | Description |
|---|---|
| `title` | To specify a document title different than its first heading. |
| `author` | Specifies the author of the document. Multiple `author` entries are possible in the same document. |
| `date` | Date of writing of the document (creation or update).<br/>No date format is enforced; it is recommended to be consistent across the documentation. |
| `description` | Typically used by Search Engines as short description of the page. This is critical to SEO. |
| `keywords` | List of keywords applicable to the page. Not used by public Search Engines, but will be used for internal *Related Topics* listing. |
