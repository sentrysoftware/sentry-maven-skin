// Constants
const SRC_MAIN = "src/main/webapp";
const DIST = "target/dist";
const LIB = "node_modules";
const TMP = "target/tmp/webapp";
const BINARY_SRC_OPTIONS = { encoding: false };

// Gulp
const { src, dest, series, parallel } = require("gulp");
const { existsSync, mkdirSync, readdirSync, copyFileSync } = require("fs");
const { join } = require("path");
const { Transform } = require("stream");

// Plugins
const useref = require("gulp-useref");
const gulpif = require("gulp-if");
const uglify = require("gulp-uglify");
const minifyCss = require("gulp-clean-css");
const del = require("del");
const embedTemplates = require("gulp-angular-embed-templates");
const replace = require("gulp-string-replace");
const eslint = require("gulp-eslint");

/***
 * Clean
 **/
function clean() {
	return del([DIST + "/**/*", TMP + "/**/*"]);
}
exports.clean = clean;

/**
 * 4-steps minification:
 * - Put AngularJS HTML templates inline, in the .js
 * - Concatenate all the .js and .css referenced in index.html
 * - Minify the .js code
 * - Minify the .css code
 **/
function jsLint() {
	return src(SRC_MAIN + "/js/*.js")
		.pipe(eslint())
		.pipe(eslint.format())
		.pipe(eslint.failAfterError());
}
function templates() {
	return src(SRC_MAIN + "/js/*.js")
		.pipe(
			embedTemplates({
				basePath: SRC_MAIN,
				minimize: { loose: true }
			})
		)
		.pipe(dest(TMP + "/js"));
}
function vmTmp() {
	// Copy all .vm files to temp directory for processing
	return src([SRC_MAIN + "/*.vm"]).pipe(dest(TMP));
}

function escapeUnicodeForJavaProperties(text) {
	let escaped = "";

	for (const character of text) {
		const codePoint = character.codePointAt(0);

		if (codePoint <= 0x7f) {
			escaped += character;
			continue;
		}

		if (codePoint <= 0xffff) {
			escaped += "\\u" + codePoint.toString(16).padStart(4, "0");
			continue;
		}

		const surrogateCodePoint = codePoint - 0x10000;
		const highSurrogate = 0xd800 + (surrogateCodePoint >> 10);
		const lowSurrogate = 0xdc00 + (surrogateCodePoint & 0x3ff);
		escaped += "\\u" + highSurrogate.toString(16).padStart(4, "0");
		escaped += "\\u" + lowSurrogate.toString(16).padStart(4, "0");
	}

	return escaped;
}

function java8PropertiesTransform() {
	return new Transform({
		objectMode: true,
		transform(file, _, callback) {
			if (file.isNull()) {
				callback(null, file);
				return;
			}

			if (file.isStream()) {
				callback(new Error("Streaming is not supported for i18n bundle conversion."));
				return;
			}

			const utf8Text = file.contents.toString("utf8");
			const java8SafeText = escapeUnicodeForJavaProperties(utf8Text);
			file.contents = Buffer.from(java8SafeText, "ascii");
			callback(null, file);
		}
	});
}

