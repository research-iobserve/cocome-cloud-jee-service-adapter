package de.kit.ipd.java.utils.framework.statemachine;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            semantics of T is unknown
 */
public interface IStateMachine<T> {

	void setEOLState(int index);

	void setInput(T input);

	void add(IState<T> state);

	void run(int state);

	T getNext();

	void setNextState(IState<T> s);

	void setNextState(int index);

	IState<T> getState(int index);

	int size();

	void callVisitor(int index, T token);

	void addVisitor(ILexerVisitor<T> visitor);

	void removeVisitor(ILexerVisitor<T> visitor);

	void appendToken(T token);

	T getToken();

	void resetToken();

}
