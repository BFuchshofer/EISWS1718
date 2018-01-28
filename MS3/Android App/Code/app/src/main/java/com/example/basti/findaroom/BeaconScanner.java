package com.example.basti.findaroom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BeaconScanner extends Service implements BeaconConsumer {

    public String beaconFileName;
    public BeaconManager beaconManager;
    public FunctionsBasic functionsBasic = new FunctionsBasic();
    boolean alreadyContains = false;
    JSONObject foundBeacon = new JSONObject();
    private JSONArray beaconData;
    private long deleteTime = 300000; // 5 Minuten = 300000
    private Context context = this;


    public BeaconScanner(Context applicationContext) {
        super();
    }

    public BeaconScanner() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
        beaconFileName = getString(R.string.beaconFile);
        beaconManager = BeaconManager.getInstanceForApplication(this);
        // Beacon Layout
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24")); // iBeacon
        beaconManager.bind(this);
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
                    Identifier id = beacons.iterator().next().getId2();

                    // Gibt die ungefähre Entfernung des gefundenen Beacons aus.
                    double distance = beacons.iterator().next().getDistance();

                    // Aktuelle Systemzeit um auf Aktualität zu prüfen
                    long currentTime = System.currentTimeMillis();

                    // Gefundener Beacon wird zur weiterverarbeitung in JSONObject gespeichert
                    try {
                        foundBeacon.put("timestamp", currentTime);
                        foundBeacon.put("distance", distance);
                        foundBeacon.put("uuid", id.toString());
                        checkBeacon();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {
        }

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
        }
    }

    // Zum überprüfen ob der erkannte Beacon bereits vorhanden ist
    public void checkBeacon() {

        try {
            alreadyContains = false;
            beaconData = functionsBasic.readFileArray(context, beaconFileName);
            cleanup(beaconData);

            // temporärer index, da nicht mit 0 initialisiert werden kann, da 0 auch als position im Array vorkommen kann.
            int index = 999999;
            for (int i = 0; i < beaconData.length(); i++) {
                if (beaconData.getJSONObject(i).get("uuid").equals(foundBeacon.getString("uuid"))) {
                    alreadyContains = true;
                    index = i;
                    break;
                }
            }
            if (!alreadyContains) {
                beaconData.put(foundBeacon);
                sortList(beaconData);
                functionsBasic.writeFile(context, beaconFileName, null, beaconData);
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
                    e.printStackTrace();
                }
                return 0;
            }
        });

        JSONArray sortedList = new JSONArray(list);
        beaconData = sortedList;
    }

    // Löscht veralterte Beacondaten nach einer bestimmten Zeit aus der Datei "beaconData.json"
    public void cleanup(JSONArray data) {

        try {
            if (data.length() != 0 && data.getJSONObject(0).has("uuid")) {
                for (int i = 0; i < beaconData.length(); i++) {
                    if (beaconData.getJSONObject(i).getLong("timestamp") < System.currentTimeMillis() - deleteTime) {
                        beaconData.remove(i);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Updated gemessene Entfernung und Zeit eines gefundenen Beacons in der Datei "beaconData.json" an der Indexstelle i
    public void update(int i) {

        try {
            beaconData.getJSONObject(i).put("timestamp", foundBeacon.getString("timestamp"));
            beaconData.getJSONObject(i).put("distance", foundBeacon.getString("distance"));
            sortList(beaconData);
            functionsBasic.writeFile(context, beaconFileName, null, beaconData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
