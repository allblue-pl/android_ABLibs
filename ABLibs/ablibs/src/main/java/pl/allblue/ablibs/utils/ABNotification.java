package pl.allblue.ablibs.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class ABNotification
{

    private Context context = null;
    public NotificationCompat.Builder builder = null;

    /**
     *
     * @param context
     * @param channelId
     * @param channelName
     * @param channelImportance from 0 to 5 (3 = default)
     */
    public ABNotification(Context context, String channelId,
            String channelName, int channelImportance)
    {
        this.context = context;
        this.builder = new NotificationCompat.Builder(context, channelId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelName, channelImportance);
            NotificationManager notificationManager = context.getSystemService(
                    NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void hide(int notificationId)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                this.context);
        notificationManager.cancel(notificationId);
    }

    public void show(int notificationId)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(
                this.context);
        notificationManager.notify(notificationId, this.builder.build());
    }

}
