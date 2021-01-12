package local.tin.tests.xml.dtd.validator;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new SimpleErrorHandler(LOGGER));
            org.w3c.dom.Document xmlDocument = builder.parse(new FileInputStream(args[0]));
            DOMSource source = new DOMSource(xmlDocument);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(outputStream);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, args[1]);
            transformer.transform(source, result);
            LOGGER.info(outputStream.toString("UTF-8"));
        } catch (IOException | IllegalArgumentException | ParserConfigurationException | TransformerException | SAXException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
