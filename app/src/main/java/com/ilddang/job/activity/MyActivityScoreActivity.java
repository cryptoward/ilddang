package com.ilddang.job.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ilddang.job.R;

public class MyActivityScoreActivity extends  BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_score_activity);
        createActionBar(getResources().getString(R.string.my_activity_score));

        setScore();

    }

    private void setScore() {
        //TODO: get data
        ((TextView) findViewById(R.id.technique_score)).setText("6");
        ((TextView) findViewById(R.id.sincerity_score)).setText("5");
        ((TextView) findViewById(R.id.cowork_score)).setText("2");
        ((TextView) findViewById(R.id.consistency_score)).setText("3");
        ((TextView) findViewById(R.id.promise_score)).setText("7");
        ((TextView) findViewById(R.id.promise_bad_score)).setText("6");
        ((TextView) findViewById(R.id.cowork_bad_score)).setText("2");
        ((TextView) findViewById(R.id.sincerity_bad_score)).setText("3");


    }
}
