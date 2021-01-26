package local.tin.tests.xml.utils;

import javax.xml.parsers.ParserConfigurationException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class BuilderTest {

    private static final String SAMPLE_XML = "sample.xml";
    private static final String SAMPLE_XML_WITH_NAMESPACE = "sampleWithNamespaces.xml";

    @Test
    public void getDocumentFromFile_returns_the_expected_document_from_file() throws ParserConfigurationException, Exception {

        Document result = Builder.getInstance().getDocumentFromFile(BuilderTest.class.getResource(SAMPLE_XML).getPath(), false);

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getTextContent(), equalTo("Tove"));
    }

    @Test
    public void getDocumentFromString_returns_the_expected_document_from_file() throws ParserConfigurationException, Exception {
        String sampleXML = TestUtils.getInstance().getFileAsString(PrettyPrint.class, SAMPLE_XML);

        Document result = Builder.getInstance().getDocumentFromString(sampleXML, false);

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getTextContent(), equalTo("Tove"));
    }

    @Test
    public void getDocumentFromString_ignores_namespace_aware_as_told() throws ParserConfigurationException, Exception {
        String sampleXML = TestUtils.getInstance().getFileAsString(PrettyPrint.class, SAMPLE_XML_WITH_NAMESPACE);

        Document result = Builder.getInstance().getDocumentFromString(sampleXML, false);

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNamespaceURI(), equalTo(null));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNodeName(), equalTo("a:from"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNamespaceURI(), equalTo(null));

    }

    @Test
    public void getDocumentFromString_process_namespaces_when_told() throws ParserConfigurationException, Exception {
        String sampleXML = TestUtils.getInstance().getFileAsString(PrettyPrint.class, SAMPLE_XML_WITH_NAMESPACE);

        Document result = Builder.getInstance().getDocumentFromString(sampleXML, true);

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNamespaceURI(), equalTo("http://a.b.com"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNodeName(), equalTo("a:from"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNamespaceURI(), equalTo("http://b.c.com"));
    }

}
