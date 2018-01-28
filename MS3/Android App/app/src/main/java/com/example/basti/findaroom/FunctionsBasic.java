package com.example.basti.findaroom;

import android.app.Application;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class FunctionsBasic extends Application {

    private final int READ_BLOCK_SIZE = 100;
    public FileInputStream fileIn;
    public FileOutputStream fileOut;
    public InputStreamReader fReader;
    public OutputStreamWriter fWriter;
    public String userFileName = "userData.json";
    public String beaconFileName = "beaconData.json";
    public String requestFileName = "requestData.json";

    public FunctionsBasic functionsBasic(Context context) {
        return this;
    }

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher um sie später wiederzuverwenden
    public void writeFile(Context context, String fName, JSONObject obj, JSONArray arr) {

        if (fName.contains(userFileName) || fName.contains(requestFileName)) {
            try {
                fileOut = context.openFileOutput(fName, context.MODE_PRIVATE);
                fWriter = new OutputStreamWriter(fileOut);
                fWriter.write(obj.toString());
                fWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fName.contains(beaconFileName)) {
            try {
                fileOut = context.openFileOutput(fName, context.MODE_PRIVATE);
                fWriter = new OutputStreamWriter(fileOut);
                fWriter.write(arr.toString());
                fWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // Gibt alle Daten aus dem TextFile fName aus
    public JSONObject readFileObject(Context context, String fName) {
        JSONObject data;
        if (fName.contains(userFileName) || fName.contains(requestFileName)) {
            try {
                fileIn = context.openFileInput(fName);
                fReader = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String stringData = "";
                int charRead;
                while ((charRead = fReader.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    stringData += readstring;
                }
                fReader.close();
                fileIn.close();
                data = new JSONObject(stringData);

                return data;
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONObject();
            }
        }
        return null;
    }

    // Gibt alle Daten aus dem TextFile fName aus
    public JSONArray readFileArray(Context context, String fName) {
        JSONArray data;
        if (fName.contains(beaconFileName)) {
            try {
                fileIn = context.openFileInput(fName);
                fReader = new InputStreamReader(fileIn);
                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String stringData = "";
                int charRead;
                while ((charRead = fReader.read(inputBuffer)) > 0) {
                    String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                    stringData += readstring;
                }
                fReader.close();
                fileIn.close();
                data = new JSONArray(stringData);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
                return new JSONArray();
            }
        }
        return null;
    }
}
