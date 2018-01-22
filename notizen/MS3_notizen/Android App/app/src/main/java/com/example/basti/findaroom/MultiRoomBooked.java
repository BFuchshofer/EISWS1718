package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class MultiRoomBooked extends AppCompatActivity {

    TextView field_Room, field_Time, textView, textView2, textView5;
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
        textView5 = (TextView) findViewById(R.id.textView5);

        field_Room.setText("3.213\n3.215");
        field_Time.setText("59:58 Minuten");
        textView.setText("Die Räume");
        textView2.setText("sind noch");
        textView5.setText("Sollten Sie die Räume nicht mehr benötigen, brechen Sie die Buchung bitte ab. Sollten Sie mehr Zeit in diesen Räumen benötigen, können Sie die Raumbuchung verlängern.");

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
