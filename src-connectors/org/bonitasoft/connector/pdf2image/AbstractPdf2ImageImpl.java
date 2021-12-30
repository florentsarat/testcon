/**
 * 
 */
package org.bonitasoft.connector.pdf2image;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

/**
 * This abstract class is generated and should not be modified.
 */
public abstract class AbstractPdf2ImageImpl extends AbstractConnector {

	protected final static String DOCUMENTPDF_INPUT_PARAMETER = "documentPdf";
	protected final static String PAGENUMBER_INPUT_PARAMETER = "pageNumber";
	protected final static String ALLPAGESINONEIMAGE_INPUT_PARAMETER = "allPagesInOneImage";
	protected final static String ALLPAGESINLIST_INPUT_PARAMETER = "allPagesInList";
	protected final static String FORMATIMAGE_INPUT_PARAMETER = "formatImage";
	protected final String SPECIFICPAGEDOCUMENT_OUTPUT_PARAMETER = "specificPageDocument";
	protected final String ALLPAGESDOCUMENT_OUTPUT_PARAMETER = "allPagesDocument";
	protected final String LISTOFPAGESDOCUMENT_OUTPUT_PARAMETER = "listOfPagesDocument";

	protected final java.lang.String getDocumentPdf() {
		return (java.lang.String) getInputParameter(DOCUMENTPDF_INPUT_PARAMETER);
	}

	protected final java.lang.Integer getPageNumber() {
		return (java.lang.Integer) getInputParameter(PAGENUMBER_INPUT_PARAMETER);
	}

	protected final java.lang.Boolean getAllPagesInOneImage() {
		return (java.lang.Boolean) getInputParameter(ALLPAGESINONEIMAGE_INPUT_PARAMETER);
	}

	protected final java.lang.Boolean getAllPagesInList() {
		return (java.lang.Boolean) getInputParameter(ALLPAGESINLIST_INPUT_PARAMETER);
	}

	protected final java.lang.String getFormatImage() {
		return (java.lang.String) getInputParameter(FORMATIMAGE_INPUT_PARAMETER);
	}

	protected final void setSpecificPageDocument(
			org.bonitasoft.engine.bpm.document.DocumentValue specificPageDocument) {
		setOutputParameter(SPECIFICPAGEDOCUMENT_OUTPUT_PARAMETER, specificPageDocument);
	}

	protected final void setAllPagesDocument(org.bonitasoft.engine.bpm.document.DocumentValue allPagesDocument) {
		setOutputParameter(ALLPAGESDOCUMENT_OUTPUT_PARAMETER, allPagesDocument);
	}

	protected final void setListOfPagesDocument(java.util.List listOfPagesDocument) {
		setOutputParameter(LISTOFPAGESDOCUMENT_OUTPUT_PARAMETER, listOfPagesDocument);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getDocumentPdf();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("documentPdf type is invalid");
		}
		try {
			getPageNumber();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("pageNumber type is invalid");
		}
		try {
			getAllPagesInOneImage();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("allPagesInOneImage type is invalid");
		}
		try {
			getAllPagesInList();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("allPagesInList type is invalid");
		}
		try {
			getFormatImage();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("formatImage type is invalid");
		}

	}

}
