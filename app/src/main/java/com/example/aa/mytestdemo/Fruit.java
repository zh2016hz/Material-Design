package com.example.aa.mytestdemo;

/**
 * Created by aa on 17/3/27.
 */

public class Fruit {
    private String fruitName;
    private int imageId;

    public Fruit(String name, int image) {
        this.fruitName = name;
        this.imageId = image;
    }

    public String getFruitName() {
        return fruitName;
    }

    public int getImageId() {
        return imageId;
    }
}
