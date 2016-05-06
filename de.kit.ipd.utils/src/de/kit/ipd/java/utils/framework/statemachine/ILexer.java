package de.kit.ipd.java.utils.framework.statemachine;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            the function of this type T is unknown
 */
public interface ILexer<T> {

	IStateMachine<T> getMachine();

	void addVisitor(ILexerVisitor<T> visitor);
}
