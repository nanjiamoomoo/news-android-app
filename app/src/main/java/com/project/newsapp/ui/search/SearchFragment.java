package com.project.newsapp.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.project.newsapp.databinding.FragmentSearchBinding;
import com.project.newsapp.repository.NewsRepository;
import com.project.newsapp.repository.NewsViewModelFactory;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    // The build system will generate a binding class based on the name of the xml file.
    // fragment_search.xml will be generated as FragmentSearchBinding.
    private FragmentSearchBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instead of using findViewById to get all the views defined with id in xml layout
        //View Binding is a new tool allowing automatic binding UI layout resources with Java code.
        //The build system will generate a binding class based on the name of the xml file. fragment_search.xml will be generated as FragmentSearchBinding.
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchNewsAdapter newsAdapter = new SearchNewsAdapter();

        /**
         * Creates a vertical GridLayoutManager
         * @param context Current context, will be used to access resources.
         * @param spanCount The number of columns in the grid
         */
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);


        // A recycler view requires an Adapter and LayoutManager.
        // An Adapter is for creating each item view and binding the data to a view by recycling.
        // A LayoutManager is for controlling how the views are organized, typically in a list or in a grid.
        binding.newsResultsRecyclerView.setLayoutManager(gridLayoutManager);
        binding.newsResultsRecyclerView.setAdapter(newsAdapter);

        // The Views also notify the ViewModel about different actions.
        // The MVVM pattern supports two-way data binding between the View and ViewModel and there is a many-to-one relationship between View and ViewModel.
        // View has a reference to ViewModel but ViewModel has no information about the View.
        // ViewModel exposes streams of data to which the Views can bind to.
        // The consumer of the data should know about the producer, but the producer — the ViewModel — doesn’t know, and doesn’t care, who consumes the data.
        binding.newsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                //clearFocus() was added because of a bug in the SearchView implementation
                binding.newsSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SearchViewModel.class);
        viewModel
                .searchNews()
                //If LiveData already has data set, it will be delivered to the observer
                .observe(getViewLifecycleOwner(),
                        newsResponse -> {
                            Log.d("SearchFragment", newsResponse.toString());
                            newsAdapter.setArticles(newsResponse.articles);
                        });
    }
}