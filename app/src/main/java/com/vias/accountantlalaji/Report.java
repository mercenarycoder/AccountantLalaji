package com.vias.accountantlalaji;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

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

public class Report extends AppCompatActivity {

    RadioButton saleRB, purchaseRB;

    LinearLayout resultLL;

    Button reportSubmit;

    LinearLayout startDateLL, lastDateLL;
    TextView dateFromTV, dateToTV;
    EditText barcodeET;

    Calendar mCurrentDate;
    int day, month, year;
    String dateFrom;
    String dateTo;


    List<String> categoryList = new ArrayList<String>();
    String categoryStr = "";

    List<String> itemList = new ArrayList<String>();
    String itemStr = "";

    List<String> modeList = new ArrayList<String>();
    String modeStr = "";

    TextView saleViewMore;
    String Allfrom = "0";
    TextView total_amountTV, total_billTV;

    private RecyclerView recyclerViewSelect_SaleList;
    private RecyclerView.Adapter adapterSelect_SaleList;
    private List<SaleList_Listitem> listitems_Salelist;

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL;
    ImageView dmenu_back, dmenu_supporttLL, dmenu_logouttLL;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setUpToolbar();
        new ReportData().execute();

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
        navigationView_SaleList = (NavigationView) findViewById(R.id.navigation_view_SaleList);

        saleRB = (RadioButton)findViewById(R.id.saleRB);
        purchaseRB = (RadioButton)findViewById(R.id.purchaseRB);


