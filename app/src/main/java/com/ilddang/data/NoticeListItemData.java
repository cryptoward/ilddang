package com.ilddang.data;

public class NoticeListItemData {
    public String title;
    public String content;
    public int peopleNumber;
    public String distance;
    public String payment;
    public String workPeriod;
    public int currentStatus;

    public NoticeListItemData(String title, String content, int peopleNumber, String distance, String payment, String workPeriod, int currentStatus) {
        this.title = title;
        this.content = content;
        this.peopleNumber = peopleNumber;
        this.distance = distance;
        this.payment = payment;
        this.workPeriod = workPeriod;
        this.currentStatus = currentStatus;
    }
}
