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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    //The build system will generate a binding class based on the name of the xml file. fragment_search.xml will be generated as FragmentSearchBinding.
    private FragmentSearchBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                .observe(getViewLifecycleOwner(),
                        newsResponse -> {
                            Log.d("SearchFragment", newsResponse.toString());
                            newsAdapter.setArticles(newsResponse.articles);
                        });
    }
}