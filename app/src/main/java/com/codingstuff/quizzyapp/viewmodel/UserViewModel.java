package com.codingstuff.quizzyapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codingstuff.quizzyapp.Model.UserModel;
import com.codingstuff.quizzyapp.repository.MessageResult;
import com.codingstuff.quizzyapp.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class UserViewModel extends ViewModel {
    UserRepository userRepository = new UserRepository();
    private MutableLiveData<MessageResult<UserModel>> isDataSaved;

    public LiveData<MessageResult<UserModel>> getIsDataSaved() {
        if (isDataSaved == null) {
            isDataSaved = new MutableLiveData<>();
        }
        return isDataSaved;
    }

    public void LogOut(){
        FirebaseAuth.getInstance().signOut();
        isDataSaved.setValue(new MessageResult<>(null,"sign out successfully :)", true));
    }
    public void signUp(UserModel userModel) {

        Map<String, Object> data = new HashMap<>();
        data.put("email", userModel.getEmail());
        data.put("email", userModel.getPassword());
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userModel.getEmail(), userModel.getPassword()).addOnCompleteListener(
                runnable -> {
                    if (runnable.isSuccessful()) {
                        isDataSaved.setValue(new MessageResult<>(userModel, "login successfully :)", true)); // Datos guardados con éxito

                    }else {
                        isDataSaved.setValue(new MessageResult<>(userModel, "Cannot register user for "+runnable.getException().getMessage()+":)", true)); // Datos guardados con éxito

                    }
                }

        );

    }
}
