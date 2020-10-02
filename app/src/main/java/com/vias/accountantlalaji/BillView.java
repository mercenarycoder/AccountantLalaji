package com.vias.accountantlalaji;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;

public class BillView extends AppCompatActivity {


    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;

    private WebView mWebview;

    String bill;
    String invoiceNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);

        mWebview  = (WebView) findViewById(R.id.webView);

        invoiceNO = getIntent().getStringExtra("invoice_id");

        bill = getIntent().getStringExtra("billno");

        ImageView backBtn = (ImageView)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BillView.this, Sale_List.class);
                startActivity(i);
                finish();
            }
        });

        ImageView shareBtn = (ImageView)findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Uri uri = Uri.parse("smsto:" + "8109930803");
                Intent sIntent = new Intent(Intent.ACTION_SEND);
                sIntent.setType("text/plain");
                String shareBody = "Bill no : "+bill+"\n";

                String shareSub = "Bill no : "+bill;
                sIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sIntent, "Send Bill through "));

            }
        });


        ImageView wshareBtn = (ImageView)findViewById(R.id.wshareBtn);
        wshareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Bitmap bitmap1 = loadBitmapFromView(mWebview, mWebview.getWidth(), mWebview.getHeight());
                    saveBitmap(bitmap1);
                    Log.d("bitmap", String.valueOf(bitmap1));
                    ImageView img = (ImageView)findViewById(R.id.imagePDF);
                    img.setImageBitmap(bitmap1);

                    BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();

                    imageToPFD(bitmap);

                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Accountant_Lalaji/"+bill+".pdf");

                    //  File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/sdcard/Accountant_Lalaji/"+bill+".pdf");


                    if (file.exists()){

                        Uri uri = Uri.fromFile(file);

                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setAction(Intent.ACTION_SEND);
                        share.setType("application/pdf");
                        share.putExtra(Intent.EXTRA_STREAM, uri);
                        share.putExtra(Intent.EXTRA_TEXT, "Accountant Lalaji Bill ");
                        share.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(share, "Your title"));


                    }
                    else {

                        Toast.makeText(BillView.this, "No File available to view ", Toast.LENGTH_LONG).show();

                    }
                }catch (NullPointerException e){


                }








/*
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(Intent.createChooser(intent, "Your title"));*/

                /*
                String a="/root/sdcard/example.pdf";
                File file = new File(a);
                if (file.exists())
                {
                    Intent intent=new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(file);
                    intent.setDataAndType(uri, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try
                    {
                        startActivity(intent);
                    }
                    catch(ActivityNotFoundException e)
                    {
                        Toast.makeText(BillView.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(BillView.this, "No File available to view ", Toast.LENGTH_LONG).show();
                }*/


                /*File outputFile = new File(Environment.getDataDirectory(), "example.pdf");
                Uri uri = Uri.fromFile(outputFile);

                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setPackage("com.whatsapp");
                startActivity(share);
*/



            }
        });


        ImageView back = (ImageView) findViewById(R.id.printBtnn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WebView webView = new WebView(BillView.this);
                webView.setWebViewClient(new WebViewClient() {

                    public boolean shouldOverrideUrlLoading(WebView view, String url)
                    {
                        return false;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url)
                    {
                        createWebPrintJob(view);
                        mWebview = null;
                    }
                });

                //  String htmlDocument = "<html><body><h1>Android Print Test</h1><p>" + "This is some sample content.</p></body></html>";
                // webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
                webView.loadUrl("https://express.accountantlalaji.com/Api1/stock_sale_print/anjalivijaysujeetsharma/"+invoiceNO+"/no");
                mWebview = webView;

            }
        });




        String status = getIntent().getStringExtra("status");

        if (status.equals("sp")){

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


            mWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebview.setScrollbarFadingEnabled(false);


            final Activity activity = this;

            mWebview .loadUrl("https://express.accountantlalaji.com/Api1/stock_sale_print/anjalivijaysujeetsharma/"+invoiceNO+"/no");



        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(BillView.this, Sale_List.class);
        startActivity(i);
        finish();
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            printManager = (PrintManager) this
                    .getSystemService(Context.PRINT_SERVICE);
        }

        PrintDocumentAdapter printAdapter =
                null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            printAdapter = webView.createPrintDocumentAdapter();
        }

        String jobName = getString(R.string.app_name) + " Print Bill";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {

        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    public void saveBitmap(Bitmap bitmap) {


        File direct  = new File(Environment.getExternalStorageDirectory() + "/Accountant_Lalaji");

        if (!direct .exists()) {
            File wallpaperDirectory = new File("/sdcard/Accountant_Lalaji/");
            wallpaperDirectory.mkdirs();
        }


        File imagePath  = new File(new File("/sdcard/Accountant_Lalaji/"), bill+".jpg");
        if (imagePath.exists()) {
            imagePath.delete();
        }

        //File imagePath = new File("/sdcard/screenshotdemo.jpg");

        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // Toast.makeText(getApplicationContext(),imagePath.getAbsolutePath()+"",Toast.LENGTH_SHORT).show();
            boolean_save = true;

            //btn_screenshot.setText("Check image");

            Toast.makeText(getApplicationContext(),"Bill Saved to Accountant Lalaji Folder",Toast.LENGTH_LONG).show();

            Log.e("ImageSave", "Saveimage");

        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public  void  imageToPFD(Bitmap bitmap){

        Document document = new Document();

        String directoryPath = android.os.Environment.getExternalStorageDirectory().toString();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(directoryPath + "/Accountant_Lalaji/"+bill+".pdf")); //  Change pdf's name.
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Image image = null;  // Change image's name and extension.
        try {
            image = Image.getInstance(directoryPath + "/Accountant_Lalaji/"+bill+".jpg");
            //  ImageView img = (ImageView)findViewById(R.id.img);
            // image = Image.getInstance("");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
        image.scalePercent(scaler);
        image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();


       /* String filename = "AAAAAA";

        write(filename, bitmap);
        if (write(filename,bitmap)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }*/

    }



}
