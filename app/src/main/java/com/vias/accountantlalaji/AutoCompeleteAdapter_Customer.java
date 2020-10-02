package com.vias.accountantlalaji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AutoCompeleteAdapter_Customer extends ArrayAdapter<CustomerList> {
    private List<CustomerList> customerLists;

    public AutoCompeleteAdapter_Customer(@NonNull Context context, @NonNull List<CustomerList> countryList) {
        super(context, 0, countryList);
        customerLists = new ArrayList<>(countryList);

        Log.e("CLists", String.valueOf(customerLists));
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autosompletetextviewcustomlayout, parent, false
            );
        }


        LinearLayout autoComLL = convertView.findViewById(R.id.autoCompeleteLL);
        TextView textViewName = convertView.findViewById(R.id.cusName);
        TextView textViewMob = convertView.findViewById(R.id.cusMob);

        final CustomerList countryItem = getItem(position);

        if (countryItem != null) {

            textViewName.setText(countryItem.getCustomerName());
            textViewMob.setText("( "+countryItem.getCustomerMob()+" )");
            //imageViewFlag.setImageResource(countryItem.getFlagImage());


        }

        return convertView;
    }




    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<CustomerList> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                suggestions.addAll(customerLists);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CustomerList item : customerLists) {
                    if (item.getCustomerName().toLowerCase().contains(filterPattern) || item.getCustomerMob().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }
        @Override
        public CharSequence convertResultToString(Object resultValue) {

            return ((CustomerList) resultValue).getCustomerName();
        }
    };
}
