package com.example.basti.findaroom;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


    TextView titel;
    TextView text;

    public CountDownTimer timer;
    public long timeToUpdate = 10000; // 10 Sekunden

    private static final int READ_BLOCK_SIZE = 100;

    JSONArray fileData;
    public String beaconFileName = "beaconData.json";
    public JSONObject jsonBody = singleRoom.getData();

    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;

    protected static final String checkData = "STATUS in Update";

    public String url = "http://192.168.2.101:5669";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room_result);
        titel = (TextView) findViewById(R.id.titel);
        text = (TextView) findViewById(R.id.text);
        titel.setText("Room");
        text.setText("" + jsonBody);

        startTimer();
    }


    public void startTimer() {

        timer = new CountDownTimer(timeToUpdate, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("remainingTimeToUpdate", " " + l / 1000 + "seconds");
            }

            @Override
            public void onFinish() {
                try {
                    fileData = readFile(beaconFileName);
                    // Prüfe ob ein aktueller Beacon vorliegt
                    if (!fileData.getJSONObject(0).getString("uuid").equals(jsonBody.getString("beacon"))) {
                        jsonBody.remove("uuid");
                        jsonBody.put("uuid", fileData.getJSONObject(0).getString("uuid"));
                        String update = "update: " + jsonBody;
                        titel.setText(update);
                        updateRequest(jsonBody);
                    } else {
                        String noupdate = "no update";
                        titel.setText(noupdate);
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

        Log.i(checkData, "" + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url+"/room", object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                /*
                 * Response liefert Raumvorschlag und ein Token mit das diesen Request im Server identifiziert.
                 * Bei Raumupdate wird der Token mit dem neuen Beacon an den Server geschickt und als Response falls verfügbar ein neuer Raum ausgegeben.
                 */
                Log.i(checkData, "Response: " + response.toString());
                try {
                    text.setText("" + response.getJSONObject("data"));
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
