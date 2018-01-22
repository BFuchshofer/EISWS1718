package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class SingleRoomBooked extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;

    public CountDownTimer remainingTimeCountDown;
    public String url;
    public String path;
    public String requestFileName;
    public String beaconFileName;
    public String userFileName;
    private TextView field_Room, field_Time;
    private Button cancelBtn, extendBtn;
    private ProgressDialog progress;

    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private String status;
    private JSONObject reqData;
    private JSONArray beaconData;
    private JSONObject userData;

    // Leitet auf den Homscreen zurück
    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_booked);

        progress = new ProgressDialog(this);

        url = getString(R.string.serverURL);
        path = getString(R.string.room);
        beaconFileName = getString(R.string.beaconFile);
        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        status = getString(R.string.status);

        readFile(requestFileName);

        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        extendBtn = (Button) findViewById(R.id.btn_extend);


        try {
            // TODO
            // text anpassen
            field_Room.setText(reqData.getString("room_id"));
            setDynamicEndTime(reqData.getLong("remainingTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remainingTimeCountDown.cancel();
                reqData.remove("token");
                try {
                    reqData.put("token", "CANCEL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                reqData.remove("type");
                postRequest(reqData);
            }
        });

        extendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // Aus requestData.json lesen ( mit readFile(requestDataFileName) ) und an den Server schicken
            }
        });
    }

    public void progressScreen(String title, String msg) {
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(true);
        progress.show();
    }

    public void postRequest(JSONObject object) {
        Log.i(status, "POST REQUEST: " + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);

        try {
            if (reqData.getString("token").contains("CANCEL")) {
                progressScreen("Abbruch", "Raum wird wieder freigegeben...");
            } else if (reqData.getString("token").contains("EXTEND")) {
                progressScreen("Verlängern", "Raumbuchung wird verlängert...");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url + path, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (reqData.getString("token").contains("EXTEND")) {
                        reqData = response;
                        progress.dismiss();
                        // TODO
                        // Seite aktualisieren?
                        response.put("type", "singleRoom");
                        writeFile(requestFileName, reqData);

                    }
                    if (reqData.getString("token").contains("CANCEL")) {

                        if (response.getLong("remainingTime") != 0) {
                            remainingTimeCountDown.cancel();
                        }
                        JSONObject clear = new JSONObject();
                        writeFile(requestFileName, clear);
                        progress.dismiss();
                        homeScreen();
                    }
                } catch (JSONException e) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Fehler: " + error, Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.i(status, "Error in Request");
            }
        });

        mRequestQueuePOST.add(postJsonRequest);

    }

    // Um die verbleibende Zeit bis zum Ablauf der Buchung anzuzeigen
    private void setDynamicEndTime(long time) {
        long endTime = time - System.currentTimeMillis();
        remainingTimeCountDown = new CountDownTimer(endTime, 1000) {
            @Override
            public void onTick(long l) {
                int beforeDecimal = (int) l/1000/60;
                int afterDecimal = (int)(l/1000) - (beforeDecimal*60) ;
                field_Time.setText(beforeDecimal + ":" + afterDecimal + " Minuten");
            }

            @Override
            public void onFinish() {
                field_Time.setText("Buchung abgelaufen!");
            }
        };
        remainingTimeCountDown.start();
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(String fName, JSONObject data) {

        try {
            FileOutputStream output = openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            writeOnOutput.write(data.toString());
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gibt alle Daten aus dem TextFile aus
    public void readFile(String fName) {

        try {
            FileInputStream fileIn = openFileInput(fName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String stringData = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                stringData += readstring;
            }
            InputRead.close();

            if (fName.contains(userFileName)) {
                if (stringData.length() != 0) {
                    JSONObject dataObject = new JSONObject(stringData);
                    Log.i(status, "readfile: " + dataObject);
                    userData = dataObject;
                } else {
                    JSONObject dataObject = new JSONObject();
                    Log.i(status, "readfile: " + dataObject);
                    userData = dataObject;
                }
            }
            if (fName.contains(requestFileName)) {
                if (stringData.length() != 0) {
                    JSONObject dataObject = new JSONObject(stringData);
                    Log.i(status, "readfile: " + dataObject);
                    reqData = dataObject;
                } else {
                    JSONObject dataObject = new JSONObject();
                    Log.i(status, "readfile: " + dataObject);
                    reqData = dataObject;
                }
            }
            if (fName.contains(beaconFileName)) {
                if (stringData.length() != 0) {
                    JSONArray dataArray = new JSONArray(stringData);
                    beaconData = dataArray;
                } else {
                    JSONArray dataArray = new JSONArray();
                    beaconData = dataArray;
                }
            }
        } catch (Exception e) {
            JSONObject errorArray = new JSONObject();
            e.printStackTrace();
            Log.i(status, "readfile error: " + errorArray.toString());
        }

    }
}
