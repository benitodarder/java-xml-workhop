package local.tin.tests.xml.utils.namespaces;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import local.tin.tests.xml.utils.Common;

/**
 * Allows XPath queries with default namespace.
 * 
 * Namespaces can be loaded from the DocumentNamespaces getDocumentNamespaces
 * @author benitodarder
 */
public class NamespaceResolver implements NamespaceContext {
    
    private static final String DEFAULT_NAMESPACE_PREFIX = "dnp";    
    private final String fakeDefaultPrefix;
    
    private final Map<String, String> namespaces;

    public NamespaceResolver(Map<String, String> namespaces) {
        this(DEFAULT_NAMESPACE_PREFIX, namespaces);
    }

    public NamespaceResolver(String fakeDefaultPrefix, Map<String, String> namespaces) {
        this.fakeDefaultPrefix = fakeDefaultPrefix;
        this.namespaces = namespaces;
    }
    
    

    @Override
    public String getNamespaceURI(String string) {
        if (getFakeDefaultPrefix().equals(string)) {
            return namespaces.get(Common.ATTRIBUTE_XMLNS);
        }
        return namespaces.get(Common.ATTRIBUTE_XMLNS + Common.NAMESPACE_PREFIX_SEPARATOR + string);
    }

    @Override
    public String getPrefix(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator getPrefixes(String string) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    public String getFakeDefaultPrefix() {
        return fakeDefaultPrefix;
    }

    
}
