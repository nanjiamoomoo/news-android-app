package com.project.newsapp.repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.network.NewsApi;
import com.project.newsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final NewsApi newsApi;

    public NewsRepository() {
        //Create an implementation of the API endpoints defined by the NewsApi interface.
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
    }

    /**
     * Get data from endpoint using url https://newsapi.org/v2/top-headlines?country={country} with registered apiKey,
     * * and hold the data in LiveData
     * @param country: the search keyword
     * @return LiveData: This class is designed to hold individual data fields of {@link ViewModel},
     * * but can also be used for sharing data between different modules in your application
     * * in a decoupled fashion.
     */
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

    /**
     * Get data from endpoint using url https://newsapi.org/v2/everything?q={query} with registered apiKey,
     * * and hold the data in LiveData
     * @param query: the search keyword
     * @return LiveData: This class is designed to hold individual data fields of {@link ViewModel},
     * * but can also be used for sharing data between different modules in your application
     * * in a decoupled fashion.
     */
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
