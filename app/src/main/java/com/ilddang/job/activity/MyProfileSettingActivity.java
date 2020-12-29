package com.ilddang.job.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.ilddang.job.R;

public class MyProfileSettingActivity extends BaseActivity {
    private ImageView mProfileImage;
    private TextView mProfileName;
    private SwitchCompat mPushNotiSwitch;
    private TextView mPushNotiText;
    private SwitchCompat mAutoLoginSwitch;
    private TextView mAutoLoginText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_setting_activity);
        createActionBar(getResources().getString(R.string.my_info_setting));

        mProfileImage = findViewById(R.id.profile_image);
        mProfileName = findViewById(R.id.profile_name);

        mProfileName.setText("슈퍼맨");
        Glide.with(MyProfileSettingActivity.this)
                .load(R.drawable.default_profile_image)
                .circleCrop()
                .into(mProfileImage);

        mPushNotiSwitch = findViewById(R.id.switch_push_notofication);
        mPushNotiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                setChecked(mPushNotiText, checked);
            }
        });
        mPushNotiText = findViewById(R.id.switch_push_notification_text);
        setChecked(mPushNotiText, mPushNotiSwitch.isChecked());

        mAutoLoginSwitch = findViewById(R.id.switch_auto_login);
        mAutoLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                setChecked(mAutoLoginText, checked);
            }
        });
        mAutoLoginText = findViewById(R.id.switch_auto_login_text);
        setChecked(mAutoLoginText, mAutoLoginSwitch.isChecked());

    }

    private void setChecked(TextView view, boolean isChecked) {
        if (isChecked) {
            view.setText(getResources().getString(R.string.switch_on));
            view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else {
            view.setText(getResources().getString(R.string.switch_off));
            view.setBackgroundColor(getResources().getColor(R.color.base_grey));
        }
    }

    public void onButtonClick(View view) {
        if (view.getId() == R.id.change_phone_number) {
            Intent intent = new Intent(this, CreateOrFindAccountActivity.class);
            intent.putExtra("intent_email_or_password_or_join_or_phone", "phone");
            startActivity(intent);
        } else if (view.getId() == R.id.my_ilddang_layout) {

        }

    }
}
