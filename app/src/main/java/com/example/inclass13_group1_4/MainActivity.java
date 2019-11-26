package com.example.inclass13_group1_4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button buttonadd;
    ArrayList<Trip> trips = new ArrayList<Trip>();
    private static final int ADD_TRIP_REQ_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonadd = findViewById(R.id.buttonaddTrip);
        buttonadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTrip.class);
                startActivityForResult(intent,ADD_TRIP_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TRIP_REQ_CODE){
            if(resultCode == MainActivity.RESULT_OK){
                String name=data.getStringExtra("name");
                City city = (City) data.getSerializableExtra("city");
                Trip trip = new Trip(name,city);
                trips.add(trip);
                Log.d("demo",trips.toString());
            }
        }
    }
}
