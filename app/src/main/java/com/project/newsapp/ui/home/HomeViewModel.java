package com.project.newsapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.model.Article;
import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.repository.NewsRepository;

public class HomeViewModel extends ViewModel {
    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    public void setFavoriteArticleInput(Article article) {
        repository.favoriteArticle(article);
    }
    public LiveData<NewsResponse> getTopHeadlines() {
        // Returns a {@code LiveData} mapped from the input {@code source} {@code LiveData} by applying
        // {@code switchMapFunction} to each value set on {@code source}.
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }
}
