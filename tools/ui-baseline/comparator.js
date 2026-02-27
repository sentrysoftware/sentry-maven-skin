const fs = require("fs");
const path = require("path");
const { PNG } = require("pngjs");
const pixelmatchModule = require("pixelmatch");
const pixelmatch = pixelmatchModule.default || pixelmatchModule;

function ensureDirectory(directoryPath) {
	fs.mkdirSync(directoryPath, { recursive: true });
}

function readJson(filePath) {
	return JSON.parse(fs.readFileSync(filePath, "utf8"));
}

function normalizeToCrlf(value) {
	return value.replace(/\r\n|\n|\r/g, "\r\n");
}

function writeTextFile(filePath, value) {
	ensureDirectory(path.dirname(filePath));
	const withoutBom = value.charCodeAt(0) === 0xfeff ? value.slice(1) : value;
	fs.writeFileSync(filePath, normalizeToCrlf(withoutBom), "utf8");
}

function writeJson(filePath, value) {
	writeTextFile(filePath, JSON.stringify(value, null, "\t") + "\n");
}

function compilePatternList(patterns) {
	return (patterns || []).map((pattern) => new RegExp(pattern));
}

function isSelectorIgnored(selector, selectorPatterns) {
	return selectorPatterns.some((regex) => regex.test(selector));
}

function normalizeStylesBySelector(computedList, selectorPatterns) {
	const stylesBySelector = new Map();
	computedList.forEach((entry) => {
		if (!entry || !entry.selector || isSelectorIgnored(entry.selector, selectorPatterns)) {
			return;
		}
		stylesBySelector.set(entry.selector, entry.styles || {});
	});
	return stylesBySelector;
}

function compareComputedStyles(baselinePath, currentPath, config) {
	const baselineRaw = readJson(baselinePath);
	const currentRaw = readJson(currentPath);

	const selectorPatterns = compilePatternList(config.ignoreSelectors);
	const ignoredProperties = new Set(config.ignoreProperties || []);

	const baseline = normalizeStylesBySelector(baselineRaw, selectorPatterns);
	const current = normalizeStylesBySelector(currentRaw, selectorPatterns);

	const diffs = [];
	const missingSelectors = [];
	const newSelectors = [];

	const baselineSelectors = Array.from(baseline.keys()).sort();
	const currentSelectors = new Set(current.keys());

	baselineSelectors.forEach((selector) => {
		if (!currentSelectors.has(selector)) {
			missingSelectors.push(selector);
		}
	});

	const baselineSelectorSet = new Set(baselineSelectors);
	Array.from(current.keys())
		.sort()
		.forEach((selector) => {
			if (!baselineSelectorSet.has(selector)) {
				newSelectors.push(selector);
			}
		});

	baselineSelectors.forEach((selector) => {
		const baseStyles = baseline.get(selector);
		const currentStyles = current.get(selector);
		if (!currentStyles) {
			return;
		}

		Object.keys(baseStyles).forEach((propertyName) => {
			if (ignoredProperties.has(propertyName)) {
				return;
			}
			const before = baseStyles[propertyName];
			const after = currentStyles[propertyName];
			if (before !== after) {
				diffs.push({
					selector,
					property: propertyName,
					baseline: before,
					current: after
				});
			}
		});
	});

	return {
		passed: missingSelectors.length === 0 && newSelectors.length === 0 && diffs.length === 0,
		summary: {
			missingSelectors: missingSelectors.length,
			newSelectors: newSelectors.length,
			styleDiffs: diffs.length
		},
		missingSelectors,
		newSelectors,
		styleDiffs: diffs
	};
}

function compareScreenshot(baselinePath, currentPath, diffPath, config) {
	if (!fs.existsSync(baselinePath)) {
		return {
			passed: false,
			reason: "missing-baseline"
		};
	}

	if (!fs.existsSync(currentPath)) {
		return {
			passed: false,
			reason: "missing-current"
		};
	}

	const baselinePng = PNG.sync.read(fs.readFileSync(baselinePath));
	const currentPng = PNG.sync.read(fs.readFileSync(currentPath));

	if (baselinePng.width !== currentPng.width || baselinePng.height !== currentPng.height) {
		return {
			passed: false,
			reason: "dimension-mismatch",
			baseline: { width: baselinePng.width, height: baselinePng.height },
			current: { width: currentPng.width, height: currentPng.height }
		};
	}

	const diffImage = new PNG({ width: baselinePng.width, height: baselinePng.height });
	const mismatchPixels = pixelmatch(
		baselinePng.data,
		currentPng.data,
		diffImage.data,
		baselinePng.width,
		baselinePng.height,
		{ threshold: 0.1 }
	);
	const totalPixels = baselinePng.width * baselinePng.height;
	const mismatchRatio = totalPixels === 0 ? 0 : mismatchPixels / totalPixels;

	if (mismatchPixels > 0) {
		ensureDirectory(path.dirname(diffPath));
		fs.writeFileSync(diffPath, PNG.sync.write(diffImage));
	}

	const maxDiffPixels = Number(config.maxImageDiffPixels || 0);
	const maxDiffRatio = Number(config.maxImageDiffRatio || 0);

	return {
		passed: mismatchPixels <= maxDiffPixels && mismatchRatio <= maxDiffRatio,
		reason: mismatchPixels > 0 ? "pixel-diff" : "ok",
		mismatchPixels,
		mismatchRatio
	};
}

module.exports = {
	compareComputedStyles,
	compareScreenshot,
	readJson,
	writeJson,
	writeTextFile,
	ensureDirectory
};
