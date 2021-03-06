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
import com.example.programmer.rss.models.ItemRoom;
import com.example.programmer.rss.ui.OnItemClickMain;

import java.util.List;

public class RecyclepreferdAdapeter extends RecyclerView.Adapter<RecyclepreferdAdapeter.VH> {
    public final static String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    private OnItemClickMain main;

    private List<ItemRoom> list;


    public RecyclepreferdAdapeter(OnItemClickMain main) {
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

    public List<ItemRoom> getList() {
        return list;
    }

    public void setList(List<ItemRoom> list) {
        this.list = list;
        notifyDataSetChanged();


    }


    @Override
    public int getItemCount() {
        return list.size();
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

        void bind(ItemRoom modelMain) {
            Glide.with(context.getApplicationContext()).load(BASE_URL_IMAGE.concat(modelMain.getSource()))
                    .into(imageView);
            txt.setText(modelMain.getName());

        }
    }
}
