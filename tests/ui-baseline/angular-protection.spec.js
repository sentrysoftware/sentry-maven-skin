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
		await expect(page.locator("#literal-attribute")).toHaveAttribute("title", goTemplate);
		await expect(page.locator("#literal-attribute")).toHaveAttribute("data-example", "{{1 + 2}}");
		await expect(page.locator("#literal-link")).toHaveAttribute("href", "https://example.org/{{name}}");
		await expect(page.locator("#right-toc")).toContainText("Literal {{1 + 2}} heading");
		await expect(page.locator("main h2").first()).toHaveText("Literal {{1 + 2}} heading");

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
		await expect(page.locator(".tab-pane.active span[title]")).toHaveAttribute(
			"title",
			"{{missingTemplateVariable}}"
		);
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
		await expect(page.locator(".carousel .item.active")).toContainText("Other slide {{missingTemplateVariable}}");
		await page.locator("zoomable").click();
		await expect(page.locator("zoomable")).toHaveClass(/zoomed/);
		await page.locator("zoomable").click();
		await expect(page.locator("zoomable")).not.toHaveClass(/zoomed/);

		await page.locator("input[ng-model=siteSearch]").filter({ visible: true }).fill("Angular");
		await expect(page.locator(".search-results")).toBeVisible();
		await expect(page.locator(".search-results h2")).toContainText("Angular");
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
	await expect(page.locator("[sentry-protect-angular]")).toBeAttached();
	await expect(page.locator("pre").filter({ hasText: "protectAngular: false" })).toContainText("<p>{{1 + 2}}</p>");
	await page.goto("/docs/settings.html");
	await expect(page.locator("tr").filter({ hasText: "protectAngular" })).toContainText("true");
	await page.goto("/docs/code.html");
	await expect(page.getByText(goTemplate, { exact: true })).toBeVisible();
	await page.screenshot({ path: "tests/results/angular-documentation.png", fullPage: true });
	expect(errors).toEqual([]);
});
