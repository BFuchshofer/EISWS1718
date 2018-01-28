package com.example.basti.findaroom;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class VerificationActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private static boolean interactFromConfigBtn = false;
    public FunctionsBasic functionsBasic = new FunctionsBasic();
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
    private FileInputStream fileIn;
    private JSONObject userConfig = new JSONObject();
    private Context context = this;

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
                    userData = functionsBasic.readFileObject(context, userFileName);
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
                        functionsBasic.writeFile(context, beaconFileName, null, new JSONArray()); // Erstelle Beacon-File
                        functionsBasic.writeFile(context, requestFileName, new JSONObject(), null); // Erstelle Request-File
                    }
                    if (checkTextInput()) {
                        functionsBasic.writeFile(context, userFileName, userConfig, null);
                        startActivity(startActivity);
                        interactFromConfigBtn = false;
                    }
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

    public boolean checkTextInput() {
        // Püfen ob die eingegebene Email Adresse ein gültiges Format hat.
        if ((user.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$"))) { //https://www.computerbase.de/forum/showthread.php?t=677550
            try {
                userConfig.put("user", user.getText().toString());
                userConfig.put("url", url.getText().toString());
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Fehler bei der Formatierung, bitte Administrator kontaktieren.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Keine gültige Email-Adresse erkannt. Bitte korigieren Sie Ihre Eingabe.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        backBtnToast.show();
    }

}
