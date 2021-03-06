package com.example.appexperto2020.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.example.appexperto2020.R;

public class NotificationUtils {


    public static  int consecutive = 1;
    public static int consecutiveS  = 1;

    public static void createNotification(Context context, String mensaje){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID,Constants.CHANNEL_NAME,Constants.CHANNEL_IMPORTANCE);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,Constants.CHANNEL_ID)
                .setContentTitle("Nuevo Mensaje")
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.expert);
        manager.notify(consecutive,builder.build());
        consecutive++;
    }

    public static void createNotificationServices(Context context, String mensaje){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Constants.CHANNEL_ID_SERVICE,Constants.CHANNEL_NAME_SERVICE,Constants.CHANNEL_IMPORTANCE);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,Constants.CHANNEL_ID_SERVICE)
                .setContentTitle("Nuevo Servicio")
                .setContentText(mensaje)
                .setSmallIcon(R.drawable.expert);
        manager.notify(consecutive,builder.build());
        consecutiveS++;
    }
}
