package pl.allblue.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Confirmation
{

    static public void ShowDialog(Context context, String message,
            String yes_text, String no_text, final OnConfirmationResultListener listener)
    {
        DialogInterface.OnClickListener dialog_listener =
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        listener.onResult(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        listener.onResult(false);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(yes_text, dialog_listener)
            .setNegativeButton(no_text, dialog_listener);

        builder.show();
    }


    public interface OnConfirmationResultListener
    {
        void onResult(boolean result);
    }

}
