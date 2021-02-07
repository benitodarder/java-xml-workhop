package local.tin.tests.xml.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

/**
 *
 * @author benitodarder
 */
public class RegexUtilsTest {
    
    private static final String SAMPLE_STRING = "aixo es una prova";
    private static final String MATCHING_REGEX = "(^.*)( es )(.*$)";
    private static final String NOT_MATCHING_REGEX = "(^.*)(  es )(.*$)";

    
    @Test
    public void isMatch_returns_true_when_string_matches_patter() {
        
        boolean result = RegexUtils.getInstance().isMatch(MATCHING_REGEX, SAMPLE_STRING);
        
        assertThat(result, equalTo(true));
    }
    
    @Test
    public void isMatch_returns_false_when_string_does_not_match_patter() {
        
        boolean result = RegexUtils.getInstance().isMatch(NOT_MATCHING_REGEX, SAMPLE_STRING);
        
        assertThat(result, equalTo(false));
    }    
}
