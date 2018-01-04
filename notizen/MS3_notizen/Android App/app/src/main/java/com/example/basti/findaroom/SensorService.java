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
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Basti on 01.01.2018.
 */

public class SensorService extends Service implements BeaconConsumer {

    protected static final String monitoring = "MonitoringActivity";
    protected static final String ranging = "RangingActivity";
    public BeaconManager beaconManager;

    public int counter=0;
    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //startTimer();

        beaconManager = BeaconManager.getInstanceForApplication(this);
        // AltBeacon Layout
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

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
                Log.i(monitoring, "I just saw an beacon for the first time!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(monitoring, "I no longer see an beacon");
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
                    Log.i(ranging, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
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
}
