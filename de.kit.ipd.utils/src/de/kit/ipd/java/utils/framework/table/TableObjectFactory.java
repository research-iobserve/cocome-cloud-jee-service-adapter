package de.kit.ipd.java.utils.framework.table;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 *
 * @author unknown
 *
 */
@XmlRegistry
public class TableObjectFactory {

	private static final QName TABLE_QNAME = new QName("", "Table");
	private static final QName HEADER_QNAME = new QName("", "Header");
	private static final QName ROW_QNAME = new QName("", "Row");
	private static final QName COLUMN_QNAME = new QName("", "Column");

	public TableObjectFactory() {}

	public Table createTable() {
		return new Table();
	}

	@XmlElementDecl(namespace = "", name = "Table")
	public JAXBElement createTable(final Table value) {
		return new JAXBElement<Table>(TABLE_QNAME, Table.class, null, value);
	}

	public TableHeader createTableHeader() {
		return new TableHeader();
	}

	@XmlElementDecl(namespace = "", name = "Header")
	public JAXBElement createTableHeader(final TableHeader value) {
		return new JAXBElement<TableHeader>(HEADER_QNAME, TableHeader.class, null, value);
	}

	public Row createRow() {
		return new Row();
	}

	@XmlElementDecl(namespace = "", name = "Row")
	public JAXBElement<Row> createTable(final Row value) {
		return new JAXBElement<Row>(ROW_QNAME, Row.class, null, value);
	}

	public Column createColumn() {
		return new Column();
	}

	@XmlElementDecl(namespace = "", name = "Column")
	public JAXBElement<Column> createColumn(final Column value) {
		return new JAXBElement<Column>(COLUMN_QNAME, Column.class, null, value);
	}

}
