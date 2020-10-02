package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    RelativeLayout loginRL;
    LinearLayout userLoginLL, adminLoginLL;
    TextView userLoginTV, adminLOginTV;

    EditText user_id_et, user_pass_et, admin_id_et, admin_pass_et;
    Button user_login_Btn, admin_login_Btn;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        loginRL = (RelativeLayout)findViewById(R.id.loginRL);

        userLoginLL = (LinearLayout) findViewById(R.id.userLoginLL);
        adminLoginLL = (LinearLayout) findViewById(R.id.adminLoginLL);
        userLoginTV = (TextView) findViewById(R.id.userLoginTV);
        adminLOginTV = (TextView) findViewById(R.id.adminLoginTV);

        user_id_et = (EditText) findViewById(R.id.u_id_et);     // admin id
        user_pass_et = (EditText) findViewById(R.id.u_pass_et);    // admin pass
        admin_id_et = (EditText) findViewById(R.id.a_id_et);   /// staff sub admin id
        admin_pass_et = (EditText) findViewById(R.id.a_pass_et);   //  staff sub admin  pass

        user_login_Btn = (Button) findViewById(R.id.userLoginBtn);
        admin_login_Btn = (Button) findViewById(R.id.adminLoginBtn);

        SharedPreferences prefLogin = getApplicationContext().
                getSharedPreferences("Lalaji_Login", 0);
        if (prefLogin.contains("userId") && prefLogin.contains("userPass")){

            String id_str = prefLogin.getString("userId",null);
            admin_id_et.setText(id_str);
            String pass_str = prefLogin.getString("userPass",null);
            admin_pass_et.setText(pass_str);

            new AdminLogin().execute();


        }else {
            //login_ll.setVisibility(View.VISIBLE);
        }



        userLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* userLoginLL.setVisibility(View.VISIBLE);
                adminLoginLL.setVisibility(View.GONE);

                TranslateAnimation transAnim = new TranslateAnimation(0, 2000, 0, 0);
                transAnim.setStartOffset(00);
                //transAnim.setRepeatCount(-1);
                //transAnim.setRepeatMode(Animation.REVERSE);
                transAnim.setDuration(1000); // translate speed
                // transAnim.setInterpolator(new BounceInterpolator());
                adminLoginLL.startAnimation(transAnim);

                TranslateAnimation transAnim2 = new TranslateAnimation(-2000, 0, 0, 0);
                transAnim2.setDuration(1000);
                userLoginLL.startAnimation(transAnim2);
*/
              

                Intent i = new Intent(Login.this, Activity_NewQr.class);
                startActivity(i);
            }
        });


        adminLOginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLoginLL.setVisibility(View.GONE);
                adminLoginLL.setVisibility(View.VISIBLE);

                TranslateAnimation transAnim = new TranslateAnimation(0, 2000, 0, 0);
                transAnim.setDuration(1000);
                userLoginLL.startAnimation(transAnim);

                TranslateAnimation transAnim2 = new TranslateAnimation(-2000, 0, 0, 0);
                transAnim2.setDuration(1000);
                adminLoginLL.startAnimation(transAnim2);

            }
        });


        user_login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Toast.makeText(getApplicationContext(),"We are working on admin login",Toast.LENGTH_LONG).show();






            }
        });

        admin_login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AdminLogin().execute();
            }
        });

        TextView ship = (TextView)findViewById(R.id.ship);
        ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://www.accountantlalaji.com");
                startActivity(i);

            }
        });

        TextView ship2 = (TextView)findViewById(R.id.skipTV);
        ship2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://www.accountantlalaji.com");
                startActivity(i);

            }
        });

        TextView ship22 = (TextView)findViewById(R.id.skipTV2);
        ship22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://www.accountantlalaji.com");
                startActivity(i);

            }
        });

        TextView websiteTV = (TextView)findViewById(R.id.websiteTV);
        websiteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://www.accountantlalaji.com");
                startActivity(i);

            }
        });

        TextView callTV = (TextView)findViewById(R.id.callTV);
        callTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String number = "7987315256";
                    Uri call = Uri.parse("tel:" + number);
                    Intent surf = new Intent(Intent.ACTION_DIAL, call);
                    startActivity(surf);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        ImageView logoo = (ImageView)findViewById(R.id.logoo);
        logoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://www.accountantlalaji.com");
                startActivity(i);

            }
        });

        Button u_signupBtn = (Button)findViewById(R.id.u_signupBtn);
        u_signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i = new Intent(Login.this, BillShow.class);
               i.putExtra("url","http://express.accountantlalaji.com/signupapp");
               startActivity(i);

            }
        });

        Button signupBtn = (Button)findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, BillShow.class);
                i.putExtra("url","http://express.accountantlalaji.com/signupapp");
                startActivity(i);


            }
        });

    }


    private class AdminLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            JsonParser jp = new JsonParser();
            String url = getString(R.string.admin_login);
            String json = jp.UserLoginJsonFromURL(url,
                    admin_id_et.getText().toString(), admin_pass_et.getText().toString());
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("admin_login", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("admin_login");
                    String response = jo.getString("error");

                    //  JSONArray jsonArray = jo.getJSONArray("record");

                    if (response.equals("true")) {

                        SharedPreferences pref = getApplicationContext().
                                getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("userId", admin_id_et.getText().toString());
                        editor.putString("userPass", admin_pass_et.getText().toString());

                        editor.putString("auth_key", jo.getString("auth_key"));
                        editor.putString("user_id", jo.getString("id"));
                        editor.putString("store_id", jo.getString("store_id"));
                        editor.putString("login_id", jo.getString("login_id"));
                        editor.putString("cmp_id", jo.getString("cmp_id"));
                        editor.putString("user_name", jo.getString("user_name"));
                        editor.putString("user_email", jo.getString("user_email"));
                        editor.putString("user_mobile", jo.getString("user_mobile"));
                        editor.putString("user_address", jo.getString("user_address"));
                        editor.putString("user_role", jo.getString("user_role"));
                        editor.putString("cmp_name", jo.getString("cmp_name"));
                        editor.putString("cmp_logo", jo.getString("cmp_logo"));
                        editor.putString("store", jo.getString("store"));
                        editor.commit();


                        SharedPreferences pref2 = getApplicationContext().
                                getSharedPreferences("DrawerDetails", 0); // 0 - for private mode
                        SharedPreferences.Editor editor2 = pref2.edit();
                        editor2.putString("user_role", jo.getString("user_role"));
                        editor2.putString("cmp_name", jo.getString("cmp_name"));
                        editor2.putString("cmp_logo", jo.getString("cmp_logo"));
                        editor2.commit();

                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();

                        Intent ul= new Intent(Login.this, Sale_List.class);
                        startActivity(ul);
                        finish();


                    } else {
                        Toast.makeText(getApplicationContext(), "Check Id and Password", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

                Toast.makeText(getApplicationContext(), "Check Login Details !", Toast.LENGTH_LONG).show();
            }
        }

    }
}