        startDateLL = (LinearLayout)findViewById(R.id.startDateLL);
        lastDateLL = (LinearLayout)findViewById(R.id.lastDateLL);
        LinearLayout qr_code_scanner,expenses_LL;
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Report.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Report.this,Expenses_Activity.class);
                startActivity(i);
            }
        });
        resultLL = (LinearLayout)findViewById(R.id.resultLL);

        reportSubmit = (Button)findViewById(R.id.reportSubmit);

        barcodeET =(EditText)findViewById(R.id.barcodeET);

        dateFromTV = (TextView)findViewById(R.id.dateFromTV);
        dateToTV = (TextView)findViewById(R.id.dateToTV);

        saleViewMore = (TextView)findViewById(R.id.saleViewMore);
        total_amountTV = (TextView)findViewById(R.id.total_amountTV);
        total_billTV = (TextView)findViewById(R.id.total_billTV);

        recyclerViewSelect_SaleList = (RecyclerView) findViewById(R.id.recyclerView_SaleListReport);
        recyclerViewSelect_SaleList.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerViewSelect_SaleList.setLayoutManager(linearLayoutManager);

        listitems_Salelist = new ArrayList<>();


        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dateToTV.setText(date);
        dateTo = date;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String ss =  dateFormat.format(cal.getTime());
        dateFromTV.setText(ss);
        dateFrom = ss;

        startDateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentDate = Calendar.getInstance();
                day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                month = mCurrentDate.get(Calendar.MONTH);
                year = mCurrentDate.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Report.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                        m = m + 1;

                        String dd = String.valueOf(d);
                        String mm = String.valueOf(m);

                        // String dd = String.valueOf(d);

                        if (m < 10) {

                            mm = "0" + mm;

                        }
                        if (d < 10) {

                            dd = "0" + dd;

                        }

                        dateFrom = y + "-" + mm + "-" + dd;
                        dateFromTV.setText(dateFrom);
                     //   date_tb_tv.setText(y + "-" + mm + "-" + dd);

                    }
                }, year, month, day);

                datePickerDialog.show();

            }
        });


        lastDateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCurrentDate = Calendar.getInstance();
                day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                month = mCurrentDate.get(Calendar.MONTH);
                year = mCurrentDate.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Report.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                        m = m + 1;

                        String dd = String.valueOf(d);
                        String mm = String.valueOf(m);

                        // String dd = String.valueOf(d);

                        if (m < 10) {

                            mm = "0" + mm;

                        }
                        if (d < 10) {

                            dd = "0" + dd;

                        }

                        dateTo = y + "-" + mm + "-" + dd;
                        dateToTV.setText(dateTo);

                    }
                }, year, month, day);

                datePickerDialog.show();

            }
        });


        saleRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitems_Salelist.clear();
                saleRB.setChecked(true);
                purchaseRB.setChecked(false);
            }
        });

        purchaseRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitems_Salelist.clear();
                saleRB.setChecked(false);
                purchaseRB.setChecked(true);
            }
        });

        ///////////////////////////////////

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
                Intent i = new Intent(getApplicationContext(), Sale_List.class);
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


        LinearLayout linkLL = (LinearLayout)findViewById(R.id.linkLL);
        linkLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(Report.this, BillShow.class);
                i.putExtra("url","http://express.accountantlalaji.com");
                startActivity(i);
            }
        });

        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Report.this, Profile.class);
                startActivity(i);

            }
        });


        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(Report.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Report.this, Support.class);
                startActivity(i);

            }
        });


        ///////////////////////////////////////


        final ArrayAdapter<String> arrayAdapter_cate = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_dropdown_item_1line, categoryList);
        final MaterialBetterSpinner materialDesignSpinner_cate = (MaterialBetterSpinner)findViewById(R.id.CategoryName);
        materialDesignSpinner_cate.setAdapter(arrayAdapter_cate);
        materialDesignSpinner_cate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                categoryStr = materialDesignSpinner_cate.getText().toString();

            }
        });



        final ArrayAdapter<String> arrayAdapter_agec2 = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_dropdown_item_1line, itemList);
        final MaterialBetterSpinner materialDesignSpinner_agec2 = (MaterialBetterSpinner)findViewById(R.id.ProModelName);
        materialDesignSpinner_agec2.setAdapter(arrayAdapter_agec2);
        materialDesignSpinner_agec2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                itemStr = materialDesignSpinner_agec2.getText().toString();

            }
        });

        saleViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewProduct);
                //  scrollView.pageScroll(View.FOCUS_UP);


                    int from = Integer.parseInt(Allfrom);
                    int fromNew = from+10;
                    Allfrom = String.valueOf(fromNew);



                    new SaleDataList().execute();





            }
        });


      // modeList.add("mode");
        final ArrayAdapter<String> arrayAdapter_agec3 = new ArrayAdapter<String>(Report.this,
                android.R.layout.simple_dropdown_item_1line, modeList);
        final MaterialBetterSpinner materialDesignSpinner_agec3 = (MaterialBetterSpinner)findViewById(R.id.ModeName);
        materialDesignSpinner_agec3.setAdapter(arrayAdapter_agec3);
        materialDesignSpinner_agec3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                modeStr = materialDesignSpinner_agec3.getText().toString();

            }
        });

        reportSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listitems_Salelist.clear();
                new  SaleDataList().execute();

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
                    Toast.makeText(Report.this,
                            "Oups! Can't open Facebook messenger app right now.",
                            Toast.LENGTH_SHORT).show();
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

        new SetupDrawerData().execute();

    }


    private void setUpToolbar() {
        drawerLayoutSaleList = (DrawerLayout) findViewById(R.id.drawerlayoutSaleList);
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarReport);
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
            progressDialog = new ProgressDialog(Report.this);
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

                Log.d("admin_login", result);
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

                        dmenu_totalSaleAmount.setText(String.valueOf(results));
                        dmenu_totalStock.setText(jo.getString("totalStock"));


                        String logoLink = getString(R.string.companyLogo);
                        Picasso.with(Report.this)

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

    private class SaleDataList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Report.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String typee = null;
            if (saleRB.isChecked()){
                 typee = "sale";
            }
            if (purchaseRB.isChecked()){
                 typee = "purchase";
            }


            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);
            String user_id = prefLogin.getString("user_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.sale_report);
            String auth = getString(R.string.auth_key);
            String json = jp.SaleReportJsonFromURL(url, auth, cmp_id, login_id, user_id ,
                    Allfrom, "10",
                    dateFrom, dateTo,
                    itemStr,categoryStr,
                    barcodeET.getText().toString(),modeStr,
                    typee


            );
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
                        resultLL.setVisibility(View.VISIBLE);
                        String ta = jo.getString("all_amount");
                        if (ta != null && !ta.isEmpty() && !ta.equals("null")) {
                            Float f = Float.valueOf(ta);
                            String ss = new DecimalFormat("##.##").format(f);
                            total_amountTV.setText(ss);
                        }

                        String tbb = jo.getString("all_bill");
                        if (tbb != null && !tbb.isEmpty() && !tbb.equals("null")) {
                            total_billTV.setText(tbb);
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


                                if (saleRB.isChecked()){
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
                                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Report.this);
                                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);

                                }
                                if (purchaseRB.isChecked()){

                                    SaleList_Listitem listitem = new SaleList_Listitem(

                                            "1",
                                            jo2.getString("id"),
                                            jo2.getString("admin_id"),
                                            jo2.getString("cmp_id"),
                                            jo2.getString("vendor_id"),
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
                                    adapterSelect_SaleList = new MyAdapter_SaleList(listitems_Salelist, Report.this);
                                    recyclerViewSelect_SaleList.setAdapter(adapterSelect_SaleList);

                                }






                            }

                            // double d = Double.parseDouble(String.valueOf(results_total));
                            // long lon = Math.round(d);
                            // todayTotalAmountTV.setText(String.valueOf(lon));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                     //   Snackbar snackbar = Snackbar
                      //          .make(saleListRL, error_response, Snackbar.LENGTH_LONG);
                    //    snackbar.show();

                        Toast.makeText(getApplicationContext(), ""+error_response , Toast.LENGTH_LONG).show();
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

    private class ReportData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.report_data);
            String auth = getString(R.string.auth_key);
            String json = jp.SaledataJsonFromURL(url, auth, cmp_id, login_id);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("report_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        try {

                            JSONArray jsonArray = jo.getJSONArray("brand_array");
                            final int numberp = jsonArray.length();

                            for (int ii = 0; ii < numberp; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                String s = jo2.getString("brand_name");

                                categoryList.add(s);
                            }


                            JSONArray jsonArrayITEM = jo.getJSONArray("model_array");
                            final int numberi = jsonArrayITEM.length();

                            for (int ii = 0; ii < numberi; ii++) {

                                JSONObject jo2 = jsonArrayITEM.getJSONObject(ii);

                                String s = jo2.getString("model_name");

                                itemList.add(s);


                            }

                            JSONArray jsonArrayMode = jo.getJSONArray("mode_array");
                            final int numberm = jsonArrayMode.length();

                            for (int ii = 0; ii < numberm; ii++) {

                                JSONObject jo2 = jsonArrayMode.getJSONObject(ii);

                                String s = jo2.getString("account_name");

                                modeList.add(s);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {


                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
            }
        }

    }

}
