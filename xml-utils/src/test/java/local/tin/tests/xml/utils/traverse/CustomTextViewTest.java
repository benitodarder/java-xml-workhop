package local.tin.tests.xml.utils.traverse;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import local.tin.tests.xml.utils.TestUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class CustomTextViewTest {

    public static final String XML_02_NODE_VALUE_01 = "          NodeA value 01        ";
    public static final String XML_02_NODE_VALUE_02 = "          NodeA value 02        ";
    public static final String SAMPLE_XML_01_CUSTOM_VIEW = "Element: nodeA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att01=att01 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att02=att02 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Element content: "
            + TestUtils.XML_01_NODE_VALUE + System.lineSeparator();
    public static final String SAMPLE_XML_02 = "<root>"
            + "   <nodesA>"
            + "       <nodeA att01=\"att01 value\">"
            + XML_02_NODE_VALUE_01
            + "</nodeA>"
            + "       <nodeA att01=\"att02 value\">"
            + XML_02_NODE_VALUE_02
            + "</nodeA>"
            + "    </nodesA>"
            + "</root>";
    public static final String SAMPLE_XML_02_CUSTOM_VIEW = "Element: nodesA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Element: nodeA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att01=att01 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "Element content: "
            + XML_02_NODE_VALUE_01 + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Element: nodeA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att01=att02 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "Element content: "
            + XML_02_NODE_VALUE_02 + System.lineSeparator();
       public static final String SAMPLE_XML_03 = "<root>"
            + "       <nodeA att01=\"att01 value\">"
            + XML_02_NODE_VALUE_01
            + "</nodeA>"
            + "       <nodeA att01=\"att02 value\">"
            + XML_02_NODE_VALUE_02
            + "</nodeA>"
            + "</root>";
    public static final String SAMPLE_XML_03_CUSTOM_VIEW = "Element: nodeA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att01=att01 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Element content: "
            + XML_02_NODE_VALUE_01 + System.lineSeparator()
            + "Element: nodeA" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + CustomTextView.INDENT_CHARACTER + "att01=att02 value" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "Element content: "
            + XML_02_NODE_VALUE_02 + System.lineSeparator(); 
    public static final String SAMPLE_XML_01_CUSTOM_VIEW_WITH_ROOT = "Root: root" + System.lineSeparator() 
            + "Attributes:" + System.lineSeparator()
            + CustomTextView.INDENT_CHARACTER + "a=b" + System.lineSeparator()            
            +  SAMPLE_XML_01_CUSTOM_VIEW;
   
    @Test
    public void getCustomXMLView_returns_expected_string_only_one_node() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TestUtils.getInstance().getDocumentFromString(TestUtils.SAMPLE_XML_01);
        Node node = doc.getFirstChild();

        String result = CustomTextView.getInstance().getCustomXMLView(node.getChildNodes());
        System.out.println(result);

        assertThat(result, equalTo(SAMPLE_XML_01_CUSTOM_VIEW));
    }

    @Test
    public void getCustomXMLView_returns_expected_string_a_list() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TestUtils.getInstance().getDocumentFromString(SAMPLE_XML_02);
        Node node = doc.getFirstChild();

        String result = CustomTextView.getInstance().getCustomXMLView(node.getChildNodes());
        System.out.println(result);

        assertThat(result, equalTo(SAMPLE_XML_02_CUSTOM_VIEW));
    }
    
    @Test
    public void getCustomXMLView_returns_expected_string_a_list_without_enclosing_node() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TestUtils.getInstance().getDocumentFromString(SAMPLE_XML_03);
        Node node = doc.getFirstChild();

        String result = CustomTextView.getInstance().getCustomXMLView(node.getChildNodes());
        System.out.println(result);

        assertThat(result, equalTo(SAMPLE_XML_03_CUSTOM_VIEW));
    }   
    
    @Test
    public void getCustomXMLView_returns_expected_string_only_one_node_with_root() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TestUtils.getInstance().getDocumentFromString(TestUtils.SAMPLE_XML_01);

        String result = CustomTextView.getInstance().getCustomXMLView(doc);
        System.out.println(result);

        assertThat(result, equalTo(SAMPLE_XML_01_CUSTOM_VIEW_WITH_ROOT));
    }       
}
