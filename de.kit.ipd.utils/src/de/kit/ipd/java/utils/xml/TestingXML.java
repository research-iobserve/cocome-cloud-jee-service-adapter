package de.kit.ipd.java.utils.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.w3c.dom.Node;

import de.kit.ipd.java.utils.xml.XML.AbstractXMLVisitor;
import de.kit.ipd.java.utils.xml.XML.XMLOption;

/**
 *
 * @author unknown
 *
 */
public class TestingXML extends AbstractXMLVisitor {

	/** Empty constructor. */
	public TestingXML() {}

	public static void main(final String[] args) throws FileNotFoundException {
		final TestingXML test = new TestingXML();
		final XML xml = XML.valueOf(new FileInputStream("testing/testing.xml"));
		xml.walkXML(XMLOption.DEPTH_FIRST_SEARCH, "/", test);
	}

	@Override
	protected XMLOption visitNode(final Node node) {
		System.out.println(node.getNodeName());
		return XMLOption.CONTINUE;
	}

	// @Override
	// protected XMLOption visitNode(String content) {
	// System.out.println(content);
	// return XMLOption.CONTINUE;
	// }
}
