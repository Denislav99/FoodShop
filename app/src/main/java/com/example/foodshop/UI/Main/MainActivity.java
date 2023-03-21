package com.example.foodshop.UI.Main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodshop.DataBase.DatabaseHandler;
import com.example.foodshop.Model.Grocery;
import com.example.foodshop.R;
import com.example.foodshop.UI.ShopCart.ShopCartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private FloatingActionButton fab;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabList;
    private FloatingActionButton fabShopCart;

    private TextView tvAddProduct;
    private TextView tvListOfProducts;
    private TextView tvShopCart;

    private boolean clicked = false;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText groceryItemID, groceryItemName, groceryItemPrice, groceryItemCategory, groceryItemDescription,
            groceryCategorySelect;
    private Button saveButton, saveSelectButton;

    private DatabaseHandler db;

    private RecyclerView recyclerView;

    private RecyclerViewMainAdapter recyclerViewAdapter;

    private List<Grocery> groceryList;

    private List<Grocery> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_botton_anim);

        fab = findViewById(R.id.fab);
        fabAdd = findViewById(R.id.fabAdd);
        fabList = findViewById(R.id.fabList);
        fabShopCart = findViewById(R.id.fabShopCart);

        tvAddProduct = findViewById(R.id.tvAddProduct);
        tvListOfProducts = findViewById(R.id.tvListOfProducts);
        tvShopCart = findViewById(R.id.tvShopCart);

        fab.setOnClickListener(view -> onfabButtonClick());
        fabAdd.setOnClickListener(view -> createAddDialog());
        fabList.setOnClickListener(view -> createSelectDialog());
        fabShopCart.setOnClickListener(view -> createShopCart());

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        groceryList = db.getAllGrocery();
        for (Grocery grc : groceryList) {
            Grocery grocery = new Grocery();
            grocery.setId(grc.getId());
            grocery.setItemID(grc.getItemID());
            grocery.setName(grc.getName());
            grocery.setPrice((grc.getPrice()));
            grocery.setCategory(grc.getCategory());
            grocery.setDescription(grc.getDescription());
            grocery.setDate(grc.getDate());

            listItems.add(grocery);
        }

        recyclerViewAdapter = new RecyclerViewMainAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private void onfabButtonClick() {
        setVisibility();
        setAnimation();
        clicked = !clicked;
    }

    private void setVisibility() {
        if (!this.clicked) {
            fabAdd.setVisibility(View.VISIBLE);
            fabList.setVisibility(View.VISIBLE);
            fabShopCart.setVisibility(View.VISIBLE);

            tvAddProduct.setVisibility(View.VISIBLE);
            tvListOfProducts.setVisibility(View.VISIBLE);
            tvShopCart.setVisibility(View.VISIBLE);
        } else {
            fabAdd.setVisibility(View.INVISIBLE);
            fabList.setVisibility(View.INVISIBLE);
            fabShopCart.setVisibility(View.INVISIBLE);

            tvAddProduct.setVisibility(View.INVISIBLE);
            tvListOfProducts.setVisibility(View.INVISIBLE);
            tvShopCart.setVisibility(View.INVISIBLE);
        }
    }

    private void setAnimation() {
        if (!this.clicked) {
            fabAdd.startAnimation(fromBottom);
            fabList.startAnimation(fromBottom);
            fabShopCart.startAnimation(fromBottom);

            tvAddProduct.startAnimation(fromBottom);
            tvListOfProducts.startAnimation(fromBottom);
            tvShopCart.startAnimation(fromBottom);

            fab.startAnimation(rotateOpen);
        } else {
            fabAdd.startAnimation(toBottom);
            fabList.startAnimation(toBottom);
            fabShopCart.startAnimation(toBottom);

            tvAddProduct.startAnimation(toBottom);
            tvListOfProducts.startAnimation(toBottom);
            tvShopCart.startAnimation(toBottom);

            fab.startAnimation(rotateClose);
        }
    }

    private void createAddDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_pop_up_add_item, null);
        groceryItemID = view.findViewById(R.id.etItemID);
        groceryItemName = view.findViewById(R.id.etItemName);
        groceryItemPrice = view.findViewById(R.id.etPrice);
        groceryItemCategory = view.findViewById(R.id.etCategory);
        groceryItemDescription = view.findViewById(R.id.etDescription);
        saveButton = view.findViewById(R.id.saveButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItemName.getText().toString().isEmpty() &&
                        !groceryItemID.getText().toString().isEmpty() &&
                        !groceryItemPrice.getText().toString().isEmpty() &&
                        !groceryItemCategory.getText().toString().isEmpty() &&
                        !groceryItemDescription.getText().toString().isEmpty()) {
                    List<String> idList = db.getAllIds();
                    if (!idList.contains(groceryItemID.getText().toString())) {
                        saveItem(v);
                    } else
                        Toast.makeText(getApplicationContext(), "Вече съществува артикул с такъв идентификатор", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Моля въведете всички полета!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveItem(View v) {
        Grocery grocery = new Grocery();

        String newGroceryID = groceryItemID.getText().toString();
        String newGroceryName = groceryItemName.getText().toString();
        String newGroceryPrice = groceryItemPrice.getText().toString();
        String newGroceryCategory = groceryItemCategory.getText().toString();
        String newGroceryDescription = groceryItemDescription.getText().toString();

        grocery.setItemID(newGroceryID);
        grocery.setName(newGroceryName);
        grocery.setPrice(newGroceryPrice);
        grocery.setCategory(newGroceryCategory);
        grocery.setDescription(newGroceryDescription);

        String date = new SimpleDateFormat("dd.MM.yyyy г.", Locale.getDefault()).format(new Date());
        grocery.setDate(date);

        db.addGrocery(grocery);
        Snackbar.make(v, "Успешно запазване!", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
        listItems.add(grocery);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void createSelectDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View viewSelect = getLayoutInflater().inflate(R.layout.activity_pop_up_select_item, null);
        groceryCategorySelect = viewSelect.findViewById(R.id.etSelectCategory);
        saveSelectButton = viewSelect.findViewById(R.id.saveSelectButton);

        dialogBuilder.setView(viewSelect);
        dialog = dialogBuilder.create();
        dialog.show();

        saveSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGroceryCategorySelect = groceryCategorySelect.getText().toString();
                Toast.makeText(getApplicationContext(), db.selectCategory(newGroceryCategorySelect), Toast.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
            }
        });

    }

    private void createShopCart() {
        startActivity(new Intent(MainActivity.this, ShopCartActivity.class));
    }
}