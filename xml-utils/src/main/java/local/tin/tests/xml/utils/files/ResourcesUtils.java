package local.tin.tests.xml.utils.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 *
 * @author benito.darder
 */
public class ResourcesUtils {
    
    private ResourcesUtils() {
    }
    
    public static ResourcesUtils getInstance() {
        return ResourcesUtilsHolder.INSTANCE;
    }
    
    private static class ResourcesUtilsHolder {

        private static final ResourcesUtils INSTANCE = new ResourcesUtils();
    }
    
    /**
     * Opens the specified file path, and returns a Properties with the included
     * properties.
     *
     * @param klass
     * @param fileName
     * @return Properties
     * @throws IOException
     */
    public Properties getPropertiesFile(Class<?> klass, String fileName) throws IOException {
        InputStreamReader fileInputStream = new InputStreamReader(klass.getResourceAsStream(fileName));
        Properties properties = new Properties();
        properties.load(fileInputStream);
        return properties;
    }    
    
    /**
     * Returns a String containing the content of the file from resources
     * folder.
     *
     * @param klass Class to state the package
     * @param fileName String
     * @return String
     * @throws java.io.IOException
     */
    public String getFileAsString(Class<?> klass, String fileName) throws IOException {
        InputStreamReader fileInputStream = new InputStreamReader(klass.getResourceAsStream(fileName));
        BufferedReader bufferedReader = new BufferedReader(fileInputStream);
        StringBuilder stringBuilder = new StringBuilder();
        String string = bufferedReader.readLine();
        while (string != null) {
            stringBuilder.append(string);
            string = bufferedReader.readLine();
            if (string != null) {
                stringBuilder.append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }    
}
