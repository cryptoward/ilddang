package com.ilddang.job.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ilddang.job.R;
import com.ilddang.job.retrofit.ResponseResult;
import com.ilddang.job.retrofit.RetrofitService;
import com.ilddang.job.util.Constants;
import com.ilddang.job.util.Util;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private TextView mFindIdPassword;
    private Button mJoinBtn;
    private Button mLoginBtn;
    private EditText mEmail;
    private EditText mPassword;
    private String mFcmToken;
    private CheckBox mAutoLogin;

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
                        intent.putExtra("intent_email_or_password_or_join_or_phone", "email");
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                password.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, CreateOrFindAccountActivity.class);
                        intent.putExtra("intent_email_or_password_or_join_or_phone", "password");
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        mJoinBtn = findViewById(R.id.bottom_button);
        mJoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TncPpActivity.class);
                startActivity(intent);
            }
        });
        mLoginBtn = findViewById(R.id.login_button);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValid()) {
                    doLogin();
                }
            }
        });

        mEmail = findViewById(R.id.email_edit_text);
        mPassword = findViewById(R.id.password_edit_text);
        mAutoLogin = findViewById(R.id.auto_login_checkbox);
        getFcmToken();
    }

    private void doLogin() {
        if (isValid()) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(clientBuilder.build())
                    .build();
            RetrofitService service = retrofit.create(RetrofitService.class);

            HashMap params = new HashMap();
            params.put("user_id", mEmail.getText().toString());
            params.put("user_pw", mPassword.getText().toString());
            params.put("user_auto_login", mAutoLogin.isChecked() ? "Y" : "N");
            params.put("user_os", "android");
            params.put("user_key_android", Util.getDevicesId(this));
            params.put("user_key_fcm", mFcmToken);

            try {
                Call<ResponseBody> call = service.requestLogin(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
                            StringBuilder builder = new StringBuilder();

                            try {
                                String str = result.string();
                                int idx = str.indexOf("hint");
                                idx = idx + 7;
                                for (int i = idx ; str.charAt(i) != '\"' ; i ++) {
                                    builder.append(str.charAt(i));
                                }
                                Log.d(TAG, "onResponse: 성공 결과\n" + builder.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(MainActivity.this, MainNoticeListActivity.class);
                            intent.putExtra("login_session", builder.toString());
                            startActivity(intent);
                            finish();
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                Log.d(TAG, "onResponse: 실패\n" + jObjError.getJSONObject("error").getString("message"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());

                    }
                });
            } catch (IllegalArgumentException exception) {
                Log.e(TAG, exception.toString());
            }
        }
    }

    private boolean isValid() {
        if (mEmail.getText().toString().isEmpty()) {
            showDialog("이메일을");
            return false;
        } else if (mPassword.getText().toString().isEmpty()) {
            showDialog("비밀번호를");
            return false;
        }
        return true;
    }

    private void showDialog(String noInput) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.base_dialog_fragment, null);
        builder.setView(view);

        final TextView title = (TextView) view.findViewById(R.id.dialog_title);
        title.setText(noInput + " 입력해주세요.");

        view.findViewById(R.id.dialog_first_button).setVisibility(View.GONE);
        final Button confirm = (Button) view.findViewById(R.id.dialog_second_button);
        confirm.setText(getResources().getString(R.string.confirm));
        final AlertDialog dialog = builder.create();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getFcmToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        mFcmToken = task.getResult();
                    }
                });
    }



}