package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Customer_List extends AppCompatActivity {

    TextView saleViewMore;
    String Customerfrom = "0";
    String Customerto = "10";

    String Vendorfrom = "0";
    String Vendorto = "10";

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL,
            dmenu_acctLL,
            dmenu_reporttLL;
    ImageView dmenu_back, dmenu_supporttLL, dmenu_logouttLL;


    LinearLayout tab_salelistLL, tab_purchaselistLL, tab_productlistLL, tab_reportlistLL;
    Button sale_btn;
    int plusBtn = 0;

    TextView nameTolbar;
    LinearLayout cusLL, venLL;
    TextView cview, vview;

    private RecyclerView recyclerViewSelect_cusList;
    private RecyclerView.Adapter adapterSelect_cusList;
    private List<CustomerList> listitems_cuslist;

    RelativeLayout cusRL;

    final Context mContext = this;
    EditText cName;
    EditText cBussName;
    EditText cMobile;
    EditText cEmail;
    EditText cAdd;
    EditText GSTNumber;

    EditText vName;
    EditText vBussName;
    EditText vMobile;
    EditText vEmail;
    EditText vAdd;
    EditText vGSTNumber;

    List<String> stateList = new ArrayList<String>();
    String stateStr;
    private ProgressDialog progressDialog;


    EditText searchET;
    private List<String> searchList= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__list);

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
        navigationView_SaleList = (NavigationView) findViewById(R.id.navigation_view_SaleList);

        tab_salelistLL = (LinearLayout)findViewById(R.id.tab_salelistLL);
        tab_purchaselistLL = (LinearLayout)findViewById(R.id.tab_purchaselistLL);
        tab_productlistLL = (LinearLayout)findViewById(R.id.tab_productlistLL);
        tab_reportlistLL = (LinearLayout)findViewById(R.id.tab_reportlistLL);

        sale_btn = (Button)findViewById(R.id.sale_btn);
        LinearLayout qr_code_scanner,expenses_LL;
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Customer_List.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Customer_List.this,Expenses_Activity.class);
                startActivity(i);
            }
        });

        nameTolbar = (TextView)findViewById(R.id.nameToolbar);
        cview = (TextView)findViewById(R.id.cview);
        vview = (TextView)findViewById(R.id.vview);

        cusLL = (LinearLayout)findViewById(R.id.cusLL);
        venLL = (LinearLayout)findViewById(R.id.venLL);


        cusRL = (RelativeLayout)findViewById(R.id.cusRL);

        recyclerViewSelect_cusList = (RecyclerView)findViewById(R.id.recyclerView_CustomerList);
        recyclerViewSelect_cusList.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerViewSelect_cusList.setLayoutManager(linearLayoutManager);
        listitems_cuslist = new ArrayList<>();

        new Cusomter().execute();
        new State().execute();





        cusLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBtn=0;
                listitems_cuslist.clear();
                new Cusomter().execute();
                cview.setVisibility(View.VISIBLE);
                vview.setVisibility(View.GONE);
                nameTolbar.setText("Customer List");
                cusLL.setBackgroundColor(Color.parseColor("#ffedf5"));
              //  venLL.setBackgroundColor(Color.parseColor("#ffedf5"));
            }
        });
        venLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBtn=1;
                listitems_cuslist.clear();
                new Vendor().execute();
                cview.setVisibility(View.GONE);
                vview.setVisibility(View.VISIBLE);
                nameTolbar.setText("Vendor List");
                cusLL.setBackgroundColor(Color.parseColor("#ffedf5"));
              //  venLL.setBackgroundColor(Color.parseColor("#df0b62"));
            }
        });

        tab_salelistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sale_List.class);
                startActivity(i);
            }
        });

        tab_purchaselistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Purchase_List.class);
                startActivity(i);
            }
        });

        tab_productlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Product_List.class);
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

        final LayoutInflater li = LayoutInflater.from(mContext);
        final View dialogView = li.inflate(R.layout.add_new_customer_dialogbox, null);

        final LayoutInflater li2 = LayoutInflater.from(mContext);
        final View dialogView2 = li2.inflate(R.layout.add_new_vendor_dialogbox, null);

        final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stateList);
        final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner) dialogView.findViewById(R.id.paymentS);
        materialDesignSpinner_age.setAdapter(arrayAdapter_age);
        materialDesignSpinner_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                stateStr = materialDesignSpinner_age.getText().toString();

            }
        });

        final ArrayAdapter<String> arrayAdapter_age2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, stateList);
        final MaterialBetterSpinner materialDesignSpinner_age2 = (MaterialBetterSpinner) dialogView2.findViewById(R.id.venState);
        materialDesignSpinner_age2.setAdapter(arrayAdapter_age2);
        materialDesignSpinner_age2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                stateStr = materialDesignSpinner_age2.getText().toString();

            }
        });



        sale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (plusBtn==1){

                    AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                            Customer_List.this);
                    sayWindows.setTitle("New Vendor");
                    // set custom dialog icon
                    sayWindows.setIcon(R.mipmap.newuser);
                    sayWindows.setView(dialogView2);

                    sayWindows.setPositiveButton("ok", null);
                    sayWindows.setNegativeButton("cancel", null);
                    //sayWindows.setAdapter(listWords,null);
                    vName = (EditText) dialogView2.findViewById(R.id.venNameET);
                    vBussName = (EditText) dialogView2.findViewById(R.id.venBussNameET);
                    vMobile = (EditText) dialogView2.findViewById(R.id.venMobileET);
                    vEmail = (EditText) dialogView2.findViewById(R.id.venEmailET);
                    vAdd = (EditText) dialogView2.findViewById(R.id.venAddressET);
                    vGSTNumber = (EditText) dialogView2.findViewById(R.id.venGSTET);


                    final AlertDialog mAlertDialog = sayWindows.create();
                    mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener()

                    {

                        @Override
                        public void onShow(DialogInterface dialog) {

                            Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            b.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                    String name = vName.getText().toString();
                                    String bussname = vName.getText().toString();
                                    String mob = vMobile.getText().toString();
                                    String email = vEmail.getText().toString();
                                    String pass = vAdd.getText().toString();
                                    String gst = vGSTNumber.getText().toString();

                                    if (name != null && !name.isEmpty() && !name.equals("null")) {

                                        if (bussname != null && !bussname.isEmpty() && !bussname.equals("null")) {

                                        if (mob != null && !mob.isEmpty() && !mob.equals("null")) {

                                            if (pass != null && !pass.isEmpty() && !pass.equals("null")) {

                                                if (stateStr != null && !stateStr.isEmpty() && !stateStr.equals("null")) {

                                                    //Toast.makeText(getApplicationContext(), "ssssssss", Toast.LENGTH_LONG).show();
                                                    new VSignupJson().execute();
                                                    mAlertDialog.dismiss();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Select your State", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Enter your Address", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter your Mobile No", Toast.LENGTH_LONG).show();
                                        }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter your Business Name", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                    mAlertDialog.show();

                }

                if (plusBtn==0){


                    AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                            Customer_List.this);
                    sayWindows.setTitle("New Customer");
                    // set custom dialog icon
                    sayWindows.setIcon(R.mipmap.newuser);
                    sayWindows.setView(dialogView);

                    sayWindows.setPositiveButton("ok", null);
                    sayWindows.setNegativeButton("cancel", null);
                    //sayWindows.setAdapter(listWords,null);
                    cName = (EditText) dialogView.findViewById(R.id.cusNameET);
                    cBussName = (EditText) dialogView.findViewById(R.id.cusBussNameET);
                    cMobile = (EditText) dialogView.findViewById(R.id.cusMobileET);
                    cEmail = (EditText) dialogView.findViewById(R.id.cusEmailET);
                    cAdd = (EditText) dialogView.findViewById(R.id.cusAddressET);
                    GSTNumber = (EditText) dialogView.findViewById(R.id.cusGSTET);


                    final AlertDialog mAlertDialog = sayWindows.create();
                    mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener()

                    {

                        @Override
                        public void onShow(DialogInterface dialog) {

                            Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            b.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                    String name = cName.getText().toString();
                                    String bussname = cName.getText().toString();
                                    String mob = cMobile.getText().toString();
                                    String email = cEmail.getText().toString();
                                    String pass = cAdd.getText().toString();
                                    String gst = GSTNumber.getText().toString();

                                    if (name != null && !name.isEmpty() && !name.equals("null")) {

                                        if (mob != null && !mob.isEmpty() && !mob.equals("null")) {

                                            if (pass != null && !pass.isEmpty() && !pass.equals("null")) {

                                                if (stateStr != null && !stateStr.isEmpty() && !stateStr.equals("null")) {

                                                    //Toast.makeText(getApplicationContext(), "ssssssss", Toast.LENGTH_LONG).show();
                                                    new SignupJson().execute();
                                                    mAlertDialog.dismiss();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Select your State", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Enter your Address", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Enter your Mobile No", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });
                    mAlertDialog.show();

                }


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
                Intent i = new Intent(Customer_List.this, BillShow.class);
                i.putExtra("url","http://express.accountantlalaji.com");
                startActivity(i);
            }
        });



        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Customer_List.this, Profile.class);
                startActivity(i);

            }
        });

        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Customer_List.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Customer_List.this, Support.class);
                startActivity(i);

            }
        });





