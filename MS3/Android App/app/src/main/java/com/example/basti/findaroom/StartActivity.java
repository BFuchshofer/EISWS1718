package com.example.basti.findaroom;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class StartActivity extends AppCompatActivity {

    public FunctionsBasic functionsBasic = new FunctionsBasic();
    private Toast comingSoonToast;
    private Toast backBtnToast;
    private Intent scanService;
    private Context ctx;
    private String requestFileName, beaconFileName, userFileName;
    private JSONObject userData, reqData;
    private JSONArray beaconData;
    private Button singleBtn, silentBtn, multiBtn, specificBtn, settingBtn;
    private BeaconScanner beaconScannerService;
    private boolean locationPermission;
    private Context context = this;

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

        userData = functionsBasic.readFileObject(context, userFileName);
        reqData = functionsBasic.readFileObject(context, requestFileName);
        beaconData = functionsBasic.readFileArray(context, beaconFileName);

        // Überprüfung ob Berechtigung für Standortbestimmung gegeben ist
        // Muss ab API 23+ in Laufzeit abgefragt werden
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermission = true;
        }
        if (!locationPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        comingSoonToast = Toast.makeText(getApplicationContext(), getString(R.string.soonToast), Toast.LENGTH_LONG);
        backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);
        ctx = this;
        beaconScannerService = new BeaconScanner(getCtx());
        scanService = new Intent(getCtx(), beaconScannerService.getClass());

        // BeaconScanner Statusabfrage
        if (!serviceRunning(beaconScannerService.getClass())) {
            startService(scanService);
        }

        // Überprüfung ob bereits eine Reservierung oder Buchung eines Raumes vorliegt. Wenn ja wird direkt auf die entsprechende Activity weitergeleitet
        try {
            if (reqData.has("token") && reqData.getString("token").contains("GET")) { // Reservierung vorhanden?
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
            } else if (reqData.has("token") && reqData.getString("token").contains("BOOK")) { // Buchung vorhanden?
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

            } // Überprüfung muss erweitert werden um mehr Usecases abdecken zu können - Im Prototyp nur SingleRoom verfügbar
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
                VerificationActivity.checkForConfigButtonInteraction(); // Um ein Flag zu setzen das beim Aufruf von Verification.java der Screen nicht übersprungen wird
                Intent backToSettingsActivity = new Intent(getApplicationContext(), VerificationActivity.class);
                startActivity(backToSettingsActivity);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    // Überprüft ob der BeaconScanner bereits läuft
    private boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        stopService(scanService);
        super.onDestroy();
    }
}

