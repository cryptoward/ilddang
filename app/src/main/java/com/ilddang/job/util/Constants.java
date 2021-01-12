package com.ilddang.job.util;

public class Constants {
    public static final String SERVER_URL = "http://117.52.20.138:3000/";

    public interface CurrentStatus {
        String RECRUITING = "모집중";
        String CLOSED = "마감";
        String SCHEDULED = "예정";
        String CANCELED = "취소";
        String COMPLETE = "완료";
        String ABSENCE = "불참";
    }

    public interface ChatType {
        int MY_CHAT = 0;
        int YOUR_CHAT = 1;
        int MY_CHAT_REQUEST_JOB = 2;
        int CHAT_DESCRIPTION = 3;
    }
}
