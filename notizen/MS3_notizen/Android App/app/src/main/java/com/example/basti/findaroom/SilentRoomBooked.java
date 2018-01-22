package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class SilentRoomBooked extends AppCompatActivity {

    TextView field_Room, field_Time, textView, textView2, textView4;
    Button cancelBtn, extendBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booked);



        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        extendBtn = (Button) findViewById(R.id.btn_extend);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView4 = (TextView) findViewById(R.id.textView4);

        textView.setText("Im Raum");
        field_Room.setText("3.214");
        field_Time.setText("59:58 Minuten");
        textView2.setText("ist noch für");
        textView4.setText("ein Arbeitsplatz für Sie gebucht.");


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), StartActivity.class); // back to homescreen
                startActivity(homeScreen);
            }
        });

        extendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.extendToast), Toast.LENGTH_LONG).show();
            }
        });
    }


}
