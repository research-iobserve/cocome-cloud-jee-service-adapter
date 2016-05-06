package de.kit.ipd.java.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author unknown
 *
 */
public final class XML {

	/**************************************************************************
	 * FIELDS
	 *************************************************************************/

	private Document doc;

	/**************************************************************************
	 * CONSTRUCTOR
	 *************************************************************************/

	private XML() {}

	private XML(final Document document) {
		this.doc = document;
	}

	/**************************************************************************
	 * STATIC
	 *************************************************************************/
	public static final XML valueOf(final InputStream input) {
		try {
			if ((input != null) && (input.available() != 0)) {
				final DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
				df.setNamespaceAware(true);
				final DocumentBuilder docBuilder = df.newDocumentBuilder();
				return new XML(docBuilder.parse(input));
			}
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final XML valueOf(final String xmlexpression) {
		try {
			final StringReader reader = new StringReader(xmlexpression);
			final DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			df.setNamespaceAware(true);
			final DocumentBuilder docBuilder = df.newDocumentBuilder();
			return new XML(docBuilder.parse(new InputSource(reader)));
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static XML valueOf(final NodeList list, final String rootName) {
		final XML temp = XML.valueOf("<" + rootName + "/>");
		final Node root = temp.getRootNode();
		for (int i = 0; i < list.getLength(); i++) {
			temp.attachNode(list.item(i), root);
		}
		return temp;
	}

	public static XML newInstance() {
		try {
			final DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
			df.setNamespaceAware(true);
			final DocumentBuilder docBuilder = df.newDocumentBuilder();
			return new XML(docBuilder.newDocument());
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Transform the given XML object using the given XSL transformation XML
	 * object.
	 *
	 * @param from
	 * @param xsl
	 * @return String with the transformation
	 */
	public static String transform(final XML from, final XML xsl) {
		try {
			final Document document = from.getDocument();
			final StringReader xslReader = new StringReader(xsl.toString());

			final DOMSource src = new DOMSource(document);
			final StringWriter output = new StringWriter();
			final StreamResult result = new StreamResult(output);

			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer t = tf.newTransformer(new StreamSource(xslReader));
			t.transform(src, result);
			return output.getBuffer().toString();
		} catch (final TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (final TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (final TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**************************************************************************
	 * PUBLIC
	 *************************************************************************/

	public final Document getDocument() {
		return this.doc;
	}

	public final Object search(final String xpathexpression, final Node node,
			final QName xpathconstants) {
		try {
			final XPathFactory xpathfac = XPathFactory.newInstance();
			final XPath path = xpathfac.newXPath();
			return path.evaluate(xpathexpression, node,
					xpathconstants);
		} catch (final XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public final XML filter(final String xpathExpression, final String rootNewXML) {
		if ((xpathExpression == null) || xpathExpression.isEmpty())
			throw new IllegalArgumentException("param xpathExpression is either"
					+ " null or empty");
		final NodeList list = (NodeList) this.search(xpathExpression, this.doc, XPathConstants.NODESET);
		return XML.valueOf(list, rootNewXML);
	}

	public final Node getNode(final String xpathExpression) {
		if ((xpathExpression == null) || xpathExpression.isEmpty())
			throw new IllegalArgumentException("param xpathExpression is either"
					+ " null or empty");
		return (Node) this.search(xpathExpression, this.doc, XPathConstants.NODE);
	}

	public final void write(final OutputStream out) {
		try {
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.MEDIA_TYPE, "text/xml");
			transformer.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
			final DOMSource src = new DOMSource(this.doc);
			final StreamResult result = new StreamResult(out);
			transformer.transform(src, result);
		} catch (final TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (final TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (final TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Walk through the XML-file using visitor-pattern approach
	 *
	 * @param start
	 *            use XPath to get the starting point of the walk through. The
	 *            starting point should be a node not a collection of node
	 * @param visitors
	 */
	public final void walkXML(final XMLOption strategy, final String start, final AbstractXMLVisitor... visitors) {
		Node starting = this.getNode(start);
		if (starting.getNodeType() == Node.DOCUMENT_NODE) {
			starting = starting.getFirstChild().getFirstChild();
		}
		if (starting != null) {
			switch (strategy) {
			case BREADTH_FIRST_SEARCH:
				this.walkRecursivlyBreadthFirstSearch(new ArrayList<Node>(), starting, visitors);
				break;
			case DEPTH_FIRST_SEARCH:
				try {
					this.walkRecursivlyDepthFirstSearch(starting, starting, visitors);
				} catch (final XMLNotification e) {
					if (!e.getMessage().equals(XMLNotification.SUCCESSFULLY_FINISHED)) {
						e.printStackTrace();
					}
				}
				break;
			default:
				throw new IllegalArgumentException("passed invalide strategy");
			}

		} else {
			/** warning, that first node could not be found. Should this be an exception? */
			System.out.println("First node could not be found.");
		}
	}

	@Override
	public String toString() {
		String output = null;

		if (this.doc == null)
			return "";

		try {
			final TransformerFactory tf = TransformerFactory.newInstance();
			final Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			final StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(this.doc), new StreamResult(
					writer));
			output = writer.getBuffer().toString();
		} catch (final TransformerException e) {
			e.printStackTrace();
		}
		return output;
	}

	/**************************************************************************
	 * PRIVATE
	 *************************************************************************/

	private final Node getRootNode() {
		return this.getNode("/*"); //$NON-NLS-1$
	}

	/**
	 * Attach the Node n1 to the document
	 *
	 * @param n1
	 * @param document
	 * @throws DOMException
	 */
	private final void attachNode(final Node n1, final Node n2) {
		try {
			Node myNode = n1.cloneNode(true);
			myNode = this.doc.adoptNode(myNode);
			if (myNode != null) {
				n2.appendChild(myNode);
			}
		} catch (final DOMException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Walk through the document starting from the given start {@link Node}.
	 * Default strategy is a broad search algorithm.
	 *
	 * @param start
	 * @param visitors
	 */
	private void walkRecursivlyBreadthFirstSearch(final List<Node> visitedNodes, final Node node, final AbstractXMLVisitor... visitors) {
		if (node != null) {
			XMLOption option = XMLOption.CONTINUE;
			for (final AbstractXMLVisitor visitor : visitors) {
				// TODO: Why is here a catch all exception? This should not be done this way. At least it
				// should only catch a specific Exception.
				// try {
				switch (node.getNodeType()) {
				case Node.ATTRIBUTE_NODE:
					option = visitor.visitAttr(node);
					if ((option != XMLOption.BREAK) && !node.getTextContent().isEmpty()) {
						option = visitor.visitAttr(node.getTextContent());
					}
					visitedNodes.add(node);
					break;
				case Node.ELEMENT_NODE:
					option = visitor.visitNode(node);
					if ((option != XMLOption.BREAK) && !node.getTextContent().isEmpty()) {
						option = visitor.visitNode(node.getTextContent());
					}
					visitedNodes.add(node);
					break;
				default:
					break;
				}
				// } catch (final Exception e) {
				// e.printStackTrace();
				// }
				switch (option) {
				case CONTINUE:
					// do nothing
					break;
				case BREAK:
					// end with the recursive walk through
					return;
				case SKIP:
					// TODO no functionality yet
					break;
				default:
					break;
				}
			}
			this.walkRecursivlyBreadthFirstSearch(visitedNodes, node.getNextSibling(), visitors);
		}
		// here there is no sibling any more
		if (visitedNodes.size() > 0) {
			final int len = visitedNodes.size() - 1;
			for (int i = 0; i <= len; i++) {
				if (visitedNodes.get(i).hasChildNodes()) {
					final Node n = visitedNodes.get(i).getFirstChild();
					visitedNodes.remove(0);
					this.walkRecursivlyBreadthFirstSearch(visitedNodes, n, visitors);
					break;
				}
			}
		}
	}

	// TODO first parameter is never used.
	private void walkRecursivlyDepthFirstSearch(final Node start, final Node node, final AbstractXMLVisitor... visitors) throws XMLNotification {
		if (node != null) {
			XMLOption option = XMLOption.CONTINUE;
			for (final AbstractXMLVisitor visitor : visitors) {
				// TODO: Why is here a catch all exception? This should not be done this way. At least it
				// should only catch a specific Exception.
				// try {
				switch (node.getNodeType()) {
				case Node.ATTRIBUTE_NODE:
					option = visitor.visitAttr(node);
					if ((option != XMLOption.BREAK) && !node.getTextContent().isEmpty()) {
						option = visitor.visitAttr(node.getTextContent());
					}
					break;
				case Node.ELEMENT_NODE:
					option = visitor.visitNode(node);
					if ((option != XMLOption.BREAK) && !node.getTextContent().isEmpty()) {
						option = visitor.visitNode(node.getTextContent());
					}
					break;
				default:
					break;
				}
				// } catch (final Exception e) {
				// e.printStackTrace();
				// }
				switch (option) {
				case CONTINUE:
					// do nothing
					break;
				case BREAK:
					// end with the recursive walk through
					return;
				case SKIP:
					// TODO no functionality yet
					break;
				default:
					break;
				}
			}
			this.walkRecursivlyDepthFirstSearch(node, node.getFirstChild(), visitors);
		}

		// here there is no sibling any more
		Node nextSibling = null; // TODO remove, this line is obsolete.
		Node present = start;
		while ((nextSibling = present.getNextSibling()) == null) {
			present = present.getParentNode();
			if (start.getNodeType() == Node.DOCUMENT_NODE)
				throw new XMLNotification(XMLNotification.SUCCESSFULLY_FINISHED);
		}
		if (nextSibling != null) {
			this.walkRecursivlyDepthFirstSearch(start, nextSibling, visitors);
		}
	}

	/*************************************************************************
	 * LOCAL CLASS
	 ************************************************************************/

	public class XMLNotification extends Throwable {

		public static final String SUCCESSFULLY_FINISHED = "exception.successfully.finished";

		private static final long serialVersionUID = 1L;

		public XMLNotification(final String string) {
			super(string);
		}
	}

	/**
	 *
	 * @author unknown
	 *
	 */
	public enum XMLOption {
		CONTINUE, BREAK, SKIP, BREADTH_FIRST_SEARCH, DEPTH_FIRST_SEARCH;
	}

	/**
	 *
	 * @author AlessandroGiusa@gmail.com
	 *
	 */
	public abstract static class AbstractXMLVisitor {

		protected XMLOption visitNode(final Node node) {
			return XMLOption.CONTINUE;
		}

		protected XMLOption visitNode(final String content) {
			return XMLOption.CONTINUE;
		}

		protected XMLOption visitAttr(final Node node) {
			return XMLOption.CONTINUE;
		}

		protected XMLOption visitAttr(final String content) {
			return XMLOption.CONTINUE;
		}

	}

}
