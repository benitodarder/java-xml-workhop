package local.tin.tests.xml.utils;

import java.io.StringWriter;
import java.io.Writer;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class PrettyPrint {
    
    public static final String WELL_FORMED_XML = "WELL FORMED XML";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PrettyPrint.class);
    private static SAXParserFactory saxParserFactory;
    private static DocumentBuilderFactory documentBuilderFactory;
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
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public String getDocumentPrettyPrinted(Document xml) throws TransformerException {
        Transformer transformer = getTransformerFactory().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
        Writer out = new StringWriter();
        transformer.transform(new DOMSource(xml), new StreamResult(out));
        return out.toString();
    }  
    
     private TransformerFactory getTransformerFactory() throws TransformerConfigurationException {
        synchronized (this) {
            if (transformerFactory == null) {
                transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            }
        }
        return transformerFactory;
    }   
 }
