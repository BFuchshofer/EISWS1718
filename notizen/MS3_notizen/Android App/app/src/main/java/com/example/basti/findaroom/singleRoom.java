package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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


public class SingleRoom extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;
    private String url, path, beaconFileName, userFileName, requestFileName, status;
    private JSONObject jsonBody = new JSONObject();
    private JSONObject userData = new JSONObject();
    private JSONArray beaconData = new JSONArray();
    private EditText field_person, field_roomSize, field_blackboard, field_beamer, field_whiteboard;
    private RadioButton yes_btn, no_btn;
    private boolean radioButton = true;
    private Button btn_ok, btn_cancel;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private JSONObject reqData;
    private ProgressDialog progress;


    // Leitet auf den Homscreen zurück
    public void homeScreen() {
        Intent homeScreen = new Intent(getApplicationContext(), StartActivity.class); // back to homescreen
        startActivity(homeScreen);
    }

    // Leitet auf die Ausgabeseite weiter
    public void singleRoomResultActivity() {
        Intent singleRoomResultActivity = new Intent(this, SingleRoomResult.class);
        startActivity(singleRoomResultActivity);
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

        progress = new ProgressDialog(this);

        url = getString(R.string.serverURL);
        path = getString(R.string.room);
        beaconFileName = getString(R.string.beaconFile);
        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        status = getString(R.string.status);

        btn_ok = (Button) findViewById(R.id.btn_search);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile(beaconFileName); // save in JSONArray beaconData
                readFile(userFileName);   // save in JSONObject userData
                try {
                    // TODO
                    // eventuell zu einem Array machen und über .getJSONObject("data").getString("...") aufrufen?
                    if (beaconData.length() == 0) {
                        jsonBody.put("beacon", "location error");
                    } else {
                        jsonBody.put("beacon", beaconData.getJSONObject(0).getString("uuid"));
                    }
                    jsonBody.put("user", userData.getString("email"));

                    jsonBody.put("person", field_person.getText());
                    jsonBody.put("roomSize", field_roomSize.getText());
                    jsonBody.put("chairTable", radioButton);
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

    }

    public void clickedRadioButton(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_yes:
                if (checked)
                    radioButton = true;
                    break;
            case R.id.radio_no:
                if (checked)
                    radioButton = false;
                    break;
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

    // Gibt alle Daten aus dem TextFile aus
    // TODO
    // in dieser .java wird ein JSONArray (beacon daten) und ein JSONObject (user daten) benötigt -> veralgemeinern?
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
            if (fName.contains(beaconFileName)) {
                if (stringData.length() != 0) {
                    JSONArray dataArray = new JSONArray(stringData);
                    beaconData = dataArray;
                } else {
                    JSONArray dataArray = new JSONArray();
                    beaconData = dataArray;
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


        } catch (Exception e) {
            JSONObject errorArray = new JSONObject();
            e.printStackTrace();
            Log.i(status, "readfile error: " + errorArray.toString());
        }

    }

    public void progressScreen(String title, String msg) {
        progress.setTitle(title);
        progress.setMessage(msg);
        progress.setCancelable(true);
        progress.show();
    }

    public void sendRequest(JSONObject object) {

        mRequestQueuePOST = Volley.newRequestQueue(this);
        progressScreen("Suche Raum", "Ein passender Raum wird für Sie gesucht...");
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, url + path, object, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.i(status, "Response: " + response.toString());
                try {
                    response.put("type", "singleRoom");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                writeFile(requestFileName, response);
                progress.dismiss();
                singleRoomResultActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // TODO
                // drehender Kreis entfernen bei Fehler oder progress dialog beenden und TOAST anzeigen
                progress.dismiss();
                error.printStackTrace();
                Log.i(status, "Error in Request");
                Toast.makeText(getApplicationContext(), "Fehler: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        mRequestQueuePOST.add(postJsonRequest);
    }


}
