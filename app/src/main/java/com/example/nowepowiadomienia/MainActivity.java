package com.example.nowepowiadomienia;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private static final String CHANNEL_ID = "My_channel_id";
    private Button guzik;
    private Button guzik_2;
    private Button guzik_3;
    private Bitmap bitmap;
    private Bitmap bitmap_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        guzik = findViewById(R.id.guzik1);
        guzik.setOnClickListener( v -> {
            sendNotification("Tytuł", "Wiadomość", "1",CHANNEL_ID,  1, "", 0);
        });
        guzik_2 = findViewById(R.id.guzik2);
        guzik_2.setOnClickListener( v -> {
            sendNotification("Tytuł", "Wiadomość","1", CHANNEL_ID, 2, "", R.drawable.pomarancza);
        });
        guzik_3 = findViewById(R.id.guzik3);
        guzik_3.setOnClickListener( v -> {
            sendNotification("Tytuł", "Wiadomość","1", CHANNEL_ID,  3, "Druga czesc wiadomosci", 0);
        });


    }

    void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            CharSequence name = "Kanał Powiadomień";
            String description = "Opis Kanału Powiadomień";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notifiactionManager = getSystemService(NotificationManager.class);
            notifiactionManager.createNotificationChannel(channel);
        }
    }

    void sendNotification(String title, String message,String CHANNEL_NAME, String CHANNEL_ID, int style,String message_2,int obrazek){
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jablko);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }


        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        if(style ==1){

                    builder.setSmallIcon(R.drawable.jablko)
                    .setContentTitle(title)

                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

        }
        else if(style==2){
            bitmap_2 = BitmapFactory.decodeResource(getResources(), obrazek);

                    builder.setSmallIcon(R.drawable.jablko)
                    .setContentTitle(title)
                    .setContentText(message)

                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap_2))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

        }
        else{

                    builder.setSmallIcon(R.drawable.jablko)
                    .setContentTitle(title)

                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine(message)
                            .addLine(message_2)
                    )


                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

    }




}