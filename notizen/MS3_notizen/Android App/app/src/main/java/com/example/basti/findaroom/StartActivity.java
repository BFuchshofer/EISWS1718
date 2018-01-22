package com.example.basti.findaroom;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private Toast toast;
    private Intent scanService;
    private Context ctx;
    private String requestFileName, beaconFileName, userFileName, status;
    private JSONObject userData, reqData;
    private JSONArray beaconData;
    private FileOutputStream output;
    private Button singleBtn, silentBtn, multiBtn, specificBtn, settingBtn;
    private BeaconScanner beaconScannerService;

    public void beaconScan() {
        Intent beaconScanActivity = new Intent(this, BeaconScanner.class);
        startActivity(beaconScanActivity);
    }

    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO
        // überprüfen ob bereits eine Reservierung/Buchung vorliegt -> Daten aus "requestData.json" lesen

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        userFileName = getString(R.string.userFile);
        requestFileName = getString(R.string.requestFile);
        beaconFileName = getString(R.string.beaconFile);
        status = getString(R.string.status);

        readFile(userFileName);
        readFile(requestFileName);
        readFile(beaconFileName);


        toast = Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_LONG);
        ctx = this;
        beaconScannerService = new BeaconScanner(getCtx());
        scanService = new Intent(getCtx(), beaconScannerService.getClass());

        // überprüft ob der beaconscanner bereits läuft um ihn nicht "doppelt zu starten"
        if (!serviceRunning(beaconScannerService.getClass())) {
            startService(scanService);
        }

        Log.i(status, "User: " + userData);
        Log.i(status, "Request: " + reqData);
        Log.i(status, "Beacon: " + beaconData);

        // Überprüfung ob bereits eine Reservierung oder Buchung eines oder mehrerer Räume vorliegt. Wenn ja wird direkt auf die entsprechende Activity weitergeleitet
        try {
            if (reqData.getString("token").contains("GET")) {
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
            } else if (reqData.getString("token").contains("BOOK")) {
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
        } catch (NullPointerException e) {}
        catch (JSONException e) {}


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
                Intent nextActivity = new Intent(getApplicationContext(), SilentRoomResult.class);
                startActivity(nextActivity);
            }
        });

        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), MultiRoom.class);
                startActivity(nextActivity);
            }
        });

        specificBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming Soon!", Toast.LENGTH_LONG).show();
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


    private boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i(status, "Beacon Scanner started: TRUE");
                return true;
            }
        }
        Log.i(status, "Beacon Scanner started: FALSE");
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

