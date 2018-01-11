package com.example.basti.findaroom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Basti on 31.12.2017.
 */

public class BeaconScanner extends Service implements BeaconConsumer {

    protected static final String monitoring = "MonitoringActivity";
    protected static final String ranging = "RangingActivity";
    protected static final String status = "Status";
    protected static final String checkData = "Data";
    private static final int READ_BLOCK_SIZE = 100;
    // File
    public String beaconFileName = "beaconData.json";
    public BeaconManager beaconManager;
    public JSONArray fileData = new JSONArray();
    boolean alreadyContains = false;
    JSONObject data = new JSONObject();
    private CountDownTimer timer;
    private long timeForSave = 5000;

    private long deleteTime = 60000; // 5 Minuten = 300000

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

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // AltBeacon Layout
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

        startTimer();

        return START_STICKY;
    }

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
                Log.i("remainingTimeForSave", " " + l / 1000 + "seconds");
            }

            @Override
            public void onFinish() {
                fileData = readFile(beaconFileName);
                Log.i(checkData, "Started: " + fileData);
                fileData = cleanup(fileData);
                checkBeacon();
                Log.i(checkData, "Finished: " + fileData);
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

    // Zum überprüfen ob der erkannte Beacon bereits vorhanden ist
    public void checkBeacon() {

        try {
            if (data.length() != 0) {
                alreadyContains = false;
                JSONObject tmpData;
                tmpData = data;
                for (int i = 0; i < fileData.length(); i++) {
                    if (fileData.getJSONObject(i).get("uuid").equals(tmpData.getString("uuid"))) {
                        alreadyContains = true;
                        Log.i(status, "Beacon already exists!");
                        break;
                    } else {
                    }
                }
                if (alreadyContains == false) {
                    fileData = cleanup(fileData);
                    writeFile(beaconFileName, tmpData);
                    alreadyContains = false;
                }
            } else {
                fileData = cleanup(fileData);
                writeFile(beaconFileName, null);
            }
            alreadyContains = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(String fName, JSONObject jsonData) {

        try {
            FileOutputStream output = getApplicationContext().openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            if (jsonData != null) {
                fileData.put(jsonData);
            }
            fileData = sortList(fileData);
            writeOnOutput.write(fileData.toString());
            writeOnOutput.close();
            Log.i(status, "Beacon data saved: " + fileData.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(status, "Beacon data not saved!");
        }
    }

    // Sortiert die Liste mit gefundenen Beacons in absteigender Reihenfolge bezüglich ihrer gemessenen Entfernung
    public JSONArray sortList(JSONArray fileData) {

        final List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < fileData.length(); i++) {
            try {
                list.add(fileData.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    try {
                        return Double.compare(o1.getDouble("distance"), o2.getDouble("distance"));
                    } catch (JSONException e) {
                        Log.i(status, "List not sorted!");
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

        JSONArray sortedList = new JSONArray(list);
        try {
            Log.i(status, "List sorted! New top beacon: " + list.get(0).getString("uuid") + " with distance: " + list.get(0).getDouble("distance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sortedList;
    }

    // Löscht veralterte Beacondaten nach einer bestimmten Zeit aus der Datei "beaconData.json"
    public JSONArray cleanup(JSONArray data) {
        JSONArray cleanArray = new JSONArray();
        try {
            if (data.length() != 0 && data.getJSONObject(0).has("uuid")) {
                cleanArray = data;
                for (int i = 0; i < cleanArray.length(); i++) {

                    if (cleanArray.getJSONObject(i).getLong("timestamp") < System.currentTimeMillis() - deleteTime) {
                        Log.i(status, "Removed: " + cleanArray.getJSONObject(i));
                        cleanArray.remove(i);
                    }

                }
                Log.i(status, "Cleanup success!");
            }
        } catch (JSONException e) {
            Log.i(status, "Cleanup failed!");
            e.printStackTrace();
        }
        return cleanArray;
    }

    // Updated gemessene Entfernung und Zeit eines gefundenen Beacons in der Datei "beaconData.json"
    public void update() {

    }

}
