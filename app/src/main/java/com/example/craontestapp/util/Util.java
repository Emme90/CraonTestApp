package com.example.craontestapp.util;

import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static String convertTimeToDuration(int time) {
        String duration;
        int hours = time / 60;
        int minutes = (time - hours * 60);
        if (time % 60 != 0) {
            duration = hours + "h " + minutes + "min";
            return duration;
        } else {
            duration = hours + "h";
            return duration;
        }
    }

    public static String formattingDate(String date) {
        String startDate = date;
        String finishDate = "";
        SimpleDateFormat startDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date mDate = startDateFormat.parse(startDate);
            finishDate = simpleDateFormat.format(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finishDate;
    }
}
