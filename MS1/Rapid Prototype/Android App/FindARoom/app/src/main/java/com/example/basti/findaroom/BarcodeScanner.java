package com.example.basti.findaroom;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class BarcodeScanner extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView cameraText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        cameraText = (TextView) findViewById(R.id.cameraText);
        cameraText.setText("Kamera bitte auf den QR-Code fokussieren.");
        createCameraSource();
    }

    private void createCameraSource() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(BarcodeScanner.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(BarcodeScanner.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                //TODO
                /* Ergebnis des QR-Codes enthält unter anderem die RaumID */

                // Überprüfen ob gefundene RaumID bereits einem anderen Benutzer vorgeschlagen wurde --> Buchung nicht möglich!
                // Überprüfen ob gefundene RaumID buchbar ist
                    // Ist der Raum gerade reserviert?  --> JA: Buchung nicht möglich! | NEIN: Buchung möglich.
                    // Ist der Raum gerade gebucht?     --> JA: Buchung nicht möglich! | NEIN: Buchung möglich.
                    // Liegt in absehbarer Zeit (festlegen!) eine Reservierung/Buchung dieses Raumes vor? --> JA: Buchung nicht möglich! | NEIN: Buchung möglich.

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size()>0) {
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0));
                    setResult(CommonStatusCodes.SUCCESS,intent);
                    finish();
                }


            }
        });
    }

}
