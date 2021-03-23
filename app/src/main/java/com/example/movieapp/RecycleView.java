package com.example.movieapp;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecycleView extends RecyclerView.Adapter<RecycleView.ViewHolderData> implements View.OnClickListener {

    private ArrayList<ItemList> items;
    private ArrayList <ItemList> originalItems;

    public RecycleView(ArrayList<ItemList> items,Context mContext) {
        this.items = items;
        this.context=mContext;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    public static Context context;
    private View.OnClickListener listener;



    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view,null,false);
        //context = getActivity();
        view.setOnClickListener(this);
        return new ViewHolderData(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        //holder.tit.setText(titulo.get(position));
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+items.get(position).getImg()).into(holder.pic);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;

    }
    public void filter(String s){
        if (s.length() == 0) {
            items.clear();
            items.addAll(originalItems);
        }
        else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            items.clear();
            List<ItemList> collect = originalItems.stream()
                    .filter(i -> i.getTit().toLowerCase().contains(s))
                    .collect(Collectors.toList());

            items.addAll(collect);
        }
        else {
            items.clear();
            for (ItemList i : originalItems) {
                if (i.getTit().toLowerCase().contains(s)) {
                    items.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {
        TextView tit;
        ImageView pic;
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
           // tit=(TextView) itemView.findViewById(R.id.titulo);
            pic = (ImageView) itemView.findViewById(R.id.foto);

        }
    }
}
