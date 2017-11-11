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
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class ReservateActivity extends AppCompatActivity {

    TextView remainingTimeToBook;
    TextView roomID;
    Button bookRoom;
    Button cancelReservation;
    TextView infoText1;
    TextView infoText2;
    public static String qrResult;
    String qrCodeErkannt = "QR-Code erkannt!";
    public boolean checkTime = true;
    private CountDownTimer countDownTimerBooking;
    private CountDownTimer countDownTimerBookingShort;
    private CountDownTimer countDownTimerCancelReservationShort;

    private String givenRoom;
    private boolean getOK = false;
    private boolean postOK = false;
    public static long bookingTime_end; // Verbleibende Zeit bis die Buchung ausläuft


    public boolean confirmation;

    // Check fo actives
    public long time_endReservation;
    public boolean boolean_endReservation;
    public String activeRoom;
    public boolean boolean_activeRoom;

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
    private FileOutputStream output;

    //public static JSONObject fileDataJSON = StartActivity.returnJSONObject();
    public static JSONObject fileDataJSON;
    static final int READ_BLOCK_SIZE = 100;
    static public long remainingTimeReservation;

    // Server URLs
    public String url;
    public String urlBooking;
    public String urlReservationCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        readFile(fileName, 0, "");
        try {
            url = fileDataJSON.getString("url");
            urlBooking = url + "/room/booking";
            urlReservationCancel = url + "/room/cancelreservation";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservate);

        setDynamicEndTimeReservation(remainingTimeReservation, 0);

        remainingTimeToBook = (TextView) findViewById(R.id.remainingTimeToBook);
        roomID = (TextView) findViewById(R.id.book_roomID);
        try {
            roomID.setText(currentRoom.getString("number"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bookRoom = (Button) findViewById(R.id.bookRoom);
        infoText1 = (TextView) findViewById(R.id.infoText1);
        infoText2 = (TextView) findViewById(R.id.infoText2);
        cancelReservation = (Button) findViewById(R.id.cancelReservation);
        cancelReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelReservation();
            }
        });

        // Passt den CountDown entsprechend der bereits vorligenden Buchung an.
        if (StartActivity.returnActiveTimeReservation() != 0) {
            time_endReservation = StartActivity.returnActiveTimeReservation();
            setDynamicEndTimeReservation(0, time_endReservation);
        } else {
            remainingTimeReservation = StartActivity.returnRemainingTimeReservation();

            writeFile(remainingTimeReservation);

            setDynamicEndTimeReservation(remainingTimeReservation, 0);

        }

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

                    startScanner();
                } else {
                    roomID.setText("Reservierung abgelaufen.");
                }
            }
        });

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


    private boolean checkRemainingTime() {
        // Wird im Countdown gesetzt
        return checkTime;
    }



    // Um die verbleibende Zeit bis zum Ablauf der Reservierung dynamisch zu aktuallisieren
    private void setDynamicEndTimeReservation(long endTimeReservation, long activeEndReservationTime) {

        long tmp;
        if (activeEndReservationTime != 0) {
            tmp = activeEndReservationTime - System.currentTimeMillis();
        } else {
            tmp = endTimeReservation - System.currentTimeMillis();
        }

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

    // Buche den Raum im System
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

                    countDownTimerBookingShort.start();
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
        countDownTimerBookingShort = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), BarcodeResult.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?

                    //TODO
                    //entferne den Vorschlag timestamp aus dem File
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



    // Überprüft ob die Zeit für eine Buchung des Raumes nicht <= 0 ist
    private boolean checkRemainingTimeForBooking() {

        // TODO
        // Read remainingTimeToBook from variable and check
            // if (remainingTimeToBook >=0)
                // checkTime = true;

        return checkTime;

    }

    public void startScanner() {
        Intent nextActivity = new Intent(getApplicationContext(), BarcodeScanner.class);
        startActivityForResult(nextActivity, 0);
    }

    // Wertet das Ergebnis vom QR-Code Scan aus
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {

                // Wenn ein QR-Code gefunden wurde
                if (data != null) {
                    roomID.setText("QR-Code erkannt!");
                    //bookRoom.setText("");
                    //cancelReservation.setText("");
                    remainingTimeToBook.setText("");
                    infoText1.setText("");
                    infoText2.setText("");

                    Barcode qrCode = data.getParcelableExtra("barcode");
                    qrResult = qrCode.displayValue;

                } else {
                    qrResult = "0";

                }

                compareRooms();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            roomID.setText("Barcode scannen war fehlerhaft.");
        }
    }

    //Storniere die Reservierung
    private void cancelReservation() {


        JSONObject jsonBody = new JSONObject();
        try {

            jsonBody.put("room_nr", currentRoom.getString("number"));
            jsonBody.put("user", fileDataJSON.getString("email") );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRequestQueuePOST = Volley.newRequestQueue(this);

        postJsonRequest = new JsonObjectRequest(Request.Method.POST, urlReservationCancel, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    // Der Response vom POST muss im body daten mitliefern! So  umsetzen? (REST konform?) Oder doch lieber ein GET machen?
                    confirmation = response.getJSONObject("johntitor").getBoolean("confirmation");
                    postOK = true;

                    countDownTimerCancelReservationShort.start();
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

        countDownTimerCancelReservationShort = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Intent nextActivity = new Intent(getApplicationContext(), StartActivity.class);
                if (postOK == true) { // unterschiedliche Anfragen unterscheiden? Wie umsetzen?

                    if (confirmation == true) {

                        //TODO
                        // entferne den Reservierungs timestamp aus dem File

                        startActivity(nextActivity);
                        Toast.makeText(getApplicationContext(), "Stornierung erfolgreich.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Stornierung gescheitert.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Keine Antwort vom Server.", Toast.LENGTH_SHORT).show();
                }
                postOK = false;
                //confirmation = false;
            }
        };

    }

    // Weiterleitung zu Buchungsübersicht
    private void compareRooms() {

        try {
            if (qrResult.equals(currentRoom.getString("number"))) {
                book();
            } else {
                Toast.makeText(getApplicationContext(), "Fehler, gescannter QR-Code gehört nicht zur Reservierung.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fehler, QR-Code konnte nicht verglichen werden.", Toast.LENGTH_SHORT).show();
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
