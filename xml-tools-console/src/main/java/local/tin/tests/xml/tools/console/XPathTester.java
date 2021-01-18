package local.tin.tests.xml.tools.console;

import java.io.File;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.traverse.CustomTextView;
import local.tin.tests.xml.utils.traverse.DocumentNameSpaces;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XPathTester {

    public static final String USAGE = "Usage:\njava -jar XPathTester <options>\n-nameSpaceAware true/false\n-xPath <XPath expression>\n-file <File path and name>";
    public static final int MAX_ARGUMENTS = 7;
    public static final int MIN_ARGUMENTS = 3;
    private static final Logger LOGGER = Logger.getLogger(XPathTester.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (MIN_ARGUMENTS < args.length && args.length < MAX_ARGUMENTS) {
            String xPathString = getArgument("-xpath", args);
            String filePath = getArgument("-file", args);
            String nameSpaceAware = getArgument("-nameSpaceAware", args);
            boolean isNameSpaceAware = true;
            if (nameSpaceAware != null) {
                isNameSpaceAware = Boolean.parseBoolean(nameSpaceAware);
            }
            DocumentBuilderFactory domFactory;
            DocumentBuilder domBuilder;
            Document domDoc;
            XPathFactory xFactory;
            XPathExpression xExpression;
            XPath xPath;
            try {
                domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(isNameSpaceAware);
                domBuilder = domFactory.newDocumentBuilder();
                domDoc = domBuilder.parse(filePath);               
                xFactory = XPathFactory.newInstance();
                xPath = xFactory.newXPath();
                if (isNameSpaceAware) {
                    Map<String, String> namespaces = DocumentNameSpaces.getInstance().getDocumentNamespaces(domDoc);
                    xPath.setNamespaceContext(new NamespaceResolver(namespaces));
                    xPathString = addDefaultNameSpace(xPathString);
                }                 
                xExpression = xPath.compile(xPathString);
                NodeList result00 = (NodeList) xExpression.evaluate(domDoc, XPathConstants.NODESET);
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
    
    private static String addDefaultNameSpace(String xPathString) {
        String[] split = xPathString.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        if ("".equals(split[i])) {
            stringBuilder.append("/");
            i++;
        }
        if ("".equals(split[i])) {
            stringBuilder.append("/");
            i++;
        }
        for (; i < split.length; i++) {
            if ("".equals(split[i])) {
            } else if (!split[i].contains(NamespaceResolver.NAMESPACE_SEPARATOR)) {
                stringBuilder.append(NamespaceResolver.DEFAULT_NAMESPACE_PREFIX).append(NamespaceResolver.NAMESPACE_SEPARATOR).append(split[i]);  
            } else {
                stringBuilder.append(split[i]);
            }
            if (i < split.length - 1) {
                stringBuilder.append("/");
            }
        }
        return stringBuilder.toString();
    }
}
