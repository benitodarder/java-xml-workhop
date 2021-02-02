# xml-tools-console

Console classes to check xml-utils:

* XMLComparator:
	- java -cp target/xml-tools-console-0.1.0-SNAPSHOT-jar-with-dependencies.jar local.tin.tests.xml.tools.console.XMLComparator <File A> <File B>
        - Sample XML files with same name and different suffix number are equal
* XPathGenerator:
	- java -cp target/xml-tools-console-0.1.0-SNAPSHOT-jar-with-dependencies.jar local.tin.tests.xml.tools.console.XPathGenerator -file <File path> -includeNamespacesAttributes <true/false> -includeNamespacesPrefixes <true/false>
* XPathTester supports the following argument:
	- java -cp target/xml-tools-console-0.1.0-SNAPSHOT-jar-with-dependencies.jar local.tin.tests.xml.tools.console.XPathTester -xpath <required states the XPath expression> -file <required states the source XML file> -nameSpaceAware <optional true/false states when check also for namespaces. If true a fake namespace is used for the default one> -fakeDefaultNamespace <Required upon prevous argument. Fake default namespace to use>
