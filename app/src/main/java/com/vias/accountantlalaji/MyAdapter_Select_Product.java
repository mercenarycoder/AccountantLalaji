package com.vias.accountantlalaji;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

public class MyAdapter_Select_Product extends RecyclerView.Adapter<MyAdapter_Select_Product.ViewHolder> {

    int cou = 1;
    private List<Listitem_Select_Product> listitemSelectProducts;
    private Context c;



    public MyAdapter_Select_Product(List<Listitem_Select_Product> listitem_select_products, Context cc) {
        this.listitemSelectProducts = listitem_select_products;
        this.c = cc;
    }


    @Override
    public MyAdapter_Select_Product.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_product, parent, false);
        return new MyAdapter_Select_Product.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Select_Product.ViewHolder holder, final int position) {


        final Listitem_Select_Product listitem = listitemSelectProducts.get(position);


        // Sale.grandTotal_tv.setText("232323");
      //  Toast.makeText(c,listitem.getAmount(),Toast.LENGTH_LONG).show();

        holder.productNameTV.setText(listitem.getModel_name());
        holder.p_brandnameTV.setText("( "+listitem.getBrand_name()+" )");
        holder.p_barcodeTV.setText(listitem.getBar_code());

        String bp =  listitem.getModel_mop_new();
        float lon = 0;
        if (bp != null && !bp.isEmpty() && !bp.equals("null")) {
            lon = Math.round(Float.parseFloat(bp));
        }

        holder.p_priceTV.setText("₹ "+lon);

        holder.item_count.setText(listitem.getCountItem());
        holder.item_count2.setText(listitem.getCountItem());
        holder.dis_ET.setText("");
        holder.gstTV.setText(listitem.getHsn_rate()+" %");
        holder.taxTV.setText("₹ "+listitem.getTax_amount());
        holder.amountTV.setText("₹ "+listitem.getAmount());
        final String t = listitem.getAmount();


        holder.p_delete.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

               // Toast.makeText(c,t,Toast.LENGTH_LONG).show();

                SharedPreferences pref_Lalaji_Total = c.getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
                SharedPreferences.Editor editor_Lalaji_Total = pref_Lalaji_Total.edit();

                if (pref_Lalaji_Total.contains("total")){
                    String tot = pref_Lalaji_Total.getString("total",null);
                    String am = t;
                    String res3 = String.valueOf(Float.parseFloat(tot) - Float.parseFloat(am));
                    double d = Double.parseDouble(res3);
                    long lon = Math.round(d);

                    try {
                        Sale.grandTotal_tv.setText("₹ "+lon);

                    }catch (NullPointerException e){

                    }

                    try {
                        Purchase.grandTotal_tv.setText("₹ "+lon);

                    }catch (NullPointerException e){

                    }


                    editor_Lalaji_Total.putString("total", res3);
                    editor_Lalaji_Total.commit();

                }else {



                }

                listitemSelectProducts.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();

                // String newValue = "I like sheep.";
               //int updateIndex = 3;

               // listitemSelectProducts.set(position, listitem_orderHistory);
              //  notifyItemChanged(position);


            }
        });

        final SharedPreferences pref_Lalaji_Product_Count = c.getSharedPreferences("Lalaji_Product_Count", 0); // 0 - for private mode
        final SharedPreferences.Editor editor_Lalaji_Product_Count = pref_Lalaji_Product_Count.edit();
        editor_Lalaji_Product_Count.clear();
        editor_Lalaji_Product_Count.commit();

        holder.item_plus_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(c,listitem.getModel_name(),Toast.LENGTH_LONG).show();

                String csp = pref_Lalaji_Product_Count.getString(String.valueOf(position),null);

                int cspint = 1;
                if (csp != null && !csp.isEmpty() && !csp.equals("null")) {
                     cspint = Integer.parseInt(csp);
                }

                int s = Integer.parseInt(listitem.getModel_stock());

            //    if (cspint>=s){
               //     Toast.makeText(c,"You have only "+listitem.getModel_stock()+" items in Stock",Toast.LENGTH_LONG).show();
            //    }else {

                    cou++;

                    String taxStr = taxable(listitem.getModel_mop_new(), listitem.getHsn_rate(), String.valueOf(cou));

                    String amountStr = amount(listitem.getModel_mop_new(), listitem.getHsn_rate(), String.valueOf(cou));

                    Listitem_Select_Product listitem_orderHistory = new Listitem_Select_Product(

                            listitem.getSelectid(),
                            listitem.getProduct_id(),
                            listitem.getLogin_id(),
                            listitem.getBrand_id(),
                            listitem.getCmp_id(),
                            listitem.getHsn_id(),
                            listitem.getUnit_id(),
                            listitem.getCast_id(),
                            listitem.getModel_name(),
                            listitem.getModel_image(),
                            listitem.getModel_specialization(),
                            listitem.getModel_mop(),
                            listitem.getModel_mrp(),
                            listitem.getModel_pp(),
                            listitem.getModel_pp_new(),
                            listitem.getModel_mop_new(),
                            listitem.getModel_stock(),
                            listitem.getBar_code(),
                            listitem.getBar_code_status(),
                            listitem.getModel_status(),
                            String.valueOf(cou),
                            listitem.getDiscunt(),
                            listitem.getBrand_name(),
                            listitem.getHsn_rate(),
                            listitem.getHsn_code(),
                            taxStr,
                            amountStr
                    );

                    listitemSelectProducts.set(position, listitem_orderHistory);
                    notifyItemChanged(position);

                    editor_Lalaji_Product_Count.putString(String.valueOf(position), String.valueOf(cou));
                    editor_Lalaji_Product_Count.commit();

                    SharedPreferences pref = c.getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();

                    int j = listitemSelectProducts.size();
                    String amt = "0";

                    for (int i=0;i < j;i++)
                    {
                        Listitem_Select_Product amount2 = listitemSelectProducts.get(i);

                        amt = String.valueOf(Double.parseDouble(amt)+Double.parseDouble(amount2.getAmount()));

                        double d = Double.parseDouble(amt);
                        long lon = Math.round(d);
                        String res = String.valueOf(lon);
                        try {
                            Sale.grandTotal_tv.setText("₹ "+res);
                        }catch (NullPointerException e){

                        }
                        try {
                            Purchase.grandTotal_tv.setText("₹ "+res);
                        }catch (NullPointerException e){

                        }

                        editor.putString("total", amt);
                        editor.commit();

                    }
            //    }
            }
        });

        holder.item_minus_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (cou<=1){
                    Toast.makeText(c,"Not Allowed",Toast.LENGTH_LONG).show();
                }else {

                   SharedPreferences pref_Lalaji_Total = c.getSharedPreferences("Lalaji_Total", 0); // 0 - for private mode
                   SharedPreferences.Editor editor_Lalaji_Total = pref_Lalaji_Total.edit();

                   String toa = pref_Lalaji_Total.getString("total", null);
                   String bp = listitem.getModel_mop_new();
                   String ta = listitem.getHsn_rate();

                   Double re = Double.parseDouble(bp)*Double.parseDouble(ta)/100;
                   Double er = Double.parseDouble(bp)+re;

                   String ss = String.valueOf(Double.parseDouble(toa)-er);

                   double d = Double.parseDouble(ss);
                   long lon = Math.round(d);

                   try {
                       Sale.grandTotal_tv.setText("₹ "+lon);
                   }catch (NullPointerException e){

                   }

                   try {
                       Purchase.grandTotal_tv.setText("₹ "+lon);
                   }catch (NullPointerException e){

                   }


                   editor_Lalaji_Total.putString("total", ss);
                   editor_Lalaji_Total.commit();


                   cou--;

                   String taxStr = taxable(listitem.getModel_mop_new(), listitem.getHsn_rate(), String.valueOf(cou));

                   String amountStr = amount(listitem.getModel_mop_new(), listitem.getHsn_rate(), String.valueOf(cou));

                    Listitem_Select_Product listitem_orderHistory = new Listitem_Select_Product(

                            listitem.getSelectid(),
                            listitem.getProduct_id(),
                            listitem.getLogin_id(),
                            listitem.getBrand_id(),
                            listitem.getCmp_id(),
                            listitem.getHsn_id(),
                            listitem.getUnit_id(),
                            listitem.getCast_id(),
                            listitem.getModel_name(),
                            listitem.getModel_image(),
                            listitem.getModel_specialization(),
                            listitem.getModel_mop(),
                            listitem.getModel_mrp(),
                            listitem.getModel_pp(),
                            listitem.getModel_pp_new(),
                            listitem.getModel_mop_new(),
                            listitem.getModel_stock(),
                            listitem.getBar_code(),
                            listitem.getBar_code_status(),
                            listitem.getModel_status(),
                            String.valueOf(cou),
                            listitem.getDiscunt(),
                            listitem.getBrand_name(),
                            listitem.getHsn_rate(),
                            listitem.getHsn_code(),
                            taxStr,
                            amountStr

                    );

                    listitemSelectProducts.set(position, listitem_orderHistory);
                    notifyItemChanged(position);

                   editor_Lalaji_Total.putString(String.valueOf(position), String.valueOf(cou));
                   editor_Lalaji_Total.commit();

               }
            }
        });


        holder.dis_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = listitem.getModel_mop_new();
                float bs = Math.round(Float.parseFloat(s));
                String etStr =  holder.dis_ET.getText().toString();
                float et = 0;
                if (etStr != null && !etStr.isEmpty() && !etStr.equals("null")) {
                    et = Math.round(Float.parseFloat(etStr));
                }

               // int et = Integer.parseInt(holder.dis_ET.getText().toString());

                if (et>bs){
                    Toast.makeText(c,"Discount should be equal to or less then base price.",Toast.LENGTH_LONG).show();
                }else {

                    String dis = discount(listitem.getModel_mop_new(),holder.dis_ET.getText().toString());
                    String taxStr = taxable(dis, listitem.getHsn_rate(), String.valueOf(cou));
                    String amountStr = amount(dis, listitem.getHsn_rate(), String.valueOf(cou));

                    Listitem_Select_Product listitem_orderHistory = new Listitem_Select_Product(

                            listitem.getSelectid(),
                            listitem.getProduct_id(),
                            listitem.getLogin_id(),
                            listitem.getBrand_id(),
                            listitem.getCmp_id(),
                            listitem.getHsn_id(),
                            listitem.getUnit_id(),
                            listitem.getCast_id(),
                            listitem.getModel_name(),
                            listitem.getModel_image(),
                            listitem.getModel_specialization(),
                            listitem.getModel_mop(),
                            listitem.getModel_mrp(),
                            listitem.getModel_pp(),
                            listitem.getModel_pp_new(),
                            dis,
                            listitem.getModel_stock(),
                            listitem.getBar_code(),
                            listitem.getBar_code_status(),
                            listitem.getModel_status(),
                            listitem.getCountItem(),
                            holder.dis_ET.getText().toString(),
                            listitem.getBrand_name(),
                            listitem.getHsn_rate(),
                            listitem.getHsn_code(),
                            taxStr,
                            amountStr

                    );

                    listitemSelectProducts.set(position, listitem_orderHistory);
                    notifyItemChanged(position);

                }

            }
        });

        holder.desc_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.desc_apply_btn.setClickable(false);
                holder.desc.setClickable(false);
                holder.desc.setFocusable(false);

                String dis = discount(listitem.getModel_mop_new(),holder.dis_ET.getText().toString());
                String taxStr = taxable(dis, listitem.getHsn_rate(), String.valueOf(cou));
                String amountStr = amount(dis, listitem.getHsn_rate(), String.valueOf(cou));

                Listitem_Select_Product listitem_orderHistory = new Listitem_Select_Product(

                        listitem.getSelectid(),
                        listitem.getProduct_id(),
                        listitem.getLogin_id(),
                        listitem.getBrand_id(),
                        listitem.getCmp_id(),
                        listitem.getHsn_id(),
                        listitem.getUnit_id(),
                        listitem.getCast_id(),
                        listitem.getModel_name(),
                        listitem.getModel_image(),
                        listitem.getModel_specialization(),
                        listitem.getModel_mop(),
                        listitem.getModel_mrp(),
                        listitem.getModel_pp(),
                        listitem.getModel_pp_new(),
                        dis,
                        listitem.getModel_stock(),
                        listitem.getBar_code(),
                        listitem.getBar_code_status(),
                        listitem.getModel_status(),
                        listitem.getCountItem(),
                        holder.dis_ET.getText().toString(),
                        listitem.getBrand_name(),
                        listitem.getHsn_rate(),
                        listitem.getHsn_code(),
                        taxStr,
                        amountStr

                );

                listitemSelectProducts.set(position, listitem_orderHistory);
                notifyItemChanged(position);

            }
        });



        int j = listitemSelectProducts.size();
        SharedPreferences prefd = c.getSharedPreferences("Lalaji_Product_JsonSata", 0); // 0 - for private mode
        SharedPreferences.Editor editord = prefd.edit();

        for (int i=0;i < j;i++)
        {
            Listitem_Select_Product product2 = listitemSelectProducts.get(i);
            editord.putString("product_array", String.valueOf(j));
            editord.putString("selectid"+i, product2.getSelectid());
            editord.putString("product_id"+i, product2.getProduct_id());
            editord.putString("login_id"+i, product2.getLogin_id());
            editord.putString("brand_id"+i, product2.getBrand_id());
            editord.putString("cmp_id"+i, product2.getCmp_id());
            editord.putString("hsn_id"+i, product2.getHsn_id());
            editord.putString("unit_id"+i, product2.getUnit_id());
            editord.putString("cast_id"+i, product2.getCast_id());
            editord.putString("model_name"+i, product2.getModel_name());
            editord.putString("model_image"+i, product2.getModel_image());
            editord.putString("model_specialization"+i, product2.getModel_specialization());
            editord.putString("model_mop"+i, product2.getModel_mop());
            editord.putString("model_mrp"+i, product2.getModel_mrp());
            editord.putString("model_pp"+i, product2.getModel_pp());
            editord.putString("model_pp_new"+i, product2.getModel_pp_new());
            editord.putString("model_mop_new"+i, product2.getModel_mop_new());
            editord.putString("model_stock"+i, product2.getModel_stock());
            editord.putString("bar_code"+i, product2.getBar_code());
            editord.putString("bar_code_status"+i, product2.getBar_code_status());
            editord.putString("model_status"+i, product2.getModel_status());
            editord.putString("countItem"+i, product2.getCountItem());
            editord.putString("discunt"+i, product2.getDiscunt());
            editord.putString("brand_name"+i, product2.getBrand_name());
            editord.putString("hsn_rate"+i, product2.getHsn_rate());
            editord.putString("hsn_code"+i, product2.getHsn_code());
            editord.putString("tax_amount"+i, product2.getTax_amount());
            editord.putString("amount"+i, product2.getAmount());
            editord.putString("desc"+i, holder.desc.getText().toString());
            editord.commit();

        }

    }

    @Override
    public int getItemCount() {
        return listitemSelectProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView productNameTV;
        TextView p_brandnameTV;
        TextView p_barcodeTV;
        TextView p_priceTV;
        EditText dis_ET;

        Button item_minus_Btn;
        Button item_plus_Btn;

        Button dis_apply_btn;

        TextView item_count;
        TextView item_count2;

        TextView gstTV;
        TextView taxTV;
        TextView amountTV;

        ImageView p_delete;

        LinearLayout disLL;

        EditText desc;
        Button desc_apply_btn;

        public ViewHolder(View itemView) {
            super(itemView);

            desc = (EditText)itemView.findViewById(R.id.item_desc);
            desc_apply_btn = (Button)itemView.findViewById(R.id.desc_apply_btn);

            productNameTV = (TextView)itemView.findViewById(R.id.productNameTV);
            p_brandnameTV = (TextView)itemView.findViewById(R.id.p_brandnameTV);
            p_barcodeTV = (TextView)itemView.findViewById(R.id.p_barcodeTV);
            p_priceTV = (TextView)itemView.findViewById(R.id.p_priceTV);
            dis_ET = (EditText) itemView.findViewById(R.id.dis_ET);

            dis_apply_btn = (Button)itemView.findViewById(R.id.dis_apply_btn);

            item_minus_Btn = (Button)itemView.findViewById(R.id.item_minus_Btn);
            item_plus_Btn = (Button)itemView.findViewById(R.id.item_plus_Btn);

            item_count = (TextView)itemView.findViewById(R.id.item_count);
            item_count2 = (TextView)itemView.findViewById(R.id.item_count2);

            gstTV = (TextView)itemView.findViewById(R.id.gstTV);
            taxTV = (TextView)itemView.findViewById(R.id.taxTV);
            amountTV = (TextView)itemView.findViewById(R.id.amountTV);

            p_delete = (ImageView)itemView.findViewById(R.id.p_delete);

            disLL = (LinearLayout)itemView.findViewById(R.id.disLL);

        }
    }


    private String discount(String bp, String diss){

        String number = bp;
        float result = 0;
        if (number != null && !number.isEmpty() && !number.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(number));
        }

        String number2 = diss;
        float result2 = 0;
        if (number2 != null && !number2.isEmpty() && !number2.equals("null")) {
            result2 = Math.round(Float.parseFloat(number2));
        }


        float re = result-result2;
        String res = String.valueOf(re);


        return res;
    }



    private String taxable(String bp, String tax, String count){

        String number = bp;
        float result = 0;
        if (number != null && !number.isEmpty() && !number.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(number));
        }

        String number2 = tax;
        float result2 = 0;
        if (number2 != null && !number2.isEmpty() && !number2.equals("null")) {
            result2 = Math.round(Float.parseFloat(number2));
        }

        String number3 = count;
        float result3 = 0;
        if (number3 != null && !number3.isEmpty() && !number3.equals("null")) {
            result3 = Math.round(Float.parseFloat(number3));
        }


        float re = result*result2/100;
        float re23 = re*result3;
        String res = String.valueOf(re23);


        return res;

    }


    private String amount(String bp, String tax, String count){

        String number = bp;
        float result = 0;
        if (number != null && !number.isEmpty() && !number.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(number));
        }

        String number2 = tax;
        float result2 = 0;
        if (number2 != null && !number2.isEmpty() && !number2.equals("null")) {
            result2 = Math.round(Float.parseFloat(number2));
        }

        String number3 = count;
        float result3 = 0;
        if (number3 != null && !number3.isEmpty() && !number3.equals("null")) {
            result3 = Math.round(Float.parseFloat(number3));
        }


        float totalbs = result*result3;
        float totaltaxx = totalbs*result2/100;

        float re23 = totalbs+totaltaxx;
        String res = String.valueOf(re23);

        return res;

    }

}
