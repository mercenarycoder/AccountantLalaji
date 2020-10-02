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

public class Expenses_Activity extends AppCompatActivity {

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL, linkLL,qr_code_scanner,expenses_LL;
    ImageView dmenu_back ,dmenu_supporttLL, dmenu_logouttLL;



    LinearLayout tab_purchaseLL, tab_productLL, tab_contactlistLL, tab_reportlistLL;

    private ProgressDialog progressDialog;
    private List<CustomerList> customerLists = new ArrayList<>();

    private RecyclerView recyclerViewSelect_SaleList;
    private RecyclerView.Adapter adapterSelect_SaleList;
    private List<SaleList_Listitem> listitems_Salelist;

    int click = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_);

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
        linkLL = (LinearLayout) findViewById(R.id.linkLL);
        navigationView_SaleList = (NavigationView) findViewById(R.id.navigation_view_SaleList);
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Expenses_Activity.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Expenses_Activity.this,Expenses_Activity.class);
                startActivity(i);
            }
        });
        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Expenses_Activity.this, Profile.class);
                startActivity(i);

            }
        });

        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Expenses_Activity.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Expenses_Activity.this, Support.class);
                startActivity(i);

            }
        });



        new SetupDrawerData().execute();



        tab_purchaseLL = (LinearLayout) findViewById(R.id.tab_purchaseLL);
        tab_productLL = (LinearLayout) findViewById(R.id.tab_productlistLL);
        tab_contactlistLL = (LinearLayout) findViewById(R.id.tab_contactlistLL);
        tab_reportlistLL = (LinearLayout) findViewById(R.id.tab_reportlistLL);






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
                    Toast.makeText(Expenses_Activity.this, "Please Install Facebook Messenger",    Toast.LENGTH_LONG).show();
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
                    //Toast.makeText(Expenses_Activity.this,                           "Oups! Can't open Facebook messenger app right now.",                             Toast.LENGTH_SHORT).show();
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
                String url = "https://api.whatsapp.com/send?phone=7987315256";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


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
            progressDialog = new ProgressDialog(Expenses_Activity.this);
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
                        Picasso.with(Expenses_Activity.this)

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
