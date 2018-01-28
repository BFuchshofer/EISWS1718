package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
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


public class SingleRoomResult extends AppCompatActivity {

    public FunctionsBasic functionsBasic = new FunctionsBasic();
    private CountDownTimer timer;
    private CountDownTimer bookTimer;
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
    private ProgressDialog progress;
    private Toast backBtnToast;
    private Context context = this;

    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    public void roomBookedActivity() {
        Intent singleRoomBookedActivity = new Intent(this, SingleRoomBooked.class);
        startActivity(singleRoomBookedActivity);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room_result);
        field_Room = (TextView) findViewById(R.id.field_room);
        field_Time = (TextView) findViewById(R.id.field_status);

        progress = new ProgressDialog(this);

        backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);

        path = getString(R.string.room);
        beaconFileName = getString(R.string.beaconFile);
        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);

        userData = functionsBasic.readFileObject(context, userFileName);
        try {
            url = userData.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        reqData = functionsBasic.readFileObject(context, requestFileName);

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
                reqData = functionsBasic.readFileObject(context, requestFileName);
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
                reqData = functionsBasic.readFileObject(context, requestFileName);
                try {
                    reqData.remove("token");
                    reqData.put("token", "BOOK");
                    reqData.remove("type");

                    progressScreen("Buche Raum", "Standort wird überprüft...");
                    // Überprüfe den Standort des Benutzers indem er den zur Anfrage gehörenden Beacon mit der aktuellen Liste von gefundenen Beacons vergleicht
                    // Wird dieser Beacon gefunden, und liegt der Zeitpunkt des Entdeckens innerhalb eines Zeitrahmens von 10 Sekunden, wird die Buchung eingeleitet.
                    // Dies dient dazu zu überprüfen, ob der Benutzer sich in der Nähe des Raumes befindet.

                    bookTimer = new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long l) {
                            try {
                                beaconData = functionsBasic.readFileArray(context, beaconFileName);
                                int index = 999999; // Index muss initialisiert werden um in der If Abfrage überprüft werden zu können
                                for (int i = 0; i < beaconData.length(); i++) {
                                    if (beaconData.getJSONObject(i).getString("uuid").contains(reqData.getString("beacon"))) {
                                        index = i;
                                    }
                                }
                                if (index != 999999) {
                                    // Überprüft ob der Beacon (der Raum) noch aktuell ist, und er sich innerhalb von 8 Metern befindet.
                                    if (((beaconData.getJSONObject(index).getLong("timestamp") + 10000) >= System.currentTimeMillis()) && (beaconData.getJSONObject(index).getDouble("distance") <= 5)) {
                                        timer.cancel();
                                        remainingTimeCountDown.cancel();
                                        progress.dismiss();
                                        postRequest(reqData);
                                    } else {
                                        progress.dismiss();
                                        Toast.makeText(getApplicationContext(), "Bitte begeben Sie sich zum reservierten Raum um ihn zu buchen!", Toast.LENGTH_LONG).show();
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
                    bookTimer.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        startUpdateTimer();
    }

    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    // Timer der nach einer bestimten Zeit prüft ob ein aktuellerer Beacon vorliegt und diesen in einen neuen Request verschickt
    public void startUpdateTimer() {

        timer = new CountDownTimer(timeToUpdate, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                try {
                    reqData = functionsBasic.readFileObject(context, requestFileName);
                    beaconData = functionsBasic.readFileArray(context, beaconFileName);
                    // Prüfe ob ein aktueller Beacon vorliegt
                    if (beaconData.length() != 0) {
                        if (beaconData.getJSONObject(0).has("uuid")) {
                            if (reqData.has("beacon") && (!beaconData.getJSONObject(0).getString("uuid").equals(reqData.getString("beacon")))) {
                                reqData.remove("beacon");
                                reqData.put("beacon", beaconData.getJSONObject(0).getString("uuid"));
                                reqData.remove("token");
                                reqData.put("token", "UPDATE");
                                reqData.remove("type");
                                postRequest(reqData);
                            }
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

    // Zeigt die verbleibende Zeit in der Activity an die für die Buchung noch möglich ist
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

                field_Time.setText("Reservierung abgelaufen!");
                bookBtn.setEnabled(false);

            }
        };
        remainingTimeCountDown.start();
    }

    public void progressScreen(String title, String msg) {
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(true);
        progress.show();
    }

    // Vibration bei Aktualisierten Raum
    private void vibrate() { // https://stackoverflow.com/questions/13950338/how-to-make-an-android-device-vibrate
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(2000);
        }
    }

    public void postRequest(JSONObject object) {
        JSONObject tmp = new JSONObject();
        mRequestQueuePOST = Volley.newRequestQueue(this);
        try {
            if (reqData.getString("token").contains("BOOK")) {
                tmp.put("token", "BOOK");
                tmp.put("room_id", object.getString("room_id"));
                tmp.put("user", object.getString("user"));
                bookTimer.cancel();
                progressScreen("Buche Raum", "Standort bestätigt! \n Raum wird für Sie gebucht...");
            } else if (reqData.getString("token").contains("UPDATE")) {
                tmp = object;
            } else if (reqData.getString("token").contains("CANCEL")) {
                tmp.put("user", object.getString("user"));
                tmp.put("room_id", object.getString("room_id"));
                tmp.put("token", object.getString("token"));
                progressScreen("Abbruch", "Reservierung wird abgebrochen...");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url + path, tmp, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("token").contains("UPDATE")) {
                        if (!field_Room.getText().equals(response.getString("room_id"))) {
                            field_Room.setText("" + response.getString("room_id"));
                            remainingTimeCountDown.cancel();
                            setDynamicEndTime(response.getLong("remainingTime"));
                            vibrate(); // Vibriert wenn ein Update gefunden wurde
                            Toast.makeText(getApplicationContext(), "Es wurde ein besserer Raum in Ihrer Nähe gefunden!", Toast.LENGTH_LONG).show();
                            reqData.remove("room_id");
                            reqData.remove("remainingTime");
                            reqData.put("room_id", response.getString("room_id"));
                            reqData.put("remainingTime", response.getLong("remainingTime"));
                            reqData.put("type", "singleRoom");
                            functionsBasic.writeFile(context, requestFileName, reqData, null);
                        }
                    }
                    if (response.getString("token").contains("BOOK")) {
                        if (response.getLong("remainingTime") != 0) {
                            remainingTimeCountDown.cancel();
                        }
                        timer.cancel();
                        reqData.remove("remainingTime");
                        reqData.remove("remainingTime");
                        reqData.put("remainingTime", response.getLong("remainingTime"));
                        reqData.put("type", "singleRoom");
                        functionsBasic.writeFile(context, requestFileName, reqData, null);
                        progress.dismiss();
                        roomBookedActivity();
                    }
                    if (response.getString("token").contains("CANCEL")) {
                        if (response.getString("room_id").equals("0")) {
                            Toast.makeText(getApplicationContext(), "Ihre Reservierung ist abgelaufen! Bitte stellen Sie eine neue Raumanfrage!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Reservierung storniert!", Toast.LENGTH_LONG).show();
                        }
                        if (response.getLong("remainingTime") == 0) {
                            remainingTimeCountDown.cancel();
                        }
                        functionsBasic.writeFile(context, requestFileName, new JSONObject(), null);
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

                    } else if (res.statusCode == 401) { // Benutzer hat bereits einen Raum
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley401), Toast.LENGTH_LONG).show();

                    } else if (res.statusCode == 416) { // Beacon wurde im System nicht gefunden
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley416), Toast.LENGTH_LONG).show();
                    } else if (res.statusCode == 404) { // Kein passender freier Raum gefunden
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.volley404), Toast.LENGTH_LONG).show();
                    }
                } else { // Keine Verbindung zum Server
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.volleyNetwork), Toast.LENGTH_LONG).show();
                }
            }
        });

        mRequestQueuePOST.add(postJsonRequest);

    }
}
