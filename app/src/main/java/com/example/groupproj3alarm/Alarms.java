package com.example.groupproj3alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Alarms extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView iv = (ImageView) findViewById(R.id.addbutton);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Set Time", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       TextView alarms= findViewById(R.id.textView5);
        Calendar c = Calendar.getInstance();
//        c.get(Calendar.HOUR_OF_DAY);
//        c.get(Calendar.MINUTE);
//        c.get(Calendar.SECOND);
        String s= c.getTime().toString();
        String x= c.getTimeZone().getDisplayName().toString();
        Long y= c.getTimeInMillis();
        alarms.setText(s+" "+ x+ " "+ y);
    }


}
