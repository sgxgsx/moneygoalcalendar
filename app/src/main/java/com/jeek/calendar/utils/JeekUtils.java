package com.jeek.calendar.utils;

import com.jeek.calendar.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jimmy on 2016/10/10 0010.
 */
public class  JeekUtils {

    public static String timeStamp2Time(long time) {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(time));
    }

}
