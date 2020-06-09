//package com.application.presidentapplication.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.application.presidentapplication.Models.FilterItem;
//import com.application.presidentapplication.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ExampleViewHolder> implements Filterable {
//    private List<FilterItem> exampleList;
//    private List<FilterItem> exampleListFull;
//    class ExampleViewHolder extends RecyclerView.ViewHolder {
//        TextView Name;
//        ExampleViewHolder(View itemView) {
//            super(itemView);
//            Name = itemView.findViewById(R.id.text_view_name);
//        }
//    }
//    FilterAdapter(List<FilterItem> exampleList) {
//        this.exampleList = exampleList;
//        exampleListFull = new ArrayList<>(exampleList);
//    }
//    @NonNull
//    @Override
//    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_item, parent, false);
//        return new ExampleViewHolder(v);
//    }
//    @Override
//    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
//        FilterItem currentItem = exampleList.get(position);
//        holder.Name.setText(currentItem.getName());
//    }
//    @Override
//    public int getItemCount() {
//        return exampleList.size();
//    }
//    @Override
//    public Filter getFilter() {
//        return exampleFilter;
//    }
//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<FilterItem> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(exampleListFull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (FilterItem item : exampleListFull) {
//                    if (item.getName().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            exampleList.clear();
//            exampleList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };
//}