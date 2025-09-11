package pl.allblue.ablibs.widgets;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pl.allblue.ablibs.R;

public class ABMessages {
    static public void showConfirmation(Activity activity, String title,
            String message, String positiveText, String negativeText,
            OnConfirmationResult callback) {
        activity.runOnUiThread(() -> {
            AlertDialog ad = new MaterialAlertDialogBuilder(activity)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, (dialog, which) -> {
                    callback.onResult(true);
                })
                .setNegativeButton(negativeText, (dialog, which) -> {
                    callback.onResult(false);
                })
                .setIcon(R.drawable.confirmation)
                .show();

            ad.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);
        });
    }

    static public void showMessage_Failure(Activity activity, String title,
            String message, OnAfterMessage onAfterMessage) {
        activity.runOnUiThread(() -> {
            AlertDialog ad = new MaterialAlertDialogBuilder(activity)
                .setTitle(title)
                .setMessage(message)
                .setOnCancelListener(dialog -> {
                    if (onAfterMessage != null)
                        onAfterMessage.onAfterMessage();
                })
                .setNeutralButton(R.string.text_ok, (dialog, which) -> {

                    if (onAfterMessage != null)
                        onAfterMessage.onAfterMessage();
                })
                .setIcon(R.drawable.error)
                .show();
        });
    }

    static public void showMessage_Failure(Activity activity, @StringRes int titleRes,
                                           @StringRes int messageRes, OnAfterMessage onAfterMessage) {
        ABMessages.showMessage_Failure(activity, activity.getString(titleRes),
                activity.getString(messageRes), onAfterMessage);
    }

    static public void showMessage_Failure(Activity activity, String title,
            String message) {
        ABMessages.showMessage_Failure(activity, title, message, null);
    }

    static public void showMessage_Failure(Activity activity, @StringRes int titleRes,
                                           @StringRes int messageRes) {
        ABMessages.showMessage_Failure(activity, titleRes, messageRes, null);
    }

    static public void showMessage_Success(Activity activity, String title,
                                           String message, OnAfterMessage onAfterMessage) {
        activity.runOnUiThread(() -> {
            AlertDialog ad = new MaterialAlertDialogBuilder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setOnCancelListener(dialog -> {
                        if (onAfterMessage != null)
                            onAfterMessage.onAfterMessage();
                    })
                    .setNeutralButton(R.string.text_ok, (dialog, which) -> {

                        if (onAfterMessage != null)
                            onAfterMessage.onAfterMessage();
                    })
                    .setIcon(R.drawable.success)
                    .show();
        });
    }

    static public void showMessage_Success(Activity activity, @StringRes int titleRes,
                                           @StringRes int messageRes, OnAfterMessage onAfterMessage) {
        ABMessages.showMessage_Success(activity, activity.getString(titleRes),
                activity.getString(messageRes), onAfterMessage);
    }

    static public void showMessage_Success(Activity activity, String title,
                                           String message) {
        ABMessages.showMessage_Success(activity, title, message, null);
    }

    static public void showMessage_Success(Activity activity, @StringRes int titleRes,
                                           @StringRes int messageRes) {
        ABMessages.showMessage_Success(activity, titleRes, messageRes, null);
    }

    static public void showMessage_Warning(Activity activity, String title,
            String message, OnAfterMessage onAfterMessage) {
        activity.runOnUiThread(() -> {
            AlertDialog ad = new MaterialAlertDialogBuilder(activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setOnCancelListener(dialog -> {
                        if (onAfterMessage != null)
                            onAfterMessage.onAfterMessage();
                    })
                    .setNeutralButton(R.string.text_ok, (dialog, which) -> {
                        if (onAfterMessage != null)
                            onAfterMessage.onAfterMessage();
                    })
                    .setIcon(R.drawable.warning)
                    .show();
        });
    }

    static public void showMessage_Warning(Activity activity, String title,
            String message) {
        ABMessages.showMessage_Warning(activity, title, message, null);
    }

    public interface OnAfterMessage {
        void onAfterMessage();
    }

    public interface OnConfirmationResult {
        void onResult(boolean result);
    }

}
