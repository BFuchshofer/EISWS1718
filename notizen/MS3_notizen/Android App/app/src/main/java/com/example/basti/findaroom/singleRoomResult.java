package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Basti on 11.01.2018.
 */

public class singleRoomResult extends AppCompatActivity {


    TextView field_Room;
    TextView field_Time;
    Button cancelBtn;
    Button bookBtn;

    public CountDownTimer timer;
    public long timeToUpdate = 10000; // 10 Sekunden

    private static final int READ_BLOCK_SIZE = 100;

    JSONArray fileData;
    public String beaconFileName = "beaconData.json";
    public static JSONObject jsonBody = singleRoom.getData();
    public static JSONObject bookedRes;

    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;


    protected static final String checkData = "STATUS in Update";

    public String url = "http://192.168.2.101:5669";


    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    public void singleRoomBookedActivity() {
        Intent singleRoomBookedActivity = new Intent(this, singleRoomBooked.class);;
        startActivity(singleRoomBookedActivity);
    }

    public static JSONObject getBookedRes() {
        return bookedRes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room_result);
        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_time);


        try {
            field_Room.setText(jsonBody.getString("beacon"));
            field_Time.setText(jsonBody.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        bookBtn = (Button) findViewById(R.id.btn_book);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                jsonBody.remove("token");
                try {
                    jsonBody.put("token", "BOOK");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bookRequest(jsonBody);
            }
        });


        startTimer();
    }


    public void startTimer() {

        timer = new CountDownTimer(timeToUpdate, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("remainingTimeToUpdate ", "" + l / 1000 + "seconds");
            }

            @Override
            public void onFinish() {
                try {
                    fileData = readFile(beaconFileName);
                    Log.i("filedata", "" + fileData);
                    Log.i("filedata", "" + jsonBody);
                    // Prüfe ob ein aktueller Beacon vorliegt
                    if (!fileData.getJSONObject(0).getString("uuid").equals(jsonBody.getString("beacon"))) {
                        jsonBody.remove("beacon");
                        jsonBody.put("beacon", fileData.getJSONObject(0).getString("uuid"));
                        jsonBody.remove("token");
                        jsonBody.put("token", "UPDATE");
                        updateRequest(jsonBody);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.start();
            }
        };
        timer.start();
    }

    public void updateRequest(JSONObject object) {

        Log.i("UPDATE REQUEST", "" + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url+"/room", object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


                  //Response liefert Raumvorschlag und ein Token mit das diesen Request im Server identifiziert.
                  //Bei Raumupdate wird der Token mit dem neuen Beacon an den Server geschickt und als Response falls verfügbar ein neuer Raum ausgegeben.

                Log.i(checkData, "Response: " + response.toString());
                try {
                    field_Room.setText("" + response.getString("beacon"));
                    field_Time.setText(response.getString("token"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i(checkData, "Error in Request");
            }
        });
        mRequestQueuePOST.add(postJsonRequest);

    }

    public void bookRequest(JSONObject object) {

        Log.i("BOOK REQUEST", "Book: " + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);
        // TODO
        // andere url? Dynamisch über query?

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url+"/room", object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {


                //Response liefert Raumvorschlag und ein Token mit das diesen Request im Server identifiziert.
                //Bei Raumupdate wird der Token mit dem neuen Beacon an den Server geschickt und als Response falls verfügbar ein neuer Raum ausgegeben.

                Log.i(checkData, "Response booking: " + response.toString());
                bookedRes = response;
                singleRoomBookedActivity();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i(checkData, "Error in Request");
            }
        });
        mRequestQueuePOST.add(postJsonRequest);

    }


    // Gibt alle Daten aus dem TextFile aus
    public JSONArray readFile(String fName) {

        try {
            FileInputStream fileIn = getApplicationContext().openFileInput(fName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String stringData = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                stringData += readstring;
            }
            InputRead.close();
            if (stringData.length() != 0) {
                JSONArray dataArray = new JSONArray(stringData);
                return dataArray;
            } else {

                JSONArray dataArray = new JSONArray();
                return dataArray;
            }

        } catch (Exception e) {
            JSONArray errorArray = new JSONArray();
            e.printStackTrace();
            Log.i(checkData, "readfile error: " + errorArray.toString());
            return errorArray;
        }

    }

}
