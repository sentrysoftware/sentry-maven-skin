keywords: navigation, header, banner, links, social
description: Configure the header navigation bar with logo, links, and social media icons.

# Navigation Links

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

The header navigation bar displays your logo, external links, and social media icons.

![Navigation bar elements](images/nav-links.png)

## Logo and Banner

### `<bannerLeft>`

Display your logo on the left side of the navigation bar:

```xml
<bannerLeft>
  <src>images/logo.png</src>
  <href>https://example.com</href>
  <alt>My Organization</alt>
</bannerLeft>
```

**Logo requirements:**
- Height: 40-80px (resized to 40px)
- Format: PNG with transparent background
- Colors: Light (for contrast with dark bar)

**Text alternative** (if no logo):

```xml
<bannerLeft>
  <name>My Organization</name>
  <href>https://example.com</href>
</bannerLeft>
```

### `<bannerRight>`

Same options as `<bannerLeft>`, displayed on the right side.

## External Links

### `<links>`

Add links in the top navigation bar:

```xml
<body>
  <links>
    <item name="GitHub" href="\${project.scm.url}"/>
    <item name="Maven Docs" href="https://maven.apache.org"/>
  </links>
</body>
```

## Social Media

### `<social>`

Add social media icons to the header:

```xml
<custom>
  <social>
    <twitter>YourHandle</twitter>
    <facebook>YourPage</facebook>
    <linkedin>company/YourCompany</linkedin>
  </social>
</custom>
```

**Supported networks:**

| Network  | Configuration                                                                       |
| -------- | ----------------------------------------------------------------------------------- |
| Twitter  | `<twitter>YourHandle</twitter>`                                                     |
| Facebook | `<facebook>YourPage</facebook>`                                                     |
| LinkedIn | `<linkedin>in/YourProfile</linkedin>` or `<linkedin>company/YourCompany</linkedin>` |

**Custom social links** (for other networks):

```xml
<social>
  <custom>
    <icon>fa-brands fa-slack</icon>
    <title>Join us on Slack</title>
    <href>https://slack.example.com</href>
  </custom>
</social>
```

## Footer Links

### `<additionalLinks>`

Add links in the page footer (for legal documents, policies, etc.):

```xml
<custom>
  <additionalLinks>
    <link>
      <name>Terms of Service</name>
      <href>https://example.com/terms</href>
    </link>
    <link>
      <name>Privacy Policy</name>
      <href>https://example.com/privacy</href>
      <target>_blank</target>
    </link>
  </additionalLinks>
</custom>
```

## Next Steps

- [Navigation Menu](nav-menu.html) - Configure sidebar navigation
- [Writing a Page](page-structure.html) - Start writing content
- [Configuration Reference](settings.html) - All site.xml options
