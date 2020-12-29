package com.ilddang.job.util;

public class Constants {
    public static final String SERVER_URL = "http://117.52.20.138:3000/";

    public interface CurrentStatus {
        public String RECRUITING = "모집중";
        public String CLOSED = "마감";
        public String SCHEDULED = "예정";
        public String CANCELED = "취소";
        public String COMPLETE = "완료";
        public String ABSENCE = "불참";
    }

    public interface ChatType {
        public int MY_CHAT = 0;
        public int YOUR_CHAT = 1;
        public int MY_CHAT_REQUEST_JOB = 2;
        public int CHAT_DESCRIPTION = 3;
    }
}
