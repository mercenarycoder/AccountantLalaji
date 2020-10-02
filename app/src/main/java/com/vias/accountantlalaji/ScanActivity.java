package com.vias.accountantlalaji;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 1;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        // Set the scanner view as the content view


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                // Toast.makeText(MainActivity.this, "Permission is granted!" ,Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }

    }


    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(ScanActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);

    }

    public void onRequestPermissionResult(int requestCode, String pemission[], int grantResults[])
    {
        switch (requestCode)
        {
            case REQUEST_CAMERA :
                if (grantResults.length > 0)
                {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted)
                    {
                        // Toast.makeText(MainActivity.this, "Permission granted!" ,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(ScanActivity.this, "Permission denied!" ,Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            if (shouldShowRequestPermissionRationale(CAMERA))
                            {

                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }

                }
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        // Log.v("tag", rawResult.getText()); // Prints scan results
        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Toast.makeText(getApplicationContext(),rawResult.getText(),Toast.LENGTH_LONG).show();

        Sale.pText.setText(rawResult.getText());
        onBackPressed();

        Sale.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sale.pText.append(" ");
            }
        });

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

    }


    public void displayAlertMessage(String message, DialogInterface.OnClickListener Listner)
    {
        new AlertDialog.Builder(ScanActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", (DialogInterface.OnClickListener) Listner)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }





}