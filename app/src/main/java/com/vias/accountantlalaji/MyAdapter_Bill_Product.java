package com.vias.accountantlalaji;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter_Bill_Product extends RecyclerView.Adapter<MyAdapter_Bill_Product.ViewHolder> {

    int cou = 1;
    private List<Listitem_Select_Product> listitemSelectProducts;
    private Context c;
    Listitem_Select_Product listitem;


    public MyAdapter_Bill_Product(List<Listitem_Select_Product> listitem_select_products, Context cc) {
        this.listitemSelectProducts = listitem_select_products;
        this.c = cc;
    }


    @Override
    public MyAdapter_Bill_Product.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_bill_product, parent, false);
        return new MyAdapter_Bill_Product.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Bill_Product.ViewHolder holder, final int position) {


        listitem = listitemSelectProducts.get(position);


        holder.productNameTV.setText(listitem.getModel_name());
        holder.p_brandnameTV.setText(listitem.getBrand_name());
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

        public ViewHolder(View itemView) {
            super(itemView);

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


}
