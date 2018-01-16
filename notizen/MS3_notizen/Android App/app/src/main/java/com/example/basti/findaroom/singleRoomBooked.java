package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Basti on 16.01.2018.
 */

public class singleRoomBooked extends AppCompatActivity {

    TextView field_Room;
    TextView field_Time;
    Button cancelBtn;
    Button extendBtn;

    public JSONObject bookedRes = singleRoomResult.getBookedRes();


    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room_booked);

        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_time);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        extendBtn = (Button) findViewById(R.id.btn_extend);

        try {
            // TODO
            // angezeigten text ändern
            field_Room.setText(bookedRes.getString("beacon"));
            field_Time.setText(bookedRes.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });

        extendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }
}
