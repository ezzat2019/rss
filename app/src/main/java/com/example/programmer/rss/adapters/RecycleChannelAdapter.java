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
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.List;

public class RecycleChannelAdapter extends RecyclerView.Adapter<RecycleChannelAdapter.VH> {
    protected static OnItemClickMain main;
    private List<ModelMain> list;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rec_rectangle, parent, false);
        return new VH(v, parent.getContext());
    }

    public void setOnItemClick(OnItemClickMain main) {
        RecycleChannelAdapter.main = main;
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
    }

    void addItem(ModelMain modelMain) {
        list.add(modelMain);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Context context;


        public VH(@NonNull View itemView, final Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_frag_all);
            this.context = context;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    main.onClick(getPosition());
                }
            });
        }

        void bind(ModelMain modelMain) {
            Glide.with(context.getApplicationContext()).load(modelMain.getSource())
                    .into(imageView);

        }
    }
}
