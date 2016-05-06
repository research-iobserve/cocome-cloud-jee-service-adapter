package de.kit.ipd.java.utils.framework.statemachine;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            unknown meaning of T
 */
public interface IState<T> {

	void run(IStateMachine<T> machine);

	int getIndex();

}
