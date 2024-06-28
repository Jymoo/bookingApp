package com.example.bookapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable {
    private final String imageUrl;
    private String filePath;

    public ImageModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected ImageModel(Parcel in) {
        imageUrl = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Parcelable.Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };



    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
