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
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;

public class Account_List extends AppCompatActivity {

    final Context mContext = this;
    EditText AccountNameET, openingAmountET;
    List<String> accountTypelists = new ArrayList<String>();
    String accountTypeStr;
    String AccountNameStr;
    String openingAmountStr;


    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL;
    ImageView dmenu_back, dmenu_supporttLL, dmenu_logouttLL;




    LinearLayout acc_tab_salelistLL, acc_tab_purchaselistLL, acc_tab_productlistLL, acc_tab_reportlistLL;
    Button acc_add_btn;

    RelativeLayout accRL;

    private RecyclerView recyclerViewSelect_acc;
    private RecyclerView.Adapter adapterSelect_acc;
    private List<Listitem_Account> listitems_acc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__list);
        setUpToolbar();

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


        accRL = (RelativeLayout)findViewById(R.id.accRL);

        acc_tab_salelistLL = (LinearLayout)findViewById(R.id.acc_tab_salelistLL) ;
        acc_tab_purchaselistLL = (LinearLayout)findViewById(R.id.acc_tab_purchaselistLL) ;
        acc_tab_productlistLL = (LinearLayout)findViewById(R.id.acc_tab_productlistLL) ;
        acc_tab_reportlistLL = (LinearLayout)findViewById(R.id.acc_tab_reportlistLL) ;

        acc_add_btn = (Button)findViewById(R.id.acc_add_btn);
        LinearLayout qr_code_scanner,expenses_LL;
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Account_List.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Account_List.this,Expenses_Activity.class);
                startActivity(i);
            }
        });


        recyclerViewSelect_acc = (RecyclerView)findViewById(R.id.recyclerView_accountlist);
        recyclerViewSelect_acc.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.setReverseLayout(true);
        recyclerViewSelect_acc.setLayoutManager(linearLayoutManager2);

        listitems_acc = new ArrayList<>();

        new AccList().execute();

        acc_tab_salelistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sale_List.class);
                startActivity(i);
            }
        });
        acc_tab_purchaselistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Purchase_List.class);
                startActivity(i);
            }
        });
        acc_tab_productlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Product_List.class);
                startActivity(i);
            }
        });
        acc_tab_reportlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Report.class);
                startActivity(i);
            }
        });

        acc_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getApplicationContext(), "Under Construction", Toast.LENGTH_LONG ).show();



                final LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.customdialog_account, null);

                final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(Account_List.this,
                        android.R.layout.simple_dropdown_item_1line, accountTypelists);

                final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner) dialogView.findViewById(R.id.AccountType);
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
                        accountTypeStr = materialDesignSpinner_age.getText().toString();


                    }
                });


                AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                        Account_List.this);

                sayWindows.setTitle("New Account");
                // set custom dialog icon
                sayWindows.setIcon(R.mipmap.account);
                sayWindows.setView(dialogView);
                sayWindows.setCancelable(false);

                sayWindows.setPositiveButton("ok", null);
                sayWindows.setNegativeButton("cancel", null);
                //sayWindows.setAdapter(listWords,null);
                AccountNameET = (EditText) dialogView.findViewById(R.id.AccountNameET);
                openingAmountET = (EditText) dialogView.findViewById(R.id.openingAmountET);


                final AlertDialog mAlertDialog = sayWindows.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                AccountNameStr = AccountNameET.getText().toString();
                                openingAmountStr = openingAmountET.getText().toString();


                                if (AccountNameStr != null && !AccountNameStr.isEmpty() && !AccountNameStr.equals("null")) {

                                    if (accountTypeStr != null && !accountTypeStr.isEmpty() && !accountTypeStr.equals("null")) {

                                       new NewAccount().execute();

                                        mAlertDialog.dismiss();


                                    } else {

                                        Toast.makeText(getApplicationContext(), "Account Type Required", Toast.LENGTH_LONG).show();

                                    }
                                } else {

                                    Toast.makeText(getApplicationContext(), "Account Name Required", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();

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
                Intent i = new Intent(Account_List.this, BillShow.class);
                i.putExtra("url","http://express.accountantlalaji.com");
                startActivity(i);
            }
        });


        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Account_List.this, Profile.class);
                startActivity(i);

            }
        });

        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(Account_List.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Account_List.this, Support.class);
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
                //    Toast.makeText(Account_List.this,
                //            "Oups! Can't open Facebook messenger app right now.",
                //            Toast.LENGTH_SHORT).show();
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
        new AccountType().execute();

    }


    private class AccList extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_List.this);
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
            String url = getString(R.string.accList);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("account_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                       // listitems_acc.clear();
                        try {

                            JSONArray jsonArray = jo.getJSONArray("account_array");

                            final int numberc = jsonArray.length();
                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                Listitem_Account listitem = new Listitem_Account(

                                        "",
                                        jo2.getString("id"),
                                        jo2.getString("login_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("opening_account"),
                                        jo2.getString("account_type"),
                                        jo2.getString("account_name"),
                                        jo2.getString("status"),
                                        jo2.getString("created_at"),
                                        jo2.getString("bank_name")
                                );

                                listitems_acc.add(listitem);
                                adapterSelect_acc = new MyAdapter_Account(listitems_acc, Account_List.this);
                                recyclerViewSelect_acc.setAdapter(adapterSelect_acc);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(accRL, error_response , Snackbar.LENGTH_LONG);
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

    private void setUpToolbar() {
        drawerLayoutSaleList = (DrawerLayout) findViewById(R.id.drawerlayoutSaleList);
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarAccountList);
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
            progressDialog = new ProgressDialog(Account_List.this);
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
                        Picasso.with(Account_List.this)

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

    private class AccountType extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_List.this);
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
            String url = getString(R.string.acctype);
            String auth = getString(R.string.auth_key);
            String json = jp.ItemsJsonFromURL(url,auth, cmp_id, login_id,"","");
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("account", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("account");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                        listitems_acc.clear();
                        try {

                            JSONArray jsonArray = jo.getJSONArray("account_array");

                            final int numberc = jsonArray.length();
                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                accountTypelists.add(jo2.getString("bank_name"));



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(accRL, error_response , Snackbar.LENGTH_LONG);
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

    private class NewAccount extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_List.this);
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
            String url = getString(R.string.newAccount);
            String auth = getString(R.string.auth_key);
            String json = jp.NewAccountJsonFromURL(url,auth, cmp_id, login_id,
                    openingAmountStr,accountTypeStr,AccountNameStr);
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("account", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("newaccount");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        new AccList().execute();


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(accRL, error_response , Snackbar.LENGTH_LONG);
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

}


/*
{
        "account": {
        "error": true,
        "error_msg": "success",
        "account_array": [
        {
        "id": "1",
        "bank_name": "Normal",
        "created_at": "2018-04-24 05:27:21",
        "updated_at": "0000-00-00 00:00:00",
        "deleted_at": "0000-00-00 00:00:00"
        },
*/


/*{
        "newaccount": {
        "error": true,
        "error_msg": "Success"
        }
        }*/
