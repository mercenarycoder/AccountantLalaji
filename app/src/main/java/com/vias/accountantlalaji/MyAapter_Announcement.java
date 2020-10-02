package com.vias.accountantlalaji;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAapter_Announcement extends RecyclerView.Adapter<MyAapter_Announcement.ViewHolder> {

    private List<Listitem_announcement> listitem_announcements;
    private Context context;


    public MyAapter_Announcement(List<Listitem_announcement> listitem_announcements, Context context) {
        this.listitem_announcements = listitem_announcements;
        this.context = context;
    }


    @Override
    public MyAapter_Announcement.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_accouncement, parent, false);
        return new MyAapter_Announcement.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final MyAapter_Announcement.ViewHolder holder, final int position) {


        Listitem_announcement listitem = listitem_announcements.get(position);


        holder.date.setText(listitem.getCreated_at());
        holder.msg.setText(Html.fromHtml(listitem.getMessages()));

    }

    @Override
    public int getItemCount() {
        return listitem_announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView msg;



        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView)itemView.findViewById(R.id.ann_date);
            msg = (TextView)itemView.findViewById(R.id.ann_msg);

        }
    }


}
