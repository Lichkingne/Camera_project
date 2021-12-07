package it.mirea.NikitaM;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private static final String NOTIFICATION_CHANNEL_ID = "Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionStatus = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.CAMERA},
                REQUEST_CODE_PERMISSION_CAMERA);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                setCameraOutput();createNotificationChannel();showNotification();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = ("Channel");
        String description= ("Description");
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new
                NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager =getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }


    private void showNotification() {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setContentTitle("Практическая работа 4")
                .setContentText("Мошков Никита ИКБО-25-20")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        Random random = new Random();
        notificationManager.notify(random.nextInt(), builder.build());
    }

    private void setCameraOutput(){
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCameraOutput();Cam();
            }
        });
    }



    private void Cam(){
        Camera camera = Camera.open();
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview();setDisplayOrientation(camera, 90);
    }

    protected void setDisplayOrientation(Camera camera, int angle){
        Method downPolymorphic;
        try
        {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[] { angle });
        }
        catch (Exception e1)
        {
        }
    }

}
;// запускаем превью камеры.

