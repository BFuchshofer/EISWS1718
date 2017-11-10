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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ReservateActivity extends AppCompatActivity {

    TextView remainingTimeToBook;
    TextView roomID;
    Button bookRoom;
    Button cancelReservation;
    public static String qrResult;
    String qrCodeErkannt = "QR-Code erkannt!";
    public boolean checkTime = true;
    private CountDownTimer countDownTimerBooking;
    private String givenRoom;
    private boolean getOK = false;
    private boolean postOK = false;
    public static long bookingTime_end; // Verbleibende Zeit bis die Buchung ausläuft

    private CountDownTimer countDownTimerReservate;

    public String confirmation;



    // HTTP-Requests
    private RequestQueue mRequestQueueGET;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest getJsonRequest;
    private JsonObjectRequest postJsonRequest;
    private static final String REQUESTTAG = "string request first";

    // File
    public String fileName = "internalData.json";
    public String fileData;
    String name;
    String nachname;
    String id;
    public JSONObject currentRoom = StartActivity.returnGivenRoom();
    //public static JSONObject fileDataJSON = StartActivity.returnJSONObject();
    public static JSONObject fileDataJSON;

    static final int READ_BLOCK_SIZE = 100;

    static public long remainingTimeReservation = StartActivity.returnRemainingTimeReservation();



    // Server URLs
    public String url = "http://192.168.2.105:5669/room";
    public String urlBooking = url+"/booking";
    public String urlBookingCancel = url+"/cancelbooking";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO
        // Lese Textdatai aus und prüfe ob reservierungen/buchungen vorliegen
        // überspringe je nach Ergebnis einen Schritt


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservate);

        setDynamicEndTimeReservation(remainingTimeReservation);

        readFile(fileName);

        remainingTimeToBook = (TextView) findViewById(R.id.remainingTimeToBook);
        roomID = (TextView) findViewById(R.id.book_roomID);
        bookRoom = (Button) findViewById(R.id.bookRoom);
        cancelReservation = (Button) findViewById(R.id.cancelReservation);
        cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReservation();
            }
        });



        // TODO
        // Texte im UI dynamisch setzen - Infos aus vorherigen Requests/Response ziehen
        // RoomID, remainingTimeToBook, ...

        bookRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTime = checkRemainingTime();
                if (checkTime == true) {
                    try {
                        countDownTimerBooking.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Countdown nicht aktiv!", Toast.LENGTH_LONG).show();
                    }

                    book();
                } else {
                    roomID.setText("Reservierung abgelaufen.");
                }
            }
        });
/*
        mRequestQueueGET = Volley.newRequestQueue(this);
        getJsonRequest = new JsonObjectRequest(Request.Method.POST, urlBooking, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    givenRoom = response.getJSONObject("room").getString("id"); // mache die angezeigte Raum ID global verfügbar
                    roomID.setText(response.getJSONObject("room").getString("id")); // eventuell noch dynamischer anpassen, oder so lassen wenn wir die datennamen so definieren
                    long endReservationTime;
                    endReservationTime = response.getJSONObject("room").getLong("endTimeReservation");
                    setDynamicEndTimeBooking(endReservationTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                    roomID.setText("Kein JSON Format gefunden.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                roomID.setText("Server antwortet nicht.");
                getOK = false;
                Log.i(TAG, "Error: " + error.toString());

            }
        });

        getJsonRequest.setTag(REQUESTTAG);

        mRequestQueueGET.add(getJsonRequest);
*/
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

    private boolean checkRemainingTime() {
        return checkTime;
    }



    // Um die verbleibende Zeit bis zum Ablauf der Reservierung dynamisch zu aktuallisieren
    private void setDynamicEndTimeReservation(long endTimeReservation) {

        long tmp = endTimeReservation - System.currentTimeMillis();
        //remainingTimeToBook.setText(" ");
        countDownTimerBooking = new CountDownTimer(tmp, 1000) {
            @Override
            public void onTick(long l) {
                //checkTime = true;
                remainingTimeToBook.setText("" + l / 1000 + " Sekunden");
            }

            @Override
            public void onFinish() {
                remainingTimeToBook.setText("Reservierung abgelaufen.");
                checkTime = false;

                //TODO
                //Fordere neuen raumvorschlag vom Server an und gebe alten Raum wieder frei
            }

        };
        countDownTimerBooking.start();


    }

    // Öffnet den QR-Code Scanner um den Code innerhalb eines Raumes zu scannen und ggf. im folgenden zu buchen
    private void book() {

        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("room_nr", currentRoom.getString("number"));
            jsonBody.put("user", fileDataJSON.getString("email") );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlBooking, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    bookingTime_end = response.getJSONObject("johntitor").getLong("booking_end");
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
                roomID.setText("Server antwortet nicht.");
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


        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerBooking = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), BarcodeScanner.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    startActivityForResult(nextActivity, 0);
                    //Toast.makeText(getApplicationContext(), "Buchung erfolgreich.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Buchung gescheitert.", Toast.LENGTH_LONG).show();
                }
            }
        };

        //postOk = false; // zurücksetzen


        // TODO
        // show "unable to book"-notification
        // return to StartActivity.java
    }



    // Überprüft ob die Zeit für eine Buchung des Raumes nicht <= 0 ist
    private boolean checkRemainingTimeForBooking() {

        // TODO
        // Read remainingTimeToBook from variable and check
            // if (remainingTimeToBook >=0)
                // checkTime = true;

        return checkTime;

    }

    // Wertet das Ergebnis vom QR-Code Scan aus
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {

                // Wenn ein QR-Code gefunden wurde
                if (data != null) {
                    Toast.makeText(getApplicationContext(), "QR-Code erkannt!", Toast.LENGTH_LONG).show();
                    Barcode qrCode = data.getParcelableExtra("barcode");
                    qrResult = qrCode.displayValue;

                } else {
                    qrResult = "0";

                }
                result();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void cancelReservation() {


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
                roomID.setText("Server antwortet nicht.");
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



    // Weiterleitung zu Buchungsübersicht
    private void result() {
        Intent nextActivity = new Intent(this, BarcodeResult.class);
        Log.i("TAG", currentRoom.toString());
        try {
            if (qrResult.equals(currentRoom.getString("number"))) {
                startActivity(nextActivity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fehler, QR-Code konnte nicht verglichen werden.", Toast.LENGTH_LONG).show();
        }
    }

    // Um in anderen Klassen auf das Ergebnis des QR-Codes heranzukommen
    public static String getQRResult() {
        return qrResult;
    }

    public static long returnTimeBookingEnd() {
        return bookingTime_end;
    }
}
