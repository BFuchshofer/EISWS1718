package com.example.basti.findaroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
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
    private static boolean interactFromConfigBtn = false;
    private FileInputStream input;
    private FileOutputStream output;
    private String userFile;
    private boolean fileFound = false;
    private JSONObject fileDataJSON;
    private String fileData;
    private EditText url;
    private EditText email;
    private Button sendVerification;
    private Button cancelVerification;
    Intent startActivity;


    // Um zu überprüfen ob die VerificationActivity.java über einen Config Button aufgerufen wird.
    public static void checkForConfigButtonInteraction() {
        interactFromConfigBtn = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userFile = getString(R.string.userFile);
        try {
            input = openFileInput(userFile);
            fileFound = true; // Wenn bereits ein File existiert
            openFileInput(userFile).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileFound = false; // Falls noch kein File existiert (erster Start)
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFile();

        // Überprüfe beim Start ob es ein erster Start ist oder ob auf die Einstellungen zugegriffen werden soll
        if (fileFound == false || (fileFound == true && interactFromConfigBtn == true)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);

            startActivity = new Intent(getApplicationContext(), StartActivity.class); // Initialisierung für die Weiterleitung auf den Homescreen

            url = (EditText) findViewById(R.id.url);
            email = (EditText) findViewById(R.id.email);
            sendVerification = (Button) findViewById(R.id.sendVerification);
            cancelVerification = (Button) findViewById(R.id.cancelVerification);

            // Lade die bestehenden Daten aus dem File in die entsprechenden Textfelder.
            if (fileFound == true) {
                try {
                    readFile();
                    email.setText(fileDataJSON.getString("email"));
                    url.setText(fileDataJSON.getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Speichert die Inhalte aus den Textfeldern in eine Datei um sie später wiederzuverwenden
            sendVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interactFromConfigBtn = false;
                    writeFile();
                }
            });

            // Beendet diese Aktivität, entweder zurück in den HomeScreen oder bei erstmaligem Start der App wird diese geschloßen um zu gewährleisten das nur registreirte Benutzer die App nutzen
            cancelVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                    if (interactFromConfigBtn == true) {
                        startActivity(startActivity);
                    } else {
                        finish();
                        System.exit(0);
                    }
                }
            });

            // Überspringt diese Aktivität falls bereits Benutzerdaten vorliegen (kein erstmaliger Start)
        } else if (fileFound == true) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            startActivity = new Intent(getApplicationContext(), StartActivity.class); // Initialisierung für die Weiterleitung auf den Homescreen
            startActivity(startActivity);
        }
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher um sie später wiederzuverwenden
    public void writeFile() {

        try {
            JSONObject userConfig = new JSONObject();
            // Püfen ob die eingegebene Email Adresse ein gültiges Format hat.
            if ((email.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$")) == true) { //https://www.computerbase.de/forum/showthread.php?t=677550
                output = openFileOutput(userFile, MODE_PRIVATE);
                OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
                userConfig.put("email", email.getText().toString());
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


    // Gibt alle Daten aus dem TextFile "userData.json" aus
    public void readFile() {

        try {
            FileInputStream fileIn = openFileInput(userFile);
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
            fileDataJSON = data;
            fileData = stringData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
