package com.project.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Finds the BottomNavigationView with id "nav_view"
        BottomNavigationView navigationView = findViewById(R.id.nav_view);

        //Finds a fragment that was identified by the given id "nav_host_fragment"
        //NavHostFragment provides an area within your layout for self-contained navigation to occur.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        //Returns the navigation controller for the navigation host fragment
        navController = navHostFragment.getNavController();

        //Sets up a BottomNavigationView for use with a NavController
        //This will call onNavDestinationSelected(MenuItem, NavController),
        // which attempts to navigate to the NavDestination associated with the given MenuItem
        NavigationUI.setupWithNavController(navigationView, navController);

        //Sets up the ActionBar returned by AppCompatActivity.getSupportActionBar() for use with a NavController.
        //By calling this method, the title in the action bar will automatically be updated when the destination changes
        NavigationUI.setupActionBarWithNavController(this, navController);

    }

    //Overriding onSupportNavigateUp is for handling the top left back button.
    //Notice the title bar also changes with bottom navigation.
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

}