function i18nBundles() {
	// Keep i18n bundles inside META-INF/maven so they stay classpath-only and are not published as site assets.
	// Source bundles are UTF-8 for readability and converted at build time to Java 8-compatible escaped properties.
	return src(SRC_MAIN + "/resources*.properties", { allowEmpty: true }).pipe(java8PropertiesTransform()).pipe(dest(DIST + "/META-INF/maven"));
}
function cssTmp() {
	return src(SRC_MAIN + "/css/*.css").pipe(dest(TMP + "/css"));
}
function mini() {
	// Process head.vm and js.vm which contain the CSS/JS build markers
	// searchPath tells useref where to look for files:
	// - "." resolves paths starting with "./" (like ./css/sentry.css) relative to the .vm file location
	// - SRC_MAIN resolves local CSS files (css/sentry.css becomes src/main/webapp/css/sentry.css)
	// - The paths in .vm files use "../../../node_modules/" which from TMP would be "../node_modules"
	//   but we add "." (project root) to find node_modules directly
	// Sourcemaps are intentionally disabled in packaged assets to keep build output small and faster.
	return src([TMP + "/head.vm", TMP + "/js.vm"])
		.pipe(useref({ searchPath: [".", TMP, SRC_MAIN] }))
		.pipe(gulpif("*.js", uglify()))
		.pipe(gulpif("*.css", minifyCss()))
		.pipe(dest(DIST));
}
function siteVmProcessed() {
	// Copy useref-processed .vm files (head.vm and js.vm) from DIST to META-INF/maven
	// Apply $relativePath replacement for CSS/JS paths
	return src([DIST + "/head.vm", DIST + "/js.vm"])
		.pipe(replace(/script src="js/g, 'script src="$relativePath/js'))
		.pipe(replace(/link rel="stylesheet" href="css/g, 'link rel="stylesheet" href="$relativePath/css'))
		.pipe(dest(DIST + "/META-INF/maven"));
}
function siteVmOther() {
	// Copy other .vm files from TMP to META-INF/maven (no useref needed)
	return src([TMP + "/*.vm", "!" + TMP + "/head.vm", "!" + TMP + "/js.vm"]).pipe(dest(DIST + "/META-INF/maven"));
}
function removeRootVm() {
	// Remove .vm files from dist root (they should only be in META-INF/maven)
	return del(DIST + "/*.vm");
}

function copyDirectoryFiles(sourceDir, destinationDir) {
	if (!existsSync(sourceDir)) {
		return;
	}
	mkdirSync(destinationDir, { recursive: true });
	readdirSync(sourceDir, { withFileTypes: true }).forEach((entry) => {
		const sourcePath = join(sourceDir, entry.name);
		const destinationPath = join(destinationDir, entry.name);

		if (entry.isDirectory()) {
			copyDirectoryFiles(sourcePath, destinationPath);
			return;
		}

		if (entry.isFile()) {
			copyFileSync(sourcePath, destinationPath);
		}
		// Other types (symlinks, sockets, etc.) are intentionally skipped.
	});
}

webProd = series(jsLint, templates, vmTmp, cssTmp, mini, siteVmProcessed, siteVmOther, i18nBundles, removeRootVm);
exports.webProd = webProd;

/**
 * Fonts
 **/
function fa() {
	copyDirectoryFiles(LIB + "/@fortawesome/fontawesome-free/webfonts", DIST + "/webfonts");
	return Promise.resolve();
}
function glyphicons() {
	copyDirectoryFiles(LIB + "/bootstrap/fonts", DIST + "/fonts");
	return Promise.resolve();
}
function srcFonts() {
	copyDirectoryFiles(SRC_MAIN + "/fonts", DIST + "/fonts");
	return Promise.resolve();
}
fonts = parallel(fa, glyphicons, srcFonts);
exports.fonts = fonts;

/**
 * Images
 **/
function imagesSrc() {
	if (!existsSync(SRC_MAIN + "/images")) {
		return Promise.resolve();
	}
	return src(SRC_MAIN + "/images/*", BINARY_SRC_OPTIONS).pipe(dest(DIST + "/images/"));
}
function favicon() {
	return src(SRC_MAIN + "/favicon*", { allowEmpty: true, ...BINARY_SRC_OPTIONS }).pipe(dest(DIST + "/"));
}
images = parallel(imagesSrc, favicon);
exports.images = images;

/**
 * PrismJS components (for autoloader)
 **/
function prismComponents() {
	const sourceDir = LIB + "/prismjs/components";
	if (!existsSync(sourceDir)) {
		return Promise.resolve();
	}
	const destinationDir = DIST + "/js/prism";
	mkdirSync(destinationDir, { recursive: true });
	readdirSync(sourceDir)
		.filter((fileName) => fileName.endsWith(".min.js"))
		.forEach((fileName) => {
			copyFileSync(join(sourceDir, fileName), join(destinationDir, fileName));
		});
	return Promise.resolve();
}
exports.prismComponents = prismComponents;

/**
 * Resources
 **/
resources = parallel(fonts, images, prismComponents);
exports.resources = resources;

/**
 * All
 **/
all = series(clean, parallel(resources, webProd));
exports.all = all;

// Exports
exports.default = all;
