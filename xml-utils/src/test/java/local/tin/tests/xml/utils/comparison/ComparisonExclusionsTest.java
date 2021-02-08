package local.tin.tests.xml.utils.comparison;

import local.tin.tests.xml.utils.comparison.ComparisonExclusion;
import local.tin.tests.xml.utils.comparison.ComparisonExclusions;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class ComparisonExclusionsTest {
    
    private static final String SAMPLE_PARENT = "parent";
    private static final String SAMPLE_NODE = "node";
    private static final String SAMPLE_ATTRIBUTE = "attribute";
    private ComparisonExclusion comparisonExclusion;
    private ComparisonExclusions comparisonExclusions;
    
    @Before
    public void setUp() {
        comparisonExclusions = new ComparisonExclusions();
        comparisonExclusion = new ComparisonExclusion();
    }

    @Test
    public void containsKey_returns_true_after_put_key() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsKey(SAMPLE_ATTRIBUTE);
        
        assertThat(result, equalTo(true));        
    }
    
    @Test
    public void containsKey_returns_false_when_not_put_key() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_NODE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsKey(SAMPLE_ATTRIBUTE);
        
        assertThat(result, equalTo(false));        
    }   
    
    @Test
    public void containsByParent_returns_true_when_present() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByParent(SAMPLE_ATTRIBUTE, SAMPLE_PARENT);
        
        assertThat(result, equalTo(true));        
    }   
    
    
    @Test
    public void containsByParent_returns_false_when_not_present_by_parent() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByParent(SAMPLE_ATTRIBUTE, SAMPLE_PARENT + SAMPLE_PARENT);
        
        assertThat(result, equalTo(false));        
    }  

    
    @Test
    public void containsByParent_returns_false_when_not_present_by_key() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByParent(SAMPLE_ATTRIBUTE + SAMPLE_ATTRIBUTE, SAMPLE_PARENT);
        
        assertThat(result, equalTo(false));        
    }    
    
     @Test
    public void containsByNode_returns_true_when_present() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByNode(SAMPLE_ATTRIBUTE, SAMPLE_NODE);
        
        assertThat(result, equalTo(true));        
    }    
    
      @Test
    public void containsByNode_returns_false_when_not_present_by_node() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByNode(SAMPLE_ATTRIBUTE, SAMPLE_NODE + SAMPLE_NODE);
        
        assertThat(result, equalTo(false));        
    }     
    
      @Test
    public void containsByNode_returns_false_when_not_present_by_key() {
        comparisonExclusion.setAttributeName(SAMPLE_ATTRIBUTE);
        comparisonExclusion.setNodeLocalName(SAMPLE_NODE);
        comparisonExclusion.setParentLocalName(SAMPLE_PARENT);
        comparisonExclusions.put(SAMPLE_ATTRIBUTE, comparisonExclusion);
        
        boolean result = comparisonExclusions.containsByNode(SAMPLE_ATTRIBUTE + SAMPLE_ATTRIBUTE, SAMPLE_NODE);
        
        assertThat(result, equalTo(false));        
    }    
}