package com.sentrysoftware.doc.mavenskin;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.FileImageOutputStream;

import org.apache.velocity.tools.config.DefaultKey;
import org.jsoup.nodes.Element;

import com.luciad.imageio.webp.WebPImageReaderSpi;
import com.luciad.imageio.webp.WebPImageWriterSpi;
import com.luciad.imageio.webp.WebPWriteParam;

/**
 * Set of tools for handling images
 *
 */
@DefaultKey("imageTool")
public class ImageTool {

	static {
		// First, register the WEBP IOImage Writer and Reader
		// (Note: this should be done automatically by IOImage, but it doesn't work
		// with Plexus and Maven because of their specific ClassLoader)
		IIORegistry iioRegistry = IIORegistry.getDefaultInstance();
		iioRegistry.registerServiceProvider(new WebPImageWriterSpi());
		iioRegistry.registerServiceProvider(new WebPImageReaderSpi());

	}

	/**
	 * Patterns that matches with absolute URLs, like:
	 * <ul>
	 * <li>http://google.com
	 * <li>ftp://cia.gov
	 * <li>//sentrysoftware.com
	 */
	private static final Pattern ABSOLUTE_URL_PATTERN = Pattern.compile("^(?:[a-z]+:)?//", Pattern.CASE_INSENSITIVE);

	/**
	 * Returns whether specified path is absolute or not.
	 * <ul>
	 * <li>http://google.com => absolute
	 * <li>ftp://cia.gov => absolute
	 * <li>//sentrysoftware.com => absolute
	 * <li>path/file => relative
	 *
	 * @param path Path to test
	 * @return whether specified path is absolute or not
	 */
	protected static boolean isAbsoluteUrl(String path) {
		return ABSOLUTE_URL_PATTERN.matcher(path).find();
	}

	/**
	 * Check the image links in the document and make sure they refer to a file that
	 * actually exists.
	 *
	 * @param body         the HTML content
	 * @param basedir         Actual root directory of the site on the file system
	 * @param currentDocument Logical path of the document being parsed (e.g.
	 *                        "index.html", or "subdir/subpage.html")
	 * @return the updated HTML content
	 * @throws IOException when an image cannot be read or converted
	 */
	public Element checkImageLinks(
			Element body,
			String basedir,
			String currentDocument
	) throws IOException {

		// Initialization
		List<String> errorList = new ArrayList<String>();

		// basedir path
		Path basedirPath = Paths.get(basedir).toAbsolutePath();

		// First, calculate the real path of the current document
		Path documentPath = Paths.get(basedir, currentDocument);

		// Select all images
		List<Element> elements = body.select("img");

		// For each image
		for (Element element : elements) {

			// Get the SRC attribute (the path)
			String imageSrc = element.attr("src");
			if (imageSrc.isEmpty()) {
				continue;
			}

			// Skip absolute URLs
			if (isAbsoluteUrl(imageSrc)) {
				continue;
			}

			// Calculate the path to the actual picture file
			Path sourcePath = documentPath.resolveSibling(imageSrc);
			File sourceFile = sourcePath.toFile();

			// Skip external URLs
			if (!sourcePath.toAbsolutePath().startsWith(basedirPath)) {
				continue;
			}

			// Recalculate the relative link and see whether the original matches
			// the recalculated one. If not, it means there is a problem in the case.
			Path recalculatedPath = documentPath.getParent().toRealPath().relativize(sourcePath.toRealPath());
			String sourcePathSlashString = sourcePath.toString().replace('\\', '/');
			String recalculatedPathSlashString = recalculatedPath.toString().replace('\\', '/');
			if (!recalculatedPathSlashString.endsWith(sourcePathSlashString) && !sourcePathSlashString.endsWith(recalculatedPathSlashString)) {
				errorList.add("Referenced image " + imageSrc + " in " + currentDocument + " doesn't match case of actual file " + recalculatedPath);
			}

			// Sanity check
			if (!sourceFile.isFile()) {
				errorList.add("Referenced image " + imageSrc + " in " + currentDocument + " doesn't exist");
			}

		}

		// Some errors, show them all
		if (!errorList.isEmpty()) {
			throw new IOException(errorList.stream().collect(Collectors.joining("\n")));
		}

		return body;

	}


	/**
	 * Returns the extension of the file
	 * <p>
	 * @param file File
	 * @return the extension of the file
	 */
	protected static String getExtension(File file) {
		String name = file.getName();
		int dotIndex = name.lastIndexOf('.');
		if (dotIndex > -1) {
			return name.substring(dotIndex + 1);
		}
		return "";
	}

