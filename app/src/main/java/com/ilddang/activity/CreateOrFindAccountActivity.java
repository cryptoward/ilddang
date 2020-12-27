package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilddang.R;

public class CreateOrFindAccountActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_find_accont);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("intent_email_or_password_or_join_or_phone");

        if (extra.equals("email")) {
            createActionBar(getResources().getString(R.string.find_email));
        } else if (extra.equals("password")) {
            createActionBar(getResources().getString(R.string.find_password));
            EditText emailEditText = findViewById(R.id.email_edit_text);
            emailEditText.setVisibility(View.VISIBLE);
        } else if (extra.equals("join")) {
            createActionBar(getResources().getString(R.string.join));
            LinearLayout layout = findViewById(R.id.join_password_layout);
            layout.setVisibility(View.VISIBLE);
        } else if (extra.equals("phone")) {
            createActionBar(getResources().getString(R.string.change_phone_number));
            Button changePhone = findViewById(R.id.join_button);
            changePhone.setText(getResources().getString(R.string.change_phone_number));
            EditText name = findViewById(R.id.name_edit_text);
            //TODO: get name
            name.setText("홍길동");
            name.setEnabled(false);
            name.setInputType(InputType.TYPE_NULL);
        }

    }
}
