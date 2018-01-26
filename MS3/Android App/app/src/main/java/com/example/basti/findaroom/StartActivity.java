package com.example.basti.findaroom;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;


public class StartActivity extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;
    private Toast comingSoonToast;
    private Toast backBtnToast;
    private Intent scanService;
    private Context ctx;
    private String requestFileName, beaconFileName, userFileName, status;
    private JSONObject userData, reqData;
    private JSONArray beaconData;
    private FileOutputStream output;
    private Button singleBtn, silentBtn, multiBtn, specificBtn, settingBtn;
    private BeaconScanner beaconScannerService;
private boolean p;

    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        beaconFileName = getString(R.string.beaconFile);
        status = getString(R.string.status);

        readFile(userFileName);
        readFile(requestFileName);
        readFile(beaconFileName);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.v(status,"Permission is granted");
            //File write logic here
            p = true;
        }
        if (!p) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        comingSoonToast = Toast.makeText(getApplicationContext(), getString(R.string.soonToast), Toast.LENGTH_LONG);
        backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);
        ctx = this;
        beaconScannerService = new BeaconScanner(getCtx());
        scanService = new Intent(getCtx(), beaconScannerService.getClass());

        // überprüft ob der beaconscanner bereits läuft um ihn nicht bei Aktivitätsaufruf doppelt zu starten
        if (!serviceRunning(beaconScannerService.getClass())) {
            startService(scanService);
        }

        Log.i(status, "User: " + userData);
        Log.i(status, "Request: " + reqData);
        Log.i(status, "Beacon: " + beaconData);

        // Überprüfung ob bereits eine Reservierung oder Buchung eines Raumes vorliegt. Wenn ja wird direkt auf die entsprechende Activity weitergeleitet
        try {
            if (reqData.has("token") && reqData.getString("token").contains("GET")) {
                if (reqData.has("type")) {
                    try {
                        if (reqData.getString("type").contains("singleRoom")) {
                            Intent singleRoomActivity = new Intent(getApplicationContext(), SingleRoomResult.class);
                            startActivity(singleRoomActivity);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (reqData.has("token") && reqData.getString("token").contains("BOOK")) {
                if (reqData.has("type")) {
                    try {
                        if (reqData.getString("type").contains("singleRoom")) {
                            Intent singleRoomActivity = new Intent(getApplicationContext(), SingleRoomBooked.class);
                            startActivity(singleRoomActivity);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } // Überprüfung muss erweitert werden um mehr Anwendungsfälle abdecken zu können
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        singleBtn = (Button) findViewById(R.id.singleRoom);
        multiBtn = (Button) findViewById(R.id.multiRooms);
        silentBtn = (Button) findViewById(R.id.silentRoom);
        specificBtn = (Button) findViewById(R.id.specificRoom);
        settingBtn = (Button) findViewById(R.id.settings);

        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singleRoomActivity = new Intent(getApplicationContext(), SingleRoom.class);
                startActivity(singleRoomActivity);
            }
        });
        silentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comingSoonToast.show();
            }
        });

        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comingSoonToast.show();
            }
        });

        specificBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comingSoonToast.show();
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerificationActivity.checkForConfigButtonInteraction();
                Intent backToSettingsActivity = new Intent(getApplicationContext(), VerificationActivity.class);
                startActivity(backToSettingsActivity);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    // Überprüft ob
    private boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(status, "Beacon Scanner not started yet.");
                return true;
            }
        }
        Log.i(status, "Beacon Scanner already started.");
        return false;
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


    @Override
    protected void onDestroy() {
        stopService(scanService);
        super.onDestroy();
    }
}

