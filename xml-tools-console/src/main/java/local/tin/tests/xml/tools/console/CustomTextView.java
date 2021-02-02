package local.tin.tests.xml.tools.console;

import local.tin.tests.xml.utils.Builder;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class CustomTextView {

    private static final Logger LOGGER = Logger.getLogger(CustomTextView.class);    
    
    /**
     * @param args the command line arguments
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public static void main(String[] args) throws XMLUtilsException {
        Document document = Builder.getInstance().getDocumentFromFile(args[0], true);
        LOGGER.info(local.tin.tests.xml.utils.CustomTextView.getInstance().getCustomXMLView(document));
    }
    
}
