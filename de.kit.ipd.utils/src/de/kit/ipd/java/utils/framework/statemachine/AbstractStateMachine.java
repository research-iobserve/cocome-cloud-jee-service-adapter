package de.kit.ipd.java.utils.framework.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author unknown
 *
 * @param <T>
 *            unknown semantics of T
 */
public abstract class AbstractStateMachine<T> implements IStateMachine<T> {

	/************************************************************************
	 * FIELDS
	 ***********************************************************************/

	protected T input;

	private IState<T> currentState;

	private int eolState;

	private boolean running = false;

	private final Map<Integer, IState<T>> states = new HashMap<>();

	private final List<ILexerVisitor<T>> visitors = new ArrayList<ILexerVisitor<T>>();

	/************************************************************************
	 * CONSTRUCTOR
	 ***********************************************************************/

	/************************************************************************
	 * PUBLIC
	 ***********************************************************************/
	@Override
	public void setEOLState(final int index) {
		this.eolState = index;
	}

	@Override
	public void setInput(final T input) {
		this.input = input;
	}

	@Override
	public void add(final IState<T> state) {
		if (state != null) {
			this.states.put(this.states.size(), state);
		} else
			throw new IllegalArgumentException("list of states empty or null");
	}

	/**
	 * Run the
	 *
	 * @param init
	 */
	@Override
	public void run(final int state) {
		this.currentState = this.getState(state);
		this.setMachineRunning();
		while (this.isMachineRunning()) {
			this.currentState.run(this);
		}
		this.getState(this.eolState).run(this);
	}

	@Override
	public T getToken() {
		return this.getTokenInternal();
	}

	@Override
	public void appendToken(final T token) {
		this.appendTokenInternal(token);
	}

	@Override
	public void resetToken() {
		this.resetTokenInternal();
	}

	@Override
	public int size() {
		return this.states.size();
	}

	@Override
	public T getNext() {
		this.moveNextTokenInternal();
		return this.nextInternal();
	}

	@Override
	public void setNextState(final IState<T> s) {
		this.currentState = s;
	}

	@Override
	public void setNextState(final int index) {
		this.currentState = this.getState(index);
	}

	@Override
	public IState<T> getState(final int index) {
		return this.states.get(index);
	}

	@Override
	public void addVisitor(final ILexerVisitor<T> visitor) {
		if (visitor != null) {
			for (final ILexerVisitor<T> nextVisitor : this.visitors) {
				if (nextVisitor.equals(visitor))
					return;
			}
			this.visitors.add(visitor);
		}
	}

	@Override
	public void removeVisitor(final ILexerVisitor<T> visitor) {
		if (visitor != null) {
			this.visitors.remove(visitor);
		}
	}

	@Override
	public void callVisitor(final int index, final T token) {
		for (final ILexerVisitor<T> nextVisitor : this.visitors) {
			nextVisitor.visit(this, index, token);
		}
	}

	/************************************************************************
	 * PRIVATE
	 ***********************************************************************/

	protected T getInput() {
		return this.input;
	}

	protected void setMachineRunning() {
		this.running = true;
	}

	protected void setMachineStopped() {
		this.running = false;
	}

	protected boolean isMachineRunning() {
		return this.running;
	}

	protected abstract void moveNextTokenInternal();

	protected abstract T getTokenInternal();

	protected abstract T appendTokenInternal(T token);

	protected abstract void resetTokenInternal();

	protected abstract T nextInternal();
}
