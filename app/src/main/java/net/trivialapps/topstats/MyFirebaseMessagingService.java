package net.trivialapps.topstats;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.FLAG_AUTO_CANCEL;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static  String CANAL = "MyNotifCanal";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String myTitle = remoteMessage.getNotification().getTitle();
        String myMessage = remoteMessage.getNotification().getBody();
        // creer une notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CANAL);
        notificationBuilder.setContentTitle(myTitle);
        notificationBuilder.setContentTitle(myMessage);
        notificationBuilder.setSmallIcon(R.mipmap.topstatt);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notificationBuilder.setContentIntent(contentIntent);
        notificationBuilder.setAutoCancel(true);

        //envoyer la notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channeId = getString(R.string.notification_channel_id);
            String channeTitle = getString(R.string.notification_channel_title);
            String channeDesctription = getString(R.string.notification_channel_desc);
            NotificationChannel channel = new NotificationChannel(channeId,channeTitle,NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channeDesctription);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channeId);
        }

        notificationManager.notify(1,notificationBuilder.build());


    }

}
