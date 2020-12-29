package com.ilddang.job.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST("user/join")
    Call<JoinResult> requestJoin(@FieldMap HashMap<String, Object> map);
}
