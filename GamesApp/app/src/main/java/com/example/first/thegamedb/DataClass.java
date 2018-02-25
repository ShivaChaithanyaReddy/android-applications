package com.example.first.thegamedb;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vandita on 2/20/17.
 */

public class DataClass implements Parcelable {

    String title, summary, releaseDate, smallImage, largeImage, heightsmall, heightlarge;
    Bitmap bitmapsmall;
    boolean makeIsGreen=false;

    public boolean isMakeIsGreen() {
        return makeIsGreen;
    }

    public void setMakeIsGreen(boolean makeIsGreen) {
        this.makeIsGreen = makeIsGreen;
    }
    public DataClass(){}


    public Bitmap getBitmapsmall() {
        return bitmapsmall;
    }

    public void setBitmapsmall(Bitmap bitmapsmall) {
        this.bitmapsmall = bitmapsmall;
    }

    public String getHeightsmall() {
        return heightsmall;
    }

    public void setHeightsmall(String heightsmall) {
        this.heightsmall = heightsmall;
    }

    public String getHeightlarge() {
        return heightlarge;
    }

    public void setHeightlarge(String heightlarge) {
        this.heightlarge = heightlarge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    @Override
    public String toString() {
        return "DataClass{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", smallImage='" + smallImage + '\'' +
                ", largeImage='" + largeImage + '\'' +
                ", heightsmall='" + heightsmall + '\'' +
                ", heightlarge='" + heightlarge + '\'' +
                ", bitmapsmall=" + bitmapsmall +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(summary);
        parcel.writeString(releaseDate);
        parcel.writeString(smallImage);
        parcel.writeString(largeImage);


    }
    public static final Creator<DataClass> CREATOR = new Creator<DataClass>() {
        public DataClass createFromParcel(Parcel in) {
            return new DataClass(in);
        }

        public DataClass[] newArray(int size) {
            return new DataClass[size];
        }
    };
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    private DataClass(Parcel in)
    {
        this.title=in.readString();
        this.summary=in.readString();
        this.releaseDate=in.readString();
        this.smallImage=in.readString();
        this.largeImage=in.readString();

    }
}
