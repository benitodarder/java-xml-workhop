package local.tin.tests.xml.tools.console;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import local.tin.tests.xml.utils.Builder;
import local.tin.tests.xml.utils.comparison.ComparisonExclusion;
import local.tin.tests.xml.utils.comparison.ComparisonExclusions;
import local.tin.tests.xml.utils.comparison.NodesComparator;
import local.tin.tests.xml.utils.errors.XMLUtilsException;
import local.tin.tests.xml.utils.files.FileUtils;
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

    public static final String USAGE = "\nUsage: java -jar xml-utils -fileA <file A required> -fileB <file B required> "
            + "\n-allXPath <true/false, optional to show all unmatching expression or only the ones are leafs>"
            + "\n-exceptions <optional file path to an exceptions file:"
            + "\n\t<node name>/<node name>/@<attribute name>"
            + "\n\t<node name>/<node name>";
    public static final int MAX_ARGUMENTS = 10;
    public static final int MIN_ARGUMENTS = 4;
    private static final Logger LOGGER = Logger.getLogger(XMLComparator.class);

    /**
     * @param args the command line arguments
     * @throws local.tin.tests.xml.utils.errors.XMLUtilsException
     */
    public static void main(String[] args) throws XMLUtilsException, IOException {
        if (MIN_ARGUMENTS <= args.length && args.length <= MAX_ARGUMENTS) {
            String fileA = Common.getInstance().getArgument("-fileA", args);
            String fileB = Common.getInstance().getArgument("-fileB", args);
            ComparisonExclusions excludedNodes = null;
            ComparisonExclusions excludedAttributes = null;
            if (Common.getInstance().getArgument("-exceptions", args) != null) {
                excludedNodes = new ComparisonExclusions();
                excludedAttributes = new ComparisonExclusions();
                String filePath = Common.getInstance().getArgument("-exceptions", args);
                List<String> exceptions = FileUtils.getInstance().getFileAsStringList(filePath);
                for (String current : exceptions) {
                    String[] split = current.split("/|@");
                    if (current.contains("@")) {
                        ComparisonExclusion comparisonExclusion = new ComparisonExclusion();
                        comparisonExclusion.setAttributeName(split[3]);
                        comparisonExclusion.setNodeLocalName(split[1]);
                        comparisonExclusion.setParentLocalName(split[0]);
                        excludedAttributes.put(comparisonExclusion.getAttributeName(), comparisonExclusion);
                    } else {
                        ComparisonExclusion comparisonExclusion = new ComparisonExclusion();
                        comparisonExclusion.setNodeLocalName(split[1]);
                        comparisonExclusion.setParentLocalName(split[0]);
                        excludedNodes.put(comparisonExclusion.getNodeLocalName(), comparisonExclusion);
                    }
                }
            }
            boolean allPath = false;
            if (Common.getInstance().getArgument("-allXPath", args) != null) {
                allPath = Boolean.parseBoolean(Common.getInstance().getArgument("-allXPath", args));
            }
            Document documentA = Builder.getInstance().getDocumentFromFile(fileA, true);
            Document documentB = Builder.getInstance().getDocumentFromFile(fileB, true);
            if (NodesComparator.getInstance().isSameDocument(documentA, documentB, excludedNodes, excludedAttributes)) {
                LOGGER.info(fileA + " and " + fileB + " contain same data");
            } else {
                LOGGER.info(fileA + " and " + fileB + " do not contain the same data, generating not matching XPath expressions.");
                Set<String> xpathsA = XPathGenerator.getInstance().getDocumentXPaths(documentA, true, excludedAttributes);
                Set<String> xpathsB = XPathGenerator.getInstance().getDocumentXPaths(documentB, true, excludedAttributes);
                Set<String> unmatchedAXPathsInB = new HashSet<>();
                Set<String> unmatchedBXpathsInA = new HashSet<>();
                LOGGER.debug("Matching XPaths from: " + fileA + " in " + fileB);
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
                    if (allPath) {
                        if (!NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, excludedNodes, excludedAttributes)) {
                            unmatchedAXPathsInB.add(xpath);
                        }
                    } else {
                        if (!NodesComparator.getInstance().isSameNodeListShallowly(nodeListA, nodeListB, excludedNodes, excludedAttributes)) {
                            unmatchedAXPathsInB.add(xpath);
                        }
                    }
                }
                LOGGER.debug("Matching XPaths from: " + fileB + " in " + fileA);
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
                    if (allPath) {
                        if (!NodesComparator.getInstance().isSameNodeListDeeply(nodeListA, nodeListB, excludedNodes, excludedAttributes)) {
                            unmatchedBXpathsInA.add(xpath);
                        }
                    } else {
                        if (!NodesComparator.getInstance().isSameNodeListShallowly(nodeListA, nodeListB, excludedNodes, excludedAttributes)) {
                            unmatchedBXpathsInA.add(xpath);
                        }
                    }
                }
                LOGGER.info("Unmatched XPaths from: " + fileA + " in " + fileB);
                for (String string : unmatchedAXPathsInB) {
                    LOGGER.info(string);
                }
                LOGGER.info("Unmatched XPaths from: " + fileB + " in " + fileA);
                for (String string : unmatchedBXpathsInA) {
                    LOGGER.info(string);
                }
            }
        } else {
            LOGGER.info(USAGE);
        }
    }

}
