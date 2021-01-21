package local.tin.tests.xml.utils.text;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class CustomTextView {

    public static final String ATTRIBUTE_SEPARATOR = "=";
    public static final String FIRST_INDENT = "";
    public static final String INDENT_CHARACTER = " ";
    public static final String ELEMENT_CONTENT = "Element content: ";
    public static final String ATTRIBUTES = "Attributes:";
    public static final String ELEMENT_NAME = "Element: ";
    public static final String ROOT_ELEMENT = "Root: ";

    private CustomTextView() {
    }

    public static CustomTextView getInstance() {
        return TraverseHolder.INSTANCE;
    }

    private static class TraverseHolder {

        private static final CustomTextView INSTANCE = new CustomTextView();
    }

    /**
     * Runs through the NodeList and returns the XML's custom view:
     *
     * <ul>
     * <li>Node:
     * <ul>
     * <li>Attributes:
     * <ul>
     * <li>List of attribute name ATTRIBUTE_SEPARATOR attribute value</li>
     * </ul>
     * </li>
     * <li>List of node names:
     * <ul>
     * <li>List of this structure from Node:</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param nodes
     * @return String
     */
    public String getCustomXMLView(NodeList nodes) {
        StringBuilder stringBuilder = new StringBuilder();
        traverse(nodes, stringBuilder, FIRST_INDENT);
        return stringBuilder.toString();
    }

    /**
     * Runs the Document and returns the XML's custom view:
     *
     * <ul>
     * <li>Root node name</li>
     * <li>Attributes:
     * <ul>
     * <li>List of attribute name ATTRIBUTE_SEPARATOR attribute value</li>
     * </ul>
     * </li>
     * <li>A list of node names:
     * <ul>
     * <li>Attributes:
     * <ul>
     * <li>List of attribute name ATTRIBUTE_SEPARATOR attribute value</li>
     * </ul>
     * </li>
     * <li>List of node names:
     * <ul>
     * <li>List of this structure from Node:</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param document as Document
     * @return String
     */
    public String getCustomXMLView(Document document) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ROOT_ELEMENT).append(document.getDocumentElement().getNodeName()).append(System.lineSeparator());
        stringBuilder.append(ATTRIBUTES).append(System.lineSeparator());
        NamedNodeMap attributs = document.getDocumentElement().getAttributes();
        for (int i = 0; i < attributs.getLength(); i++) {
            stringBuilder.append(INDENT_CHARACTER).append(attributs.item(i).getNodeName()).append(ATTRIBUTE_SEPARATOR).append(attributs.item(i).getNodeValue()).append(System.lineSeparator());
        }
        stringBuilder.append(getCustomXMLView(document.getFirstChild().getChildNodes()));
        return stringBuilder.toString();
    }

    private void traverse(NodeList rootNode, StringBuilder area, String indentation) {
        StringBuilder stringBuilder = new StringBuilder();
        String nextIndentation = stringBuilder.append(indentation).append(INDENT_CHARACTER).toString();
        String nextNextIndentation = stringBuilder.append(INDENT_CHARACTER).toString();
        for (int index = 0; index < rootNode.getLength(); index++) {
            Node aNode = rootNode.item(index);
            if (aNode.getNodeType() == Node.ELEMENT_NODE) {
                NodeList childNodes = aNode.getChildNodes();
                if (childNodes.getLength() > 0) {
                    if (childNodes.getLength() > 1) {
                        area.append(indentation).append(ELEMENT_NAME).append(aNode.getNodeName()).append(System.lineSeparator());
                        area.append(nextIndentation).append(ATTRIBUTES).append(System.lineSeparator());
                        NamedNodeMap attributs = aNode.getAttributes();
                        for (int i = 0; i < attributs.getLength(); i++) {
                            area.append(nextNextIndentation).append(attributs.item(i).getNodeName()).append(ATTRIBUTE_SEPARATOR).append(attributs.item(i).getNodeValue()).append(System.lineSeparator());
                        }
                    } else {
                        area.append(indentation).append(ELEMENT_NAME).append(aNode.getNodeName()).append(System.lineSeparator());
                        area.append(nextIndentation).append(ATTRIBUTES).append(System.lineSeparator());
                        NamedNodeMap attributs = aNode.getAttributes();
                        for (int i = 0; i < attributs.getLength(); i++) {
                            area.append(nextNextIndentation).append(attributs.item(i).getNodeName()).append(ATTRIBUTE_SEPARATOR).append(attributs.item(i).getNodeValue()).append(System.lineSeparator());
                        }
                        area.append(nextIndentation).append(ELEMENT_CONTENT);
                        area.append(childNodes.item(0).getNodeValue()).append(System.lineSeparator());
                    }
                }
                traverse(childNodes, area, nextIndentation);
            }
        }
    }

}
