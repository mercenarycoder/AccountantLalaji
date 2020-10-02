package com.vias.accountantlalaji;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter_Customer extends RecyclerView.Adapter<MyAdapter_Customer.ViewHolder> {

    private List<CustomerList> customerLists;
    private Context context;


    public MyAdapter_Customer(List<CustomerList> customerLists, Context context) {
        this.customerLists = customerLists;
        this.context = context;
    }


    @Override
    public MyAdapter_Customer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_customer_list, parent, false);
        return new MyAdapter_Customer.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAdapter_Customer.ViewHolder holder, final int position) {


        final CustomerList listitem = customerLists.get(position);

        holder.cusName.setText(listitem.getCustomerName());
        holder.cusMob.setText(listitem.getCustomerMob());
        holder.cusType.setText(listitem.getCustomerType());

        holder.cv_customerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Customer_View.class);
                i.putExtra("id",listitem.getCustomerId());
                i.putExtra("customer_name",listitem.getCustomerName());
                i.putExtra("customer_mobile",listitem.getCustomerMob());
                i.putExtra("customer_type",listitem.getCustomerType());
                i.putExtra("login_id",listitem.getLogin_id());
                i.putExtra("cmp_id",listitem.getCmp_id());
                i.putExtra("dob",listitem.getDob());
                i.putExtra("email",listitem.getEmail());
                i.putExtra("billing_address",listitem.getBilling_address());
                i.putExtra("shipping_address",listitem.getShipping_address());
                i.putExtra("state_id",listitem.getState_id());
                i.putExtra("pincode",listitem.getPincode());
                i.putExtra("customer_gst",listitem.getCustomer_gst());
                i.putExtra("status",listitem.getStatus());
                i.putExtra("buss_name",listitem.getBuss_name());
                i.putExtra("opening_account",listitem.getOpening_account());
                i.putExtra("account_name",listitem.getAccount_name());
                i.putExtra("account_number",listitem.getAccount_number());
                i.putExtra("bank_ifsc_code",listitem.getBank_ifsc_code());
                i.putExtra("consumer_no",listitem.getConsumer_no());
                i.putExtra("created_at",listitem.getCreated_at());
                i.putExtra("state_name",listitem.getState_name());

                context.startActivity(i);
            }
        });

/*        jo2.getString("id"),
                jo2.getString("customer_name"),
                jo2.getString("customer_mobile"),
                jo2.getString("customer_type"),
                jo2.getString("login_id"),
                jo2.getString("cmp_id"),
                jo2.getString("dob"),
                jo2.getString("email"),
                jo2.getString("billing_address"),
                jo2.getString("shipping_address"),
                jo2.getString("state_id"),
                jo2.getString("pincode"),
                jo2.getString("customer_gst"),
                jo2.getString("status"),
                jo2.getString("buss_name"),
                jo2.getString("opening_account"),
                jo2.getString("account_name"),
                jo2.getString("account_number"),
                jo2.getString("bank_ifsc_code"),
                jo2.getString("consumer_no"),
                jo2.getString("created_at")*/

    }

    @Override
    public int getItemCount() {
        return customerLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cusName;
        TextView cusMob;
        TextView cusType;

        CardView cv_customerView;


        public ViewHolder(View itemView) {
            super(itemView);

            cusName = (TextView)itemView.findViewById(R.id.cus_Name);
            cusMob = (TextView)itemView.findViewById(R.id.cus_Mob);
            cusType = (TextView)itemView.findViewById(R.id.cus_Type);

            cv_customerView = (CardView)itemView.findViewById(R.id.cv_customerView);


        }
    }


}