package com.codingstuff.quizzyapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codingstuff.quizzyapp.Adapter.CategoryAdapterSelected;
import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.Model.ResultModel;
import com.codingstuff.quizzyapp.viewmodel.QuestionViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;


public class PlayQuestionnFragment extends Fragment {

    private NavController navController;

    ArrayList<QuestionModel> questionModels = new ArrayList<>();
    QuestionViewModel questionViewModel = new QuestionViewModel();
    TextView txtNumber, txtQuestion;
    Button btna, btnb, btnc, btnd;

    QuestionModel questionModel_actual;
    int index = 1;
    int i = 0;
    int respuestaBuena = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_questionn, container, false);
    }


    public void Responder(View view) {


        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(btna, btnb, btnc, btnd));
        String respuesta = view.getTag().toString();
        if (respuesta.equalsIgnoreCase(questionModel_actual.getAnswer_correct())) {
            respuestaBuena++;
            view.setBackgroundColor(Color.GREEN);
        } else {
            view.setBackgroundColor(Color.RED);
            for (Button button : buttons) {
                button.setEnabled(false);
                if (button.getTag().toString().equalsIgnoreCase(questionModel_actual.getAnswer_correct())) {
                    button.setBackgroundColor(Color.GREEN);
                }
            }
        }

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Coloca aquí el código de la función que deseas ejecutar
                view.setBackgroundColor(Color.WHITE);

                i++;
                index++;
                for (Button button : buttons) {
                    button.setBackgroundColor(Color.WHITE);
                    button.setEnabled(true );
                }
                if (index == 10) {
                    ResultModel.setCorrect(respuestaBuena);
                    ResultModel.setWrong(10 - respuestaBuena);
                    ResultModel.setTotal(index);
                    navController.navigate(R.id.play_end);
                }
                if (i < questionModels.size()) {
                   questionModel_actual = questionModels.get(i);
                    SetQuestion();
                } else {
                    index--;
                    ResultModel.setCorrect(respuestaBuena);
                    ResultModel.setWrong(index - respuestaBuena);
                    ResultModel.setTotal(index);
                    navController.navigate(R.id.play_end);
                }
            }


        }, 3000);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtNumber = view.findViewById(R.id.txtNQuestion);
        txtQuestion = view.findViewById(R.id.txtQPlay);
        navController = Navigation.findNavController(view);

        btna = view.findViewById(R.id.PA);
        btnb = view.findViewById(R.id.PB);
        btnc = view.findViewById(R.id.PC);
        btnd = view.findViewById(R.id.PD);
        btna.setOnClickListener(view1 -> Responder(view1));
        btnb.setOnClickListener(view1 -> Responder(view1));
        btnc.setOnClickListener(view1 -> Responder(view1));
        btnd.setOnClickListener(view1 -> Responder(view1));
        questionViewModel.GetData().observe(getViewLifecycleOwner(), questionModels1 -> {

            Log.i("data", CategoryAdapterSelected.categoryModelsFull.size()+"");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                questionModels = (ArrayList<QuestionModel>) questionModels1.stream()
                        .filter(question -> CategoryAdapterSelected.categoryModelsFull.stream().anyMatch(category -> category.getId().equals(question.getCategory().split("/")[1])))
                        .collect(Collectors.toList());
            }else {

            }
            index = 1;
            i = 0;
            Collections.shuffle(questionModels);
            if (questionModels.size() > 0) {
                questionModel_actual = questionModels.get(0);

                SetQuestion();


            }else {

            }

        });
        questionViewModel.get();
    }

    private void SetQuestion() {
        txtNumber.setText("Pregunta N° " + index + " ");
        txtQuestion.setText(questionModel_actual.getTitle());
        ArrayList<String> answer = questionModel_actual.getAnswers();
        Collections.shuffle(answer);
        btna.setTag(answer.get(0));
        btna.setText("A) " + answer.get(0));
        btnb.setText("B) " + answer.get(1));
        btnb.setTag(answer.get(1));
        btnc.setText("C) " + answer.get(2));
        btnc.setTag(answer.get(2));
        btnd.setText("D) " + answer.get(3));
        btnd.setTag(answer.get(3));
    }
}