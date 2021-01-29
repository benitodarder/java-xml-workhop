package local.tin.tests.xml.tools.console;

import java.util.Set;
import local.tin.tests.xml.utils.Builder;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author benitodarder
 */
public class XPathGenerator {

    public static final String USAGE = "Usage:\njava -cp target/xml-tools-console-0.1.0-SNAPSHOT-jar-with-dependencies.jar local.tin.tests.xml.tools.console.XPathGenerator -file <File path> -includeNamespaces <true/false>";
    public static final int MAX_ARGUMENTS = 2;
    public static final int MIN_ARGUMENTS = 2;
    private static final Logger LOGGER = Logger.getLogger(XPathGenerator.class);

    /**
     * @param args the command line arguments
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public static void main(String[] args) throws XMLUtilsException {
        if (MIN_ARGUMENTS <= args.length && args.length <= MAX_ARGUMENTS) {
            String filePath = Common.getInstance().getArgument("-file", args);
            boolean includeNamespace = Boolean.parseBoolean(Common.getInstance().getArgument("-includeNamespaces", args));
            Document document = Builder.getInstance().getDocumentFromFile(args[0], true);
            Set<String> xpaths = local.tin.tests.xml.utils.xpath.XPathGenerator.getInstance().getDocumentXPaths(document, includeNamespace);
            xpaths.forEach((string) -> {
                LOGGER.info(string);
            });
        } else {
            LOGGER.info(USAGE);
        }
    }

}
