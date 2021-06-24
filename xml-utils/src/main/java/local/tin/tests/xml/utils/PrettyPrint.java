package local.tin.tests.xml.utils;

import java.io.StringWriter;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class PrettyPrint {

    public static final String WELL_FORMED_XML = "WELL FORMED XML";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PrettyPrint.class);
    private static TransformerFactory transformerFactory;

    private PrettyPrint() {
    }

    public static PrettyPrint getInstance() {
        return PrettyPrintHolder.INSTANCE;
    }

    private static class PrettyPrintHolder {

        private static final PrettyPrint INSTANCE = new PrettyPrint();
    }

    /**
     * Returns a pretty printed String containing the org.w3d.dom.Document
     * specified as parameter.
     *
     * @param xml org.w3d.dom.Document
     * @return String
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public String getDocumentPrettyPrinted(Document xml) throws XMLUtilsException {
        try {
            Transformer transformer = getTransformerFactory().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            Writer out = new StringWriter();
            transformer.transform(new DOMSource(xml), new StreamResult(out));
            return out.toString();
        } catch (TransformerException ex) {
            LOGGER.debug(ex);
            throw new XMLUtilsException(ex);
        }
    }

    private TransformerFactory getTransformerFactory() throws XMLUtilsException {
        synchronized (this) {
            if (transformerFactory == null) {
                try {
                    transformerFactory = TransformerFactory.newInstance();
                    transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                } catch (TransformerConfigurationException ex) {
                    LOGGER.debug(ex);
                    throw new XMLUtilsException(ex);
                }
            }
        }
        return transformerFactory;
    }
}
