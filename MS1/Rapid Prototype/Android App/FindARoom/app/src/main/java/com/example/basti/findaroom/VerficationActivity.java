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

    TextView textView;
    public Button sendVerification;

    FileInputStream input;
    FileOutputStream output;
    private String configFile = "internalData.json";
    private boolean fileFound = false;
    public static boolean interactFromConfigBtn = false;

    static final int READ_BLOCK_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            input = openFileInput(configFile);
            fileFound = true; // Wenn bereits ein File existiert
            openFileInput(configFile).close();
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
            textView = (TextView) findViewById(R.id.textView);

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




    private void verification() {

        Intent nextActivity = new Intent(this, StartActivity.class);
        startActivity(nextActivity);
    }
}
