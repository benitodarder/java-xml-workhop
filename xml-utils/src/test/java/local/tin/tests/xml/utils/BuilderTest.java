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

    private static final String SAMPLE_XML_FILE_NAME = "sample.xml";

    @Test
    public void getDocumentFromFile_returns_the_expected_document_from_file() throws ParserConfigurationException, Exception {

        Document result = Builder.getInstance().getDocumentFromFile(BuilderTest.class.getResource(SAMPLE_XML_FILE_NAME).getPath());

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getTextContent(), equalTo("Tove"));
    }

    @Test
    public void getDocumentFromString_returns_the_expected_document_from_file() throws ParserConfigurationException, Exception {
        String sampleXML = TestUtils.getInstance().getFileAsString(PrettyPrint.class, SAMPLE_XML_FILE_NAME);

        Document result = Builder.getInstance().getDocumentFromString(sampleXML);

        assertThat(result.getDocumentElement().getNodeName(), equalTo("note"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getNodeName(), equalTo("to"));
        assertThat(result.getDocumentElement().getFirstChild().getNextSibling().getTextContent(), equalTo("Tove"));
    }

}
