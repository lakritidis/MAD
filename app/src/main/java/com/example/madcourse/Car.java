package com.example.madcourse;

public class Car {
    private int id;
    private String name;
    private String brand;
    private float price;

    public Car(int i, String n, String b, float p) {
        id = i;
        name = n;
        brand = b;
        price = p;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public void setId(int v) { id = v;  }
    public void setName(String v) { name = v;  }
    public void setBrand(String v) { brand = v;  }
    public void setPrice(float v) { price = v;  }
}
