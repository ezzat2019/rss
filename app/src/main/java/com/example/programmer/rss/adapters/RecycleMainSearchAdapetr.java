package com.example.programmer.rss.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.programmer.rss.R;
import com.example.programmer.rss.models.Results;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.ArrayList;
import java.util.List;

public class RecycleMainSearchAdapetr extends RecyclerView.Adapter<RecycleMainSearchAdapetr.VH> implements Filterable {
    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    private static List<Results> alll;
    private OnItemClickMain main;
    private List<Results> list;
    private List<Results> all;
    private Filter filter = new Filter() {
        @Override
        public FilterResults performFiltering(CharSequence charSequence) {
            String s = charSequence.toString().toLowerCase().trim();
            for (Results modelMain : all) {
                if (modelMain.getTitle().toLowerCase().contains(s)) {
                    Log.d("rrrrrrrrr", modelMain.getTitle() + "");
                    alll.add(modelMain);

                }
            }

            FilterResults results = new FilterResults();
            results.values = alll;


            return results;
        }

        @Override
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (list != null)
                list.clear();

            Log.d("eeeee2", filterResults.values + "");
            if (filterResults.values != null)
                list.addAll((List) filterResults.values);


            notifyDataSetChanged();
            if (alll != null)
                alll.clear();
        }
    };


    public RecycleMainSearchAdapetr(OnItemClickMain main) {
        this.main = main;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodes, parent, false);
        return new VH(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(list.get(position));


    }

    public List<Results> getList() {
        return list;
    }

    public void setList(List<Results> list) {
        this.list = list;
        all = new ArrayList(list);
        alll = new ArrayList<>();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    public class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Context context;
        private TextView txt;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_episodes);
            txt = itemView.findViewById(R.id.txt_name_vedio);


            this.context = context;
            if (itemView != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        main.onClick(getAdapterPosition());
                    }
                });
            }

        }

        void bind(Results modelMain) {
            Glide.with(context.getApplicationContext()).load(BASE_URL_IMAGE.concat(modelMain.getPoster_path()))
                    .into(imageView);
            txt.setText(modelMain.getTitle());

        }
    }
}
