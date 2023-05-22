package com.codingstuff.quizzyapp.Adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.repository.CategoryRepository;
import com.codingstuff.quizzyapp.repository.QuestionRepository;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {


    ArrayList<QuestionModel> questionModels;

    public QuestionAdapter(ArrayList<QuestionModel> questionModels) {
        this.questionModels = questionModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.question_item, parent, false);
        return new QuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(questionModels.get(position).getTitle());
        holder.btnDelete.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
            builder.setMessage("esta seguro de eliminar a  "+questionModels.get(position).getTitle()+"?")
                    .setCancelable(false)
                    .setPositiveButton("si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            delete(position,holder.itemView);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return questionModels.size();
    }
    public void delete(int position, View view) {
        if (position >= 0 && position < questionModels.size()) {
            QuestionModel category = questionModels.get(position);
            new QuestionRepository().GetCollection().document(category.getId()).delete().addOnSuccessListener(aVoid -> {

                        questionModels.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(view.getContext(), "Error deleting category", Toast.LENGTH_SHORT).show();
                    });


        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtCategory;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtQuestion);
            txtCategory = itemView.findViewById(R.id.txtcate);
            btnDelete = itemView.findViewById(R.id.btnDeleteQuestion);
        }
    }
}
