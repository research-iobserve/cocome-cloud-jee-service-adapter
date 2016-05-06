package de.kit.ipd.java.utils.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author unknown
 *
 */
public final class Utilities {

	/** this class provides stateless functions and must not be instantiated. */
	private Utilities() {}

	/**
	 * Reads data from a given input stream. The input stream must be open
	 * and it is not closed by this function.
	 *
	 * @param input
	 *            the input stream
	 * @return returns the content of the stream or null on error
	 */
	public static String getString(final InputStream input) {
		BufferedInputStream in = null;
		try {
			if (input.available() != -1) {
				in = new BufferedInputStream(input);
				final StringBuilder builder = new StringBuilder();
				byte[] myBytes = new byte[256];
				while (in.read(myBytes) != -1) {
					builder.append(new String(myBytes, "UTF-8"));
					myBytes = null;
					myBytes = new byte[256];
				}
				return builder.toString();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
