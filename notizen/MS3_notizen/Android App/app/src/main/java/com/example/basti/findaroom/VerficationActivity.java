package com.example.basti.findaroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class VerficationActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;
    //Common variables
    public static boolean interactFromConfigBtn = false;
    //File
    public FileInputStream input;
    public FileOutputStream output;
    public String fileName = "internalData.json";
    public boolean fileFound = false;
    public JSONObject fileDataJSON;
    public String fileData;
    //Widgets
    EditText url;
    EditText email;
    Button sendVerification;
    Button cancelVerification;

    // Um zu überprüfen ob die VerificationActivity.java über einen Config Button aufgerufen wird.
    public static void checkForConfigButtonInteraction() {
        interactFromConfigBtn = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
Log.i("TEST", "TEST");
        try {
            input = openFileInput(fileName);
            fileFound = true; // Wenn bereits ein File existiert
            openFileInput(fileName).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileFound = false; // Falls noch kein File existiert (erster Start)
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readFile();

        // Überprüfe beim start ob es ein erster start ist oder ob auf die Einstellungen zugegriffen werden soll
        if (fileFound == false || (fileFound == true && interactFromConfigBtn == true)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            url = (EditText) findViewById(R.id.url);
            email = (EditText) findViewById(R.id.email);

            // Lade die bestehenden Daten aus dem File in die Textzeilen.
            if (fileFound == true) {
                try {
                    readFile();
                    email.setText(fileDataJSON.getString("email"));
                    url.setText(fileDataJSON.getString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            sendVerification = (Button) findViewById(R.id.sendVerification);
            sendVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interactFromConfigBtn = false;
                   // readFile(fileName);
                    writeFile();
                }
            });
            cancelVerification = (Button) findViewById(R.id.cancelVerification);
            cancelVerification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO
                    if (interactFromConfigBtn == true) {
                        verification();
                    } else {
                        finish();
                        System.exit(0);
                    }
                }
            });

        } else if (fileFound == true) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            verification();
        }
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile() {

            try {
                JSONObject userConfig = new JSONObject();
                if ((email.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$")) == true) { //https://www.computerbase.de/forum/showthread.php?t=677550
                    output = openFileOutput(fileName, MODE_PRIVATE);
                    OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
                    userConfig.put("email", email.getText().toString());
                    userConfig.put("url", url.getText().toString());
                    writeOnOutput.write(userConfig.toString());
                    writeOnOutput.close();
                    verification();
                } else {
                    Toast.makeText(getApplicationContext(), "Keine gültige Email erkannt. Bitte korigieren Sie ihre Eingabe", Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {

            } catch (IOException e) {

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                        Toast.LENGTH_LONG).show();
            }


            // Wenn ein File gefunden wurde lade die Daten aus diesem in die Textfelde
            /*
            if (fileFound == true) {
                JSONObject userConfig = new JSONObject();
                if ((email.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$")) == true) { //https://www.computerbase.de/forum/showthread.php?t=677550
                    userConfig.put("email", email.getText().toString());
                    userConfig.put("url", url.getText().toString());
                    writeOnOutput.write(userConfig.toString());
                    verification();
                }
            } else {

                JSONObject userConfig = new JSONObject();
                try {
                    //Überprüft die Email auf korrektes Format und speichert sie ein
                    if ((email.getText().toString().matches("^[\\w\\.=-]+@[\\w\\.-]+\\.[\\w]{2,4}$")) == true) { //https://www.computerbase.de/forum/showthread.php?t=677550
                        if ((userConfig.has("email") == false) || (userConfig.has("email") && userConfig.getString("email").equals(email.getText().toString()) == false)) {
                            userConfig.put("email", email.getText().toString());
                        }
                        if ((userConfig.has("url") == false) || (userConfig.has("url") && userConfig.getString("url").equals(url.getText().toString()) == false)) {
                            userConfig.put("url", url.getText().toString());
                        }
                        writeOnOutput.write(userConfig.toString());
                        verification();
                    } else {
                        Toast.makeText(getApplicationContext(), "Keine gültige Email erkannt. Bitte korigieren Sie ihre Eingabe", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            */
    }


    // Gibt alle Daten aus dem TextFile aus
    public void readFile() {


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
            Log.i("STRINGDATA: ", stringData);
            JSONObject data = new JSONObject(stringData);
            Log.i("FILEDATA: ", data.toString());
            fileDataJSON = data;
            fileData = stringData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Weiterleitung auf die nächste Aktivität
    private void verification() {

        Intent nextActivity = new Intent(this, StartActivity.class);
        startActivity(nextActivity);
    }
}
