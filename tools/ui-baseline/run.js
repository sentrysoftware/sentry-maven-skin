const { spawnSync } = require("child_process");
const path = require("path");
const fs = require("fs");
const { TextDecoder } = require("util");

const mode = process.argv[2];
const repositoryRoot = process.cwd();
const playwrightCli = path.join(repositoryRoot, "node_modules", "@playwright", "test", "cli.js");
const utf8Decoder = new TextDecoder("utf-8", { fatal: true });

function normalizeTextToCrlf(value) {
	return value.replace(/\r\n|\n|\r/g, "\r\n");
}

function normalizeTreeToUtf8Crlf(relativeDirectory) {
	const root = path.join(repositoryRoot, relativeDirectory);
	if (!fs.existsSync(root)) {
		return;
	}

	const entries = fs.readdirSync(root, { withFileTypes: true });
	entries.forEach((entry) => {
		const absolutePath = path.join(root, entry.name);
		if (entry.isDirectory()) {
			normalizeTreeToUtf8Crlf(path.join(relativeDirectory, entry.name));
			return;
		}
		if (!entry.isFile()) {
			return;
		}

		const contentBuffer = fs.readFileSync(absolutePath);
		if (contentBuffer.includes(0)) {
			return;
		}

		let decoded;
		try {
			decoded = utf8Decoder.decode(contentBuffer);
		} catch (error) {
			return;
		}

		const withoutBom = decoded.charCodeAt(0) === 0xfeff ? decoded.slice(1) : decoded;
		const normalized = normalizeTextToCrlf(withoutBom);
		fs.writeFileSync(absolutePath, normalized, "utf8");
	});
}

function run(command, args, options) {
	const result = spawnSync(command, args, {
		stdio: "inherit",
		cwd: repositoryRoot,
		env: options && options.env ? options.env : process.env
	});
	if (result.error) {
		console.error(result.error.message);
		process.exit(1);
	}
	if (result.status !== 0) {
		process.exit(result.status || 1);
	}
}

function cleanDirectory(relativePath) {
	const absolutePath = path.join(repositoryRoot, relativePath);
	fs.rmSync(absolutePath, { recursive: true, force: true });
}

function runPlaywright(target) {
	run(process.execPath, [playwrightCli, "test", "tests/ui-baseline/baseline.spec.js", "--project=chromium"], {
		env: {
			...process.env,
			UI_BASELINE_TARGET: target
		}
	});
}

function main() {
	if (mode === "capture") {
		runPlaywright("baseline");
		normalizeTreeToUtf8Crlf("tests/baseline");
		return;
	}

	if (mode === "verify") {
		cleanDirectory("tests/results/current");
		cleanDirectory("tests/results/diffs");
		cleanDirectory("tests/results/playwright");
		runPlaywright("current");
		run("node", ["tools/ui-baseline/compare-baseline.js"]);
		normalizeTreeToUtf8Crlf("tests/results");
		return;
	}

	if (mode === "compare") {
		run("node", ["tools/ui-baseline/compare-baseline.js"]);
		normalizeTreeToUtf8Crlf("tests/results");
		return;
	}

	console.error("Usage: node tools/ui-baseline/run.js <capture|verify|compare>");
	process.exit(1);
}

main();
