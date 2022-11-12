package com.home.furniturepurchasing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseOperation db = new DatabaseOperation(this);
    private List<ProductCategory> productCategories;
    private List<Product> products;
    private String[] sortByItems;
    private String[] categoriesItems;
    private int selectedSortBy;
    private int selectedCategoryId;
    private ProductAdapter adapter;
    private Spinner spinnerSortBy;
    private Spinner spinnerCategories;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productCategories = db.getAllProductCategories();
        selectedSortBy = 3;
        selectedCategoryId = -1;
        products = db.getAllProducts(selectedSortBy, selectedCategoryId);
        if (productCategories.size() == 0) {
            Toast.makeText(MainActivity.this, "No product category found", Toast.LENGTH_SHORT).show();
        }

        spinnerSortBy = findViewById(R.id.spinner);
        sortByItems = new String[]{"Product Name Ascending", "Product Name Descending",
                "Price Ascending", "Price Descending"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortByItems);
        spinnerSortBy.setAdapter(arrayAdapter);
        spinnerSortBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sortByItems[i] == "Price Ascending") {
                    selectedSortBy = 1;
                } else if (sortByItems[i] == "Price Descending") {
                    selectedSortBy = 2;
                } else if (sortByItems[i] == "Product Name Ascending") {
                    selectedSortBy = 3;
                } else if (sortByItems[i] == "Product Name Descending") {
                    selectedSortBy = 4;
                }
                products = db.getAllProducts(selectedSortBy, selectedCategoryId);
                adapter = new ProductAdapter(MainActivity.this, products);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCategories = findViewById(R.id.spinner_categories);
        categoriesItems = new String[]{"All", "Office Chairs", "Office Tables"};
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoriesItems);
        spinnerCategories.setAdapter(arrayAdapter2);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (categoriesItems[i] == "All") {
                    selectedCategoryId = -1;
                } else if (categoriesItems[i] == "Office Chairs") {
                    selectedCategoryId = 1;
                } else if (categoriesItems[i] == "Office Tables") {
                    selectedCategoryId = 2;
                }
                products = db.getAllProducts(selectedSortBy, selectedCategoryId);
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