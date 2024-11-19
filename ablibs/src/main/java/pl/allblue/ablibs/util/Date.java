package pl.allblue.ablibs.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by SfTd on 12/06/2015.
 */
public class Date
{

    static final public long SPAN_HOUR  =  3600;
    static final public long SPAN_DAY   =  86400;

    static private String date_Format = null;
    static private TimeZone timeZone = TimeZone.getTimeZone("UTC");

    static public long getDay(long time) {
        long offset = getUTCOffset_Seconds(time);

        return (long)Math.floor((time + offset) /
                Date.SPAN_DAY) * Date.SPAN_DAY - offset;
    }

    static public long getDay() {
        return Date.getDay(Date.getTime());
    }

    static public long getDay_UTC(long time) {
        return (long)Math.floor(time / Date.SPAN_DAY) * Date.SPAN_DAY;
    }

    static public long getDay_UTC() {
        return Date.getDay(Date.getTime());
    }

    static public long getTime() {
        return Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                .getTimeInMillis() / 1000l;
    }

    static public long getTime_WithUTCOffset() {
        long time = getTime();
        return time + getUTCOffset_Seconds(time);
    }

    static public long getTime_WithNegativeUTCOffset() {
        long time = getTime();
        return time - getUTCOffset_Seconds(time);
    }

    static public long getUTCOffset(long time) {
        return getUTCOffset_Seconds(time) / SPAN_HOUR;
    }

    static public long getUTCOffset_Seconds(long time) {
        return timeZone.getOffset(time * 1000l) / 1000l;
    }

    static public String format_Date(Long time) {
        if (time == null)
            return "-";

        DateFormat dateFormat = new SimpleDateFormat(date_Format);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new java.util.Date(time * 1000l));
    }

    static public String format_Date_UTC(Long time) {
        if (time == null)
            return "-";

        DateFormat dateFormat = new SimpleDateFormat(date_Format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new java.util.Date(time * 1000l));
    }

    static public void setFormat_Date(String date_format) {
        Date.date_Format = date_format;
    }

    static public void setTimeZone(String timeZone) {
        Date.timeZone = TimeZone.getTimeZone(timeZone);
    }

    static public long strToTime_Date_UTC(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(date_Format);
        try {
            java.util.Date date = dateFormat.parse(dateStr);
            return (date.getTime() + TimeZone.getDefault()
                    .getRawOffset()) / 1000l;
        } catch (ParseException e) {
            Log.e("Date", "Cannot parse date string.", e);
            return 0;
        }
    }
}
