package com.ilddang.job.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ilddang.job.R;

public class TncPpActivity extends BaseActivity {
    private final String TAG = TncPpActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tnc_pp_activity);

        TextView tnc = findViewById(R.id.terms_and_condition);
        tnc.setText("이용약관\n");
        TextView pp = findViewById(R.id.privacy_policy);
        pp.setText("이용약관\n");

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TncPpActivity.this, CreateOrFindAccountActivity.class);
                intent.putExtra("intent_email_or_password_or_join_or_phone", "join");
                startActivity(intent);
            }
        });
    }
}
