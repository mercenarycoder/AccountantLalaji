package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.parser.Line;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sale_List extends AppCompatActivity {

    TextView saleViewMore;
    String Allfrom = "0";
    String Allto = "10";

    String Todayfrom = "0";
    String Todayto = "10";

    String Yesfrom = "0";
    String Yesto = "10";

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL, linkLL,qr_code_scanner,expenses_LL;
    ImageView dmenu_back ,dmenu_supporttLL, dmenu_logouttLL;

    ImageView sale_list_left, sale_list_right;

    TextView salelist_dayTV, todayTotalAmountTV, todayTotalBillTV;

    Button saleBtn;
    LinearLayout tab_purchaseLL, tab_productLL, tab_contactlistLL, tab_reportlistLL;
    RelativeLayout saleListRL;

    private ProgressDialog progressDialog;
    private List<CustomerList> customerLists = new ArrayList<>();

    private RecyclerView recyclerViewSelect_SaleList;
    private RecyclerView.Adapter adapterSelect_SaleList;
    private List<SaleList_Listitem> listitems_Salelist;

    int click = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale__list);

        setUpToolbar();

        saleViewMore = (TextView)findViewById(R.id.saleViewMore);

        dmenu_back = (ImageView) findViewById(R.id.dmenuback);
        dmenu_sale_LL = (LinearLayout) findViewById(R.id.dmenu_saleLL);
        dmenu_purchaseLL = (LinearLayout) findViewById(R.id.dmenu_purchaseLL);
        dmenu_contactLL = (LinearLayout) findViewById(R.id.dmenu_contactLL);
        dmenu_producttLL = (LinearLayout) findViewById(R.id.dmenu_productLL);
        dmenu_hsntLL = (LinearLayout) findViewById(R.id.dmenu_hsntLL);
        dmenu_acctLL = (LinearLayout) findViewById(R.id.dmenu_acctLL);
        dmenu_supporttLL = (ImageView) findViewById(R.id.dmenu_supporttLL);
        dmenu_reporttLL = (LinearLayout) findViewById(R.id.dmenu_reporttLL);
        dmenu_logouttLL = (ImageView) findViewById(R.id.dmenu_logouttLL);
        linkLL = (LinearLayout) findViewById(R.id.linkLL);
        navigationView_SaleList = (NavigationView) findViewById(R.id.navigation_view_SaleList);
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Sale_List.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(Sale_List.this,Expenses_Activity.class);
               startActivity(i);
            }
        });
        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Sale_List.this, Profile.class);
                startActivity(i);

            }
        });

        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Sale_List.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Sale_List.this, Support.class);
                startActivity(i);

            }
        });




        sale_list_left = (ImageView) findViewById(R.id.sale_list_left);
        sale_list_right = (ImageView) findViewById(R.id.sale_list_right);
        salelist_dayTV = (TextView) findViewById(R.id.salelist_dayTV);

        todayTotalAmountTV = (TextView) findViewById(R.id.today_total_amountTV);
        todayTotalBillTV = (TextView) findViewById(R.id.today_total_billTV);

        saleBtn = (Button) findViewById(R.id.sale_btn);

        tab_purchaseLL = (LinearLayout) findViewById(R.id.tab_purchaseLL);
        tab_productLL = (LinearLayout) findViewById(R.id.tab_productlistLL);
        tab_contactlistLL = (LinearLayout) findViewById(R.id.tab_contactlistLL);
        tab_reportlistLL = (LinearLayout) findViewById(R.id.tab_reportlistLL);

        saleListRL = (RelativeLayout) findViewById(R.id.saleListRL);

        recyclerViewSelect_SaleList = (RecyclerView) findViewById(R.id.recyclerView_SaleList);
        recyclerViewSelect_SaleList.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerViewSelect_SaleList.setLayoutManager(linearLayoutManager);

        listitems_Salelist = new ArrayList<>();

        new TodaySaleDataList().execute();

        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Sale.class);
                startActivity(i);
            }
        });



        dmenu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
            }
        });

        dmenu_sale_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Sale.class);
                startActivity(i);
            }
        });

        dmenu_purchaseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Purchase_List.class);
                startActivity(i);
            }
        });
        dmenu_contactLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Customer_List.class);
                startActivity(i);
            }
        });
        dmenu_producttLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Product_List.class);
                startActivity(i);
            }
        });

        dmenu_hsntLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), HSN_List.class);
                startActivity(i);
            }
        });
        dmenu_acctLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Account_List.class);
                startActivity(i);
            }
        });
        dmenu_supporttLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Support.class);
                startActivity(i);
            }
        });
        dmenu_reporttLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(getApplicationContext(), Report.class);
                startActivity(i);
            }
        });
        dmenu_logouttLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();

                Toast.makeText(getApplicationContext(), "Logout Successfully ! ", Toast.LENGTH_LONG).show();

                SharedPreferences pref = getApplication().getSharedPreferences("Lalaji_Login", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                SharedPreferences pref2 = getApplication().getSharedPreferences("Lalaji_Product_JsonSata", 0);
                SharedPreferences.Editor editor2 = pref2.edit();
                editor2.clear();
                editor2.commit();

                SharedPreferences pref3 = getApplication().getSharedPreferences("Sale_data", 0);
                SharedPreferences.Editor editor3 = pref3.edit();
                editor3.clear();
                editor3.commit();

                SharedPreferences pref4 = getApplication().getSharedPreferences("Lalaji_Total", 0);
                SharedPreferences.Editor editor4 = pref4.edit();
                editor4.clear();
                editor4.commit();

                SharedPreferences pref5 = getApplication().getSharedPreferences("Lalaji_SaleData", 0);
                SharedPreferences.Editor editor5 = pref5.edit();
                editor5.clear();
                editor5.commit();

                Intent itoLogin = new Intent(getApplicationContext(), Login.class);
                itoLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(itoLogin);
                finish();


                drawerLayoutSaleList.closeDrawers();


            }
        });

        linkLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                String url = "http://www.accountantlalaji.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



        tab_purchaseLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Purchase_List.class);
                startActivity(i);
            }
        });
        tab_productLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Product_List.class);
                startActivity(i);
            }
        });
        tab_contactlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Customer_List.class);
                startActivity(i);
            }
        });
        tab_reportlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Report.class);
                startActivity(i);
            }
        });

        sale_list_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (click == 1) {
                    listitems_Salelist.clear();
                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                    ImageViewCompat.setImageTintList(sale_list_left, ColorStateList.valueOf(Color.parseColor("#ffffff")));

                    click = 2;
                    salelist_dayTV.setText("YESTERDAY");

                    new YesterdaySaleDataList().execute();

                }

                if (click == 3) {
                    listitems_Salelist.clear();
                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                    ImageViewCompat.setImageTintList(sale_list_left, ColorStateList.valueOf(Color.parseColor("#BDC3C7")));
                    ImageViewCompat.setImageTintList(sale_list_right, ColorStateList.valueOf(Color.parseColor("#BDC3C7")));

                    click = 1;
                    salelist_dayTV.setText("TODAY");

                    new TodaySaleDataList().execute();

                }


            }
        });

        sale_list_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click == 1) {
                    ImageViewCompat.setImageTintList(sale_list_right, ColorStateList.valueOf(Color.parseColor("#ffffff")));
                    click = 3;
                    salelist_dayTV.setText("ALL");
                    listitems_Salelist.clear();
                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                    new SaleDataList().execute();

                }

                if (click == 2) {
                    ImageViewCompat.setImageTintList(sale_list_right, ColorStateList.valueOf(Color.parseColor("#BDC3C7")));
                    ImageViewCompat.setImageTintList(sale_list_left, ColorStateList.valueOf(Color.parseColor("#BDC3C7")));

                    click = 1;
                    salelist_dayTV.setText("TODAY");
                    listitems_Salelist.clear();
                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                    new TodaySaleDataList().execute();

                }


            }
        });

        new SetupDrawerData().execute();


        saleViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewSale);
            //    scrollView.pageScroll(View.FOCUS_UP);


                if (click==3){

                    int from = Integer.parseInt(Allfrom);
                    int fromNew = from+10;
                    Allfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Allto);
                    int toNew = to+10;
                    Allto = String.valueOf(toNew);

                    new SaleDataList().execute();

                }

                if (click==2){

                    int from = Integer.parseInt(Yesfrom);
                    int fromNew = from+10;
                    Yesfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Yesto);
                    int toNew = to+10;
                    Yesto = String.valueOf(toNew);

                    new YesterdaySaleDataList().execute();

                }


                if (click==1){

                    int from = Integer.parseInt(Todayfrom);
                    int fromNew = from+10;
                    Todayfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Todayto);
                    int toNew = to+10;
                    Todayto = String.valueOf(toNew);

                    new TodaySaleDataList().execute();

                }


            }
        });


        ((ImageView)findViewById(R.id.facebookImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/accountantlalaji/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ((ImageView)findViewById(R.id.messengerImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Uri uri = Uri.parse("fb-messenger://user/100005727832736");
                Intent toMessenger= new Intent(Intent.ACTION_VIEW, uri);
                startActivity(toMessenger);
                try {
                    startActivity(toMessenger);
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(Sale_List.this, "Please Install Facebook Messenger",    Toast.LENGTH_LONG).show();
                }*/

                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");
                intent.setType("text/plain");
                intent.setPackage("com.facebook.orca");

                try
                {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException ex)
                {
                    //Toast.makeText(Sale_List.this,                           "Oups! Can't open Facebook messenger app right now.",                             Toast.LENGTH_SHORT).show();
                    String url = "https://www.facebook.com/accountantlalaji/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            }
        });

        ((ImageView)findViewById(R.id.whatsappImg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=919300131492";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }


    private class SaleDataList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Sale_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);
            String user_id = prefLogin.getString("user_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.sale_list);
            String auth = getString(R.string.auth_key);
            String json = jp.SaledataListJsonFromURL(url, auth, cmp_id, login_id, user_id ,Allfrom, "10");
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("salelist_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        String ta = jo.getString("all_amount");
                        if (ta != null && !ta.isEmpty() && !ta.equals("null")) {
                            Float f = Float.valueOf(ta);
                            String ss = new DecimalFormat("##.##").format(f);
                            todayTotalAmountTV.setText(ss);
                        }

                        String tbb = jo.getString("all_bill");
                        if (tbb != null && !tbb.isEmpty() && !tbb.equals("null")) {
                            todayTotalBillTV.setText(tbb);
                        }



                        try {
                           // listitems_Salelist.clear();
                            JSONArray jsonArray = jo.getJSONArray("salelist_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);

                            }

                            float results_total = 0;


                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                /*tb++;
                                todayTotalBillTV.setText(String.valueOf(tb));

                                String totalStr = jo2.getString("grand_total");
                                float results = 0;

                                if (totalStr != null && !totalStr.isEmpty() && !totalStr.equals("null")) {
                                    // result = Integer.parseInt(result);
                                    results = Math.round(Float.parseFloat(totalStr));
                                    results_total = results_total + results;

                                }*/

                                SaleList_Listitem listitem = new SaleList_Listitem(

                                        "1",
                                        jo2.getString("id"),
                                        jo2.getString("admin_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("customer_id"),
                                        jo2.getString("created_by"),
                                        "",
                                        jo2.getString("bill_no"),
                                        "",
                                        "",
                                        jo2.getString("bill_date"),
                                        "",
                                        "",
                                        "",
                                        jo2.getString("grand_total"),
                                        jo2.getString("total_paid"),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        jo2.getString("customer_name"),
                                        jo2.getString("customer_mobile"),
                                        jo2.getString("mode")
                                );

                                listitems_Salelist.add(listitem);
                                adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                                recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);


                            }

                           // double d = Double.parseDouble(String.valueOf(results_total));
                           // long lon = Math.round(d);
                           // todayTotalAmountTV.setText(String.valueOf(lon));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(saleListRL, error_response, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }

    }

    private class TodaySaleDataList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Sale_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);
            String user_id = prefLogin.getString("user_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.sale_list_today);
            String auth = getString(R.string.auth_key);
            String json = jp.SaledataListJsonFromURL(url, auth, cmp_id, login_id, user_id,Todayfrom, "10");
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("salelist_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    String today_date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                    if (response.equals("true")) {

                        String ta = jo.getString("today_amount");
                        if (ta != null && !ta.isEmpty() && !ta.equals("null")) {
                            Float f = Float.valueOf(ta);
                            String ss = new DecimalFormat("##.##").format(f);
                            todayTotalAmountTV.setText(ss);
                        }

                        String tbb = jo.getString("today_bill");
                        if (tbb != null && !tbb.isEmpty() && !tbb.equals("null")) {
                            todayTotalBillTV.setText(tbb);
                        }


                            try {
                         //   listitems_Salelist.clear();
                          //  adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                          //  recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                          //  todayTotalAmountTV.setText("0000");
                         //   todayTotalBillTV.setText("00");
                            JSONArray jsonArray = jo.getJSONArray("salelist_array");

                            final int numberc = jsonArray.length();
                            float results_total = 0;

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);
                            }

                            for (int ii = 0; ii < numberc; ii++) {

                                // todayTotalBillTV.setText(String.valueOf(ii+1));

                                JSONObject jo2 = jsonArray.getJSONObject(ii);


                              //  if (billdate.equals(today_date)) {

                                   /* tb++;
                                    todayTotalBillTV.setText(String.valueOf(tb));

                                    String totalStr = jo2.getString("grand_total");
                                    float results = 0;

                                    if (totalStr != null && !totalStr.isEmpty() && !totalStr.equals("null")) {
                                        // result = Integer.parseInt(result);
                                        results = Math.round(Float.parseFloat(totalStr));
                                        results_total = results_total + results;

                                    }
*/
                                    SaleList_Listitem listitem = new SaleList_Listitem(

                                            "1",
                                            jo2.getString("id"),
                                            jo2.getString("admin_id"),
                                            jo2.getString("cmp_id"),
                                            jo2.getString("customer_id"),
                                            jo2.getString("created_by"),
                                            "",
                                            jo2.getString("bill_no"),
                                            "",
                                            "",
                                            jo2.getString("bill_date"),
                                            "",
                                            "",
                                            "",
                                            jo2.getString("grand_total"),
                                            jo2.getString("total_paid"),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            jo2.getString("customer_name"),
                                            jo2.getString("customer_mobile"),
                                            jo2.getString("mode")
                                    );

                                    listitems_Salelist.add(listitem);
                                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);


                            }

                       //     double d = Double.parseDouble(String.valueOf(results_total));
                       //     long lon = Math.round(d);
                        //    todayTotalAmountTV.setText(String.valueOf(lon));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        Snackbar snackbar = Snackbar
                                .make(saleListRL, error_response, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }

    }

    private class YesterdaySaleDataList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Sale_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);
            String user_id = prefLogin.getString("user_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.sale_list_yesterday);
            String auth = getString(R.string.auth_key);
            String json = jp.SaledataListJsonFromURL(url, auth, cmp_id, login_id, user_id,Yesfrom, "10");
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("salelist_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ydate = dateFormat.format(mydate);

                    //    Toast.makeText(getApplicationContext(), ydate ,Toast.LENGTH_LONG).show();

                    if (response.equals("true")) {

                        String ta = jo.getString("yesterday_amount");
                        if (ta != null && !ta.isEmpty() && !ta.equals("null")) {
                            Float f = Float.valueOf(ta);
                            String ss = new DecimalFormat("##.##").format(f);
                            todayTotalAmountTV.setText(ss);
                        }else {
                            todayTotalAmountTV.setText("0000");
                        }

                        String tbb = jo.getString("yesterday_bill");
                        if (tbb != null && !tbb.isEmpty() && !tbb.equals("null")) {
                            todayTotalBillTV.setText(tbb);
                        }


                        try {

                        //    listitems_Salelist.clear();
                          //  adapterSelect_SaleList.notifyDataSetChanged();
                     //       adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                      //      recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);
                         //   todayTotalAmountTV.setText("0000");
                         //   todayTotalBillTV.setText("00");

                            JSONArray jsonArray = jo.getJSONArray("salelist_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);

                            }

                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                // todayTotalBillTV.setText(String.valueOf(ii+1));

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                              //  String billdate = jo2.getString("bill_date");


                            //   if (billdate.equals(ydate)) {

                               /*     tb++;
                                    todayTotalBillTV.setText(String.valueOf(tb));

                                    String totalStr = jo2.getString("grand_total");
                                    float results = 0;

                                    if (totalStr != null && !totalStr.isEmpty() && !totalStr.equals("null")) {
                                        // result = Integer.parseInt(result);
                                        results = Math.round(Float.parseFloat(totalStr));
                                        results_total = results_total + results;

                                    }
*/
                                    SaleList_Listitem listitem = new SaleList_Listitem(

                                            "1",
                                            jo2.getString("id"),
                                            jo2.getString("admin_id"),
                                            jo2.getString("cmp_id"),
                                            jo2.getString("customer_id"),
                                            jo2.getString("created_by"),
                                            "",
                                            jo2.getString("bill_no"),
                                            "",
                                            "",
                                            jo2.getString("bill_date"),
                                            "",
                                            "",
                                            "",
                                            jo2.getString("grand_total"),
                                            jo2.getString("total_paid"),
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            jo2.getString("customer_name"),
                                            jo2.getString("customer_mobile"),
                                            jo2.getString("mode")
                                    );

                                    listitems_Salelist.add(listitem);
                                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Sale_List.this);
                                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);


                               // }
                            }

                      //      double d = Double.parseDouble(String.valueOf(results_total));
                     //       long lon = Math.round(d);
                     //       todayTotalAmountTV.setText(String.valueOf(lon));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(saleListRL, error_response, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
            progressDialog.dismiss();
        }

    }

    private void setUpToolbar() {
        drawerLayoutSaleList = (DrawerLayout) findViewById(R.id.drawerlayoutSaleList);
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarSaleList);
        setSupportActionBar(toolbarSaleList1);

        actionBarDrawerToggleSaleList = new ActionBarDrawerToggle(this, drawerLayoutSaleList, toolbarSaleList1, R.string.app_name, R.string.app_name);
        drawerLayoutSaleList.addDrawerListener(actionBarDrawerToggleSaleList);
        actionBarDrawerToggleSaleList.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorDrawer));
        actionBarDrawerToggleSaleList.syncState();
    }

    private class SetupDrawerData extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Sale_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.drawer_data);
            String auth = getString(R.string.auth_key);
            String json = jp.ItemsJsonFromURL(url,auth, cmp_id, login_id,"","");
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.e("DD_SL", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("drawer_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode

                        TextView cmp_nametv = (TextView) findViewById(R.id.cmp_nameTV);
                        TextView loginAsTV = (TextView) findViewById(R.id.loginAsTV);
                        TextView dmenu_totalSale = (TextView) findViewById(R.id.dmenu_totalSale);
                        TextView dmenu_totalSaleAmount = (TextView) findViewById(R.id.dmenu_totalSaleAmount);
                        TextView dmenu_totalStock = (TextView) findViewById(R.id.dmenu_totalStock);
                        ImageView cmp_logoiv = (ImageView) findViewById(R.id.cmp_logooo);

                        // String ts = pref.getString("totalSale", null);

                        cmp_nametv.setText(pref.getString("cmp_name",null));
                        loginAsTV.setText(pref.getString("user_role",null));

                        dmenu_totalSale.setText(jo.getString("totalSale"));

                        String number = jo.getString("totalAmount");
                        float results = 0;
                        if (number != null && !number.isEmpty() && !number.equals("null")) {
                            // result = Integer.parseInt(result);
                            results = Math.round(Float.parseFloat(number));
                        }

                        dmenu_totalSaleAmount.setText("₹ "+String.valueOf(results));
                        dmenu_totalStock.setText(jo.getString("totalStock"));

                        String logoLink = getString(R.string.companyLogo);
                        Picasso.with(Sale_List.this)

                                .load(logoLink+pref.getString("cmp_logo", null))
                                .config(Bitmap.Config.RGB_565)
                                .error(R.drawable.ic_launcher_background)
                                .into(cmp_logoiv);

                    } else {
                       Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
            }
        }

    }

}
