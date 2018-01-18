package com.example.basti.findaroom;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Basti on 17.01.2018.
 */

public class Basic extends Activity {



    public static  Context context;
    private static final int READ_BLOCK_SIZE = 100;

    //Schreibe Daten aus den Textfeldern als JSON in eine Datei im internen Speicher
    public static void writeFile(String fName, JSONObject data) {

        JSONArray curentData = readFile(fName);
        Log.i("dataobject0", curentData + "");
        JSONObject dataObject;
        try {
            dataObject = curentData.getJSONObject(0);
            Log.i("dataobject1", dataObject + "");
            FileOutputStream output = context.openFileOutput(fName, MODE_PRIVATE);
            OutputStreamWriter writeOnOutput = new OutputStreamWriter(output);
            dataObject.put("room_id", data.getString("room_id"));
            dataObject.put("remainingTime", data.getLong("remainingTime"));
            writeOnOutput.write(dataObject.toString());
            Log.i("dataobject2", dataObject + "");
            writeOnOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gibt alle Daten aus dem TextFile aus
    public static  JSONArray readFile(String fName) {

        try {
            FileInputStream fileIn = context.openFileInput(fName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String stringData = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                stringData += readstring;
            }
            InputRead.close();
            if (stringData.length() != 0) {
                JSONArray dataArray = new JSONArray(stringData);
                Log.i("readFile1", "readfile: " + dataArray);
                return dataArray;
            } else {

                JSONArray dataArray = new JSONArray();
                Log.i("readFile2", "readfile: " + dataArray);
                return dataArray;
            }

        } catch (Exception e) {
            JSONArray errorArray = new JSONArray();
            e.printStackTrace();
            Log.i("readFile", "readfile error: " + errorArray.toString());
            return errorArray;
        }

    }

}
