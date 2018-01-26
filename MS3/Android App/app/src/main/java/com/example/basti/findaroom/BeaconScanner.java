package com.example.basti.findaroom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    private static final int READ_BLOCK_SIZE = 100;
    public String beaconFileName;
    public BeaconManager beaconManager;
    boolean alreadyContains = false;
    JSONObject foundBeacon = new JSONObject();
    private String monitoring, monitoringfailed, ranging, rangingfailed, status;
    private JSONArray beaconData;
    private long deleteTime = 300000; // 5 Minuten = 300000

    public BeaconScanner(Context applicationContext) {
        super();
    }

    public BeaconScanner() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        status = getString(R.string.status);
        Log.i(status, "Start Backgroundservice");

        super.onStartCommand(intent, flags, startId);

        monitoring = getString(R.string.monitoring);
        ranging = getString(R.string.ranging);
        monitoringfailed = getString(R.string.monitoringfailed);
        rangingfailed = getString(R.string.rangingfailed);
        status = getString(R.string.status);
        beaconFileName = getString(R.string.beaconFile);


        beaconManager = BeaconManager.getInstanceForApplication(this);
        // Beacon Layout
        //beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")); // AltBeacon
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")); // iBeacon
        beaconManager.bind(this);

/*
        // Test Daten
        try {
            readFile(beaconFileName);
            foundBeacon.put("timestamp", System.currentTimeMillis());
            foundBeacon.put("distance", 0.1111111111);
            foundBeacon.put("uuid", "2f234454-cf6d-4a0f-adf2-f4911ba9ffb3");
            beaconData.put(foundBeacon);
            writeFile(beaconFileName, beaconData);
            Log.i(status, "Test Beacon saved!");
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() { // http://altbeacon.github.io/android-beacon-library/samples.html
        beaconManager.addMonitorNotifier(new MonitorNotifier() {

            @Override
            public void didEnterRegion(Region region) {
            }

            @Override
            public void didExitRegion(Region region) {
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
            }

        });
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                if (beacons.size() > 0) {
                    // Gibt die 16-byte Proximity UUID des erkannten Beacons aus.
                    Identifier id1 = beacons.iterator().next().getId1();
                    Identifier id2 = beacons.iterator().next().getId2();

                    // Gibt die ungefähre Entfernung des gefundenen Beacons aus.
                    double distance = beacons.iterator().next().getDistance();

                    // Aktuelle Systemzeit um auf Aktualität zu prüfen
                    long currentTime = System.currentTimeMillis();

                    // Gefundener Beacon wird zur weiterverarbeitung in JSONObject gespeichert
                    try {
                        foundBeacon.put("timestamp", currentTime);
                        foundBeacon.put("distance", distance);
                        foundBeacon.put("uuid", id2.toString());
                        //foundBeacon.put("uuid2", id2.toString());
                        Log.i(status, "BEACON FOUND: " + foundBeacon);
                        checkBeacon();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
            Log.i(status, monitoring);
        } catch (RemoteException e) {
            Log.i(status, monitoringfailed);
        }

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            Log.i(status, ranging);
        } catch (RemoteException e) {
            Log.i(status, rangingfailed);
        }
    }

    // Zum überprüfen ob der erkannte Beacon bereits vorhanden ist
    public void checkBeacon() {

        try {
            alreadyContains = false;
            readFile(beaconFileName);
            cleanup(beaconData);

            // temporärer index, da nicht mit 0 initialisiert werden kann, da 0 auch als position im Array vorkommen kann.
            int index = 999999;
            for (int i = 0; i < beaconData.length(); i++) {
                if (beaconData.getJSONObject(i).get("uuid").equals(foundBeacon.getString("uuid"))) {
                    alreadyContains = true;
                    Log.i(status, "Beacon already exists!");
                    index = i;
                    break;
                } else {
                }
            }
            if (!alreadyContains) {
                Log.i(status, "Added new Beacon!");
                beaconData.put(foundBeacon);
                sortList(beaconData);
                writeFile(beaconFileName, beaconData);
                alreadyContains = false;
            } else {
                if (index != 999999) {
                    update(index); // Gefundener Beacon an index i im File wird neu beschrieben
                }
            }
            alreadyContains = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Sortiert die Liste mit gefundenen Beacons in absteigender Reihenfolge bezüglich ihrer gemessenen Entfernung
    // Somit steht an index 0 immer der Beacon mit der geringsten gemessenen Entfernung
    public void sortList(JSONArray fileData) {

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
        beaconData = sortedList;
        try {
            Log.i(status, "List sorted! New top beacon: " + list.get(0).getString("uuid") + " with distance: " + list.get(0).getDouble("distance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Löscht veralterte Beacondaten nach einer bestimmten Zeit aus der Datei "beaconData.json"
    public void cleanup(JSONArray data) {

        try {
            if (data.length() != 0 && data.getJSONObject(0).has("uuid")) {
                for (int i = 0; i < beaconData.length(); i++) {
                    if (beaconData.getJSONObject(i).getLong("timestamp") < System.currentTimeMillis() - deleteTime) {
                        Log.i(status, "Removed old Beacon: " + beaconData.getJSONObject(i).getString("uuid"));
                        beaconData.remove(i);
                    }
                }
            }
        } catch (JSONException e) {
            Log.i(status, "Cleanup failed!");
            e.printStackTrace();
        }
    }

    // Updated gemessene Entfernung und Zeit eines gefundenen Beacons in der Datei "beaconData.json" an der Indexstelle i
    public void update(int i) {

        try {
            beaconData.getJSONObject(i).put("timestamp", foundBeacon.getString("timestamp"));
            beaconData.getJSONObject(i).put("distance", foundBeacon.getString("distance"));
            sortList(beaconData);
            writeFile(beaconFileName, beaconData);
            Log.i(status, "Updated Beacon: " + beaconData.getJSONObject(i));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            fileIn.close();
            if (fName.contains(beaconFileName)) {
                if (stringData.contains("uuid")) {
                    JSONArray dataArray = new JSONArray(stringData);
                    beaconData = dataArray;
                } else {
                    JSONArray dataArray = new JSONArray();
                    beaconData = dataArray;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(status, "Readfile Err FNF in BeaconScanner: " + e);
            writeFile(beaconFileName, new JSONArray());
            readFile(beaconFileName);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(status, "Readfile Err JSON in BeaconScanner: " + e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(status, "Readfile Err IO in BeaconScanner: " + e);
        }

    }


    //Schreibe übermittelte Beacon Daten persistent in beaconData.json
    public void writeFile(String fName, JSONArray data) {

        try {
            FileOutputStream output = openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            writeOnOutput.write(data.toString());
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
