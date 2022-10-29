package com.home.furniturepurchasing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextView goregister;
    TextInputEditText uemail, upass;
    Button btn_signin;
    DatabaseOperation db = new DatabaseOperation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        goregister = findViewById(R.id.goregister);
        uemail = findViewById(R.id.uemail);
        upass = findViewById(R.id.upass);
        btn_signin = findViewById(R.id.btn_signin);
        final SharedPreferences shared = getSharedPreferences("credientials", MODE_PRIVATE);
        final SharedPreferences.Editor edit = shared.edit();

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cus = db.login(uemail.getText().toString(), upass.getText().toString());
                if (cus.getCount() == 1) {
                    Toast.makeText(LoginActivity.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                    while (cus.moveToNext()) {
                        edit.putString("uname", cus.getString(1));
                        edit.putString("uemail", cus.getString(2));
                        edit.putString("upass", cus.getString(3));
                        edit.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login UnSuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // test
        if (db.getAllProductCategories().size() == 0) {
            db.addProductCategory("Office Chairs");
            db.addProductCategory("Office Tables");
            db.addProductCategory("Dining Tables");
            db.addProductCategory("Sofas");

            db.addProduct(1, "Relaxing Chair", 4000);
            db.addProduct(1, "Revolving Chair", 2600);
            db.addProduct(1, "Normal Chair", 2000);
            db.addProduct(1, "Massaging Chair", 3000);

            db.addProduct(2, "Normal Table", 1500);
            db.addProduct(2, "Computer Table", 3000);
            db.addProduct(2, "Office Table", 2200);
        }
    }
}