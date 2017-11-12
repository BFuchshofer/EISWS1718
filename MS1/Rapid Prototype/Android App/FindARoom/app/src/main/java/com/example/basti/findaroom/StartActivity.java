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

    // Widgets
    private TextView remainingTime;
    private TextView roomID;
    private Button reservateRoom;
    private Button bookingRoom_direct;
    private Button configButton;
    private TextView infoTextDirektBuchen;
    private CountDownTimer countDownTimerSuggestion;
    private CountDownTimer countDownTimerReservate;
    private CountDownTimer countDownTimerBooking;

    // Common Variables
    private boolean checkTime = true;
    private String toastStatusReservated = "Raum wurde reserviert!";
    private String toastQRCodeErkannt = "QR-Code erkannt!";
    private boolean reservatedRoom = false;
    public static String qrResult;
    private boolean checkForActiveRoom = false;
    public static long remainingTimeReservation;
    private boolean postOK = false;

    // Check fo actives
    public long time_endSuggestion = 0;
    public boolean boolean_endSuggestion;
    public static long time_endReservation  = 0;
    public boolean boolean_endReservation;
    public static long time_endBooking  = 0;
    public boolean boolean_endBooking;
    public String activeRoom;
    public boolean boolean_activeRoom;

    // File
    public String fileName = "internalData.json";
    public String fileData;
    public static JSONObject fileDataJSON;
    private String fileDataFromType;
    private String name;
    private String nachname;
    private String id;
    private JSONObject data;
    private FileOutputStream output;
    private static final int READ_BLOCK_SIZE = 100;
    private boolean emailValidation;
    public String email;

    // URLs
    private String url;
    private String urlSuggestion;
    private String urlReservate;
    private String urlBooking;

    // HTTP-Requests
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private static final String REQUESTTAG = "string request first";
    private boolean postOk = false; // Ist der POST Req erfolgreich angenommen worden
    public static String time; //bekommene Zeit vom Server die der Nutzer hat um den Raum zu reservieren
    public long bookingTime_end;

    // Variablen für andere Klassen
    public static String givenRoom;
    public static JSONObject currentRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_start);
            readFile(fileName, 0, "");
            try {
                emailValidation = fileDataJSON.getString("email").matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$"); //https://www.computerbase.de/forum/showthread.php?t=677550
            } catch (JSONException e) {
                e.printStackTrace();
            }
            roomID = (TextView) findViewById(R.id.reservate_roomID);
            if (emailValidation == true) {
                try {
                    email = fileDataJSON.getString("email");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                roomID.setText("Keine gültige Email gefunden. Bitte gehen Sie zurück zu den Einstellungen.");
            }
            try {
                url = fileDataJSON.getString("url");
                urlSuggestion = url + "/room/suggestion";
                urlReservate = url + "/room/reservation";
                urlBooking = url + "/room/booking";
            } catch (JSONException e) {
                e.printStackTrace();
            }



            remainingTime = (TextView) findViewById(R.id.remainingTimeToReservate);

            infoTextDirektBuchen = (TextView) findViewById(R.id.infoTextDirektBuchen);

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

        checkForActiveRoom = checkForActiveRooms();
        // Sucht in der Textdatei ob bereits ein Raum reserviert/gebucht/vorgeschlagen wurde

        if (checkForActiveRoom == true) {
            if (boolean_endSuggestion == true) {
                setDynamicEndSuggestionTime(0, time_endSuggestion);
            }
            if (boolean_endReservation == true) {
                Intent nextActivity = new Intent(getApplicationContext(), ReservateActivity.class);
                startActivity(nextActivity);
            }
            if (boolean_endBooking == true) {
                Intent nextActivity = new Intent(getApplicationContext(), BarcodeResult.class);
                startActivity(nextActivity);
            }

        } else {
            askForFreeRoom();
        }

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
        //Log.i("TAG", fileDataJSON.toString());
        checkForActiveRooms();

    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile(long endSuggestionTime, String roomID) {

        String room_ID = roomID;
        readFile(fileName, 0, "");
        try {
            output = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            JSONObject newData = new JSONObject(fileData); // Nehme die Daten vom File und schreibe sie in ein neues JSONObject

            try {
                if (endSuggestionTime != 0) {
                    newData.put("endSuggestionTime", endSuggestionTime);

                }
                if (room_ID.equals("_") == false) {
                    newData.put("room_id", roomID);

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

    // Überprüft aktive timestamps für Räume (für den fall das die App geschloßen wird)
    private boolean checkForActiveRooms() {
        readFile(fileName, 0, "");
        if (fileData.contains("endSuggestionTime")) {
            try {
                time_endSuggestion = fileDataJSON.getLong("endSuggestionTime");
                boolean_endSuggestion = true;
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (fileData.contains("endReservationTime")) {
            try {
                time_endReservation = fileDataJSON.getLong("endReservationTime");
                boolean_endReservation = true;
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (fileData.contains("endBookingTime")) {
            try {
                time_endBooking = fileDataJSON.getLong("endBookingTime");
                boolean_endBooking = true;
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (fileData.contains("room_id")) {
            try {
                activeRoom = fileDataJSON.getString("room_id");
                boolean_activeRoom = true;
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

       return false;
    }


    // GET-Request
    // Fragt beim start der Aktivität im Server einen freien Raum an und liefert das Ergebnis zurück
    // Alternativ aufgerufen wenn die Vorschlagszeit abgelaufen ist
    private void askForFreeRoom() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user", fileDataJSON.getString("email") );
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException n) {
            n.printStackTrace();
            Toast.makeText(getApplicationContext(), "Keine Email hinterlegt.", Toast.LENGTH_SHORT).show();
        }

        mRequestQueuePOST = Volley.newRequestQueue(this);
        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlSuggestion, jsonBody, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    //givenRoom = response.getJSONObject("room").getString("number"); // mache die angezeigte Raum ID global verfügbar
                    roomID.setText(response.getJSONObject("johntitor").getString("number")); // eventuell noch dynamischer anpassen, oder so lassen wenn wir die datennamen so definieren
                    currentRoom = response.getJSONObject("johntitor");


                    //Log.i("TAG", currentRoom.toString());
                    //TODO
                    // rauminfos aus dem response auch in die datei zu dem user schreiben (neues json object - selbes file)

                    long endSuggestionTime;
                    endSuggestionTime = response.getJSONObject("johntitor").getLong("suggestion_end");
                    writeFile(currentRoom.getLong("suggestion_end"), currentRoom.getString("number") );//Schreibe erhaltene Raum/endzeit in den internen Speicher
                    Log.i("TAG", fileDataJSON.toString());
                    setDynamicEndSuggestionTime(endSuggestionTime, 0);
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

        postJsonRequest.setTag(REQUESTTAG);

        mRequestQueuePOST.add(postJsonRequest);

    }

    // Um die verbleibende Zeit bis zum Ablauf des Vorschlags dynamisch zu aktuallisieren
    private void setDynamicEndSuggestionTime(long endSuggestionTime, long activeEndSuggestionTime) {
        long tmp;
        if (activeEndSuggestionTime != 0) {
            tmp = activeEndSuggestionTime - System.currentTimeMillis();
        } else {
            tmp = endSuggestionTime - System.currentTimeMillis();
        }

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
                result();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // POST-Request
    // Weiterleitung zu Buchungsübersicht ( beim direkten buchen, ohne Reservierung )
    private void resultBooking() {

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

        mRequestQueuePOST.add(postJsonRequest);


        // Countdown, um die if abfrage nach erfolgreichen POST abzufragen (postOK würde sonst abgefragt bevor er gesetzt wird)
        countDownTimerBooking = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), BarcodeResult.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?
                    startActivity(nextActivity);
                    //Toast.makeText(getApplicationContext(), "Buchung erfolgreich.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Buchung gescheitert.", Toast.LENGTH_LONG).show();
                }
                postOK = false;

            }
        };



        // TODO
        // show "unable to book"-notification
        // return to StartActivity.java
    }

    // Weiterleitung zu Buchungsübersicht
    private void result() {
        try {
            if (qrResult.equals(currentRoom.getString("number"))) {
                resultBooking();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fehler, QR-Code konnte nicht verglichen werden.", Toast.LENGTH_SHORT).show();
        }
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
        } catch (NullPointerException n) {
            n.printStackTrace();
            Toast.makeText(getApplicationContext(), "Keine Verbindung zum Server.", Toast.LENGTH_SHORT).show();
        }
        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlReservate, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    postOk = true;
                    remainingTimeReservation = response.getJSONObject("johntitor").getLong("reservation_end");
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
                    Toast.makeText(getApplicationContext(), "Reservierung erfolgreich.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Reservierung gescheitert.", Toast.LENGTH_LONG).show();
                }
            }
        };

        postOk = false; // zurücksetzen


    }



    // Requestqueue wird gelöscht falls die App oder Aktivität(Screen) geschloßen wird
    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueuePOST != null) {
            mRequestQueuePOST.cancelAll(REQUESTTAG);
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

    // Um in anderen Klassen auf das Ergebnis des QR-Codes heranzukommen
    public static String getQRResult() {
        return qrResult;
    }

    public static long returnActiveTimeReservation() {
        return time_endReservation;
    }
    public static long returnActiveTimeBooking() {
        return time_endBooking;
    }
}

