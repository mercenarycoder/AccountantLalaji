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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product_List extends AppCompatActivity {

    TextView saleViewMore;
    String Productfrom = "0";
    String Productto = "10";

    String Brandfrom = "0";
    String Brandto = "10";


    String newProData;

    final Context mContext = this;
    EditText cateNameET;

    DrawerLayout drawerLayoutSaleList;
    Toolbar toolbarSaleList1;
    ActionBarDrawerToggle actionBarDrawerToggleSaleList;
    NavigationView navigationView_SaleList;
    LinearLayout dmenu_sale_LL, dmenu_purchaseLL, dmenu_contactLL, dmenu_producttLL, dmenu_hsntLL, dmenu_acctLL,
            dmenu_reporttLL;
    ImageView dmenu_back, dmenu_supporttLL, dmenu_logouttLL;


    LinearLayout p_tab_salelistLL, p_tab_purchaselistLL, p_tab_contactlistLL, p_tab_reportlistLL;
    Button p_sale_btn;

    int plusBtn = 0;

    LinearLayout itemLL, cateLL;
    TextView iview, cview, toolbRnAME;

    RelativeLayout itemCateRL;

    private RecyclerView recyclerViewSelect_itemList;
    private RecyclerView.Adapter adapterSelect_itemList;
    private List<Listitem_items> listitems_itemList;

    private RecyclerView recyclerViewSelect_brand;
    private RecyclerView.Adapter adapterSelect_brand;
    private List<Listitem_brand> listitems_brand;


    EditText searchET;
    private List<String> searchList= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);

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


        itemLL = (LinearLayout)findViewById(R.id.itemLL);
        cateLL = (LinearLayout)findViewById(R.id.cateLL);

        iview = (TextView)findViewById(R.id.iview);
        cview = (TextView)findViewById(R.id.cview);
        toolbRnAME = (TextView)findViewById(R.id.toolbRnAME);

        itemCateRL = (RelativeLayout)findViewById(R.id.itemCateRL);

        recyclerViewSelect_itemList = (RecyclerView)findViewById(R.id.recyclerView_items);
        recyclerViewSelect_itemList.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(false);
        recyclerViewSelect_itemList.setLayoutManager(linearLayoutManager);

        listitems_itemList = new ArrayList<>();

        LinearLayout qr_code_scanner,expenses_LL;
        qr_code_scanner=(LinearLayout)findViewById(R.id.qr_code_scanner);
        qr_code_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Product_List.this,Qr_scanner.class);
                startActivity(i);
            }
        });
        expenses_LL=(LinearLayout)findViewById(R.id.expenses_LL);
        expenses_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Product_List.this,Expenses_Activity.class);
                startActivity(i);
            }
        });
        recyclerViewSelect_brand = (RecyclerView)findViewById(R.id.recyclerView_brand);
        recyclerViewSelect_brand.setHasFixedSize(true);
        //recyclerViewSelect_Product.setLayoutManager(new LinearLayoutManager(this))

        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.setReverseLayout(false);
        recyclerViewSelect_brand.setLayoutManager(linearLayoutManager2);

        listitems_brand = new ArrayList<>();



        new Items().execute();

        itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBtn=0;
                listitems_itemList.clear();
                new Items().execute();
                iview.setVisibility(View.VISIBLE);
                cview.setVisibility(View.GONE);
                toolbRnAME.setText("Item List");
                itemLL.setBackgroundColor(Color.parseColor("#ffedf5"));
               /// cateLL.setBackgroundColor(Color.parseColor("#ffedf5"));

                LinearLayout showItem = (LinearLayout)findViewById(R.id.showItem);
                showItem.setVisibility(View.VISIBLE);
                LinearLayout showCate = (LinearLayout)findViewById(R.id.showCate);
                showCate.setVisibility(View.GONE);

            }
        });

        cateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusBtn=1;
                listitems_brand.clear();
                new Brand().execute();
                iview.setVisibility(View.GONE);
                cview.setVisibility(View.VISIBLE);
                toolbRnAME.setText("Category List");
                itemLL.setBackgroundColor(Color.parseColor("#ffedf5"));
              //  cateLL.setBackgroundColor(Color.parseColor("#df0b62"));
                LinearLayout showCate = (LinearLayout)findViewById(R.id.showCate);
                showCate.setVisibility(View.VISIBLE);
                LinearLayout showItem = (LinearLayout)findViewById(R.id.showItem);
                showItem.setVisibility(View.GONE);

            }
        });

        p_tab_salelistLL = (LinearLayout)findViewById(R.id.p_tab_salelistLL);
        p_tab_salelistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Sale_List.class);
                startActivity(i);
            }
        });

        p_tab_purchaselistLL = (LinearLayout)findViewById(R.id.p_tab_purchaselistLL);
        p_tab_purchaselistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Purchase_List.class);
                startActivity(i);
            }
        });

        p_tab_contactlistLL = (LinearLayout)findViewById(R.id.p_tab_contactlistLL);
        p_tab_contactlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Customer_List.class);
                startActivity(i);
            }
        });

        p_tab_reportlistLL = (LinearLayout)findViewById(R.id.p_tab_reportlistLL);
        p_tab_reportlistLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Report.class);
                startActivity(i);
            }
        });

        p_sale_btn = (Button) findViewById(R.id.p_sale_btn);
        p_sale_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(getApplicationContext(),"Under Construction",Toast.LENGTH_LONG).show();

                if (plusBtn==1){

                    final LayoutInflater li = LayoutInflater.from(mContext);
                    final View dialogView = li.inflate(R.layout.custom_dialog_category, null);

                    AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                            Product_List.this);

                    sayWindows.setTitle("New Category");
                    // set custom dialog icon
                    sayWindows.setIcon(R.mipmap.cube);
                    sayWindows.setView(dialogView);
                    sayWindows.setCancelable(false);

                    sayWindows.setPositiveButton("ok", null);
                    sayWindows.setNegativeButton("cancel", null);
                    //sayWindows.setAdapter(listWords,null);
                    cateNameET = (EditText) dialogView.findViewById(R.id.cateNameET);



                    final AlertDialog mAlertDialog = sayWindows.create();
                    mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {

                            Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            b.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {

                                  String cateName = cateNameET.getText().toString();


                                    if (cateName != null && !cateName.isEmpty() && !cateName.equals("null")) {

                                            mAlertDialog.dismiss();

                                            new NewCategory().execute();

                                    } else {

                                        Toast.makeText(getApplicationContext(), "Category Name Required", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    });
                    mAlertDialog.show();

                }

                if (plusBtn==0){

                    Intent i = new Intent(Product_List.this, Product.class);
                    i.putExtra("newProData",newProData);
                    startActivity(i);

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
                Intent i = new Intent(Product_List.this, BillShow.class);
                i.putExtra("url","http://express.accountantlalaji.com");
                startActivity(i);
            }
        });
        ImageView profile = (ImageView)findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Product_List.this, Profile.class);
                startActivity(i);

            }
        });

        ImageView setting = (ImageView)findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayoutSaleList.closeDrawers();
                Intent i = new Intent(Product_List.this, Setting.class);
                startActivity(i);

            }
        });

        ImageView support = (ImageView)findViewById(R.id.support);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Product_List.this, Support.class);
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

                ScrollView scrollView = (ScrollView) findViewById(R.id.scrollViewProduct);
              //  scrollView.pageScroll(View.FOCUS_UP);


                if (plusBtn==0){

                    int from = Integer.parseInt(Productfrom);
                    int fromNew = from+10;
                    Productfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Productto);
                    int toNew = to+10;
                    Productto = String.valueOf(toNew);

                    new Items().execute();

                }

                if (plusBtn==1){

                    int from = Integer.parseInt(Brandfrom);
                    int fromNew = from+10;
                    Brandfrom = String.valueOf(fromNew);

                    int to = Integer.parseInt(Brandto);
                    int toNew = to+10;
                    Brandto = String.valueOf(toNew);

                    new Brand().execute();

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
             //       Toast.makeText(Product_List.this,
             ///               "Oups! Can't open Facebook messenger app right now.",
               //             Toast.LENGTH_SHORT).show();
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


    private class Items extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            listitems_brand.clear();
            adapterSelect_brand = new MyAdapter_Brand(listitems_brand, Product_List.this);
            recyclerViewSelect_brand.setAdapter(adapterSelect_brand);

            super.onPreExecute();
            progressDialog = new ProgressDialog(Product_List.this);
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
            String url = getString(R.string.itemCate);
            String auth = getString(R.string.auth_key);
            String json = jp.ItemsJsonFromURL(url,auth, cmp_id, login_id, Productfrom, "10");
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            newProData = result;
            SharedPreferences pref = getApplicationContext().getSharedPreferences("newProData", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("newProData", newProData);
            editor.commit();



            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                      //  listitems_itemList.clear();
                        try {

                            JSONArray jsonArray = jo.getJSONArray("item_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);

                            }

                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                Listitem_items listitem = new Listitem_items(

                                        "",
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


                                );

                                listitems_itemList.add(listitem);
                                adapterSelect_itemList = new MyAdapter_Items(listitems_itemList, Product_List.this);
                                recyclerViewSelect_itemList.setAdapter(adapterSelect_itemList);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(itemCateRL, error_response , Snackbar.LENGTH_LONG);
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

    private class Brand extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            listitems_itemList.clear();
            adapterSelect_itemList = new MyAdapter_Items(listitems_itemList, Product_List.this);
            recyclerViewSelect_itemList.setAdapter(adapterSelect_itemList);


            super.onPreExecute();
            progressDialog = new ProgressDialog(Product_List.this);
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
            String url = getString(R.string.itemCate);
            String auth = getString(R.string.auth_key);
            String json = jp.ItemsJsonFromURL(url,auth, cmp_id, login_id, Brandfrom, "10");
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {
                    //   listitems_brand.clear();
                        try {

                            JSONArray jsonArray = jo.getJSONArray("brand_array");

                            final int numberc = jsonArray.length();

                            if (numberc<10){
                                saleViewMore.setVisibility(View.GONE);
                            }else {
                                saleViewMore.setVisibility(View.VISIBLE);
                            }

                            float results_total = 0;

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);

                                Listitem_brand listitem = new Listitem_brand(

                                        "",
                                        jo2.getString("id"),
                                        jo2.getString("login_id"),
                                        jo2.getString("cmp_id"),
                                        jo2.getString("hsn_id"),
                                        jo2.getString("brand_name"),
                                        jo2.getString("status"),
                                        jo2.getString("created_at")

                                );

                                listitems_brand.add(listitem);
                                adapterSelect_brand = new MyAdapter_Brand(listitems_brand, Product_List.this);
                                recyclerViewSelect_brand.setAdapter(adapterSelect_brand);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                        Snackbar snackbar = Snackbar
                                .make(itemCateRL, error_response , Snackbar.LENGTH_LONG);
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
        toolbarSaleList1 = (Toolbar) findViewById(R.id.toolbarProductList);
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
            progressDialog = new ProgressDialog(Product_List.this);
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
                        Picasso.with(Product_List.this)

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

    private class NewCategory extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Product_List.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            String cn = cateNameET.getText().toString();

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.newcate);
            String auth = getString(R.string.auth_key);
            String json = jp.NewCateJsonFromURL(url, auth, cmp_id, login_id, cn);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("cate");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        plusBtn=1;
                        listitems_brand.clear();
                        new Brand().execute();
                        iview.setVisibility(View.GONE);
                        cview.setVisibility(View.VISIBLE);
                        toolbRnAME.setText("Category List");
                        itemLL.setBackgroundColor(Color.parseColor("#ffedf5"));
                        //  cateLL.setBackgroundColor(Color.parseColor("#df0b62"));
                        LinearLayout showCate = (LinearLayout)findViewById(R.id.showCate);
                        showCate.setVisibility(View.VISIBLE);
                        LinearLayout showItem = (LinearLayout)findViewById(R.id.showItem);
                        showItem.setVisibility(View.GONE);


                    } else {

                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

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
                final List<Listitem_items> filteredList = new ArrayList<>();

                for (int i = 0; i < listitems_itemList.size(); i++) {
                    final String text = listitems_itemList.get(i).getModel_name().toLowerCase();

                    if (text.contains(s)) {
                        filteredList.add(listitems_itemList.get(i));
                    }

                    final String text2 = listitems_itemList.get(i).getBrand_name().toLowerCase();
                    if (text2.contains(s)) {
                        filteredList.add(listitems_itemList.get(i));
                    }
                }

                recyclerViewSelect_itemList.setLayoutManager(new LinearLayoutManager(Product_List.this));
                adapterSelect_itemList = new MyAdapter_Items(filteredList, Product_List.this);
                recyclerViewSelect_itemList.setAdapter(adapterSelect_itemList);
                adapterSelect_itemList.notifyDataSetChanged();



                final String s2 = query.toString().toLowerCase().trim();
                final List<Listitem_brand> filteredList2 = new ArrayList<>();

                for (int i = 0; i < listitems_brand.size(); i++) {
                    final String text = listitems_brand.get(i).getBrand_name().toLowerCase();

                    if (text.contains(s2)) {
                        filteredList2.add(listitems_brand.get(i));
                    }

                }

                recyclerViewSelect_brand.setLayoutManager(new LinearLayoutManager(Product_List.this));
                adapterSelect_brand = new MyAdapter_Brand(filteredList2, Product_List.this);
                recyclerViewSelect_brand.setAdapter(adapterSelect_brand);
                adapterSelect_brand.notifyDataSetChanged();
            }
        });
    }

}
