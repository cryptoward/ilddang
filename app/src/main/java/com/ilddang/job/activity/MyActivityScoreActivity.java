package com.ilddang.job.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ilddang.job.R;

public class MyActivityScoreActivity extends  BaseActivity {
    private ImageView mProfileImage;
    private TextView mProfileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_score_activity);
        createActionBar(getResources().getString(R.string.my_activity_score));


    }
}
