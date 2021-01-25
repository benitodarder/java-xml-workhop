package local.tin.tests.xml.utils;

import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class ValidationTest {

    private static final String SAMPLE_XML_FILE_NAME = "sample.xml";      
    private static final String SAMPLE_XML_ERROR_NOT_MATCHING_END = "sampleError.xml";    
    
    @Test
    public void checkWellFormednessResult_returns_empty_list_when_no_errors_nor_exception() throws ParserConfigurationException, SAXException, Exception {   
        String sampleXML = TestUtils.getInstance().getFileAsString(Validation.class, SAMPLE_XML_FILE_NAME);

        List<String> result = Validation.getInstance().getParsingErrorsList(sampleXML);

        assertThat(result.isEmpty(), equalTo(true));
    }      
    
    @Test
    public void checkWellFormednessResult_returns_expected_error() throws ParserConfigurationException, SAXException, Exception {   
        String sampleXML = TestUtils.getInstance().getFileAsString(Validation.class, SAMPLE_XML_ERROR_NOT_MATCHING_END);

        List<String> result = Validation.getInstance().getParsingErrorsList(sampleXML);

        assertThat(result.isEmpty(), equalTo(false));
    }        
}
