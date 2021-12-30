/**
 * 
 */
package org.bonitasoft.connector.pdf2image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bonitasoft.engine.api.ProcessAPI;
import org.bonitasoft.engine.bpm.document.Document;
import org.bonitasoft.engine.bpm.document.DocumentNotFoundException;
import org.bonitasoft.engine.bpm.document.DocumentValue;
import org.bonitasoft.engine.connector.ConnectorException;

/**
 * The connector execution will follow the steps 1 - setInputParameters() -->
 * the connector receives input parameters values 2 - validateInputParameters()
 * --> the connector can validate input parameters values 3 - connect() --> the
 * connector can establish a connection to a remote server (if necessary) 4 -
 * executeBusinessLogic() --> execute the connector 5 - getOutputParameters()
 * --> output are retrieved from connector 6 - disconnect() --> the connector
 * can close connection to remote server (if any)
 */
public class Pdf2ImageImpl extends AbstractPdf2ImageImpl {

	Logger logger = Logger.getLogger(Pdf2ImageImpl.class.getName());

	@Override
	protected void executeBusinessLogic() throws ConnectorException {
		// Get access to the connector input parameters
		// getDocumentPdf();
		// getPageNumber();
		// getAllPagesInPng();
		// getAllPagesInList();
		final ProcessAPI processAPI = getAPIAccessor().getProcessAPI();

		logger.fine("Pdf2Image: start");
		PDDocument pdDocument = null;
		try {
			Document document = loadDocument(processAPI);
			byte[] content = processAPI.getDocumentContent(document.getContentStorageId());
			pdDocument = PDDocument.load(content);
			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);

			logger.info("Pdf2Image: PDF loaded, " + pdDocument.getNumberOfPages() + " pages detected");

			setSpecificPageDocument(null);
			setAllPagesDocument(null);
			setListOfPagesDocument(null);
			if (getPageNumber() != null) {

				if (getPageNumber() - 1 < pdDocument.getNumberOfPages()) {
					logger.info("Pdf2Image: Produce page " + getPageNumber());
					BufferedImage bim = pdfRenderer.renderImageWithDPI(getPageNumber() - 1, 300, ImageType.RGB);
					ByteArrayOutputStream by = new ByteArrayOutputStream();
					ImageIO.write(bim, "PNG", by);

					DocumentValue resultDocument = new DocumentValue(by.toByteArray(),
							"image/" + getFormatImage().toLowerCase(),
							document.getName() + "." + getFormatImage().toLowerCase());
					setSpecificPageDocument(resultDocument);
				} else
					logger.info("Pdf2Image: Page requested " + getPageNumber() + " out of the number of page "
							+ pdDocument.getNumberOfPages());

			}

			if (getAllPagesInOneImage()) {
				logger.info("Pdf2Image: Produce All pages in one image ");

				BufferedImage joinBufferedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

				for (int page = 0; page < pdDocument.getNumberOfPages(); page++) {
					BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
					joinBufferedImage = joinBufferedImage(joinBufferedImage, bufferedImage);
				}
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ImageIO.write(joinBufferedImage, "PNG", byteArray);

				DocumentValue resultDocument = new DocumentValue(byteArray.toByteArray(),
						"image/" + getFormatImage().toLowerCase(),
						document.getName() + "." + getFormatImage().toLowerCase());

				setAllPagesDocument(resultDocument);

			}

			if (getAllPagesInList()) {
				logger.info("Pdf2Image: Produce List of images");

				List<DocumentValue> listDocuments = new ArrayList<>();
				for (int page = 0; page < pdDocument.getNumberOfPages(); page++) {
					BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
					ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
					ImageIO.write(bufferedImage, "PNG", byteArray);

					DocumentValue resultDocument = new DocumentValue(byteArray.toByteArray(),
							"image/" + getFormatImage().toLowerCase(),
							document.getName() + "_" + page + "." + getFormatImage().toLowerCase());
					listDocuments.add(resultDocument);
				}
				setListOfPagesDocument(listDocuments);
			}

		} catch (final DocumentNotFoundException | IOException e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionDetails = sw.toString();

			logger.severe("Pdf2Image: Error " + e.getMessage() + " at " + exceptionDetails);

			throw new ConnectorException(e);
		} finally {
			if (pdDocument != null)
				try {
					pdDocument.close();
				} catch (IOException e) {
				}
		}
	}

	@Override
	public void connect() throws ConnectorException {
		// [Optional] Open a connection to remote server

	}

	@Override
	public void disconnect() throws ConnectorException {
		// [Optional] Close connection to remote server

	}

	private Document loadDocument(final ProcessAPI processAPI) throws DocumentNotFoundException {
		final long processInstanceId = getExecutionContext().getProcessInstanceId();
		return processAPI.getLastDocument(processInstanceId, getDocumentPdf());
	}

	public BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {

		// do some calculate first
		int offset = 5;
		int wid = Math.max(img1.getWidth(), img2.getWidth()) + offset;
		int height = img1.getHeight() + img2.getHeight() + offset;
		// create a new buffer and draw two image into the new image
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		// fill background
		g2.setPaint(Color.WHITE);
		g2.fillRect(0, 0, wid, height);
		// draw image
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, 0, img1.getHeight() + offset);
		g2.dispose();
		return newImage;
	}
}
