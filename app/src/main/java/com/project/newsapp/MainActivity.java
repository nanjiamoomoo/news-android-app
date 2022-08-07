package com.project.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.newsapp.model.NewsResponse;
import com.project.newsapp.network.NewsApi;
import com.project.newsapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    //Overriding onSupportNavigateUp is for handling the top left back button.
    //Notice the title bar also changes with bottom navigation.
    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finds the BottomNavigationView with id "nav_view"
        BottomNavigationView navigationView = findViewById(R.id.nav_view);

        //Finds a fragment that was identified by the given id "nav_host_fragment"
        //NavHostFragment provides an area within your layout for self-contained navigation to occur.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        //Returns the navigation controller for the navigation host fragment
        navController = navHostFragment.getNavController();

        //Sets up a BottomNavigationView for use with a NavController
        //This will call onNavDestinationSelected(MenuItem, NavController),
        // which attempts to navigate to the NavDestination associated with the given MenuItem
        NavigationUI.setupWithNavController(navigationView, navController);

        //Sets up the ActionBar returned by AppCompatActivity.getSupportActionBar() for use with a NavController.
        //By calling this method, the title in the action bar will automatically be updated when the destination changes
        NavigationUI.setupActionBarWithNavController(this, navController);

        //New a task, make the call<NewsResponse>
        //add task to queue
        //while(true) {retrofit keep check the queue}
        //if queue has 'task', retrofit do task: call endpoint, parse json, etc
        //once retrofit finish the task
        //callback.onResponse(response)
        //if (task success) newsResponseCallBack.onResponse

        //Create an implementation of the API endpoints defined by the NewsApi interface.
        NewsApi newsApi = RetrofitClient.newInstance().create(NewsApi.class);

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
                    Log.d("getNewTopHeadlines", response.body().toString());
                } else {
                    Log.d("getTopHeadlines", response.toString());
                }
            }

            //Invoked when a network exception occurred talking to the server or when an unexpected exception
            //occurred creating the request or processing the response.
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("getTopHeadlines", t.toString());
            }
        });
    }
}