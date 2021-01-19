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

    private static final String ONE_NODE_NO_ATTRIBUTS = "<root>\n"
            + "  <nodeA>Node A value</nodeA>\n"
            + "</root>";
    private static final String ONE_SUBNODE_NO_ATTRIBUTES = "<root>\n"
            + "    <nodeA>\n"
            + "        <nodeAA>\n"
            + "            Node A value\n"
            + "        </nodeAA>\n"
            + "    </nodeA>\n"
            + "</root>";
    private static final String TWO_SUBNODES_NO_ATTRIBUTES = "<root>\n"
            + "    <nodeA>\n"
            + "        <nodeAA>\n"
            + "            Node A value\n"
            + "        </nodeAA>\n"
            + "        <nodeAA>\n"
            + "            Node A value\n"
            + "        </nodeAA>        \n"
            + "    </nodeA>\n"
            + "</root>";
    private static final String TWO_SUBNODES_ONE_ATTRIBUTE = "<root>\n"
            + "    <nodeA>\n"
            + "        <nodeAA a=\"b\">\n"
            + "            Node A value\n"
            + "        </nodeAA>\n"
            + "        <nodeAA a=\"c\">\n"
            + "            Node A value\n"
            + "        </nodeAA>        \n"
            + "    </nodeA>\n"
            + "</root>";
    private static final String NODES_SUBNODES_ATTRIBUTES = "<root>\n"
            + "    <nodeA>\n"
            + "        <nodeAA a=\"b\" b=\"3\">\n"
            + "            Node A value\n"
            + "        </nodeAA>\n"
            + "        <nodeAA a=\"c\" >\n"
            + "            Node A value\n"
            + "        </nodeAA>        \n"
            + "    </nodeA>\n"
            + "    <nodeB c=\"2021-01-20T00:00:00.000Z\">meh</nodeB>\n"
            + "</root>";

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_one_node_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(ONE_NODE_NO_ATTRIBUTS);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document);

        assertThat(result.size(), equalTo(1));
        assertThat(result.contains("root/nodeA"), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_one_subnodes_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(ONE_SUBNODE_NO_ATTRIBUTES);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document);

        assertThat(result.size(), equalTo(2));
        assertThat(result.contains("root/nodeA"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA"), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_two_subnodes_no_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(ONE_SUBNODE_NO_ATTRIBUTES);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document);

        assertThat(result.size(), equalTo(2));
        assertThat(result.contains("root/nodeA"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA"), equalTo(true));
    }

    @Test
    public void getDocumentXPaths_returns_expected_paths_for_two_subnodes_one_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(TWO_SUBNODES_ONE_ATTRIBUTE);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document);

        assertThat(result.size(), equalTo(3));
        assertThat(result.contains("root/nodeA"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a='b']"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a='c']"), equalTo(true));
    }
    
    @Test
    public void getDocumentXPaths_returns_expected_paths_nodes_subnodes_attributes() throws ParserConfigurationException, SAXException, IOException {
        Document document = TestUtils.getInstance().getDocumentFromString(NODES_SUBNODES_ATTRIBUTES);

        Set<String> result = XPathGenerator.getInstance().getDocumentXPaths(document);

        assertThat(result.size(), equalTo(4));
        assertThat(result.contains("root/nodeA"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a='b' and @b='3']"), equalTo(true));
        assertThat(result.contains("root/nodeA/nodeAA[@a='c']"), equalTo(true));
        assertThat(result.contains("root/nodeB[@c='2021-01-20T00:00:00.000Z']"), equalTo(true));
    }    
}
