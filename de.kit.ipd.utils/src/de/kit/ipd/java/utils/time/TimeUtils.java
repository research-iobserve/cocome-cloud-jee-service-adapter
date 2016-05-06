package de.kit.ipd.java.utils.time;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author unknown
 *
 */
public final class TimeUtils {

	private TimeUtils() {}

	/**
	 * The expected format is dd-mm-yyyy
	 *
	 * @param date
	 * @return null if the format was not okay!
	 * @throws Exception
	 *             when something went wrong. Wrong can be the format dd.mm.yyyy
	 */
	public static Date convertToDateObject(final String strdate) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.ENGLISH);
		Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(strdate);
			calendar.setTime(date);
		} catch (final ParseException e) {
			e.printStackTrace();
		}
		return calendar.getTime();
	}

	/**
	 * Convert the given date to a well formatted string like dd.mm.yyyy
	 *
	 * @param date
	 * @return
	 */
	public static String convertToStringDate(final Date date) {
		final Format formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}

	/**
	 * Create an calendar, get the present time and date and return it as a string.
	 *
	 * @return string representing the current time and date
	 */
	public static String getTime() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.ENGLISH);
		final Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		return formatter.format(calendar.getTime());
	}

}
