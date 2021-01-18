package local.tin.tests.xml.tools;


import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.awt.Color;

public class XML2Tree {
    
    /** Creates a new instance of xml2Tree */
    public XML2Tree(File f, DefaultMutableTreeNode padre) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            docBuilder.isValidating();
            Document doc = docBuilder.parse(f);

            Node node01 = doc.getFirstChild();
            traverse(doc.getChildNodes(), padre);

        } catch (Exception e) {
            System.out.println("Excepcion Excepcion!!:" + e.getMessage());
        }       
    }
    
    /** Creates a new instance of xml2Tree */
    public XML2Tree(Document doc, DefaultMutableTreeNode padre) {
        try {
            traverse(doc.getChildNodes(), padre);
        } catch (Exception e) {
            System.out.println("Excepcion Excepcion!!:" + e.getMessage());
        }       
    }
    
    
    private static void traverse(NodeList rootNode,DefaultMutableTreeNode padre){
        for(int index = 0; index < rootNode.getLength(); index ++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE){
                NodeList childNodes = aNode.getChildNodes();            
                if (childNodes.getLength() > 0){
                    if (childNodes.getLength() > 1) {
                        DefaultMutableTreeNode elemento = new DefaultMutableTreeNode(aNode.getNodeName());                        
                        NamedNodeMap attributs = aNode.getAttributes();
                        DefaultMutableTreeNode attributsArbre = new DefaultMutableTreeNode("Atributs:");                        
                        if (attributs.getLength() > 0) {
                            for(int i = 0; i < attributs.getLength(); i++) {
                                attributsArbre.add(new DefaultMutableTreeNode(attributs.item(i).getNodeName() + ":" + attributs.item(i).getNodeValue()));
                            }
                        }
                        elemento.add(attributsArbre);
                        padre.add(elemento);
                        traverse(childNodes, elemento);
                    } else {
                        DefaultMutableTreeNode info = new DefaultMutableTreeNode(childNodes.item(0).getNodeValue());
                        DefaultMutableTreeNode elemento = new DefaultMutableTreeNode(aNode.getNodeName());
                        NamedNodeMap attributs = aNode.getAttributes();
                        DefaultMutableTreeNode attributsArbre = new DefaultMutableTreeNode("Atributs:");                        
                        if (attributs.getLength() > 0) {
                            for(int i = 0; i < attributs.getLength(); i++) {
                                attributsArbre.add(new DefaultMutableTreeNode(attributs.item(i).getNodeName() + ":" + attributs.item(i).getNodeValue()));
                            }
                        }
                        elemento.add(attributsArbre);                        
                        elemento.add(info);
                        padre.add(elemento);
                        traverse(childNodes, padre);                                        
                   }
                }
            }
        }        
    }    
    
}

