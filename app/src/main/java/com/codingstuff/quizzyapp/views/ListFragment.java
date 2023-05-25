package com.codingstuff.quizzyapp.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.codingstuff.quizzyapp.Adapter.CategoryAdapter;
import com.codingstuff.quizzyapp.Adapter.CategoryAdapterSelected;
import com.codingstuff.quizzyapp.Adapter.QuizListAdapter;
import com.codingstuff.quizzyapp.Model.QuizListModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.viewmodel.AuthViewModel;
import com.codingstuff.quizzyapp.viewmodel.CategoryViewModel;
import com.codingstuff.quizzyapp.viewmodel.QuizListViewModel;

import java.util.List;


public class ListFragment extends Fragment {
    CategoryViewModel categoryViewModel = new CategoryViewModel();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private NavController navController;
    Button btnPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CategoryAdapterSelected.categoryModelsFull.clear();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.listQuizRecyclerview);
        progressBar = view.findViewById(R.id.quizListProgressbar);
        navController = Navigation.findNavController(view);
        btnPlay = view.findViewById(R.id.buttonPlay);
        btnPlay.setEnabled(false);
        btnPlay.setOnClickListener(view1 -> {
            navController.navigate(R.id.action_listFragment2_to_playQuestionnFragment);
        });
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        categoryViewModel.GetData().observe(getViewLifecycleOwner(), categoryModels -> {
            // Crea y asigna un adaptador al RecyclerView
            CategoryAdapterSelected adapter = new CategoryAdapterSelected(categoryModels);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
            btnPlay.setEnabled(true);
        });

        categoryViewModel.getCategories();

    }


}