package pl.allblue.util;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SfTd on 12/06/2015.
 */
public class Date
{

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

}
