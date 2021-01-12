package com.ilddang.job.retrofit;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseResult {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    @SerializedName("hint")
    private String hint;

    @SerializedName("error")
    private String error;

    @SerializedName("router")
    private String router;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public String getRouter() {
        return router;
    }

    public String getHint() {
        return hint;
    }

    public Object getData() {
        return data;
    }

    public class Data {
        @SerializedName("ColumnDataTypes")
        private Object dataType;

        @SerializedName("ColumnNames")
        private Object authVal;

        @SerializedName("ColumnValues")
        private Object columnVal;

        @SerializedName("RowsCount")
        private int rowsCount;

        public Object getColumnValues() {
            return columnVal;
        }
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "success=" + success +
                ", message=" + message +
                ", hint='" + hint + '\'' +
                ", error='" + error + '\'' +
                ", router='" + router + '\'' +
                '}';
    }
}
