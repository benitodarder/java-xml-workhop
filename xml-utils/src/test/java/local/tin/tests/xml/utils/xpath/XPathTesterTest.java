package local.tin.tests.xml.utils.xpath;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import local.tin.tests.xml.utils.TestUtils;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class XPathTesterTest {

    private static final String DEFAULT_NAMESPACE_XML = "defaultNamespace.xml";

    @Before
    public void setUp() {
    }

    @Test(expected=local.tin.tests.xml.utils.errors.XMLUtilsException.class)
    public void throw_exception_with_local_name_when_namespace_aware() throws IOException, ParserConfigurationException, SAXException, XMLUtilsException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, DEFAULT_NAMESPACE_XML), true);
        XPathDetails xPathDetails = new XPathDetails();
        xPathDetails.setDocument(document);
        xPathDetails.setFakeDefaultNamespacePrefix("crap");
        xPathDetails.setNamespaceAware(true);
        xPathDetails.setXpathExpression("*[local-name() = 'root']/*[local-name() = 'nodeA']]/*[local-name() = 'nodeAA']");
        
        NodeList result = XPathTester.getInstance().getResult(xPathDetails);

    }
    
    @Test
    public void honors_local_name_when_namespace_unaware() throws IOException, ParserConfigurationException, SAXException, XMLUtilsException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, DEFAULT_NAMESPACE_XML), true);
        XPathDetails xPathDetails = new XPathDetails();
        xPathDetails.setDocument(document);
        xPathDetails.setFakeDefaultNamespacePrefix("crap");
        xPathDetails.setNamespaceAware(false);
        xPathDetails.setXpathExpression("*[local-name() = 'root']/*[local-name() = 'nodeA']/*[local-name() = 'nodeAA']");
        
        NodeList result = XPathTester.getInstance().getResult(xPathDetails);
        
        assertThat(result.getLength(), equalTo(1));
    }    
    
    @Test
    public void honors_local_name_when_namespace_unaware_with_complex_document() throws IOException, ParserConfigurationException, SAXException, XMLUtilsException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, "otaHotelAvailRS.xml"), true);
        XPathDetails xPathDetails = new XPathDetails();
        xPathDetails.setDocument(document);
        xPathDetails.setFakeDefaultNamespacePrefix("crap");
        xPathDetails.setNamespaceAware(false);
        xPathDetails.setXpathExpression("*[local-name() = 'Envelope']/*[local-name() = 'Body']/*[local-name() = 'OTA_HotelAvailRS'][@Target='Production' and @TransactionIdentifier='1-1/1' and @Version='2006']/*[local-name() = 'RoomStays']/*[local-name() = 'RoomStay'][@RPH='0' and @ResponseType='PropertyList' and @RoomStayCandidateRPH='0']");
        
        NodeList result = XPathTester.getInstance().getResult(xPathDetails);
        
        assertThat(result.getLength(), equalTo(1));
    }     
}
