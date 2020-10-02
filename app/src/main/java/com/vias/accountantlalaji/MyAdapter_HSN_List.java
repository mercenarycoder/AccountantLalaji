package com.vias.accountantlalaji;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter_HSN_List extends RecyclerView.Adapter<MyAdapter_HSN_List.ViewHolder> {

    private List<Listitem_HSN> listitem_hsns;
    private Context context;


    public MyAdapter_HSN_List(List<Listitem_HSN> listitem_hsns, Context context) {
        this.listitem_hsns = listitem_hsns;
        this.context = context;
    }


    @Override
    public MyAdapter_HSN_List.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_hsn, parent, false);
        return new MyAdapter_HSN_List.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_HSN_List.ViewHolder holder, final int position) {


        final Listitem_HSN listitem = listitem_hsns.get(position);


        holder.hsnCode.setText(listitem.getHsn_code());
        holder.hsnRate.setText(listitem.getHsn_rate());

        holder.cardview_hsn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HSN_view.class);
                i.putExtra("id",listitem.getIdd());
                i.putExtra("code",listitem.getHsn_code());
                i.putExtra("desc",listitem.getHsn_description());
                i.putExtra("rate",listitem.getHsn_rate());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitem_hsns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hsnCode;
        TextView hsnRate;

        CardView cardview_hsn;



        public ViewHolder(View itemView) {
            super(itemView);

            hsnCode = (TextView)itemView.findViewById(R.id.HSN_Code);
            hsnRate = (TextView)itemView.findViewById(R.id.HSN_Rate);

            cardview_hsn = (CardView)itemView.findViewById(R.id.cardview_hsn);


        }
    }


}
