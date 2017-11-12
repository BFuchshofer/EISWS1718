package com.example.basti.findaroom;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class StartActivity extends AppCompatActivity {

    private static final int READ_BLOCK_SIZE = 100;
    private static final String REQUESTTAG = "string request first";
    public static String qrResult;
    public static long remainingTimeReservation;

    public static JSONObject fileDataJSON;
    public static String time; //bekommene Zeit vom Server die der Nutzer hat um den Raum zu reservieren
    // Variablen für andere Klassen
    public static String givenRoom;
    public static JSONObject currentRoom;

    // Check fo actives
    public static long time_endReservation = 0;
    public static long time_endBooking = 0;
    public long time_endSuggestion = 0;
    public boolean boolean_endSuggestion = false;
    public boolean boolean_endReservation = false;
    public boolean boolean_endBooking = false;
    public String activeRoom;
    public boolean boolean_activeRoom = false;

    // File
    public String fileName = "internalData.json";
    public String fileData;
    public String email;
    public long bookingTime_end;

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
    public boolean countDownstart = false;

    // Common Variables
    private boolean checkTime = true;

    private String toastQRCodeErkannt = "QR-Code erkannt!";

    private boolean checkForActiveRoom = false;
    private boolean postOK = false;

    private FileOutputStream output;
    // URLs
    private String url;
    private String urlSuggestion;
    private String urlReservate;
    private String urlBooking;
    // HTTP-Requests
    private RequestQueue mRequestQueuePOST;
    private JsonObjectRequest postJsonRequest;
    private boolean postOk = false; // Ist der POST Req erfolgreich angenommen worden

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        readFile(fileName, 0, "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        roomID = (TextView) findViewById(R.id.reservate_roomID);
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
        checkForActiveRooms();
        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getApplicationContext(), VerficationActivity.class);
                VerficationActivity.checkForConfigButtonInteraction();
                if (countDownstart == true) {
                    countDownTimerSuggestion.cancel();
                    readFile(fileName, 0, "");
                    try {
                        writeFile(fileName, fileDataJSON.getLong("suggestion_end"), "", fileDataJSON, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(nextActivity);
                } else {
                    readFile(fileName, 0, "");
                    try {
                        writeFile(fileName, fileDataJSON.getLong("suggestion_end"), "", fileDataJSON, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(nextActivity);
                }
            }
        });
        reservateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }


    public void changeAktivitys() {
        if (checkForActiveRoom == true) {
            if (boolean_endSuggestion == true) {
                checkTime = true;
                setDynamicEndSuggestionTime(0, time_endSuggestion);
                roomID.setText(activeRoom);
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
    }


    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
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
                    if (endTime != 0) {
                        extraData.put("endSuggestionTime", endTime);
                    }
                    if (room_ID.equals("_") == false) {
                        extraData.put("room_id", roomID);
                    }
                    writeOnOutput.write(extraData.toString());
                }
                if (newData != null && type.toLowerCase().contains("buchen")) {
                    extraData.put("endBookingTime", endTime);
                }
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


    // Überprüft aktive timestamps für Räume (für den fall das die App geschloßen wird)
    private boolean checkForActiveRooms() {
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
            if (boolean_endSuggestion == false && boolean_endReservation == false && boolean_endBooking == false) {
                fileDataJSON.remove("room_id");
                boolean_activeRoom = false;
                checkForActiveRoom = false;
            } else {
                activeRoom = fileDataJSON.getString("room_id");
                boolean_activeRoom = true;
                checkForActiveRoom = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writeFile(fileName, 0, "", fileDataJSON, "");
        changeAktivitys();
        return checkForActiveRoom;
    }


    // GET-Request
    // Fragt beim start der Aktivität im Server einen freien Raum an und liefert das Ergebnis zurück
    // Alternativ aufgerufen wenn die Vorschlagszeit abgelaufen ist
    private void askForFreeRoom() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user", fileDataJSON.getString("email"));
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
                    roomID.setText(response.getJSONObject("johntitor").getString("room_nr")); // eventuell noch dynamischer anpassen, oder so lassen wenn wir die datennamen so definieren
                    currentRoom = response.getJSONObject("johntitor");
                    long endSuggestionTime;
                    endSuggestionTime = response.getJSONObject("johntitor").getLong("suggestion_end");
                    writeFile(fileName, response.getJSONObject("johntitor").getLong("suggestion_end"), response.getJSONObject("johntitor").getString("room_nr"), null, "");//Schreibe erhaltene Raum/endzeit in den internen Speicher
                    checkTime = true;
                    setDynamicEndSuggestionTime(endSuggestionTime, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                    roomID.setText("Kein JSON Format gefunden.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //postJsonRequest.setTag(REQUESTTAG);
        mRequestQueuePOST.add(postJsonRequest);
    }


    // Um die verbleibende Zeit bis zum Ablauf des Vorschlags dynamisch zu aktuallisieren
    private void setDynamicEndSuggestionTime(long endSuggestionTime, long activeEndSuggestionTime) {
        long tmp = 0;
        if (activeEndSuggestionTime != 0 && endSuggestionTime == 0) {
            tmp = activeEndSuggestionTime - System.currentTimeMillis();
        }
        if (endSuggestionTime != 0 && activeEndSuggestionTime == 0) {
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
        countDownstart = true;
        countDownTimerSuggestion.start();
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
                    Toast.makeText(getApplicationContext(), "QR-Code erkannt.", Toast.LENGTH_SHORT).show();
                    resultBooking();
                } else {
                    qrResult = "Kein QR-Code gefunden.";
                    //TODO
                    // auf Seite bleiben, NICHT auf BarcodeResult.java wechseln
                }
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
            jsonBody.put("room_nr", fileDataJSON.getString("room_id"));
            jsonBody.put("user", fileDataJSON.getString("email"));
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
                    postOK = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
                    readFile(fileName, 0, "");
                    writeFile(fileName, bookingTime_end, "", fileDataJSON, "buchen");
                    startActivity(nextActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Buchung gescheitert.", Toast.LENGTH_SHORT).show();
                }
                postOK = false;
            }
        };
    }


    //POST-Request
    // Fragt eine Reservierung des Raumes an und leitet den Benutzer bei erfolgreicher Reservierung weiter
    private void reservation() {

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("room_nr", fileDataJSON.getString("room_id"));
            jsonBody.put("user", fileDataJSON.getString("email"));
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
                    postOk = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                roomID.setText("Server antwortet nicht.");
                postOk = false;
            }
        });
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
                    checkTime = true;
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
}

