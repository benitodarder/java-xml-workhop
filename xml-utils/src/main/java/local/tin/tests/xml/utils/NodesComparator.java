package local.tin.tests.xml.utils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 * @author benitodarder
 */
public class NodesComparator {

    private NodesComparator() {
    }

    public static NodesComparator getInstance() {
        return NodeComparatorHolder.INSTANCE;
    }

    private static class NodeComparatorHolder {

        private static final NodesComparator INSTANCE = new NodesComparator();
    }

    /**
     * Compares the nodes A and B:
     * <ul>
     * <li>Comparing attributes</li>
     * <li>Comparing text content
     * <ul>
     * <li>When CDATA present comparing the exact text</li>
     * <li>When no CDATA present trimmed text content</li>
     * </ul>
     * </ul>
     * The following aspects are not considered:
     * <ul>
     * <li>Presence of child nodes</li>
     * <li>Namespace attributes</li>
     * </ul>
     *
     * @param nodeA as Node
     * @param nodeB as Node
     * @return boolean
     */
    public boolean isSameNodeShallowly(Node nodeA, Node nodeB) {
        NamedNodeMap nodeAAttributes = nodeA.getAttributes();
        NamedNodeMap nodeBAttributes = nodeB.getAttributes();
        if (nodeAAttributes.getLength() != nodeBAttributes.getLength()) {
            return false;
        } else {
            int attributesLEnght = nodeAAttributes.getLength();
            for (int i = 0; i < attributesLEnght; i++) {
                if (isNonNameSpaceAttribute(nodeAAttributes.item(i))) {
                    boolean found = false;
                    for (int j = 0; j < attributesLEnght; j++) {

                        if (isSameNonNameSpaceAttribute(nodeAAttributes.item(i), nodeBAttributes.item(j))) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        return false;
                    }
                }
            }
        }
        String nodeAText = getCDATANodeContent(nodeA);
        String nodeBText = getCDATANodeContent(nodeB);
        if (nodeAText != null && nodeBText != null) {
            return nodeAText.equals(nodeBText);
        }
        nodeAText = getTextNodeContent(nodeA);
        nodeBText = getTextNodeContent(nodeB);
        if (nodeAText != null && nodeBText != null) {
            return nodeAText.equals(nodeBText);
        }
        return false;
    }

    private  boolean isNonNameSpaceAttribute(Node node) {
        return node.getNodeType() == Node.ATTRIBUTE_NODE
                && !node.getNodeName().startsWith(Common.ATTRIBUTE_XMLNS);
    }

    /**
     * Compares the node shallowly and if they are equal, compare all child
     * nodes deeply.
     *
     * @param nodeA as Node
     * @param nodeB as Node
     * @return boolean
     */
    public boolean isSameNodeDeeply(Node nodeA, Node nodeB) {
        if (!isSameNodeShallowly(nodeA, nodeB)) {
            return false;
        }
        return isSameNodeList(nodeA.getChildNodes(), nodeB.getChildNodes());
    }

    /**
     * Returns true if both node lists contain the same nodes, compared as
     * isSameNodeDeeply defines.
     *
     * @param nodeListA as NodeList
     * @param nodeListB as NodeList
     * @return boolean
     */
    public boolean isSameNodeList(NodeList nodeListA, NodeList nodeListB) {
        if (nodeListA.getLength() != nodeListB.getLength()) {
            return false;
        }
        int nodesChilds = nodeListA.getLength();
        for (int i = 0; i < nodesChilds; i++) {
            if (nodeListA.item(i).getNodeType() == Node.ELEMENT_NODE) {
                boolean found = false;
                for (int j = 0; j < nodesChilds; j++) {
                    if (nodeListB.item(j).getNodeType() == Node.ELEMENT_NODE
                            && isSameNodeDeeply(nodeListA.item(i), nodeListB.item(j))) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the root element is the same deeply, regardless the
     * namespace definitions.
     *
     * @param documentA as org.w3c.Document
     * @param documentB as org.w3c.Document
     * @return boolean
     */
    public boolean isSameDocument(Document documentA, Document documentB) {
        if (!isSameNodeShallowly(documentA.getFirstChild(), documentB.getFirstChild())) {
            return false;
        }
        return isSameNodeList(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes());
    }

    private String getTextNodeContent(Node node) {
        String textContent = null;
        for (int i = 0, boundary = node.getChildNodes().getLength(); i < boundary; i++) {
            if (node.getChildNodes().item(i).getNodeType() == Node.TEXT_NODE) {
                textContent = node.getChildNodes().item(i).getTextContent().trim();
                break;
            }
        }
        return textContent;
    }

    private String getCDATANodeContent(Node node) {
        String textContent = null;
        for (int i = 0, boundary = node.getChildNodes().getLength(); i < boundary; i++) {
            if (node.getChildNodes().item(i).getNodeType() == Node.CDATA_SECTION_NODE) {
                textContent = node.getChildNodes().item(i).getTextContent();
                break;
            }
        }
        return textContent;
    }

    private boolean isSameNonNameSpaceAttribute(Node attributeA, Node attributeB) throws DOMException {
        return attributeB.getNodeType() == Node.ATTRIBUTE_NODE
                && !attributeB.getNodeName().startsWith(Common.ATTRIBUTE_XMLNS)
                && attributeA.getNodeName().equals(attributeB.getNodeName())
                && attributeA.getNodeValue().equals(attributeB.getNodeValue());
    }    
}
