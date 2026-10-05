# Angular literal protection

<!-- MACRO{toc|fromDepth=1|toDepth=2|id=toc} -->

## Literal {{1 + 2}} heading

<p id="literal-prose">{{missingTemplateVariable}} and {{1 + 2}} and {{index .data "MCP_AGENT_TOKEN" | base64decode}}</p>

Inline: `{{index .data "MCP_AGENT_TOKEN" | base64decode}}`.

<pre id="plain-pre">{{missingTemplateVariable}} {{1 + 2}}</pre>

<textarea id="literal-textarea">{{missingTemplateVariable}} {{1 + 2}}</textarea>

<button type="button" id="angular-attribute" ng-init="attributeValue = 'Attribute binding'" title="{{attributeValue}}">Attribute binding</button>

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

![Zoom image](images/logo-short.png)

> [!CAROUSEL interval=0]
>
> - ![First slide](images/logo-short.png)
> - ![Other slide](images/logo-short.png)
