/**
 * 
 */
package org.mycompany.connector;

import org.bonitasoft.engine.connector.AbstractConnector;
import org.bonitasoft.engine.connector.ConnectorValidationException;

/**
 * This abstract class is generated and should not be modified.
 */
public abstract class AbstractStarwarsImpl extends AbstractConnector {

	protected final static String NAME_INPUT_PARAMETER = "name";
	protected final static String URL_INPUT_PARAMETER = "url";
	protected final String PERSON_OUTPUT_PARAMETER = "person";

	protected final java.lang.String getName() {
		return (java.lang.String) getInputParameter(NAME_INPUT_PARAMETER);
	}

	protected final java.lang.String getUrl() {
		return (java.lang.String) getInputParameter(URL_INPUT_PARAMETER);
	}

	protected final void setPerson(java.lang.String person) {
		setOutputParameter(PERSON_OUTPUT_PARAMETER, person);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getName();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("name type is invalid");
		}
		try {
			getUrl();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("url type is invalid");
		}

	}

}
