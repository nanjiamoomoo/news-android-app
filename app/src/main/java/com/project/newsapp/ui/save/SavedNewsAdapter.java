package com.project.newsapp.ui.save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newsapp.R;
import com.project.newsapp.databinding.SavedNewsItemBinding;
import com.project.newsapp.model.Article;

import java.util.ArrayList;
import java.util.List;

public class SavedNewsAdapter extends RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>{

    // 1. Supporting data:
    //To remove items from saved articles by clicking on the icon. We need to add an ItemCallback to relay the events from inside SaveNewsAdapter to SaveFragment
    interface ItemCallback {
        void onOpenDetails(Article article);
        void onRemoveFavorite(Article article);
    }

    private ItemCallback itemCallback;

    private List<Article> articles = new ArrayList<>();

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    public void setArticles(List<Article> newList) {
        articles.clear();
        articles.addAll(newList);

        //Every time a new list is set, we call notifyDataSetChanged to let the adapter refresh and re-render the data.
        notifyDataSetChanged();
    }

    // 2. SavedNewsViewHolder:
    // A ViewHolder describes an item view and metadata about its place within the RecyclerView
    public static class SavedNewsViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView;
        TextView descriptionTextView;
        ImageView favoriteIcon;

        public SavedNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SavedNewsItemBinding binding = SavedNewsItemBinding.bind(itemView);
            authorTextView = binding.savedItemAuthorContent;
            descriptionTextView = binding.savedItemDescriptionContent;
            favoriteIcon = binding.savedItemFavoriteImageView;
        }
    }

    // 3. Adapter overrides:

    //Provide the generated item views
    //Called when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public SavedNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //The LayoutInflater class is used to instantiate the contents of layout XML files into their corresponding View objects.
        //In other words, it takes an XML file as input and builds the View objects from it.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new SavedNewsViewHolder(view);
    }

    //Bind the data with a view
    //Called by RecyclerView to display the data at the specified position.
    //This method should update the contents of the ViewHolder to reflect the item at the given position.
    @Override
    public void onBindViewHolder(@NonNull SavedNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.authorTextView.setText(article.author);
        holder.descriptionTextView.setText(article.description);
        holder.favoriteIcon.setOnClickListener(v -> itemCallback.onRemoveFavorite(article));
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    //Provide the current data collection size
    //Returns the total number of items in the data set held by the adapter.
    @Override
    public int getItemCount() {
        return articles.size();
    }
}
