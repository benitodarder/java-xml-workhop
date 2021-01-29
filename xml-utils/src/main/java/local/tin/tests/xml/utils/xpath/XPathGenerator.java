package local.tin.tests.xml.utils.xpath;

import java.util.HashSet;
import java.util.Set;
import local.tin.tests.xml.utils.Common;
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
    public static final String ATTRIBUTE_EQUAL = "=";
    public static final String ATTRIBUTE_SELECTOR = "@";
    public static final String ATTRIBUTES_CONDITION = " and ";
    public static final String SQUARE_BRACKET_CLOSE = "]";
    public static final String SQUARE_BRACKET_OPEN = "[";
    public static final String XPATH_COMPONENT_SEPARATOR = "/";
    public static final String NAMESPACE_PREFIX_SEPARATOR = ":";

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
     * Namespace prefixes are ignored and not included in the XPath expressions.
     * 
     * Namespace attrbutes inclusion controlled by parameter.
     * 
     * @param document as Document
     * @param includeNamespace as boolean
     * @return Set of String
     */
    public Set<String> getDocumentXPaths(Document document, boolean includeNamespace) {
        Set<String> namespaces = new HashSet<>();
        NamedNodeMap attributs = document.getDocumentElement().getAttributes();
        int attributsLengh = attributs.getLength();
        for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
            if (!Common.getInstance().isNamespaceAttribute(attributs.item(attributsIndex)) || (isAttributeNode(attributs.item(attributsIndex)) && includeNamespace)) {

            }
        }
        traverse(document.getFirstChild().getChildNodes(), namespaces, document.getFirstChild().getNodeName(), includeNamespace);
        return namespaces;
    }

    private void traverse(NodeList rootNode, Set<String> xpaths, String accumulatedPath, boolean includeNamespace) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderAttributes = new StringBuilder();
        for (int index = 0; index < rootNode.getLength(); index++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                stringBuilder.setLength(0);
                stringBuilder.append(accumulatedPath).append(XPATH_COMPONENT_SEPARATOR).append(getNodeNameWithoutNamespacePrefix(aNode));
                NamedNodeMap attributs = aNode.getAttributes();
                if (attributs != null) {
                    stringBuilderAttributes.setLength(0);
                    int attributsLengh = attributs.getLength();
                    for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
                        if (!Common.getInstance().isNamespaceAttribute(attributs.item(attributsIndex)) || (isAttributeNode(attributs.item(attributsIndex)) && includeNamespace)) {
                            if (attributsIndex > 0) {
                                stringBuilderAttributes.append(ATTRIBUTES_CONDITION);
                            }
                            stringBuilderAttributes.append(ATTRIBUTE_SELECTOR).append(attributs.item(attributsIndex).getNodeName()).append(ATTRIBUTE_EQUAL).append(SINGLE_QUOTE).append(attributs.item(attributsIndex).getNodeValue()).append(SINGLE_QUOTE);
                        }
                    }
                    if (stringBuilderAttributes.length() > 0) {
                        stringBuilder.append(SQUARE_BRACKET_OPEN).append(stringBuilderAttributes.toString()).append(SQUARE_BRACKET_CLOSE);
                    }
                }
                xpaths.add(stringBuilder.toString());
                NodeList childNodes = aNode.getChildNodes();
                if (childNodes.getLength() > 0) {
                    traverse(childNodes, xpaths, stringBuilder.toString(), includeNamespace);
                }
            }
        }
    }

    private String getNodeNameWithoutNamespacePrefix(Node aNode) {
        String nodeName = aNode.getNodeName();
        if (nodeName.contains(NAMESPACE_PREFIX_SEPARATOR)) {
            nodeName = nodeName.substring(nodeName.indexOf(NAMESPACE_PREFIX_SEPARATOR) + 1);
        }
        return nodeName;
    }

    private boolean isAttributeNode(Node node) {
        return  node.getNodeType() == Node.ATTRIBUTE_NODE;
    }
}
