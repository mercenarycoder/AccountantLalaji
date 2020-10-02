package com.vias.accountantlalaji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class Activity_NewQr extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
private ImageButton go_back_login;
private BarcodeReader barcodeReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__new_qr);
       go_back_login=(ImageButton)findViewById(R.id.go_back_login);
       go_back_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner2);

    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        try {
            //JSONObject object=new JSONObject(barcode.displayValue);
            Intent intent=new Intent(Activity_NewQr.this,Qr_WebsiteView.class);
            intent.putExtra("url",barcode.displayValue);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
//Toast.makeText(Qr_scanner.this,"Some other format qr code readen",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
