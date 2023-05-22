package com.codingstuff.quizzyapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.quizzyapp.MainActivity;
import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.repository.CategoryRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryModel> categories;

    public CategoryAdapter(ArrayList<CategoryModel> categories) {
        this.categories = categories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        ImageView btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtName.setText(categories.get(position).getName());
        holder.btnDelete.setOnClickListener(
                view -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setMessage("esta seguro de eliminar a  "+categories.get(position).getName()+"?")
                            .setCancelable(false)
                            .setPositiveButton("yes, sure", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   deleteCategory(position,holder.itemView);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
        );
        holder.btnEdit.setOnClickListener(
                view -> {
                    editCategory(position, view.getContext());
                }

        );
    }
    public void editCategory(int position, Context context) {
        if (position >= 0 && position < categories.size()) {
            CategoryModel category = categories.get(position);
            String categoryId = category.getId(); // Assuming your CategoryModel class has a method called getId() to get the category ID

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Edit Category");

            final EditText input = new EditText(context);
            input.setText(category.getName()); // Set the current value of the category in the text box
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> {
                String newName = input.getText().toString().trim();

                // Update the category name in Firestore
                DocumentReference categoryRef = new CategoryRepository().GetCollection().document(categoryId);
                categoryRef.update("name", newName)
                        .addOnSuccessListener(aVoid -> {
                            // Successful update, update the value in the category list in the adapter
                            category.setName(newName);
                            notifyItemChanged(position);
                        })
                        .addOnFailureListener(e -> {
                            // Error updating the category, display an error message or perform error handling
                            Toast.makeText(context, "Error updating the category", Toast.LENGTH_SHORT).show();
                        });
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        }
    }
    public void deleteCategory(int position, View view) {
        if (position >= 0 && position < categories.size()) {
            CategoryModel category = categories.get(position);
            new CategoryRepository().GetCollection().document(category.getId()).delete().addOnSuccessListener(aVoid -> {

                        categories.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(view.getContext(), "Error deleting category", Toast.LENGTH_SHORT).show();
                    });


        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
