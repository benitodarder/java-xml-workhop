package local.tin.tests.xml.utils.traverse;

import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class DocumentNameSpacesTest {

    public static final String SAMPLE_XML_02 = "<root a=\"b\" xmlns:ns01=\"http://www.a.b\">" + System.lineSeparator()
            + "   <nodeA att01=\"att01 value\" att02=\"att02 value\">"
            + TraverseTestUtils.XML_01_NODE_VALUE
            + "</nodeA>"
            + "</root>";
     public static final String SAMPLE_XML_03 = "<root a=\"b\" >" + System.lineSeparator()
            + "   <nodeA att01=\"att01 value\" att02=\"att02 value\" xmlns:ns01=\"http://www.a.b\">"
            + TraverseTestUtils.XML_01_NODE_VALUE
            + "</nodeA>"
            + "</root>";  
     public static final String SAMPLE_XML_04 = "<root a=\"b\" >" + System.lineSeparator()
            + "   <nodeA att01=\"att01 value\" att02=\"att02 value\" xmlns=\"http://www.a.b\">"
            + TraverseTestUtils.XML_01_NODE_VALUE
            + "</nodeA>"
            + "</root>";      

    @Test
    public void getDocumentNamespaces_returns_empty_map_when_no_namespaces() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TraverseTestUtils.getInstance().getDocumentFromString(TraverseTestUtils.SAMPLE_XML_01);

        Map<String, String> result = DocumentNameSpaces.getInstance().getDocumentNamespaces(doc);

        assertThat(result.isEmpty(), equalTo(true));
    }

    @Test
    public void getDocumentNamespaces_returns_root_namespaces() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TraverseTestUtils.getInstance().getDocumentFromString(SAMPLE_XML_02);

        Map<String, String> result = DocumentNameSpaces.getInstance().getDocumentNamespaces(doc);

        assertThat(result.isEmpty(), equalTo(false));
        assertThat(result.size(), equalTo(1));
        assertThat(result.get("xmlns:ns01"), equalTo("http://www.a.b"));
    }
    
    @Test
    public void getDocumentNamespaces_returns_inner_namespaces() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TraverseTestUtils.getInstance().getDocumentFromString(SAMPLE_XML_03);

        Map<String, String> result = DocumentNameSpaces.getInstance().getDocumentNamespaces(doc);

        assertThat(result.isEmpty(), equalTo(false));
        assertThat(result.size(), equalTo(1));
        assertThat(result.get("xmlns:ns01"), equalTo("http://www.a.b"));
    }    
    
    @Test
    public void getDocumentNamespaces_returns_default_namespaces() throws ParserConfigurationException, SAXException, IOException {
        Document doc = TraverseTestUtils.getInstance().getDocumentFromString(SAMPLE_XML_04);

        Map<String, String> result = DocumentNameSpaces.getInstance().getDocumentNamespaces(doc);

        assertThat(result.isEmpty(), equalTo(false));
        assertThat(result.size(), equalTo(1));
        assertThat(result.get("xmlns"), equalTo("http://www.a.b"));
    }      
}
