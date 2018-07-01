package com.solo.lifetoday;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author eddy.
 */

public class Utils {

    /**
     * Returns a date formatted to familiar pattern
     *
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
        sf.applyLocalizedPattern("E, MMM dd, yyyy KK:mm a");
        return sf.format(date);
    }

    /**
     * Converts an iterable to a list
     *
     * @param iterable Iterable instance
     * @param <E>      Type of iterable
     * @return List of tye <E>
     */
    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
}
