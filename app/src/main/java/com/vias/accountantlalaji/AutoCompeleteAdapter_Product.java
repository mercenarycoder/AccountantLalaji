package com.vias.accountantlalaji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AutoCompeleteAdapter_Product extends ArrayAdapter<ProductList> {
    private List<ProductList> productLists;

    public AutoCompeleteAdapter_Product(@NonNull Context context, @NonNull List<ProductList> productList) {
        super(context, 0, productList);
        productLists = new ArrayList<>(productList);

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
                    R.layout.autocompeletetv_products, parent, false
            );
        }


        TextView textViewName = convertView.findViewById(R.id.productName);
        TextView textViewP = convertView.findViewById(R.id.productPrice);

        final ProductList productList = getItem(position);

        if (productList != null) {

            textViewName.setText(productList.getModel_name());
            textViewP.setText("( "+productList.getBar_code()+" )");
            //imageViewFlag.setImageResource(countryItem.getFlagImage());


        }

        return convertView;
    }




    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProductList> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length()==0){
                suggestions.addAll(productLists);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ProductList item : productLists) {
                    if (item.getModel_name().toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                    if (item.getBar_code().toLowerCase().contains(filterPattern)) {
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

            return ((ProductList) resultValue).getModel_name();
        }
    };
}
