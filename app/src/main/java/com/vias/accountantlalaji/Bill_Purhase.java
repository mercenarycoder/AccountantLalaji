package com.vias.accountantlalaji;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Bill_Purhase extends AppCompatActivity {

    ScrollView billSV;
    LinearLayout billLL;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;

    RelativeLayout bill_RL;

    Button submitBillBTN;
    Button submitBillBTN_Save_Print;

    ProgressDialog progressDialog;
    TextView totalBTV;
    EditText payingBET;

    List<String> payment2 = new ArrayList<String>();
    String paymentModeStr;

    private RecyclerView recyclerViewBill_Product;
    private RecyclerView.Adapter adapterBill_Product;
    private List<Listitem_Select_Product> listitems_Bill_Product;

    JSONArray jsonArrayP;

    EditText billNoET;
    Calendar mCurrentDate;
    int day, month, year;
    String dobStr;
    String igst = "0.0";
    String cgst;
    String sgst;

    String btnStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill__purhase);

        fn_permission();

        bill_RL = (RelativeLayout)findViewById(R.id.billRL);
        billLL = (LinearLayout)findViewById(R.id.billLL);

        TextView gsttv = (TextView) findViewById(R.id.b_gst);

        submitBillBTN = (Button) findViewById(R.id.submitBillBTN);
        submitBillBTN_Save_Print = (Button) findViewById(R.id.submitBillBTN2);

        ImageView billBack = (ImageView)findViewById(R.id.billBack);
        billBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Listitem_Select_Product list = new Listitem_Select_Product();
        //  int size = (filelist.size());
        //  Toast.makeText(getApplicationContext(),String.valueOf(size),Toast.LENGTH_LONG).show();
        billNoET = (EditText) findViewById(R.id.billNoET);

        final TextView date_tb_tv = (TextView) findViewById(R.id.date_bill_TV);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // HH:mm:ss
        String strDate = sdf.format(c.getTime());
        date_tb_tv.setText(strDate);
        dobStr = strDate;

        date_tb_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentDate = Calendar.getInstance();
                day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                month = mCurrentDate.get(Calendar.MONTH);
                year = mCurrentDate.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Bill_Purhase.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                        m = m + 1;

                        String dd = String.valueOf(d);
                        String mm = String.valueOf(m);

                        // String dd = String.valueOf(d);

                        if (m < 10) {

                            mm = "0" + mm;

                        }
                        if (d < 10) {

                            dd = "0" + dd;

                        }
                        dobStr = y + "-" + mm + "-" + dd;

                        date_tb_tv.setText(y + "-" + mm + "-" + dd);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        recyclerViewBill_Product = (RecyclerView) findViewById(R.id.recyclerView_Bill_Product);
        recyclerViewBill_Product.setHasFixedSize(true);
        recyclerViewBill_Product.setLayoutManager(new LinearLayoutManager(this));
        /*final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewSelect_Product.setLayoutManager(linearLayoutManager);*/
        listitems_Bill_Product = new ArrayList<>();

        SharedPreferences preff = getApplicationContext().getSharedPreferences("Lalaji_Product_JsonSata", 0); // 0 - for private mode

        int arr = Integer.parseInt(preff.getString("product_array", null));
        arr = arr - 1;
        jsonArrayP = new JSONArray();
        Double gt = 00.0;
        for (int i = 0; i <= arr; i++) {

            String gst = preff.getString("tax_amount" + i, null);
            Double ss = Double.parseDouble(gst);
            gt = gt + ss;


            Listitem_Select_Product listitem = new Listitem_Select_Product(

                    preff.getString("selectid" + i, null),
                    preff.getString("product_id" + i, null),
                    preff.getString("login_id" + i, null),
                    preff.getString("brand_id" + i, null),
                    preff.getString("cmp_id" + i, null),
                    preff.getString("hsn_id" + i, null),
                    preff.getString("unit_id" + i, null),
                    preff.getString("cast_id" + i, null),
                    preff.getString("model_name" + i, null),
                    preff.getString("model_image" + i, null),
                    preff.getString("model_specialization" + i, null),
                    preff.getString("model_mop" + i, null),
                    preff.getString("model_mrp" + i, null),
                    preff.getString("model_pp" + i, null),
                    preff.getString("model_pp_new" + i, null),
                    preff.getString("model_mop_new" + i, null),
                    preff.getString("model_stock" + i, null),
                    preff.getString("bar_code" + i, null),
                    preff.getString("bar_code_status" + i, null),
                    preff.getString("model_status" + i, null),
                    preff.getString("countItem" + i, null),
                    preff.getString("discunt" + i, null),
                    preff.getString("brand_name" + i, null),
                    preff.getString("hsn_rate" + i, null),
                    preff.getString("hsn_code" + i, null),
                    preff.getString("tax_amount" + i, null),
                    preff.getString("amount" + i, null)
            );


            listitems_Bill_Product.add(listitem);
            adapterBill_Product = new MyAdapter_Bill_Product(listitems_Bill_Product, Bill_Purhase.this);
            recyclerViewBill_Product.setAdapter(adapterBill_Product);


            String bs_w_d = preff.getString("model_mop_new" + i, null);
            Double bs_w_d2 = 0.0;
            if (bs_w_d != null && !bs_w_d.isEmpty() && !bs_w_d.equals("null")) {
                bs_w_d2 = Double.parseDouble(bs_w_d);
            }

            String dis = preff.getString("discunt" + i, null);
            Double dis2 = 0.0;
            if (dis != null && !dis.isEmpty() && !dis.equals("null") && !dis.equals("")) {
                dis2 = Double.parseDouble(dis);
            }

            String qua = preff.getString("countItem" + i, null);
            Double qua2 = 0.0;
            if (qua != null && !qua.isEmpty() && !qua.equals("null")) {
                qua2 = Double.parseDouble(qua);
            }

            Double bp_new = bs_w_d2 + dis2;
            String bp_new_str = String.valueOf(bp_new);

            Double item_amt = bs_w_d2*qua2;
            String item_amt_str = String.valueOf(item_amt);


//            String item_rate = String.valueOf(Double.parseDouble(preff.getString("model_mop_new" + i, null) + Double.parseDouble(preff.getString("discunt" + i, null))));
            //    Toast.makeText(getApplicationContext(),item_rate ,Toast.LENGTH_LONG).show();

            JSONObject jo = new JSONObject();
            try {

                jo.put("item_id", preff.getString("product_id" + i, null));
                jo.put("item_name", "");
                jo.put("item_rate", bp_new_str);
                jo.put("discount", preff.getString("discunt" + i, null));
                jo.put("item_qua", preff.getString("countItem" + i, null));
                jo.put("item_hsn", preff.getString("hsn_rate" + i, null));
                jo.put("item_amount", item_amt_str);

                jsonArrayP.put(jo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        gsttv.setText(String.valueOf(gt));
        Double cgstt = gt / 2;
        cgst = String.valueOf(cgstt);
        sgst = String.valueOf(cgstt);

        String totalItem = jsonArrayP.toString();

        SharedPreferences pref9 = getApplicationContext().getSharedPreferences("Sale_data", 0); // 0 - for private mode
        String cus_arrayStr = pref9.getString("saledata", null);

        totalBTV = (TextView) findViewById(R.id.totalBTV);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
        totalBTV.setText(pref.getString("total", null));

        payingBET = (EditText) findViewById(R.id.payingBET);
        payingBET.setText(pref.getString("total", null));

        try {

            JSONObject jo = (new JSONObject(cus_arrayStr)).getJSONObject("sale_data");
            //billNoET.setText(jo.getString("billno"));
            JSONArray jsonArray = jo.getJSONArray("payment_array");

            for (int ii = 0; ii < jsonArray.length(); ii++) {

                JSONObject jo2 = jsonArray.getJSONObject(ii);
                payment2.add(jo2.getString("account_name"));
                // state.add(jo2.getString("account_name"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ArrayAdapter<String> arrayAdapter_age = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, payment2);
        final MaterialBetterSpinner materialDesignSpinner_age = (MaterialBetterSpinner)
                findViewById(R.id.paymentS);
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
                paymentModeStr = materialDesignSpinner_age.getText().toString();

            }
        });





        submitBillBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bii = billNoET.getText().toString();
                String pay = payingBET.getText().toString();
                if (bii != null && !bii.isEmpty() && !bii.equals("null")) {
                    if (pay != null && !pay.isEmpty() && !pay.equals("null")) {
                        if (paymentModeStr != null && !paymentModeStr.isEmpty() && !paymentModeStr.equals("null")) {

                            btnStr = "sp";

                            new JSONPlaceOrder().execute();


                        } else {

                            Toast.makeText(getApplicationContext(), "Select Payment Mode", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Paying Amount.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Bill No", Toast.LENGTH_LONG).show();
                }

            }
        });

        submitBillBTN_Save_Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bii = billNoET.getText().toString();
                String pay = payingBET.getText().toString();
                if (bii != null && !bii.isEmpty() && !bii.equals("null")) {
                    if (pay != null && !pay.isEmpty() && !pay.equals("null")) {
                        if (paymentModeStr != null && !paymentModeStr.isEmpty() && !paymentModeStr.equals("null")) {
                            btnStr = "ssms";
                            new JSONPlaceOrder().execute();

                        } else {

                            Toast.makeText(getApplicationContext(), "Select Payment Mode", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Bill No.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Paying Amount.", Toast.LENGTH_LONG).show();
                }

            }
        });

    }




    private class JSONPlaceOrder extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Bill_Purhase.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String auth_key = getString(R.string.auth_key);
            String url = getString(R.string.porder);

            SharedPreferences prefLogin = getApplicationContext().getSharedPreferences("Lalaji_Login", 0); // 0 - for private mode

            String user_id = prefLogin.getString("user_id", null);
            String login_id = prefLogin.getString("login_id", null);
            String cmp_id = prefLogin.getString("cmp_id", null);


            SharedPreferences pref = getApplicationContext().getSharedPreferences("Lalaji_SaleData", 0); // 0 - for private mode
            String cus_id = pref.getString("cus_id", null);


            JsonParser jpo = new JsonParser();
            String json = jpo.JsonFromURLOrder(auth_key, url, login_id, cmp_id, cus_id, user_id,
                    billNoET.getText().toString(),
                    dobStr,
                    igst,
                    cgst,
                    sgst,
                    totalBTV.getText().toString(),
                    payingBET.getText().toString(),
                    paymentModeStr,
                    "",
                    jsonArrayP);


            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (null != result && !result.isEmpty()) {

                Log.d("order", result);

                try {

                    JSONObject jo = (new JSONObject(result)).getJSONObject("order");
                    String response = jo.getString("error");
                    String error_response = jo.getString("error_msg");
                    String invoice_idd = jo.getString("invoice_id");

                    if (response.equals("true")) {
                        //alert();
                        // Toast.makeText(getApplicationContext(),"Working", Toast.LENGTH_LONG).show();

                        //   Bitmap bitmap1 = loadBitmapFromView(billLL, billLL.getWidth(), billLL.getHeight());
                        //   saveBitmap(bitmap1);
                        //   Log.d("bitmap", String.valueOf(bitmap1));

                        //  ImageView img = (ImageView)findViewById(R.id.img);
                        //   img.setImageBitmap(bitmap1);

                        //    BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                        //  Bitmap bitmap = drawable.getBitmap();

                        //  imageToPFD(bitmap);

                        Intent i = new Intent(Bill_Purhase.this, BillView_purhase.class);
                        i.putExtra("status", btnStr);
                        i.putExtra("billno", billNoET.getText().toString());
                        i.putExtra("invoice_id", invoice_idd);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();


                    } else {
                        // Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        //alert2(error_response);
                        Snackbar snackbar = Snackbar
                                .make(bill_RL, error_response , Snackbar.LENGTH_LONG);
                        snackbar.show();

                        // Bitmap bitmap1 = loadBitmapFromView(billLL, billLL.getWidth(), billLL.getHeight());
                        // saveBitmap(bitmap1);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Submited Successfully")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent itoLogin = new Intent(getApplicationContext(), Sale_List.class);
                        itoLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(itoLogin);
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Lalaji");
        alert.show();
        setContentView(R.layout.activity_main);
    }

    public void alert2(String error_response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(error_response)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.setTitle("Lalaji");
        alert.show();
        setContentView(R.layout.activity_main);
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {

        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    public void saveBitmap(Bitmap bitmap) {

        String bill = billNoET.getText().toString();

        File direct  = new File(Environment.getExternalStorageDirectory() + "/Accountant_Lalaji");

        if (!direct .exists()) {
            File wallpaperDirectory = new File("/sdcard/Accountant_Lalaji/");
            wallpaperDirectory.mkdirs();
        }


        File imagePath  = new File(new File("/sdcard/Accountant_Lalaji/"), bill+".jpg");
        if (imagePath.exists()) {
            imagePath.delete();
        }

        //File imagePath = new File("/sdcard/screenshotdemo.jpg");

        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            // Toast.makeText(getApplicationContext(),imagePath.getAbsolutePath()+"",Toast.LENGTH_SHORT).show();
            boolean_save = true;

            //btn_screenshot.setText("Check image");

            Toast.makeText(getApplicationContext(),"Bill Saved",Toast.LENGTH_LONG).show();

            Log.e("ImageSave", "Saveimage");
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    public  void  imageToPFD(Bitmap bitmap){

        Document document = new Document();

        String directoryPath = android.os.Environment.getExternalStorageDirectory().toString();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(directoryPath + "/example.pdf")); //  Change pdf's name.
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();

        Image image = null;  // Change image's name and extension.
        try {
            image = Image.getInstance(directoryPath + "/" + "example.jpg");
            //  ImageView img = (ImageView)findViewById(R.id.img);
            // image = Image.getInstance("");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
        image.scalePercent(scaler);
        image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();


       /* String filename = "AAAAAA";

        write(filename, bitmap);
        if (write(filename,bitmap)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }*/

    }

    public Boolean write(String fname, Bitmap bm) {
        try {
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }
            Document document = new Document();
            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            String filename = bm.toString();
            com.itextpdf.text.Image image  =com.itextpdf.text.Image.getInstance(filename);
            document.add(image);
            document.add(new Paragraph("Hello World2!"));
            // step 5
            document.close();

            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(Bill_Purhase.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(Bill_Purhase.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(Bill_Purhase.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(Bill_Purhase.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }


}
