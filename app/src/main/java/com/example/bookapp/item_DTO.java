package com.example.bookapp;

import android.net.Uri;

import com.google.firebase.storage.StorageMetadata;

public class item_DTO {

    private String title, description;
    //  private Uri data;


    public item_DTO() {
    }

    public item_DTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}