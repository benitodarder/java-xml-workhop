package local.tin.tests.xml.utils.errors;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *  From http://www.edankert.com/validate.html#Error_Handler
 * 
 * @author benito.darder
 */
public class ErrorHandlerStringBuilder implements ErrorHandler {

    private final StringBuilder stringBuilder;
    
    public ErrorHandlerStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }
    
    @Override
    public void warning(SAXParseException e) throws SAXException {
        stringBuilder.append(e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        stringBuilder.append(e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        stringBuilder.append(e.getMessage());
    }
}