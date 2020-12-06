package com.ilddang.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ilddang.R;

public class MainActivity extends AppCompatActivity {
    private Button mFindIdPassword;
    private Button mJoinBtn;
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFindIdPassword = findViewById(R.id.find_id_password);
        mFindIdPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.base_dialog_fragment, null);
                builder.setView(view);
                final Button email = (Button) view.findViewById(R.id.dialog_first_button);
                final Button password = (Button) view.findViewById(R.id.dialog_second_button);
                email.setText(getResources().getString(R.string.email));
                password.setText(getResources().getString(R.string.password));

                final AlertDialog dialog = builder.create();
                email.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CreateOrFindAccountActivity.class);
                        intent.putExtra("intent_email_or_password_or_join", "email");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CreateOrFindAccountActivity.class);
                        intent.putExtra("intent_email_or_password_or_join", "password");
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        mJoinBtn = findViewById(R.id.join_button);
        mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateOrFindAccountActivity.class);
                intent.putExtra("intent_email_or_password_or_join", "join");
                startActivity(intent);
            }
        });
        mLoginBtn = findViewById(R.id.login_button);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainNoticeListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}