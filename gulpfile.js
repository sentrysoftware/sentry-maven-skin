// Constants
const SRC_MAIN = "src/main/webapp";
const DIST = "target/dist";
const LIB = "node_modules";
const TMP = "target/tmp/webapp";

// Gulp
const { src, dest, series, parallel, watch } = require("gulp");

// Plugins
const useref = require("gulp-useref");
const gulpif = require("gulp-if");
const uglify = require("gulp-uglify");
const minifyCss = require("gulp-clean-css");
const sourcemaps = require("gulp-sourcemaps");
const lazypipe = require("lazypipe");
const del = require("del");
const embedTemplates = require("gulp-angular-embed-templates");
const replace = require("gulp-string-replace");
const rename = require("gulp-rename");
const filter = require("gulp-filter");
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
	return src([TMP + "/head.vm", TMP + "/js.vm"])
		.pipe(useref({ searchPath: [".", TMP, SRC_MAIN] }, lazypipe().pipe(sourcemaps.init, { loadMaps: true })))
		.pipe(gulpif("*.js", uglify()))
		.pipe(gulpif("*.css", minifyCss()))
		.pipe(sourcemaps.write("maps"))
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

webProd = series(jsLint, templates, vmTmp, cssTmp, mini, siteVmProcessed, siteVmOther, removeRootVm);
exports.webProd = webProd;

/**
 * Fonts
 **/
function fa() {
	return src(LIB + "/@fortawesome/fontawesome-free/webfonts/*").pipe(dest(DIST + "/webfonts/"));
}
function glyphicons() {
	return src(LIB + "/bootstrap/fonts/*").pipe(dest(DIST + "/fonts/"));
}
function srcFonts() {
	return src(SRC_MAIN + "/fonts/*").pipe(dest(DIST + "/fonts"));
}
fonts = series(fa, glyphicons, srcFonts);
exports.fonts = fonts;

/**
 * Images
 **/
function imagesSrc() {
	return src(SRC_MAIN + "/images/*").pipe(dest(DIST + "/images/"));
}
function imagesJstree() {
	return src(LIB + "/bootstrap-jstree-theme/dist/themes/bootstrap/*.+(gif|png)").pipe(dest(DIST + "/css/"));
}
function favicon() {
	return src(SRC_MAIN + "/favicon*").pipe(dest(DIST + "/"));
}
images = series(imagesSrc, imagesJstree, favicon);
exports.images = images;

/**
 * PrismJS components (for autoloader)
 **/
function prismComponents() {
	return src(LIB + "/prismjs/components/*.min.js").pipe(dest(DIST + "/js/prism/"));
}
exports.prismComponents = prismComponents;

/**
 * Resources
 **/
resources = series(fonts, images, prismComponents);
exports.resources = resources;

/**
 * All
 **/
all = series(clean, resources, webProd);
exports.all = all;

// Exports
exports.default = all;
