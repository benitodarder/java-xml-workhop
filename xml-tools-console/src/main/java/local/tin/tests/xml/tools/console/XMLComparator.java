package local.tin.tests.xml.tools.console;

import local.tin.tests.xml.utils.Builder;
import local.tin.tests.xml.utils.NodesComparator;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class XMLComparator {

    private static final Logger LOGGER = Logger.getLogger(XMLComparator.class);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XMLUtilsException {
       if (args.length == 2) {
           Document documentA = Builder.getInstance().getDocumentFromFile(args[0]);
           Document documentB = Builder.getInstance().getDocumentFromFile(args[1]);
           if (NodesComparator.getInstance().isSameDocument(documentA, documentB)) {
               LOGGER.info(args[0] + " and " + args[1] + " contain same data");
           } else {
               LOGGER.info(args[0] + " and " + args[1] + " do not contain the same data, generating not matching XPath expressions.");
           }
       } else {
           LOGGER.info("Usage: java -jar xml-utils <file A> <file B>");
       }
    }

}
