package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HSN_view extends AppCompatActivity {


    final Context mContext = this;
    EditText HSNCodeET, HSNDescET;
    List<String> hsnlists = new ArrayList<String>();
    String hsnStr;
    String hsnCodeStr;
    String hsnDescStr;

    TextView HSNCode, HSNDesc, HSNRate;

    String hsnID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsn_view);

        ImageView backHSNView = (ImageView)findViewById(R.id.backHSNView);
        backHSNView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        HSNCode = (TextView)findViewById(R.id.HSNCode);
        HSNDesc = (TextView)findViewById(R.id.HSNDesc);
        HSNRate = (TextView)findViewById(R.id.HSNRate);

        hsnID = getIntent().getStringExtra("id");
        final String code = getIntent().getStringExtra("code");
        HSNCode.setText(code);
        hsnCodeStr = code;

        final String desc = getIntent().getStringExtra("desc");
        HSNDesc.setText(desc);
        hsnDescStr = desc;

        final String rate = getIntent().getStringExtra("rate");
        HSNRate.setText(rate+" %");
        hsnStr = rate;

        ImageView HSNEdit = (ImageView)findViewById(R.id.HSNEdit);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
        String s = pref.getString("user_role",null);

        if (s.equals("staff")){

            HSNEdit.setVisibility(View.GONE);
        }else {

        }


        HSNEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(), "Under Construction", Toast.LENGTH_LONG ).show();

                hsnlists.add("0");
                hsnlists.add("5");
                hsnlists.add("12");
                hsnlists.add("14");
                hsnlists.add("18");
                hsnlists.add("28");

                final LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.custom_dialog_hsn, null);

                final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(HSN_view.this,
                        android.R.layout.simple_dropdown_item_1line, hsnlists);

                final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner) dialogView.findViewById(R.id.HSNPercent);
                materialDesignSpinner_age.setAdapter(arrayAdapter_age);
                materialDesignSpinner_age.setText(rate);
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
                        hsnStr = materialDesignSpinner_age.getText().toString();


                    }
                });


                AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                        HSN_view.this);

                sayWindows.setTitle("HSN Edit");
                // set custom dialog icon
                sayWindows.setIcon(R.mipmap.percentage);
                sayWindows.setView(dialogView);
                sayWindows.setCancelable(false);

                sayWindows.setPositiveButton("ok", null);
                sayWindows.setNegativeButton("cancel", null);
                //sayWindows.setAdapter(listWords,null);
                HSNCodeET = (EditText) dialogView.findViewById(R.id.HSNCodeET);
                HSNDescET = (EditText) dialogView.findViewById(R.id.HSNDescET);

                if (code != null && !code.isEmpty() && !code.equals("null")) {
                    HSNCodeET.setText(code);
                }

                if (desc != null && !desc.isEmpty() && !desc.equals("null")) {
                    HSNDescET.setText(desc);
                }

                final AlertDialog mAlertDialog = sayWindows.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                hsnCodeStr = HSNCodeET.getText().toString();
                                hsnDescStr = HSNDescET.getText().toString();

                                if (hsnCodeStr != null && !hsnCodeStr.isEmpty() && !hsnCodeStr.equals("null")) {

                                    if (hsnStr != null && !hsnStr.isEmpty() && !hsnStr.equals("null")) {

                                        new HSN_Edit().execute();
                                        mAlertDialog.dismiss();


                                    } else {

                                        Toast.makeText(getApplicationContext(), "HSN Percent Required", Toast.LENGTH_LONG).show();

                                    }
                                } else {

                                    Toast.makeText(getApplicationContext(), "HSN Code Required", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                });
                mAlertDialog.show();




            }
        });


    }

    private class HSN_Edit extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(HSN_view.this);
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
            String url = getString(R.string.hsn_edit);
            String auth = getString(R.string.auth_key);
            String json = jp.HSNEditJsonFromURL(url, auth, cmp_id, login_id,hsnCodeStr, hsnDescStr, hsnStr,hsnID);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("hsn");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Intent i = new Intent(HSN_view.this, HSN_List.class);
                        startActivity(i);
                        finish();


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
