package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
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


public class SingleRoomResult extends AppCompatActivity {


    private static final int READ_BLOCK_SIZE = 100;
    private CountDownTimer timer;
    private long timeToUpdate = 10000; // 10 Sekunden
    private CountDownTimer remainingTimeCountDown;
    private String beaconFileName;
    private String requestFileName;
    private String userFileName;
    private String url;
    private String path;
    private JSONObject userData = new JSONObject();
    private JSONArray beaconData = new JSONArray();
    private JSONObject reqData = new JSONObject();
    private TextView field_Room, field_Time;
    private Button cancelBtn, bookBtn;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private String status;
    private ProgressDialog progress;

    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    public void roomBookedActivity() {
        Intent singleRoomBookedActivity = new Intent(this, SingleRoomBooked.class);
        startActivity(singleRoomBookedActivity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room_result);
        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);

        progress = new ProgressDialog(this);

        url = getString(R.string.serverURL);
        path = getString(R.string.room);
        beaconFileName = getString(R.string.beaconFile);
        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        status = getString(R.string.status);

        readFile(requestFileName);

        try {
            field_Room.setText(reqData.getString("room_id"));
            setDynamicEndTime(reqData.getLong("remainingTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        bookBtn = (Button) findViewById(R.id.btn_book);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    reqData.remove("token");
                    reqData.put("token", "CANCEL");
                    reqData.remove("type");
                    postRequest(reqData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.cancel();
                remainingTimeCountDown.cancel();
                try {
                    reqData.remove("token");
                    reqData.put("token", "BOOK");
                    reqData.remove("type");
                    postRequest(reqData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        startTimer();
    }


    public void startTimer() {

        timer = new CountDownTimer(timeToUpdate, 1000) {
            @Override
            public void onTick(long l) {
                Log.i(status, "Remaining time to Update: " + l / 1000 + "seconds");
            }

            @Override
            public void onFinish() {
                try {
                    readFile(beaconFileName);
                    // Prüfe ob ein aktueller Beacon vorliegt
                    if (beaconData.length() != 0)
                    if (beaconData.getJSONObject(0).has("uuid")) {
                        if (!beaconData.getJSONObject(0).getString("uuid").equals(reqData.getString("beacon"))) {
                            reqData.remove("beacon");
                            reqData.put("beacon", beaconData.getJSONObject(0).getString("uuid"));
                            reqData.remove("token");
                            reqData.put("token", "UPDATE");
                            reqData.remove("type");
                            postRequest(reqData);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.start();

            }
        };
        timer.start();
    }



    // TODO
    // Ablaufzeitpunkt oder vorgegebenes Zeitintervall? Je nachdem muss die remainingTime anders in reqData.json gespeichert werden um die verbleibende Zeit nach App neustart aufzufen zu können
    // Um die verbleibende Zeit bis zum Ablauf der Reservierung anzuzeigen
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
                field_Time.setText("Reservierung abgelaufen!");
                bookBtn.setEnabled(false);
            }
        };
        remainingTimeCountDown.start();
    }

    public void progressScreen(String title, String msg) {
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(false);
        progress.show();
    }

    public void postRequest(JSONObject object) {
        Log.i(status, "POST Request" + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);
        try {
            if (reqData.getString("token").contains("BOOK")) {
                progressScreen("Buche Raum", "Raum wird für Sie gebucht...");
            } else if (reqData.getString("token").contains("CANCEL")) {
                progressScreen("Abbruch", "Reservierung wird abgebrochen...");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url + path, object, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("token").contains("UPDATE")) {
                        if (!field_Room.getText().equals(response.getString("room_id"))) {
                            field_Room.setText("" + response.getString("room_id"));
                            remainingTimeCountDown.cancel();
                            setDynamicEndTime(response.getLong("remainingTime"));
                            vibrate(); // Vibriert wenn ein Update gefunden wurde
                            // TODO
                            // Möglichkeit bieten diesen Vorschlag abzulehnen?
                            Toast.makeText(getApplicationContext(), "Es wurde ein besserer Raum in Ihrer Nähe gefunden!", Toast.LENGTH_LONG).show();
                            response.put("type", "singleRoom");
                            writeFile(requestFileName, response);
                        }
                    }
                    if (response.getString("token").contains("BOOK")) {
                        if (response.getLong("remainingTime") != 0) {
                            remainingTimeCountDown.cancel();
                        }
                        timer.cancel();
                        response.put("type", "singleRoom");
                        writeFile(requestFileName, response);
                        progress.dismiss();
                        // TODO
                        // Key mit einbeziehen, eventuell neue Aktivität?
                        roomBookedActivity();
                    }
                    if (response.getString("token").contains("CANCEL")) {
                        Log.i(status, "CANCEL");
                        timer.cancel();
                        if (response.getLong("remainingTime") != 0) {
                            remainingTimeCountDown.cancel();
                        }
                        response.put("type", "singleRoom"); // TODO nötig?
                        writeFile(requestFileName, new JSONObject());
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


    // Vibration
    private void vibrate() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(2000);
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

}
