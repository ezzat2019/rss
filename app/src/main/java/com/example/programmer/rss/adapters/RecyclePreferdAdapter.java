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
import com.example.programmer.rss.models.ItemEmail;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.List;

public class RecyclePreferdAdapter extends RecyclerView.Adapter<RecyclePreferdAdapter.VH> {
    protected static OnItemClickMain main;
    private List<ItemEmail> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_episodes, parent, false);
        return new VH(v, parent.getContext());
    }

    public void setOnItemClick(OnItemClickMain main) {
        RecyclePreferdAdapter.main = main;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(list.get(position));


    }

    public List<ItemEmail> getList() {
        return list;
    }

    public void setList(List<ItemEmail> list) {
        this.list = list;

    }


    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;

        private Context context;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_episodes);


            this.context = context;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.onClick(getPosition());
                }
            });
        }

        void bind(ItemEmail modelMain) {
            Glide.with(context.getApplicationContext()).load(modelMain.getUrl())
                    .into(imageView);


        }
    }
}
