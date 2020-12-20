package com.ilddang.util;

import com.ilddang.R;

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
}
