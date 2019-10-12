package com.example.mankind;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The type Date util.
 */
public class DateUtil {

    /**
     * Format date string.
     *
     * @param str the str
     * @return the string
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }
}
