package com.ilddang.job.retrofit;

import com.google.gson.annotations.SerializedName;

public class JoinResult {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String data;

    @SerializedName("hint")
    private String hint;

    @SerializedName("error")
    private String error;

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "JoinResult{" +
                "success=" + success +
                ", message=" + message +
                ", data='" + data + '\'' +
                ", hint='" + hint + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
