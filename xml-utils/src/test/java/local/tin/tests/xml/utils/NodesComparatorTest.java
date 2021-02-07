package local.tin.tests.xml.utils;

import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.namespaces.DocumentNamespaces;
import local.tin.tests.xml.utils.namespaces.NamespaceResolver;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class NodesComparatorTest {

    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_A = "twoSubnodesWithAttributesA.xml";
    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_A_REORDENED = "twoSubnodesWithAttributesA_Reordened.xml";
    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_B = "twoSubnodesWithAttributesB.xml";
    private static final String NODE_WITH_ATTRIBUTES_A_C = "oneNodeWithAttributesAC.xml";
    private static final String NODE_WITH_ATTRIBUTES_C_A = "oneNodeWithAttributesCA.xml";
    private static final String NODE_WITH_ATTRIBUTES_A_C_CDATA = "oneNodeWithAttributesACWithCDATA.xml";
    private static final String NODE_WITH_ATTRIBUTES_C_A_CDATA = "oneNodeWithAttributesCAWithCDATA.xml";
    private static final String IS_SAME_NODE_LIST_SHALLOWLY_A = "isSameNodeListShallowlly_A.xml";
    private static final String IS_SAME_NODE_LIST_SHALLOWLY_B = "isSameNodeListShallowlly_B.xml";
    private static final String TWO_SUBNODES_WITH_TWO_ATTRIBUTE_A = "twoSubnodesWithTwoAttributesA.xml";
    private static final String TWO_SUBNODES_WITH_TWO_ATTRIBUTE_B = "twoSubnodesWithTwoAttributesB.xml";
    private static final String COMPARISON_SOURCE_A = "comparisonSourceA.xml";
    private static final String COMPARISON_SOURCE_B = "comparisonSourceB.xml";
    private static final String COMPARISON_SOURCE_C = "comparisonSourceC.xml";  
    private static final String COMPARISON_SOURCE_D = "comparisonSourceD.xml";  
    private static final String COMPARISON_SOURCE_F = "comparisonSourceF.xml";  
    private Document documentA;
    private Document documentB;
    private Node nodeA;
    private Node nodeB;
    private NodeList nodeListA;
    private NodeList nodeListB;

    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_A), true);
        nodeA = get2ndLevelNodeFromDocument(documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_B), true);
        nodeB = get2ndLevelNodeFromDocument(documentB);
    }

    public Node get2ndLevelNodeFromDocument(Document document) {
        Node node = null;
        for (int i = 0; i < document.getFirstChild().getChildNodes().getLength(); i++) {
            if (document.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < document.getFirstChild().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (document.getFirstChild().getChildNodes().item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        node = document.getFirstChild().getChildNodes().item(i).getChildNodes().item(j);
                        break;
                    }
                }

            }
        }
        return node;
    }

    private Node get1stLevelNode(Document document) {
        Node node = null;
        for (int i = 0; i < document.getFirstChild().getChildNodes().getLength(); i++) {
            if (document.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                node = document.getFirstChild().getChildNodes().item(i);
                break;

            }

        }
        return node;
    }



    @Test
    public void isSameNodeShallowly_ignores_whitespaces_when_not_in_cdata() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_A_C), true);
        nodeA = get1stLevelNode(documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_C_A), true);
        nodeB = get1stLevelNode(documentB);
        Document documentA0 = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_A_C_CDATA), true);
        Node nodeA0 = null;
        for (int i = 0; i < documentA0.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA0.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA0 = documentA0.getFirstChild().getChildNodes().item(i);
                break;
            }

        }
        Document documentB0 = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_C_A_CDATA), true);
        Node nodeB0 = null;
        for (int i = 0; i < documentB0.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB0.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB0 = documentB0.getFirstChild().getChildNodes().item(i);
                break;
            }
        }
        short nodeAType = nodeA.getNodeType();
        String nodeATextContent = nodeA.getTextContent();
        String nodeANodeValue = nodeA.getNodeValue();

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);
        boolean resultCData = NodesComparator.getInstance().isSameNodeShallowly(nodeA0, nodeB0, null, null);

        assertThat(result, equalTo(true));
        assertThat(resultCData, equalTo(false));
    }

    @Test
    public void isSameNodeListShallowly_returns_true_and_isSameNodeListDeeply_returns_false() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_A), true);
        nodeA = get2ndLevelNodeFromDocument(documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_B), true);
        nodeB = get2ndLevelNodeFromDocument(documentB);

        boolean resulShallowly = NodesComparator.getInstance().isSameNodeListShallowly(nodeA.getChildNodes(), nodeB.getChildNodes());
        boolean resultDeeply = NodesComparator.getInstance().isSameNodeListDeeply(nodeA.getChildNodes(), nodeB.getChildNodes(), null, null);

        assertThat(resulShallowly, equalTo(false));
        assertThat(resultDeeply, equalTo(false));
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_matching_nodes_and_is_excluded_by_node() throws IOException, ParserConfigurationException, SAXException {
        ComparisonExclusions nodeExclusions = getNodeExclusions(nodeA);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(true));
    }

    private ComparisonExclusions getNodeExclusions(Node node) {
        ComparisonExclusions nodeExclusions = new ComparisonExclusions();
        ComparisonExclusion exclusion = new ComparisonExclusion();
        exclusion.setNodeLocalName(node.getLocalName());
        exclusion.setParentLocalName(node.getParentNode().getLocalName());
        nodeExclusions.put(exclusion.getNodeLocalName(), exclusion);
        return nodeExclusions;
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_no_matching_nodes_and_is_excluded_by_node() throws IOException, ParserConfigurationException, SAXException {
        ComparisonExclusions nodeExclusions = getNodeExclusions(nodeA);
        nodeA.setTextContent(nodeA.getTextContent() + nodeA.getTextContent());

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_matching_nodes_and_is_excluded_by_attribute() throws IOException, ParserConfigurationException, SAXException {

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, getAttributeExclusions(nodeA, "a"));

        assertThat(result, equalTo(true));
    }

    private ComparisonExclusions getAttributeExclusions(Node node, String attributeName) {
        ComparisonExclusions attrbutesExclusions = new ComparisonExclusions();
        ComparisonExclusion exclusion = new ComparisonExclusion();
        exclusion.setAttributeName(attributeName);
        exclusion.setNodeLocalName(node.getLocalName());
        exclusion.setParentLocalName(node.getParentNode().getLocalName());
        attrbutesExclusions.put(exclusion.getAttributeName(), exclusion);
        return attrbutesExclusions;
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_un_matching_nodes_and_is_excluded_by_attribute() throws IOException, ParserConfigurationException, SAXException {
        NamedNodeMap nodeAAttributes = nodeA.getAttributes();
        int attributesLEnght = nodeAAttributes.getLength();
        for (int i = 0; i < attributesLEnght; i++) {

            if (nodeAAttributes.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
                nodeAAttributes.item(i).setNodeValue(nodeAAttributes.item(i).getNodeValue() + nodeAAttributes.item(i).getNodeValue());
                break;
            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, getAttributeExclusions(nodeA, "a"));

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_equals_nodes_excluded() {
        nodeA = get1stLevelNode(documentA);
        nodeB = get1stLevelNode(documentB);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_when_number_of_childs_differs_and_are_excluded() {
        nodeA = get1stLevelNode(documentA);
        nodeB = get1stLevelNode(documentB);
        for (int i = 0; i < nodeB.getChildNodes().getLength(); i++) {
            if (nodeB.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB.appendChild(nodeB.getChildNodes().item(i).cloneNode(true));
                break;
            }
        }

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_when_one_child_does_not_match_but_are_excluded() {
        nodeA = get1stLevelNode(documentA);
        nodeB = get1stLevelNode(documentB);
        for (int i = 0; i < nodeB.getChildNodes().getLength(); i++) {
            if (nodeB.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB.getChildNodes().item(i).setTextContent(nodeB.getChildNodes().item(i).getTextContent() + nodeB.getChildNodes().item(i).getTextContent());
            }
        }

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeList_returns_true_for_equal_node_list_with_exclusion() {

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes(), getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeList_returns_true_when_number_of_childs_differs_with_exclusions() throws TransformerException {
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;

            }

        }
        for (int i = 0; i < nodeB.getChildNodes().getLength(); i++) {
            if (nodeB.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB.appendChild(nodeB.getChildNodes().item(i).cloneNode(true));
                break;
            }
        }
        System.out.println("Document A: " + PrettyPrint.getInstance().getDocumentPrettyPrinted(documentA));
        System.out.println("Document B: " + PrettyPrint.getInstance().getDocumentPrettyPrinted(documentB));

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes(), getNodeExclusions(nodeB), null);
        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeList_returns_true_when_one_child_does_not_match_with_exclusion() {

        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;

            }

        }
        for (int i = 0; i < nodeB.getChildNodes().getLength(); i++) {
            if (nodeB.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB.getChildNodes().item(i).setTextContent(nodeB.getChildNodes().item(i).getTextContent() + nodeB.getChildNodes().item(i).getTextContent());
            }
        }

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes(), getNodeExclusions(nodeB), null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameDocument_returns_false_for_different_documents() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_A), true);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_B), true);

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_no_matching_nodes_name_and_is_excluded_by_node() throws IOException, ParserConfigurationException, SAXException {
        ComparisonExclusions nodeExclusions = getNodeExclusions(nodeA);
        nodeB = get1stLevelNode(documentB);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShalloly_returns_false_when_one_excluded_attribute_is_missing_and_there_is_one_extra_attribute() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_TWO_ATTRIBUTE_A), true);
        nodeA = get2ndLevelNodeFromDocument(documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_TWO_ATTRIBUTE_B), true);
        nodeB = get2ndLevelNodeFromDocument(documentB);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, getAttributeExclusions(nodeA, "b"), null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShalloly_returns_false_when_one_excluded_attribute_is_missing_and_there_is_one_extra_attribute_backwards() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_TWO_ATTRIBUTE_A), true);
        nodeA = get2ndLevelNodeFromDocument(documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_TWO_ATTRIBUTE_B), true);
        nodeB = get2ndLevelNodeFromDocument(documentB);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, getAttributeExclusions(nodeB, "c"), null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_no_matching_nodes_name_and_is_excluded_by_node() throws IOException, ParserConfigurationException, SAXException {
        ComparisonExclusions nodeExclusions = getNodeExclusions(nodeA);
        nodeB = get1stLevelNode(documentB);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(false));
    }

    private Node getNodeByXPath(String xPathString, Document document, int i) throws XPathExpressionException {
        return getNodeListByXPath(xPathString, document).item(i);
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_same_node_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeA, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_equal_node_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_nodes_have_different_attributes_and_same_text_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_text_nodes_have_same_attributes_and_different_text_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 1);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_same_node_exclusionless() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeA, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_equal_nodes_exclusionless() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 0);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_number_of_childs_differs_exclusionless() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_one_child_does_not_match_exclsionless() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    private NodeList getNodeListByXPath(String xPathString, Document document) throws XPathExpressionException {
        XPathFactory xFactory = XPathFactory.newInstance();
        XPath xPath = xFactory.newXPath();
        XPathExpression xExpression = xPath.compile(xPathString);
        return ((NodeList) xExpression.evaluate(document, XPathConstants.NODESET));
    }
    
    @Test
    public void isSameNodeList_returns_true_for_same_node_list_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);        
        nodeListA = getNodeListByXPath("//*[local-name() = 'nodeC']", documentA);
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListA, null, null);

        assertThat(result, equalTo(true));
    } 
    
    @Test
    public void isSameNodeList_returns_true_for_equal_node_list_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);        
        nodeListA = getNodeListByXPath("//*[local-name() = 'nodeC']", documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);        
        nodeListB = getNodeListByXPath("//*[local-name() = 'nodeC']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(true));
    }   
    
    @Test
    public void isSameNodeList_returns_false_for_node_list_with_different_number_of_elements_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);        
        nodeListA = getNodeListByXPath("//*[local-name() = 'nodeD']", documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);        
        nodeListB = getNodeListByXPath("//*[local-name() = 'nodeD']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(false));
    }     
    
    @Test
    public void isSameNodeList_returns_false_for_node_list_with_different_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);        
        nodeListA = getNodeListByXPath("//*[local-name() = 'nodeE']", documentA);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);        
        nodeListB = getNodeListByXPath("//*[local-name() = 'nodeE']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(false));
    } 
    
    @Test
    public void isSameDocument_return_true_for_same_document_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_D), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_C), true);        
        
        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameDocument_return_false_for_different_document_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);        
        
        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(false));
    }    
    
        @Test
    public void isSameNodeShallowly_ignores_namespace_attributes_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeF']", documentA, 0);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeF']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }
    
        @Test
    public void isSameNodeShallowly_ignores_attributes_order_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        nodeA = getNodeByXPath("//*[local-name() = 'nodeG']", documentA, 0);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);
        nodeB = getNodeByXPath("//*[local-name() = 'nodeG']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameDocument_returns_true_for_equal_documents_with_diferent_order_exclsionless() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_D), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_F), true);

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(true));
    }    
}
