package com.vias.accountantlalaji;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonParser {
    public JsonParser() {
    }

    public String UserLoginJsonFromURL(String urls, String id, String pass){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("id", id);
            loginData.put("pass", pass);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();
            return finalJson;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String SaledataJsonFromURL(String urls, String auth_key, String cmp_id, String login_id){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String JsonFromURLOrder(String auth_key,
                                   String urlS,
                                   String login_id,
                                   String cmp_id,
                                   String cus_id,
                                   String user_id,
                                   String billno,
                                   String date1,
                                   String igst,
                                   String cgst,
                                   String sgst,
                                   String total9,
                                   String totalpaid,
                                   String paymentModeStr,
                                   String remark,
                                   JSONArray jsonArray){


        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urlS);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();

            loginData.put("auth_key", auth_key);
            loginData.put("login_id", login_id);
            loginData.put("cmp_id", cmp_id);
            loginData.put("cus_id", cus_id);
            loginData.put("user_id", user_id);
            loginData.put("billno", billno);
            loginData.put("date1", date1);
            loginData.put("igst", igst);
            loginData.put("cgst", cgst);
            loginData.put("sgst", sgst);
            loginData.put("total9", total9);
            loginData.put("totalpaid", totalpaid);
            loginData.put("remark", remark);
            loginData.put("paymentModeStr", paymentModeStr);

            String s = String.valueOf(jsonArray);
            loginData.put("json_total_items", s);
            Log.d("ListitemJsonData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();
            //jLoginObj = new JSONObject(finalJson);
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String SignUpJsonFromURL(String auth_key, String cmpid, String loginid,
                                    String name, String mob, String email, String add,
                                    String urlS, String state, String busName, String gst, String type){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {

            URL url = new URL(urlS);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmpid);
            loginData.put("login_id", loginid);
            loginData.put("name", name);
            loginData.put("mob", mob);
            loginData.put("email", email);
            loginData.put("add", add);
            loginData.put("state", state);
            loginData.put("bname", busName);
            loginData.put("gst", gst);
            loginData.put("type", type);
            //loginData.put("pass", uPass);

            Log.d("LoginJsonData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {

                buffer.append(line);

            }

            String finalJson = buffer.toString();
            //jLoginObj = new JSONObject(finalJson);

            return finalJson;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public String SaledataListJsonFromURL(String urls, String auth_key,
                                          String cmp_id, String login_id, String user_id,
                                          String from, String to){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("created_by", user_id);
            loginData.put("from", from);
            loginData.put("to", to);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String CustomerListJsonFromURL(String urls, String auth_key, String cmp_id, String login_id, String type, String from, String to){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("type", type);
            loginData.put("from", from);
            loginData.put("to", to);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String StateListJsonFromURL(String urls, String auth_key){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ItemsJsonFromURL(String urls, String auth_key, String cmp_id, String login_id, String from, String to){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("from", from);
            loginData.put("to", to);

            Log.e("DrawerData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String NewHSNJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                    String code, String desc, String rate){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("hsn_desc", desc);
            loginData.put("hsn_rate", rate);
            loginData.put("hsn_code", code);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String HSNEditJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                    String code, String desc, String rate, String hsnID){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("hsn_desc", desc);
            loginData.put("hsn_rate", rate);
            loginData.put("hsn_code", code);
            loginData.put("hsnID", hsnID);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String NewCateJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                    String cn){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("cn", cn);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String NewAccountJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                     String aopenamount, String atype, String aname  ){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("aname", aname);
            loginData.put("atype", atype);
            loginData.put("aopenamount", aopenamount);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String AccountEditJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                        String aopenamount, String atype, String aname , String accid ){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("aname", aname);
            loginData.put("atype", atype);
            loginData.put("aopenamount", aopenamount);
            loginData.put("accid", accid);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String NewProductJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                        String cate, String HSN, String unit, String cast,
                                        String ProName, String image, String ProSpeci,
                                        String ProMOP, String ProPP, String newPP,
                                        String newMOP, String ProBarcode, String status){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);

            loginData.put("cate", cate);
            loginData.put("HSN", HSN);
            loginData.put("unit", unit);
            loginData.put("cast", cast);
            loginData.put("ProName", ProName);

            loginData.put("ProSpeci", ProSpeci);
            loginData.put("ProMOP", ProMOP);
            loginData.put("ProPP", ProPP);
            loginData.put("newPP", newPP);
            loginData.put("newMOP", newMOP);
            loginData.put("ProBarcode", ProBarcode);
            loginData.put("status", status);
            loginData.put("imagess", image);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String editProductJsonFromURL(String urls, String auth_key, String cmp_id, String login_id,
                                        String cate, String HSN, String unit, String cast,
                                        String ProName, String image, String ProSpeci,
                                        String ProMOP, String ProPP, String newPP,
                                        String newMOP, String ProBarcode, String status, String pid){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("login_id", login_id);
            loginData.put("cmp_id", cmp_id);

            loginData.put("pro_id", pid);

            loginData.put("cate", cate);
            loginData.put("hsn", HSN);
            loginData.put("unit", unit);
            loginData.put("cast", cast);
            loginData.put("pro_name", ProName);

            loginData.put("pro_speci", ProSpeci);
            loginData.put("pro_mop", ProMOP);
            loginData.put("pro_pp", ProPP);
            loginData.put("pro_new_pp", newPP);
            loginData.put("pro_new_mop", newMOP);
            loginData.put("pro_barcode", ProBarcode);
            loginData.put("status", "active");
            loginData.put("imagess", image);

            Log.e("pEdit", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String AccountJsonFromURL(String urls, String auth_key, String cv_id){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cv_id", cv_id);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String SaleReportJsonFromURL(String urls, String auth_key, String cmp_id, String login_id, String user_id,
                                        String from, String to,
                                        String date_from, String date_to,
                                        String model_id,String brand_id,
                                        String barcode,String mode,
                                        String type

    ){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("created_by", user_id);
            loginData.put("from", from);
            loginData.put("to", to);
            loginData.put("date_from", date_from);
            loginData.put("date_to", date_to);
            loginData.put("model_id", model_id);
            loginData.put("brand_id", brand_id);
            loginData.put("barcode", barcode);
            loginData.put("mode", mode);
            loginData.put("type", type);

            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String ProfiledataJsonFromURL(String urls, String auth_key, String cmp_id, String login_id, String user_id){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("user_id", user_id);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String ProfileEditJsonFromURL(String urls, String auth_key, String cmp_id, String login_id, String user_id
            , String name, String role, String email, String password
            , String mobile, String address, String state, String city){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("user_id", user_id);

            loginData.put("name", name);
            loginData.put("role", role);
            loginData.put("email", email);
            loginData.put("password", password);
            loginData.put("mobile", mobile);
            loginData.put("address", address);
            loginData.put("state", state);
            loginData.put("city", city);


            Log.d("LoginData", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String changePassJsonFromURL(String urls, String auth_key,
                                        String cmp_id, String login_id, String user_id,
                                        String old_pass, String new_pass, String con_pass){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("user_id", user_id);

            loginData.put("old_pass", old_pass);
            loginData.put("new_pass", new_pass);
            loginData.put("con_pass", con_pass);

            Log.d("changepasswordRess", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String changeBrandJsonFromURL(String urls, String auth_key,
                                        String cmp_id, String login_id, String user_id, String cat_id,
                                        String name){

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();

            JSONObject loginData = new JSONObject();
            loginData.put("auth_key", auth_key);
            loginData.put("cmp_id", cmp_id);
            loginData.put("login_id", login_id);
            loginData.put("user_id", user_id);
            loginData.put("cat_id", cat_id);
            loginData.put("cat_name", name);
            loginData.put("cat_status", "active");


            Log.d("changebrandRess", "---> " + loginData);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(loginData.toString());
            wr.flush();
            wr.close();

            InputStream stream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String finalJson = buffer.toString();
            return finalJson;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


