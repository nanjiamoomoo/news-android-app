package com.project.newsapp.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.repository.NewsRepository;

public class SearchViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();

    public SearchViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }


    public LiveData<NewsResponse> searchNews() {
        // Returns a {@code LiveData} mapped from the input {@code source} {@code LiveData} by applying
        // {@code switchMapFunction} to each value set on {@code source}.
        return Transformations.switchMap(searchInput, repository::searchNews);
    }
}
