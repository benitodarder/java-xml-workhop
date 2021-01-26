package local.tin.tests.xml.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class TestUtils {

    public static final String XML_01_NODE_VALUE = "      NodeA value 01    ";
    public static final String SAMPLE_XML_01 = "<root a=\"b\">" + System.lineSeparator()
            + "   <nodeA att01=\"att01 value\" att02=\"att02 value\">"
            + XML_01_NODE_VALUE
            + "</nodeA>"
            + "</root>";

    private TestUtils() {
    }

    public static TestUtils getInstance() {
        return TraverseTestUtilsHolder.INSTANCE;
    }

    private static class TraverseTestUtilsHolder {

        private static final TestUtils INSTANCE = new TestUtils();
    }
    

    public Document getDocumentFromString(String string, boolean namespaceAware) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setIgnoringElementContentWhitespace(namespaceAware);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        docBuilder.isValidating();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(string));
        return docBuilder.parse(is);
    }
    
    /**
     * Returns a String containing the content of the file from resources
     * folder.
     *
     * @param klass Class to state the package
     * @param fileName String
     * @return String
     * @throws java.io.IOException
     */
    public String getFileAsString(Class klass, String fileName) throws IOException {
        InputStreamReader fileInputStream = new InputStreamReader(klass.getResourceAsStream(fileName));
        BufferedReader bufferedReader = new BufferedReader(fileInputStream);
        StringBuilder stringBuilder = new StringBuilder();
        String string = bufferedReader.readLine();
        while (string != null) {
            stringBuilder.append(string);
            string = bufferedReader.readLine();
            if (string != null) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }       
}
