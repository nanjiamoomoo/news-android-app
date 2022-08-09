package com.project.newsapp.repository;


import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.project.newsapp.NewsApplication;
import com.project.newsapp.database.NewsAppDatabase;
import com.project.newsapp.model.Article;
import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.network.NewsApi;
import com.project.newsapp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final NewsApi newsApi;
    private final NewsAppDatabase database;

    public NewsRepository() {
        //Create an implementation of the API endpoints defined by the NewsApi interface.
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = NewsApplication.getDatabase();
    }

    // Database query accessing the disk storage can be very slow sometimes.
    // We do not want it to run on the default main UI thread.
    // So we use an AsyncTask to dispatch the query work to a background thread.
    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {
        private final NewsAppDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(NewsAppDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }


        //Everything inside doInBackground would be executed on a separate background thread
        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    //save an article to the database
    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        //execute() returns immediately.
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        //The database operation runs in the background and notifies the result through the resultLiveData at a later time.
        return resultLiveData;
    }

    //query all saved articles from the database
    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }


    // delete a saved article from database
    // A simpler version of the AsyncTask to run deleteArticle operation when we don't care about the result or the intermediate progress.
    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

    // Get data from endpoint using url https://newsapi.org/v2/top-headlines?country={country} with registered apiKey,
    // and hold the data in LiveData
    public LiveData<NewsResponse> getTopHeadlines(String country) {
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();

        //New a task, make the call<NewsResponse>
        //add task to queue
        //while(true) {retrofit keep check the queue}
        //if queue has 'task', retrofit do task: call endpoint, parse json, etc
        //once retrofit finish the task
        //callback.onResponse(response)
        //if (task success) newsResponseCallBack.onResponse

        //enqueue: Asynchronously send the request and notify {@code callback} of its response or
        //if an error occurred talking to the server, creating the request, or processing the response.
        newsApi.getTopHeadlines("US").enqueue(new Callback<NewsResponse>() {

            //Invoked for a received HTTP response.
            //An HTTP response may still indicate an application-level failure such as a 404 or 500.
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                // call response.isSuccessful() to determine if the response indicates success.
                if (response.isSuccessful()) {
                    //Notice the response is automatically deserialized into our model class.
                    topHeadlinesLiveData.setValue(response.body());
                } else {
                    topHeadlinesLiveData.setValue(null);
                }
            }

            //Invoked when a network exception occurred talking to the server or when an unexpected exception
            //occurred creating the request or processing the response.
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                topHeadlinesLiveData.setValue(null);
            }
        });
        return topHeadlinesLiveData;
    }


    // Get data from endpoint using url https://newsapi.org/v2/everything?q={query} with registered apiKey,
    // and hold the data in LiveData
    public LiveData<NewsResponse> searchNews(String query) {
        MutableLiveData<NewsResponse> allLiveData = new MutableLiveData<>();

        //New a task, make the call<NewsResponse>
        //add task to queue
        //while(true) {retrofit keep check the queue}
        //if queue has 'task', retrofit do task: call endpoint, parse json, etc
        //once retrofit finish the task
        //callback.onResponse(response)
        //if (task success) newsResponseCallBack.onResponse

        //enqueue: Asynchronously send the request and notify {@code callback} of its response or
        //if an error occurred talking to the server, creating the request, or processing the response.
        newsApi.getEverything("query", 40).enqueue(new Callback<NewsResponse>() {

            //Invoked for a received HTTP response.
            //An HTTP response may still indicate an application-level failure such as a 404 or 500.
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                // call response.isSuccessful() to determine if the response indicates success.
                if (response.isSuccessful()) {
                    //Notice the response is automatically deserialized into our model class.
                    allLiveData.setValue(response.body());
                } else {
                    allLiveData.setValue(null);
                }
            }

            //Invoked when a network exception occurred talking to the server or when an unexpected exception
            //occurred creating the request or processing the response.
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                allLiveData.setValue(null);
            }
        });
        return allLiveData;
    }
}
