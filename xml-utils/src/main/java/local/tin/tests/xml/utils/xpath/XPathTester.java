package local.tin.tests.xml.utils.xpath;

import local.tin.tests.xml.utils.namespaces.NamespaceResolver;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.Common;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import local.tin.tests.xml.utils.namespaces.DocumentNamespaces;
import org.apache.log4j.Logger;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XPathTester {

    private static final String NO_OP_MESSAGE = "Empty string or local-name() in XPath expression split. Skipping";
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
        String[] split = xPathString.split(Common.XPATH_COMPONENT_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        if (Common.EMPTY_STRING.equals(split[i])) {
            stringBuilder.append(Common.XPATH_COMPONENT_SEPARATOR);
            i++;
        }
        if (Common.EMPTY_STRING.equals(split[i])) {
            stringBuilder.append(Common.XPATH_COMPONENT_SEPARATOR);
            i++;
        }
        int rootAt = -1;
        if (i == 0) {
            rootAt = i;
        }
        for (; i < split.length; i++) {
            if (Common.EMPTY_STRING.equals(split[i])) {
                LOGGER.debug(NO_OP_MESSAGE);
            } else if (split[i].contains(Common.LOCAL_NAME)) {
                LOGGER.debug(NO_OP_MESSAGE);
            } else if (!split[i].contains(Common.NAMESPACE_PREFIX_SEPARATOR) && i > rootAt) {
                stringBuilder.append(fakeDefaultNamespace).append(Common.NAMESPACE_PREFIX_SEPARATOR).append(split[i]);
            } else {
                stringBuilder.append(split[i]);
            }
            if (i < split.length - 1) {
                stringBuilder.append(Common.XPATH_COMPONENT_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }

}
