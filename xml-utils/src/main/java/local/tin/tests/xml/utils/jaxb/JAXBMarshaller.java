package local.tin.tests.xml.utils.jaxb;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author benito.darder
 */
public class JAXBMarshaller {

    public static final String JAXB_XML_ENCODING = "UTF-8";
    private static volatile Map<Object, JAXBContext> jaxbContexts;
    private static DocumentBuilderFactory dbFactory;

    private JAXBMarshaller() {
    }

    public synchronized static JAXBMarshaller getInstance() throws ParserConfigurationException {
        Map<Object, JAXBContext> temp = jaxbContexts;
        if (temp == null) {
            dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            jaxbContexts = new HashMap<>();
        }
        return JAXBMarshallerHolder.INSTANCE;
    }

    private static class JAXBMarshallerHolder {

        private static final JAXBMarshaller INSTANCE = new JAXBMarshaller();
    }

    public String toString(Object pObject) throws Exception {
        try {
            Class klass = pObject.getClass();
            if (!jaxbContexts.containsKey(klass)) {
                JAXBContext jAXBContext = JAXBContext.newInstance(klass);
                jaxbContexts.put(klass, jAXBContext);
            }
            JAXBContext jAXBContext = jaxbContexts.get(klass);
            java.io.StringWriter sw = new StringWriter();
            Marshaller marshaller = jAXBContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, JAXB_XML_ENCODING);
            marshaller.marshal(new JAXBElement(new QName(klass.getSimpleName()), klass, pObject), sw);
            return sw.toString();
        } catch (JAXBException ex) {
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    public Object toObject(String xml, Class klass) throws Exception {
        try {
            if (!jaxbContexts.containsKey(klass)) {
                JAXBContext jAXBContext = JAXBContext.newInstance(klass);
                jaxbContexts.put(klass, jAXBContext);
            }
            JAXBContext jAXBContext = jaxbContexts.get(klass);

            Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
            Document doc = getDocumentFromXMLString(xml);
            DOMSource ds = new DOMSource(doc);

            JAXBElement jAXBElement = unmarshaller.unmarshal(ds, klass);
            return jAXBElement.getValue();
        } catch (JAXBException | SAXException | IOException | ParserConfigurationException ex) {
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    private Document getDocumentFromXMLString(String xml) throws SAXException, IOException, ParserConfigurationException, org.xml.sax.SAXException {
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        Document doc = dBuilder.parse(is);
        return doc;
    }

    public Object toObject(String xml, String className) throws Exception {
        Class klass;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
        return toObject(xml, klass);
    }
}