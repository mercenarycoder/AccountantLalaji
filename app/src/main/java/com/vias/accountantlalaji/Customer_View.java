package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer_View extends AppCompatActivity {
    String id;

    TextView cv_customerName, cv_customerMobile, cv_customerEmail, cv_customerShippingAddress,
            cv_customerBillingAddress, cv_customerGST, cv_customerBusinessName, cv_customerPinCode,
            cv_customerAccountName, cv_customerAccountNumber, cv_customerIFSCCode, cv_customerStatus;
    TextView cv_totalAmount, cv_paidAmount, cv_duesAmount, cv_availableAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__view);

        ImageView backCustomer = (ImageView)findViewById(R.id.backCustomer);
        backCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cv_customerName = (TextView)findViewById(R.id.cv_customerName);
        cv_customerMobile = (TextView)findViewById(R.id.cv_customerMobile);
        cv_customerEmail = (TextView)findViewById(R.id.cv_customerEmail);
        cv_customerShippingAddress = (TextView)findViewById(R.id.cv_customerShippingAddress);
        cv_customerBillingAddress = (TextView)findViewById(R.id.cv_customerBillingAddress);
        cv_customerGST = (TextView)findViewById(R.id.cv_customerGST);
        cv_customerBusinessName = (TextView)findViewById(R.id.cv_customerBusinessName);
        cv_customerPinCode = (TextView)findViewById(R.id.cv_customerPinCode);
        cv_customerAccountName = (TextView)findViewById(R.id.cv_customerAccountName);
        cv_customerAccountNumber = (TextView)findViewById(R.id.cv_customerAccountNumber);
        cv_customerIFSCCode = (TextView)findViewById(R.id.cv_customerIFSCCode);
        cv_customerStatus = (TextView)findViewById(R.id.cv_customerStatus);

        cv_totalAmount = (TextView)findViewById(R.id.cv_totalAmount);
        cv_paidAmount = (TextView)findViewById(R.id.cv_paidAmount);
        cv_duesAmount = (TextView)findViewById(R.id.cv_duesAmount);
        cv_availableAmount = (TextView)findViewById(R.id.cv_availableAmount);


        id = getIntent().getStringExtra("id");
        String customer_name = getIntent().getStringExtra("customer_name");
        cv_customerName.setText(customer_name);
        String customer_mobile = getIntent().getStringExtra("customer_mobile");
        cv_customerMobile.setText(customer_mobile);
        String customer_type = getIntent().getStringExtra("customer_type");
        String login_id = getIntent().getStringExtra("login_id");
        String cmp_id = getIntent().getStringExtra("cmp_id");
        String dob = getIntent().getStringExtra("dob");
        String email = getIntent().getStringExtra("email");
        cv_customerEmail.setText(": "+email);
        String billing_address = getIntent().getStringExtra("billing_address");
        cv_customerBillingAddress.setText(": "+billing_address);
        String shipping_address = getIntent().getStringExtra("shipping_address");
        cv_customerShippingAddress.setText(": "+shipping_address);
        String state_id = getIntent().getStringExtra("state_id");
        String pincode = getIntent().getStringExtra("pincode");
        cv_customerPinCode.setText(": "+pincode);
        String customer_gst = getIntent().getStringExtra("customer_gst");
        cv_customerGST.setText(": "+customer_gst);
        String status = getIntent().getStringExtra("status");
        cv_customerStatus.setText(": "+status);
        String buss_name = getIntent().getStringExtra("buss_name");
        cv_customerBusinessName.setText(": "+buss_name);
        String opening_account = getIntent().getStringExtra("opening_account");
        String account_name = getIntent().getStringExtra("account_name");
        cv_customerAccountName.setText(": "+account_name);
        String account_number = getIntent().getStringExtra("account_number");
        cv_customerAccountNumber.setText(": "+account_number);
        String bank_ifsc_code = getIntent().getStringExtra("bank_ifsc_code");
        cv_customerIFSCCode.setText(": "+bank_ifsc_code);
        String consumer_no = getIntent().getStringExtra("consumer_no");
        String created_at = getIntent().getStringExtra("created_at");
        String state_name = getIntent().getStringExtra("state_name");

        final ImageView customerDelete = (ImageView)findViewById(R.id.customerDelete);
        final ImageView customerEdit = (ImageView)findViewById(R.id.customerEdit);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
        String s = pref.getString("user_role",null);

        if (s.equals("staff")){
            customerDelete.setVisibility(View.GONE);
            customerEdit.setVisibility(View.GONE);
        }else {

        }

        customerDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new customerDelete().execute();
            }
        });


        new accountDetail().execute();
    }

    private class accountDetail extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_View.this);
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
            String url = getString(R.string.account);
            String auth = getString(R.string.auth_key);
            String json = jp.AccountJsonFromURL(url,auth, id);
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

                     //   Toast.makeText(getApplicationContext(), "wwwwww", Toast.LENGTH_LONG).show();

                        cv_totalAmount.setText("₹ "+jo.getString("total_amount"));
                        cv_paidAmount.setText("₹ "+jo.getString("paid_amount"));
                        cv_duesAmount.setText("₹ "+jo.getString("dues_amount"));
                        cv_availableAmount.setText("₹ "+jo.getString("available_amount"));


                    } else {



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

    private class customerDelete extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Customer_View.this);
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
            String url = getString(R.string.customerDelete);
            String auth = getString(R.string.auth_key);
            String json = jp.AccountJsonFromURL(url,auth, id);
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("account", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("customer_delete");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                          Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Customer_View.this, Customer_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                    } else {



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
        "error_msg": "Success",
        "cv_id": "29",
        "total_amount": "11928",
        "paid_amount": "1000",
        "dues_amount": "10928",
        "available_amount": "-999"
        }
        }*/
