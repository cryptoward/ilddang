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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.ilddang.job.R;
import com.ilddang.job.retrofit.RetrofitService;
import com.ilddang.job.util.Constants;
import com.ilddang.job.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateOrFindAccountActivity extends BaseActivity {
    private final String TAG = CreateOrFindAccountActivity.class.getSimpleName();
    private Button mBottomButton;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordConfirm;
    private EditText mPhoneNum;
    private EditText mAuthNum;
    private String mFcmToken;
    private Button mAuthNumRequestBtn;
    private RetrofitService mRetrofitService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_find_accont);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("intent_email_or_password_or_join_or_phone");


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
        mRetrofitService = retrofit.create(RetrofitService.class);

        if (extra.equals("email")) {
            createActionBar(getResources().getString(R.string.find_email));
            mName = findViewById(R.id.name_edit_text);
            mPhoneNum = findViewById(R.id.phone_number_edit_text);
            mAuthNum = findViewById(R.id.auth_num_edit_text);
            mAuthNumRequestBtn = findViewById(R.id.request_auth_num_button);
            mAuthNumRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestFindId();
                }
            });
            mBottomButton = findViewById(R.id.bottom_button);
            mBottomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestFindIdCheck();
                }
            });
        } else if (extra.equals("password")) {
            createActionBar(getResources().getString(R.string.find_password));
            mEmail = findViewById(R.id.email_edit_text);
            mEmail.setVisibility(View.VISIBLE);
            mName = findViewById(R.id.name_edit_text);
            mPhoneNum = findViewById(R.id.phone_number_edit_text);
            mAuthNum = findViewById(R.id.auth_num_edit_text);
            mAuthNumRequestBtn = findViewById(R.id.request_auth_num_button);
            mAuthNumRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestFindPw();
                }
            });
            mBottomButton = findViewById(R.id.bottom_button);
            mBottomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestFindPwCheck();
                }
            });
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
            mAuthNumRequestBtn = findViewById(R.id.request_auth_num_button);
            mAuthNumRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestFindPw();
                }
            });
            mBottomButton = findViewById(R.id.bottom_button);
            mBottomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doJoin();
                }
            });
        } else if (extra.equals("phone")) {
            createActionBar(getResources().getString(R.string.change_phone_number));
            Button changePhone = findViewById(R.id.bottom_button);
            changePhone.setText(getResources().getString(R.string.change_phone_number));
            EditText name = findViewById(R.id.name_edit_text);
            name.setBackgroundColor(ContextCompat.getColor(this, R.color.base_light_grey));
            //TODO: get name
            name.setText("홍길동");
            name.setEnabled(false);
            name.setInputType(InputType.TYPE_NULL);
        }

    }

    private void requestFindId() {
        if (isValidFindId()) {
            HashMap params = new HashMap();
            params.put("user_name", mName.getText().toString());
            params.put("user_phone", mPhoneNum.getText().toString());

            try {
                Call<ResponseBody> call = mRetrofitService.requestFindId(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
                            try {
                                String str = result.string();
                                Log.d(TAG, "onResponse: 성공 결과\n" + str);
                                JSONObject json = new JSONObject(str);
                                boolean isSuccess = json.getBoolean("success");
                                if (isSuccess) {
                                } else {
                                    Toast.makeText(CreateOrFindAccountActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    private void requestFindPw() {
        if (isValidFindPw()) {
            HashMap params = new HashMap();
            params.put("user_id", mEmail.getText().toString());
            params.put("user_name", mName.getText().toString());
            params.put("user_phone", mPhoneNum.getText().toString());

            try {
                Call<ResponseBody> call = mRetrofitService.requestFindPw(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
                            String str = null;
                            try {
                                str = result.string();
                                Log.d(TAG, "onResponse: 성공 결과\n" + str);
                                JSONObject json = new JSONObject(str);
                                boolean isSuccess = json.getBoolean("success");
                                if (isSuccess) {
                                } else {
                                    Toast.makeText(CreateOrFindAccountActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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

    private void requestFindIdCheck() {
        if (isValidFindIdCheck()) {
            HashMap params = new HashMap();
            params.put("user_name", mName.getText().toString());
            params.put("user_phone", mPhoneNum.getText().toString());
            params.put("auth_code", mAuthNum.getText().toString());

            try {
                Call<ResponseBody> call = mRetrofitService.requestFindIdCheck(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
                            String str = "";

                            try {
                                str = result.string();
                                Log.d(TAG, "onResponse: 성공 결과\n" + str);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrFindAccountActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View view = inflater.inflate(R.layout.base_dialog_fragment, null);
                            builder.setView(view);

                            final Button firstButton = (Button) view.findViewById(R.id.dialog_first_button);
                            final Button secondButton = (Button) view.findViewById(R.id.dialog_second_button);
                            TextView title = view.findViewById(R.id.dialog_title);
                            StringBuilder strBuilder = new StringBuilder();

                            int idx = str.indexOf("ColumnValues");
                            idx = idx + 14;
                            while (str.charAt(idx) != '\"') {
                                idx++;
                            }
                            idx++;
                            while (str.charAt(idx) != '\\') {
                                strBuilder.append(str.charAt(idx));
                                idx++;
                            }

                            title.setText("회원님의 아이디는 " + strBuilder.toString() + " 입니다.");
                            firstButton.setVisibility(View.GONE);
                            secondButton.setText(getResources().getString(R.string.confirm));

                            final AlertDialog dialog = builder.create();
                            secondButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            });
                            dialog.show();
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

    private void requestFindPwCheck() {
        if (isValidFindPwCheck()) {
            HashMap params = new HashMap();
            params.put("user_id", mEmail.getText().toString());
            params.put("user_name", mName.getText().toString());
            params.put("user_phone", mPhoneNum.getText().toString());
            params.put("auth_code", mAuthNum.getText().toString());

            try {
                Call<ResponseBody> call = mRetrofitService.requestFindPwCheck(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
                            String str = "";
                            try {
                                str = result.string();
                                Log.d(TAG, "onResponse: 성공 결과\n" + str);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateOrFindAccountActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View view = inflater.inflate(R.layout.base_dialog_fragment, null);
                            builder.setView(view);

                            final Button firstButton = (Button) view.findViewById(R.id.dialog_first_button);
                            final Button secondButton = (Button) view.findViewById(R.id.dialog_second_button);
                            TextView title = view.findViewById(R.id.dialog_title);
                            StringBuilder strBuilder = new StringBuilder();

                            int idx = str.indexOf("ColumnValues");
                            idx = idx + 14;
                            while (str.charAt(idx) != '\"') {
                                idx++;
                            }
                            idx++;
                            while (str.charAt(idx) != '\\') {
                                strBuilder.append(str.charAt(idx));
                                idx++;
                            }

                            title.setText("회원님의 비밀번호는 " + strBuilder.toString() + " 입니다.");
                            firstButton.setVisibility(View.GONE);
                            secondButton.setText(getResources().getString(R.string.confirm));

                            final AlertDialog dialog = builder.create();
                            secondButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    onBackPressed();
                                }
                            });
                            dialog.show();
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
            params.put("user_key_android", Util.getDevicesId(this));
            params.put("user_key_fcm", mFcmToken);

            try {
                Call<ResponseBody> call = service.requestJoin(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody result = response.body();
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    private boolean isValidFindId() {
        if (mName.getText().toString().isEmpty()) {
            showDialog("이름을");
            return false;
        } else if (mPhoneNum.getText().toString().isEmpty()) {
            showDialog("휴대폰 번호를");
            return false;
        }
        return true;
    }

    private boolean isValidFindPw() {
        if (mEmail.getText().toString().isEmpty()) {
            showDialog("이메일을");
            return false;
        } else if (mName.getText().toString().isEmpty()) {
            showDialog("이름을");
            return false;
        } else if (mPhoneNum.getText().toString().isEmpty()) {
            showDialog("휴대폰 번호를");
            return false;
        }
        return true;
    }


    private boolean isValidFindIdCheck() {
        if (mName.getText().toString().isEmpty()) {
            showDialog("이름을");
            return false;
        } else if (mPhoneNum.getText().toString().isEmpty()) {
            showDialog("휴대폰 번호를");
            return false;
        } else if (mAuthNum.getText().toString().isEmpty()) {
            showDialog("인증번호를");
            return false;
        }
        return true;
    }

    private boolean isValidFindPwCheck() {
        if (mEmail.getText().toString().isEmpty()) {
            showDialog("이메일을");
            return false;
        } else if (mName.getText().toString().isEmpty()) {
            showDialog("이름을");
            return false;
        } else if (mPhoneNum.getText().toString().isEmpty()) {
            showDialog("휴대폰 번호를");
            return false;
        } else if (mAuthNum.getText().toString().isEmpty()) {
            showDialog("인증번호를");
            return false;
        }
        return true;
    }

    private boolean isValid() {
        if (mEmail.getText().toString().isEmpty()) {
            showDialog("이메일을");
            return false;
        } else if (mPassword.getText().toString().isEmpty() || mPasswordConfirm.getText().toString().isEmpty()) {
            showDialog("비밀번호를");
            return false;
        } else if (mName.getText().toString().isEmpty()) {
            showDialog("이름을");
            return false;
        }  else if (mName.getText().toString().isEmpty()) {
            showDialog("휴대폰 번호를");
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

    static class MyDeserializer<T> implements JsonDeserializer<T>
    {
        @Override
        public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException {
            JsonElement content = je.getAsJsonObject();

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(content, type);
        }
    }

}
