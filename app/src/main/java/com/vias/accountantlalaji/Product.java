package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Product extends AppCompatActivity {


    EditText ProNameET, ProSpeciET, ProMOPET, ProPPET, ProBarcodeET;
    ImageView proImage;

    Button proSubmitBtn;

    int RESULT_LOAD_IMAGE = 1;
    String imagebitmap = "null";

    List<String> cateList = new ArrayList<String>();
    String cateStr;

    List<String> hsnList = new ArrayList<String>();
    String HSNStr;

    List<String> unitList = new ArrayList<String>();
    String unitStr;

    List<String> castList = new ArrayList<String>();
    String castStr;

    List<String> statusList = new ArrayList<String>();
    String statusStr;


    String mopNew;
    String ppNew;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ProNameET = (EditText)findViewById(R.id.ProNameET);
        ProSpeciET = (EditText)findViewById(R.id.ProSpeciET);
        ProMOPET = (EditText)findViewById(R.id.ProMOPET);
        ProPPET = (EditText)findViewById(R.id.ProPPET);
        ProBarcodeET = (EditText)findViewById(R.id.ProBarcodeET);

        statusList.add("active");
        statusList.add("deactive");

        proImage = (ImageView)findViewById(R.id.proImage);
        proImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


            }
        });

        ImageView backProduct = (ImageView)findViewById(R.id.backProduct);
        backProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        final ArrayAdapter<String> arrayAdapter_agec = new ArrayAdapter<String>(Product.this,
                android.R.layout.simple_dropdown_item_1line, cateList);

        final MaterialBetterSpinner materialDesignSpinner_agec = (MaterialBetterSpinner)
                findViewById(R.id.ProCateName);
        materialDesignSpinner_agec.setAdapter(arrayAdapter_agec);
        materialDesignSpinner_agec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                cateStr = materialDesignSpinner_agec.getText().toString();


            }
        });



        final ArrayAdapter<String> arrayAdapter_ageh = new ArrayAdapter<String>(Product.this,
                android.R.layout.simple_dropdown_item_1line, hsnList);

        final MaterialBetterSpinner materialDesignSpinner_ageh = (MaterialBetterSpinner)findViewById(R.id.ProHSNCode);
        materialDesignSpinner_ageh.setAdapter(arrayAdapter_ageh);
        materialDesignSpinner_ageh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                HSNStr = materialDesignSpinner_ageh.getText().toString();


            }
        });


        final ArrayAdapter<String> arrayAdapter_u = new ArrayAdapter<String>(Product.this,
                android.R.layout.simple_dropdown_item_1line, unitList);

        final MaterialBetterSpinner materialDesignSpinner_u = (MaterialBetterSpinner)findViewById(R.id.ProUnit);
        materialDesignSpinner_u.setAdapter(arrayAdapter_u);
        materialDesignSpinner_u.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //  payingBET.setText( materialDesignSpinner_age.getText().toString());
                unitStr = materialDesignSpinner_u.getText().toString();


            }
        });


        final ArrayAdapter<String> arrayAdapter_c = new ArrayAdapter<String>(Product.this,
                android.R.layout.simple_dropdown_item_1line, castList);

        final MaterialBetterSpinner materialDesignSpinner_c = (MaterialBetterSpinner)findViewById(R.id.ProCast);
        materialDesignSpinner_c.setAdapter(arrayAdapter_c);
        materialDesignSpinner_c.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                castStr = materialDesignSpinner_c.getText().toString();

            }
        });



        final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(Product.this,
                android.R.layout.simple_dropdown_item_1line, statusList);

        final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner)findViewById(R.id.ProStatus);
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
                statusStr = materialDesignSpinner_age.getText().toString();


            }
        });




        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {
                        JSONArray brand_array = jo.getJSONArray("brand_array");
                        JSONArray jsonArray = jo.getJSONArray("hsn_array");
                        JSONArray unit_array = jo.getJSONArray("unit_array");
                        JSONArray cast_array = jo.getJSONArray("cast_array");


                        final int numberc2 = brand_array.length();
                        for (int ii = 0; ii < numberc2; ii++) {
                            JSONObject jo2 = brand_array.getJSONObject(ii);
                            cateList.add(jo2.getString("brand_name"));
                        }

                        final int numberc = jsonArray.length();
                        for (int ii = 0; ii < numberc; ii++) {
                            JSONObject jo2 = jsonArray.getJSONObject(ii);
                            hsnList.add(jo2.getString("hsn_code"));
                        }

                        final int numbercu = unit_array.length();
                        for (int ii = 0; ii < numbercu; ii++) {
                            JSONObject jo2 = unit_array.getJSONObject(ii);
                            unitList.add(jo2.getString("unit_name"));
                        }

                        final int numberccc = cast_array.length();
                        for (int ii = 0; ii < numberccc; ii++) {
                            JSONObject jo2 = cast_array.getJSONObject(ii);
                            castList.add(jo2.getString("cast_name"));
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }


        ProMOPET.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub


                String ppEt = ProMOPET.getText().toString();

                if (ppEt != null && !ppEt.isEmpty() && !ppEt.equals("null")) {

                    if (HSNStr != null && !HSNStr.isEmpty() && !HSNStr.equals("null")) {

                        String hsnRate = HsnRate(HSNStr);

                        Float hsnFloat = Float.valueOf(hsnRate);
                        Float mopFloat = Float.valueOf(ProMOPET.getText().toString());

                        Float per = hsnFloat+100;

                        Float woGst = mopFloat*hsnFloat/per;

                        Float woGst2 = mopFloat-woGst;

                        String ss = new DecimalFormat("##.##").format(woGst2);

                        TextView mopView = (TextView)findViewById(R.id.mopView);
                        mopView.setText("Price : ₹ "
                                + ProMOPET.getText().toString() +
                               " - GST Rate : "+ hsnRate +" % = Base Price (w/t GST) : ₹ "+ ss );


                        mopNew = String.valueOf(woGst2);

                    }else {
                        Toast.makeText(Product.this, "Select HSN",Toast.LENGTH_LONG ).show();
                    }

                }



            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub

            }
        });



        ProPPET.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub

                String ppEt = ProPPET.getText().toString();

                if (ppEt != null && !ppEt.isEmpty() && !ppEt.equals("null")) {

                    if (HSNStr != null && !HSNStr.isEmpty() && !HSNStr.equals("null")) {

                        String hsnRate = HsnRate(HSNStr);

                        Float hsnFloat = Float.valueOf(hsnRate);
                        Float mopFloat = Float.valueOf(ProPPET.getText().toString());

                        Float woGst = mopFloat+mopFloat*hsnFloat/100;
                        String ss = new DecimalFormat("##.##").format(woGst);

                        TextView mopView = (TextView)findViewById(R.id.ppView);
                        mopView.setText("Base Price : ₹ "
                                + ProPPET.getText().toString() +
                                " + GST Rate : "+ hsnRate +" % =  Price : ₹ "+ ss );

                        ppNew = String.valueOf(woGst);

                        TextView profitView = (TextView)findViewById(R.id.profitView);

                        Float mopBP = Float.valueOf(ProMOPET.getText().toString());

                        Float profit = mopBP-woGst;
                        String ss2 = new DecimalFormat("##.##").format(profit);
                        profitView.setText("Your Profit : ₹ "+ss2);

                    }else {
                        Toast.makeText(Product.this, "Select HSN",Toast.LENGTH_LONG ).show();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });



        //////////////////////////////////////////////////////


        proSubmitBtn = (Button)findViewById(R.id.proSubmitBtn);
        proSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String proName = ProNameET.getText().toString();
                String mop= ProMOPET.getText().toString();
                String pp= ProPPET.getText().toString();

                if (proName != null && !proName.isEmpty() && !proName.equals("null")) {
                    if (cateStr != null && !cateStr.isEmpty() && !cateStr.equals("null")) {
                        if (HSNStr != null && !HSNStr.isEmpty() && !HSNStr.equals("null")) {
                            if (unitStr != null && !unitStr.isEmpty() && !unitStr.equals("null")) {
                                if (castStr != null && !castStr.isEmpty() && !castStr.equals("null")) {
                                    if (mop != null && !mop.isEmpty() && !mop.equals("null")) {
                                        if (pp != null && !pp.isEmpty() && !pp.equals("null")) {
                                            if (statusStr != null && !statusStr.isEmpty() && !statusStr.equals("null")) {


                                                new NewProductgory().execute();



                                            }else {
                                                Toast.makeText(getApplicationContext(), "Select Status", Toast.LENGTH_LONG).show();
                                            }
                                        }else {
                                            Toast.makeText(getApplicationContext(), "P.P Required", Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(), "M.O.P Required", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(), "Cast Required", Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Unit Required", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "HSN Required", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Category Name Required", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Product Name Required", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.proImage);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                imagebitmap = imagetoString(bmp);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);

        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private String imagetoString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }




    private class NewProductgory extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(Product.this);
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

            String cateID = CateID(cateStr);
            String hsnId = HsnID(HSNStr);
            String unitId = UnitID(unitStr);
            String castId = CastID(castStr);

            JsonParser jp = new JsonParser();
            String url = getString(R.string.newProduct);
            String auth = getString(R.string.auth_key);
            String json = jp.NewProductJsonFromURL(url, auth, cmp_id, login_id,
                    cateID, hsnId, unitId, castId,
                    ProNameET.getText().toString(),
                    imagebitmap,
                    ProSpeciET.getText().toString(),
                    ProMOPET.getText().toString(),
                    ProPPET.getText().toString(),
                    ppNew,
                    mopNew ,
                    ProBarcodeET.getText().toString(),
                    statusStr
            );
            return json;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("newproduct", result);
                int tb = 0;
                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("newproduct");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");

                    if (response.equals("true")) {

                        Intent i = new Intent(Product.this, Product_List.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


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



    public String CateID(String cateName){

        String CateId = null;

        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {
                        JSONArray brand_array = jo.getJSONArray("brand_array");

                        final int numberc2 = brand_array.length();
                        for (int ii = 0; ii < numberc2; ii++) {

                            JSONObject jo2 = brand_array.getJSONObject(ii);
                            String cn = jo2.getString("brand_name");

                            if (cn.equals(cateName)){
                                CateId = jo2.getString("id");
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }
     return CateId;
    }

    public String HsnID(String name){

        String Id = null;

        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {

                        JSONArray jsonArray = jo.getJSONArray("hsn_array");

                        final int numberc2 = jsonArray.length();
                        for (int ii = 0; ii < numberc2; ii++) {

                            JSONObject jo2 = jsonArray.getJSONObject(ii);

                            String hc = jo2.getString("hsn_code");

                            if (hc.equals(name)){

                                Id = jo2.getString("id");

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }
        return Id;
    }

    public String UnitID(String name){

        String Id = null;

        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {

                        JSONArray unit_array = jo.getJSONArray("unit_array");

                        final int numberc2 = unit_array.length();
                        for (int ii = 0; ii < numberc2; ii++) {

                            JSONObject jo2 = unit_array.getJSONObject(ii);

                            String hc = jo2.getString("unit_name");

                            if (hc.equals(name)){

                                Id = jo2.getString("id");

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }
        return Id;
    }

    public String CastID(String name){

        String Id = null;

        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {
                        JSONArray cast_array = jo.getJSONArray("cast_array");

                        final int numberc2 = cast_array.length();
                        for (int ii = 0; ii < numberc2; ii++) {

                            JSONObject jo2 = cast_array.getJSONObject(ii);

                            String hc = jo2.getString("cast_name");

                            if (hc.equals(name)){

                                Id = jo2.getString("id");

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }
        return Id;
    }

    public String HsnRate(String name){

        String Id = null;

        String result = getIntent().getStringExtra("newProData");
        if (null != result && !result.isEmpty()) {
            Log.d("result", result);
            try {
                JSONObject jo = (new JSONObject(result)).getJSONObject("item_cate_data");
                String response = jo.getString("error");
                String error_response = jo.getString("error_msg");
                if (response.equals("true")) {
                    try {

                        JSONArray jsonArray = jo.getJSONArray("hsn_array");

                        final int numberc2 = jsonArray.length();
                        for (int ii = 0; ii < numberc2; ii++) {

                            JSONObject jo2 = jsonArray.getJSONObject(ii);

                            String hc = jo2.getString("hsn_code");

                            if (hc.equals(name)){

                                Id = jo2.getString("hsn_rate");

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
        }
        return Id;
    }


}
