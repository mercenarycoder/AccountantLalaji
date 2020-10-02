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

public class MyAdapter_Brand extends RecyclerView.Adapter<MyAdapter_Brand.ViewHolder> {

    private List<Listitem_brand> listitem_brands;
    private Context context;


    public MyAdapter_Brand(List<Listitem_brand> listitem_brands, Context context) {
        this.listitem_brands = listitem_brands;
        this.context = context;
    }


    @Override
    public MyAdapter_Brand.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_brand, parent, false);
        return new MyAdapter_Brand.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Brand.ViewHolder holder, final int position) {


        final Listitem_brand listitem = listitem_brands.get(position);

        holder.brandName.setText(listitem.getBrand_name());
        holder.brandStatus.setText(listitem.getStatus());

        holder.cardViewBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Brand_view.class);
                i.putExtra("id",listitem.getIdd());
                i.putExtra("brandname",listitem.getBrand_name());
                i.putExtra("brandstatus",listitem.getStatus());
                i.putExtra("date",listitem.getCreated_at());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitem_brands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView brandName;
        TextView brandStatus;

        CardView cardViewBrand;



        public ViewHolder(View itemView) {
            super(itemView);

            brandName = (TextView)itemView.findViewById(R.id.brandName);
            brandStatus = (TextView)itemView.findViewById(R.id.brandStatus);

            cardViewBrand = (CardView)itemView.findViewById(R.id.cardViewBrand);

        }
    }


}
