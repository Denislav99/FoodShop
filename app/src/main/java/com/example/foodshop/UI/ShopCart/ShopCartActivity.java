package com.example.foodshop.UI.ShopCart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodshop.DataBase.DatabaseHandler;
import com.example.foodshop.Model.Grocery;
import com.example.foodshop.R;
import com.example.foodshop.UI.Main.MainActivity;
import com.example.foodshop.UI.Main.RecyclerViewMainAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopCartActivity extends AppCompatActivity {

    private EditText personName, personFamily, personPhone, personBD, personEmail;
    private Button finishButton, finishPersonButton;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private RecyclerViewCartAdapter recyclerViewCartAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        finishButton = findViewById(R.id.finishButton);

        finishButton.setOnClickListener(view -> createPersonDialog());

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewShopID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        //TODO:
        //Програмата ще работи правилно когато данните се взимат от 2та таблица свързана за първата,
        //която сме напълнили чрез натискането на бутона за добавяне в количката.
        //getFromShopCart()
        groceryList = db.getAllGrocery();
        for (Grocery grc : groceryList) {
            Grocery grocery = new Grocery();
            grocery.setId(grc.getId());
            grocery.setName(grc.getName());
            grocery.setPrice((grc.getPrice()));

            listItems.add(grocery);
        }

        recyclerViewCartAdapter = new RecyclerViewCartAdapter(this, listItems);
        recyclerView.setAdapter(recyclerViewCartAdapter);
        recyclerViewCartAdapter.notifyDataSetChanged();
    }

    private void createPersonDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.activity_pop_up_person, null);
        personName = view.findViewById(R.id.etPersonName);
        personFamily = view.findViewById(R.id.etPersonFamily);
        personPhone = view.findViewById(R.id.etPersonPhone);
        personBD = view.findViewById(R.id.etPersonBD);
        personEmail = view.findViewById(R.id.etPersonEmail);
        finishPersonButton = view.findViewById(R.id.finsihPersonButton);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        finishPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Успешна поръчка!", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        dialog.dismiss();
                        startActivity(new Intent(ShopCartActivity.this, MainActivity.class));
                    }
                }, 1000);
            }
        });
    }
}