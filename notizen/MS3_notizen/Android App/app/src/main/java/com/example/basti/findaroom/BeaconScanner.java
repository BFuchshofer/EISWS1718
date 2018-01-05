package com.example.basti.findaroom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;

/**
 * Created by Basti on 31.12.2017.
 */

public class BeaconScanner extends Service implements BeaconConsumer {

    private static final int READ_BLOCK_SIZE = 100;

    private CountDownTimer timer;
    private long timeForSave = 5000;


    // File
    public String fileName = "internalData.json";
    public String beaconFileName = "beaconData.json";

    public static JSONObject fileDataJSON;

    protected static final String monitoring = "MonitoringActivity";
    protected static final String ranging = "RangingActivity";
    protected static final String status = "Status";
    protected static final String checkData = "Data";
    boolean alreadyContains = false;

    //JSONArray fileData = readFile(beaconFileName);

    JSONArray fileData = new JSONArray();
    JSONObject data = new JSONObject();


    public BeaconManager beaconManager;

    public BeaconScanner(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public BeaconScanner() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(status, "start background service");

        super.onStartCommand(intent, flags, startId);
        //startTimer();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // AltBeacon Layout
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        //Log.i(status, "Current BeaconFile" + fileData);
        startTimer();

        return START_STICKY;
    }
    /*
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }
*/


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(new MonitorNotifier() {


            @Override
            public void didEnterRegion(Region region) {
                Log.i(monitoring, "New beacon detected!");
            }

            @Override
            public void didExitRegion(Region region) {
                //Log.i(monitoring, "I no longer see an beacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(monitoring, "I have just switched from seeing/not seeing beacons: " + state);
            }

        });
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    // Gibt die 16-byte Proximity UUID des erkannten Beacons aus.
                    //Log.i(ranging, "BeaconID: " + beacons.iterator().next().getId1());
                    Identifier id = beacons.iterator().next().getId1();

                    // Gibt die ungefähre Entfernung des gefundenen Beacons aus.
                    //Log.i(ranging, "Beacon range: " + beacons.iterator().next().getDistance());
                    double distance = beacons.iterator().next().getDistance();

                    // Aktuelle Systemzeit um auf aktualität zu prüfen
                    long currentTime = System.currentTimeMillis();

                    try {
                        data.put("timestamp", currentTime);
                        data.put("distance", distance);
                        data.put("uuid", id.toString());


                        //Log.i("daten", "New data: " + data.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
            Log.i(monitoring, "Monitoring started.");
        } catch (RemoteException e) {
            Log.i(monitoring, "Monitoring failed.");
        }

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            Log.i(ranging, "Ranging started.");
        } catch (RemoteException e) {
            Log.i(ranging, "Ranging failed.");
        }
    }

    public void startTimer() {

        timer = new CountDownTimer(timeForSave, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("remainingTimeForSave", " " + l/1000 + "seconds");
            }

            @Override
            public void onFinish() {
                //readFile(beaconFileName, 0, "");
                writeFile(beaconFileName);
                timer.start();
            }
        };
        timer.start();
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

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    //Genutzt um alte Einträge zu löschen
    public void writeFile(String fName) {
        fileData = readFile(beaconFileName);

        try {

            //FileOutputStream output = getApplicationContext().openFileOutput(fName, MODE_PRIVATE);
            //OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);


            if (data != null) {

                if (fileData.length() == 0) {

                    FileOutputStream output = getApplicationContext().openFileOutput(fName, MODE_PRIVATE);
                    OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

                    fileData.put(data);
                    writeOnOutput.write(fileData.toString());
                    writeOnOutput.close();
                } else {

                    alreadyContains = false;
                    JSONObject tmpData;
                    tmpData = data;
                    for (int i = 0; i < fileData.length(); i++) {

                        if (fileData.getJSONObject(i).get("uuid").equals(tmpData.getString("uuid"))) {
                            alreadyContains = true;
                            break;

                        } else {
                            alreadyContains = false;
                        }
                    }

                    if (alreadyContains == false) {

                        FileOutputStream output = getApplicationContext().openFileOutput(fName, MODE_PRIVATE);
                        OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

                        fileData.put(tmpData);

                        writeOnOutput.write(fileData.toString());
                        writeOnOutput.close();
                        Log.i(status, "Beacon data saved: " + fileData.toString());
                        alreadyContains = false;
                    } else if (alreadyContains == true){
                        Log.i(status, "Beacon already exists.");

                    }
                }


            }

            String tmp = readFile(beaconFileName).toString();
            Log.i(status, "after writing: " + tmp);
            alreadyContains = false;
        } catch (Exception e) {
            e.printStackTrace();

            Log.i(status, "Beacon data not saved!");
        }
    }

    // Sortiert die Liste mit gefundenen Beacons in absteigender Reihenfolge bezüglich ihrer gemessenen Entfernung
    public void sortList() {

    }

    // Löscht veralterte Beacondaten aus der Datei "beaconData.json"
    public void cleanup() {

    }

    // Updated gemessene Entfernung und Zeit eines gefundenen Beacons in der Datei "beaconData.json"
    public void update() {

    }

}
