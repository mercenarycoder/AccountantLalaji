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

public class MyAdapter_Items extends RecyclerView.Adapter<MyAdapter_Items.ViewHolder> {

    private List<Listitem_items> listitem_items;
    private Context context;


    public MyAdapter_Items(List<Listitem_items> listitem_items, Context context) {
        this.listitem_items = listitem_items;
        this.context = context;
    }


    @Override
    public MyAdapter_Items.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_items, parent, false);
        return new MyAdapter_Items.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Items.ViewHolder holder, final int position) {


        final Listitem_items listitem = listitem_items.get(position);

        holder.itemName.setText(listitem.getModel_name());
        holder.itembName.setText(listitem.getBrand_name());
        holder.itemMOP.setText(listitem.getModel_mop());
        holder.itemStock.setText(listitem.getModel_stock());

        holder.cardview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Product_View.class);
                i.putExtra("idd", listitem.getIdd());
                i.putExtra("login_idd", listitem.getIdd());
                i.putExtra("brand_id", listitem.getBrand_id());
                i.putExtra("cmp_idd", listitem.getCmp_idd());
                i.putExtra("hsn_id", listitem.getHsn_id());
                i.putExtra("unit_id", listitem.getUnit_id());
                i.putExtra("cast_id", listitem.getCast_id());
                i.putExtra("model_name", listitem.getModel_name());
                i.putExtra("model_image", listitem.getModel_image());
                i.putExtra("model_specialization", listitem.getModel_specialization());
                i.putExtra("model_mop", listitem.getModel_mop());
                i.putExtra("model_mrp", listitem.getModel_mrp());
                i.putExtra("model_pp", listitem.getModel_pp());
                i.putExtra("model_pp_new", listitem.getModel_pp_new());
                i.putExtra("model_mop_new", listitem.getModel_mop_new());
                i.putExtra("model_stock", listitem.getModel_stock());
                i.putExtra("bar_code", listitem.getBar_code());
                i.putExtra("model_status", listitem.getModel_status());
                i.putExtra("brand_name", listitem.getBrand_name());
                i.putExtra("hsn_rate", listitem.getHsn_rate());
                i.putExtra("hsn_code", listitem.getHsn_code());


                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitem_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itembName;
        TextView itemMOP;
        TextView itemStock;

        CardView cardview_item;


        public ViewHolder(View itemView) {
            super(itemView);

            itemName = (TextView)itemView.findViewById(R.id.item_Name);
            itembName = (TextView)itemView.findViewById(R.id.item_Brand_Name);
            itemMOP = (TextView)itemView.findViewById(R.id.item_MOP);
            itemStock = (TextView)itemView.findViewById(R.id.item_Stock);

            cardview_item = (CardView)itemView.findViewById(R.id.cardview_item);

        }
    }


}
