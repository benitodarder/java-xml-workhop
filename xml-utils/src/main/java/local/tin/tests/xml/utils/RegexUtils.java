package local.tin.tests.xml.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author benitodarder
 */
public class RegexUtils {

    private RegexUtils() {
    }

    public static RegexUtils getInstance() {
        return RegexUtilsHolder.INSTANCE;
    }

    private static class RegexUtilsHolder {
        private static final RegexUtils INSTANCE = new RegexUtils();
    }
    
    public boolean isMatch(String patternString, String string) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
 }
