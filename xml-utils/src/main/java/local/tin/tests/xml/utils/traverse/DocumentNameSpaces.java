package local.tin.tests.xml.utils.traverse;

import java.util.HashMap;
import java.util.Map;
import static local.tin.tests.xml.utils.traverse.CustomTextView.ATTRIBUTES;
import static local.tin.tests.xml.utils.traverse.CustomTextView.ATTRIBUTE_SEPARATOR;
import static local.tin.tests.xml.utils.traverse.CustomTextView.ELEMENT_CONTENT;
import static local.tin.tests.xml.utils.traverse.CustomTextView.ELEMENT_NAME;
import static local.tin.tests.xml.utils.traverse.CustomTextView.INDENT_CHARACTER;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class DocumentNameSpaces {

    private DocumentNameSpaces() {
    }

    public static DocumentNameSpaces getInstance() {
        return DocumentNameSpacesHolder.INSTANCE;
    }

    private static class DocumentNameSpacesHolder {

        private static final DocumentNameSpaces INSTANCE = new DocumentNameSpaces();
    }

    /**
     * Returns a map with all namespaces from the document.
     *
     * Map keys include xmlns: prefix to easily process default namespace.
     * 
     * @param document as Document
     * @return Map of String/String
     */
    public Map<String, String> getDocumentNamespaces(Document document) {
        Map<String, String> namespaces = new HashMap<>();
        NamedNodeMap attributs = document.getDocumentElement().getAttributes();
        int attributsLengh = attributs.getLength();
        for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
            if (attributs.item(attributsIndex).getNodeType() == Node.ATTRIBUTE_NODE && attributs.item(attributsIndex).getNodeName().startsWith("xmlns")) {
                namespaces.put(attributs.item(attributsIndex).getNodeName(), attributs.item(attributsIndex).getNodeValue());
            }
        }
        traverse(document.getFirstChild().getChildNodes(), namespaces);
        return namespaces;
    }

    private void traverse(NodeList rootNode, Map<String, String> namespaces) {
        for (int index = 0; index < rootNode.getLength(); index++) {
            Node aNode = rootNode.item(index);
            NamedNodeMap attributs = aNode.getAttributes();
            if (attributs != null) {
                int attributsLengh = attributs.getLength();
                for (int attributsIndex = 0; attributsIndex < attributsLengh; attributsIndex++) {
                    if (attributs.item(attributsIndex).getNodeType() == Node.ATTRIBUTE_NODE && attributs.item(attributsIndex).getNodeName().startsWith("xmlns")) {
                        namespaces.put(attributs.item(attributsIndex).getNodeName(), attributs.item(attributsIndex).getNodeValue());
                    }
                }
            }
            traverse(aNode.getChildNodes(), namespaces);
        }
    }
}
