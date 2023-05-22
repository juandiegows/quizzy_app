package com.codingstuff.quizzyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.codingstuff.quizzyapp.Adapter.CategoryAdapter;
import com.codingstuff.quizzyapp.viewmodel.CategoryViewModel;


public class CategoriesFragment extends Fragment {

    CategoryViewModel categoryViewModel;
    Button btnAddCategory;
    private NavController navController;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //FirebaseApp.initializeApp(getContext());
        btnAddCategory = view.findViewById(R.id.btnAddCategory);

        recyclerView = view.findViewById(R.id.recyclerViewQuestion);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        navController = Navigation.findNavController(view);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.GetData().observe(getViewLifecycleOwner(), categoryModels -> {
            // Crea y asigna un adaptador al RecyclerView
            CategoryAdapter adapter = new CategoryAdapter(categoryModels);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        });
        categoryViewModel.getCategories();
        btnAddCategory.setOnClickListener(
                view1 -> navController.navigate(R.id.action_categoriesFragment_to_addEditCategoryFragment)
        );
    }
}