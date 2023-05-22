package com.codingstuff.quizzyapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.codingstuff.quizzyapp.AdminQuestionsActivity;
import com.codingstuff.quizzyapp.MainActivity;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.viewmodel.UserViewModel;

public class AdminActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea Salir de la aplicacion?")
                .setCancelable(false)
                .setPositiveButton("si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cerrar la aplicación
                        AdminActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancelar el cierre de la aplicación
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Button btnSignOut, btnAdminCategory, btnAdminQuestion, btnPlay;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnAdminCategory = findViewById(R.id.btnManageCategory);
        btnAdminQuestion = findViewById(R.id.btnManageQuestion);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getIsDataSaved().observe(this, userModelMessageResult -> {
            btnSignOut.setEnabled(true);
            if (userModelMessageResult.isSuccess()) {
                startActivity(new Intent(this, MainActivity.class));
            }
        });
        btnPlay = findViewById(R.id.btnQuizzy);
        btnPlay.setOnClickListener(view -> startActivity(new Intent(this, PlayActivity.class)));
        btnAdminCategory.setOnClickListener(
                view -> startActivity(new Intent(this, AdminCategoryActivity.class))
        );
        btnAdminQuestion.setOnClickListener(

                view -> startActivity(new Intent(this, AdminQuestionsActivity.class))
        );
        btnSignOut.setOnClickListener(
                view -> {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("you want to close session ?")
                            .setCancelable(false)
                            .setPositiveButton("yes, sign out", (dialog, id) -> {
                                btnSignOut.setEnabled(false);
                                userViewModel.LogOut(); // Cerrar la aplicación
                            })
                            .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();


                }
        );
    }
}