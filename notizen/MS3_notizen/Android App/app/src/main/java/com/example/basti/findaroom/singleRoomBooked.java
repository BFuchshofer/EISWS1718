package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class SingleRoomBooked extends AppCompatActivity {

    TextView field_Room;
    TextView field_Time;
    Button cancelBtn;
    Button extendBtn;

    public JSONObject bookedRes = SingleRoomResult.getBookedRes();

    public CountDownTimer remainingTimeCountDown;

    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booked);

        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_time);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        extendBtn = (Button) findViewById(R.id.btn_extend);

        try {
            // TODO
            // text anpassen
            field_Room.setText(bookedRes.getString("room_id"));
            setDynamicEndTime(bookedRes.getLong("remainingTime"));
            //field_Time.setText(bookedRes.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remainingTimeCountDown.cancel();
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

    // Um die verbleibende Zeit bis zum Ablauf der Reservierung anzuzeigen
    private void setDynamicEndTime(long time) {
        long endTime = time;
        remainingTimeCountDown = new CountDownTimer(endTime, 1000) {
            @Override
            public void onTick(long l) {
                field_Time.setText("" + l / 1000 + " Sekunden");
            }

            @Override
            public void onFinish() {
                field_Time.setText("Buchung abgelaufen!");
            }
        };
        remainingTimeCountDown.start();
    }
}
