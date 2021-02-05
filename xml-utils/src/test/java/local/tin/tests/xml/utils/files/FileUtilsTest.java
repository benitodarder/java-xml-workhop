package local.tin.tests.xml.utils.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author benito.darder
 */
public class FileUtilsTest {
    
    
    @Test
    public void getFileInputStream_returs_the_bufferedreader() throws Exception {
        
        Object result  = FileUtils.getInstance().getBufferedReader(FileUtilsTest.class.getResource("sample.xml").getPath());
        
        assertThat(result, instanceOf(BufferedReader.class));
    }    
    
    @Test(expected=IOException.class)
    public void getFileInputStream_throws_ioexception_when_can_not_find_the_file() throws Exception {
        
        BufferedReader result  = FileUtils.getInstance().getBufferedReader("sample.xml");
        
    }   
    
    @Test
    public void getPropertiesFile_returs_the_corresponding_properties() throws Exception {

        
        Properties result  = FileUtils.getInstance().getPropertiesFile(FileUtilsTest.class.getResource("sample.properties").getPath());
        
        assertThat(result.getProperty("prop1"), equalTo("prop1"));
        assertThat(result.getProperty("prop2"), equalTo("2"));
    }   
    
    @Test(expected=IOException.class)
    public void getPropertiesFile_throws_ioexception_when_can_not_find_the_file() throws Exception {

        
        Properties result  = FileUtils.getInstance().getPropertiesFile("blabla");

    }     
    
    @Test
    public void getFileAsString_returns_the_file_content_as_string() throws Exception {
        
        String result = FileUtils.getInstance().getFileAsString(FileUtilsTest.class.getResource("sample.properties").getPath());
        
        assertThat(result.trim(), equalTo("prop1=prop1" + System.lineSeparator() + "prop2=2"));
    }
      
    @Test
    public void saveStringAsFile_saves_the_string() throws IOException {
        String filePath = "test.txt";
        String content = "content";
        
        FileUtils.getInstance().saveStringAsFile(filePath, content);
        
        String savedFile = FileUtils.getInstance().getFileAsString(filePath);
        assertThat(savedFile, equalTo(content));
    }
}
