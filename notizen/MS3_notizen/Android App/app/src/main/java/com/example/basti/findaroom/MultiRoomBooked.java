package com.example.basti.findaroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Basti on 17.01.2018.
 */

public class MultiRoomBooked extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booked); // Activity wird mehrfach verwendet da sie das gleiche präsentiert
    }

}
