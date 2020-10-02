package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Product_View extends AppCompatActivity {

    String id;
    TextView productSold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__view);

        ImageView productImage = (ImageView)findViewById(R.id.productImage);

        id = getIntent().getStringExtra("idd");

        final String imgg = getIntent().getStringExtra("model_image");
        String img = getString(R.string.productLogo)+imgg;
        if (img!=null && !img.isEmpty() && !img.equals("") && !img.equals("null")){
            Picasso.with(Product_View.this)
                    //.load("http://posbooks.com/images/01.jpg")
                    .load(img)
                    .config(Bitmap.Config.RGB_565)
                    .error(R.drawable.magicdots)
                    .into(productImage);
        }

        ImageView backProductView = (ImageView)findViewById(R.id.backProductView);
        backProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView productName = (TextView)findViewById(R.id.productName);
        final String model_name = getIntent().getStringExtra("model_name");
        productName.setText(model_name);

        TextView productAvailable = (TextView)findViewById(R.id.productAvailable);
        String model_stock = getIntent().getStringExtra("model_stock");
        productAvailable.setText("Available : "+model_stock);


        productSold = (TextView)findViewById(R.id.productSold);
       // String model_sold = "12";
       // productSold.setText("Sold : "+model_sold);

        TextView productBaseMOP = (TextView)findViewById(R.id.productBaseMOP);
        final String model_mop_new = getIntent().getStringExtra("model_mop_new");
        productBaseMOP.setText(": ₹ "+model_mop_new);

        TextView productSellingPrice = (TextView)findViewById(R.id.productSellingPrice);
        final String model_mop = getIntent().getStringExtra("model_mop");
        productSellingPrice.setText(": ₹ "+model_mop);

        TextView productBasePP = (TextView)findViewById(R.id.productBasePP);
        final String model_pp_new = getIntent().getStringExtra("model_pp_new");
        productBasePP.setText(": ₹ "+model_pp_new);



        TextView productPurchasePrice = (TextView)findViewById(R.id.productPurchasePrice);
        final String model_pp = getIntent().getStringExtra("model_pp");
        productPurchasePrice.setText(": ₹ "+model_pp);


        TextView productBarcode = (TextView)findViewById(R.id.productBarcode);
        final String bar_code = getIntent().getStringExtra("bar_code");
        productBarcode.setText(": "+bar_code);


        TextView productDesc = (TextView)findViewById(R.id.productDesc);
        final String model_specialization = getIntent().getStringExtra("model_specialization");
        productDesc.setText(": "+model_specialization);

        TextView productStatus = (TextView)findViewById(R.id.productStatus);
        final String model_status = getIntent().getStringExtra("model_status");
        productStatus.setText(": "+model_status);


        ImageView productEdit = (ImageView)findViewById(R.id.productEdit);
        ImageView productDelete = (ImageView)findViewById(R.id.productDelete);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode
        String s = pref.getString("user_role",null);

        if (s.equals("staff")){
            productEdit.setVisibility(View.GONE);
            productDelete.setVisibility(View.GONE);
        }else {

        }

        productEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Product_View.this, Product_edit.class);
                i.putExtra("id",id);
                i.putExtra("imgg",imgg);
                i.putExtra("model_name",model_name);
                i.putExtra("model_mop_new",model_mop_new);
                i.putExtra("model_mop",model_mop);
                i.putExtra("model_pp_new",model_pp_new);
                i.putExtra("model_pp",model_pp);
                i.putExtra("bar_code",bar_code);
                i.putExtra("model_specialization",model_specialization);
                i.putExtra("model_status",model_status);
                i.putExtra("id",id);

                startActivity(i);

            }
        });


        productDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new productDelete().execute();

            }
        });

        new productSold().execute();



    }




    private class productDelete extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Product_View.this);
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
            String url = getString(R.string.productDelete);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("product_delete");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(Product_View.this, Product_List.class);
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

    private class productSold extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Product_View.this);
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
            String url = getString(R.string.productSold);
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

                    JSONObject jo = (new JSONObject(result)).getJSONObject("modle_sold");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        String sold = jo.getString("sold");
                        productSold.setText("Sold : "+sold);

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


}
