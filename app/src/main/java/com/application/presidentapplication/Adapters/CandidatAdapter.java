package com.application.presidentapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.presidentapplication.Models.Candidat;
import com.application.presidentapplication.R;

import java.util.ArrayList;


public class CandidatAdapter extends BaseAdapter {

    ArrayList<Candidat> candidatList;
    Context context;

    public CandidatAdapter(Context context, ArrayList<Candidat> candidats){
        candidatList = candidats;
        this.context = context;
    }

    @Override
    public int getCount() {
        return candidatList.size();
    }

    @Override
    public Candidat getItem(int position) {
        return candidatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        final Candidat candidat = getItem(position);
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            customView = inflater.inflate(R.layout.candidat_item, null);

            holder.imageView = customView.findViewById(R.id.image_candidat);
            holder.textView1 = customView.findViewById(R.id.text_candidat_name);
            holder.textView2 = customView.findViewById(R.id.text_candidat_io);
            customView.setTag(holder);
        }
        else{
            holder = (ViewHolder) customView.getTag();
        }

        holder.imageView.setImageResource(candidat.photo);
        holder.textView1.setText(candidat.Name);
        holder.textView2.setText(candidat.IO);

        return customView;
    }

    public static class ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
    }

}
