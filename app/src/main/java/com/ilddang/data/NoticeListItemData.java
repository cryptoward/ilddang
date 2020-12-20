package com.ilddang.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NoticeListItemData implements Parcelable {
    public String title;
    public String content;
    public int peopleNumber;
    public String distance;
    public String payment;
    public String workPeriod;
    public String currentStatus;

    public NoticeListItemData(String title, String content, int peopleNumber, String distance, String payment, String workPeriod, String currentStatus) {
        this.title = title;
        this.content = content;
        this.peopleNumber = peopleNumber;
        this.distance = distance;
        this.payment = payment;
        this.workPeriod = workPeriod;
        this.currentStatus = currentStatus;
    }

    protected NoticeListItemData(Parcel in) {
        title = in.readString();
        content = in.readString();
        peopleNumber = in.readInt();
        distance = in.readString();
        payment = in.readString();
        workPeriod = in.readString();
        currentStatus = in.readString();
    }

    public static final Creator<NoticeListItemData> CREATOR = new Creator<NoticeListItemData>() {
        @Override
        public NoticeListItemData createFromParcel(Parcel in) {
            return new NoticeListItemData(in);
        }

        @Override
        public NoticeListItemData[] newArray(int size) {
            return new NoticeListItemData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeInt(peopleNumber);
        parcel.writeString(distance);
        parcel.writeString(payment);
        parcel.writeString(workPeriod);
        parcel.writeString(currentStatus);
    }
}