///////////////////////////////////////

        new SetupDrawerData().execute();

        searchET = (EditText)findViewById(R.id.searchET);
        addTextListener();

        saleViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewCus);
              //  scrollView.pageScroll(View.FOCUS_UP);


                if (plusBtn==0){

                    int from = Integer.parseInt(Customerfrom);
                    int fromNew = from+10;
                    Customerfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Customerto);
                    int toNew = to+10;
                    Customerto = String.valueOf(toNew);

                    new Cusomter().execute();

                }

                if (plusBtn==1){

                    int from = Integer.parseInt(Vendorfrom);
                    int fromNew = from+10;
                    Vendorfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Vendorto);
                    int toNew = to+10;
                    Vendorto = String.valueOf(toNew);

                    new Vendor().execute();

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
           //         Toast.makeText(Customer_List.this,
            //                "Oups! Can't open Facebook messenger app right now.",
            //                Toast.LENGTH_SHORT).show();
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


    private class Cusomter extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_List.this);
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
            String url = getString(R.string.customer_list);
            String auth = getString(R.string.auth_key);
            String json = jp.CustomerListJsonFromURL(url,auth, cmp_id, login_id,"customer",Customerfrom,"10");
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("customer_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                     //   listitems_cuslist.clear();

                        try {

                            JSONArray jsonArray = jo.getJSONArray("customer_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);

                            }

                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                CustomerList listitem = new CustomerList(

                                        jo2.getString("id"),
                                        jo2.getString("customer_name"),
                                        jo2.getString("customer_mobile"),
                                        jo2.getString("customer_type"),
                                        jo2.getString("login_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("dob"),
                                        jo2.getString("email"),
                                        jo2.getString("billing_address"),
                                        jo2.getString("shipping_address"),
                                        jo2.getString("state_id"),
                                        jo2.getString("pincode"),
                                        jo2.getString("customer_gst"),
                                        jo2.getString("status"),
                                        jo2.getString("buss_name"),
                                        jo2.getString("opening_account"),
                                        jo2.getString("account_name"),
                                        jo2.getString("account_number"),
                                        jo2.getString("bank_ifsc_code"),
                                        jo2.getString("consumer_no"),
                                        jo2.getString("created_at"),
                                        jo2.getString("state_name")
                                );

                                listitems_cuslist.add(listitem);
                                adapterSelect_cusList = new MyAdapter_Customer(listitems_cuslist, Customer_List.this);
                                recyclerViewSelect_cusList.setAdapter(adapterSelect_cusList);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, error_response , Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class Vendor extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_List.this);
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
            String url = getString(R.string.customer_list);
            String auth = getString(R.string.auth_key);
            String json = jp.CustomerListJsonFromURL(url,auth, cmp_id, login_id,"vendor",Vendorfrom,"10");
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("customer_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                     //   listitems_cuslist.clear();
                        try {

                            JSONArray jsonArray = jo.getJSONArray("customer_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);

                            }

                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                CustomerList listitem = new CustomerList(

                                        jo2.getString("id"),
                                        jo2.getString("customer_name"),
                                        jo2.getString("customer_mobile"),
                                        jo2.getString("customer_type"),
                                        jo2.getString("login_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("dob"),
                                        jo2.getString("email"),
                                        jo2.getString("billing_address"),
                                        jo2.getString("shipping_address"),
                                        jo2.getString("state_id"),
                                        jo2.getString("pincode"),
                                        jo2.getString("customer_gst"),
                                        jo2.getString("status"),
                                        jo2.getString("buss_name"),
                                        jo2.getString("opening_account"),
                                        jo2.getString("account_name"),
                                        jo2.getString("account_number"),
                                        jo2.getString("bank_ifsc_code"),
                                        jo2.getString("consumer_no"),
                                        jo2.getString("created_at"),
                                        jo2.getString("state_name")
                                );

                                listitems_cuslist.add(listitem);
                                adapterSelect_cusList = new MyAdapter_Customer(listitems_cuslist, Customer_List.this);
                                recyclerViewSelect_cusList.setAdapter(adapterSelect_cusList);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, error_response , Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class State extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JsonParser jp = new JsonParser();
            String url = getString(R.string.state);
            String auth = getString(R.string.auth_key);
            String json = jp.StateListJsonFromURL(url,auth);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("state");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        try {

                            JSONArray jsonArray = jo.getJSONArray("state_data");

                            final int numberc = jsonArray.length();
                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                String st = jo2.getString("state_name");
                                stateList.add(st);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, error_response , Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
        }

    }

    private class SignupJson extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = null;
            JsonParser djp = new JsonParser();

            String auth_key = getString(R.string.auth_key);
            String url = getString(R.string.newcustomer);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode

            String comid = pref.getString("cmp_id", null);
            String login_id = pref.getString("login_id", null);

            json = djp.SignUpJsonFromURL(auth_key,
                    comid,
                    login_id,
                    cName.getText().toString(),
                    cMobile.getText().toString(),
                    cEmail.getText().toString(),
                    cAdd.getText().toString(),
                    url,
                    stateStr,
                    cBussName.getText().toString(),
                    GSTNumber.getText().toString(),
            "customer"
            );

            return json;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (null != result && !result.isEmpty()) {

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("signup");
                    String response = jo.getString("error");
                    String responsemsg = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, "Customer Created", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        plusBtn=0;
                        listitems_cuslist.clear();
                        new Cusomter().execute();
                        cview.setVisibility(View.VISIBLE);
                        vview.setVisibility(View.GONE);
                        nameTolbar.setText("Customer List");
                        cusLL.setBackgroundColor(Color.parseColor("#ffedf5"));



                    } else {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, responsemsg, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        // Toast.makeText(getApplicationContext(), responsemsg , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Customer_List.this, "Something went wrong ! ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class VSignupJson extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String json = null;
            JsonParser djp = new JsonParser();

            String auth_key = getString(R.string.auth_key);
            String url = getString(R.string.newcustomer);

            SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode

            String comid = pref.getString("cmp_id", null);
            String login_id = pref.getString("login_id", null);

            json = djp.SignUpJsonFromURL(auth_key,
                    comid,
                    login_id,
                    vName.getText().toString(),
                    vMobile.getText().toString(),
                    vEmail.getText().toString(),
                    vAdd.getText().toString(),
                    url,
                    stateStr,
                    vBussName.getText().toString(),
                    vGSTNumber.getText().toString(),
                    "vendor"

            );

            return json;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            if (null != result && !result.isEmpty()) {

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("signup");
                    String response = jo.getString("error");
                    String responsemsg = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, "Vendor Created", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        plusBtn=1;
                        listitems_cuslist.clear();
                        new Vendor().execute();
                        cview.setVisibility(View.GONE);
                        vview.setVisibility(View.VISIBLE);
                        nameTolbar.setText("Vendor List");
                        cusLL.setBackgroundColor(Color.parseColor("#ffedf5"));


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(cusRL, responsemsg, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        // Toast.makeText(getApplicationContext(), responsemsg , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Customer_List.this, "Something went wrong ! ", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void setUpToolbar() {
        drawerLayoutSaleList = (DrawerLayout) findViewById(R.id.drawerlayoutSaleList);
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarCustomer);
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
            progressDialog = new ProgressDialog(Customer_List.this);
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

                        dmenu_totalSaleAmount.setText("₹ "+String.valueOf(results));
                        dmenu_totalStock.setText(jo.getString("totalStock"));


                        String logoLink = getString(R.string.companyLogo);
                        Picasso.with(Customer_List.this)

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


    public void addTextListener() {

        searchET.addTextChangedListener(new TextWatcher() {


            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                final String s = query.toString().toLowerCase().trim();
                final List<CustomerList> filteredList = new ArrayList<>();

                for (int i = 0; i < listitems_cuslist.size(); i++) {
                    final String text = listitems_cuslist.get(i).getCustomerName().toLowerCase();
                    if (text.contains(s)) {
                        filteredList.add(listitems_cuslist.get(i));
                    }

                    final String text2 = listitems_cuslist.get(i).getCustomerMob().toLowerCase();
                    if (text2.contains(s)) {
                        filteredList.add(listitems_cuslist.get(i));
                    }
                }

                recyclerViewSelect_cusList.setLayoutManager(new LinearLayoutManager(Customer_List.this));
                adapterSelect_cusList = new MyAdapter_Customer(filteredList, Customer_List.this);
                recyclerViewSelect_cusList.setAdapter(adapterSelect_cusList);
                adapterSelect_cusList.notifyDataSetChanged();
            }
        });
    }

}
