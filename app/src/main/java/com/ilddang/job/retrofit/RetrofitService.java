package com.ilddang.job.retrofit;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("user/join")
    Call<ResponseBody> requestJoin(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseBody> requestLogin(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("user/findid")
    Call<ResponseBody> requestFindId(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("user/findidcheck")
    Call<ResponseBody> requestFindIdCheck(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("user/findpw")
    Call<ResponseBody> requestFindPw(@FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST("user/findpwcheck")
    Call<ResponseBody> requestFindPwCheck(@FieldMap HashMap<String, Object> map);
}
