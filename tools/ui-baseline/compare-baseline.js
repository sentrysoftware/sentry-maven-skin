const fs = require("fs");
const path = require("path");
const { SCENARIOS, THEMES } = require("./pages");
const { compareComputedStyles, compareScreenshot, ensureDirectory, writeJson, writeTextFile } = require("./comparator");

const repositoryRoot = process.cwd();
const baselineRoot = path.join(repositoryRoot, "tests", "baseline");
const currentRoot = path.join(repositoryRoot, "tests", "results", "current");
const diffRoot = path.join(repositoryRoot, "tests", "results", "diffs");
const reportRoot = path.join(repositoryRoot, "tests", "results");
const configPath = path.join(repositoryRoot, "tools", "ui-baseline", "compare-config.json");
const config = JSON.parse(fs.readFileSync(configPath, "utf8"));

function expectedArtifactsForScenario(scenarioId, theme) {
	const baseName = `${scenarioId}--${theme}`;
	const result = [
		{
			kind: "computed",
			baseline: path.join(baselineRoot, "computed", `${baseName}.json`),
			current: path.join(currentRoot, "computed", `${baseName}.json`)
		},
		{
			kind: "image",
			name: `${baseName}--full`,
			baseline: path.join(baselineRoot, "screenshots", `${baseName}--full.png`),
			current: path.join(currentRoot, "screenshots", `${baseName}--full.png`),
			diff: path.join(diffRoot, "screenshots", `${baseName}--full.diff.png`)
		},
		{
			kind: "image",
			name: `${baseName}--viewport`,
			baseline: path.join(baselineRoot, "screenshots", `${baseName}--viewport.png`),
			current: path.join(currentRoot, "screenshots", `${baseName}--viewport.png`),
			diff: path.join(diffRoot, "screenshots", `${baseName}--viewport.diff.png`)
		}
	];

	if (scenarioId === "site4-code-samples") {
		result.push(
			{
				kind: "image",
				name: `${baseName}--copy-hover`,
				baseline: path.join(baselineRoot, "screenshots", `${baseName}--copy-hover.png`),
				current: path.join(currentRoot, "screenshots", `${baseName}--copy-hover.png`),
				diff: path.join(diffRoot, "screenshots", `${baseName}--copy-hover.diff.png`)
			},
			{
				kind: "image",
				name: `${baseName}--copy-focus-visible`,
				baseline: path.join(baselineRoot, "screenshots", `${baseName}--copy-focus-visible.png`),
				current: path.join(currentRoot, "screenshots", `${baseName}--copy-focus-visible.png`),
				diff: path.join(diffRoot, "screenshots", `${baseName}--copy-focus-visible.diff.png`)
			}
		);
	}

	if (scenarioId === "site4-ui-components" && theme === "light") {
		result.push({
			kind: "image",
			name: `${baseName}--print`,
			baseline: path.join(baselineRoot, "screenshots", `${baseName}--print.png`),
			current: path.join(currentRoot, "screenshots", `${baseName}--print.png`),
			diff: path.join(diffRoot, "screenshots", `${baseName}--print.diff.png`)
		});
	}

	return result;
}

function toMarkdownReport(report) {
	const lines = [];
	lines.push("# UI Baseline Comparison Report");
	lines.push("");
	lines.push(`- Compared at: ${new Date().toISOString()}`);
	lines.push(`- Success: ${report.passed ? "yes" : "no"}`);
	lines.push(`- Total checks: ${report.summary.totalChecks}`);
	lines.push(`- Failed checks: ${report.summary.failedChecks}`);
	lines.push("");

	report.results.forEach((result) => {
		lines.push(`## ${result.id}`);
		lines.push(`- Type: ${result.kind}`);
		lines.push(`- Passed: ${result.passed ? "yes" : "no"}`);
		if (result.kind === "computed") {
			lines.push(
				`- Diffs: missing selectors=${result.summary.missingSelectors}, new selectors=${result.summary.newSelectors}, style diffs=${result.summary.styleDiffs}`
			);
		}
		if (result.kind === "image") {
			lines.push(
				`- Pixel diff: ${result.mismatchPixels || 0} (ratio ${(result.mismatchRatio || 0).toFixed(8)})`
			);
		}
		if (!result.passed && result.reason) {
			lines.push(`- Reason: ${result.reason}`);
		}
		lines.push("");
	});

	return lines.join("\n");
}

function main() {
	const results = [];

	SCENARIOS.forEach((scenario) => {
		THEMES.forEach((theme) => {
			expectedArtifactsForScenario(scenario.id, theme).forEach((artifact) => {
				if (artifact.kind === "computed") {
					if (!fs.existsSync(artifact.baseline) || !fs.existsSync(artifact.current)) {
						results.push({
							id: `${scenario.id}--${theme}`,
							kind: "computed",
							passed: false,
							reason: "missing-computed-artifact",
							summary: {
								missingSelectors: 0,
								newSelectors: 0,
								styleDiffs: 0
							}
						});
						return;
					}

					const computedResult = compareComputedStyles(artifact.baseline, artifact.current, config);
					results.push({
						id: `${scenario.id}--${theme}`,
						kind: "computed",
						...computedResult
					});
					return;
				}

				const imageResult = compareScreenshot(artifact.baseline, artifact.current, artifact.diff, config);
				results.push({
					id: artifact.name,
					kind: "image",
					...imageResult
				});
			});
		});
	});

	const failedChecks = results.filter((result) => !result.passed);
	const report = {
		passed: failedChecks.length === 0,
		summary: {
			totalChecks: results.length,
			failedChecks: failedChecks.length
		},
		results
	};

	ensureDirectory(reportRoot);
	writeJson(path.join(reportRoot, "diff-report.json"), report);
	writeTextFile(path.join(reportRoot, "diff-report.md"), toMarkdownReport(report));

	if (!report.passed) {
		process.exitCode = 1;
		console.error("UI baseline comparison failed. See tests/results/diff-report.md");
		return;
	}

	console.log("UI baseline comparison passed.");
}

main();
