package local.tin.tests.xml.utils.errors;

import java.util.List;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *  From http://www.edankert.com/validate.html#Error_Handler
 * 
 * @author benito.darder
 */
public class ErrorHandlerStringList implements ErrorHandler {

    private final List<String> stringList;
    
    public ErrorHandlerStringList(List<String> list) {
        this.stringList = list;
    }
    
    @Override
    public void warning(SAXParseException e) throws SAXException {
        stringList.add(e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        stringList.add(e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        stringList.add(e.getMessage());
    }
}