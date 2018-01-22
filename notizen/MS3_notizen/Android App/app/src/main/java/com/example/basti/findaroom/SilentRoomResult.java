package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SilentRoomResult extends AppCompatActivity {

    TextView field_Room, field_Time;
    Button cancelBtn, bookBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_room_result);
        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);

        field_Room.setText("3.214");
        field_Time.setText("9:58 Minuten");

        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        bookBtn = (Button) findViewById(R.id.btn_book);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), StartActivity.class); // back to homescreen
                startActivity(homeScreen);
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), SilentRoomBooked.class); // back to homescreen
                startActivity(nextActivity);
            }


        });
    }
}
