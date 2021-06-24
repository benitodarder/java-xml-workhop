package local.tin.tests.xml.utils.xpath;

import java.util.HashSet;
import java.util.Set;
import local.tin.tests.xml.utils.Common;
import local.tin.tests.xml.utils.comparison.ComparisonExclusions;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XPathGenerator {

    public static final String SINGLE_QUOTE = "'";
    public static final String ATTRIBUTE_SELECTOR = "@";
    public static final String ATTRIBUTES_CONDITION = " and ";
    public static final String SQUARE_BRACKET_CLOSE = "]";
    public static final String SQUARE_BRACKET_OPEN = "[";
    public static final String WILDCARD_ALL = "*";
    private static final Logger LOGGER = Logger.getLogger(XPathGenerator.class);
    
    private XPathGenerator() {
    }

    public static XPathGenerator getInstance() {
        return DocumentNameSpacesHolder.INSTANCE;
    }

    private static class DocumentNameSpacesHolder {

        private static final XPathGenerator INSTANCE = new XPathGenerator();
    }

    /**
     * Returns a set with all XPath available from the given document.
     * 
     * Nodes can include the namespace prefixe or filtered by local-name
     * 
     * @param document as Document
     * @param isLocalName as boolean
     * @param attributes as ComparisonExclusions
     * @return Set of String
     */
    public Set<String> getDocumentXPaths(Document document, boolean isLocalName, ComparisonExclusions attributes) {
        Set<String> namespaces = new HashSet<>();
        NamedNodeMap attributs = document.getDocumentElement().getAttributes();
        int attributsLengh = attributs.getLength();
        for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
            if (!Common.getInstance().isNamespaceAttribute(attributs.item(attributsIndex))) {
                LOGGER.debug("Non namespace attribute. Skipping.");
            }
        }
        String xpathRoot = document.getFirstChild().getNodeName();
        if (isLocalName) {
            xpathRoot = getNodeByLocalName(document.getFirstChild());
        }
        traverse(document.getFirstChild().getChildNodes(), namespaces, xpathRoot, isLocalName, attributes);
        return namespaces;
    }

    private void traverse(NodeList rootNode, Set<String> xpaths, String accumulatedPath, boolean isLocalName, ComparisonExclusions attributes) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderAttributes = new StringBuilder();
        for (int index = 0; index < rootNode.getLength(); index++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                stringBuilder.setLength(0);
                stringBuilder.append(accumulatedPath).append(Common.XPATH_COMPONENT_SEPARATOR);
                if (isLocalName) {
                    stringBuilder.append(getNodeByLocalName(aNode));
                } else {
                    stringBuilder.append(aNode.getNodeName());
                }
                NamedNodeMap attributs = aNode.getAttributes();
                if (attributs != null) {
                    stringBuilderAttributes.setLength(0);
                    int attributsLengh = attributs.getLength();
                    for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
                        if (!Common.getInstance().isNamespaceAttribute(attributs.item(attributsIndex))
                                && !isAttributeExcluded(attributes, attributs.item(attributsIndex).getLocalName(), aNode.getLocalName())) {
                            if (attributsIndex > 0) {
                                stringBuilderAttributes.append(ATTRIBUTES_CONDITION);
                            }
                            stringBuilderAttributes.append(ATTRIBUTE_SELECTOR).append(attributs.item(attributsIndex).getNodeName()).append(Common.EQUAL_SIGN).append(SINGLE_QUOTE).append(attributs.item(attributsIndex).getNodeValue()).append(SINGLE_QUOTE);
                        }
                    }
                    if (stringBuilderAttributes.length() > 0) {
                        stringBuilder.append(SQUARE_BRACKET_OPEN).append(stringBuilderAttributes.toString()).append(SQUARE_BRACKET_CLOSE);
                    }
                }
                xpaths.add(stringBuilder.toString());
                NodeList childNodes = aNode.getChildNodes();
                if (childNodes.getLength() > 0) {
                    traverse(childNodes, xpaths, stringBuilder.toString(), isLocalName, attributes);
                }
            }
        }
    }

    private String getNodeByLocalName(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(WILDCARD_ALL).append(SQUARE_BRACKET_OPEN).append(Common.LOCAL_NAME).append(Common.EQUAL_SIGN).append(SINGLE_QUOTE).append(node.getLocalName()).append(SINGLE_QUOTE).append(SQUARE_BRACKET_CLOSE);
        return stringBuilder.toString();
    }

    private boolean isAttributeExcluded(ComparisonExclusions attributes,  String attributName, String nodeName) {
        return attributes != null && attributes.containsByNode(attributName, nodeName);
    }
}
