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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Created by Basti on 30.12.2017.
 */

public class SingleRoom extends AppCompatActivity {

    EditText field_person, field_roomSize, field_blackboard, field_beamer, field_whiteboard;
    RadioButton yes_btn, no_btn;
    Button btn_ok, btn_cancel;

    public boolean checkButton = true;
    public String url = "http://192.168.2.101:5669";
    public String path = "/room";
    private static final int READ_BLOCK_SIZE = 100;
    public String beaconFileName = "beaconData.json";
    public String userFileName = "internalData.json";
    public JSONArray fileData = new JSONArray();
    protected static final String REQUEST = "REQUEST";
    protected static final String status = "STATUS in Request";
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    JSONObject jsonBody = new JSONObject();
    static JSONObject resData = new JSONObject();

    public void homeScreen() {
        Intent homeScreen = new Intent(getApplicationContext(), StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    public void singleRoomResultActivity() {
        Intent singleRoomResultActivity = new Intent(this, SingleRoomResult.class);;
        startActivity(singleRoomResultActivity);
    }

    public static JSONObject getData() {
        return resData;
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

        field_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_person.setText("");
            }
        });
        field_roomSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_roomSize.setText("");
            }
        });
        field_blackboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_blackboard.setText("");
            }
        });
        field_whiteboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_whiteboard.setText("");
            }
        });
        field_beamer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                field_beamer.setText("");
            }
        });

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fileData = Basic.readFile(beaconFileName);
                readFile(beaconFileName);
                try {
                    // TODO
                    // eventuell zu einem Array machen und über .getJSONObject("data").getString("...") aufrufen?
                    if (fileData.length() == 0) {
                        jsonBody.put("beacon", "location error");
                    } else {
                        jsonBody.put("beacon", fileData.getJSONObject(0).getString("uuid"));
                    }
                    jsonBody.put("person", field_person.getText());
                    jsonBody.put("roomSize", field_roomSize.getText());
                    jsonBody.put("chairTable", checkButton);
                    jsonBody.put("blackboard", field_blackboard.getText());
                    jsonBody.put("whiteboard", field_whiteboard.getText());
                    jsonBody.put("beamer", field_beamer.getText());
                    jsonBody.put("token", "GET");

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
                checkButton = true;

            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkButton = false;
            }
        });
    }


        //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(String fName, JSONObject data) {

        JSONArray curentData = readFile(fName);
        Log.i("dataobject0", curentData + "");
        JSONObject dataObject;
        try {
            dataObject = curentData.getJSONObject(0);
            Log.i("dataobject1", dataObject + "");
            FileOutputStream output = openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            dataObject.put("room_id", data.getString("room_id"));
            dataObject.put("remainingTime", data.getLong("remainingTime"));
            writeOnOutput.write(dataObject.toString());
            Log.i("dataobject2", dataObject + "");
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gibt alle Daten aus dem TextFile aus
    public JSONArray readFile(String fName) {

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
            if (stringData.length() != 0) {
                JSONArray dataArray = new JSONArray(stringData);
                Log.i("readFile1", "readfile: " + dataArray);
                return dataArray;
            } else {

                JSONArray dataArray = new JSONArray();
                Log.i("readFile2", "readfile: " + dataArray);
                return dataArray;
            }

        } catch (Exception e) {
            JSONArray errorArray = new JSONArray();
            e.printStackTrace();
            Log.i("readFile", "readfile error: " + errorArray.toString());
            return errorArray;
        }

    }



    public void sendRequest(JSONObject object) {

        Log.i(status, "jsonBody: " + object);
        mRequestQueuePOST = Volley.newRequestQueue(this);
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url+path, object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                /*
                 * Response liefert Raumvorschlag und ein Token mit das diesen Request im Server identifiziert.
                 * Bei Raumupdate wird der Token mit dem neuen Beacon an den Server geschickt und als Response falls verfügbar ein neuer Raum ausgegeben.
                 */
                Log.i(status, "Response: " + response.toString());

                    //resData = response.getJSONObject("data");
                    resData = response;

                    //basic.writeFile(userFileName, resData);
                writeFile(userFileName, resData);
                singleRoomResultActivity();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.i(REQUEST, "Error in Request");
            }
        });
        mRequestQueuePOST.add(postJsonRequest);
    }


}
