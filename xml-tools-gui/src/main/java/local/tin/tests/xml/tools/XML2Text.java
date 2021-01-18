package local.tin.tests.xml.tools;

import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import javax.swing.JTextArea;
import java.io.File;
import java.awt.Color;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author  benitodarder
 */
public class XML2Text {
    
    /** Creates a new instance of xml2Tree
     * @param nodes
     * @param area */
    public XML2Text(NodeList nodes, JTextArea area) {
        try {
            traverse(nodes, area, "-");
        } catch (Exception e) {
            System.out.println("Excepcion Excepcion!!:" + e.getMessage());
        }       
    }
    
    /** Creates a new instance of xml2Text
     * @param f
     * @param area */
    public XML2Text(File f, JTextArea area) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            docBuilder.isValidating();
            Document doc = docBuilder.parse(f);

            Node node01 = doc.getFirstChild();
            traverse(doc.getChildNodes(), area, "");

        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Excepcion Excepcion!!:" + e.getMessage());
        }       
    }
    
    private void traverse(NodeList rootNode, JTextArea area, String s){
        for(int index = 0; index < rootNode.getLength(); index ++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE){
                NodeList childNodes = aNode.getChildNodes();            
                if (childNodes.getLength() > 0){
                    if (childNodes.getLength() > 1) {
                        area.setForeground(Color.BLUE);
                        area.append("_Element:" + s + ">");
                        NamedNodeMap attributs = aNode.getAttributes();
                        for(int i = 0; i < attributs.getLength(); i++) {
                            area.append(":_Attribut:(" + attributs.item(i).getNodeName() + ":" + attributs.item(i).getNodeValue()+ "):");
                        }                        
                        area.setForeground(Color.BLACK);
                        area.append(aNode.getNodeName() + System.getProperty("line.separator"));
                    } else {
                        area.setForeground(Color.BLUE);
                        area.append("_Element:" + s + ">");
                        area.setForeground(Color.BLACK);                        
                        area.append(aNode.getNodeName());
                        NamedNodeMap attributs = aNode.getAttributes();
                        for(int i = 0; i < attributs.getLength(); i++) {
                            area.append(":_Attribut:(" + attributs.item(i).getNodeName() + ":" + attributs.item(i).getNodeValue() + "):");
                        }
                        area.setForeground(Color.BLUE);                        
                        area.append(":_Valor :");
                        area.setForeground(Color.BLACK);                                                
                        area.append(childNodes.item(0).getNodeValue() + System.getProperty("line.separator"));
                    }
                }
                traverse(childNodes, area, s + "-");                
            }
        }        
    }    
    
}




