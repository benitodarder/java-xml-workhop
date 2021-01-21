package local.tin.tests.xml.utils.xpath;

import local.tin.tests.xml.utils.namespaces.NamespaceResolver;
import java.util.HashMap;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import local.tin.tests.xml.utils.XMLUtilsException;
import local.tin.tests.xml.utils.namespaces.DocumentNamespaces;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({DocumentNamespaces.class, XPathFactory.class})
public class XPathTesterTest {

    private static final String SAMPLE_EXPRESSION_01 = "gñ";
    private static final String FAKE_DEFAULT_PREFIX = "crp";
    private static final String SAMPLE_EXPRESSION_02 = "//thing";
    private static final String SAMPLE_EXPRESSION_02_FAKED = "//" + FAKE_DEFAULT_PREFIX + NamespaceResolver.NAMESPACE_SEPARATOR + "thing";
    private static DocumentNamespaces mockedDocumentNamespaces;
    private XPathFactory mockedXPathFactory;
    private Document mockedDocument;
    private XPath mockedXPath;
    private XPathExpression mockedXPathExpression;
    private NodeList mockedNodeList;
    private XPathDetails xPathDetails;

    @BeforeClass
    public static void setUpClass() {
        mockedDocumentNamespaces = mock(DocumentNamespaces.class);

    }

    @Before
    public void setUp() {
        mockedXPathFactory = mock(XPathFactory.class);
        PowerMockito.mockStatic(XPathFactory.class);
        when(XPathFactory.newInstance()).thenReturn(mockedXPathFactory);
        mockedXPath = mock(XPath.class);
        when(mockedXPathFactory.newXPath()).thenReturn(mockedXPath);
        mockedXPathExpression = mock(XPathExpression.class);
        mockedNodeList = mock(NodeList.class);
        xPathDetails = new XPathDetails();
        mockedDocument = mock(Document.class);
    }

    @Test
    public void getResult_returns_expected_evaluation() throws XPathExpressionException, XMLUtilsException {
        xPathDetails.setDocument(mockedDocument);
        xPathDetails.setXpathExpression(SAMPLE_EXPRESSION_01);
        xPathDetails.setNamespaceAware(false);
        when(mockedXPath.compile(SAMPLE_EXPRESSION_01)).thenReturn(mockedXPathExpression);
        when(mockedXPathExpression.evaluate(mockedDocument, XPathConstants.NODESET)).thenReturn(mockedNodeList);

        NodeList result = XPathTester.getInstance().getResult(xPathDetails);

        assertThat(result, equalTo(mockedNodeList));
    }

    @Test(expected = XMLUtilsException.class)
    public void getResult_throws_expected_exception() throws XPathExpressionException, XMLUtilsException {
        xPathDetails.setDocument(mockedDocument);
        xPathDetails.setXpathExpression(SAMPLE_EXPRESSION_01);
        xPathDetails.setNamespaceAware(false);
        when(mockedXPath.compile(SAMPLE_EXPRESSION_01)).thenThrow(XPathExpressionException.class);
        when(mockedXPathExpression.evaluate(mockedDocument, XPathConstants.NODESET)).thenReturn(mockedNodeList);

        NodeList result = XPathTester.getInstance().getResult(xPathDetails);

    }

    @Test
    public void getResult_uses_corrected_expression_when_namespace_aware_and_default_namespace() throws XPathExpressionException, XMLUtilsException {
        Map<String, String> map = new HashMap<>();
        map.put(NamespaceResolver.NAMESPACE_PREFIX, "default");
        map.put(NamespaceResolver.NAMESPACE_PREFIX + NamespaceResolver.NAMESPACE_SEPARATOR + "meh", "another");
        PowerMockito.mockStatic(DocumentNamespaces.class);
        when(DocumentNamespaces.getInstance()).thenReturn(mockedDocumentNamespaces);
        when(mockedDocumentNamespaces.getDocumentNamespaces(mockedDocument)).thenReturn(map);
        xPathDetails.setDocument(mockedDocument);
        xPathDetails.setXpathExpression(SAMPLE_EXPRESSION_02);
        xPathDetails.setNamespaceAware(true);
        xPathDetails.setFakeDefaultNamespacePrefix(FAKE_DEFAULT_PREFIX);
        when(mockedXPath.compile(SAMPLE_EXPRESSION_02_FAKED)).thenReturn(mockedXPathExpression);
        when(mockedXPathExpression.evaluate(mockedDocument, XPathConstants.NODESET)).thenReturn(mockedNodeList);

        NodeList result = XPathTester.getInstance().getResult(xPathDetails);

        assertThat(result, equalTo(mockedNodeList));
    }
    
    @Test
    public void getResult_uses_corrected_expression_when_namespace_aware_without_default_namespace() throws XPathExpressionException, XMLUtilsException {
        Map<String, String> map = new HashMap<>();
        map.put(NamespaceResolver.NAMESPACE_PREFIX + NamespaceResolver.NAMESPACE_SEPARATOR + FAKE_DEFAULT_PREFIX, "default");
        map.put(NamespaceResolver.NAMESPACE_PREFIX + NamespaceResolver.NAMESPACE_SEPARATOR + "meh", "another");
        PowerMockito.mockStatic(DocumentNamespaces.class);
        when(DocumentNamespaces.getInstance()).thenReturn(mockedDocumentNamespaces);
        when(mockedDocumentNamespaces.getDocumentNamespaces(mockedDocument)).thenReturn(map);
        xPathDetails.setDocument(mockedDocument);
        xPathDetails.setXpathExpression(SAMPLE_EXPRESSION_02_FAKED);
        xPathDetails.setNamespaceAware(true);
        xPathDetails.setFakeDefaultNamespacePrefix(FAKE_DEFAULT_PREFIX);
        when(mockedXPath.compile(SAMPLE_EXPRESSION_02_FAKED)).thenReturn(mockedXPathExpression);
        when(mockedXPathExpression.evaluate(mockedDocument, XPathConstants.NODESET)).thenReturn(mockedNodeList);

        NodeList result = XPathTester.getInstance().getResult(xPathDetails);

        assertThat(result, equalTo(mockedNodeList));
    }    
}