	/**
	 * Returns the name of the file without its extension
	 * <p>
	 * @param file File
	 * @return the name of the file without its extension
	 */
	protected static String getNameWithoutExtension(File file) {
		String name = file.getName();
		int dotIndex = name.lastIndexOf('.');
		if (dotIndex > -1) {
			return name.substring(0, dotIndex);
		}
		return name;
	}

	/**
	 * Create a thumbnail image file from the specified image file.
	 * <p>
	 * @param sourceFile File instance of the source image
	 * @param thumbnailMark Suffix to be appended to the source file name to build the thumbnail file
	 * @param maxWidth Maximum width of the thumbnail, 0 if no maximum width
	 * @param maxHeight Maximum height of the thumbnail, 0 if no maximum height
	 * @return File instance of the thumbail image
	 * @throws IOException when cannot read the source image, or write the thumbnail file
	 */
	protected static File createThumbnail(
			File sourceFile,
			String thumbnailMark,
			int maxWidth,
			int maxHeight
	) throws IOException {

		// Sanity check
		if (!sourceFile.isFile()) {
			throw new IOException(sourceFile.getAbsolutePath()  + " does not exist");
		}

		// Destination
		File destination = new File(sourceFile.getParent(), getNameWithoutExtension(sourceFile) + thumbnailMark + ".jpg");

		// Do we need to do anything? (if destination is newer than source, we skip)
		if (Helper.getLastModifiedTime(sourceFile) < Helper.getLastModifiedTime(destination)) {
			return destination;
		}

		// Read the specified image
		BufferedImage sourceImage = ImageIO.read(sourceFile);
		String imageType = getExtension(sourceFile).toLowerCase();

		// Calculate the dimensions of the resulting thumbnail
		int targetWidth = sourceImage.getWidth();
		int targetHeight = sourceImage.getHeight();

		if (maxWidth > 0 && targetWidth > maxWidth) {
			targetHeight = targetHeight * maxWidth / targetWidth;
			targetWidth = maxWidth;
		}
		if (maxHeight > 0 && targetHeight > maxHeight) {
			targetWidth = targetWidth * maxHeight / targetHeight;
			targetHeight = maxHeight;
		}

		// Rescale
		Image resultingImage = sourceImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
		BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

		// Write the thumbnail file
		ImageIO.write(outputImage, imageType, destination);

		return destination;

	}

