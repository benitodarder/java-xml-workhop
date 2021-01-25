package local.tin.tests.xml.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import local.tin.tests.xml.utils.errors.ErrorHandlerStringList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/**
 *
 * @author benitodarder
 */
public class Validation {
    
   public static final String WELL_FORMED_XML = "WELL FORMED XML";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Validation.class);
    private static SAXParserFactory saxParserFactory;
 

    private Validation() {
    }

    public static Validation getInstance() {
        return ValidationHolder.INSTANCE;
    }

    private static class ValidationHolder {
        private static final Validation INSTANCE = new Validation();
    }
    
    
    /**
     * Parses document to check if it is well formed, and returns an error list
     * with all errors found, or an empty list when no errors found.
     * 
     * Exception is logged as INFO.
     *
     * @param xmlString String
     * @return List of String
     */
    public List<String>  getParsingErrorsList(String xmlString) {
        List<String> result = new ArrayList<>();
        try {
            SAXParser parser = getSAXParserFactory().newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new ErrorHandlerStringList(result));
            reader.parse(new InputSource(new StringReader(xmlString)));
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            LOGGER.info(ex.getMessage());
        }
        return result;
    }

    private SAXParserFactory getSAXParserFactory() throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
        synchronized (this) {
            if (saxParserFactory == null) {
                saxParserFactory = SAXParserFactory.newInstance();
                saxParserFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                saxParserFactory.setValidating(false);
                saxParserFactory.setNamespaceAware(true);
            }
        }
        return saxParserFactory;
    }    
 }
