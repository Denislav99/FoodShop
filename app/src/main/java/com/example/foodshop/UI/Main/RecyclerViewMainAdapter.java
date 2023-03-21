package com.example.foodshop.UI.Main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodshop.R;
import com.example.foodshop.UI.Details.DetailActivity;
import com.example.foodshop.DataBase.DatabaseHandler;
import com.example.foodshop.Model.Grocery;

import java.util.List;

public class RecyclerViewMainAdapter extends RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryList;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public RecyclerViewMainAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public RecyclerViewMainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gorecery_list_cell, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMainAdapter.ViewHolder holder, int position) {
        Grocery grocery = groceryList.get(position);

        holder.cellId.setText(grocery.getItemID());
        holder.cellName.setText(grocery.getName());
        holder.cellPrice.setText(grocery.getPrice());
        holder.cellCategory.setText(grocery.getCategory());
        holder.cellDescription.setText(grocery.getDescription());
        holder.cellDate.setText(grocery.getDate());
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView cellId, cellName, cellPrice, cellCategory, cellDescription, cellDate,
        tvCellId, tvCellName, tvCellPrice, tvCellCategory, tvCellDescription, tvCellDate;
        public Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            cellId = itemView.findViewById(R.id.cellId);
            cellName = itemView.findViewById(R.id.cellName);
            cellPrice = itemView.findViewById(R.id.cellPrice);
            cellCategory = itemView.findViewById(R.id.cellCategory);
            cellDescription = itemView.findViewById(R.id.cellDescription);
            cellDate = itemView.findViewById(R.id.cellDate);

            tvCellId = itemView.findViewById(R.id.tvCellId);
            tvCellName = itemView.findViewById(R.id.tvCellName);
            tvCellPrice = itemView.findViewById(R.id.tvCellPrice);
            tvCellCategory = itemView.findViewById(R.id.tvCellCategory);
            tvCellDescription = itemView.findViewById(R.id.tvCellDescription);
            tvCellDate = itemView.findViewById(R.id.tvCellDate);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Grocery grocery = groceryList.get(position);
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", grocery.getId());
                    intent.putExtra("item_id", grocery.getItemID());
                    intent.putExtra("name", grocery.getName());
                    intent.putExtra("price", grocery.getPrice());
                    intent.putExtra("category", grocery.getCategory());
                    intent.putExtra("description", grocery.getDescription());
                    intent.putExtra("date", grocery.getDate());

                    context.startActivity(intent);

                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.editButton:
                    int position = getAdapterPosition();
                    Grocery grocery = groceryList.get(position);
                    editItem(grocery);
                    break;
                case R.id.deleteButton:
                    position = getAdapterPosition();
                    grocery = groceryList.get(position);
                    deleteItem(grocery.getId());
                    break;
                case R.id.addToCartButton:
                    position = getAdapterPosition();
                    grocery = groceryList.get(position);
                    //addToCart(grocery.getId());
                    break;
            }
        }

        private void editItem(final Grocery grocery) {
            dialogBuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.activity_pop_up_add_item, null);

            final EditText itemID = view.findViewById(R.id.etItemID);
            final EditText itemName = view.findViewById(R.id.etItemName);
            final EditText price = view.findViewById(R.id.etPrice);
            final EditText category = view.findViewById(R.id.etCategory);
            final EditText description = view.findViewById(R.id.etDescription);

            final TextView title = view.findViewById(R.id.title);

            title.setText(R.string.editItem);

            Button saveButton = view.findViewById(R.id.saveButton);

            dialogBuilder.setView(view);
            dialog = dialogBuilder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);

                    grocery.setItemID(itemID.getText().toString());
                    grocery.setName(itemName.getText().toString());
                    grocery.setPrice(price.getText().toString());
                    grocery.setCategory(category.getText().toString());
                    grocery.setDescription(description.getText().toString());

                    db.updateGrocery(grocery);
                    notifyItemChanged(getAdapterPosition(), grocery);
                    dialog.dismiss();
                }
            });
        }

        private void deleteItem(final int id) {
            dialogBuilder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.activity_confirmation_dialog, null);

            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            dialogBuilder.setView(view);
            dialog = dialogBuilder.create();
            dialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteGrocery(id);
                    groceryList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();
                }
            });
        }

        //TODO:
        //функция за добавяне на избраните от нас артикули в количката.
        private void addToCart(final int id) {
            //addToShopCart()
        }
    }
}
