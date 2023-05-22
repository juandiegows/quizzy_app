package com.codingstuff.quizzyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.viewmodel.CategoryViewModel;


public class AddEditCategoryFragment extends Fragment {


    CategoryViewModel categoryViewModel;
    Button btnSave;
    EditText txtName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_category, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getIsDataSaved().observe(getViewLifecycleOwner(), categoryModelMessageResult ->
        {
            btnSave.setEnabled(true);

            if (categoryModelMessageResult.isSuccess()) {
                txtName.setText("");

            }
            Toast.makeText(getContext(), categoryModelMessageResult.getMessage(), Toast.LENGTH_LONG).show();
        });
        btnSave = view.findViewById(R.id.btnSubmitCategory);
        txtName = view.findViewById(R.id.nameEdit);
        btnSave.setOnClickListener(
                view1 -> {
                    String name = txtName.getText().toString();
                    if (name.isEmpty()) {
                        Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    btnSave.setEnabled(false);

                    categoryViewModel.Insert(new CategoryModel(name));

                }
        );
    }
}