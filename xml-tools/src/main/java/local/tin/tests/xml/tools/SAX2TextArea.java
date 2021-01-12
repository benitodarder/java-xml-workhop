package local.tin.tests.xml.tools;

/*
 * sax2TextArea.java
 *
 * Created on October 6, 2011, 11:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author benitodarder
 */
class SAX2TextArea {
    
    /** Creates a new instance of sax2TextArea */
    public SAX2TextArea(File f, JTextArea text) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        JTextAreaHandler handler = new JTextAreaHandler(text);
        saxParser.parse(f, handler);
    }
    
}

class JTextAreaHandler extends DefaultHandler {

  /** Current JTextArea */
  private JTextArea text;

  /** Store URI to prefix mappings */
  private Map namespaceMappings;
  
  private int indent = 0;
  private boolean chars = false;
  private boolean inici = true;


  private String indentation(int i) {
      String s = "";
      for(int j=0; j < i; j++) {
          s = s + "    ";
      }
      return s;
  }
  
  public JTextAreaHandler(JTextArea text) {
    this.text = text;
    this.namespaceMappings = new HashMap();
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
    String s = new String(ch, start, length);
    if (s.trim().length() > 0) {
        text.append(s);
        chars = true;
    }
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    indent--;      
    if (!chars) {
        text.append("\n" + indentation(indent) + "</" + qName + ">");
    } else {
        text.append("</" + qName + ">");
        chars = false;
    }
  }

  public void endPrefixMapping(String prefix) throws SAXException {
    for (Iterator i = namespaceMappings.keySet().iterator(); i.hasNext();) {
      String uri = (String) i.next();
      String thisPrefix = (String) namespaceMappings.get(uri);
      if (prefix.equals(thisPrefix)) {
        namespaceMappings.remove(uri);
        break;
      }
    }

  }

//  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
//  }

//  public void processingInstruction(String target, String data) throws SAXException {
//  }

//  public void skippedEntity(String name) throws SAXException {
//  }

  public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    if (inici) {
        text.append(indentation(indent) + "<" + qName);
        inici = false;
    } else {
        text.append("\n" + indentation(indent) + "<" + qName);
    }
    // Determine namespace
    if (namespaceURI.length() > 0) {
      String prefix = (String) namespaceMappings.get(namespaceURI);
      if (prefix.equals("")) {
        prefix = "[None]";
      }
      text.append(prefix + "=\"" + namespaceURI + "\" ");
    }

    // Process attributes
    for (int i = 0; i < atts.getLength(); i++) {
        text.append(atts.getLocalName(i) + "=\"" + atts.getValue(i) + "\" ");
      String attURI = atts.getURI(i);
      if (attURI.length() > 0) {
        String attPrefix = (String) namespaceMappings.get(attURI);
        if (attPrefix.equals("")) {
          attPrefix = "[None]";
        }
        text.append(attPrefix + "=\"" + attURI + "\" ");        
      }
    }
    text.append(">");
    indent++;
  }

  public void startPrefixMapping(String prefix, String uri)
      throws SAXException {
    namespaceMappings.put(uri, prefix);

  }
}
