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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity {

    final Context mContext = this;

    EditText pNameET, pRoleET, pEmailET, pPasswordET, pMobileET, pAddressET, pStateET, pCityET;
    private ProgressDialog progressDialog;

    EditText pCompanyET, pStoreET;

    EditText oldPassET, newPassET, newPassET2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        new ProfileData().execute();

        pCompanyET = (EditText) findViewById(R.id.pCompanyET);
        pStoreET = (EditText) findViewById(R.id.pStoreET);

        pNameET = (EditText) findViewById(R.id.pNameET);
        pRoleET = (EditText) findViewById(R.id.pRoleET);
        pEmailET = (EditText) findViewById(R.id.pEmailET);
        pPasswordET = (EditText) findViewById(R.id.pPasswordET);
        pMobileET = (EditText) findViewById(R.id.pMobileET);
        pAddressET = (EditText) findViewById(R.id.pAddressET);
        pStateET = (EditText) findViewById(R.id.pStateET);
        pCityET = (EditText) findViewById(R.id.pCityET);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode

        pCompanyET.setText(pref.getString("cmp_name",null ));
        pStoreET.setText(pref.getString("store",null ));



        Button profileEditSubmitBtn = (Button) findViewById(R.id.profileEditSubmitBtn);
        profileEditSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = pNameET.getText().toString();
                String mobile = pMobileET.getText().toString();
                String add = pAddressET.getText().toString();
                String state = pStateET.getText().toString();
                String city = pCityET.getText().toString();

                if (name != null && !name.isEmpty() && !name.equals("null")) {

                    if (mobile != null && !mobile.isEmpty() && !mobile.equals("null")) {

                        if (add != null && !add.isEmpty() && !add.equals("null")) {

                            if (state != null && !state.isEmpty() && !state.equals("null")) {

                                if (city != null && !city.isEmpty() && !city.equals("null")) {

                                    new ProfileUpdate().execute();

                                } else {
                                    Toast.makeText(Profile.this, "State Required", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(Profile.this, "State Required", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(Profile.this, "Address Required", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(Profile.this, "Mobile No. Required", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Profile.this, "Name Required", Toast.LENGTH_LONG).show();
                }

            }
        });


        ImageView backProfileView = (ImageView)findViewById(R.id.backProfileView);
        backProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RelativeLayout changePassRL = (RelativeLayout)findViewById(R.id.changePassRL);
        changePassRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater li = LayoutInflater.from(mContext);
                final View dialogView = li.inflate(R.layout.custom_dialog_change_pass, null);

                AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                        Profile.this);

                sayWindows.setTitle("Change Password");
                // set custom dialog icon
                sayWindows.setIcon(R.mipmap.padlock);
                sayWindows.setView(dialogView);
                sayWindows.setCancelable(false);

                sayWindows.setPositiveButton("ok", null);
                sayWindows.setNegativeButton("cancel", null);
                //sayWindows.setAdapter(listWords,null);
                oldPassET = (EditText) dialogView.findViewById(R.id.oldPassET);
                newPassET = (EditText) dialogView.findViewById(R.id.newPassET);
                newPassET2 = (EditText) dialogView.findViewById(R.id.newPassET2);


                final AlertDialog mAlertDialog = sayWindows.create();
                mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                if (!oldPassET.getText().toString().equals("") || !newPassET.getText().toString().equals("") || !newPassET2.getText().toString().equals("")){

                                    if (newPassET.getText().toString().equals(newPassET2.getText().toString())){

                                        new changePassword().execute();
                                        mAlertDialog.dismiss();




                                    }else {
                                        Toast.makeText(Profile.this,"Retype New Password should be same",Toast.LENGTH_LONG).show();
                                    }

                                }else {

                                    Toast.makeText(Profile.this,"All Field Required !",Toast.LENGTH_LONG).show();

                                }





                            }
                        });
                    }
                });
                mAlertDialog.show();


            }
        });

    }

    private class ProfileData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Profile.this);
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
            String url = getString(R.string.profile);
            String auth = getString(R.string.auth_key);
            String json = jp.ProfiledataJsonFromURL(url, auth, cmp_id, login_id, user_id);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("profile");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        try {

                            JSONArray jsonArray = jo.getJSONArray("profile_array");

                            final int numberc = jsonArray.length();

                            for (int ii = 0; ii < numberc; ii++) {

                                JSONObject jo2 = jsonArray.getJSONObject(ii);


                                String name = jo2.getString("user_name");
                                if (name != null && !name.isEmpty() && !name.equals("null")) {
                                    pNameET.setText(name);
                                }
                                String user_email = jo2.getString("user_email");
                                if (user_email != null && !user_email.isEmpty() && !user_email.equals("null")) {
                                    pEmailET.setText(user_email);
                                }
                                String user_pass = jo2.getString("user_pass");
                                if (user_pass != null && !user_pass.isEmpty() && !user_pass.equals("null")) {
                                    pPasswordET.setText(user_pass);
                                }
                                String user_mobile = jo2.getString("user_mobile");
                                if (user_mobile != null && !user_mobile.isEmpty() && !user_mobile.equals("null")) {
                                    pMobileET.setText(user_mobile);
                                }
                                String user_address = jo2.getString("user_address");
                                if (user_address != null && !user_address.isEmpty() && !user_address.equals("null")) {
                                    pAddressET.setText(user_address);
                                }
                                String user_state = jo2.getString("user_state");
                                if (user_state != null && !user_state.isEmpty() && !user_state.equals("null")) {
                                    pStateET.setText(user_state);
                                }
                                String user_city = jo2.getString("user_city");
                                if (user_city != null && !user_city.isEmpty() && !user_city.equals("null")) {
                                    pCityET.setText(user_city);
                                }
                                String user_role = jo2.getString("user_role");
                                if (user_role != null && !user_role.isEmpty() && !user_role.equals("null")) {
                                    pRoleET.setText(user_role);
                                }


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

    private class ProfileUpdate extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Profile.this);
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
            String url = getString(R.string.profile_update);
            String auth = getString(R.string.auth_key);
            String json = jp.ProfileEditJsonFromURL(url, auth, cmp_id, login_id, user_id,
                    pNameET.getText().toString(),
                    pRoleET.getText().toString(),
                    pEmailET.getText().toString(),
                    pPasswordET.getText().toString(),
                    pMobileET.getText().toString(),
                    pAddressET.getText().toString(),
                    pStateET.getText().toString(),
                    pCityET.getText().toString()


                    );
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("profile_update");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Toast.makeText(Profile.this, "Profile Updated", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Profile.this, Profile.class);
                        startActivity(i);
                        finish();


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

    private class changePassword extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Profile.this);
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

            String old_pass = oldPassET.getText().toString();
            String new_pass = newPassET.getText().toString();
            String con_pass = newPassET2.getText().toString();

            JsonParser jp = new JsonParser();
            String url = getString(R.string.changepassword);
            Log.d("changepasswordURL", url);
            String auth = getString(R.string.auth_key);
            String json = jp.changePassJsonFromURL(url, auth, cmp_id, login_id, user_id, old_pass, new_pass, con_pass);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("changepasswordRes", result);
                try {

                    JSONObject jo = new JSONObject(result);
                    String res = jo.getString("changepassword");
                    // Invalid Password!!

                    if (res.equals("Invalid Password!!")) {

                        Toast.makeText(getApplicationContext(), "Check Old Password", Toast.LENGTH_LONG).show();

                    } else if(res.equals("your both password mismatch!!!")){

                        Toast.makeText(getApplicationContext(), "Your both password mismatch!!! ", Toast.LENGTH_LONG).show();



                    }else {
                        Toast.makeText(getApplicationContext(), "Password Changed ", Toast.LENGTH_LONG).show();

                        Toast.makeText(getApplicationContext(), "Login Again ", Toast.LENGTH_LONG).show();

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
