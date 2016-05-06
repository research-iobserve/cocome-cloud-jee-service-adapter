package cocome.cloud.sa.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author unknown
 *
 */
@XmlRootElement(name = "MessageEntry")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessageEntry", propOrder = { "index", "content" })
public class MessageEntry {

	@XmlAttribute(name = "index")
	private int index;

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "Content")
	private String content;

	/** Empty constructor. */
	public MessageEntry() {}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

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
