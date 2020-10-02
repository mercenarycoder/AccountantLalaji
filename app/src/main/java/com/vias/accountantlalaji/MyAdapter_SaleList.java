package com.vias.accountantlalaji;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapter_SaleList extends RecyclerView.Adapter<MyAdapter_SaleList.ViewHolder> {

    private List<SaleList_Listitem> saleList_listitems;
    private Context context;



    public MyAdapter_SaleList(List<SaleList_Listitem> saleList_listitems, Context context) {
        this.saleList_listitems = saleList_listitems;
        this.context = context;
    }


    @Override
    public MyAdapter_SaleList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_sale_list, parent, false);
        return new MyAdapter_SaleList.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_SaleList.ViewHolder holder, final int position) {

        final SaleList_Listitem listitem = saleList_listitems.get(position);

        holder.sale_cusName.setText(listitem.getCustomer_name());
        holder.sale_Date.setText(listitem.getBill_date());
        holder.sale_BillNo.setText(listitem.getBill_no());
        holder.sale_CusMobile.setText(listitem.getCustomer_mobile());
        holder.sale_TotalAmount.setText("₹ " + listitem.getGrand_total());

        String gt = listitem.getGrand_total();
        String paid = listitem.getTotal_paid();

        float result = 0;
        if (gt != null && !gt.isEmpty() && !gt.equals("null")) {
            // result = Integer.parseInt(result);
            result = Math.round(Float.parseFloat(gt));
        }
        float result2 = 0;
        if (paid != null && !paid.isEmpty() && !paid.equals("null")) {
            // result = Integer.parseInt(result);
            result2 = Math.round(Float.parseFloat(paid));
        }

        if (result2 == 0 && result2 == 0.0 && gt != null && !gt.isEmpty() && !gt.equals("null")) {

            holder.sale_Status.setText("Unpaid");
            holder.sale_Status.setTextColor(Color.parseColor("#E74C3C"));
        } else if (result > result2) {

            holder.sale_Status.setText("Due");
            holder.sale_Status.setTextColor(Color.parseColor("#F39C12"));
        } else if (result2 == result) {

            holder.sale_Status.setText("Paid");
            holder.sale_Status.setTextColor(Color.parseColor("#009C41"));
        }


        holder.cardview_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BillView.class);
                i.putExtra("status", "sp");
                i.putExtra("billno", listitem.getBill_no());
                i.putExtra("invoice_id", listitem.getIdd());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return saleList_listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sale_Sno;
        TextView sale_Date;
        TextView sale_BillNo;
        TextView sale_cusName;
        TextView sale_CusMobile;
        TextView sale_TotalAmount;
        TextView sale_Status;

        ImageView sale_View;
        ImageView sale_Delete;

        CardView cardview_sale;


        public ViewHolder(View itemView) {
            super(itemView);

            sale_Sno = (TextView) itemView.findViewById(R.id.sale_Sno);
            sale_Date = (TextView) itemView.findViewById(R.id.sale_Date);
            sale_BillNo = (TextView) itemView.findViewById(R.id.sale_BillNo);
            sale_cusName = (TextView) itemView.findViewById(R.id.sale_cusName);
            sale_CusMobile = (TextView) itemView.findViewById(R.id.sale_CusMobile);
            sale_TotalAmount = (TextView) itemView.findViewById(R.id.sale_TotalAmount);
            sale_Status = (TextView) itemView.findViewById(R.id.sale_Status);

            cardview_sale = (CardView)itemView.findViewById(R.id.cardview_sale);

            // sale_View = (ImageView)itemView.findViewById(R.id.sale_View);
            // sale_Delete = (ImageView)itemView.findViewById(R.id.sale_Delete);

        }
    }


}