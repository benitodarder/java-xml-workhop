package local.tin.tests.xml.utils.comparison;

import local.tin.tests.xml.utils.comparison.NodesComparator;
import local.tin.tests.xml.utils.comparison.ComparisonExclusion;
import local.tin.tests.xml.utils.comparison.ComparisonExclusions;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.TestUtils;
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

    private static final String COMPARISON_SOURCE_A = "comparisonSourceA.xml";
    private static final String COMPARISON_SOURCE_B = "comparisonSourceB.xml";
    private static final String COMPARISON_SOURCE_C = "comparisonSourceC.xml";  
    private static final String COMPARISON_SOURCE_D = "comparisonSourceD.xml";  
    private static final String COMPARISON_SOURCE_F = "comparisonSourceF.xml";  
    private static final String COMPARISON_SOURCE_G = "comparisonSourceG.xml";  
    private Document documentA;
    private Document documentB;
    private Node nodeA;
    private Node nodeB;
    private NodeList nodeListA;
    private NodeList nodeListB;

    @Before
    public void setUp() throws IOException, ParserConfigurationException, SAXException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_A), true);
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_B), true);        
    }


    @Test
    public void isSameNodeShallowly_returns_true_when_same_node_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeA, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_true_when_equal_node_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_nodes_have_different_attributes_and_same_text_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeShallowly_returns_false_when_text_nodes_have_same_attributes_and_different_text_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 1);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_same_node_exclusionless() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeA, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_true_for_equal_nodes_exclusionless() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_number_of_childs_differs_exclusionless() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }

    @Test
    public void isSameNodeDeeply_returns_false_when_one_child_does_not_match_exclsionless() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }


    @Test
    public void isSameNodeList_returns_true_for_same_node_list_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {    
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeC']", documentA);
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListA, null, null);

        assertThat(result, equalTo(true));
    } 
    
    @Test
    public void isSameNodeList_returns_true_for_equal_node_list_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {     
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeC']", documentA);     
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeC']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(true));
    }   
    
    @Test
    public void isSameNodeList_returns_false_for_node_list_with_different_number_of_elements_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {     
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeD']", documentA);
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeD']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(false));
    }     
    
    @Test
    public void isSameNodeList_returns_false_for_node_list_with_different_content_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {    
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeE']", documentA);
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeE']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, null, null);

        assertThat(result, equalTo(false));
    } 
    
    @Test
    public void isSameDocument_return_true_for_same_document_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_D), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_C), true);        
        
        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB, null, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameDocument_return_false_for_different_document_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {       
        
        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB, null, null);

        assertThat(result, equalTo(false));
    }    
    
        @Test
    public void isSameNodeShallowly_ignores_namespace_attributes_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeF']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeF']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }
    
        @Test
    public void isSameNodeShallowly_ignores_attributes_order_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeG']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeG']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameDocument_returns_true_for_equal_documents_with_diferent_order_exclsionless() throws IOException, ParserConfigurationException, SAXException, TransformerException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_D), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_F), true);

        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB, null, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeShallowly_ignores_whitespaces_when_not_in_cdata_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeH']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeH']", documentB, 0);        

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeDeeply_ignores_whitespaces_when_not_in_cdata_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeH']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeH']", documentB, 0);        

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }     
    
    @Test
    public void isSameNodeShallowly_returns_true_for_whitespaces_when_in_cdata_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeIA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeIA']", documentB, 0);        

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(true));
    }     
    
    @Test
    public void isSameNodeShallowly_returns_false_for_whitespaces_when_in_cdata_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeIA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeIA']", documentB, 1);        

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, null);

        assertThat(result, equalTo(false));
    }      
    
    @Test
    public void isSameNodeListShallowly_returns_true_and_isSameNodeListDeeply_returns_false_exclusionless() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeJ']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeJ']", documentB, 0);   

        boolean resulShallowly = NodesComparator.getInstance().isSameNodeListShallowly(nodeA.getChildNodes(), nodeB.getChildNodes(), null, null);
        boolean resultDeeply = NodesComparator.getInstance().isSameNodeListDeeply(nodeA.getChildNodes(), nodeB.getChildNodes(), null, null);

        assertThat(resulShallowly, equalTo(true));
        assertThat(resultDeeply, equalTo(false));
    }    

    @Test
    public void isSameNodeShallowly_returns_true_when_matching_nodes_and_is_excluded_by_node_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 0);
        ComparisonExclusions nodeExclusions = TestUtils.getInstance().getNodeExclusions(nodeA);
  

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(true));
    } 
    
    @Test
    public void isSameNodeShallowly_returns_true_when_no_matching_nodes_and_is_excluded_by_node_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 1);
        ComparisonExclusions nodeExclusions = TestUtils.getInstance().getNodeExclusions(nodeA);
  

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeShallowly_returns_true_when_matching_nodes_and_is_excluded_by_attribute_with_attribute_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 0);
        
        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, TestUtils.getInstance().getAttributeExclusions(nodeA, "a"));

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeShallowly_returns_true_when_no_matching_nodes_and_is_excluded_by_attribute_with_attribute_exclusions() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentB, 0);        
        NamedNodeMap nodeAAttributes = nodeA.getAttributes();
        int attributesLEnght = nodeAAttributes.getLength();
        for (int i = 0; i < attributesLEnght; i++) {

            if (nodeAAttributes.item(i).getNodeType() == Node.ATTRIBUTE_NODE) {
                nodeAAttributes.item(i).setNodeValue(nodeAAttributes.item(i).getNodeValue() + nodeAAttributes.item(i).getNodeValue());
                break;
            }

        }

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, TestUtils.getInstance().getAttributeExclusions(nodeA, "a"));

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeDeeply_returns_true_for_equal_nodes_with_node_exclusion() throws IOException, ParserConfigurationException, XPathExpressionException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeDeeply_returns_true_when_number_of_childs_differs_with_node_excluion() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeDeeply_returns_true_when_one_child_does_not_match_with_node_exclsion() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentA, 1);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeB']", documentB, 1);

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isSameNodeList_returns_true_for_equal_node_list_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {    
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeC']", documentA);
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeCA']", documentA, 0);     
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeC']", documentB);        
        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }      
    
    @Test
    public void isSameNodeList_returns_true_for_node_list_with_different_number_of_elements_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {     
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeD']", documentA);
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeDA']", documentA, 0);  
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeD']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }       
    
    @Test
    public void isSameNodeList_returns_true_for_node_list_with_different_number_of_elements_with_node_exclusion_and_one_alternate_node_not_excluded() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {  
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeKA']", documentA);
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeKA']", documentA, 0); 
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeKA']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(true));
    }       
    
    @Test
    public void isSameNodeList_returns_false_for_node_list_with_different_number_of_elements_with_node_exclusion_and_one_alternate_node_excluded() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {  
        nodeListA = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeKA']", documentA);
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeKB']", documentA, 0);  
        nodeListB = TestUtils.getInstance().getNodeListByXPath("//*[local-name() = 'nodeKA']", documentB);        
        
        boolean result = NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, TestUtils.getInstance().getNodeExclusions(nodeA), null);

        assertThat(result, equalTo(false));
    }     
    
    @Test
    public void isSameNodeShallowly_returns_true_when_no_matching_nodes_names_and_is_excluded_by_node_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeCA']", documentB, 0);
        ComparisonExclusions nodeExclusions = TestUtils.getInstance().getNodeExclusions(nodeA);
  

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(false));
    }     
    
    @Test
    public void isSameNodeShallowly_returns_false_when_one_excluded_attribute_is_missing_and_there_is_one_extra_attribute_with_attribute_exclusions() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeLA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeLA']", documentB, 0);

        boolean result = NodesComparator.getInstance().isSameNodeShallowly(nodeA, nodeB, null, TestUtils.getInstance().getAttributeExclusions(nodeA, "b"));

        assertThat(result, equalTo(false));
    }    
    
    @Test
    public void isSameNodeDeeply_returns_true_when_no_matching_nodes_names_and_is_excluded_by_node_with_node_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);
        nodeB = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeCA']", documentB, 0);
        ComparisonExclusions nodeExclusions = TestUtils.getInstance().getNodeExclusions(nodeA);
  

        boolean result = NodesComparator.getInstance().isSameNodeDeeply(nodeA, nodeB, nodeExclusions, null);

        assertThat(result, equalTo(false));
    }       
    
    @Test
    public void isSameDocument_return_false_for_different_document_with_attribute_exclusion() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        documentA = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_F), true);        
        documentB = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(getClass(), COMPARISON_SOURCE_G), true);        
        nodeA = TestUtils.getInstance().getNodeByXPath("//*[local-name() = 'nodeAA']", documentA, 0);        
        
        boolean result = NodesComparator.getInstance().isSameDocument(documentA, documentB, null, TestUtils.getInstance().getAttributeExclusions(nodeA, "a"));

        assertThat(result, equalTo(true));
    }       
}
