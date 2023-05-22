package com.codingstuff.quizzyapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.UserModel;
import com.codingstuff.quizzyapp.repository.CategoryRepository;
import com.codingstuff.quizzyapp.repository.MessageResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CategoryViewModel extends ViewModel {
    CategoryRepository categoryRepository = new CategoryRepository();
    private MutableLiveData<MessageResult<CategoryModel>> isDataSaved;
    private  MutableLiveData<ArrayList<CategoryModel>> data;


    public LiveData<ArrayList<CategoryModel>> GetData(){
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }
    public LiveData<MessageResult<CategoryModel>> getIsDataSaved() {
        if (isDataSaved == null) {
            isDataSaved = new MutableLiveData<>();
        }
        return isDataSaved;
    }

    public void getCategories() {
        categoryRepository.GetCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    ArrayList<CategoryModel> categoryList = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        String id = documentSnapshot.getId();
                        CategoryModel category = documentSnapshot.toObject(CategoryModel.class);
                        categoryList.add(new CategoryModel(id, category.getName()));
                    }
                    data.setValue(categoryList);
                } else
                    data.setValue(new ArrayList<>());
            } else {// Manejo de excepciones en caso de error
                data.setValue(new ArrayList<>());
            }
        });
    }


    public void Insert(CategoryModel categoryModel){
        Map<String, Object> data = new HashMap<>();
        data.put("name", categoryModel.getName());
       categoryRepository.GetCollection().add(data).addOnCompleteListener(
                runnable -> {
                    if(runnable.isSuccessful()){
                        isDataSaved.setValue(new MessageResult<>(categoryModel,"has been successfully created",true));
                    }else {
                        isDataSaved.setValue(new MessageResult<>(categoryModel,runnable.getException().getMessage(),false));
                    }

                }
        );
    }
}
