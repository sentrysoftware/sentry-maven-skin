const http = require("http");
const fs = require("fs");
const path = require("path");

const HOST = "127.0.0.1";
const PORT = 4173;
const repositoryRoot = process.cwd();
const site4Root = path.resolve(repositoryRoot, "target", "it", "site4", "target", "site");
const harnessRoot = path.resolve(repositoryRoot, "tools", "ui-harness");

function failIfMissingSite4() {
	if (!fs.existsSync(site4Root)) {
		console.error(`Missing generated Site4 pages at: ${site4Root}`);
		console.error("Build them first with: mvn verify");
		process.exit(1);
	}
}

function normalizeRequestPath(requestUrl) {
	const withoutQuery = (requestUrl || "/").split("?")[0].split("#")[0];
	let decodedPath = withoutQuery;
	try {
		decodedPath = decodeURIComponent(withoutQuery);
	} catch (_error) {
		decodedPath = withoutQuery;
	}
	const normalized = path.posix.normalize(decodedPath);
	return normalized.startsWith("/") ? normalized : `/${normalized}`;
}

function resolveUnderRoot(rootDirectory, relativePath) {
	if (!relativePath || relativePath.includes("\\") || path.isAbsolute(relativePath)) {
		return null;
	}

	const resolvedPath = path.resolve(rootDirectory, relativePath);
	const rootPrefix = rootDirectory.endsWith(path.sep) ? rootDirectory : `${rootDirectory}${path.sep}`;
	if (resolvedPath === rootDirectory || resolvedPath.startsWith(rootPrefix)) {
		return resolvedPath;
	}

	return null;
}

function resolveStaticPath(requestPath) {
	if (requestPath === "/") {
		return path.join(site4Root, "index.html");
	}

	if (requestPath.startsWith("/site4/")) {
		const relativePath = requestPath.slice("/site4/".length);
		return resolveUnderRoot(site4Root, relativePath);
	}

	if (requestPath.startsWith("/harness/")) {
		const relativePath = requestPath.slice("/harness/".length);
		return resolveUnderRoot(harnessRoot, relativePath);
	}

	return null;
}

function contentTypeFor(filePath) {
	const extension = path.extname(filePath).toLowerCase();
	switch (extension) {
		case ".html":
			return "text/html; charset=utf-8";
		case ".css":
			return "text/css; charset=utf-8";
		case ".js":
			return "application/javascript; charset=utf-8";
		case ".json":
			return "application/json; charset=utf-8";
		case ".svg":
			return "image/svg+xml";
		case ".png":
			return "image/png";
		case ".jpg":
		case ".jpeg":
			return "image/jpeg";
		case ".woff":
			return "font/woff";
		case ".woff2":
			return "font/woff2";
		case ".ttf":
			return "font/ttf";
		case ".ico":
			return "image/x-icon";
		default:
			return "application/octet-stream";
	}
}

function writeNotFound(response, requestPath) {
	response.writeHead(404, { "Content-Type": "text/plain; charset=utf-8" });
	response.end(`Not found: ${requestPath}`);
}

function serveFile(response, filePath) {
	if (!fs.existsSync(filePath) || fs.statSync(filePath).isDirectory()) {
		return false;
	}

	response.writeHead(200, {
		"Content-Type": contentTypeFor(filePath),
		"Cache-Control": "no-store"
	});
	fs.createReadStream(filePath).pipe(response);
	return true;
}

function main() {
	failIfMissingSite4();

	const server = http.createServer((request, response) => {
		const requestPath = normalizeRequestPath(request.url);
		const resolvedFile = resolveStaticPath(requestPath);
		if (!resolvedFile) {
			writeNotFound(response, requestPath);
			return;
		}

		if (!serveFile(response, resolvedFile)) {
			writeNotFound(response, requestPath);
		}
	});

	server.listen(PORT, HOST, () => {
		console.log(`UI baseline server listening on http://${HOST}:${PORT}`);
	});
}

main();
