package local.tin.tests.xml.utils;

import java.util.Objects;

/**
 *
 * @author benitodarder
 */
public class ComparisonExclusion {
    
    private String parentLocalName;
    private String nodeLocalName;
    private String attributeName;

    public String getParentLocalName() {
        return parentLocalName;
    }

    public void setParentLocalName(String parentLocalName) {
        this.parentLocalName = parentLocalName;
    }

    public String getNodeLocalName() {
        return nodeLocalName;
    }

    public void setNodeLocalName(String nodeLocalName) {
        this.nodeLocalName = nodeLocalName;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.parentLocalName);
        hash = 53 * hash + Objects.hashCode(this.nodeLocalName);
        hash = 53 * hash + Objects.hashCode(this.attributeName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComparisonExclusion other = (ComparisonExclusion) obj;
        if (!Objects.equals(this.parentLocalName, other.parentLocalName)) {
            return false;
        }
        if (!Objects.equals(this.nodeLocalName, other.nodeLocalName)) {
            return false;
        }
        if (!Objects.equals(this.attributeName, other.attributeName)) {
            return false;
        }
        return true;
    }
    
    
    
}
