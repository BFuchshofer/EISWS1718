package com.example.basti.findaroom;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.ContentValues.TAG;

public class BarcodeResult extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    private static final String REQUESTTAG = "string request first";
    public boolean checkTime = true;
    public boolean postOK = false;
    public String confirmation;
    public JSONObject fileDataJSON;
    public String fileData;
    public JSONObject currentRoom = StartActivity.returnGivenRoom();
    public String fileName = "internalData.json";
    public CountDownTimer countDownTimerBooking;
    // Check fo actives
    public long time_endBooking;
    // Server URLs
    public String url;
    public String urlBookingCancel;
    public String urlBookingExtend;
    TextView bookedInfo;
    TextView remainingTimeBooked;
    TextView booked_roomID;
    Button cancelBooking;
    Button extendBooking;
    private String roomID;
    private String qrResult;
    private long remainingBookedTime;
    private JSONObject givenRoom;
    private CountDownTimer countDownTimerBooked;
    // File
    private FileOutputStream output;
    // HTTP-Requests
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private boolean getOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_result);
        readFile(fileName, 0, "");
        try {
            url = fileDataJSON.getString("url");
            urlBookingCancel = url + "/room/cancelbooking";
            urlBookingExtend = url + "/room/extendbooking";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bookedInfo = (TextView) findViewById(R.id.bookedInfo);
        remainingTimeBooked = (TextView) findViewById(R.id.remainingTimeBooked);
        booked_roomID = (TextView) findViewById(R.id.booked_roomID);
        try {
            booked_roomID.setText(fileDataJSON.getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cancelBooking = (Button) findViewById(R.id.cancelBooking);
        extendBooking = (Button) findViewById(R.id.extendBooking);
        // Prüft ob eine Buchung vorliegt
        if (fileDataJSON.has("endBookingTime")) {
            try {
                time_endBooking = fileDataJSON.getLong("endBookingTime");
                setDynamicEndTimeBooked(time_endBooking, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Prüft ob eine aktive Buchung vorliegt
        }
        if (StartActivity.returnActiveTimeBooking() != 0) {
            time_endBooking = StartActivity.returnActiveTimeBooking();
            setDynamicEndTimeBooked(0, time_endBooking);
        }
        cancelBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimerBooked.cancel();
                cancel();
            }
        });
        extendBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimerBooked.cancel();
                extend();
            }
        });
        givenRoom = StartActivity.returnGivenRoom();
        qrResult = ReservateActivity.getQRResult();
        try {
            booked_roomID.setText(fileDataJSON.getString("room_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setDynamicEndTimeBooked(long endBookedTime, long activeEndBookingTime) {

        long tmp = 0;
        if (activeEndBookingTime != 0 && endBookedTime == 0) {
            tmp = activeEndBookingTime - System.currentTimeMillis();
        }
        if (activeEndBookingTime == 0 && endBookedTime != 0) {
            tmp = endBookedTime - System.currentTimeMillis();
        }
        //remainingTimeBooked.setText("");
        countDownTimerBooked = new CountDownTimer(tmp, 1000) {
            @Override
            public void onTick(long l) {
                //checkTime = true;
                remainingTimeBooked.setText("" + l / 1000 + " Sekunden");
            }

            @Override
            public void onFinish() {
                remainingTimeBooked.setText("Buchung abgelaufen");
                checkTime = false;
                readFile(fileName, 0, "");
                writeFile(0, fileDataJSON, "cancel");
                Intent nextActivity = new Intent(getApplicationContext(), StartActivity.class);
                startActivity(nextActivity);
            }
        };
        countDownTimerBooked.start();
    }


    // Erweitert die aktive Buchung eines Raumes um eine bestimmte Zeitspanne
    private void extend() {
        //TODO
        // prüfe ob der Benutzer die Berechtigung hat seine Raumbuchung zu verlängern
        // connect to Server
        // POST (mit roomID) an den Server und verlängere die Buchung
        // erweitere die Buchung im File
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
    }


    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(long endTime, JSONObject object, String type) {

        try {
            output = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            JSONObject newData = new JSONObject(fileData); // Nehme die Daten vom File und schreibe sie in ein neues JSONObject
            try {
                // Booking
                if (type.toLowerCase().contains("verlaengern")) {
                    if (newData.has("endBookingTime") == false) {
                        newData.put("endBookingTime", endTime);
                    } else {
                        newData.remove("endBookingTime");
                        newData.put("endBookingTime", endTime);
                    }
                }
                // Get active Rooms
                if (type.toLowerCase().contains("activeTime")) {
                }
                if (endTime != 0) {
                    newData.put("endReservationTime", endTime);
                }
                // Cancel booking
                if (type.toLowerCase().contains("cancel")) {
                    if (object != null && newData.has("endReservationTime")) {
                        newData.remove("endReservationTime");
                    }
                    if (object != null && newData.has("endSuggestionTime")) {
                        newData.remove("endSuggestionTime");
                    }
                    if (object != null && newData.has("endBookingTime")) {
                        newData.remove("endBookingTime");
                    }
                    if (object != null && newData.has("room_id")) {
                        newData.remove("room_id");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            writeOnOutput.write(newData.toString()); // Schreibe die aktuallisierten Daten ins File
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                    Toast.LENGTH_LONG).show();
        }
        readFile(fileName, 0, "");
    }


    private void cancel() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("room_nr", fileDataJSON.getString("room_id"));
            jsonBody.put("user", fileDataJSON.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRequestQueuePOST = Volley.newRequestQueue(this);
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlBookingCancel, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    confirmation = response.getJSONObject("johntitor").getString("confirmation");
                    postOK = true;
                    countDownTimerBooking.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                    postOK = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                booked_roomID.setText("Server antwortet nicht.");
                postOK = false;
            }
        });
        mRequestQueuePOST.add(postJsonRequest);
        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerBooking = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), StartActivity.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    if (confirmation.toLowerCase().contains("true")) {
                        readFile(fileName, 0, "");
                        writeFile(0, fileDataJSON, "cancel");
                        startActivity(nextActivity);
                        Toast.makeText(getApplicationContext(), "Stornierung erfolgreich.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Stornierung gescheitert.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    booked_roomID.setText("Keine Anwtowrt vom Server.");
                }
            }
        };
    }
}
