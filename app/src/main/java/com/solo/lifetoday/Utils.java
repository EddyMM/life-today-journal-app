package com.solo.lifetoday;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author eddy.
 */

public class Utils {

    /**
     * Returns a date formatted to familiar pattern
     * @param date Date instance
     * @return Formatted date
     */
    public static String getFormattedDate(Date date) {
        SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        sf.applyLocalizedPattern("E, MMM dd, yyyy");
        return sf.format(date);
    }

    public static String getFormattedDateWithTime(Date date) {
        SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        sf.applyLocalizedPattern("E, MMM dd, yyyy K:m a");
        return sf.format(date);
    }
}
