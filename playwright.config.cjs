const path = require("path");

module.exports = {
	testDir: path.join(__dirname, "tests", "ui-baseline"),
	timeout: 120000,
	expect: {
		timeout: 10000
	},
	workers: 1,
	reporter: [["list"]],
	use: {
		baseURL: "http://127.0.0.1:4173",
		viewport: { width: 1366, height: 900 },
		deviceScaleFactor: 1,
		colorScheme: "light",
		locale: "en-US",
		timezoneId: "UTC",
		ignoreHTTPSErrors: true,
		launchOptions: {
			args: ["--font-render-hinting=none"]
		}
	},
	projects: [
		{
			name: "chromium",
			use: {
				browserName: "chromium"
			}
		}
	],
	webServer: {
		command: "node tools/ui-baseline/server.js",
		url: "http://127.0.0.1:4173/site4/index.html",
		timeout: 120000,
		reuseExistingServer: true
	},
	outputDir: path.join(__dirname, "tests", "results", "playwright")
};
