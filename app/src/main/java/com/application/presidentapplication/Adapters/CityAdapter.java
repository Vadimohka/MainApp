package com.application.presidentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.application.presidentapplication.JSONClass.City;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CityAdapter extends ArrayAdapter<City> implements Filterable {

    ArrayList<City> arrayList;
    ArrayList<City> mOriginalValues;

    public CityAdapter(Context context, ArrayList<City> arrayList) {
        super(context, android.R.layout.simple_list_item_2, arrayList);
        this.arrayList = arrayList;

        Collections.sort(this.arrayList, new Comparator<City>() {
            @Override
            public int compare(City lhs, City rhs) {
                return lhs.cityName.compareTo(rhs.cityName);
            }
        });
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public City getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position);
        String text2;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, null);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(city.cityName);
        if(!city.cityCategory.equals(""))
            text2 = city.categoryName + " "  + city.cityCategory + " cельсовет";
        else
        {
            text2 = city.categoryName;
        }
        ((TextView) convertView.findViewById(android.R.id.text2))
                .setText(text2);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                arrayList = (ArrayList<City>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<City> FilteredArrList = new ArrayList<City>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<City>(arrayList); // saves the original data in mOriginalValues
                }

                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        City data = mOriginalValues.get(i);
                        if (data.cityName.toLowerCase().contains(constraint.toString())) {
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