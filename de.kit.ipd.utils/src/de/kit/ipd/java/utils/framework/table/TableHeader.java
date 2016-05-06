package de.kit.ipd.java.utils.framework.table;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author unknown
 *
 */
@XmlRootElement(name = "Header")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = { "index", "name" })
public class TableHeader implements Serializable {

	private static final long serialVersionUID = -572928658839985138L;

	@XmlAttribute(name = "index", required = true)
	private int index;
	@XmlAttribute(name = "name", required = true)
	private String name;

	/** Should be replaced by one with parameters. */
	public TableHeader() {}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
