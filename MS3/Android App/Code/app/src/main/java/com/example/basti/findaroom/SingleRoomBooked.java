package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
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


public class SingleRoomBooked extends AppCompatActivity {

    public CountDownTimer remainingTimeCountDown;
    public String url;
    public String path;
    public String requestFileName;
    public String beaconFileName;
    public String userFileName;
    public FunctionsBasic functionsBasic = new FunctionsBasic();
    private TextView field_Room, field_Time;
    private Button cancelBtn, extendBtn;
    private ProgressDialog progress;
    private Toast backBtnToast;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private JSONObject reqData;
    private JSONArray beaconData;
    private JSONObject userData;
    private CountDownTimer extendTimer;
    private Context context = this;

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

        userData = functionsBasic.readFileObject(context, userFileName);
        try {
            url = userData.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        reqData = functionsBasic.readFileObject(context, requestFileName);

        backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);

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
                reqData = functionsBasic.readFileObject(context, requestFileName);
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
                            beaconData = functionsBasic.readFileArray(context, beaconFileName);
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
                                } else {
                                    Toast.makeText(getApplicationContext(), "Bitte begeben Sie sich zum verlängern der Buchung zum Raum um ihn zu buchen!", Toast.LENGTH_LONG).show();
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
                field_Time.setText("Buchung abgelaufen!");
                ;
                extendBtn.setEnabled(false);
                extendBtn.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                functionsBasic.writeFile(context, requestFileName, new JSONObject(), null);
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
            } else if (reqData.getString("token").contains("EXTEND")) {
                tmp.put("token", "BOOK");
                tmp.put("room_id", object.getString("room_id"));
                tmp.put("user", object.getString("user"));
                progressScreen("Verlängern", "Raumbuchung wird verlängert...");
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
                        functionsBasic.writeFile(context, requestFileName, reqData, null);
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
