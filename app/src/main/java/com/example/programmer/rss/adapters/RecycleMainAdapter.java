package com.example.programmer.rss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.programmer.rss.R;
import com.example.programmer.rss.models.ModelMain;
import com.example.programmer.rss.models.Results;
import com.example.programmer.rss.ui.OnItemClickMain;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecycleMainAdapter extends RecyclerView.Adapter<RecycleMainAdapter.VH> {
    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    private OnItemClickMain main;
    private List<Results> list;

    public RecycleMainAdapter(OnItemClickMain main) {
        this.main = main;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_rectangle, parent, false);
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
        List<ModelMain> all = new ArrayList(list);
        List<ModelMain> alll = new ArrayList<>();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Context context;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_frag_all);
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
            if (modelMain==null)
            {
                Picasso.with(context).load(R.drawable.profile).into(imageView);
            }
            else
            {
                Glide.with(context.getApplicationContext()).load(BASE_URL_IMAGE.concat(modelMain.getPoster_path()))
                        .into(imageView);
            }


        }
    }
}
