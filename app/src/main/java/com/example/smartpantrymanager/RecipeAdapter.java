package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {
    private Context context;
    private Cursor cursor;

    public RecipeAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recipe_row, parent, false);
        return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndexOrThrow("recipe_name"));
        String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

        holder.nameLabel.setText(name);
        holder.instructionsLabel.setText(instructions);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("RECIPE_ID", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    class RecipeHolder extends RecyclerView.ViewHolder {
        TextView nameLabel;
        TextView instructionsLabel;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            nameLabel = itemView.findViewById(R.id.tvRecipeName);
            instructionsLabel = itemView.findViewById(R.id.tvRecipeInstructions);
        }
    }
}