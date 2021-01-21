package local.tin.tests.xml.utils.nodes;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
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
     * Returs true when nodeA and nodeB have:
     * <ul>
     * <li>Same attributes</li>
     * <li>Same text content</li>
     * </ul>
     * The following aspects are not considered:
     * <ul>
     * <li>Presence of child nodes</li>
     * <li>Namespace</li>
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
                if (nodeAAttributes.item(i).getNodeType() == Node.ATTRIBUTE_NODE
                        && !nodeAAttributes.item(i).getNodeName().startsWith(ATTRIBUTE_NAMESPACE_PREFIX)) {
                    boolean found = false;
                    for (int j = 0; j < attributesLEnght; j++) {

                        if (nodeBAttributes.item(j).getNodeType() == Node.ATTRIBUTE_NODE
                                && !nodeBAttributes.item(j).getNodeName().startsWith(ATTRIBUTE_NAMESPACE_PREFIX)
                                && nodeAAttributes.item(i).getNodeName().equals(nodeBAttributes.item(j).getNodeName())
                                && nodeAAttributes.item(i).getNodeValue().equals(nodeBAttributes.item(j).getNodeValue())) {
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
        return nodeA.getTextContent().equals(nodeB.getTextContent());
    }
    private static final String ATTRIBUTE_NAMESPACE_PREFIX = "xmlns";

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
        return isSameNodeDeeply(documentA.getFirstChild(), documentB.getFirstChild());
    }
}
