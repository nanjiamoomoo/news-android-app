package com.project.newsapp.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.databinding.SearchNewsItemBinding;
import com.project.newsapp.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//Steps to implement a RecyclerView:
//Create the layout for item views.
//Create an adapter that contains the ViewHolder.
//Link the item_view layout to the ViewHolder and finish create/bind ViewHolder process in the recyclerView
//Pass the Adapter to the RecyclerView and setup the LayoutManage


//Adapters provide a binding from an app-specific data set to views that are displayed within a {@link RecyclerView}.
//A class that extends ViewHolder that will be used by the adapter
public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newList) {
        articles.clear();
        articles.addAll(newList);

        //Every time a new list is set, we call notifyDataSetChanged to let the adapter refresh and re-render the data.
        notifyDataSetChanged();
    }

    // 2. SearchNewsViewHolder:
    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }

    // 3. Adapter overrides:

    //Provide the generated item views
    //Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //The LayoutInflater class is used to instantiate the contents of layout XML files into their corresponding View objects.
        //In other words, it takes an XML file as input and builds the View objects from it.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }

    //Bind the data with a view
    //Called by RecyclerView to display the data at the specified position.
    //This method should update the contents of the ViewHolder to reflect the item at the given position.
    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        if (article.urlToImage != null) {
            // //Use Picasso library to load any given image URL
            Picasso.get().load(article.urlToImage).resize(200,200).into(holder.itemImageView);
        }
    }

    //Provide the current data collection size
    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return articles.size();
    }


}
