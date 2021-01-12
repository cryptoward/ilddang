package com.ilddang.job.util;

import android.content.Context;

import com.ilddang.job.R;

public class Util {
    public static int getStatusColor(String currentStatus) {
        switch (currentStatus) {
            case Constants.CurrentStatus.RECRUITING:
                return R.color.base_green;
            case Constants.CurrentStatus.CLOSED:
                return R.color.base_grey;
            case Constants.CurrentStatus.SCHEDULED:
                return R.color.base_green;
            case Constants.CurrentStatus.CANCELED:
                return R.color.base_grey;
            case Constants.CurrentStatus.COMPLETE:
                return R.color.base_blue;
            case Constants.CurrentStatus.ABSENCE:
                return R.color.base_yellow;
            default:
                return R.color.base_green;
        }
    }

    public static String getDevicesId(Context context){
        String androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
