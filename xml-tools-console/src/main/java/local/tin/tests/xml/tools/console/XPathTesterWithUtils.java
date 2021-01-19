package local.tin.tests.xml.tools.console;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import local.tin.tests.xml.utils.traverse.CustomTextView;
import local.tin.tests.xml.utils.xpath.XPathDetails;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XPathTesterWithUtils {

    public static final String USAGE = "Usage:\njava -jar XPathTester <options>\n-nameSpaceAware true/false\n-xPath <XPath expression>\n-file <File path and name>\n-fakeDefaultNamespace <Fake default namespace>";
    public static final int MAX_ARGUMENTS = 9;
    public static final int MIN_ARGUMENTS = 3;
    private static final Logger LOGGER = Logger.getLogger(XPathTesterWithUtils.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (MIN_ARGUMENTS < args.length && args.length < MAX_ARGUMENTS) {
            String xPathString = getArgument("-xpath", args);
            String filePath = getArgument("-file", args);
            String nameSpaceAware = getArgument("-nameSpaceAware", args);
            String fakeDefaultNamespace = getArgument("-fakeDefaultNamespace", args);
            boolean isNameSpaceAware = true;
            if (nameSpaceAware != null) {
                isNameSpaceAware = Boolean.parseBoolean(nameSpaceAware);
            }
            DocumentBuilderFactory domFactory;
            DocumentBuilder domBuilder;
            Document domDoc;
            try {
                domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(isNameSpaceAware);
                domBuilder = domFactory.newDocumentBuilder();
                domDoc = domBuilder.parse(filePath);               
                XPathDetails xPathDetails = new XPathDetails();
                xPathDetails.setDocument(domDoc);
                xPathDetails.setFakeDefaultNamespacePrefix(fakeDefaultNamespace);
                xPathDetails.setNamespaceAware(isNameSpaceAware);
                xPathDetails.setXpathExpression(xPathString);
                NodeList result00 = local.tin.tests.xml.utils.xpath.XPathTester.getInstance().getResult(xPathDetails);
                LOGGER.info("Source xml file: " + filePath);
                LOGGER.info(CustomTextView.getInstance().getCustomXMLView(domDoc));
                LOGGER.info("XPath expression: " + xPathString);
                LOGGER.info(CustomTextView.getInstance().getCustomXMLView(result00));
            } catch (Exception e) {
                LOGGER.error(USAGE);
                LOGGER.error("Source xml file: " + filePath);
                LOGGER.error("XPath expression: " + xPathString);      
                LOGGER.error("Name space aware: " + isNameSpaceAware); 
                LOGGER.error("Unexpected exception: ", e);

            }
        } else {
            LOGGER.info(USAGE);
        }
    }

    private static String getArgument(String argument, String[] args) {
        int i = 0;
        for (; i < args.length; i++) {
            if (argument.equals(args[i])) {
                break;
            }
        }
        if (i >= args.length - 1) {
            return null;
        }
        return args[i + 1];
    }
    

}
