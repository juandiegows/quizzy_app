package com.codingstuff.quizzyapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.Model.ResultModel;
import com.codingstuff.quizzyapp.repository.MessageResult;
import com.codingstuff.quizzyapp.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionViewModel extends ViewModel {
    private MutableLiveData<MessageResult<QuestionModel>> isDataSaved;
    private  MutableLiveData<ArrayList<QuestionModel>> data;


    public LiveData<ArrayList<QuestionModel>> GetData(){
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }
    public LiveData<MessageResult<QuestionModel>> getIsDataSaved() {
        if (isDataSaved == null) {
            isDataSaved = new MutableLiveData<>();
        }
        return isDataSaved;
    }
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;

    QuestionRepository questionRepository = new QuestionRepository();

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel() {
    }

    public void Insert(QuestionModel questionModel) {
        Map<String, Object> data = new HashMap<>();
        data.put("title", questionModel.getTitle());
        data.put("answer_correct", questionModel.getAnswer_correct());
        data.put("answers", questionModel.getAnswers());
        data.put("category", questionModel.getCategory());

        questionRepository.GetCollection().add(data).addOnCompleteListener(
                runnable -> {
                    if (runnable.isSuccessful()) {
                        isDataSaved.setValue(new MessageResult<>( questionModel, "Se ha creado correctamente", true));
                    } else {
                        isDataSaved.setValue(new MessageResult<>( questionModel, runnable.getException().getMessage(), false));
                    }

                }
        );
    }


}
