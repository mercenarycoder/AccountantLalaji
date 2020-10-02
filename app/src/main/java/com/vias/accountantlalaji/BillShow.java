package com.vias.accountantlalaji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class BillShow extends AppCompatActivity {


    private WebView mWebview;
    ProgressBar pbar;
    FrameLayout frameLayout;
    GifImageView progressbarnew;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bill_show);

        url = getIntent().getStringExtra("url");
        progressbarnew = (GifImageView)findViewById(R.id.progressBarNew);

        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        pbar = (ProgressBar)findViewById(R.id.progressBar);
        pbar.setMax(100);

        mWebview  = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        mWebview.setFocusable(true);
        mWebview.setFocusableInTouchMode(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setDatabaseEnabled(true);
        mWebview.getSettings().setAppCacheEnabled(true);
        mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mWebview.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        mWebview.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setAllowContentAccess(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setDisplayZoomControls(false);
        mWebview.getSettings().setDomStorageEnabled(true);
        // mWebview.getSettings().setTextZoom(fontSize);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        }
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.requestDisallowInterceptTouchEvent(true);

        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebview.setScrollbarFadingEnabled(false);
        mWebview.getSettings().setBuiltInZoomControls(true);

//        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        final Activity activity = this;
        mWebview.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different      scales.
                // The progress meter will automatically disappear when we reach 100%

                // frameLayout.setVisibility(View.VISIBLE);
                progressbarnew.setVisibility(View.VISIBLE);
                pbar.setProgress(progress);

                if (progress == 100){
                    //  frameLayout.setVisibility(View.GONE);
                    progressbarnew.setVisibility(View.GONE);
                    super.onProgressChanged(view, progress);
                }

                // activity.setProgress(progress * 1000);
            }
        });

        mWebview.setVerticalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pbar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        pbar.setProgress(0);

        mWebview.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, int errorCode, String      description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description,      Toast.LENGTH_SHORT).show();
            }

            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //  frameLayout.setVisibility(View.VISIBLE);
                progressbarnew.setVisibility(View.VISIBLE);

                return true;

            }

        });

        mWebview .loadUrl(url);

        mWebview.addJavascriptInterface(new Object()
        {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {
                // Toast.makeText (MainActivity.this, "Dealer ", Toast.LENGTH_SHORT).show();

              //  Intent d = new Intent(MainActivity.this, DealerLogin.class);
               // startActivity(d);

            }
        }, "dealer_ok");

        mWebview.addJavascriptInterface(new Object()
        {
            @JavascriptInterface           // For API 17+
            public void performClick(String strl)
            {

                // Toast.makeText (MainActivity.this, "User ", Toast.LENGTH_SHORT).show();
               // Intent d = new Intent(MainActivity.this, UserLogin.class);
              //  startActivity(d);
            }
        }, "user_ok");
    }

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(mWebview.canGoBack()) {
            mWebview.goBack();
        } else {


            new AlertDialog.Builder(this)

                    .setTitle("Accountant Lalaji")
                    .setMessage("Are you sure you want to close?")
                    .setIcon(R.drawable.logoo)

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebview.canGoBack()){
                mWebview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }




}
