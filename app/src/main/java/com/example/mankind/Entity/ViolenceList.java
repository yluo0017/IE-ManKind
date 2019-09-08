package com.example.mankind.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ViolenceList implements Parcelable {
    private List<String> violenceType;

    public ViolenceList() {
    }

    protected ViolenceList(Parcel in) {
        violenceType = in.createStringArrayList();
    }

    public static final Creator<ViolenceList> CREATOR = new Creator<ViolenceList>() {
        @Override
        public ViolenceList createFromParcel(Parcel in) {
            return new ViolenceList(in);
        }

        @Override
        public ViolenceList[] newArray(int size) {
            return new ViolenceList[size];
        }
    };

    public List<String> getViolenceType() {
        return violenceType;
    }

    public void setViolenceType(List<String> violenceType) {
        this.violenceType = violenceType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(violenceType);
    }
}
