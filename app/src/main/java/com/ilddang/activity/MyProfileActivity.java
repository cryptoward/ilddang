package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.ilddang.R;

public class MyProfileActivity extends BaseActivity {
    private ImageView mProfileImage;
    private TextView mProfileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_activity);
        setTitle(getResources().getString(R.string.my_info));

        mProfileImage = findViewById(R.id.profile_image);
        mProfileName = findViewById(R.id.profile_name);

        mProfileName.setText("슈퍼맨");
        Glide.with(MyProfileActivity.this)
                .load(R.drawable.default_profile_image)
                .circleCrop()
                .into(mProfileImage);
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.my_job_list_layout) {
            Intent intent = new Intent(this, MyJobListActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.my_ilddang_layout) {

        } else if (view.getId() == R.id.my_activity_score_layout) {

        }

    }

}
