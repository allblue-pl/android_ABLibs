package pl.allblue.ablibs.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SfTd on 12/06/2015.
 */
public class Date
{

    static final public long Span_Hour  =  3600;
    static final public long Span_Day   =  86400;


    static private int UTCOffset = 0;
    static private String Date_Format = null;


    static public long getDay(long time) {
        return Date.getDay_UTC(time) - Date.UTCOffset * Date.Span_Hour;
    }

    static public long getDay() {
        return Date.getDay(Date.getTime());
    }

    static public long getDay_UTC(long time) {
        return (long)(Math.floor((double)time / Date.Span_Day) *
                Date.Span_Day);
    }

    static public long getDay_UTC() {
        return Date.getDay_UTC(Date.getTime());
    }

    static public long getTime() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                .getTimeInMillis() / 1000l;
    }

    static public long getTime_Rel(long time) {
        return time + Date.UTCOffset * Date.Span_Hour;
    }

    static public long getTime_Rel() {
        return Date.getTime_Rel(Date.UTCOffset);
    }

    static public long getTime_RelNeg() {
        return Date.getTime_Rel(-Date.UTCOffset);
    }

    static public String Format_Date(Long time)
    {
        if (time == null)
            return "-";

        return Date.Format_Date(new java.util.Date(time * 1000));
    }

    static public String Format_Date(java.util.Date date)
    {
        if (date == null)
            return "-";

        DateFormat date_format = new SimpleDateFormat(Date.Date_Format);
        date_format.setTimeZone(TimeZone.getTimeZone("UTC"));

        return date_format.format(date);
    }

    static public void SetFormat(String date_format)
    {
        Date.Date_Format = date_format;
    }

}
