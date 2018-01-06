package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

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
 * Created by Basti on 30.12.2017.
 */

public class singleRoom extends AppCompatActivity {

    EditText field_person;
    EditText field_roomSize;
    EditText field_blackboard;
    EditText field_beamer;
    EditText field_whiteboard;
    RadioButton yes_btn;
    RadioButton no_btn;
    Button btn_ok;
    Button btn_cancel;

    public String url = "";

    private static final int READ_BLOCK_SIZE = 100;
    public String beaconFileName = "beaconData.json";
    public JSONArray fileData = new JSONArray();

    public String nearestBeacon;

    protected static final String REQUEST = "REQUEST";
    protected static final String status = "STATUS in Request";


    private RequestQueue mRequestQueueGET;
    private JsonObjectRequest getJsonRequest;

    public void homeScreen() {
        Intent homeScreen = new Intent(this, StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_room);
        field_person = (EditText) findViewById(R.id.field_person);
        field_roomSize = (EditText) findViewById(R.id.field_roomSize);
        yes_btn = (RadioButton) findViewById(R.id.radio_yes);
        no_btn = (RadioButton) findViewById(R.id.radio_yes);
        field_blackboard = (EditText) findViewById(R.id.field_tafel);
        field_whiteboard = (EditText) findViewById(R.id.field_whiteboard);
        field_beamer = (EditText) findViewById(R.id.field_beamer);

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileData = readFile(beaconFileName);
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("beacon", fileData.getJSONObject(0).getString("uuid"));
                    jsonBody.put("person", field_person.getText());
                    jsonBody.put("roomSize", field_roomSize.getText());
                    jsonBody.put("blackboard", field_blackboard.getText());
                    jsonBody.put("whiteboard", field_whiteboard.getText());
                    jsonBody.put("beamer", field_beamer.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendRequest(jsonBody);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void sendRequest(JSONObject object) {

        Log.i(status, "" + object);
        mRequestQueueGET = Volley.newRequestQueue(this);
        getJsonRequest = new JsonObjectRequest(Request.Method.GET, url, object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                Log.i(status, "Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i(REQUEST, "Error in Request");
            }
        });
        mRequestQueueGET.add(getJsonRequest);
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
            Log.i(status, "readfile error: " + errorArray.toString());
            return errorArray;
        }

    }
}
