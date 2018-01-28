package com.example.basti.findaroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.List;


public class SingleRoom extends AppCompatActivity {

    public FunctionsBasic functionsBasic = new FunctionsBasic();
    private String url, path, beaconFileName, userFileName, requestFileName;
    private JSONObject userData = new JSONObject();
    private JSONArray beaconData = new JSONArray();
    private EditText field_person, field_blackboard, field_beamer, field_whiteboard;
    private RadioButton yes_btn, no_btn;
    private boolean radioButton = true;
    private Button btn_ok, btn_cancel;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private JSONObject reqData = new JSONObject();
    private ProgressDialog progress;
    private Toast backBtnToast;
    private Spinner dropdown;
    private String roomType;
    private Context context = this;

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
        yes_btn = (RadioButton) findViewById(R.id.radio_yes);
        no_btn = (RadioButton) findViewById(R.id.radio_yes);
        field_blackboard = (EditText) findViewById(R.id.field_tafel);
        field_whiteboard = (EditText) findViewById(R.id.field_whiteboard);
        field_beamer = (EditText) findViewById(R.id.field_beamer);
        dropdown = (Spinner) findViewById(R.id.dropdown);

        // DropDown Auswahl füllen
        // https://www.mkyong.com/android/android-spinner-drop-down-list-example/
        List<String> list = new ArrayList<String>();
        list.add("Arbeitsraum");
        list.add("Hörsaal");
        list.add("Seminar");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                roomType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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


        btn_ok = (Button) findViewById(R.id.btn_search);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beaconData = functionsBasic.readFileArray(context, beaconFileName); // save in JSONArray beaconData
                userData = functionsBasic.readFileObject(context, userFileName);   // save in JSONObject userData
                reqData = functionsBasic.readFileObject(context, requestFileName);
                try {
                    if (beaconData.length() == 0) {
                        reqData.put("beacon", "location error");
                    } else {
                        reqData.put("beacon", beaconData.getJSONObject(0).getString("uuid"));
                    }
                    reqData.put("user", userData.getString("user"));
                    reqData.put("person", field_person.getText());
                    reqData.put("roomType", roomType);
                    reqData.put("chairTable", radioButton);
                    reqData.put("blackboard", field_blackboard.getText());
                    reqData.put("whiteboard", field_whiteboard.getText());
                    reqData.put("beamer", field_beamer.getText());
                    reqData.put("token", "GET");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendRequest(reqData);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });

    }


    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    public void clickedRadioButton(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
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
            // Status-Code 200
            public void onResponse(JSONObject response) {
                try {
                    reqData.put("type", "singleRoom");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    reqData.put("remainingTime", response.getString("remainingTime"));
                    reqData.put("room_id", response.getString("room_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                functionsBasic.writeFile(context, requestFileName, reqData, null);
                progress.dismiss();
                singleRoomResultActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Frage den Response Status ab um auf die HTTP-Codes zugreifen zu können
                NetworkResponse res = error.networkResponse;

                if (res != null) { // Internetverbindung OK
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
                } else { // Keine Verbindung zum Server/Internet
                    progress.dismiss();
                    Toast.makeText(getApplicationContext(), getString(R.string.volleyNetwork), Toast.LENGTH_LONG).show();
                }

            }


        });
        mRequestQueuePOST.add(postJsonRequest);
    }

}