	/**
	 * Saves the specified image file as a WEBP image.
	 * <p>
	 * @param sourceFile image file to convert to WEBP
	 * @return a File instance of the converted image, or null if the file was already a WEBP
	 * @throws IOException when cannot read the image file
	 */
	protected static File saveImageFileAsWebp(File sourceFile) throws IOException {

		// Sanity check
		if (!sourceFile.isFile()) {
			throw new IOException(sourceFile.getAbsolutePath()  + " does not exist");
		}

		// Output file
		String webpImagePath = getNameWithoutExtension(sourceFile) + ".webp";
		File webpFile = new File(sourceFile.getParent(), webpImagePath);

		// Do we need to do anything? (if destination is newer than source, we skip)
		if (Helper.getLastModifiedTime(sourceFile) < Helper.getLastModifiedTime(webpFile)) {
			return webpFile;
		}

		// Read the specified image
		BufferedImage sourceImage = ImageIO.read(sourceFile);
		if (sourceImage == null) {
			return null;
		}

		// Image type (skip if webp)
		String imageType = getExtension(sourceFile).toLowerCase();
		if ("webp".equals(imageType)) {
			return null;
		}

		// Obtain a WebP ImageWriter instance
		ImageWriter writer = ImageIO.getImageWritersBySuffix("webp").next();

		// Configure encoding parameters: LOSSY for jpeg and jpg, LOSSLESS otherwise
		WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
		writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		if ("jpeg".equals(imageType) || "jpg".equals(imageType)) {
			writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
		} else {
			writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSLESS_COMPRESSION]);
		}

		// Configure the output on the ImageWriter
		writer.setOutput(new FileImageOutputStream(webpFile));

		// Write the WEBP image
		writer.write(null, new IIOImage(sourceImage, null, null), writeParam);

		// Return the file
		return webpFile;

	}

	/**
	 * Upgrades all images in the specified HTML document to WEBP.
	 * <p>
	 *
	 * @param body         the HTML content
	 * @param selector        CSS selector to select all images to upgrade
	 *                        ("img.screenshot" will process all &lt;IMG
	 *                        class="screenshot"&gt; elements)
	 * @param basedir         Actual root directory of the site on the file system
	 * @param currentDocument Logical path of the document being parsed (e.g.
	 *                        "index.html", or "subdir/subpage.html")
	 * @return the updated HTML content
	 * @throws IOException when an image cannot be read or converted
	 */
	public Element convertImagesToWebp(
			Element body,
			String selector,
			String basedir,
			String currentDocument
	) throws IOException {

		// basedir path
		Path basedirPath = Paths.get(basedir).toAbsolutePath();

		// First, calculate the real path of the current document
		Path documentPath = Paths.get(basedir, currentDocument);

		// Select all images
		List<Element> elements = body.select(selector);

		// For each image
		for (Element element : elements) {

			// Get the SRC attribute (the path)
			String imageSrc = element.attr("src");
			if (imageSrc.isEmpty()) {
				continue;
			}

			// Skip absolute URLs
			if (isAbsoluteUrl(imageSrc)) {
				continue;
			}

			// Calculate the path to the actual picture file
			Path sourcePath = documentPath.resolveSibling(imageSrc);
			File sourceFile = sourcePath.toFile();

			// Skip external URLs
			if (!sourcePath.toAbsolutePath().startsWith(basedirPath)) {
				continue;
			}

			// Sanity check
			if (!sourceFile.isFile()) {
				throw new IOException(sourceFile.getAbsolutePath()  + " (referenced as " + imageSrc + ") does not exist");
			}

			// Save as webp
			File webpFile = saveImageFileAsWebp(sourceFile);
			if (webpFile == null) {
				continue;
			}

			// Calculate the src path of the webp image
			String webpSrc = documentPath.getParent().relativize(webpFile.toPath()).toString().replace('\\', '/');

			// Now wrap the IMG element with <picture> and <source srcset="...webp">
			element
				.wrap("<picture>")
				.parent()
				.prependElement("source")
				.attr("srcset", webpSrc)
				.attr("type", "image/webp");

		}

		return body;

	}


	/**
	 * Explicitly states the width and height of each image in the specified document.
	 * <p>
	 *
	 * @param body         the HTML content
	 * @param selector        CSS selector to select all images to upgrade
	 *                        ("img.screenshot" will process all &lt;IMG
	 *                        class="screenshot"&gt; elements)
	 * @param basedir         Actual root directory of the site on the file system
	 * @param currentDocument Logical path of the document being parsed (e.g.
	 *                        "index.html", or "subdir/subpage.html")
	 * @return the updated HTML content
	 * @throws IOException when an image cannot be read or converted
	 */
	public Element explicitImageSize(
			Element body,
			String selector,
			String basedir,
			String currentDocument
	) throws IOException {

		// basedir path
		Path basedirPath = Paths.get(basedir).toAbsolutePath();

		// First, calculate the real path of the current document
		Path documentPath = Paths.get(basedir, currentDocument);

		// Select all images
		List<Element> elements = body.select(selector);

		// For each image
		for (Element element : elements) {

			// Get the SRC attribute (the path)
			String imageSrc = element.attr("src");
			if (imageSrc.isEmpty()) {
				continue;
			}

			// Skip absolute URLs
			if (isAbsoluteUrl(imageSrc)) {
				continue;
			}

			// If size and height are already specified, skip
			if (element.attr("style").matches("(^|\\b)(width:|height:)")
					|| !element.attr("height").isEmpty()
					|| !element.attr("width").isEmpty()) {
				continue;
			}

			// Calculate the path to the actual picture file
			Path sourcePath = documentPath.resolveSibling(imageSrc);
			File sourceFile = sourcePath.toFile();

			// Skip external URLs
			if (!sourcePath.toAbsolutePath().startsWith(basedirPath)) {
				continue;
			}

			// Sanity check
			if (!sourceFile.isFile()) {
				throw new IOException(sourceFile.getAbsolutePath()  + " (referenced as " + imageSrc + ") does not exist");
			}

			// Read the image
			BufferedImage sourceImage = ImageIO.read(sourceFile);
			if (sourceImage == null) {
				continue;
			}

			// Now set the width and height attributes (and CSS)
			element
				.attr("width", String.valueOf(sourceImage.getWidth()))
				.attr("height", String.valueOf(sourceImage.getHeight()))
				.attr("style", String.format(
						"width: %dpx; height: %dpx;%s",
						sourceImage.getWidth(),
						sourceImage.getHeight(),
						element.attr("style")
				));

		}

		return body;

	}

	/**
	 * For all images in the document, create the corresponding thumbnail, and wrap
	 * the picture elements with the specified template.
	 * <p>
	 * The specified template may reference the below "macros":
	 * <ul>
	 * <li><code>%imgWidth%</code>: the original image width
	 * <li><code>%imgHeight%</code>: the original image height
	 * <li><code>%imgAlt%</code>: the image alternate text (it's description)
	 * <li><code>%thumbWidth%</code>: the thumbnail image width
	 * <li><code>%thumbHeight%</code>: the thumbnail image height
	 * <li><code>%thumbSrc%</code>: the thumbnail image source path
	 * </ul>
	 *
	 * @param body         the HTML content
	 * @param selector        CSS selector to select all images to upgrade
	 *                        ("img.screenshot" will process all &lt;IMG
	 *                        class="screenshot"&gt; elements)
	 * @param basedir         Actual root directory of the site on the file system
	 * @param currentDocument Logical path of the document being parsed (e.g.
	 *                        "index.html", or "subdir/subpage.html")
	 * @param maxWidth        Maximum width for the thumbnail (or 0 for no maximum)
	 * @param maxHeight       Maximum height for the thumbnail (or 0 for no maximum)
	 * @param wrapTemplate    HTML code wrap the image element with. This will be
	 *                        typically used to create the thumbnail element. The HTML
	 *                        can reference macros.
	 * @return the updated HTML content
	 * @throws IOException when an image cannot be read or converted
	 */
	public Element convertImagesToThumbnails(
			Element body,
			String selector,
			String basedir,
			String currentDocument,
			int maxWidth,
			int maxHeight,
			String wrapTemplate
	) throws IOException {

		// basedir path
		Path basedirPath = Paths.get(basedir).toAbsolutePath();

		// First, calculate the real path of the current document
		Path documentPath = Paths.get(basedir, currentDocument);

		// Select all images
		List<Element> elements = body.select(selector);

		// For each image
		for (Element element : elements) {

			// Get the SRC attribute (the path)
			String imageSrc = element.attr("src");
			if (imageSrc.isEmpty()) {
				continue;
			}

			// Skip absolute URLs
			if (isAbsoluteUrl(imageSrc)) {
				continue;
			}

			// Get the ALT attribute (the description)
			String imageAlt = element.attr("alt");

			// Calculate the path to the actual picture file
			Path sourcePath = documentPath.resolveSibling(imageSrc);
			File sourceFile = sourcePath.toFile();

			// Skip external URLs
			if (!sourcePath.toAbsolutePath().startsWith(basedirPath)) {
				continue;
			}

			// Sanity check
			if (!sourceFile.isFile()) {
				throw new IOException(sourceFile.getAbsolutePath()  + " (referenced as " + imageSrc + ") does not exist");
			}

			// Image size
			BufferedImage sourceImage = ImageIO.read(sourceFile);
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();

			// Create the thumbnail
			File thumbnailFile = createThumbnail(sourceFile, "-thumbnail", maxWidth, maxHeight);

			// Read the thumbnail and get its size
			BufferedImage thumbnailImage = ImageIO.read(thumbnailFile);
			int thumbnailWidth = thumbnailImage.getWidth();
			int thumbnailHeight = thumbnailImage.getHeight();

			// Calculate the src path of the webp image
			String thumbnailSrc = documentPath.getParent().relativize(thumbnailFile.toPath()).toString().replace('\\', '/');

			// Replace macros in the wrap template
			String wrapHtml = wrapTemplate
				.replaceAll("%imgWidth%", String.valueOf(sourceWidth))
				.replaceAll("%imgHeight%", String.valueOf(sourceHeight))
				.replaceAll("%thumbWidth%", String.valueOf(thumbnailWidth))
				.replaceAll("%thumbHeight%", String.valueOf(thumbnailHeight))
				.replaceAll("%thumbSrc%", thumbnailSrc)
				.replaceAll("%imgAlt%", imageAlt)
			;

			// Now wrap the IMG element with template
			// If the IMG element is inside a PICTURE element, wrap the PICTURE element
			Element pictureElement = element;
			if ("PICTURE".equalsIgnoreCase(element.parent().tagName())) {
				pictureElement = element.parent();
			}
			pictureElement.wrap(wrapHtml);

		}

		return body;

	}


}
