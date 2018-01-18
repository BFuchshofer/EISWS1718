package com.example.basti.findaroom;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class StartActivity extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;

    // Check fo active room
    public static long time_endReservation = 0;
    public static long time_endBooking = 0;
    public long time_endSuggestion = 0;
    public boolean boolean_endSuggestion = false;
    public boolean boolean_endReservation = false;
    public boolean boolean_endBooking = false;


    // File
    public String fileName = "internalData.json";
    public String fileData;
    public String email;
    private FileOutputStream output;
    public static JSONObject fileDataJSON;


    // Buttons
    private Button singleBtn;
    private Button silentBtn;
    private Button multiBtn;
    private Button specificBtn;
    private Button settingBtn;

    protected static final String service = "BackgroundService";


    public void beaconScan() {
        Intent beaconScanActivity = new Intent(this, BeaconScanner.class);;
        startActivity(beaconScanActivity);
    }


    Intent scanService;
    private BeaconScanner beaconScannerService;
    Context ctx;
    public Context getCtx() {
        return ctx;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readFile(fileName, 0, "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ctx = this;
        beaconScannerService = new BeaconScanner(getCtx());
        scanService = new Intent(getCtx(), beaconScannerService.getClass());

        // überprüft ob der beaconscanner bereits läuft um ihn nicht "doppelt zu starten"
        if (!serviceRunning(beaconScannerService.getClass())) {
            startService(scanService);
        }

/*
        try {
            Intent mServiceIntent = new Intent(this, BeaconScanner.class);
            mServiceIntent.setData(Uri.EMPTY);
            this.startService(mServiceIntent);
            Log.i(service, "Background Service started");
        } catch (Exception i) {
            Log.i(service, "Background Service failed");
        }
*/
        singleBtn = (Button) findViewById(R.id.singleRoom);
        singleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singleRoomActivity = new Intent(getApplicationContext(), SingleRoom.class);;
                startActivity(singleRoomActivity);
            }
        });
        silentBtn = (Button) findViewById(R.id.silentRoom);
        silentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent silentRoomActivity = new Intent(getApplicationContext(), SilentRoomResult.class);
                startActivity(silentRoomActivity);
            }
        });
        multiBtn = (Button) findViewById(R.id.multiRooms);
        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent multiRoomActivity = new Intent(getApplicationContext(), MultiRoom.class);
                startActivity(multiRoomActivity);
            }
        });
        specificBtn = (Button) findViewById(R.id.specificRoom);
        specificBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent specificRoomActivity = new Intent(getApplicationContext(), SpecificRoom.class);
                startActivity(specificRoomActivity);
            }
        });
        settingBtn = (Button) findViewById(R.id.settings);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerficationActivity.checkForConfigButtonInteraction();
                Intent backToSettings = new Intent(getApplicationContext(), VerficationActivity.class);
                startActivity(backToSettings);
            }
        });

    }

    private boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service running: ", "" + true);
                return true;
            }
        }
        Log.i("Service running: ", "" + false);
        return false;
    }



    // Gibt alle Daten aus dem TextFile aus
    public void readFile(String fName, long type1, String type2) {

        String fileName = fName;
        try {
            FileInputStream fileIn = openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String stringData = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                stringData += readstring;
            }
            InputRead.close();
            JSONObject data = new JSONObject(stringData);
            fileDataJSON = data;
            fileData = stringData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //checkForActiveRooms(); // Prüft ob aktuelle Reservierungen/Buchungen vorliegen
    }


    // Überprüft aktive timestamps für Räume (für den fall das die App geschloßen wird)
    private void checkForActiveRooms() {
        try {
            if ((fileDataJSON.has("endSuggestionTime")) && (fileDataJSON.getLong("endSuggestionTime") <= System.currentTimeMillis() + 1000)) {
                fileDataJSON.remove("endSuggestionTime");
                boolean_endSuggestion = false;
            }
            if (((fileDataJSON.has("endSuggestionTime")) && (fileDataJSON.getLong("endSuggestionTime") > System.currentTimeMillis() + 1000))) {
                time_endSuggestion = fileDataJSON.getLong("endSuggestionTime");
                boolean_endSuggestion = true;
            }
            if ((fileDataJSON.has("endReservationTime")) && fileDataJSON.getLong("endReservationTime") <= System.currentTimeMillis() + 1000) {
                fileDataJSON.remove("endReservationTime");
                boolean_endReservation = false;
            }
            if (((fileDataJSON.has("endReservationTime")) && (fileDataJSON.getLong("endReservationTime") > System.currentTimeMillis() + 1000))) {
                time_endReservation = fileDataJSON.getLong("endReservationTime");
                boolean_endReservation = true;
            }
            if ((fileDataJSON.has("endBookingTime")) && fileDataJSON.getLong("endBookingTime") <= System.currentTimeMillis() + 1000) {
                fileDataJSON.remove("endBookingTime");
                boolean_endBooking = false;
            }
            if (((fileDataJSON.has("endBookingTime")) && (fileDataJSON.getLong("endBookingTime") > System.currentTimeMillis() + 1000))) {
                time_endBooking = fileDataJSON.getLong("endBookingTime");
                boolean_endBooking = true;
            }
            /*
            if (boolean_endSuggestion == false && boolean_endReservation == false && boolean_endBooking == false) {
                fileDataJSON.remove("room_id");
                boolean_activeRoom = false;
                checkForActiveRoom = false;
            } else {
                activeRoom = fileDataJSON.getString("room_id");
                boolean_activeRoom = true;
                checkForActiveRoom = true;
            }
            */
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writeFile(fileName, 0, "", fileDataJSON, "");
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    //Genutzt um alte Einträge zu löschen
    public void writeFile(String fName, long endTime, String roomID, JSONObject newData, String type) {

        String room_ID = roomID;
        try {
            output = openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            JSONObject extraData = new JSONObject(fileData);
            try {
                // Schreibe übergebenes JSONObect in das FIle
                if (newData != null) {
                    writeOnOutput.write(newData.toString());
                    // Ergänze das File um extra Werte
                } else {

                    if (endTime != 0 && type.toLowerCase().contains("vorschlag")) {
                        extraData.put("endSuggestionTime", endTime);
                    }

                    if (room_ID.equals("_") == false) {
                        extraData.put("room_id", roomID);
                    }

                }
                if (newData != null && type.toLowerCase().contains("buchen")) {
                    if (extraData.has("endSuggestionTime")) {
                        extraData.remove("endSuggestionTime");
                    }
                    extraData.put("endBookingTime", endTime);
                }
                writeOnOutput.write(extraData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                    Toast.LENGTH_LONG).show();
        }
        readFile(fName, 0, "");
    }

    @Override
    protected void onDestroy() {
        stopService(scanService);
        super.onDestroy();
    }
}

