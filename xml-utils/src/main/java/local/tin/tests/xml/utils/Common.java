package local.tin.tests.xml.utils;

import org.w3c.dom.Node;

/**
 *
 * @author benitodarder
 */
public class Common {

    public static final String ATTRIBUTE_XMLNS = "xmlns";

    private Common() {
    }

    public static Common getInstance() {
        return CommonHolder.INSTANCE;
    }

    private static class CommonHolder {

        private static final Common INSTANCE = new Common();
    }

    public boolean isNamespaceAttribute(Node node) {
        return node.getNodeType() == Node.ATTRIBUTE_NODE && node.getNodeName().startsWith(ATTRIBUTE_XMLNS);
    }
}
