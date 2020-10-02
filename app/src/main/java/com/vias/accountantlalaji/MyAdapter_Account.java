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

public class MyAdapter_Account extends RecyclerView.Adapter<MyAdapter_Account.ViewHolder> {

    private List<Listitem_Account> listitem_accounts;
    private Context context;


    public MyAdapter_Account(List<Listitem_Account> listitem_accounts, Context context) {
        this.listitem_accounts = listitem_accounts;
        this.context = context;
    }


    @Override
    public MyAdapter_Account.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_account, parent, false);
        return new MyAdapter_Account.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Account.ViewHolder holder, final int position) {

        final Listitem_Account listitem = listitem_accounts.get(position);

        holder.accountName.setText(listitem.getAccount_name());
        holder.accountType.setText(listitem.getBankName());

        holder.cardViewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Account_view.class);
                i.putExtra("id",listitem.getIdd());
                i.putExtra("an",listitem.getAccount_name());
                i.putExtra("at",listitem.getBankName());
                i.putExtra("oa",listitem.getOpening_account());
                i.putExtra("status",listitem.getStatus());
                i.putExtra("date",listitem.getCreated_at());

                context.startActivity(i);
            }
        });

        
    }

    @Override
    public int getItemCount() {
        return listitem_accounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView accountName;
        TextView accountType;

        CardView cardViewAccount;

        public ViewHolder(View itemView) {
            super(itemView);

            accountName = (TextView)itemView.findViewById(R.id.accountName);
            accountType = (TextView)itemView.findViewById(R.id.accountType);

            cardViewAccount = (CardView)itemView.findViewById(R.id.cardViewAccount);

        }
    }


}
