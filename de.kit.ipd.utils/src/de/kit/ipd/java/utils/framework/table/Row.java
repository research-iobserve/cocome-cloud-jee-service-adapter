package de.kit.ipd.java.utils.framework.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            content type of the row
 */
@XmlRootElement(name = "Row")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Row", propOrder = { "name", "index", "columns" })
public class Row<T> implements Serializable {

	private static final long serialVersionUID = 1257889213019604259L;

	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "index", required = true)
	private int index;

	@XmlElementWrapper(name = "Columns", required = true)
	@XmlElement(name = "Column", required = true)
	private List<Column<T>> columns = new ArrayList<>();

	/**
	 * This constructor could be replaced by
	 * a constructor with parameters or instead it should be
	 * stated why it has no parameters.
	 */
	public Row() {}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public List<Column<T>> getColumns() {
		return this.columns;
	}

	public void setColumns(final List<Column<T>> columns) {
		this.columns = columns;
	}

	public int size() {
		return this.columns.size();
	}

}
