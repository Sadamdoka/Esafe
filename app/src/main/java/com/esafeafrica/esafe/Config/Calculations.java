package com.esafeafrica.esafe.Config;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Calculations {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    private static final SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
    private static String result;

    public static String calTotal(String cost, int dff) {
        int a = Integer.valueOf(cost);
        int Total = a * dff;
        result = String.valueOf(Total);
        return result;
    }

    public static int dffDate(String a, String b) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date d1 = dateFormat.parse(a);
        Date d2 = dateFormat.parse(b);
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int c = (int) (long) diff;
        Log.d("Result", String.valueOf(c));
        return c;
    }

    public static int dffHour(String a, String b) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date d1 = timeformat.parse(a);
        Date d2 = timeformat.parse(b);
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        int c = (int) (long) diff;
        return c;
    }

    public static int addperiod(String a, String b) {
        int p = Integer.valueOf(a);
        int e = Integer.valueOf(b);
        int c = p + e;
        return c;
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        result = dateFormat.format(calendar.getTime());
        return result;
    }

    public static String addDay(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, i);
        cal.getTime();
        result = dateFormat.format(cal.getTime());
        return result;

    }

    public static String addMonth(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, i);
        return result = dateFormat.format(cal.getTime());
    }

    public static String addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return result = dateFormat.format(cal.getTime());
    }

    public static String addHours(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, i);
        return result = timeformat.format(cal.getTime());
    }

    public static Toast ConstructToast(Context context, String info) {
        return Toast.makeText(context, info, Toast.LENGTH_SHORT);
    }

    public static String Replace(String s) {
        s = s.trim();
        s = s.replaceAll("%20", " ");
        return s;
    }

    public static int ToInt(String s) {
        int a = Integer.valueOf(s);
        return a;
    }

}
