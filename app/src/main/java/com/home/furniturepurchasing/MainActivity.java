package com.home.furniturepurchasing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseOperation db = new DatabaseOperation(this);
    private List<ProductCategory> productCategories;
    private List<Product> products;
    private String[] items;
    private ProductAdapter adapter;
    private Spinner spinner;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productCategories = db.getAllProductCategories();
        products = db.getAllProducts(1);
        if (productCategories.size() == 0) {
            Toast.makeText(MainActivity.this, "No product category found", Toast.LENGTH_SHORT).show();
        }

        spinner = findViewById(R.id.spinner);
        items = new String[]{"Price Ascending", "Price Descending"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (items[i] == "Price Ascending") {
                    products = db.getAllProducts(1);
                } else {
                    products = db.getAllProducts(2);
                }
                adapter = new ProductAdapter(MainActivity.this, products);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ProductAdapter(this, products);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(adapter);
    }
}