package local.tin.tests.xml.utils;

import org.apache.log4j.Logger;
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

    private static final Logger LOGGER = Logger.getLogger(NodesComparator.class);

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
     * <li>Comparing node local name</li>
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
     * @param nodes as ComparisonExclusions
     * @param attributes as ComparisonExclusions
     * @return boolean
     */
    public boolean isSameNodeShallowly(Node nodeA, Node nodeB, ComparisonExclusions nodes, ComparisonExclusions attributes) {
        if (!nodeA.getLocalName().equals(nodeB.getLocalName())) {
            return false;
        }
        if (isNodeExclusionApplied(nodes, nodeA, nodeB)) {
            return true;
        }        
        NamedNodeMap nodeAAttributes = nodeA.getAttributes();
        NamedNodeMap nodeBAttributes = nodeB.getAttributes();
        if (nodeAAttributes.getLength() != nodeBAttributes.getLength()) {
            return false;
        } else {
            int attributesLenght = nodeAAttributes.getLength();
            for (int i = 0; i < attributesLenght; i++) {
                if (isNonNameSpaceAttribute(nodeAAttributes.item(i)) && !isAttributeExcluded(attributes, nodeAAttributes.item(i).getNodeName(), nodeA.getLocalName())) {
                    boolean found = false;
                    for (int j = 0; j < attributesLenght; j++) {
                        if (isSameNonNameSpaceAttribute(nodeAAttributes.item(i), nodeBAttributes.item(j)) && !isAttributeExcluded(attributes, nodeBAttributes.item(j).getNodeName(), nodeB.getLocalName())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        LOGGER.debug("Node: " + nodeA.getNodeName() + ", attribute: " + nodeAAttributes.item(i) + " unmatched in node: " + nodeB.getNodeName());
                        return false;
                    }
                }
            }
        }
        String nodeACData = getCDATANodeContent(nodeA);
        String nodeBCData = getCDATANodeContent(nodeB);
        if (nodeACData != null && nodeBCData != null) {
            return nodeACData.equals(nodeBCData);
        }
        String nodeAText = getTextNodeContent(nodeA);
        String nodeBText = getTextNodeContent(nodeB);
        if (nodeAText != null && nodeBText != null) {
            return nodeAText.equals(nodeBText);
        }
        if  (nodeACData == null && nodeBCData == null && nodeAText == null && nodeBText == null) {
            return true;
        }
        LOGGER.debug("Node: " + nodeA.getNodeName() + " unmatched CDATA and text with node: " + nodeB.getNodeName());
        return false;
    }


    private boolean isNonNameSpaceAttribute(Node node) {
        return node.getNodeType() == Node.ATTRIBUTE_NODE
                && !node.getNodeName().startsWith(Common.ATTRIBUTE_XMLNS);
    }

    /**
     * Compares the node shallowly and if they are equal, compare all child
     * nodes deeply.
     *
     * @param nodeA as Node
     * @param nodeB as Node
     * @param nodes as ComparisonExclusions
     * @param attributes as ComparisonExclusions 
     * @return boolean
     */
    public boolean isSameNodeDeeply(Node nodeA, Node nodeB, ComparisonExclusions nodes, ComparisonExclusions attributes) {      
        if (!isSameNodeShallowly(nodeA, nodeB, nodes, attributes)) {
            return false;
        }
        if (isNodeExclusionApplied(nodes, nodeA, nodeB)) {
            return true;
        }          
        return isSameNodeListDeeply(nodeA.getChildNodes(), nodeB.getChildNodes(), nodes, attributes);
    }

    /**
     * Returns true if both node lists contain the same nodes, compared as
     * isSameNodeDeeply defines.
     *
     * @param nodeListA as NodeList
     * @param nodeListB as NodeList
     * @param nodes as ComparisonExclusions
     * @param attributes as ComparisonExclusions
     * @return boolean
     */
    public boolean isSameNodeListDeeply(NodeList nodeListA, NodeList nodeListB, ComparisonExclusions nodes, ComparisonExclusions attributes) {
        if (nodeListA.getLength() != nodeListB.getLength()) {
            return false;
        }
        int nodesChilds = nodeListA.getLength();
        for (int i = 0; i < nodesChilds; i++) {
            if (nodeListA.item(i).getNodeType() == Node.ELEMENT_NODE && !isNodeExcluded(nodes, nodeListA.item(i))) {
                boolean found = false;
                for (int j = 0; j < nodesChilds; j++) {
                    if (nodeListB.item(j).getNodeType() == Node.ELEMENT_NODE
                            && isSameNodeDeeply(nodeListA.item(i), nodeListB.item(j), nodes, attributes)) {
                        found = true;
                        break;
                    } else if (isNodeExcluded(nodes, nodeListB.item(j))) {
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
        if (!isSameNodeShallowly(documentA.getFirstChild(), documentB.getFirstChild(), null, null)) {
            return false;
        }
        return isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes(), null, null);
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
    
    /**
     * Returns true if both node lists contain the same number of nodesnodes, 
     * and they match isSameNodeShawlloly
     *
     * @param nodeListA as NodeList
     * @param nodeListB as NodeList
     * @return boolean
     */
    public boolean isSameNodeListShallowly(NodeList nodeListA, NodeList nodeListB) {
        if (nodeListA.getLength() != nodeListB.getLength()) {
            return false;
        }
        int nodesChilds = nodeListA.getLength();
        for (int i = 0; i < nodesChilds; i++) {
            if (nodeListA.item(i).getNodeType() == Node.ELEMENT_NODE) {
                boolean found = false;
                for (int j = 0; j < nodesChilds; j++) {
                    if (nodeListB.item(j).getNodeType() == Node.ELEMENT_NODE
                            && isSameNodeShallowly(nodeListA.item(i), nodeListB.item(j), null, null)) {
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

    private boolean isNodeExclusionApplied(ComparisonExclusions nodes, Node nodeA, Node nodeB) {
        return nodes != null && 
                (nodes.containsByParent(nodeA.getLocalName(), nodeA.getParentNode().getLocalName()) ||
                nodes.containsByParent(nodeB.getLocalName(), nodeB.getParentNode().getLocalName()));
    }
    
    private boolean isAttributeExcluded(ComparisonExclusions nodes, String attributeName, String nodeLocalName) {
        return nodes != null && nodes.containsByNode(attributeName, nodeLocalName);
    }    
    
    private boolean isNodeExcluded(ComparisonExclusions nodes, Node nodeA) {
        return nodes != null && nodes.containsByNode(nodeA.getLocalName(), nodeA.getParentNode().getLocalName());
    }
}
