package com.example.smartpantrymanager;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.IngredientHolder> {
    private Context context;
    private Cursor cursor;
    private PantryDb dbHelper;

    public PantryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        this.dbHelper = new PantryDb(context);
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_ingredient, parent, false);
        return new IngredientHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        final int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        final String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        final double qty = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
        final String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
        final String exp = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

        holder.nameLabel.setText(name);
        holder.quantityLabel.setText(qty + " " + unit);

        if (exp != null && !exp.trim().isEmpty()) {
            holder.expiryLabel.setText("Exp: " + exp);
        } else {
            holder.expiryLabel.setText("");
        }

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleted = dbHelper.deleteIngredient(id);
                if (deleted) {
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    if (context instanceof MainActivity) {
                        Cursor newCursor = dbHelper.getInventory();
                        ((MainActivity) context).adapter.swapCursor(newCursor);
                    }
                }
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

    class IngredientHolder extends RecyclerView.ViewHolder {
        TextView nameLabel;
        TextView quantityLabel;
        TextView expiryLabel;
        ImageView deleteBtn;

        public IngredientHolder(@NonNull View itemView) {
            super(itemView);
            nameLabel = itemView.findViewById(R.id.tvIngredientName);
            quantityLabel = itemView.findViewById(R.id.tvIngredientQuantity);
            expiryLabel = itemView.findViewById(R.id.tvIngredientExpiry);
            deleteBtn = itemView.findViewById(R.id.btnDeleteIngredient);
        }
    }
}