package com.example.foodshop.UI.Details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.foodshop.R;

public class DetailActivity extends AppCompatActivity {

    private TextView detailId, detailName, detailPrice, detailCategory, detailDescription, detailDate;
    private int groceryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailId = findViewById(R.id.detailId);
        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);
        detailCategory = findViewById(R.id.detailCategory);
        detailDescription = findViewById(R.id.detailDescription);
        detailDate = findViewById(R.id.detailDate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailId.setText(bundle.getString("item_id"));
            detailName.setText(bundle.getString("name"));
            detailPrice.setText(bundle.getString("price"));
            detailCategory.setText(bundle.getString("category"));
            detailDescription.setText(bundle.getString("description"));
            detailDate.setText(bundle.getString("date"));

            groceryId = bundle.getInt("id");
        }


    }
}