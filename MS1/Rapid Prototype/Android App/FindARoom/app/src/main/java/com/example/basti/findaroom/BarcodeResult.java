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

    TextView bookedInfo;
    TextView remainingTimeBooked;
    TextView booked_roomID;
    Button cancelBooking;
    Button extendBooking;
    private String roomID;
    private String qrResult;
    private long remainingBookedTime;
    private JSONObject givenRoom;
    public boolean checkTime = true;

    public boolean postOK = false;

    public boolean confirmation;

    public JSONObject fileDataJSON;
    public String fileData;

    static final int READ_BLOCK_SIZE = 100;

    public JSONObject currentRoom = StartActivity.returnGivenRoom();

    private CountDownTimer countDownTimerBooked;

    public String fileName = "internalData.json";

    public CountDownTimer countDownTimerBooking;

    // File
    private FileOutputStream output;

    // Check fo actives
    public long time_endBooking;
    public boolean boolean_endBooking;
    public String activeRoom;
    public boolean boolean_activeRoom;

    // HTTP-Requests
    private RequestQueue mRequestQueueGET;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest getJsonRequest;
    private JsonObjectRequest postJsonRequest;
    private static final String REQUESTTAG = "string request first";
    private boolean getOK = false;

    // Server URLs
    public String url;
    public String urlBookingCancel;
    public String urlBookingExtend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_result);

        setDynamicEndTimeBooked(remainingBookedTime, 0);

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
            booked_roomID.setText(currentRoom.getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cancelBooking = (Button) findViewById(R.id.cancelBooking);
        extendBooking = (Button) findViewById(R.id.extendBooking);

        // Passt den CountDown entsprechend der bereits vorligenden Reservierung an.
        if (StartActivity.returnActiveTimeBooking() != 0) {
            time_endBooking = StartActivity.returnActiveTimeBooking();
            setDynamicEndTimeBooked(0, time_endBooking);
        } else {
            remainingBookedTime = ReservateActivity.returnTimeBookingEnd();

            writeFile(remainingBookedTime);

            setDynamicEndTimeBooked(remainingBookedTime, 0);

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
            booked_roomID.setText(givenRoom.getJSONObject("room").getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setDynamicEndTimeBooked(long endBookedTime, long activeEndBookingTime) {

        long tmp;
        if (activeEndBookingTime != 0) {
            tmp = activeEndBookingTime - System.currentTimeMillis();
        } else {
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

                //TODO
                //Gebe den Raum wieder für andere Benutzer frei
                // Post/GET an den Server
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
    public void writeFile(long endReservationTime) {


        readFile(fileName, 0, "");
        try {
            output = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            JSONObject newData = new JSONObject(fileData); // Nehme die Daten vom File und schreibe sie in ein neues JSONObject

            try {
                if (endReservationTime != 0) {
                    newData.put("endReservationTime", endReservationTime);
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

            jsonBody.put("room_nr", currentRoom.getString("number"));
            jsonBody.put("user", fileDataJSON.getString("email") );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlBookingCancel, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    confirmation = response.getJSONObject("johntitor").getBoolean("confirmation");
                    postOK = true;

                    countDownTimerBooking.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("getJSONObject Error: ", e.toString());
                    postOK = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error Response POST: ", error.toString());
                booked_roomID.setText("Server antwortet nicht.");
                postOK = false;
            }
        });

        mRequestQueuePOST.add(postJsonRequest);
        //TODO
        // connect to server
        // POST (mit roomID) und teile dem Server mit das der Raum nicht mehr genutzt wird

        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerBooking = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), StartActivity.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    if (confirmation == true) {

                        //TODO
                        // entferne die reservierung aus dem File

                        startActivityForResult(nextActivity, 0);
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
