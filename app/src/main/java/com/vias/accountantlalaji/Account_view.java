package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Account_view extends AppCompatActivity {

    final Context mContext = this;
    EditText AccountNameET, openingAmountET;
    List<String> accountTypelists = new ArrayList<String>();
    String accountTypeStr;
    String AccountNameStr;
    String openingAmountStr;


    String accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);

        new AccountType().execute();

        ImageView backAccountView = (ImageView)findViewById(R.id.backAccountView);
        backAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView accountDelete = (ImageView)findViewById(R.id.accountDelete);
        ImageView accountEdit = (ImageView)findViewById(R.id.accountEdit);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
        String s = pref.getString("user_role",null);

        if (s.equals("staff")){
            accountDelete.setVisibility(View.GONE);
            accountEdit.setVisibility(View.GONE);
        }else {

        }

        accountDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new accountDelete().execute();
            }
        });

        accountID = getIntent().getStringExtra("id");

        final TextView accountName = (TextView)findViewById(R.id.accountName);
        final String an = getIntent().getStringExtra("an");
        accountName.setText(": "+an);
        AccountNameStr = an;

        final TextView accountType = (TextView)findViewById(R.id.accountType);
        final String at = getIntent().getStringExtra("at");
        accountType.setText(": "+at);
        accountTypeStr = at;

        TextView accountAmount = (TextView)findViewById(R.id.accountAmount);
        final String oa = getIntent().getStringExtra("oa");
        accountAmount.setText(": "+oa);
        openingAmountStr = oa;

        TextView accountStatus = (TextView)findViewById(R.id.accountStatus);
        String status = getIntent().getStringExtra("status");
        accountStatus.setText(": "+status);

        TextView accountDate = (TextView)findViewById(R.id.accountDate);
        String date = getIntent().getStringExtra("date");
        accountDate.setText(": "+date);


        accountEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.customdialog_account, null);

                final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(Account_view.this,
                        android.R.layout.simple_dropdown_item_1line, accountTypelists);

                final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner) dialogView.findViewById(R.id.AccountType);
                materialDesignSpinner_age.setAdapter(arrayAdapter_age);
                materialDesignSpinner_age.setText(at);
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
                        Account_view.this);

                sayWindows.setTitle("Account Edit");
                // set custom dialog icon
                sayWindows.setIcon(R.mipmap.account);
                sayWindows.setView(dialogView);
                sayWindows.setCancelable(false);

                sayWindows.setPositiveButton("ok", null);
                sayWindows.setNegativeButton("cancel", null);
                //sayWindows.setAdapter(listWords,null);
                AccountNameET = (EditText) dialogView.findViewById(R.id.AccountNameET);
                openingAmountET = (EditText) dialogView.findViewById(R.id.openingAmountET);

                AccountNameET.setText(an);
                openingAmountET.setText(oa);


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



    }

    private class NewAccount extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_view.this);
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
            String url = getString(R.string.account_edit);
            String auth = getString(R.string.auth_key);
            String json = jp.AccountEditJsonFromURL(url,auth, cmp_id, login_id,
                    openingAmountStr,accountTypeStr,AccountNameStr, accountID);
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

                        Intent i = new Intent(Account_view.this, Account_List.class);
                        startActivity(i);
                        finish();


                    } else {



                       Toast.makeText(getApplicationContext(), error_response, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

            }
        }

    }


    private class accountDelete extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_view.this);
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
            String url = getString(R.string.accountDelete);
            String auth = getString(R.string.auth_key);
            String json = jp.AccountJsonFromURL(url,auth, accountID);
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("account", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("account_delete");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Account_view.this, Account_List.class);
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

    private class AccountType extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {


            super.onPreExecute();
            progressDialog = new ProgressDialog(Account_view.this);
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

                      Toast.makeText(getApplicationContext(), error_response, Toast.LENGTH_LONG).show();
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
