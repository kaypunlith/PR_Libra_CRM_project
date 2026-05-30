package com.ut.nlSystemAPi.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {

    public static String date() {
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public static String time() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
