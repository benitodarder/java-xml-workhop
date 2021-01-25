package local.tin.tests.xml.utils;


import local.tin.tests.xml.utils.PrettyPrint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import local.tin.tests.xml.utils.TestUtils;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
/**
 *
 * @author benitodarder
 */
public class PrettyPrintTest {
    
    private static final String SAMPLE_XML_FILE_NAME = "sample.xml";    
    
 
    @Test
    public void getDocumentPrettyPrinted_returns_the_expected_document_from_file() throws ParserConfigurationException, Exception {
        String sampleXML = TestUtils.getInstance().getFileAsString(PrettyPrint.class, SAMPLE_XML_FILE_NAME);
        
        String result = PrettyPrint.getInstance().getDocumentPrettyPrinted(TestUtils.getInstance().getDocumentFromString(sampleXML));
        
        assertThat(result, notNullValue());
    }    
    
     
}