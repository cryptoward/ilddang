package com.ilddang.job.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ilddang.job.R;
import com.ilddang.job.retrofit.JoinResult;
import com.ilddang.job.retrofit.RetrofitService;
import com.ilddang.job.util.Constants;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateOrFindAccountActivity extends BaseActivity {
    private final String TAG = CreateOrFindAccountActivity.class.getSimpleName();
    private Button mJoinButton;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mPhoneNum;
    private EditText mAuthNum;
    private String mFcmToken;

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
            mEmail = findViewById(R.id.email_edit_text);
            mEmail.setVisibility(View.VISIBLE);
        } else if (extra.equals("join")) {
            createActionBar(getResources().getString(R.string.join));
            getFcmToken();
            mName = findViewById(R.id.name_edit_text);
            mEmail = findViewById(R.id.email_edit_text);
            mEmail.setVisibility(View.VISIBLE);
            mPhoneNum = findViewById(R.id.phone_number_edit_text);
            mPassword = findViewById(R.id.password_edit_text);
            mPasswordConfirm = findViewById(R.id.confirm_password_edit_text);
            mAuthNum = findViewById(R.id.auth_num_edit_text);
            LinearLayout layout = findViewById(R.id.join_password_layout);
            layout.setVisibility(View.VISIBLE);
            mJoinButton = findViewById(R.id.join_button);
            mJoinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doJoin();
                }
            });
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

    private void doJoin() {
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
            params.put("user_name", mName.getText().toString());
            params.put("user_type", 0);
            params.put("user_phone", mPhoneNum.getText().toString());
            params.put("user_key_android", getDevicesId());
            params.put("user_key_fcm", mFcmToken);

            try {
                Call<JoinResult> call = service.requestJoin(params);
                call.enqueue(new Callback<JoinResult>() {
                    @Override
                    public void onResponse(Call<JoinResult> call, Response<JoinResult> response) {
                        if (response.isSuccessful()) {
                            JoinResult result = response.body();
                            Log.d(TAG, "onResponse: 성공 결과\n" + result.toString());
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
                    public void onFailure(Call<JoinResult> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            } catch (IllegalArgumentException exception) {
                Log.e(TAG, exception.toString());
            }
        }
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

    private boolean isValid() {
        if (mEmail.getText().toString().isEmpty()) {
            showDialog("이메일을");
            return false;
        } else if (mPassword.getText().toString().isEmpty() || mPasswordConfirm.getText().toString().isEmpty()) {
            showDialog("비밀번호를");
            return false;
        } else if (mAuthNum.getText().toString().isEmpty()) {
            showDialog("인증번호를");
            return false;
        }
        return true;
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

    private String getDevicesId(){
        String androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return androidId;
    }

}
