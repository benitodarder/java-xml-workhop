package local.tin.tests.xml.utils;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import local.tin.tests.xml.utils.TestUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class NodesComparatorTest {

    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_A = "twoSubnodesWithAttributesA.xml";
    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_A_REORDENED = "twoSubnodesWithAttributesA_Reordened.xml";
    private static final String TWO_SUBNODES_WITH_ATTRIBUTE_B = "twoSubnodesWithAttributesB.xml";
    private static final String NODE_WITH_ATTRIBUTE_AND_NAMESPACE_A = "oneNodeWithAttributesAAndNameSpaceA.xml";
    private static final String NODE_WITH_ATTRIBUTE_AND_NAMESPACE_B = "oneNodeWithAttributesBAndNameSpaceB.xml";
    private static final String NODE_WITH_ATTRIBUTES_A_C = "oneNodeWithAttributesAC.xml";
    private static final String NODE_WITH_ATTRIBUTES_C_A = "oneNodeWithAttributesCA.xml";
    private static final String NODE_WITH_ATTRIBUTES_A_C_CDATA = "oneNodeWithAttributesACWithCDATA.xml";
    private static final String NODE_WITH_ATTRIBUTES_C_A_CDATA = "oneNodeWithAttributesCAWithCDATA.xml";
    private static final String IS_SAME_NODE_LIST_SHALLOWLY_A = "isSameNodeListShallowlly_A.xml";
    private static final String IS_SAME_NODE_LIST_SHALLOWLY_B = "isSameNodeListShallowlly_B.xml";
    private Document documentA;
    private Document documentB;
    private Node nodeA;
    private Node nodeB;

    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_A), true);
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < documentA.getFirstChild().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (documentA.getFirstChild().getChildNodes().item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        nodeA = documentA.getFirstChild().getChildNodes().item(i).getChildNodes().item(j);
                        break;
                    }
                }

            }
        }
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_B), true);
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < documentB.getFirstChild().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (documentB.getFirstChild().getChildNodes().item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        nodeB = documentB.getFirstChild().getChildNodes().item(i).getChildNodes().item(j);
                        break;
                    }
                }

            }
        }
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_same_node() throws IOException, ParserConfigurationException, SAXException {
        short nodeAType = nodeA.getNodeType();
        String nodeATextContent = nodeA.getTextContent();
        String nodeANodeValue = nodeA.getNodeValue();

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeA);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_nodes_have_same_attributes_and_text_content() throws IOException, ParserConfigurationException, SAXException {

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_falsewhen_nodes_have_different_attributes_and_same_text_content() throws IOException, ParserConfigurationException, SAXException {
        NamedNodeMap nodeAAttributes = nodeA.getAttributes();
        int attributesLEnght = nodeAAttributes.getLength();
        for (int i = 0; i < attributesLEnght; i++) {

            if (nodeAAttributes.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
                nodeAAttributes.item(i).setNodeValue(nodeAAttributes.item(i).getNodeValue() + nodeAAttributes.item(i).getNodeValue());
                break;
            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_text_nodes_have_same_attributes_and_different_text_content() throws IOException, ParserConfigurationException, SAXException {
        nodeA.setTextContent(nodeA.getTextContent() + nodeA.getTextContent());

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_same_node() {
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;

            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeA);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_equals_nodes() {
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;

            }

        }

        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;

            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_number_of_childs_differs() {
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;

            }

        }

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

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_one_child_does_not_match() {
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;

            }

        }

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

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeList_returns_true_for_same_node_list() {

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentA.getFirstChild().getChildNodes());

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeList_returns_true_for_equal_node_list() {

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes());

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeList_returns_false_when_number_of_childs_differs() throws TransformerException {
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

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes());

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeList_returns_false_when_one_child_does_not_match() {

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

        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(documentA.getFirstChild().getChildNodes(), documentB.getFirstChild().getChildNodes());

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameDocument_returns_true_for_same_document() {

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentA);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameDocument_returns_true_for_equal_documents() {

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_ignores_namespace_attributes() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTE_AND_NAMESPACE_A), true);
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;
            }

        }

        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTE_AND_NAMESPACE_B), true);
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;
            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_expected_value_for_unordered_attributes() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_A_C), true);
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;
            }

        }

        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_C_A), true);
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;
            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameDocument_returns_true_for_equal_documents_with_diferent_order() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_A), true);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), TWO_SUBNODES_WITH_ATTRIBUTE_A_REORDENED), true);
        System.out.println("Document A: " + PrettyPrint.getInstance().getDocumentPrettyPrinted(documentA));
        System.out.println("Document B: " + PrettyPrint.getInstance().getDocumentPrettyPrinted(documentB));

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_ignores_whitespaces_when_not_in_cdata() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_A_C), true);
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeA = documentA.getFirstChild().getChildNodes().item(i);
                break;
            }
        }
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), NODE_WITH_ATTRIBUTES_C_A), true);
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                nodeB = documentB.getFirstChild().getChildNodes().item(i);
                break;
            }
        }
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

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB);
        boolean resultCData = NodesComparator.getInstance().isSameNodeShallowly(nodeA0, nodeB0);

        assertThat(result, equalTo(true));
        assertThat(resultCData, equalTo(false));
    }

    @Test
    public void isSameNodeListShallowly_returns_true_and_isSameNodeListDeeply_returns_false() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_A), true);
        nodeA = null;
        for (int i = 0; i < documentA.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentA.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < documentA.getFirstChild().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (documentA.getFirstChild().getChildNodes().item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        nodeA = documentA.getFirstChild().getChildNodes().item(i).getChildNodes().item(j);
                        break;
                    }
                }

            }
        }
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), IS_SAME_NODE_LIST_SHALLOWLY_B), true);
        nodeB = null;
        for (int i = 0; i < documentB.getFirstChild().getChildNodes().getLength(); i++) {
            if (documentB.getFirstChild().getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE) {
                for (int j = 0; j < documentB.getFirstChild().getChildNodes().item(i).getChildNodes().getLength(); j++) {
                    if (documentB.getFirstChild().getChildNodes().item(i).getChildNodes().item(j).getNodeType() == Node.ELEMENT_NODE) {
                        nodeB = documentB.getFirstChild().getChildNodes().item(i).getChildNodes().item(j);
                        break;
                    }
                }

            }
        }


        boolean resulShallowly = NodesComparator.getInstance().isSameNodeListShallowly(nodeA.getChildNodes(), nodeB.getChildNodes());        
        boolean resultDeeply = NodesComparator.getInstance().isSameNodeListDeeply(nodeA.getChildNodes(), nodeB.getChildNodes());

        assertThat(resulShallowly, equalTo(false));        
        assertThat(resultDeeply, equalTo(false));
    }
}
