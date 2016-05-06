package de.kit.ipd.java.utils.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * A JAXB-Framework layer to store and load xml-data based on its schema.
 *
 * @author alessandrogiusa@gmail.com
 *
 */
public final class JAXBEngine {

	/** instance of this class, Singleton */
	private static JAXBEngine jaxbEngineInstance;

	/** if debug, all System.out.println will be invoked */
	private boolean debug = false;

	/**
	 * Make sure this (factory) class is not instantiated.
	 */
	private JAXBEngine() {}

	/**
	 * Turn the debug on
	 */
	public void debugON() {
		this.debug = true;
	}

	/**
	 * Turn the debug off
	 */
	public void debugOFF() {
		this.debug = false;
	}

	/**
	 * Get new instance of {@link JAXBEngine}. Client should take care of the
	 * instace
	 *
	 * @return
	 */
	public static JAXBEngine getInstance() {
		return new JAXBEngine();
	}

	/**
	 * Get an instance of the JAXBEngine ->Singleton. Each call of this method,
	 * will bring the same object
	 *
	 * @return
	 */
	public static JAXBEngine getSingleTon() {
		if (jaxbEngineInstance == null) {
			jaxbEngineInstance = new JAXBEngine();
		}
		return jaxbEngineInstance;
	}

	/**
	 * Kill the internal instance
	 */
	public static void kill() {
		jaxbEngineInstance = null;
	}

	/**
	 * Create a {@link SchemaFileResolver#SchemaResolver(String, String)}
	 *
	 * @param namespaceUri
	 * @param suggestedFileName
	 * @return
	 */
	private SchemaFileResolver getSchemaRessolver(final String namespaceUri,
			final String suggestedFileName) {
		final SchemaFileResolver schemaRes = new SchemaFileResolver(namespaceUri,
				suggestedFileName);
		return schemaRes;
	}

