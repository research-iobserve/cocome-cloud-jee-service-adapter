package de.kit.ipd.java.utils.strings;

/**
 *
 * @author unknown
 *
 */
public final class StringUtils {

	private StringUtils() {}

	public static String firstUpper(final String text) {
		return text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
	}
}
