package com.project.newsapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.databinding.SearchNewsItemBinding;
import com.project.newsapp.databinding.SwipeNewsCardBinding;
import com.project.newsapp.model.Article;
import com.project.newsapp.ui.search.SearchNewsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//CardStackView is implemented based on RecyclerView
public class CardSwipeAdapter extends RecyclerView.Adapter<CardSwipeAdapter.CardSwipeViewHolder>{

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newList) {
        articles.clear();
        articles.addAll(newList);

        //Every time a new list is set, we call notifyDataSetChanged to let the adapter refresh and re-render the data.
        notifyDataSetChanged();
    }

    // 2. SwipeCardViewHolder:
    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class CardSwipeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public CardSwipeViewHolder(@NonNull View itemView) {
            super(itemView);

            SwipeNewsCardBinding binding = SwipeNewsCardBinding.bind(itemView);
            imageView = binding.swipeCardImageView;
            titleTextView = binding.swipeCardTitle;
            descriptionTextView = binding.swipeCardDescription;
        }

    }

    // 3. Adapter overrides: three essential functions for an adapter

    //Provide the generated item views
    //Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public CardSwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //The LayoutInflater class is used to instantiate the contents of layout XML files into their corresponding View objects.
        //In other words, it takes an XML file as input and builds the View objects from it.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_news_card, parent, false);
        return new CardSwipeViewHolder(view);
    }

    //Bind the data with a view
    //Called by RecyclerView to display the data at the specified position.
    //This method should update the contents of the ViewHolder to reflect the item at the given position.
    @Override
    public void onBindViewHolder(@NonNull CardSwipeViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTextView.setText(article.title);
        holder.descriptionTextView.setText(article.description);
        if (article.urlToImage != null) {
            //Use Picasso library to load any given image URL
            Picasso.get().load(article.urlToImage).into(holder.imageView);
        }
    }

    //Provide the current data collection size
    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return articles.size();
    }

}
