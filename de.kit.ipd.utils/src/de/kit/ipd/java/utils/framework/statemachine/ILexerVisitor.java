package de.kit.ipd.java.utils.framework.statemachine;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            semantics of T are unknown
 */
public interface ILexerVisitor<T> {

	void visit(IStateMachine<T> machine, int state, T token);

}
