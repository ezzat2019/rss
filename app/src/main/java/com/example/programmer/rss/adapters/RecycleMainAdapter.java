package com.example.programmer.rss.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.programmer.rss.R;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleMainAdapter extends RecyclerView.Adapter<RecycleMainAdapter.VH> implements Filterable {
    protected static OnItemClickMain main;
    private static List<ModelMain> alll;
    private List<ModelMain> list;
    private List<ModelMain> all;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String s = charSequence.toString().trim();
            for (ModelMain modelMain : all) {
                if (String.valueOf(modelMain.getSource()).contains(s)) {
                    alll.add(modelMain);
                }
            }

            FilterResults results = new FilterResults();
            results.values = alll;
            Log.d("eeee55", alll.size() + "");

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();

            Log.d("eeeee2", filterResults.values + "");
            if (filterResults.values != null)
                list.addAll((List) filterResults.values);


            notifyDataSetChanged();
            alll.clear();
        }
    };

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_main, parent, false);
        return new VH(v, parent.getContext());
    }

    public void setOnItemClick(OnItemClickMain main) {
        RecycleMainAdapter.main = main;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(list.get(position));


    }

    public List<ModelMain> getList() {
        return list;
    }

    public void setList(List<ModelMain> list) {
        this.list = list;
        all = new ArrayList(list);
        alll = new ArrayList<>();

    }

    void addItem(ModelMain modelMain) {
        list.add(modelMain);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public static class VH extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private Context context;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.main_image);
            this.context = context;
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.onClick(getPosition());
                }
            });
        }

        void bind(ModelMain modelMain) {
            Glide.with(context.getApplicationContext()).load(modelMain.getSource())
                    .into(circleImageView);

        }
    }
}
