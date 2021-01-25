package local.tin.tests.xml.utils.errors;

import org.apache.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *  From http://www.edankert.com/validate.html#Error_Handler
 * 
 * @author benito.darder
 */
public class ErrorHandlerLogger implements ErrorHandler {

    private final Logger logger;
    
    public ErrorHandlerLogger(Logger logger) {
        this.logger = logger;
    }
    
    @Override
    public void warning(SAXParseException e) throws SAXException {
        logger.warn(e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        logger.error(e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        logger.fatal(e.getMessage());
    }
}