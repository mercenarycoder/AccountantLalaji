package com.vias.accountantlalaji;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Brand_view extends AppCompatActivity {

    String brandId;
    EditText cateNameET;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_view);


        ImageView backBrandView = (ImageView)findViewById(R.id.backBrandView);
        backBrandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView brandEdit = (ImageView)findViewById(R.id.brandEdit);
        ImageView brandDelete = (ImageView)findViewById(R.id.brandDelete);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
        String s = pref.getString("user_role",null);

        if (s.equals("staff")){
            brandEdit.setVisibility(View.GONE);
            brandDelete.setVisibility(View.GONE);
        }else {

        }

        brandEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater li = LayoutInflater.from(Brand_view.this);
                final View dialogView = li.inflate(R.layout.custom_dialog_change_brand, null);

                AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                        Brand_view.this);

                sayWindows.setTitle("Change Category Name");
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

                                    new changeBrand().execute();

                                } else {

                                    Toast.makeText(getApplicationContext(), "Category Name Required", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();



            }
        });


        brandDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new brandDelete().execute();
            }
        });

        brandId = getIntent().getStringExtra("id");

        String brandName = getIntent().getStringExtra("brandname");
        String brandstatus = getIntent().getStringExtra("brandstatus");
        String date = getIntent().getStringExtra("date");

        TextView brandNameee = (TextView)findViewById(R.id.brandNamee);
        brandNameee.setText(": "+brandName);

        TextView brandStatus = (TextView)findViewById(R.id.brandStatus);
        brandStatus.setText(": "+brandstatus);

        TextView brandCreated = (TextView)findViewById(R.id.brandCreated);
        brandCreated.setText(": "+date);


    }

    private class brandDelete extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Brand_view.this);
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
            String url = getString(R.string.brandDelete);
            String auth = getString(R.string.auth_key);
            String json = jp.AccountJsonFromURL(url,auth, brandId);
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("account", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("brand_delete");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Brand_view.this, Product_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


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


    private class changeBrand extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Brand_view.this);
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

            String name = cateNameET.getText().toString();

            JsonParser jp = new JsonParser();
            String url = getString(R.string.changebrand);
            Log.d("category_updateurl", url);
            String auth = getString(R.string.auth_key);
            String json = jp.changeBrandJsonFromURL(url, auth, cmp_id, login_id,user_id, brandId, name);
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("category_u_respose", result);
                try {

                    JSONObject jo = new JSONObject(result);
                    String res = jo.getString("category_update");

                    JSONObject jo2 = new JSONObject(res);

                    String error = jo2.optString("error");

                    if (error.equals("true")){
                        Toast.makeText(Brand_view.this,"Changed Successfully",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Brand_view.this, Product_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    }else {
                        Toast.makeText(Brand_view.this,"Changed Failed !",Toast.LENGTH_LONG).show();

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
