package local.tin.tests.xml.tools.console;

import java.util.HashSet;
import java.util.Set;
import local.tin.tests.xml.utils.Builder;
import local.tin.tests.xml.utils.NodesComparator;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import local.tin.tests.xml.utils.xpath.XPathDetails;
import local.tin.tests.xml.utils.xpath.XPathGenerator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author benitodarder
 */
public class XMLComparator {

    private static final Logger LOGGER = Logger.getLogger(XMLComparator.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XMLUtilsException {
        if (args.length >= 2) {
            Document documentA = Builder.getInstance().getDocumentFromFile(args[0], false);
            Document documentB = Builder.getInstance().getDocumentFromFile(args[1], false);
            if (NodesComparator.getInstance().isSameDocument(documentA, documentB)) {
                LOGGER.info(args[0] + " and " + args[1] + " contain same data");
            } else {
                LOGGER.info(args[0] + " and " + args[1] + " do not contain the same data, generating not matching XPath expressions.");
                Set<String> xpathsA = XPathGenerator.getInstance().getDocumentXPaths(documentA, false);
                Set<String> xpathsB = XPathGenerator.getInstance().getDocumentXPaths(documentB, false);
                Set<String> unmatchedAXPathsInB = new HashSet<>();
                Set<String> unmatchedBXpathsInA = new HashSet<>();
                LOGGER.debug("Matching XPaths from: " + args[0] + " in " + args[1]);
                for (String xpath : xpathsA) {
                    XPathDetails xpathDetailsA = new XPathDetails();
                    xpathDetailsA.setDocument(documentA);
                    xpathDetailsA.setNamespaceAware(false);
                    xpathDetailsA.setXpathExpression(xpath);
                    NodeList nodeListA = local.tin.tests.xml.utils.xpath.XPathTester.getInstance().getResult(xpathDetailsA);
                    XPathDetails xpathDetailsB = new XPathDetails();
                    xpathDetailsB.setDocument(documentB);
                    xpathDetailsB.setNamespaceAware(false);
                    xpathDetailsB.setXpathExpression(xpath);
                    NodeList nodeListB = local.tin.tests.xml.utils.xpath.XPathTester.getInstance().getResult(xpathDetailsB);
                    LOGGER.debug("XPath: " + xpath + "\nNodes in A: " + nodeListA.getLength() + "; nods in B: " + nodeListB.getLength());
                    if (!NodesComparator.getInstance().isSameNodeList(nodeListA, nodeListB)) {
                        unmatchedAXPathsInB.add(xpath);
                    }
                }
                LOGGER.debug("Matching XPaths from: " + args[1] + " in " + args[0]);
                for (String xpath : xpathsB) {
                    XPathDetails xpathDetailsA = new XPathDetails();
                    xpathDetailsA.setDocument(documentA);
                    xpathDetailsA.setNamespaceAware(false);
                    xpathDetailsA.setXpathExpression(xpath);
                    NodeList nodeListA = local.tin.tests.xml.utils.xpath.XPathTester.getInstance().getResult(xpathDetailsA);
                    XPathDetails xpathDetailsB = new XPathDetails();
                    xpathDetailsB.setDocument(documentB);
                    xpathDetailsB.setNamespaceAware(false);
                    xpathDetailsB.setXpathExpression(xpath);
                    NodeList nodeListB = local.tin.tests.xml.utils.xpath.XPathTester.getInstance().getResult(xpathDetailsB);
                    LOGGER.debug("XPath: " + xpath + "\nNodes in A: " + nodeListA.getLength() + "; nods in B: " + nodeListB.getLength());
                    if (!NodesComparator.getInstance().isSameNodeList(nodeListA, nodeListB)) {
                        unmatchedBXpathsInA.add(xpath);
                    }
                }
                LOGGER.info("Unmatched XPaths from: " + args[0] + " in " + args[1]);
                for (String string : unmatchedAXPathsInB) {
                    LOGGER.info(string);
                }
                LOGGER.info("Unmatched XPaths from: " + args[1] + " in " + args[0]);
                for (String string : unmatchedBXpathsInA) {
                    LOGGER.info(string);
                }                
            }
        } else {
            LOGGER.info("Usage: java -jar xml-utils <file A> <file B>");
        }
    }

}
