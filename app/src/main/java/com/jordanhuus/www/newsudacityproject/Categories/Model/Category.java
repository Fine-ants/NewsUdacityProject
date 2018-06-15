package com.jordanhuus.www.newsudacityproject.Categories.Model;

/**
 * Created by jordanhuus on 6/3/2018.
 */

public class Category {
    private String categoryApiTag;
    private String categoryTitle;
    private int categoryIconResourceId;

    public Category(String categoryApiTag, String categoryTitle, int categoryIconResourceId) {
        this.categoryApiTag = categoryApiTag;
        this.categoryTitle = categoryTitle;
        this.categoryIconResourceId = categoryIconResourceId;
    }


    public String getCategoryApiTag() {
        return categoryApiTag;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public int getCategoryIconResourceId() {
        return categoryIconResourceId;
    }
}
