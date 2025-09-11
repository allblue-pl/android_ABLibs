package pl.allblue.ablibs.widgets;

import android.app.Activity;
import android.content.Context;

public class ABToast {
    static public void showMessage(final Activity activity, final CharSequence message) {
        activity.runOnUiThread(() -> {
            Context context = activity.getApplicationContext();
            int duration = android.widget.Toast.LENGTH_LONG;

            android.widget.Toast toast = android.widget.Toast.makeText(
                    context, message, duration);
            toast.show();
        });
    }

}
