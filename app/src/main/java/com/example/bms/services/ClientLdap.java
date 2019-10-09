package com.example.bms.services;

import com.example.bms.model.LoginLdap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClientLdap {

    @FormUrlEncoded
    @POST("api")
    Call<LoginLdap> loginLdapClient(@Field("username") LoginLdap loginLdap);

    @GET("token")
    Call<LoginLdap> token (@Query("token") String token);
}