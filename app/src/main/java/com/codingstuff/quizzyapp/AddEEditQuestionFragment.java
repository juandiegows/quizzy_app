package com.codingstuff.quizzyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.viewmodel.CategoryViewModel;
import com.codingstuff.quizzyapp.viewmodel.QuestionViewModel;

import java.util.ArrayList;
import java.util.Arrays;


public class AddEEditQuestionFragment extends Fragment {

    Switch SA, SB, SC, SD;
    EditText EA, EB, EC, ED, txtTitle;
    ArrayList<Switch> switches = new ArrayList<Switch>();
    ArrayList<EditText> editTexts = new ArrayList<>();
    Spinner spinner;
    CategoryViewModel categoryViewModel;
    Button btnSave;
    QuestionViewModel questionViewModel = new QuestionViewModel();
    String category;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_e_edit_question, container, false);
    }

    public void setCorrect(View view) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.SCategory);
        categoryViewModel = new CategoryViewModel();
        categoryViewModel.GetData().observe(getViewLifecycleOwner(), categoryModels -> {
            spinner.setAdapter(new ArrayAdapter<CategoryModel>(getContext(), android.R.layout.simple_dropdown_item_1line, categoryModels));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   category = ((CategoryModel) parent.getItemAtPosition(position)).getId();

                  }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No se seleccionó ningún elemento
                }
            });
        });
        categoryViewModel.getCategories();
        SA = view.findViewById(R.id.SA);
        SB = view.findViewById(R.id.SB);
        SC = view.findViewById(R.id.SC);
        SD = view.findViewById(R.id.SD);

        switches.add(SA);
        switches.add(SB);
        switches.add(SC);
        switches.add(SD);
        btnSave = view.findViewById(R.id.btnSaveP);
        questionViewModel.getIsDataSaved().observe(getViewLifecycleOwner(), questionModelMessageResult -> {

            if(questionModelMessageResult.isSuccess()){
                getActivity().onBackPressed();
                Toast.makeText(getContext(), "Se agrego correctamente", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getContext(), questionModelMessageResult.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnSave.setOnClickListener(view1 -> {
            String answer = "";
            for (int i = 0; i < 4; i++) {
                if(switches.get(i).isChecked()){
                    answer = editTexts.get(i).getText().toString();
                }
            }
            questionViewModel.Insert(new QuestionModel("", answer, txtTitle.getText().toString(), "Categories/"+category, new ArrayList<>(Arrays.asList(EA.getText().toString(), EB.getText().toString(), EC.getText().toString(), ED.getText().toString()))));
        });

        EA = view.findViewById(R.id.txtOpcionA);
        EB = view.findViewById(R.id.txtOpcionB);
        EC = view.findViewById(R.id.txtOpcionC);
        ED = view.findViewById(R.id.txtOpcionD);
        txtTitle = view.findViewById(R.id.editTextTextTitle);
        editTexts.add(EA);
        editTexts.add(EB);
        editTexts.add(EC);
        editTexts.add(ED);

        SA.setClickable(true);
        SA.setOnClickListener(view1 -> {
            for (Switch aSwitch : switches) {
                aSwitch.setChecked(false);
            }
            ((Switch) view1).setChecked(true);
        });
        SB.setClickable(true);
        SB.setOnClickListener(view1 -> {
            for (Switch aSwitch : switches) {
                aSwitch.setChecked(false);
            }
            ((Switch) view1).setChecked(true);
        });

        SC.setClickable(true);
        SC.setOnClickListener(view1 -> {
            for (Switch aSwitch : switches) {
                aSwitch.setChecked(false);
            }
            ((Switch) view1).setChecked(true);
        });

        SD.setClickable(true);
        SD.setOnClickListener(view1 -> {
            for (Switch aSwitch : switches) {
                aSwitch.setChecked(false);
            }
            ((Switch) view1).setChecked(true);
        });
    }
}