package pl.allblue.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SfTd on 12/06/2015.
 */
public class Date
{

    static private String Date_Format = null;

    static public long GetMillis(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month, day, 0, 0, 0);

        return calendar.getTimeInMillis() / 1000 * 1000;
    }

    static public long GetSeconds(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.set(year, month, day, 0, 0, 0);

        return calendar.getTimeInMillis() / 1000;
    }

    static public String Format_Date(java.util.Date date)
    {
        DateFormat date_format = new SimpleDateFormat(Date.Date_Format);
        date_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return date_format.format(date);
    }

    static public void SetFormat(String date_format)
    {
        Date.Date_Format = date_format;
    }

}
