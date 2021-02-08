package local.tin.tests.xml.utils.comparison;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author benitodarder
 */
public class ComparisonExclusions {

    private Map<String, Set<ComparisonExclusion>> map;

    public ComparisonExclusions() {
        map = new HashMap<>();
    }

    public void put(String key, ComparisonExclusion comparisonExclusion) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<>());
        }
        map.get(key).add(comparisonExclusion);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public boolean containsByParent(String key, String parentLocalName) {
        if (!containsKey(key)) {
            return false;
        }
        Set<ComparisonExclusion> comparisons = map.get(key);
        for (ComparisonExclusion current : comparisons) {
            if (current.getParentLocalName().equals(parentLocalName)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsByNode(String key, String nodeLocalName) {
        if (!containsKey(key)) {
            return false;
        }
        Set<ComparisonExclusion> comparisons = map.get(key);
        for (ComparisonExclusion current : comparisons) {
            if (current.getNodeLocalName().equals(nodeLocalName)) {
                return true;
            }
        }
        return false;
    }    
}
