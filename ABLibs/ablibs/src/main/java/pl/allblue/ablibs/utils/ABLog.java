package pl.allblue.ablibs.utils;

import android.util.Log;

public class ABLog
{

    static public void Test(String msg, Exception exception)
    {
        Log.d("ABLog", "[Test] " + msg);
        Log.w("ABLog", exception);
    }

    static public void Test(String msg)
    {
        ABLog.Test(msg, null);
    }

}
