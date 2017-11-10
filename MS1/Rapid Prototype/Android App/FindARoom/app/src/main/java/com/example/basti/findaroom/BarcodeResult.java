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
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;

public class BarcodeResult extends AppCompatActivity {

    TextView bookedInfo;
    TextView remainingTimeBooked;
    TextView booked_roomID;
    Button cancelBooking;
    Button extendBooking;
    private String roomID;
    private String qrResult;
    private long remainingBookedTime = ReservateActivity.returnTimeBookingEnd();
    private JSONObject givenRoom;
    public boolean checkTime = true;

    public boolean postOK = false;

    public String confirmation;

    public JSONObject fileDataJSON;
    public String fileData;

    static final int READ_BLOCK_SIZE = 100;

    public JSONObject currentRoom = StartActivity.returnGivenRoom();

    private CountDownTimer countDownTimerBooked;

    public String fileName = "internalData.json";

    public CountDownTimer countDownTimerBooking;

    // HTTP-Requests
    private RequestQueue mRequestQueueGET;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest getJsonRequest;
    private JsonObjectRequest postJsonRequest;
    private static final String REQUESTTAG = "string request first";
    private boolean getOK = false;

    // Server URLs
    public String url = "http://192.168.2.105:5669/room";
    public String urlBookingCancel = url+"/cancelbooking";
    public String urlBookingExtend = url+"/extendbooking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_result);

        setDynamicEndTimeBooked(remainingBookedTime);

        bookedInfo = (TextView) findViewById(R.id.bookedInfo);
        remainingTimeBooked = (TextView) findViewById(R.id.remainingTimeBooked);
        booked_roomID = (TextView) findViewById(R.id.booked_roomID);

        readFile(fileName);

        cancelBooking = (Button) findViewById(R.id.cancelBooking);
        extendBooking = (Button) findViewById(R.id.extendBooking);

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

/*
        mRequestQueueGET = Volley.newRequestQueue(this);
        getJsonRequest = new JsonObjectRequest(Request.Method.GET, bookedRoomURL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    roomID = response.getJSONObject("room").getString("id"); // mache die empfangene Raum ID global verfügbar
                    if (roomID.equals(qrResult)) {
                        booked_roomID.setText(roomID); // eventuell noch dynamischer anpassen, oder so lassen wenn wir die datennamen so definieren
                        long endBookedTime;
                        endBookedTime = response.getJSONObject("room").getLong("endTimeBooking");
                        setDynamicEndTimeBooked(endBookedTime);
                    } else {
                        //TODO
                        //eventuell  noch überarbeiten. Überprüfung auf Serverseite lösen?
                        booked_roomID.setText("Raum stimmt nicht mit der Reservierung überein");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    booked_roomID.setText("Kein JSON Format gefunden.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                booked_roomID.setText("Server antwortet nicht.");
                getOK = false; // weglassen?
                Log.i(TAG, "Error: " + error.toString());

            }
        });

        getJsonRequest.setTag(REQUESTTAG);

        mRequestQueueGET.add(getJsonRequest);
*/

    }

    private void setDynamicEndTimeBooked(long endBookedTime) {

        long tmp = endBookedTime - System.currentTimeMillis();
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

    }

    // Gibt alle Daten aus dem TextFile aus
    public void readFile(String fName) {

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
                    confirmation = response.getJSONObject("johntitor").getString("confirmation");
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
            /*
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "testValue");
                params.put("time", "testValue");
                return super.getParams();
            }
        };
        */
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
                    startActivityForResult(nextActivity, 0);
                    //Toast.makeText(getApplicationContext(), "Buchung erfolgreich.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Stornierung gescheitert.", Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
