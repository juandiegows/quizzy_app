package com.codingstuff.quizzyapp.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.codingstuff.quizzyapp.MainActivity;
import com.codingstuff.quizzyapp.R;

public class PlayActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea terminar el juego?")
                .setCancelable(false)
                .setPositiveButton("si", (dialog, id) -> {
                    // Cerrar la aplicación
                 startActivity(new Intent(getBaseContext(), AdminActivity.class));
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.cancel();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }
}