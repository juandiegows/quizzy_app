package com.codingstuff.quizzyapp.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CategoryRepository {
    public CollectionReference GetCollection() {

        return   FirebaseFirestore.getInstance().collection("Categories");
    }


}
