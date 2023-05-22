package com.codingstuff.quizzyapp.Adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.quizzyapp.Model.CategoryModel;
import com.codingstuff.quizzyapp.Model.QuestionModel;
import com.codingstuff.quizzyapp.R;
import com.codingstuff.quizzyapp.repository.QuestionRepository;

import java.util.ArrayList;

public class CategoryAdapterSelected extends RecyclerView.Adapter<CategoryAdapterSelected.ViewHolder> {


    public static ArrayList<CategoryModel> categoryModelsFull = new ArrayList<>();
    ArrayList<CategoryModel> categoryModels;

    public CategoryAdapterSelected(ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryAdapterSelected.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_selected_category, parent, false);
        return new CategoryAdapterSelected.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterSelected.ViewHolder holder, int position) {
        holder.txtName.setText(categoryModels.get(position).getName());
        holder.aSwitch.setOnCheckedChangeListener((compoundButton, b) -> {

            try {
                if (b) {
                    CategoryAdapterSelected.categoryModelsFull.remove(categoryModels.get(position));
                }else {
                    categoryModelsFull.add(categoryModels.get(position));
                }
            } catch (Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        Switch aSwitch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCateName);
            aSwitch = itemView.findViewById(R.id.SCate);
        }
    }
}
