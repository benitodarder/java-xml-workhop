package local.tin.tests.xml.utils.xpath;

import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.XMLUtilsException;
import local.tin.tests.xml.utils.traverse.DocumentNamespaces;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XPathTester {

    private static final String NO_OP_MESSAGE = "Empty string in XPath expression split. Skipping";    
    public static final String XPATH_COMPONENT_SEPARATOR = "/";
    public static final String EMPTY_STRING = "";    
    private static final Logger LOGGER = Logger.getLogger(XPathTester.class);

    private XPathTester() {
    }

    public static XPathTester getInstance() {
        return XPathTesterHolder.INSTANCE;
    }

    private static class XPathTesterHolder {

        private static final XPathTester INSTANCE = new XPathTester();
    }

    public NodeList getResult(XPathDetails xPathDetails) throws XMLUtilsException {

        try {
            String xPathString = xPathDetails.getXpathExpression();
            XPathFactory xFactory = XPathFactory.newInstance();
            XPath xPath = xFactory.newXPath();
            if (xPathDetails.isNamespaceAware()) {
                Map<String, String> namespaces = DocumentNamespaces.getInstance().getDocumentNamespaces(xPathDetails.getDocument());
                NamespaceResolver namespaceResolver = new NamespaceResolver(xPathDetails.getFakeDefaultNamespacePrefix(), namespaces);
                xPath.setNamespaceContext(namespaceResolver);
                xPathString = addDefaultNameSpace(xPathDetails.getXpathExpression(), namespaceResolver.getFakeDefaultPrefix());
            }
            XPathExpression xExpression = xPath.compile(xPathString);
            return (NodeList) xExpression.evaluate(xPathDetails.getDocument(), XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            throw new XMLUtilsException(e);
        }
    }

    private String addDefaultNameSpace(String xPathString, String fakeDefaultNamespace) {
        String[] split = xPathString.split(XPATH_COMPONENT_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        if (EMPTY_STRING.equals(split[i])) {
            stringBuilder.append(XPATH_COMPONENT_SEPARATOR);
            i++;
        }
        if (EMPTY_STRING.equals(split[i])) {
            stringBuilder.append(XPATH_COMPONENT_SEPARATOR);
            i++;
        }
        for (; i < split.length; i++) {
            if (EMPTY_STRING.equals(split[i])) {
                LOGGER.debug(NO_OP_MESSAGE);
            } else if (!split[i].contains(NamespaceResolver.NAMESPACE_SEPARATOR)) {
                stringBuilder.append(fakeDefaultNamespace).append(NamespaceResolver.NAMESPACE_SEPARATOR).append(split[i]);
            } else {
                stringBuilder.append(split[i]);
            }
            if (i < split.length - 1) {
                stringBuilder.append(XPATH_COMPONENT_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }


}
