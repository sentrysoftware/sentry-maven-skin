const { test, expect } = require("@playwright/test");

test.use({ actionTimeout: 10000 });

const goTemplate = '{{index .data "MCP_AGENT_TOKEN" | base64decode}}';

for (const project of ["studio-km", "site4"]) {
	test(`${project} protects literals and preserves skin controls`, async ({ page, context }) => {
		const errors = [];
		page.on("pageerror", (error) => errors.push(error.message));
		page.on("console", (message) => {
			if (message.type() === "error") errors.push(message.text());
		});
		await context.grantPermissions(["clipboard-read", "clipboard-write"]);
		await page.goto(`/${project}/angular-protection.html`);
		await expect(page.locator("#literal-prose")).toHaveText(
			`{{missingTemplateVariable}} and {{1 + 2}} and ${goTemplate}`
		);
		await expect(page.locator("#plain-pre")).toHaveText("{{missingTemplateVariable}} {{1 + 2}}");
		await expect(page.locator("p > code").filter({ hasText: goTemplate })).toHaveText(goTemplate);
		await expect(page.locator("#literal-textarea")).toHaveValue("{{missingTemplateVariable}} {{1 + 2}}");
		await expect(page.locator("#angular-attribute")).toHaveAttribute("title", "Attribute binding");
		await expect(page.locator("#right-toc")).toContainText("Literal {{1 + 2}} heading");
		await expect(page.locator("main h2").first()).toHaveText("Literal {{1 + 2}} heading");
		await expect(page.locator(".toc-heading")).toHaveText([`Contents ${goTemplate}`, `Contents ${goTemplate}`]);
		await page.setViewportSize({ width: 1024, height: 900 });
		await expect(page.locator(".toc-inline-container .toc-heading")).toBeVisible();
		await page.setViewportSize({ width: 1366, height: 900 });

		const command = page.locator("pre").filter({ hasText: "kubectl" });
		await expect(command.locator("code .token").first()).toBeAttached();
		await expect(command).toHaveText(
			`kubectl -n "$NS" get secret m8b-runtime -o go-template='${goTemplate}' | wc -c\n`
		);
		await command.hover();
		await command.locator("..").locator('button[title="Copy to clipboard"]').click();
		expect((await page.evaluate(() => navigator.clipboard.readText())).trim()).toBe(
			`kubectl -n "$NS" get secret m8b-runtime -o go-template='${goTemplate}' | wc -c`
		);
		await page.locator(".nav-tabs").getByText("Second", { exact: true }).click();
		await expect(page.locator(".tab-pane.active")).toContainText("{{missingTemplateVariable}}");
		await expect(page.locator(".tab-pane.active .copy-to-clipboard button")).toBeAttached();
		await page.getByRole("button", { name: "Second panel {{1 + 2}}" }).click();
		await expect(page.getByText("Second panel {{missingTemplateVariable}}.", { exact: true })).toBeVisible();
		await page.getByRole("button", { name: "Details {{1 + 2}}" }).click();
		await expect(page.getByText("Hidden {{missingTemplateVariable}} content.", { exact: true })).toBeVisible();
		// Expanded components give the short 3.x page enough room to scroll past the heading.
		const componentLink = page.locator("#right-toc").getByRole("link", { name: "Components", exact: true });
		await componentLink.click();
		await expect(componentLink.locator("..")).toHaveClass(/active/);
		await expect
			.poll(() => page.locator("#components").evaluate((element) => element.getBoundingClientRect().top))
			.toBeLessThan(150);
		await page.locator(".carousel .right.carousel-control").click();
		await expect(page.locator(".carousel .item.active")).toContainText("Other slide");
		await page.locator("zoomable").click();
		await expect(page.locator("zoomable")).toHaveClass(/zoomed/);
		await page.locator("zoomable").click();
		await expect(page.locator("zoomable")).not.toHaveClass(/zoomed/);

		await page.locator("input[ng-model=siteSearch]").filter({ visible: true }).fill("Angular");
		await expect(page.locator(".search-results")).toBeVisible();
		await expect(page.locator(".search-results h2")).toContainText("Angular");
		expect(errors).toEqual([]);
	});

	test(`${project} copies literal code with a code selector`, async ({ page, context }) => {
		const errors = [];
		page.on("pageerror", (error) => errors.push(error.message));
		page.on("console", (message) => {
			if (message.type() === "error") errors.push(message.text());
		});
		await context.grantPermissions(["clipboard-read", "clipboard-write"]);
		await page.goto(`/${project}/angular-code-copy.html`);
		await expect(page.locator("#pre-protected-link")).toHaveAttribute("href", "https://example.org/{{name}}");
		await expect(page.locator("#pre-protected-link")).toHaveAttribute("title", "{{name}}");
		await expect.poll(() => page.locator("#literal-style").textContent()).toContain(goTemplate);
		await expect.poll(() => page.locator("#literal-style").textContent()).toContain("{{1 + 2}}");
		const editor = page.locator("#interactive-textarea");
		await expect(editor).toHaveValue("initial");
		await editor.fill("ab");
		await expect(editor).toHaveClass(/ng-invalid-minlength/);
		await editor.fill("edited");
		await expect(page.locator("#textarea-value")).toHaveText("edited");
		await expect(page.locator("#textarea-changes")).toHaveText("2");
		await expect(page.locator("#literal-editor")).toHaveValue("Editable");
		await page.locator("#literal-editor").fill("updated");
		await expect(page.locator("#literal-editor-value")).toHaveText("updated");
		await expect(page.locator(".copy-to-clipboard button")).toHaveCount(2);
		const code = page.locator("code").filter({ hasText: "echo" });
		await expect(code.locator(".token").first()).toBeAttached();
		await code.hover();
		await code.locator("..").locator('button[title="Copy to clipboard"]').click();
		expect((await page.evaluate(() => navigator.clipboard.readText())).trim()).toBe(`echo '${goTemplate}'`);
		const inline = page.locator("code").filter({ hasText: goTemplate }).filter({ hasNotText: "echo" });
		await expect(inline).toHaveText(goTemplate);
		await inline.hover();
		await inline.locator("..").locator('button[title="Copy to clipboard"]').click();
		expect((await page.evaluate(() => navigator.clipboard.readText())).trim()).toBe(goTemplate);
		expect(errors).toEqual([]);
	});

	test(`${project} protects decoration labels and preserves their markup`, async ({ page }) => {
		const errors = [];
		page.on("pageerror", (error) => errors.push(error.message));
		page.on("console", (message) => {
			if (message.type() === "error") errors.push(message.text());
		});
		await page.goto(`/${project}/angular-code-copy.html`);
		await expect(page.locator(".header-title").first()).toContainText("{{1 + 2}}");
		await expect(page.locator(".site-banner .version")).toContainText("{{1 + 2}}");
		await expect(page.locator(".footer-title strong").first()).toContainText("{{1 + 2}}");
		await expect(page.locator("header .site-logo img").first()).toHaveAttribute("alt", /\{\{1 \+ 2\}\}/);
		await expect(page.locator("header .breadcrumb a").first()).toContainText("{{1 + 2}}");
		await expect(page.locator(".left-menu h5").first()).toHaveText("Getting Started {{1 + 2}}");
		await expect(page.locator(".parents .breadcrumb")).toContainText("Parent {{1 + 2}}");
		await expect(page.locator(".parents-xs")).toContainText("Getting Started {{1 + 2}}");
		const child = page.locator('.left-menu a[href="angular-code-copy.html"]');
		await expect(child).toContainText("Child {{1 + 2}}");
		// Label markup and author attribute bindings survive build-time text protection.
		await expect(child.locator("i.fa-star")).toHaveAttribute("title", "3");
		await expect(page.locator(".authors strong")).toHaveText(`Author ${goTemplate}`);
		await page.setViewportSize({ width: 390, height: 844 });
		await expect(page.locator("header .site-logo-xs a").first()).toContainText("Banner Left {{1 + 2}}");
		await page.goto(`/${project}/angular-protection.html`);
		await expect(page.locator(".subtopics-xs")).toContainText("Child {{1 + 2}}");
		await page.goto(`/${project}/index.html`);
		const expression = project === "site4" ? "3" : "{{1 + 2}}";
		await expect(page.locator(".home-xs")).toContainText(`Getting Started ${expression}`);
		await page.goto(`/${project}/angular-enabled.html`);
		await expect(page.locator(".header-title").first()).not.toContainText("{{");
		await expect(page.locator(".left-menu h5").first()).toHaveText("Getting Started 3");
		expect(errors).toEqual([]);
	});

	test(`${project} protects terminal directive clones`, async ({ page }) => {
		const errors = [];
		page.on("pageerror", (error) => errors.push(error.message));
		page.on("console", (message) => {
			if (message.type() === "error") errors.push(message.text());
		});
		await page.goto(`/${project}/angular-code-copy.html`);
		await page.evaluate((literal) => {
			const injector = angular.element(document.body).injector();
			const scope = injector.get("$rootScope").$new();
			scope.show = true;
			scope.clicks = { value: 0 };
			scope.items = [1, 2];
			const host = angular.element(
				'<div id="terminal-literals"><button ng-click="show = !show">Toggle</button><button ng-click="items.push(items.length + 1)">Add</button><code class="conditional" ng-if="show" sentry-literal-content ng-click="clicks.value = clicks.value + 1" title="{{clicks.value}}"></code><code class="repeated" ng-repeat="item in items" title="{{item}}" sentry-literal-content></code></div>'
			);
			host.find("code").text(literal);
			angular.element(document.body).append(host);
			injector.get("$compile")(host)(scope);
			scope.$digest();
		}, goTemplate);
		const host = page.locator("#terminal-literals");
		await expect(host.locator("code")).toHaveText([goTemplate, goTemplate, goTemplate]);
		await host.locator(".conditional").click();
		await expect(host.locator(".conditional")).toHaveAttribute("title", "1");
		await host.getByRole("button", { name: "Toggle", exact: true }).click();
		await expect(host.locator(".conditional")).toHaveCount(0);
		await host.getByRole("button", { name: "Add", exact: true }).click();
		await expect(host.locator(".repeated")).toHaveText([goTemplate, goTemplate, goTemplate]);
		await expect(host.locator(".repeated").last()).toHaveAttribute("title", "3");
		await host.getByRole("button", { name: "Toggle", exact: true }).click();
		await expect(host.locator("code")).toHaveText([goTemplate, goTemplate, goTemplate, goTemplate]);
		await host.locator(".conditional").click();
		await expect(host.locator(".conditional")).toHaveAttribute("title", "2");
		expect(errors).toEqual([]);
	});

	for (const name of ["angular-enabled", "angular-enabled-xhtml"]) {
		test(`${project} ${name} allows intentional AngularJS`, async ({ page }) => {
			await page.goto(`/${project}/${name}.html`);
			await expect(page.locator("#counter")).toHaveText("1");
			await expect(page.locator("#counter")).toHaveAttribute("title", "1");
			await page.locator("#increment").click();
			await expect(page.locator("#counter")).toHaveText("2");
			await expect(page.locator("#counter")).toHaveAttribute("title", "2");
		});
	}
}

test("documentation renders the protection setting and literal examples", async ({ page }) => {
	const errors = [];
	page.on("pageerror", (error) => errors.push(error.message));
	page.on("console", (message) => {
		if (message.type() === "error") errors.push(message.text());
	});
	await page.goto("/docs/headers.html");
	await expect(page.locator("pre code[sentry-literal-content]").first()).toBeAttached();
	await expect(page.locator("pre").filter({ hasText: "protectAngular: false" })).toContainText("<p>{{1 + 2}}</p>");
	await page.locator(".nav-tabs").getByText("XHTML", { exact: true }).click();
	await expect(page.locator(".tab-pane.active pre")).toContainText('<meta name="protectAngular" content="false" />');
	await page.screenshot({ path: "tests/results/angular-headers.png", fullPage: true });
	await page.goto("/docs/settings.html");
	await expect(page.locator("tr").filter({ hasText: "protectAngular" })).toContainText("true");
	await page.goto("/docs/code.html");
	await expect(page.getByText(goTemplate, { exact: true })).toBeVisible();
	await page.screenshot({ path: "tests/results/angular-documentation.png", fullPage: true });
	expect(errors).toEqual([]);
});
