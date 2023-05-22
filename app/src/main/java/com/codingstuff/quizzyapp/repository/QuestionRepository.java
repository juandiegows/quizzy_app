package com.codingstuff.quizzyapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.Model.ResultModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionRepository {

    public CollectionReference GetCollection() {

        return   FirebaseFirestore.getInstance().collection("Questions");
    }

}
