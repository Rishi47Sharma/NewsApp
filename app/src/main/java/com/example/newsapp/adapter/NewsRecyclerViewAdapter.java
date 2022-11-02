package com.example.newsapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.model.News;
import com.example.newsapp.util.NewsItemClicked;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {
    private  ArrayList<News> items = new ArrayList<News>();
    private final NewsItemClicked newsItemClicked;

    public NewsRecyclerViewAdapter(NewsItemClicked newsItemClicked) {

        this.newsItemClicked=newsItemClicked;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolder holder, int position) {
    News currentItem= items.get(position);
    holder.titleView.setText(currentItem.title);
    holder.authorView.setText(currentItem.author);
    Glide.with(holder.itemView.getContext()).load(currentItem.urlImage).into(holder.image);

    }

    @Override
    public int getItemCount() {

        return items.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void update(ArrayList<News> updatedNews){
        items.clear();
        items.addAll(updatedNews);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public AppCompatTextView titleView,authorView;
        public ImageView image;

        NewsItemClicked OnNewsItemClicked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView= itemView.findViewById(R.id.title);
            authorView=itemView.findViewById(R.id.author);
            image =itemView.findViewById(R.id.image);

            this.OnNewsItemClicked=newsItemClicked;
            titleView.setOnClickListener(this);
            image.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            News CurrentItems = items.get(getAdapterPosition());
            if(id==R.id.title || id==R.id.image  ){
                OnNewsItemClicked.onItemsClicked(CurrentItems);
            }



        }
    }
}
