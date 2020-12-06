package com.ilddang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilddang.R;

public class CreateOrFindAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_find_accont);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("intent_email_or_password_or_join");

        if (extra.equals("email")) {
            setTitle(getResources().getString(R.string.find_email));
        } else if (extra.equals("password")) {
            setTitle(getResources().getString(R.string.find_password));
            EditText emailEditText = findViewById(R.id.email_edit_text);
            emailEditText.setVisibility(View.VISIBLE);
        } else if (extra.equals("join")) {
            setTitle(getResources().getString(R.string.join));
            LinearLayout layout = findViewById(R.id.join_password_layout);
            layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu) ;

        return true ;
    }
}
