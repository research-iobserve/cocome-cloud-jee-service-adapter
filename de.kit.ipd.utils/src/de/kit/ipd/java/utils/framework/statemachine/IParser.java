package de.kit.ipd.java.utils.framework.statemachine;

import java.io.InputStream;

/**
 *
 * @author unknown
 *
 * @param <M>
 *            semantics of M are unknwon
 */
public interface IParser<M> {

	public void parse(InputStream in);

	public void parse(String content);

	public M getModel();

	public void setModel(M model);

	public void addVisitor(IParserVisitor<M> visitor);

}
