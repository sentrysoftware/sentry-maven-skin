title: Copy from code elements
author: Author {{index .data "MCP_AGENT_TOKEN" | base64decode}}
protectAngular: true
copyToClipboard: code

# Copy from code elements

Inline: `{{index .data "MCP_AGENT_TOKEN" | base64decode}}`.

```bash
echo '{{index .data "MCP_AGENT_TOKEN" | base64decode}}'
```

<textarea id="interactive-textarea" ng-init="textareaValue = 'initial'; textareaChanges = 0" ng-model="textareaValue" ng-change="textareaChanges = textareaChanges + 1" minlength="3"></textarea>

<button type="button" id="textarea-value" ng-bind="textareaValue"></button>
<button type="button" id="textarea-changes" ng-bind="textareaChanges"></button>

<textarea id="literal-editor" ng-init="literalValue = 'Editable'" ng-model="literalValue">{{index .data "MCP_AGENT_TOKEN" | base64decode}}</textarea>

<button type="button" id="literal-editor-value" ng-bind="literalValue"></button>

<style id="literal-style"><![CDATA[.literal-style-preview::before { content: '{{1 + 2}}'; } /* {{index .data "MCP_AGENT_TOKEN" | base64decode}} */]]></style>

<form ng-non-bindable=""><a id="pre-protected-link" href="https://example.org/{{name}}" title="{{name}}">Protected link</a></form>
