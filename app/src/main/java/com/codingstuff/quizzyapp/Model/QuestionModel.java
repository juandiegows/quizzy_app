package com.codingstuff.quizzyapp.Model;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

public class QuestionModel {


    private String id;


    private String answer_correct , title, category;
    private ArrayList<String> answers;

    public QuestionModel(String id, String answer_correct, String title, String category, ArrayList<String> answers) {
        this.id = id;
        this.answer_correct = answer_correct;
        this.title = title;
        this.category = category;
        this.answers = answers;
    }

    public QuestionModel(String id, String title, String answer_correct, ArrayList<String> answers) {
        this.id = id;
        this.answer_correct = answer_correct;
        this.title = title;
        this.answers = answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswer_correct() {
        return answer_correct;
    }

    public void setAnswer_correct(String answer_correct) {
        this.answer_correct = answer_correct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
