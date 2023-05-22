package com.codingstuff.quizzyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.codingstuff.quizzyapp.Adapter.QuestionAdapter;
import com.codingstuff.quizzyapp.viewmodel.QuestionViewModel;


public class QuestionFragment extends Fragment {

    private NavController navController;
    Button btnAddEdit;
    QuestionViewModel questionViewModel = new QuestionViewModel();

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddEdit = view.findViewById(R.id.btnAddQ);
        navController = Navigation.findNavController(view);
        progressBar = view.findViewById(R.id.progressBarQuestion);
        recyclerView = view.findViewById(R.id.recyclerViewQuestion);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        progressBar.setVisibility(View.VISIBLE);
        questionViewModel.GetData().observe(getViewLifecycleOwner(), questionModels -> {
            QuestionAdapter adapter = new QuestionAdapter(questionModels);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        });
        questionViewModel.get();
        btnAddEdit.setOnClickListener(
                __ -> navController.navigate(R.id.action_questionFragment_to_addEEditQuestionFragment)
        );
    }
}