package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
    private Toast backBtnToast;
    private Toast extendToast;

    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private String status;
    private JSONObject reqData;
    private JSONArray beaconData;
    private JSONObject userData;
    private CountDownTimer extendTimer;

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

        path = getString(R.string.room);
        beaconFileName = getString(R.string.beaconFile);
        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        status = getString(R.string.status);

        readFile(userFileName);
        try {
            url = userData.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        readFile(requestFileName);

        backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);
        extendToast = Toast.makeText(getApplicationContext(), getString(R.string.extendToast), Toast.LENGTH_SHORT);

        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);
        cancelBtn = (Button) findViewById(R.id.btn_cancel);
        extendBtn = (Button) findViewById(R.id.btn_extend);


        try {
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
                //extendToast.show();
                readFile(requestFileName);
                reqData.remove("type");
                reqData.remove("token");
                try {
                    reqData.put("token", "EXTEND");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressScreen("Verlängere Raumbuchung", "Standort wird überprüft...");
                // Überprüfe den Standort des Benutzers indem er den zur Anfrage gehörenden Beacon mit der aktuellen Liste von gefundenen Beacons vergleicht
                // Wird dieser Beacon gefunden, und liegt der Zeitpunkt des Entdeckens innerhalb eines Zeitrahmens von 10 Sekunden, wird die Buchung eingeleitet.
                // Dies dient dazu zu überprüfen, ob der Benutzer sich in der Nähe des Raumes befindet.

                extendTimer = new CountDownTimer(2000, 1000) {
                    @Override
                    public void onTick(long l) {
                        try {
                            readFile(beaconFileName);
                            int index = 999999; // Index muss initialisiert werden um in der If Abfrage überprüft werden zu können
                            for (int i = 0; i < beaconData.length(); i++) {
                                if (beaconData.getJSONObject(i).getString("uuid").contains(reqData.getString("beacon"))) {
                                    index = i;
                                }
                            }
                            if (index != 999999) {
                                // Überprüft ob der Beacon (der Raum) noch aktuell ist, und er sich innerhalb von 8 Metern befindet.
                                if ((System.currentTimeMillis() + 10000 >= beaconData.getJSONObject(index).getLong("timestamp")) && (beaconData.getJSONObject(index).getDouble("distance") <= 8)) {
                                    extendTimer.cancel();
                                    remainingTimeCountDown.cancel();
                                    progress.dismiss();

                                    postRequest(reqData);
                                    Log.i(status, "Gebuchter Raum erkannt! - Extend ok");
                                } else {
                                    Toast.makeText(getApplicationContext(), "Bitte begeben Sie sich zum verlängern der Buchung zum Raum um ihn zu buchen!", Toast.LENGTH_LONG).show();
                                    Log.i(status, "Gebuchter Raum NICHT erkannt! - Extend failed");
                                }
                            }
                        } catch (JSONException e) {

                        }

                    }

                    @Override
                    public void onFinish() {
                        progress.dismiss();

                    }
                };
                extendTimer.start();
            }
        });
    }

    // Deaktiviert den Zurück-Button des Endgerätes um den Programmfluss nicht zu gefährden
    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    public void progressScreen(String title, String msg) {
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(false);
        progress.show();
    }

    // Um die verbleibende Zeit bis zum Ablauf der Buchung anzuzeigen
    private void setDynamicEndTime(long time) {
        long endTime = time - System.currentTimeMillis();
        remainingTimeCountDown = new CountDownTimer(endTime, 1000) {
            @Override
            public void onTick(long l) {
                int beforeDecimal = (int) l / 1000 / 60;
                int afterDecimal = (int) (l / 1000) - (beforeDecimal * 60);
                field_Time.setText(beforeDecimal + ":" + afterDecimal + " Minuten");
            }

            @Override
            public void onFinish() {
                field_Time.setText("Buchung abgelaufen!");;
                extendBtn.setEnabled(false);
                extendBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                writeFile(requestFileName, new JSONObject());
            }
        };
        remainingTimeCountDown.start();
    }

    public void postRequest(JSONObject object) {
        JSONObject tmp = new JSONObject();
        mRequestQueuePOST = Volley.newRequestQueue(this);

        try {
            if (reqData.getString("token").contains("CANCEL")) {
                tmp.put("user", object.getString("user"));
                tmp.put("room_id", object.getString("room_id"));
                tmp.put("token", object.getString("token"));
                progressScreen("Abbruch", "Raum wird wieder freigegeben...");
                Log.i(status, "POST REQUEST CANCEL: " + tmp);
            } else if (reqData.getString("token").contains("EXTEND")) {
                tmp.put("token", "BOOK");
                tmp.put("room_id", object.getString("room_id"));
                tmp.put("user", object.getString("user"));
                progressScreen("Verlängern", "Raumbuchung wird verlängert...");
                Log.i(status, "POST REQUEST EXTEND: " + tmp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url + path, tmp, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (reqData.getString("token").contains("EXTEND")) {

                        reqData.remove("remainingTime");
                        reqData.remove("room_id");
                        reqData.put("room_id", response.getString("room_id"));
                        reqData.put("remainingTime", response.getLong("remainingTime"));
                        reqData.put("type", "singleRoom");
                        progress.dismiss();
                        writeFile(requestFileName, reqData);
                        remainingTimeCountDown.cancel();
                        field_Room.setText("" + response.getString("room_id"));
                        setDynamicEndTime(response.getLong("remainingTime"));
                        Toast.makeText(getApplicationContext(), "Raumbuchung verlängert!", Toast.LENGTH_LONG).show();

                    }
                    if (reqData.getString("token").contains("CANCEL")) {

                        if (response.getString("room_id").equals("0")) {
                            Toast.makeText(getApplicationContext(), "Ihre Buchung ist abgelaufen! Bitte stellen Sie eine neue Buchungsanfrage!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Buchung storniert!", Toast.LENGTH_LONG).show();
                        }
                        if (response.getLong("remainingTime") == 0) {
                            remainingTimeCountDown.cancel();
                        }
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
                // Frage den Response Status ab um auf die HTTP-Codes zugreifen zu können
                NetworkResponse res = error.networkResponse;

                if (res != null) { // Internetverbindung
                    if (res.statusCode == 400) { // Beacon im Request nicht gefunden --> location error
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley400), Toast.LENGTH_LONG).show();
                        Log.i(status, "Status Code: " + res.statusCode + "Beacon im Request nicht gefunden --> location error");

                    } else if (res.statusCode == 401) { // Benutzer hat bereits einen Raum
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley401), Toast.LENGTH_LONG).show();
                        Log.i(status, "Status Code: " + res.statusCode + "Benutzer hat bereits einen Raum");

                    } else if (res.statusCode == 416) { // Beacon wurde im System nicht gefunden
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley416), Toast.LENGTH_LONG).show();
                        Log.i(status, "Status Code: " + res.statusCode + "Beacon wurde im System nicht gefunden");
                    } else if (res.statusCode == 404) { // Kein passender freier Raum gefunden
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley404), Toast.LENGTH_LONG).show();
                        Log.i(status, "Status Code: " + res.statusCode + "Kein passender freier Raum gefunden");
                    }
                } else { // Keine Verbindung zum Server
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.volleyNetwork), Toast.LENGTH_LONG).show();
                    Log.i(status, "Network Error: Keine Internetverbindung (zum Server)");
                }
            }

        });

        mRequestQueuePOST.add(postJsonRequest);

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
