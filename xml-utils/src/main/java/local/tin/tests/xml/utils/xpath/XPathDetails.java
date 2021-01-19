package local.tin.tests.xml.utils.xpath;

import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class XPathDetails {

    private Document document;
    private String xpathExpression;
    private boolean namespaceAware;
    private String fakeDefaultNamespacePrefix;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getXpathExpression() {
        return xpathExpression;
    }

    public void setXpathExpression(String xpathExpression) {
        this.xpathExpression = xpathExpression;
    }

    public boolean isNamespaceAware() {
        return namespaceAware;
    }

    public void setNamespaceAware(boolean namespaceAware) {
        this.namespaceAware = namespaceAware;
    }

    public String getFakeDefaultNamespacePrefix() {
        return fakeDefaultNamespacePrefix;
    }

    public void setFakeDefaultNamespacePrefix(String fakeDefaultNamespacePrefix) {
        this.fakeDefaultNamespacePrefix = fakeDefaultNamespacePrefix;
    }
    
    
}
