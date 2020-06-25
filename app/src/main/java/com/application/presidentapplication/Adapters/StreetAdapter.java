package com.application.presidentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.application.presidentapplication.JSONClass.Street;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StreetAdapter extends ArrayAdapter<Street> implements Filterable {

    ArrayList<Street> arrayList;
    ArrayList<Street> mOriginalValues;

    public StreetAdapter(Context context, ArrayList<Street> arrayList) {
        super(context, android.R.layout.simple_list_item_2, arrayList);
        this.arrayList = arrayList;

        Collections.sort(this.arrayList, new Comparator<Street>() {
            @Override
            public int compare(Street lhs, Street rhs) {
                return lhs.streetName.compareTo(rhs.streetName);
            }
        });
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Street getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Street street = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, null);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(street.streetName);
        
        ((TextView) convertView.findViewById(android.R.id.text2))
                .setText(street.streetType);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                arrayList = (ArrayList<Street>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Street> FilteredArrList = new ArrayList<Street>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Street>(arrayList); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        Street data = mOriginalValues.get(i);
                        if (data.streetName.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(data);
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
    }
}