package com.example.basti.findaroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class VerficationActivity extends AppCompatActivity {

    EditText vorname;
    EditText nachname;
    EditText id;
    EditText url;
    EditText email;

    public Button sendVerification;

    FileInputStream input;
    FileOutputStream output;
    private String configFile = "internalData.json";
    private boolean fileFound = false;
    public static boolean interactFromConfigBtn = false;

    public JSONObject fileDataJSON;

    static final int READ_BLOCK_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            input = openFileInput(configFile);
            fileFound = true; // Wenn bereits ein File existiert
            openFileInput(configFile).close();
            readFile(configFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fileFound = false; // Falls noch kein File existiert (erster Start)
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (fileFound == false || (fileFound == true && interactFromConfigBtn == true)) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            vorname = (EditText) findViewById(R.id.vorname);
            nachname = (EditText) findViewById(R.id.nachname);
            id = (EditText) findViewById(R.id.id);
            url = (EditText) findViewById(R.id.url);
            email = (EditText) findViewById(R.id.email);

            if (fileFound == true) {
                try {
                    vorname.setText(fileDataJSON.getString("vorname"));
                    nachname.setText(fileDataJSON.getString("nachname"));
                    id.setText(fileDataJSON.getString("companyID"));
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

                    //TODO
                    // Prüfe ob gültige Werte in die Textzeilen eingetragen wurden
                    // Formatiere ggf. die Werte in ein entsprechendes Format

                    interactFromConfigBtn = false;
                    writeFile();
                }


            });
        } else if (fileFound == true) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_verfication);
            verification();
        }
    }
    // Um zu überprüfen ob die VerificationActivity.java über einen Config Button aufgerufen wird.
    public static void checkForConfigButtonInteraction() {
        interactFromConfigBtn = true;
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public void writeFile() {

        try {
            output = openFileOutput(configFile, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);

            JSONObject userConfig = new JSONObject();
            try {
                userConfig.put("vorname", vorname.getText().toString());
                userConfig.put("nachname", nachname.getText().toString());
                userConfig.put("companyID", id.getText().toString());
                userConfig.put("email", email.getText().toString());
                userConfig.put("url", url.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

           // JSONObject userData = new JSONObject();
           // writeOnOutput.write(userData.toString());
            writeOnOutput.write(userConfig.toString());


            writeOnOutput.close();
            verification();


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Daten konnten nicht gespeichert werden.",
                    Toast.LENGTH_LONG).show();
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
            //fileData = stringData;

        } catch (Exception e) {
            e.printStackTrace();
        }



    }




    private void verification() {

        Intent nextActivity = new Intent(this, StartActivity.class);
        startActivity(nextActivity);
    }
}
