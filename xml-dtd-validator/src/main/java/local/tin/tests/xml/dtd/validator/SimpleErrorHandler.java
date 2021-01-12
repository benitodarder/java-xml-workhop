package local.tin.tests.xml.dtd.validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author benitodarder
 */
public class SimpleErrorHandler implements org.xml.sax.ErrorHandler {

    private final Logger logger;

    public SimpleErrorHandler(Logger logger) {
        this.logger = logger;
    }

    //Ignore the fatal errors
    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
    }
    //Validation errors 

    @Override
    public void error(SAXParseException e) throws SAXParseException {
        logger.info("Error at " + e.getLineNumber() + " line.");
        logger.info(e.getMessage());
        System.exit(0);
    }
    //Show warnings

    @Override
    public void warning(SAXParseException err) throws SAXParseException {
        logger.info(err.getMessage());
        System.exit(0);
    }
}
