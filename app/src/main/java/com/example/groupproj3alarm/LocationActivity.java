package com.example.groupproj3alarm;

import android.accessibilityservice.AccessibilityService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Thread.*;

public class LocationActivity extends AppCompatActivity implements SensorEventListener {
    private  final String TAG = getClass().getSimpleName();
    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private double mAccel;
    private double mAccelCurrent;
    private double mAccelLast;
    private long lockTime=2000000;
    private boolean sensorRegistered = false;
    private AccessibilityService context;
    private boolean locationTrigger=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sensorMan= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer= sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMan.registerListener(LocationActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);



        FloatingActionButton fab =findViewById(R.id.localarm);
        final TextView tv= findViewById(R.id.textView3);
        final EditText timer= findViewById(R.id.timer);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar.make(view,"Adding Location Alarm", Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
                    //read time from time setter and make location true after that time
                String content = timer.getText().toString();//gets you the contents of edit text

                   locationTrigger= true;
                 lockTime= 120000;//default time before Checking location (2 minutes)
               boolean flag = false;
                if (!content.isEmpty()){
                    for (int i=0; i<content.length();i++) {
                        char c = content.charAt(i);
                        if (c == ':') flag = true;
                    }

                    if(flag){
                        String[] parts = content.split(":");
                String min = parts[0];
                String sec= parts[1];
                long min1= Long.parseLong(min);
                min1*=60000;
                long sec1= Long.parseLong(sec);
                sec1*=1000;
               lockTime= min1+sec1;}
                    else{
                        long min= Long.parseLong(content);
                             min *=60000;
                    }
                }
               else{ content= "2";}
                tv.setText("Location check after "+content+" minutes");
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int hitCount = 0;
    private double hitSum = 0;
    private double hitResult = 0;

    private  int SAMPLE_SIZE = 10;// change this sample size as you want, higher is more precise but slow measure. (was 50...10... can mess with_)
    private final double THRESHOLD = 0.2; // change this threshold as you want, higher is more spike movement





@Override
    public void onSensorChanged(SensorEvent event) {
    // Log.d(TAG,"Change X: "+ event.values[0]+"Y: "+event.values[1]+"Z: "+event.values[2]); //logs x, y, z cord.
    if (locationTrigger=false ) {
        SAMPLE_SIZE = 10;
    }

//            Log.d(TAG, "onSensorChanged: Waiting"+lockTime+" Milliseconds");
//        try {
//            sleep(lockTime);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values.clone();
            // Shake detection
            double x = mGravity[0];
            double y = mGravity[1];
            double z = mGravity[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = Math.sqrt(x * x + y * y + z * z);
            double delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            if (hitCount <= SAMPLE_SIZE) {
                hitCount++;
                hitSum += Math.abs(mAccel);
            } else {
                hitResult = hitSum / SAMPLE_SIZE;

                Log.d(TAG, String.valueOf(hitResult));

                if (hitResult > THRESHOLD) {
                    Log.d(TAG, "Moved");// logging purposes
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Way to Move! \nAlarm Cancelled", Snackbar.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "Not moving");//if stop walking, alarm goes off
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Get Up and Move! ", Snackbar.LENGTH_INDEFINITE).show();
                }

                hitCount = 0;
                hitSum = 0;
                hitResult = 0;
            }
        }



}

}