	/**
	 * Write the obj to xml by using the given schema
	 *
	 * @param obj
	 *            object which should be persist to xml
	 * @param clazz
	 *            class of the obj
	 * @param fileSchema
	 *            whole path to the schema which should be used
	 * @param outputFilename
	 *            output file name
	 * @return true if the write process was okay
	 */
	public boolean write(final Object obj, final File fileSchema, final String outputFilename,
			final Class<?>... classes) {
		try {
			this.checkFiles(fileSchema);
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			final SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			if (this.debug) {
				System.out.println(fileSchema.getAbsolutePath());
			}
			final Schema schema = sf.newSchema(fileSchema);
			ms.setSchema(schema);
			ms.marshal(obj, new FileOutputStream(outputFilename, false));
			return true;
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Write the given classes into the provided node. A schema file will be
	 * generated temperately in the location of eclipse platform with the name
	 * tempschema.xsd
	 *
	 * @param obj
	 * @param node
	 * @param classes
	 * @return true, if the write process was successfully
	 */
	public boolean write(final Object obj, final File fileSchema, final Node node,
			final Class<?>... classes) {
		try {
			this.checkFiles(fileSchema);
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			final SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			final Schema schema = sf.newSchema(fileSchema);
			ms.setSchema(schema);
			ms.marshal(obj, node);
			return true;
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Write the given classes into the provided node. A schema file will be
	 * generated temperately in the location of eclipse platform with the name
	 * tempschema.xsd
	 *
	 * @param obj
	 * @param node
	 * @param classes
	 * @return true, if the write process was successfully
	 */
	public boolean write(final Object obj, final Node node, final Class<?>... classes) {
		try {
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ms.setSchema(this.createSchema(context));
			ms.marshal(obj, node);
			return true;
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Get the given object based on the classes back as {@link XML}
	 *
	 * @param obj
	 * @param classes
	 * @return
	 */
	public XML write(final Object obj, final Class<?>... classes) {
		try {
			JAXBContext context = null;
			if ((classes == null) || (classes.length == 0)) {
				context = JAXBContext.newInstance(obj.getClass());
			} else {
				context = JAXBContext.newInstance(classes);
			}

			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ms.setSchema(this.createSchema(context));
			final XML xml = XML.newInstance();
			ms.marshal(obj, xml.getDocument());
			return xml;
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Write the object to the console
	 *
	 * @param obj
	 * @param node
	 * @param classes
	 * @return true, if the write process was successfully
	 */
	public boolean write(final Object obj, final OutputStream out, final Class<?>... classes) {
		try {
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ms.setSchema(this.createSchema(context));
			ms.marshal(obj, out);
			return true;
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Write the object to the console
	 *
	 * @param obj
	 * @param node
	 * @param classes
	 * @return true, if the write process was successfully
	 */
	public boolean write(final Object obj, final Writer writer, final Class<?>... classes) {
		try {
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Marshaller ms = context.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ms.setSchema(this.createSchema(context));
			ms.marshal(obj, writer);
			return true;
		} catch (final NullPointerException e) {
			e.printStackTrace();
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Read the xml passed as string
	 *
	 * @param xml
	 *            string with xml
	 * @param classes
	 * @return
	 */
	public Object read(final String xml, final Class<?>... classes) {
		if ((xml != null) && !xml.isEmpty()) {
			try {
				final JAXBContext context = JAXBContext.newInstance(classes);
				final Unmarshaller unm = context.createUnmarshaller();
				unm.setSchema(this.createSchema(context));
				return unm.unmarshal(new StringReader(xml));
			} catch (final JAXBException e) {
				e.printStackTrace();
			} catch (final NullPointerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Read the xml passed as string
	 *
	 * @param xml
	 *            string with xml
	 * @param classes
	 * @return
	 */
	public Object read(final File xml, final Class<?>... classes) {
		if ((xml != null) && xml.exists()) {
			try {
				final JAXBContext context = JAXBContext.newInstance(classes);
				final Unmarshaller unm = context.createUnmarshaller();
				unm.setSchema(this.createSchema(context));
				return unm.unmarshal(xml);
			} catch (final JAXBException e) {
				e.printStackTrace();
			} catch (final NullPointerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Read xml to object of type which is passed in by using the passed schema
	 *
	 * @param filexml
	 *            xml which should be get into object
	 * @param fileSchema
	 *            schema of filexml
	 * @param type
	 *            type of the object which should be created
	 * @return the main object based on xml and schema
	 */
	public Object read(final File filexml, final File fileSchema, final Class<?>... classes) {
		try {
			this.checkFiles(filexml, fileSchema);
			final JAXBContext context = JAXBContext.newInstance(classes);
			final Unmarshaller unm = context.createUnmarshaller();
			final SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			if (this.debug) {
				System.out.println(fileSchema.getAbsolutePath());
			}
			final Schema schema = sf.newSchema(fileSchema);
			unm.setSchema(schema);
			final Object obj = unm.unmarshal(filexml);
			return obj;
		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check whether the files are available or not
	 *
	 * @param files
	 * @throws FileNotFoundException
	 */
	private void checkFiles(final File... files) throws FileNotFoundException,
			NullPointerException {
		if ((files != null) && (files.length > 0)) {
			final File[] myFiles = files.clone();
			for (final File itrFile : myFiles) {
				if (!itrFile.exists())
					throw new FileNotFoundException("The file:"
							+ itrFile.getAbsolutePath() + " is not available?");
			}
		} else
			throw new NullPointerException("the given files are null or empty");
	}

	/**
	 * Create a schema file based on clazz passed in. The schema consider all
	 * complex types in the class. The whole hierarchy
	 *
	 * @param parentPath
	 *            parent path of schema. if "" or null, the current place of
	 *            thread is taken
	 * @param schemaName
	 *            name for the schema file
	 * @param clazz
	 *            the class which from what the schema should be generated.
	 *            Normally the top-level class
	 * @return the file of the schema, this can be used as input for other
	 *         methods of this class
	 */
	public File createSchema(final String parentPath, final String schemaName,
			final Class<?>... classes) {
		try {
			final JAXBContext context = JAXBContext.newInstance(classes);
			final SchemaFileResolver sr = this.getSchemaRessolver(parentPath, schemaName);
			context.generateSchema(sr);
			return sr.schemaFile();
		} catch (final JAXBException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Create a {@link Schema} file in a virtual {@link StringWriter} /
	 * {@link StringReader}
	 *
	 * @param context
	 * @return
	 */
	public Schema createSchema(final JAXBContext context) {
		try {
			final SchemaStringBufferResolver sr = new SchemaStringBufferResolver();
			context.generateSchema(sr);
			final SchemaFactory sf = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			final StringWriter writer = sr.getBuffer();
			final StringReader reader = new StringReader(writer.getBuffer()
					.toString());
			final StreamSource source = new StreamSource(reader);
			final Schema schema = sf.newSchema(source);
			return schema;
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final SAXException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate java-files based on the passed schema into
	 *
	 * @param packagename
	 * @param destdir
	 * @param fileschema
	 * @return
	 */
	public boolean generateClasses(final String packagename, final File destdir,
			final File fileschema) {
		if (destdir.exists() && fileschema.exists()) {
			if (this.debug) {
				System.out.println("creating files in directory "
						+ destdir.getAbsoluteFile() + " whith schema "
						+ fileschema.getAbsolutePath());
			}
			try {
				final String command = "xjc -p " + packagename + " -d "
						+ destdir.getAbsolutePath() + " "
						+ fileschema.getAbsolutePath();
				if (this.debug) {
					System.out.println("com: " + command);
				}
				Runtime.getRuntime().exec(command);
				return true;
			} catch (final IOException e) {
				e.printStackTrace();
			}
			return false;
		}
		if (this.debug) {
			System.out.println("directory " + destdir.getAbsoluteFile()
					+ " OR schema " + fileschema.getAbsolutePath()
					+ " doesn't exist!");
		}
		return false;
	}

	/**
	 * Resolve the {@link SchemaOutputResolver}
	 *
	 * @author alessandro.giusa@valeo.com, SBX1322
	 *
	 */
	private class SchemaFileResolver extends SchemaOutputResolver {

		private String schemaName;
		private String directory;

		/**
		 * Create an instance of a {@link SchemaFileResolver}
		 *
		 * @param directory
		 *            parent path of schema name
		 * @param schemaName
		 *            only the filename of the schema which should be created
		 *            with the extension .xsd
		 */
		public SchemaFileResolver(final String directory, final String schemaName) {
			super();
			if (!schemaName.contains(".")) {
				this.schemaName += ".xsd";
			} else {
				this.schemaName = schemaName;
			}

			if ((directory == null) || directory.isEmpty()) {
				this.directory = ".";
			} else {
				this.directory = directory;
			}
		}

		@Override
		public Result createOutput(final String namespaceUri, final String suggestedFileName)
				throws IOException {
			final File file = new File(this.directory, this.schemaName);
			final StreamResult result = new StreamResult(file);
			result.setSystemId(file.toURI().toURL().toString());
			if (JAXBEngine.this.debug) {
				System.out.println(file.toURI().toURL().toString());
			}
			return result;
		}

		/**
		 * Get the schema file
		 *
		 * @return
		 */
		public File schemaFile() {
			return new File(this.directory, this.schemaName);
		}
	}

	/**
	 * This implementation of {@link SchemaOutputResolver} is only for virtual
	 * working, where the schema is not needed afterwards
	 *
	 * @author alessandro.giusa@valeo.com, SBX1322
	 *
	 */
	public class SchemaStringBufferResolver extends SchemaOutputResolver {

		private StringWriter buffer;

		public SchemaStringBufferResolver() {
			super();
		}

		@Override
		public Result createOutput(final String namespaceUri, final String suggestedFileName)
				throws IOException {
			this.buffer = new StringWriter();
			final StreamResult result = new StreamResult(this.buffer);
			result.setSystemId("");
			return result;
		}

		public StringWriter getBuffer() {
			return this.buffer;
		}
	}
}
