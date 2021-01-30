package local.tin.tests.xml.utils.xpath;

import java.io.IOException;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import local.tin.tests.xml.utils.TestUtils;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class XPathGeneratorTest {

    private static final String ONE_NODE_NO_ATTRIBUTS = "oneNodeNoAttributes.xml";
    private static final String ONE_SUBNODE_NO_ATTRIBUTES = "oneSubnodeNoAttributes.xml";
    private static final String TWO_SUBNODES_ONE_ATTRIBUTE = "twoSubnodesOneAttribute.xml";
    private static final String NODES_SUBNODES_ATTRIBUTES = "nodeSubnodesAttributes.xml";
    private static final String XPATH_NODEA = "root/nodeA";
    private static final String XPATH_NODEA_NODEAA = "root/nodeA/nodeAA";
    private static final String DEFAULT_NAMESPACE_XML = "defaultNamespace.xml";

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_one_node_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, ONE_NODE_NO_ATTRIBUTS), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, false);

        assertThat(result.size(), equalTo(1));
        assertThat(result.contains(XPATH_NODEA), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_one_subnodes_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, ONE_SUBNODE_NO_ATTRIBUTES), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, false);

        assertThat(result.size(), equalTo(2));
        assertThat(result.contains(XPATH_NODEA), equalTo(true));
        assertThat(result.contains(XPATH_NODEA_NODEAA), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_two_subnodes_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, ONE_SUBNODE_NO_ATTRIBUTES), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, false);

        assertThat(result.size(), equalTo(2));
        assertThat(result.contains(XPATH_NODEA), equalTo(true));
        assertThat(result.contains(XPATH_NODEA_NODEAA), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_two_subnodes_one_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, TWO_SUBNODES_ONE_ATTRIBUTE), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, false);

        assertThat(result.size(), equalTo(3));
        assertThat(result.contains(XPATH_NODEA), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a = 'b']"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a = 'c']"), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_nodes_subnodes_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, NODES_SUBNODES_ATTRIBUTES), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, false);

        assertThat(result.size(), equalTo(4));
        assertThat(result.contains(XPATH_NODEA), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a = 'b' and @b = '3']"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a = 'c']"), equalTo(true));
        assertThat(result.contains("root/nodeB[@c = '2021-01-20T00:00:00.000Z']"), equalTo(true));
    }

    
    @Test
    public void getDocumentXPaths_includes_local_name_when_told() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TestUtils.getInstance().getFileAsString(XPathGenerator.class, DEFAULT_NAMESPACE_XML), true);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document, true);

        assertThat(result.size(), equalTo(3));
        assertThat(result.contains("*[local-name() = 'root']/*[local-name() = 'nodeA']"), equalTo(true));
        assertThat(result.contains("*[local-name() = 'root']/*[local-name() = 'nodeA']/*[local-name() = 'nodeAA'][@a = 'b' and @c = 'd']/*[local-name() = 'nodeAAA'][@a = '1']"), equalTo(true));
        assertThat(result.contains("*[local-name() = 'root']/*[local-name() = 'nodeA']/*[local-name() = 'nodeAA'][@a = 'b' and @c = 'd']"), equalTo(true));
    }    

}
