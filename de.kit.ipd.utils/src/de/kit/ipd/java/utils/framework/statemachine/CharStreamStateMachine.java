package de.kit.ipd.java.utils.framework.statemachine;

/**
 *
 * @author unknown
 *
 */
public class CharStreamStateMachine extends AbstractStateMachine<CharSequence> {

	/************************************************************************
	 * FIELDS
	 ***********************************************************************/

	private int pointer = 0;

	private char nextChar;

	private final StringBuilder builder = new StringBuilder();

	/** Empty constructor, requested by checkstyle. */
	public CharStreamStateMachine() {}

	@Override
	protected void moveNextTokenInternal() {
		this.nextChar = this.input.charAt(this.pointer);
		this.pointer++;
		if (this.pointer >= this.input.length()) {
			this.setMachineStopped();
		}
	}

	@Override
	protected CharSequence getTokenInternal() {
		return this.builder.toString();
	}

	@Override
	protected CharSequence appendTokenInternal(final CharSequence token) {
		return this.builder.append(token);
	}

	@Override
	protected void resetTokenInternal() {
		this.builder.delete(0, this.builder.length());
	}

	@Override
	protected CharSequence nextInternal() {
		return String.valueOf(this.nextChar);
	}

}
