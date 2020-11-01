package com.example.programmer.rss.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.programmer.rss.R;
import com.example.programmer.rss.models.video.Results;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.List;

public class RecycleVideoAdapter extends RecyclerView.Adapter<RecycleVideoAdapter.VH> {

    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";


    // var
    private Context context;
    private OnItemClickMain onItemClick;
    private List<Results> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodes, parent, false);
        return new VH(v, parent.getContext());
    }

    public void setOnItemClick(OnItemClickMain main) {
        this.onItemClick = main;
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
    }

    void addItem(Results modelMain) {
        list.add(modelMain);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txt_name;
        private Context context;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_episodes);

            txt_name = itemView.findViewById(R.id.txt_name_vedio);
            this.context = context;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(getAdapterPosition());
                }
            });
        }


        void bind(Results modelMain) {

            txt_name.setText(modelMain.getName());
            Glide.with(context.getApplicationContext()).load(R.drawable.youtube)
                    .into(imageView);


        }
    }
}
