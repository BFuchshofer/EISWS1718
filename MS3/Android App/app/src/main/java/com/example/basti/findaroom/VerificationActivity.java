package com.example.basti.findaroom;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class VerificationActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    private final static int REQUEST_ENABLE_BT = 1;
    private static boolean interactFromConfigBtn = false;
    Intent startActivity;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private String userFileName;
    private String beaconFileName;
    private String requestFileName;
    private boolean fileFound = false;
    private JSONObject userData;
    private EditText url;
    private EditText user;
    private Button sendVerification;
    private Button cancelVerification;
    private Toast backBtnToast;
    private String status = "LOGSTATUS";
    private FileInputStream fileIn;
    private FileOutputStream fileOut;

    // Um zu überprüfen ob die VerificationActivity.java über einen Config Button aufgerufen wird.
    public static void checkForConfigButtonInteraction() {
        interactFromConfigBtn = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        userFileName = getString(R.string.userFile);
        beaconFileName = getString(R.string.beaconFile);
        requestFileName = getString(R.string.requestFile);

        try {
            fileIn = openFileInput(userFileName);
            fileFound = true; // Wenn bereits ein File existiert
            openFileInput(userFileName).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileFound = false; // Falls noch kein File existiert (erster Start)
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Überprüfe beim Start ob es ein erster Start ist oder ob auf die Einstellungen zugegriffen werden soll
        if (!fileFound || (fileFound && interactFromConfigBtn)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);

            backBtnToast = Toast.makeText(getApplicationContext(), getString(R.string.backBtnToast), Toast.LENGTH_SHORT);

            startActivity = new Intent(getApplicationContext(), StartActivity.class); // Initialisierung für die Weiterleitung auf den Homescreen

            url = (EditText) findViewById(R.id.url);
            user = (EditText) findViewById(R.id.user);
            sendVerification = (Button) findViewById(R.id.sendVerification);
            cancelVerification = (Button) findViewById(R.id.cancelVerification);

            // Lade die bestehenden Daten aus dem File in die entsprechenden Textfelder.
            if (fileFound) {
                try {
                    readFile(userFileName);
                    user.setText(userData.getString("user"));
                    url.setText(userData.getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Speichert die Inhalte aus den Textfeldern in eine Datei um sie später wiederzuverwenden
            sendVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!fileFound) { // Beim ersten App Start werden die benötigen Files angelegt
                        writeFile(beaconFileName); // Erstelle Beacon-File
                        writeFile(requestFileName); // Erstelle Request-File
                    }
                    writeFile(userFileName); // Schreibe Textfelder Daten in das User-File und lade die Startseite
                    interactFromConfigBtn = false;
                }
            });

            // Beendet diese Aktivität, entweder zurück in den HomeScreen oder bei erstmaligem Start der App wird diese geschloßen um zu gewährleisten das nur registreirte Benutzer die App nutzen
            cancelVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (interactFromConfigBtn) {
                        startActivity(startActivity);
                    } else {
                        finish();
                        System.exit(0);
                    }
                }
            });

            // Überspringt diese Aktivität falls bereits Benutzerdaten vorliegen (kein erstmaliger Start)
        } else if (fileFound) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            startActivity = new Intent(getApplicationContext(), StartActivity.class); // Initialisierung für die Weiterleitung auf den Homescreen
            startActivity(startActivity);
        }
    }

    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher um sie später wiederzuverwenden
    public void writeFile(String fName) {

        if (fName.contains(userFileName)) {
            try {
                JSONObject userConfig = new JSONObject();
                // Püfen ob die eingegebene Email Adresse ein gültiges Format hat.
                if ((user.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$"))) { //https://www.computerbase.de/forum/showthread.php?t=677550
                    fileOut = openFileOutput(fName, MODE_PRIVATE);
                    OutputStreamWriter writeOnOutput = new OutputStreamWriter(fileOut);
                    userConfig.put("user", user.getText().toString());
                    userConfig.put("url", url.getText().toString());
                    writeOnOutput.write(userConfig.toString());
                    writeOnOutput.close();
                    startActivity(startActivity);
                } else {
                    Toast.makeText(getApplicationContext(), "Keine gültige Email erkannt. Bitte korigieren Sie ihre Eingabe", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (fName.contains(beaconFileName)) {
            try {
                FileOutputStream output = openFileOutput(fName, MODE_PRIVATE);
                OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

                JSONArray array = new JSONArray();

                writeOnOutput.write(array.toString());
                writeOnOutput.close();
                Log.i(status, "First write beaconfile");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (fName.contains(requestFileName)) {
            try {
                FileOutputStream output = openFileOutput(fName, MODE_PRIVATE);
                OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

                JSONObject object = new JSONObject();

                writeOnOutput.write(object.toString());
                writeOnOutput.close();
                Log.i(status, "First write requestfile");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    // Gibt alle Daten aus dem TextFile fName aus
    public void readFile(String fName) {

        if (fName.contains(userFileName)) {
            try {
                FileInputStream fileIn = openFileInput(fName);
                InputStreamReader InputRead = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String stringData = "";
                int charRead;
                while ((charRead = InputRead.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    stringData += readstring;
                }
                InputRead.close();
                fileIn.close();
                JSONObject data = new JSONObject(stringData);
                Log.i("LOGSTATUS", "readFile data in verification: " + data);
                userData = data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
