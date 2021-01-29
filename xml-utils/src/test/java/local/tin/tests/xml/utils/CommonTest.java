package local.tin.tests.xml.utils;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import static local.tin.tests.xml.utils.CustomTextViewTest.XML_02_NODE_VALUE_01;
import static local.tin.tests.xml.utils.CustomTextViewTest.XML_02_NODE_VALUE_02;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class CommonTest {
    
    public static final String ATTRIBUTE_DEFAULT_NS = "<root>"
            + "<nodesA xmlns=\"http://a.b.com\">"
            + " Node A"
            + "</nodesA>"
            + "</root>";    
    public static final String ATTRIBUTE_NS = "<root>"
            + "<nodesA xmlns:crap=\"http://a.b.com\">"
            + " Node A"
            + "</nodesA>"
            + "</root>";  
    public static final String ATTRIBUTE_NO_NS = "<root>"
            + "<nodesA crap=\"http://a.b.com\">"
            + " Node A"
            + "</nodesA>"
            + "</root>";      
    private Node node;
    private Document document;
    

    
    @Test
    public void isNamesapceAttribute_returns_true_for_default_namespace_attribute() throws ParserConfigurationException, SAXException, IOException {
        node = TestUtils.getInstance().getDocumentFromString(ATTRIBUTE_DEFAULT_NS, true).getFirstChild().getChildNodes().item(0).getAttributes().item(0);
        
        boolean result = Common.getInstance().isNamespaceAttribute(node);
        
        assertThat(result, equalTo(true));
    }
    
    @Test
    public void isNamesapceAttribute_returns_true_for_namespace_attribute() throws ParserConfigurationException, SAXException, IOException {
        node = TestUtils.getInstance().getDocumentFromString(ATTRIBUTE_NS, true).getFirstChild().getChildNodes().item(0).getAttributes().item(0);
        
        boolean result = Common.getInstance().isNamespaceAttribute(node);
        
        assertThat(result, equalTo(true));
    }    
    
    @Test
    public void isNamesapceAttribute_returns_false_for_non_namespace_attribute() throws ParserConfigurationException, SAXException, IOException {
        node = TestUtils.getInstance().getDocumentFromString(ATTRIBUTE_NO_NS, true).getFirstChild().getChildNodes().item(0).getAttributes().item(0);
        
        boolean result = Common.getInstance().isNamespaceAttribute(node);
        
        assertThat(result, equalTo(false));
    }      
}
