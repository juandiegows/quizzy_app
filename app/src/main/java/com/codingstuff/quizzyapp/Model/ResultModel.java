package com.codingstuff.quizzyapp.Model;

public class ResultModel {

    private static  int correct ,wrong, total;

    public static int getCorrect() {
        return correct;
    }

    public static void setCorrect(int correct) {
        ResultModel.correct = correct;
    }

    public static int getWrong() {
        return wrong;
    }

    public static void setWrong(int wrong) {
        ResultModel.wrong = wrong;
    }

    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        ResultModel.total = total;
    }
}
