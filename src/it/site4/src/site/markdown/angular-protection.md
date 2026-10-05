title: Angular literal protection
protectAngular: true

# Angular literal protection

<!-- MACRO{toc|fromDepth=2|toDepth=3|id=toc} -->

## Literal {{1 + 2}} heading

<p id="literal-prose">{{missingTemplateVariable}} and {{1 + 2}} and {{index .data "MCP_AGENT_TOKEN" | base64decode}}</p>

Inline: `{{index .data "MCP_AGENT_TOKEN" | base64decode}}`.

<pre id="plain-pre">{{missingTemplateVariable}} {{1 + 2}}</pre>

<textarea id="literal-textarea">{{missingTemplateVariable}} {{1 + 2}}</textarea>

<button type="button" id="literal-attribute" title="{{index .data &quot;MCP_AGENT_TOKEN&quot; | base64decode}}" data-example="{{1 + 2}}">Attribute example</button>
<a id="literal-link" href="https://example.org/{{name}}">Template URL</a>

```bash
kubectl -n "$NS" get secret m8b-runtime -o go-template='{{index .data "MCP_AGENT_TOKEN" | base64decode}}' | wc -c
```

## Components

> [!TABS]
>
> - First {{1 + 2}}
>
>     First tab content.
>
> - Second
>
>     `{{missingTemplateVariable}}`
>
>     <span title="{{missingTemplateVariable}}">Nested attribute</span>
>
>     ```bash
>     echo '{{index .data "MCP_AGENT_TOKEN" | base64decode}}'
>     ```

> [!ACCORDION]
>
> - First panel
>
>     First panel content.
>
> - Second panel {{1 + 2}}
>
>     Second panel {{missingTemplateVariable}}.

> [!COLLAPSIBLE] Details {{1 + 2}}
>
> Hidden {{missingTemplateVariable}} content.

![Zoom {{1 + 2}}](images/test-image.png)

> [!CAROUSEL interval=0]
>
> - ![Slide {{1 + 2}}](images/test-image.png)
> - ![Other slide {{missingTemplateVariable}}](images/test-image.png)
