const fs = require("fs");
const path = require("path");
const { test, expect } = require("@playwright/test");
const { SCENARIOS, THEMES } = require("../../tools/ui-baseline/pages");
const { CURATED_PROPERTIES } = require("../../tools/ui-baseline/style-properties");
const { ensureDirectory, writeJson } = require("../../tools/ui-baseline/comparator");

const repositoryRoot = process.cwd();
const captureTarget = process.env.UI_BASELINE_TARGET === "baseline" ? "baseline" : "current";
const outputRoot =
	captureTarget === "baseline"
		? path.join(repositoryRoot, "tests", "baseline")
		: path.join(repositoryRoot, "tests", "results", "current");

function screenshotPath(fileName) {
	return path.join(outputRoot, "screenshots", fileName);
}

function computedPath(fileName) {
	return path.join(outputRoot, "computed", fileName);
}

async function setTheme(page, theme) {
	await page.addInitScript((requestedTheme) => {
		const darkEnabled = requestedTheme === "dark";
		window.localStorage.setItem("matchMediaLight.userIsDark", darkEnabled ? "true" : "false");
	}, theme);
}

async function waitForStableRender(page, theme) {
	await page.waitForLoadState("networkidle");
	await page.waitForSelector("body.sentry-site");
	await page.evaluate((requestedTheme) => {
		document.body.classList.toggle("dark", requestedTheme === "dark");
	}, theme);
	await page.addStyleTag({
		content: "*,*::before,*::after{animation:none !important;transition:none !important;}"
	});
	await page.evaluate(async () => {
		if (document.fonts && document.fonts.ready) {
			await document.fonts.ready;
		}
	});
	await page.waitForTimeout(600);
}

async function dumpComputedStyles(page) {
	return page.evaluate((properties) => {
		const elements = Array.from(document.querySelectorAll("*"));

		function isStableId(id) {
			if (!/^[A-Za-z][\w:-]*$/.test(id)) {
				return false;
			}
			if (/^ember-\d+$/.test(id)) {
				return false;
			}
			if (/accordiongroup-\d+-\d+-(tab|panel)/.test(id)) {
				return false;
			}
			if (/-\d{3,}/.test(id)) {
				return false;
			}
			return true;
		}

		function normalizedClasses(classList) {
			return Array.from(classList)
				.filter((className) => /^[A-Za-z_][\w-]*$/.test(className))
				.filter((className) => !className.startsWith("ng-"))
				.sort()
				.slice(0, 6);
		}

		function partFor(node) {
			let part = node.tagName.toLowerCase();
			if (node.id && isStableId(node.id)) {
				return `${part}#${node.id}`;
			}

			const classes = normalizedClasses(node.classList);
			if (classes.length > 0) {
				part += `.${classes.join(".")}`;
			}

			let nth = 1;
			let previous = node.previousElementSibling;
			while (previous) {
				if (previous.tagName === node.tagName) {
					nth += 1;
				}
				previous = previous.previousElementSibling;
			}
			part += `:nth-of-type(${nth})`;
			return part;
		}

		function stableSelector(node) {
			const parts = [];
			let current = node;
			while (current && current.nodeType === Node.ELEMENT_NODE) {
				parts.unshift(partFor(current));
				if (current.id && isStableId(current.id)) {
					break;
				}
				if (current.tagName.toLowerCase() === "html") {
					break;
				}
				current = current.parentElement;
			}
			return parts.join(" > ");
		}

		return elements.map((element) => {
			const computed = getComputedStyle(element);
			const styles = {};
			properties.forEach((propertyName) => {
				styles[propertyName] = computed[propertyName];
			});
			return {
				selector: stableSelector(element),
				tagName: element.tagName.toLowerCase(),
				styles
			};
		});
	}, CURATED_PROPERTIES);
}

async function captureCopyToClipboardFocusedState(page, baseName) {
	const wrapper = page.locator(".copy-to-clipboard").first();
	await expect(wrapper).toBeVisible();
	const button = wrapper.locator("> button").first();
	await expect(button).toBeVisible();

	await wrapper.hover();
	await wrapper.screenshot({
		path: screenshotPath(`${baseName}--copy-hover.png`)
	});

	await page.locator("body").click({ position: { x: 2, y: 2 } });
	let focused = false;
	for (let index = 0; index < 120; index += 1) {
		await page.keyboard.press("Tab");
		const hasFocus = await button.evaluate((element) => element === document.activeElement);
		if (hasFocus) {
			focused = true;
			break;
		}
	}
	expect(focused).toBeTruthy();

	const isFocusVisible = await button.evaluate((element) => element.matches(":focus-visible"));
	expect(isFocusVisible).toBeTruthy();

	const focusVisibleOutline = await button.evaluate((element) => {
		const styles = getComputedStyle(element);
		return {
			outlineStyle: styles.outlineStyle,
			outlineWidth: styles.outlineWidth
		};
	});
	expect(focusVisibleOutline.outlineStyle).not.toBe("none");
	expect(focusVisibleOutline.outlineWidth).not.toBe("0px");

	await wrapper.screenshot({
		path: screenshotPath(`${baseName}--copy-focus-visible.png`)
	});
}

test.describe.configure({ mode: "serial" });

test.beforeAll(() => {
	ensureDirectory(path.join(outputRoot, "screenshots"));
	ensureDirectory(path.join(outputRoot, "computed"));
});

SCENARIOS.forEach((scenario) => {
	THEMES.forEach((theme) => {
		test(`${captureTarget} ${scenario.id} ${theme}`, async ({ page }) => {
			const baseName = `${scenario.id}--${theme}`;
			await setTheme(page, theme);
			await page.goto(scenario.path, { waitUntil: "networkidle" });
			await waitForStableRender(page, theme);

			await page.screenshot({
				path: screenshotPath(`${baseName}--viewport.png`)
			});

			await page.screenshot({
				path: screenshotPath(`${baseName}--full.png`),
				fullPage: true
			});

			const computed = await dumpComputedStyles(page);
			writeJson(computedPath(`${baseName}.json`), computed);

			if (scenario.id === "site4-code-samples") {
				await captureCopyToClipboardFocusedState(page, baseName);
			}

			if (scenario.id === "site4-ui-components" && theme === "light") {
				await page.emulateMedia({ media: "print" });
				await page.waitForTimeout(150);
				await page.screenshot({
					path: screenshotPath(`${baseName}--print.png`),
					fullPage: true
				});
				await page.emulateMedia({ media: "screen" });
			}
		});
	});
});

test("print css contains required print selectors", async () => {
	const printCssPath = path.join(repositoryRoot, "src", "main", "webapp", "css", "scss", "print.scss");
	const printCss = fs.readFileSync(printCssPath, "utf8");

	const requiredSnippets = [
		"@media print",
		"nav[role=\"navigation\"]",
		"footer",
		".main-content",
		".sentry-uib > .nav-tabs",
		".sentry-uib > .tab-content > .tab-pane",
		"uib-accordion .panel-collapse",
		"[uib-collapse]",
		"[uib-carousel] .carousel-inner > .item"
	];

	requiredSnippets.forEach((snippet) => {
		expect(printCss.includes(snippet), `Missing print rule snippet: ${snippet}`).toBeTruthy();
	});
});
