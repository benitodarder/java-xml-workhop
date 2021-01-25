package local.tin.tests.xml.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author benitodarder
 */
public class Builder {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Builder.class);
    private static DocumentBuilderFactory documentBuilderFactory;

    private Builder() {
    }

    public static Builder getInstance() {
        return DocumentBuildersHolder.INSTANCE;
    }

    private static class DocumentBuildersHolder {

        private static final Builder INSTANCE = new Builder();
    }

    /**
     * Returns a org.w3d.dom Document parsed from the given string.
     *
     * @param string String
     * @return org.w3d.dom.Document
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public Document getDocumentFromString(String string) throws XMLUtilsException {
        try {
            DocumentBuilder docBuilder = getDocumentBuilderFactory().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(string));
            return docBuilder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new XMLUtilsException(ex);
        }
    }

    /**
     * Returns a org.w3d.dom Document containing the XML document included in
     * the file pointed by filePath
     *
     * @param filePath String
     * @return org.w3d.dom.Document
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public Document getDocumentFromFile(String filePath) throws XMLUtilsException {
        try {
            DocumentBuilder db = getDocumentBuilderFactory().newDocumentBuilder();
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            return db.parse(fileInputStream);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new XMLUtilsException(ex);
        }
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() throws ParserConfigurationException {
        synchronized (this) {
            if (documentBuilderFactory == null) {
                documentBuilderFactory = DocumentBuilderFactory.newInstance();
                documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                documentBuilderFactory.setValidating(false);
            }
        }
        return documentBuilderFactory;
    }
}
