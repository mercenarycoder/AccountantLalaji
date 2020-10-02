package com.vias.accountantlalaji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
public class Qr_WebsiteView extends AppCompatActivity {
private WebView webView;

private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr__website_view);
    Intent intent=getIntent();
    String url=intent.getStringExtra("url");
    webView=(WebView)findViewById(R.id.Hare_Krishna);
    btn=(Button)findViewById(R.id.simon_goback);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });
    webView.loadUrl(url);
    }
}
