package com.home.furniturepurchasing;

import android.content.Context;

public class Product {
    public int id;
    public String name;
    public int categoryId;
    public double price;

    public String getCategoryName() {
        switch (categoryId) {
            case 1:
                return "Office Chairs";
            case 2:
                return "Office Tables";
            case 3:
                return "Dining Tables";
            case 4:
                return "Sofas";
        }
        return "Misc";
    }
}
