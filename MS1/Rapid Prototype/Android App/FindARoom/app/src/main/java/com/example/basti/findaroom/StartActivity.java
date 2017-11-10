package com.example.basti.findaroom;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class StartActivity extends AppCompatActivity {

    TextView remainingTime;
    TextView roomID;
    Button reservateRoom;
    Button bookingRoom_direct;
    Button configButton;
    TextView infoTextDirektBuchen;
    private CountDownTimer countDownTimerSuggestion;
    private CountDownTimer countDownTimerReservate;

    public boolean checkTime = true;
    public String toastStatusReservated = "Raum wurde reserviert!";
    public String toastQRCodeErkannt = "QR-Code erkannt!";
    private boolean reservatedRoom = false;
    public static String qrResult;
    public boolean checkForActiveRoom = false;

    public static JSONObject currentRoom;
    public static long remainingTimeReservation;

    // File
    public String fileName = "internalData.json";
    public String fileData;
    public static JSONObject fileDataJSON;
    public String fileDataFromType;
    public String url = "http://192.168.2.105:5669/room";
    String name;
    String nachname;
    String id;
    JSONObject data;
    FileOutputStream output;
    static final int READ_BLOCK_SIZE = 100;

    //URLs
    public String urlSuggestion = url + "/suggestion";
    public String urlReservate = url + "/reservation";
    public String urlBooking = url + "/booking";


    // HTTP-Requests
    private RequestQueue mRequestQueueGET;
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest getJsonRequest;
    private JsonObjectRequest postJsonRequest;
    private static final String REQUESTTAG = "string request first";
    public boolean postOk = false; // Ist der POST Req erfolgreich angenommen worden
    public static String time; //bekommene Zeit vom Server die der Nutzer hat um den Raum zu reservieren


    // Variablen für andere Klassen
    public static String givenRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        checkForActiveRoom = checkForActiveRooms();
        // Sucht in der Textdatei ob bereits ein Raum reserviert/gebucht/vorgeschlagen wurde
        if (checkForActiveRoom == true) {

            //TODO
            // prüfe ob der raum reserviert oder gebucht wurde (eventuell auf aktuallität prüfen - curTime)
            // gehe auf entsprechende Aktivität und aktuallisiere die TexViews anhand der Daten aus der Datei

        } else {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_start);

            remainingTime = (TextView) findViewById(R.id.remainingTimeToReservate);
            roomID = (TextView) findViewById(R.id.reservate_roomID);
            infoTextDirektBuchen = (TextView) findViewById(R.id.infoTextDirektBuchen);

            readFile(fileName);

            reservateRoom = (Button) findViewById(R.id.reservateRoom);
            bookingRoom_direct = (Button) findViewById(R.id.bookingRoom_direct);
            configButton = (Button) findViewById(R.id.configButton);
            configButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextActivity = new Intent(getApplicationContext(), VerficationActivity.class);
                    VerficationActivity.checkForConfigButtonInteraction();
                    //TODO
                    // cancel countdowns
                    startActivity(nextActivity);
                }
            });

            reservateRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkTime = checkRemainingTime();
                    if (checkTime == true) {
                        try {
                            countDownTimerSuggestion.cancel();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Countdown nicht aktiv! / Keine Daten vom Server erhalten.", Toast.LENGTH_SHORT).show();
                        }
                        reservation();
                    } else {
                        Toast.makeText(getApplicationContext(), "Neuer Raum angefragt.", Toast.LENGTH_SHORT).show();
                        askForFreeRoom();
                        reservateRoom.setText("RESERVIEREN");
                        checkTime = false;
                    }
                }
            });

            bookingRoom_direct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookDirect();
                }
            });


            askForFreeRoom();

        }

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

        checkForActiveRooms();

    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(String fName) {
        String fileName = fName;

        try {
            output = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

            JSONObject userData = new JSONObject();
            try {
                userData.put("room_id", currentRoom.getString("number"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //JSONObject userData = new JSONObject();

            writeOnOutput.write(userData.toString());


            writeOnOutput.close();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                    Toast.LENGTH_LONG).show();
        }
    }


    private boolean checkForActiveRooms() {

        //TODO
        // Lese Textdatai aus und prüfe ob reservierungen/buchungen vorliegen

        return checkForActiveRoom;
    }


    // GET-Request
    // Fragt beim start der Aktivität im Server einen freien Raum an und liefert das Ergebnis zurück
    // Alternativ aufgerufen wenn die Vorschlagszeit abgelaufen ist
    private void askForFreeRoom() {

        mRequestQueueGET = Volley.newRequestQueue(this);
        getJsonRequest = new JsonObjectRequest(Request.Method.GET, urlSuggestion, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    //givenRoom = response.getJSONObject("room").getString("number"); // mache die angezeigte Raum ID global verfügbar
                    roomID.setText(response.getJSONObject("room").getString("number")); // eventuell noch dynamischer anpassen, oder so lassen wenn wir die datennamen so definieren
                    currentRoom = response.getJSONObject("room");

                    //Log.i("TAG", currentRoom.toString());
                    //TODO
                    // rauminfos aus dem response auch in die datei zu dem user schreiben (neues json object - selbes file)

                    long endSuggestionTime;
                    endSuggestionTime = response.getJSONObject("room").getLong("suggestion_end");
                    setDynamicEndSuggestionTime(endSuggestionTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                    roomID.setText("Kein JSON Format gefunden.");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.i(TAG, "Error: " + error.toString());

            }
        });

        getJsonRequest.setTag(REQUESTTAG);

        mRequestQueueGET.add(getJsonRequest);

    }

    // Um die verbleibende Zeit bis zum Ablauf des Vorschlags dynamisch zu aktuallisieren
    private void setDynamicEndSuggestionTime(long endSuggestionTime) {

        long tmp = endSuggestionTime - System.currentTimeMillis();
        remainingTime.setText("");
        countDownTimerSuggestion = new CountDownTimer(tmp, 1000) {
            @Override
            public void onTick(long l) {

                remainingTime.setText("" + l / 1000 + " Sekunden");
            }

            @Override
            public void onFinish() {
                remainingTime.setText("Vorschlag abgelaufen");
                reservateRoom.setText("Neuen Raum anfordern.");
                checkTime = false;

                //TODO
                //Fordere neuen raumvorschlag vom Server an und gebe alten Raum wieder frei
            }

        };
        countDownTimerSuggestion.start();

    }


    // Überprüft ob die verbleibende zeit für eine gültige Reservierung nicht <= 0  ist
    private boolean checkRemainingTime() {

        //TODO
        // aktuell wird checkTime im CountdownTimer gesetzt
        // eventuell noch umändern um dynamisch die zeit aus dem TextView zu ziehen.

        // TODO
        // eventuell die verbleibende zeit aus dem textfile lesen um auch nach dem neustart der app die richtige zeit zu haben

        return checkTime;
    }


    // Ermöglicht das direkte Buchen eines Raumes, bspw. wenn man schon vor einem vermeintlich freien Raum steht
    // Vor der buchung wird noch im System überprüft ob der Raum auch wirklich frei ist
    // Öffnet den QR-Code Scanner um den Code innerhalb eines Raumes zu scannen
    private void bookDirect() {

        countDownTimerSuggestion.cancel();
        Intent nextActivity = new Intent(this, BarcodeScanner.class);
        startActivityForResult(nextActivity, 0);
    }


    // Wertet das Ergebnis vom QR-Code Scan aus ( beim direkten buchen, ohne Reservierung )
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {

                // Wenn ein QR-Code gefunden wurde
                if (data != null) {
                    Barcode qrCode = data.getParcelableExtra("barcode");
                    qrResult = qrCode.displayValue;
                    Toast.makeText(getApplicationContext(), toastQRCodeErkannt, Toast.LENGTH_SHORT).show();
                } else {
                    qrResult = "Kein QR-Code gefunden.";

                    //TODO
                    // auf Seite bleiben, NICHT auf BarcodeResult.java wechseln

                }
                resultBooking();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // POST-Request
    // Weiterleitung zu Buchungsübersicht ( beim direkten buchen, ohne Reservierung )
    private void resultBooking() {

        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlBooking, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    time = response.getJSONObject("room").getString("reservation_end");

                    postOk = true;
                    //countDownTimerReservate.cancel();
                    countDownTimerReservate.start();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i("getJSONObject Error: ", e.toString());
                    postOk = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error Response POST: ", error.toString());
                roomID.setText("Server antwortet nicht.");
                postOk = false;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                try {
                    Log.i("TAG1", currentRoom.getString("number"));
                    Log.i("TAG2", fileDataJSON.getString("email"));

                    params.put("room_nr", currentRoom.getString("number"));
                    params.put("user", fileDataJSON.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return super.getParams();
            }
        };
        mRequestQueuePOST.add(postJsonRequest);


        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerReservate = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), BarcodeResult.class);
                if (postOk == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    startActivity(nextActivity);

                } else {
                    Toast.makeText(getApplicationContext(), "Buchung gescheitert.", Toast.LENGTH_LONG).show();
                }
            }
        };

        //postOk = false; // zurücksetzen


        Intent nextActivity = new Intent(this, BarcodeResult.class);
        startActivityForResult(nextActivity, 0);
    }


    // Um in anderen Klassen auf das Ergebnis des QR-Codes heranzukommen
    public static String getQRResult() {
        return qrResult;
    }


    //POST-Request
    // Fragt eine Reservierung des Raumes an und leitet den Benutzer bei erfolgreicher Reservierung weiter
    private void reservation() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("room_nr", currentRoom.getString("number"));
            jsonBody.put("user", fileDataJSON.getString("email") );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlReservate, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    //time = response.getJSONObject("room").getString("reservation_end");
                    //time = response.getString("time");
                    //Toast.makeText(getApplicationContext(), "Reservierung erfolgreich.", Toast.LENGTH_LONG).show();
                    postOk = true;
                    remainingTimeReservation = response.getJSONObject("johntitor").getLong("reservation_end");
                    //countDownTimerReservate.cancel();
                    countDownTimerReservate.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("getJSONObject Error: ", e.toString());
                    postOk = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Error Response POST: ", error.toString());
                roomID.setText("Server antwortet nicht.");
                postOk = false;
            }
        }) ;
        /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<String, String>();
                //Log.i("TAG1", currentRoom.getString("number"));
                //Log.i("TAG2", fileDataJSON.getString("email"));

                //params.put("room_nr",currentRoom.getString("number"));
                //params.put("user",fileDataJSON.getString("email"));
                params.put("test1", "test2");
                return params;




            }
        };
        */
        mRequestQueuePOST.add(postJsonRequest);


        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerReservate = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), ReservateActivity.class);
                if (postOk == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    startActivity(nextActivity);
                    //Toast.makeText(getApplicationContext(), "Reservierung erfolgreich.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Reservierung gescheitert.", Toast.LENGTH_LONG).show();
                }
            }
        };

        //postOk = false; // zurücksetzen


    }



    // Requestqueue wird gelöscht falls die App oder Aktivität(Screen) geschloßen wird
    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueueGET != null) {
            mRequestQueueGET.cancelAll(REQUESTTAG);
        }
    }


    public static JSONObject returnJSONObject() {
        return fileDataJSON;
    }

    public static JSONObject returnGivenRoom() {
        return currentRoom;
    }

    public static long returnRemainingTimeReservation() {
        return remainingTimeReservation;
    }
}

