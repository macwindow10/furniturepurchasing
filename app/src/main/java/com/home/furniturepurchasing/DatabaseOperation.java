package com.home.furniturepurchasing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation extends SQLiteOpenHelper {

    public DatabaseOperation(@Nullable Context context) {
        super(context, "db_furniture", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(id integer primary key AUTOINCREMENT, username varchar(250), useremail varchar(250), userpass varchar(250))");

        db.execSQL("create table productcategories(id integer primary key AUTOINCREMENT, name varchar(250))");

        db.execSQL("create table products(id integer primary key AUTOINCREMENT , category_id integer, name varchar(250), price integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean registerUser(String uname, String uemail, String upass) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("insert into users (username, useremail, userpass) values ('" + uname + "','" + uemail + "','" + upass + "')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor login(String uname, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from users where useremail='" + uname + "' AND userpass='" + password + "'", null);
        return cursor;
    }

    public boolean addProductCategory(String name) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("insert into productcategories (name) values ('" + name + "')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ProductCategory> getAllProductCategories() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from productcategories", null);
        List<ProductCategory> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.id = cursor.getInt(0);
            productCategory.name = cursor.getString(1);
            list.add(productCategory);
        }
        return list;
    }

    public boolean addProduct(int categoryId, String name, int price) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("insert into products (category_id, name, price) values ('" + categoryId + "' , '" + name + "' , " + price + ")");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Product> getAllProducts(int sortByPrice) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (sortByPrice == 1)
            cursor = db.rawQuery("select * from products order by price asc", null);
        else
            cursor = db.rawQuery("select * from products order by price desc", null);

        List<Product> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Product product = new Product();
            product.id = cursor.getInt(0);
            product.categoryId = cursor.getInt(1);
            product.name = cursor.getString(2);
            product.price = cursor.getInt(3);
            list.add(product);
        }
        return list;
    }
}