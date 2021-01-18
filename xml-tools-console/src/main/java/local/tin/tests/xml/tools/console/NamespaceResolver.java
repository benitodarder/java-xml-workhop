package local.tin.tests.xml.tools.console;

import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

/**
 * Allows XPath queries with default namespace.
 * 
 * Namespaces can be loaded from the DocumentNamespaces getDocumentNamespaces
 * @author benitodarder
 */
public class NamespaceResolver implements NamespaceContext {
    
    public static final String DEFAULT_NAMESPACE_PREFIX = "dnp";
    public static final String NAMESPACE_SEPARATOR = ":";
    public static final String NAMESPACE_PREFIX = "xmlns";
    
    private final Map<String, String> namespaces;

    public NamespaceResolver(Map<String, String> namespaces) {
        this.namespaces = namespaces;
    }
    
    

    @Override
    public String getNamespaceURI(String string) {
        if (DEFAULT_NAMESPACE_PREFIX.equals(string)) {
            return namespaces.get(NAMESPACE_PREFIX);
        }
        return namespaces.get(NAMESPACE_PREFIX + NAMESPACE_SEPARATOR + string);
    }

    @Override
    public String getPrefix(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator getPrefixes(String string) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

}
