package com.example.mankind;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

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
