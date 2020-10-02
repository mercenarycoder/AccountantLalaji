package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Purchase extends AppCompatActivity {

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL, dmenu_supporttLL, dmenu_logouttLL;
    ImageView dmenu_back;



    List<String> stateList = new ArrayList<String>();
    String stateStr;

    ImageView newcustomerSale;

    RelativeLayout saleRL;
    ProgressDialog progressDialog;
    Button continueBTN;

    TextView date_tb_tv;
    TextView cusMobileTV;

    private List<CustomerList> customerLists = new ArrayList<>();
    private List<ProductList> productLists = new ArrayList<>();

    private RecyclerView recyclerViewSelect_Product;
    private RecyclerView.Adapter adapterSelect_Product;
    private List<Listitem_Select_Product> listitems_Select_Product;

    ImageView barcodeScan;
    public static AutoCompleteTextView pText;

    int i = 0;
    String product_jsonStr;

    public static TextView grandTotal_tv;
    AutoCompleteTextView editText;

    String cusID;

    final Context mContext = this;
    EditText cName;
    EditText cBussName;
    EditText cMobile;
    EditText cEmail;
    EditText cAdd;
    EditText GSTNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);



        setUpToolbar();
        dmenu_back = (ImageView) findViewById(R.id.dmenuback);
        dmenu_sale_LL = (LinearLayout) findViewById(R.id.dmenu_saleLL);
        dmenu_purchaseLL = (LinearLayout) findViewById(R.id.dmenu_purchaseLL);
        dmenu_contactLL = (LinearLayout) findViewById(R.id.dmenu_contactLL);
        dmenu_producttLL = (LinearLayout) findViewById(R.id.dmenu_productLL);
        dmenu_hsntLL = (LinearLayout) findViewById(R.id.dmenu_hsntLL);
        dmenu_acctLL = (LinearLayout) findViewById(R.id.dmenu_acctLL);
        dmenu_supporttLL = (LinearLayout) findViewById(R.id.dmenu_supporttLL);
        dmenu_reporttLL = (LinearLayout) findViewById(R.id.dmenu_reporttLL);
        dmenu_logouttLL = (LinearLayout) findViewById(R.id.dmenu_logouttLL);
        navigationView_SaleList = (NavigationView) findViewById(R.id.navigation_view_SaleList);


        newcustomerSale = (ImageView) findViewById(R.id.newcustomerSale);
        saleRL = (RelativeLayout) findViewById(R.id.saleRL);

        new SaleData().execute();

        cusMobileTV = (TextView) findViewById(R.id.cusMobile);
        barcodeScan = (ImageView) findViewById(R.id.barcodeScan);

        continueBTN = (Button) findViewById(R.id.continueBTN);

        grandTotal_tv = (TextView) findViewById(R.id.grandTotalTV);

        SharedPreferences pref2 = getApplicationContext().getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref2.edit();
        editor.clear();
        editor.commit();
        LinearLayout qr_code_scanner,expenses_LL;
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Purchase.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Purchase.this,Expenses_Activity.class);
                startActivity(i);
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode


        date_tb_tv = (TextView) findViewById(R.id.date_tb_TV);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // HH:mm:ss
        String strDate = sdf.format(c.getTime());
        date_tb_tv.setText(strDate);

        recyclerViewSelect_Product = (RecyclerView) findViewById(R.id.recyclerView_Select_Product);
        recyclerViewSelect_Product.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewSelect_Product.setLayoutManager(linearLayoutManager);

        listitems_Select_Product = new ArrayList<>();


        // fillCountList();
        editText = findViewById(R.id.count);
        pText = findViewById(R.id.barCode);


        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                CustomerList selected = (CustomerList) arg0.getAdapter().getItem(arg2);
                // Toast.makeText(Sale.this, "Clicked " + arg2 + " name: " + selected.getCustomerId(), Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_SaleData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("cus_id", selected.getCustomerId());
                cusMobileTV.setText(selected.getCustomerMob());
                editor.commit();
                cusID = selected.getCustomerId();

                LinearLayout mobileLL = (LinearLayout)findViewById(R.id.mobileLL);
                mobileLL.setVisibility(View.VISIBLE);

            }
        });


        pText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                ProductList selected = (ProductList) arg0.getAdapter().getItem(arg2);
                // Toast.makeText(Sale.this, "Clicked " + arg2 + " name: " + selected.getCustomerId(), Toast.LENGTH_SHORT).show();
                /*SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_SaleData", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("cus_p", selected.getCustomerId());
                cusMobileTV.setText(selected.getCustomerMob());*/

                String st = selected.getModel_stock();
                int stock = 0;
                if (st != null && !st.isEmpty() && !st.equals("null")) {
                    stock = Integer.parseInt(st);
                }

                //  if (stock <= 0) {
                //Toast.makeText(getApplicationContext(), "Item out of Stock", Toast.LENGTH_LONG).show();
                //   } else {

                Listitem_Select_Product listitem_orderHistory = new Listitem_Select_Product(

                        String.valueOf(i),
                        selected.getProduct_id(),
                        selected.getLogin_id(),
                        selected.getBrand_id(),
                        selected.getCmp_id(),
                        selected.getHsn_id(),
                        selected.getUnit_id(),
                        selected.getCast_id(),
                        selected.getModel_name(),
                        selected.getModel_image(),
                        selected.getModel_specialization(),
                        selected.getModel_mop(),
                        selected.getModel_mrp(),
                        selected.getModel_pp(),
                        selected.getModel_pp_new(),
                        selected.getModel_mop_new(),
                        selected.getModel_stock(),
                        selected.getBar_code(),
                        selected.getBar_code_status(),
                        selected.getModel_status(),
                        "1",
                        "0",
                        selected.getBrand_name(),
                        selected.getHsn_rate(),
                        selected.getHsn_code(),
                        tax(selected.getModel_mop_new(), selected.getHsn_rate()),
                        amount(selected.getModel_mop_new(), selected.getHsn_rate())
                );

                listitems_Select_Product.add(listitem_orderHistory);
                //  Toast.makeText(getApplicationContext(),listitems_Select_Product.toString().toLowerCase().trim(),Toast.LENGTH_LONG).show();
                adapterSelect_Product = new MyAdapter_Select_Product(listitems_Select_Product, getApplicationContext());
                recyclerViewSelect_Product.setAdapter(adapterSelect_Product);

                //    }


                pText.setText("");


            }

        });


        barcodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Purchase.this, ScanActivity.class);
                startActivity(intent);


            }
        });


        continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cusID != null && !cusID.isEmpty() && !cusID.equals("null")) {
                    if (listitems_Select_Product != null && !listitems_Select_Product.isEmpty() && !listitems_Select_Product.equals("null")) {

                        Intent i2 = new Intent(getApplicationContext(), Bill_Purhase.class);
                        startActivity(i2);
                        finish();

                    } else {

                        Toast.makeText(getApplicationContext(), "Select Product", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Select Customer", Toast.LENGTH_LONG).show();
                }


            }
        });

        final LayoutInflater li = LayoutInflater.from(mContext);
        final View dialogView = li.inflate(R.layout.add_new_customer_dialogbox, null);

        newcustomerSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                        Purchase.this);
                sayWindows.setTitle("New Vendor");
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
        });


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
                String url = "http://www.accountantlalaji.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(Purchase.this, Setting.class);
                startActivity(i);

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
                    Toast.makeText(Purchase.this,
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

///////////////////////////////////////



        new SetupDrawerData().execute();

    }


    private String tax(String bp, String tax) {

        String number = bp;
        float result = 0;
        if (number != null && !number.isEmpty() && !number.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(number));
        }

        String number2 = tax;
        float result2 = 0;
        if (number2 != null && !number2.isEmpty() && !number2.equals("null")) {
            result2 = Math.round(Float.parseFloat(number2));
        }

        float re = result * result2 / 100;
        String res = String.valueOf(re);

        return res;

    }

    private String amount(String bp, String tax) {

        String number = bp;
        float result = 0;
        if (number != null && !number.isEmpty() && !number.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(number));
        }

        String number2 = tax;
        float result2 = 0;
        if (number2 != null && !number2.isEmpty() && !number2.equals("null")) {
            result2 = Math.round(Float.parseFloat(number2));
        }

        float taxable = result * result2 / 100;
        float re = taxable + result;
        String res = String.valueOf(re);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        if (pref.contains("total")) {
            String res2 = pref.getString("total", null);
            String res3 = String.valueOf(Float.parseFloat(res) + Float.parseFloat(res2));

            double d = Double.parseDouble(res3);
            long lon = Math.round(d);
            grandTotal_tv.setText("₹ " + lon);
            editor.putString("total", res3);
            editor.commit();

        } else {

            double d = Double.parseDouble(res);
            long lon = Math.round(d);
            grandTotal_tv.setText("₹ " + lon);
            editor.putString("total", res);
            editor.commit();

        }


        return res;

    }

    private class SaleData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Purchase.this);
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
            String url = getString(R.string.purchasedata);
            String auth = getString(R.string.auth_key);
            String json = jp.SaledataJsonFromURL(url, auth, cmp_id, login_id);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("sale_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("Sale_data", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("saledata", result);
                        editor.commit();


                        try {

                            JSONArray jsonArray = jo.getJSONArray("cus_array");
                            JSONArray jsonArrayP = jo.getJSONArray("product_array");
                            JSONArray jsonArrayS = jo.getJSONArray("state_array");

                            final int numberc = jsonArray.length();

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);
                                String i = jo2.getString("id");
                                String s = jo2.getString("customer_name");
                                String s2 = jo2.getString("customer_mobile");

                                customerLists.add(new CustomerList(i, s, s2, "",
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
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                ));

                                // cus_array.add(jo2.getString("user_mobile"));
                            }

                            AutoCompeleteAdapter_Customer adapter = new AutoCompeleteAdapter_Customer(Purchase.this, customerLists);
                            editText.setAdapter(adapter);


                            final int numberp = jsonArrayP.length();

                            for (int ii = 0; ii < numberp; ii++) {

                                JSONObject jo2 = jsonArrayP.getJSONObject(ii);

                                productLists.add(new ProductList(
                                        jo2.getString("id"),
                                        jo2.getString("login_id"),
                                        jo2.getString("brand_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("hsn_id"),
                                        jo2.getString("unit_id"),
                                        jo2.getString("cast_id"),
                                        jo2.getString("model_name"),
                                        jo2.getString("model_image"),
                                        jo2.getString("model_specialization"),
                                        jo2.getString("model_mop"),
                                        jo2.getString("model_mrp"),
                                        jo2.getString("model_pp"),
                                        jo2.getString("model_pp_new"),
                                        jo2.getString("model_mop_new"),
                                        jo2.getString("model_stock"),
                                        jo2.getString("bar_code"),
                                        jo2.getString("bar_code_status"),
                                        jo2.getString("model_status"),
                                        jo2.getString("brand_name"),
                                        jo2.getString("hsn_rate"),
                                        jo2.getString("hsn_code")

                                ));

                            }

                            AutoCompeleteAdapter_Product adapterP = new AutoCompeleteAdapter_Product(Purchase.this, productLists);
                            pText.setAdapter(adapterP);

                            for (int ii = 0; ii < jsonArrayS.length(); ii++) {

                                JSONObject jo2 = jsonArrayS.getJSONObject(ii);
                                String i = jo2.getString("state_name");
                                stateList.add(i);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        Snackbar snackbar = Snackbar
                                .make(saleRL, error_response, Snackbar.LENGTH_LONG);
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

    private class SignupJson extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Purchase.this);
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
                                .make(saleRL, "Vendor Created", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        Toast.makeText(getApplicationContext(), "New Vendor Created", Toast.LENGTH_LONG).show();


                        Intent i = new Intent(getApplicationContext(), Purchase.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    } else {

                        Snackbar snackbar = Snackbar
                                .make(saleRL, responsemsg, Snackbar.LENGTH_LONG);
                        snackbar.show();
                        // Toast.makeText(getApplicationContext(), responsemsg , Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Purchase.this, "Something went wrong ! ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setUpToolbar() {
        drawerLayoutSaleList = (DrawerLayout) findViewById(R.id.drawerlayoutSaleList);
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarMainActivity);
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
            progressDialog = new ProgressDialog(Purchase.this);
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
                        Picasso.with(Purchase.this)

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
